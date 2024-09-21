import controller.GameController;
import model.Cena;
import repository.CenaDAO;
import java.sql.SQLException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // Exibe a mensagem inicial e espera pelo comando START
            System.out.println("Digite START para iniciar o jogo");
            String comandoInicial;
            do {
                comandoInicial = scanner.nextLine().trim();
                if (!comandoInicial.equalsIgnoreCase("START")) {
                    System.out.println("Comando inválido. Digite START para iniciar o jogo.");
                }
            } while (!comandoInicial.equalsIgnoreCase("START")); // Só prossegue quando o comando for "START"

            int cenaInicialId = 1; //define o id da cena inicial como 1
            GameController gameController = new GameController(cenaInicialId); //cria instância do game controller passando o id 1

            Cena cenaAtual = CenaDAO.findCenaById(gameController.getCenaAtualId()); // pega a cena atual usando o id da cena do gameController
            if (cenaAtual != null) { //se a cena não for nula exibe a descrição da cena atual
                System.out.println(cenaAtual.getDescricao());
            }

            while (true) {
                String comandoUser = scanner.nextLine().trim(); //atribuindo comando do usuário
                String resultado = gameController.processarComando(comandoUser); //carrega a função processarComando

                System.out.println(resultado); //exibe resultado

                if(comandoUser.equalsIgnoreCase("QUIT")) { //condicional para encerrar o jogo
                    break;
                };
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
