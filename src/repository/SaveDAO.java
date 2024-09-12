package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaveDAO {

    // Salva o progresso do jogo
    public static void salvarProgresso(int cenaAtualId, int saveId) throws SQLException {
        String query = "INSERT INTO save (idSave, idSaveCenaAtual) VALUES (?, ?)";

        try (Connection conn = repository.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, saveId);
            pstmt.setInt(2, cenaAtualId);
            pstmt.executeUpdate();
        }
    }

    // Carrega o progresso com base no ID do save
    public static int carregarProgresso(int saveId) throws SQLException {
        String query = "SELECT idSaveCenaAtual FROM save WHERE idSave = ?";

        try (Connection conn = repository.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, saveId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idSaveCenaAtual");
            } else {
                return -1; // Indica que o salvamento não foi encontrado
            }
        }
    }

    // Lista todos os IDs de salvamento
    public static List<Integer> listarSaves() throws SQLException {
        String query = "SELECT idSave FROM save";
        List<Integer> saves = new ArrayList<>();

        try (Connection conn = repository.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                saves.add(rs.getInt("idSave"));
            }
        }
        return saves;
    }
}
