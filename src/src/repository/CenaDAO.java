package repository;

import config.MySql;
import model.Cena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CenaDAO {
    public static Cena findCenaById(Integer id) throws SQLException {
        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM cena WHERE idCena = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Cena cena = null;

        if (rs.next()) {
            cena = new Cena(
                    rs.getInt("idCena"),
                    rs.getString("titulo"),
                    rs.getString("descricao")
            );
        }
        return cena;
    }

    public static Cena findCenaByDestinoId(Integer idCenaDestino) throws SQLException {
        if (idCenaDestino == null) {
            return null;
        }

        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM cena WHERE idCena = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idCenaDestino);

        ResultSet rs = ps.executeQuery();
        Cena cena = null;

        if (rs.next()) {
            cena = new Cena(
                    rs.getInt("idCena"),
                    rs.getString("titulo"),
                    rs.getString("descricao")
            );
        }
        return cena;
    }
}
