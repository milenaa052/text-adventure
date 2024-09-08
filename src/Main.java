import controller.GameController;
import model.Cena;
import repository.CenaDAO;
import java.sql.SQLException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            int cenaInicialId = 1; //define o id da cena inicial como 1
            GameController gameController = new GameController(cenaInicialId); //cria instância do game controller passando o id 1
            Scanner scanner = new Scanner(System.in);

            Cena cenaAtual = CenaDAO.findCenaById(gameController.getCenaAtualId()); // pega a cena atual usando o id da cena do gameController
            if (cenaAtual != null) { //se a cena não for nula exibe a descrição da cena atual
                System.out.println(cenaAtual.getDescricao());
            }

            while (true) {
                String comandoUser = scanner.nextLine().trim(); //atribuindo comando do usuário
                String resultado = gameController.processarComando(comandoUser); //carrega a função processarComando

                System.out.println(resultado); //exibe resultado

                if(comandoUser.equalsIgnoreCase("QUIT")) { //condicional para encerrar o jogo
                    System.out.println("Saindo do jogo...");
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
