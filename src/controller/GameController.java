package controller;

import model.Comandos;
import repository.CenaDAO;
import repository.ComandosDAO;
import model.Objeto;
import repository.ObjetoDAO;

import java.sql.SQLException;

public class GameController {
    private int cenaAtualId; // armazena o id da cena atual do jogo

    public GameController(int cenaInicialId) { // Recebe o ID da cena inicial e o define como cenaAtualId. Isso inicia o jogo na cena especificada.
        this.cenaAtualId = cenaInicialId;
    }

    public int getCenaAtualId() { //Retorna o ID da cena atual. É útil para acessar a cena atual fora da classe.
        return cenaAtualId;
    }

    public String processarComando(String comandoUser) { //recebe o comando do usuário para fazer o processamento
        try {
            Comandos comandos = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId); //busca comandos no banco de dados

            if (comandos != null && comandos.getResultadoPositivo() != null) { //se o comando for encontrado e tiver o resultado positivo o método continua
                String resultado = comandos.getResultadoPositivo(); //exibe resultado positivo após o usuário digitar o comando certo

                if (comandos.getIdCenaDestino() != null && comandos.getIdCenaDestino() != 0) { // se o idCenaDestino estiver definido e não for 0 atualiza o cenaAtualId e retorna o resultado positivo seguido pela descrição da prox cena
                    this.cenaAtualId = comandos.getIdCenaDestino();
                    return resultado + "\n" + CenaDAO.findCenaById(cenaAtualId).getDescricao();
                }
                return resultado;
            } else {
                return processarCheck(comandoUser); //se não exibe a função processarCheck
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
}
