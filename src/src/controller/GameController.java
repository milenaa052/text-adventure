package controller;

import model.Objeto;
import repository.ObjetoDAO;

import java.sql.SQLException;

public class GameController {

    public String processarComando(String comandoUser) {
        String[] partes = comandoUser.split(" ", 2);
        String comando = partes[0];
        String argumento = partes.length > 1 ? partes[1] : "";

        try {
            if ("check".equalsIgnoreCase(comando)) {
                return processarCheck(argumento);
            }
            // Adicione outros comandos aqui
            else {
                return "Comando não reconhecido. Tente novamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Ocorreu um erro ao processar o comando.";
        }
    }


    private String processarCheck(String argumento) throws SQLException {
        if (argumento.isEmpty()) {
            return "Opa amigo, você precisa digitar o nome de um objeto.";
        }

        try {
            Objeto objeto = ObjetoDAO.findObjetoByNome(argumento);

            if (objeto != null) {
                return objeto.getDescricaoCheck();
            } else {
                return "Opa amigo, esse objeto não existe.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao processar o comando.";
        }
    }

}
