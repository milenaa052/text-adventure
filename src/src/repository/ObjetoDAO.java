package repository;

import config.MySql;
import model.Objeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjetoDAO {
    public static Objeto findObjetoByNome(String nome) throws SQLException {
        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM objeto WHERE nomeObjeto = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nome);

        ResultSet rs = ps.executeQuery();
        Objeto objeto = null;

        if (rs.next()) {
            objeto = new Objeto();
            objeto.setIdObjeto(rs.getInt("idObjeto"));
            objeto.setNomeObjeto(rs.getString("nomeObjeto"));
            objeto.setDescricaoCheck(rs.getString("descricaoCheck"));

            // Verifique se a coluna realmente existe e se o nome está correto
            if (rs.getObject("idCenaObjeto") != null) {
                objeto.setIdCenaObjeto(rs.getInt("idCenaObjeto"));
            } else {
                objeto.setIdCenaObjeto(null);
            }
        }
        return objeto;
    }
}
