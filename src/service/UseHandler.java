package service;

import controller.GameController;
import model.Comandos;
import repository.CenaDAO;
import repository.ComandosDAO;

import java.sql.SQLException;

public class UseHandler {

    public static String processarUse(String comandoUser, GameController gameController) throws SQLException {
        int cenaAtualId = gameController.getCenaAtualId();
        int sequenciaAtual = gameController.getSequenciaAtual();

        Comandos comandos = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);

        if (comandos.getSequencia() == null || comandos.getSequencia() == sequenciaAtual) {
            String resultado = comandos.getResultadoPositivo();

            if (comandos.getSequencia() != null) {
                gameController.incrementarSequencia();
            }

            if (comandos.getIdCenaDestino() != null && comandos.getIdCenaDestino() != 0) {
                gameController.setCenaAtualId(comandos.getIdCenaDestino());
                gameController.resetarSequencia();
                return resultado + "\n" + CenaDAO.findCenaById(gameController.getCenaAtualId()).getDescricao();
            }
            return resultado;
        } else {
            return "Você está tentando executar os comandos fora de ordem. Tente novamente.";
        }
    }
}
