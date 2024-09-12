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

    // Construtor que inicializa o GameController com a cena inicial
    public GameController(int cenaInicialId) {
        this.cenaAtualId = cenaInicialId; // define a cena inicial
        this.sequenciaAtual = 1; // inicializa a sequência atual como 1
    }

    // Getter para obter o ID da cena atual
    public int getCenaAtualId() {
        return cenaAtualId;
    }

    // Processa o comando do usuário e retorna a resposta apropriada
    public String processarComando(String comandoUser) {
        try {
            // Comando para salvar o progresso do jogo
            if (comandoUser.startsWith("save ")) {
                String[] partes = comandoUser.split(" ");
                if (partes.length > 1) {
                    try {
                        int saveId = Integer.parseInt(partes[1]); // ID de salvamento fornecido pelo usuário
                        SaveDAO.salvarProgresso(cenaAtualId, saveId); // Salva o progresso no banco de dados
                        return "Progresso salvo com sucesso no slot " + saveId + ".";
                    } catch (NumberFormatException e) {
                        return "ID de salvamento inválido."; // Mensagem para ID de salvamento inválido
                    } catch (SQLException e) {
                        // Verifica se o erro é devido a uma entrada duplicada
                        if (e.getErrorCode() == 1062) {
                            return "O slot de salvamento " + partes[1] + " já está em uso. Escolha um slot diferente.";
                        }
                        e.printStackTrace();
                        return "Erro ao salvar o progresso."; // Mensagem para erro geral ao salvar
                    }
                }
                return "Comando inválido. Tente 'save [ID]'.";
            }

            // Comando para carregar o progresso do jogo
            if (comandoUser.startsWith("load ")) {
                String[] partes = comandoUser.split(" ");
                if (partes.length > 1) {
                    try {
                        int saveId = Integer.parseInt(partes[1]); // ID de salvamento fornecido pelo usuário
                        int cenaId = SaveDAO.carregarProgresso(saveId); // Carrega o progresso do banco de dados
                        if (cenaId != -1) {
                            cenaAtualId = cenaId; // Atualiza a cena atual
                            sequenciaAtual = 1; // Reinicia a sequência
                            Cena cenaAtual = CenaDAO.findCenaById(cenaAtualId); // Obtém a descrição da cena
                            return "Progresso carregado com sucesso. " + cenaAtual.getDescricao();
                        } else {
                            return "ID de salvamento não encontrado."; // Mensagem se o ID de salvamento não existir
                        }
                    } catch (NumberFormatException e) {
                        return "ID de salvamento inválido."; // Mensagem para ID de salvamento inválido
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "Erro ao carregar o progresso."; // Mensagem para erro geral ao carregar
                    }
                }
                return "Comando inválido. Tente 'load [ID]'.";
            }

            // Comando para listar todos os salvamentos disponíveis
            if (comandoUser.equalsIgnoreCase("load")) {
                try {
                    List<Integer> saves = SaveDAO.listarSaves(); // Obtém a lista de salvamentos
                    if (saves.isEmpty()) {
                        return "Nenhum salvamento encontrado."; // Mensagem se não houver salvamentos
                    }
                    StringBuilder salvaList = new StringBuilder("Salvamentos disponíveis:\n");
                    for (int saveId : saves) {
                        salvaList.append("- ").append(saveId).append("\n"); // Adiciona cada ID de salvamento à lista
                    }
                    return salvaList.toString();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "Erro ao listar salvamentos."; // Mensagem para erro ao listar salvamentos
                }
            }

            // Comando para reiniciar o jogo
            if (comandoUser.equalsIgnoreCase("RESTART")) {
                // Voltar para a cena inicial e limpar o inventário
                cenaAtualId = 1; // Cena inicial
                sequenciaAtual = 1; // Reinicia a sequência
                InventarioDAO.limparInventario(); // Limpa o inventário
                Cena cenaAtual = CenaDAO.findCenaById(cenaAtualId); // Obtém a descrição da cena inicial
                return "Jogo reiniciado. " + cenaAtual.getDescricao();
            }

            // Comando para pegar objetos (adicionar ao inventário)
            if (comandoUser.startsWith("get ")) {
                String[] partes = comandoUser.split(" ");
                if (partes.length > 1) {
                    String nomeObjeto = partes[1]; // Nome do objeto fornecido pelo usuário

                    // Verificar se o objeto existe no banco de dados
                    Objeto objeto = ObjetoDAO.findObjetoByNome(nomeObjeto);
                    if (objeto == null) {
                        return "Esse objeto não existe."; // Mensagem se o objeto não for encontrado
                    }

                    // Verificar se o objeto já está no inventário
                    if (InventarioDAO.isObjetoNoInventario(objeto.getIdObjeto())) {
                        return "Objeto já foi adicionado ao inventário."; // Mensagem se o objeto já estiver no inventário
                    }

                    // Verifica se o objeto pode ser adicionado ao inventário
                    if (objeto.getInventarioBool().equals(0)) {
                        return "Como que você vai adicionar este objeto em um inventário?????????"; // Mensagem para objeto não permitidos no inventário
                    }

                    // Adicionar ao inventário
                    InventarioDAO.adicionarAoInventario(objeto.getIdObjeto());

                    // Agora, validar se o comando "get" faz parte da sequência
                    Comandos comandoGet = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);

                    if (comandoGet != null && comandoGet.getSequencia() == sequenciaAtual) {
                        sequenciaAtual++; // Incrementa a sequência atual
                    }

                    return "Objeto " + nomeObjeto + " adicionado ao inventário."; // Mensagem de sucesso
                }
                return "Comando inválido. Tente 'get [objeto]'.";
            }

            // Comando "inventory" para exibir o inventário
            if (comandoUser.equalsIgnoreCase("inventory")) {
                return listarInventario(); // Chama o método para listar o inventário
            }

            // Comando "HELP" para exibir informações de ajuda
            if (comandoUser.equalsIgnoreCase("HELP")) {
                return processarHelp(comandoUser); // Chama o método para processar ajuda
            }

            // Comando "QUIT" para encerrar o jogo e limpar o inventário
            if (comandoUser.equalsIgnoreCase("QUIT")) {
                InventarioDAO.limparInventario(); // Limpa o inventário
                return "Saindo do jogo..."; // Mensagem de saída
            }

            // Processamento de comandos gerais
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
                        this.cenaAtualId = comandos.getIdCenaDestino(); // Atualiza a cena atual
                        this.sequenciaAtual = 1; // Reinicia a sequência
                        return resultado + "\n" + CenaDAO.findCenaById(cenaAtualId).getDescricao(); // Retorna o resultado e a descrição da nova cena
                    }
                    return resultado; // Retorna o resultado do comando
                } else {
                    return "Você está tentando executar os comandos fora de ordem. Tente novamente."; // Mensagem de erro de ordem de comandos
                }
            } else {
                return processarCheck(comandoUser); // Chama o método para processar o comando de verificação
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao processar o comando."; // Mensagem para erro geral de processamento
        }
    }

    // Processa o comando de verificação (CHECK) para exibir a descrição do objeto
    public String processarCheck(String comandoUser) throws SQLException {
        String[] partes = comandoUser.split(" ");
        String check = partes[0]; // Comando "check"
        String argumento = (partes.length > 1) ? partes[1] : null; // Argumento do comando "check"

        if (check.equalsIgnoreCase("check") && argumento != null) {
            Objeto objeto = ObjetoDAO.findObjetoByNome(argumento); // Procura o objeto pelo nome

            if (objeto != null) {
                // Retorna a descrição do objeto encontrado
                return objeto.getDescricaoCheck();
            } else {
                return "Esse objeto aí não existe."; // Mensagem se o objeto não for encontrado
            }
        }
        return "Comando inválido. Tente 'check [objeto]'.";
    }

    // Processa o comando de ajuda (HELP)
    public String processarHelp(String comandoUser) {
        if (comandoUser.equalsIgnoreCase("HELP")) {
            return "Comandos disponíveis:\n" +
                    "1. save [ID] - Salva o progresso no slot especificado.\n" +
                    "2. load [ID] - Carrega o progresso do slot especificado.\n" +
                    "3. load - Lista todos os salvamentos disponíveis.\n" +
                    "4. get [objeto] - Adiciona o objeto especificado ao inventário.\n" +
                    "5. inventory - Exibe os itens do inventário.\n" +
                    "6. RESTART - Reinicia o jogo e limpa o inventário.\n" +
                    "7. QUIT - Encerra o jogo e limpa o inventário.\n";
        }
        return "Comando HELP não reconhecido."; // Mensagem se o comando HELP não for reconhecido
    }

    // Lista os itens do inventário
    public String listarInventario() {
        try {
            List<Objeto> inventario = InventarioDAO.listarInventario(); // Obtém a lista de objetos no inventário
            if (inventario.isEmpty()) {
                return "Seu inventário está vazio."; // Mensagem se o inventário estiver vazio
            }
            StringBuilder inventarioList = new StringBuilder("Itens no inventário:\n");
            for (Objeto objeto : inventario) {
                inventarioList.append("- ").append(objeto.getNomeObjeto()).append("\n"); // Adiciona cada objeto à lista
            }
            return inventarioList.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao listar o inventário."; // Mensagem para erro ao listar o inventário
        }
    }
}
