package service;

import model.Cena;
import repository.CenaDAO;
import repository.SaveDAO;
import controller.GameController;

import java.sql.SQLException;

public class LoadHandler {
    public static String processsarLoad(String comandoUser, GameController gameController) throws SQLException {
        String[] partes = comandoUser.split(" ");
        if (partes.length > 1) {
            try {
                int saveId = Integer.parseInt(partes[1]);
                int cenaId = SaveDAO.carregarProgresso(saveId);

                if (cenaId != -1) {
                    gameController.setCenaAtualId(cenaId);
                    gameController.resetarSequencia();
                    Cena cenaAtual = CenaDAO.findCenaById(cenaId);
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
}