package controller;

import model.Objeto;
import repository.ObjetoDAO;

import java.sql.SQLException;

public class GameController {
    private Integer idCenaAtual;

    public GameController(Integer idCenaAtual) {
        this.idCenaAtual = idCenaAtual;
    }

    public GameController() {
    }

    public String processarComando(String comandoUser) {
        String[] partes = comandoUser.split(" ", 2);
        String comando = partes[0];
        String argumento = partes.length > 1 ? partes[1] : "";

        try {
            if ("check".equalsIgnoreCase(comando)) {
                return processarCheck(argumento);
            } else {
                return "Comando não reconhecido, tente novamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao processar o comando.";
        }
    }

    private String processarCheck(String argumento) throws SQLException {
        if (argumento.isEmpty()) {
            return "Opa amigo, você precisa digitar o nome de um objeto.";
        }

        try {
            Objeto objeto = ObjetoDAO.findObjetoByNome(argumento);

            if (objeto != null) {
                Integer idCenaObjeto = objeto.getIdCenaObjeto();
                System.out.println("ID da Cena Atual: " + idCenaAtual);
                System.out.println("ID da Cena do Objeto: " + idCenaObjeto);

                if (idCenaObjeto != null && idCenaObjeto.equals(idCenaAtual)) {
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
    }
}
