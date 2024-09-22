package service;

import repository.SaveDAO;

import java.sql.SQLException;
import java.util.List;

public class ListarLoadHandler {
    public static String listarLoad(String comandoUser) throws SQLException {
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
}
