package repository;

import config.MySql;
import model.Objeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    public static boolean isObjetoNoInventario(int idObjeto) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inventario WHERE idInventarioObj = ?";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObjeto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public static void adicionarAoInventario(int idObjeto) throws SQLException {
        String sql = "INSERT INTO inventario (idInventarioObj) VALUES (?)";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObjeto);
            ps.executeUpdate();
        }
    }

    public static List<Objeto> listarInventario() throws SQLException {
        List<Objeto> inventario = new ArrayList<>();
        String sql = "SELECT * FROM inventario i INNER JOIN objeto o ON i.idInventarioObj = o.idObjeto";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Objeto objeto = new Objeto(rs.getInt("idObjeto"), rs.getString("nomeObjeto"), rs.getString("descricaoCheck"), rs.getInt("inventarioBool"), rs.getInt("idCenaObjeto"));
                inventario.add(objeto);
            }
        }
        return inventario;
    }

    public static void limparInventario() throws SQLException {
        String sql = "DELETE FROM inventario";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
