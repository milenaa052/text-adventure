package repository;

import config.MySql;
import model.Inventario;
import model.Objeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    public static Inventario findInventarioById(Integer id) throws SQLException {
        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM inventario WHERE idInventario = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Inventario inventario = null;

        if (rs.next()) {
            inventario = new Inventario(
                    rs.getInt("idInventario"),
                    rs.getInt("idInventarioObj")
            );
        }
        return inventario;
    }

    // Verifica se o objeto já está no inventário
    public static boolean isObjetoNoInventario(int idObjeto) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inventario WHERE idInventarioObj = ?";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObjeto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o objeto já estiver no inventário
            }
        }
        return false;
    }

    // Adiciona o objeto ao inventário
    public static void adicionarAoInventario(int idObjeto) throws SQLException {
        String sql = "INSERT INTO inventario (idInventarioObj) VALUES (?)";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObjeto);
            ps.executeUpdate();
        }
    }

    // Lista todos os objetos no inventário
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

    // Limpa o inventário
    public static void limparInventario() throws SQLException {
        String sql = "DELETE FROM inventario";
        try (Connection conn = MySql.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
