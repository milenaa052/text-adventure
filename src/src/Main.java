import controller.GameController;
import model.Cena;
import repository.CenaDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Inicialize a cena inicial (por exemplo, cena com ID 1)
            Cena cena = CenaDAO.findCenaById(1);
            System.out.println(cena.getDescricao());

            // Crie um controlador de jogo com a cena inicial
            GameController gameController = new GameController(1); // Cena inicial ID 1
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String comandoUser = scanner.nextLine().trim();
                String resultado = gameController.processarComando(comandoUser);

                System.out.println(resultado);

                if (comandoUser.equalsIgnoreCase("QUIT")) {
                    System.out.println("Saindo do jogo...");
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
