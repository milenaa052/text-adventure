package repository;

import config.MySql;
import model.Inventario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventarioDAO {
    public static Inventario findInventarioById(Integer id) throws SQLException {

        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM inventario WHERE idInventario = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Inventario inventario = new Inventario();

        if (rs.next()) {
            inventario.setIdInventario(rs.getInt("idInventario"));
            inventario.setIdInventarioObj(rs.getInt("idInventarioObj"));
        }
        return inventario;
    }

    public void adicionarAoInventario(int idObjeto) throws  SQLException {
        String sql = "INSERT INTO inventario (idInventarioObj) VALUES (?)";

        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idObjeto);
            ps.executeUpdate();
        }

    }
}
