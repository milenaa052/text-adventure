package repository;

import config.MySql;
import model.Comandos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComandosDAO {
    public static Comandos findComandosByNameAndCena(String comandoName, Integer cenaAtualId) throws SQLException {
        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM comandos WHERE comando = ? AND idCenaAtual = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, comandoName);
        ps.setInt(2, cenaAtualId);

        ResultSet rs = ps.executeQuery();
        Comandos comandos = null;

        if (rs.next()) {
            comandos = new Comandos(
                    rs.getInt("idComando"),
                    rs.getString("comando"),
                    rs.getString("resultadoPositivo"),
                    rs.getInt("idComandoObj"),
                    rs.getInt("idCenaAtual"),
                    rs.getInt("idCenaDestino"),
                    rs.getObject("sequencia") != null ? rs.getInt("sequencia") : null
            );
        }
        return comandos;
    }
}
