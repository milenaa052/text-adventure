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
        String sql = "SELECT objeto.*, cena.idCena FROM objeto " +
                "INNER JOIN cena ON objeto.idCenaObjeto = cena.idCena " +
                "WHERE objeto.nomeObjeto = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nome);

        ResultSet rs = ps.executeQuery();
        Objeto objeto = null;

        if (rs.next()) {
            objeto = new Objeto();
            objeto.setIdObjeto(rs.getInt("idObjeto"));
            objeto.setNomeObjeto(rs.getString("nomeObjeto"));
            objeto.setDescricaoCheck(rs.getString("descricaoCheck"));
            objeto.setInventarioBool(rs.getInt("inventarioBool"));
            objeto.setIdCenaObjeto(rs.getInt("idCenaObjeto"));
        }
        return objeto;
    }

}

