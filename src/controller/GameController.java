package controller;

import model.Comandos;
import repository.CenaDAO;
import repository.ComandosDAO;
import model.Objeto;
import repository.ObjetoDAO;

import java.sql.SQLException;

public class GameController {
    private int cenaAtualId; // armazena o id da cena atual do jogo
    private int sequenciaAtual; //armazena a sequenciaAtual


    public GameController(int cenaInicialId) { // Recebe o ID da cena inicial e o define como cenaAtualId. Isso inicia o jogo na cena especificada.
        this.cenaAtualId = cenaInicialId;
        this.sequenciaAtual = 1; //declara a sequencia atual como 1

    }

    public int getCenaAtualId() { //Retorna o ID da cena atual. É útil para acessar a cena atual fora da classe.
        return cenaAtualId;
    }

    public String processarComando(String comandoUser) { //recebe o comando do usuário para fazer o processamento
        try {
            if (comandoUser.equalsIgnoreCase("HELP")) {
                return processarHelp(comandoUser);
            }

            if (comandoUser.equalsIgnoreCase("QUIT")) {
                return "Saindo do jogo...";
            }

            Comandos comandos = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);

            if (comandos != null && comandos.getResultadoPositivo() != null) {

                // Verifica se a sequência é null ou se o comando está na ordem correta
                if (comandos.getSequencia() == null || comandos.getSequencia() == sequenciaAtual) {
                    String resultado = comandos.getResultadoPositivo();

                    // Incrementa a sequência atual apenas se a sequência não for null
                    if (comandos.getSequencia() != null) {
                        sequenciaAtual++;
                    }

                    // Verifica se há uma cena de destino e a atualiza
                    if (comandos.getIdCenaDestino() != null && comandos.getIdCenaDestino() != 0) {
                        this.cenaAtualId = comandos.getIdCenaDestino();
                        this.sequenciaAtual = 1;
                        return resultado + "\n" + CenaDAO.findCenaById(cenaAtualId).getDescricao();
                    }
                    return resultado;
                } else {
                    return "Você está tentando executar os comandos fora de ordem. Tente novamente.";
                }
            } else {
                return processarCheck(comandoUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Ocorreu um erro ao processar o comando.";
        }
    }

    // função processarCheck com a verificação da cena do objeto
    public String processarCheck(String comandoUser) {
        String[] partes = comandoUser.split(" ", 2); //divide o comando digitado pelo usuário em duas partes check + objeto
        String check = partes[0]; //declara na variável check a primeira palavra digitada pelo usuário
        String argumento = partes.length > 1 ? partes[1] : ""; //declara na variável argumento a segunda palavra digitada pelo usuário

        try {

            if ("check".equalsIgnoreCase(check)) { //verifica se o valor da variável check é igual ao "check"
                if (argumento.isEmpty()) { //verifica se o argumento está vazio
                    return "Opa amigo, você precisa digitar o nome de um objeto.";
                }

                try {
                    Objeto objeto = ObjetoDAO.findObjetoByNome(argumento); //encontra objeto pelo nome

                    if (objeto != null) { //se o obj não for nulo, verifica a cena
                        Integer idCenaObjeto = objeto.getIdCenaObjeto();

                        // verifica se o objeto ta na cena atual
                        if (idCenaObjeto != null && idCenaObjeto.equals(cenaAtualId)) {
                            return objeto.getDescricaoCheck();
                        } else {
                            return "Opa amigo, esse objeto não está na cena atual.";
                        }
                    } else {
                        return "Opa amigo, esse objeto não existe.";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Erro ao processar o comando.";
                }
            } else {
                return "Comando não reconhecido. Tente novamente.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocorreu um erro ao processar o comando.";
        }
    }

    public String processarHelp(String comandoUser) {
        if(comandoUser.equalsIgnoreCase("HELP")) {
            System.out.println("O objetivo do text adventure é o usuário interagir com os objetos descritos na cena (identificados pelos nomes em letra maiúsculas) para avançar no jogo. Os comandos possíveis são:\n\n" +
                    "HELP: exbibe o menu de ajuda do jogo\n" +
                    "USE [ITEM]: interage com o item da cena\n" +
                    "CHECK [ITEM]: mostra a descrição do objeto na cena\n" +
                    "GET [ITEM]: Se possível, adiciona o item ao inventário\n" +
                    "INVENTORY: mostra os itens que estão no inventário\n" +
                    "USE [INVENTORY_ITEM] WITH [SCENE_ITEM]: Realiza a ação utilizando um item do inventário com um item da cena\n" +
                    "SAVE: salva o jogo\n" +
                    "LOAD: carrega um jogo salvo\n" +
                    "RESTART: reinicia o jogo");
        }
        return "";
    }
}
