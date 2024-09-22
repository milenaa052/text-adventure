package service;

import model.Objeto;
import repository.InventarioDAO;

import java.sql.SQLException;
import java.util.List;

public class ListarInventarioHandler {
    public static String listarInventario() throws SQLException {
        List<Objeto> objetos = InventarioDAO.listarInventario();
        if (objetos.isEmpty()) {
            return "O inventário está vazio.";
        }

        StringBuilder inventarioStr = new StringBuilder("Itens no inventário:\n");
        for (Objeto objeto : objetos) {
            inventarioStr.append("- ").append(objeto.getNomeObjeto()).append("\n");
        }
        return inventarioStr.toString();
    }
}
