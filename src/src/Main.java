import controller.GameController;
import model.Cena;
import model.Comandos;
import model.Objeto;
import repository.CenaDAO;
import repository.ComandosDAO;
import repository.ObjetoDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Cena cena = CenaDAO.findCenaById(1);
            System.out.println(cena.getDescricao());

            GameController gameController = new GameController();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String comandoUser = scanner.nextLine().trim();
                String resultado = gameController.processarComando(comandoUser);

                System.out.println(resultado);

                if(comandoUser.equalsIgnoreCase("QUIT")) {
                    System.out.println("Saindo do jogo...");
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
