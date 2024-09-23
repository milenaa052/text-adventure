package controller;

import model.Comandos;
import repository.*;
import service.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

public class GameController implements Route {
    private int cenaAtualId;
    private int sequenciaAtual;

    public GameController(int cenaInicialId) {
        this.cenaAtualId = cenaInicialId;
        this.sequenciaAtual = 1;
    }

    public int getCenaAtualId() {
        return cenaAtualId;
    }

    public void setCenaAtualId(int cenaAtualId) {
        this.cenaAtualId = cenaAtualId;
    }

    public int getSequenciaAtual() {
        return sequenciaAtual;
    }

    public void incrementarSequencia() {
        this.sequenciaAtual++;
    }

    public void resetarSequencia() {
        this.sequenciaAtual = 1;
    }

    public String processarComando(String comandoUser) {
        try {
            if (comandoUser.startsWith("get ")) {
                return GetHandler.processarGet(comandoUser, this);
            }

            if (comandoUser.equalsIgnoreCase("INVENTORY")) {
                return ListarInventarioHandler.listarInventario();
            }

            if (comandoUser.equalsIgnoreCase("HELP")) {
                return HelpHandler.processarHelp(comandoUser);
            }

            if(comandoUser.startsWith("save ")) {
                return SaveHandler.processsarSave(comandoUser, cenaAtualId);
            }

            if (comandoUser.startsWith("load ")) {
                return LoadHandler.processsarLoad(comandoUser, this);
            }

            if (comandoUser.equalsIgnoreCase("LOAD")) {
                return ListarLoadHandler.listarLoad(comandoUser);
            }

            if (comandoUser.equalsIgnoreCase("RESTART")) {
                return RestartHandler.processsarRestart(comandoUser, this);
            }

            if (comandoUser.equalsIgnoreCase("QUIT")) {
                InventarioDAO.limparInventario();
                SaveDAO.limparSave();
                return "Saindo do jogo...";
            }

            Comandos comandos = ComandosDAO.findComandosByNameAndCena(comandoUser, cenaAtualId);

            if (comandos != null && comandos.getResultadoPositivo() != null) {
                return UseHandler.processarUse(comandoUser, this);
            } else {
                return CheckHandler.processarCheck(comandoUser, cenaAtualId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Ocorreu um erro ao processar o comando.";
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String comandoBruto = request.params(":comando");
        String resultado = processarComando(comandoBruto);

        response.type("text/plain");
        return resultado;
    }
}
