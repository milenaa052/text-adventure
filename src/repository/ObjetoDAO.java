package repository;

import config.MySql;
import model.Objeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjetoDAO {

    public static Objeto findObjetoById(Integer id) throws SQLException {

        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM objeto WHERE idObjeto = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Objeto objeto = new Objeto();

        if (rs.next()) {
            objeto.setIdObjeto(rs.getInt("idObjeto"));
            objeto.setNomeObjeto(rs.getString("nomeObjeto"));
        }
        return objeto;
    }

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
        }
        return objeto;
    }
}
