package service;

import repository.SaveDAO;

import java.sql.SQLException;

public class SaveHandler {
    public static String processsarSave(String comandoUser, int cenaAtualId) throws SQLException {
        String[] partes = comandoUser.split(" ");
        if (partes.length > 1) {
            try {
                int saveId = Integer.parseInt(partes[1]);
                SaveDAO.salvarProgresso(cenaAtualId, saveId);
                return "Progresso salvo com sucesso no slot " + saveId + ".";
            } catch (NumberFormatException e) {
                return "ID de salvamento inválido.";
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    return "O slot de salvamento " + partes[1] + " já está em uso. Escolha um slot diferente.";
                }
                e.printStackTrace();
                return "Erro ao salvar o progresso.";
            }
        }
        return "Comando inválido. Tente 'save [ID]'.";
    }
}
