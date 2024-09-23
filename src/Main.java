import controller.GameController;
import model.Cena;
import repository.CenaDAO;
import routes.RoutesHandler;
import java.sql.SQLException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            RoutesHandler.configurarRotas();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite START para iniciar o jogo");
            String comandoInicial;

            do {
                comandoInicial = scanner.nextLine().trim();
                if (!comandoInicial.equalsIgnoreCase("START")) {
                    System.out.println("Comando inválido. Digite START para iniciar o jogo.");
                }
            } while (!comandoInicial.equalsIgnoreCase("START"));

            int cenaInicialId = 1;
            GameController gameController = new GameController(cenaInicialId);
            Cena cenaAtual = CenaDAO.findCenaById(gameController.getCenaAtualId());

            if (cenaAtual != null) {
                System.out.println(cenaAtual.getDescricao());
            }

            while (true) {
                String comandoUser = scanner.nextLine().trim();
                String resultado = gameController.processarComando(comandoUser);

                System.out.println(resultado);

                if(comandoUser.equalsIgnoreCase("QUIT")) {
                    break;
                };
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
