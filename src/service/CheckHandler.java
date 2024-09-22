package service;

import model.Objeto;
import repository.ObjetoDAO;

public class CheckHandler {

    public static String processarCheck(String comandoUser, int cenaAtualId) {
        String[] partes = comandoUser.split(" ", 2);
        String check = partes[0];
        String argumento = partes.length > 1 ? partes[1] : "";

        try {
            if ("check".equalsIgnoreCase(check)) {
                if (argumento.isEmpty()) {
                    return "Opa amigo, você precisa digitar o nome de um objeto.";
                }

                Objeto objeto = ObjetoDAO.findObjetoByNome(argumento);
                if (objeto != null) {
                    Integer idCenaObjeto = objeto.getIdCenaObjeto();
                    if (idCenaObjeto != null && idCenaObjeto.equals(cenaAtualId)) {
                        return objeto.getDescricaoCheck();
                    } else {
                        return "Opa amigo, esse objeto não está na cena atual.";
                    }
                } else {
                    return "Opa amigo, esse objeto não existe.";
                }
            } else {
                return "Comando não reconhecido. Tente novamente.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao processar o comando.";
        }
    }
}
