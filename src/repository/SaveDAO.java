package repository;

import config.MySql;
import model.Save;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveDAO {
    public static Save findSaveById(Integer id) throws SQLException {
        Connection conn = MySql.getConnection();
        String sql = "SELECT * FROM save WHERE idSave = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Save save = null;

        if (rs.next()) {
            save = new Save(
                    rs.getInt("idSave"),
                    rs.getInt("idSaveCenaAtual"),
                    rs.getString("data")
            );
        }
        return save;
    }
}
