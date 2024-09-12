package controller;

import model.Cena;
import model.Comandos;
import repository.CenaDAO;
import repository.ComandosDAO;
import model.Objeto;
import repository.InventarioDAO;
import repository.ObjetoDAO;
import repository.SaveDAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class GameController {
    private int cenaAtualId; // armazena o id da cena atual do jogo
    private int sequenciaAtual; // armazena a sequência atual

    public GameController(int cenaInicialId) { // Recebe o ID da cena inicial e o define como cenaAtualId. Isso inicia o jogo na cena especificada.
        this.cenaAtualId = cenaInicialId;
        this.sequenciaAtual = 1; // declara a sequência atual como 1
    }

    public int getCenaAtualId() { // Retorna o ID da cena atual. É útil para acessar a cena atual fora da classe.
        return cenaAtualId;
    }

    public String processarComando(String comandoUser) {
        try {
            // Comando "save" para salvar o progresso
            if (comandoUser.startsWith("save ")) {
                String[] partes = comandoUser.split(" ");
                if (partes.length > 1) {
                    try {
                        int saveId = Integer.parseInt(partes[1]);
                        SaveDAO.salvarProgresso(cenaAtualId, saveId);
                        return "Progresso salvo com sucesso no slot " + saveId + ".";
                    } catch (NumberFormatException e) {
                        return "ID de salvamento inválido.";
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "Erro ao salvar o progresso.";
                    }
                }
                return "Comando inválido. Tente 'save [ID]'.";
            }

            // Comando "load" para carregar o progresso
            if (comandoUser.startsWith("load ")) {
                String[] partes = comandoUser.split(" ");
                if (partes.length > 1) {
                    try {
                        int saveId = Integer.parseInt(partes[1]);
                        int cenaId = SaveDAO.carregarProgresso(saveId);
                        if (cenaId != -1) {
                            cenaAtualId = cenaId;
                            sequenciaAtual = 1;
                            Cena cenaAtual = CenaDAO.findCenaById(cenaAtualId);
                            return "Progresso carregado com sucesso. " + cenaAtual.getDescricao();
                        } else {
                            return "ID de salvamento não encontrado.";
                        }
                    } catch (NumberFormatException e) {
                        return "ID de salvamento inválido.";
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "Erro ao carregar o progresso.";
                    }
                }
                return "Comando inválido. Tente 'load [ID]'.";
            }

            // Comando "load" para listar todos os salvamentos
            if (comandoUser.equalsIgnoreCase("load")) {
                try {
                    List<Integer> saves = SaveDAO.listarSaves();
                    if (saves.isEmpty()) {
                        return "Nenhum salvamento encontrado.";
                    }
                    StringBuilder salvaList = new StringBuilder("Salvamentos disponíveis:\n");
                    for (int saveId : saves) {
                        salvaList.append("- ").append(saveId).append("\n");
                    }
                    return salvaList.toString();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "Erro ao listar salvamentos.";
                }
            }

            // Comando "get" para pegar objetos, agora com verificação da coluna inventarioBool
            if (comandoUser.startsWith("get ")) {
                String[] partes = comandoUser.split(" ");
                if (partes.length > 1) {
                    String nomeObjeto = partes[1];

                    // Verificar se o objeto existe no banco de dados
                    Objeto objeto = ObjetoDAO.findObjetoByNome(nomeObjeto);
                    if (objeto == null) {
                        return "Esse objeto não existe.";
                    }

                    // Verificar se o objeto já está no inventário
                    if (InventarioDAO.isObjetoNoInventario(objeto.getIdObjeto())) {
                        return "Objeto já foi adicionado ao inventário.";
                    }

                    // Verifica se o objeto pode ser adicionado ao inventário ou não
                    if (objeto.getInventarioBool().equals(0)) {
                        return "Como que você vai adicionar este objeto em um inventário?????????";
                    }

                    // Adicionar ao inventário
                    InventarioDAO.adicionarAoInventario(objeto.getIdObjeto());

                    // Agora, validar se o comando "get" faz parte da sequência
                    Comandos comandoGet = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);

                    if (comandoGet != null && comandoGet.getSequencia() == sequenciaAtual) {
                        sequenciaAtual++; // Incrementa a sequência após o comando correto
                    }

                    return "Objeto " + nomeObjeto + " adicionado ao inventário.";
                }
                return "Comando inválido. Tente 'get [objeto]'.";
            }

            // Comando "inventory" para exibir o inventário
            if (comandoUser.equalsIgnoreCase("inventory")) {
                return listarInventario();
            }

            if (comandoUser.equalsIgnoreCase("HELP")) {
                return processarHelp(comandoUser);
            }

            if (comandoUser.equalsIgnoreCase("QUIT")) {
                InventarioDAO.limparInventario();
                return "Saindo do jogo...";
            }

            // Tratamento dos outros comandos (como 'use')
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

    // Função processarCheck com a verificação da cena do objeto
    public String processarCheck(String comandoUser) {
        String[] partes = comandoUser.split(" ", 2); // divide o comando digitado pelo usuário em duas partes check + objeto
        String check = partes[0]; // declara na variável check a primeira palavra digitada pelo usuário
        String argumento = partes.length > 1 ? partes[1] : ""; // declara na variável argumento a segunda palavra digitada pelo usuário

        try {
            if ("check".equalsIgnoreCase(check)) { // verifica se o valor da variável check é igual ao "check"
                if (argumento.isEmpty()) { // verifica se o argumento está vazio
                    return "Opa amigo, você precisa digitar o nome de um objeto.";
                }

                try {
                    Objeto objeto = ObjetoDAO.findObjetoByNome(argumento); // encontra objeto pelo nome

                    if (objeto != null) { // se o obj não for nulo, verifica a cena
                        Integer idCenaObjeto = objeto.getIdCenaObjeto();

                        // Verifica se o objeto está na cena atual
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
        if (comandoUser.equalsIgnoreCase("HELP")) {
            System.out.println("O objetivo do text adventure é o usuário interagir com os objetos descritos na cena (identificados pelos nomes em letra maiúsculas) para avançar no jogo. Os comandos possíveis são:\n\n" +
                    "HELP: exibe o menu de ajuda do jogo\n" +
                    "USE [ITEM]: interage com o item da cena\n" +
                    "CHECK [ITEM]: mostra a descrição do objeto na cena\n" +
                    "GET [ITEM]: Se possível, adiciona o item ao inventário\n" +
                    "INVENTORY: mostra os itens que estão no inventário\n" +
                    "USE [INVENTORY_ITEM] WITH [ITEM]: usa um item do inventário com um item da cena atual\n" +
                    "SAVE [ID]: salva o progresso no slot especificado\n" +
                    "LOAD [ID]: carrega o progresso do slot especificado\n" +
                    "LOAD: lista todos os slots de salvamento disponíveis\n" +
                    "QUIT: encerra o jogo e limpa o inventário\n" +
                    "Para comandos específicos, consulte a documentação do jogo.");
            return null;
        }
        return "Comando de ajuda inválido.";
    }

    // Listar itens no inventário
    public String listarInventario() {
        try {
            List<Objeto> inventario = InventarioDAO.listarInventario();
            if (inventario.isEmpty()) {
                return "O inventário está vazio.";
            }
            StringBuilder sb = new StringBuilder("Itens no inventário:\n");
            for (Objeto objeto : inventario) {
                sb.append("- ").append(objeto.getNomeObjeto()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao listar o inventário.";
        }
    }
}
