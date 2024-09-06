package controller;

import model.Comandos;
import repository.ComandosDAO;

import java.sql.SQLException;

public class GameController {

    public String processarComando(String comandoUser) {
        try {
            Comandos comandos = ComandosDAO.findComandosByName(comandoUser);

            if (comandos.getResultadoPositivo() != null) {
                return comandos.getResultadoPositivo();
            } else {
                return "Comando não reconhecido. Tente novamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Ocorreu um erro ao processar o comando.";
        }
    }
}
