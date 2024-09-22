package service;

import model.Comandos;
import model.Objeto;
import repository.ComandosDAO;
import repository.InventarioDAO;
import repository.ObjetoDAO;
import controller.GameController;

import java.sql.SQLException;

public class GetHandler {

    public static String processarGet(String comandoUser, GameController gameController) throws SQLException {
        int cenaAtualId = gameController.getCenaAtualId();
        int sequenciaAtual = gameController.getSequenciaAtual();

        Comandos comandos = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);

        String[] partes = comandoUser.split(" ");
        if (partes.length > 1) {
            String nomeObjeto = partes[1];

            Objeto objeto = ObjetoDAO.findObjetoByNome(nomeObjeto);
            if (objeto == null) {
                return "Esse objeto não existe.";
            }

            if (InventarioDAO.isObjetoNoInventario(objeto.getIdObjeto())) {
                return "Objeto já foi adicionado ao inventário.";
            }

            if (objeto.getInventarioBool().equals(0)) {
                return "Como que você vai adicionar este objeto em um inventário?????????";
            }

            InventarioDAO.adicionarAoInventario(objeto.getIdObjeto());

            Comandos comandoGet = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);
            if (comandoGet != null && comandoGet.getSequencia() == sequenciaAtual) {
                gameController.incrementarSequencia();
            }

            return comandos.getResultadoPositivo();
        }
        return "Comando inválido. Tente 'get [objeto]'.";
    }
}
