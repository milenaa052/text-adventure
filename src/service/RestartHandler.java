package service;

import controller.GameController;
import model.Cena;
import repository.CenaDAO;
import repository.InventarioDAO;

import java.sql.SQLException;

public class RestartHandler {
    public static String processsarRestart(String comandoUser, GameController gameController) throws SQLException {
        gameController.setCenaAtualId(1);
        gameController.resetarSequencia();
        InventarioDAO.limparInventario();
        Cena cenaAtual = CenaDAO.findCenaById(gameController.getCenaAtualId());
        return "Jogo reiniciado. " + cenaAtual.getDescricao();
    }
}
