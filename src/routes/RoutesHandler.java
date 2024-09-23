package routes;

import com.google.gson.Gson;
import controller.GameController;
import model.Cena;
import repository.CenaDAO;
import service.HelpHandler;
import spark.Spark;

public class RoutesHandler {
    private static Gson gson = new Gson();

    public static void configurarRotas() {

        Spark.port(4567);

        Spark.get("/", (req, res) -> "Servidor do jogo online");

        Spark.get("/start", (req, res) -> {
            int cenaInicialId = 1;
            Cena cena = CenaDAO.findCenaById(cenaInicialId);
            if (cena != null) {
                res.type("application/json");
                return gson.toJson(cena);
            } else {
                res.status(404);
                return "Cena inicial não encontrada.";
            }
        });

        Spark.get("/:comando", new GameController(1));

        Spark.get("/help", (request, response) -> {
            String comando = "HELP";
            return HelpHandler.processarHelp(comando);
        });

    }
}