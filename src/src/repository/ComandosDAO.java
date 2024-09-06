package repository;

import config.MySql;
import model.Comandos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComandosDAO {
    public static Comandos findComandosById(Integer id) throws SQLException {

        Connection conn = MySql.getConnection();
        String sql = "SELECT resultadoPositivo FROM comandos WHERE idComando = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Comandos comandos = new Comandos();

        if (rs.next()) {
            comandos.setResultadoPositivo(rs.getString("resultadoPositivo"));
        }
        return comandos;
    }

    public static Comandos findComandosByName(String comandoName) throws SQLException {

        Connection conn = MySql.getConnection();
        String sql = "SELECT resultadoPositivo FROM comandos WHERE comando = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, comandoName);

        ResultSet rs = ps.executeQuery();
        Comandos comandos = new Comandos();

        if (rs.next()) {
            comandos.setResultadoPositivo(rs.getString("resultadoPositivo"));
        }
        return comandos;
    }

    public static Comandos findComandosByNameAndCena(String comandoName, Integer cenaAtualId) throws SQLException {

        Connection conn = MySql.getConnection();
        String sql = "SELECT resultadoPositivo, idCenaDestino FROM comandos WHERE comando = ? AND idCenaAtual = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, comandoName);
        ps.setInt(2, cenaAtualId);

        ResultSet rs = ps.executeQuery();
        Comandos comandos = new Comandos();

        if (rs.next()) {
            comandos.setResultadoPositivo(rs.getString("resultadoPositivo"));
            comandos.setIdCenaDestino(rs.getInt("idCenaDestino"));
        }
        return comandos;
    }
}
