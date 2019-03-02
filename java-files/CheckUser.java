package gym;

import java.util.Scanner;

/**
 * Classe pour vérifier le statut d'un membre. Elle demande le ID du membre puis donne une réponse sur son statut
 * Implémente l'interface Action
 */
public class CheckUser implements Action {

    @Override
    public String getTitle() {
        return "Vérifier un membre";
    }

    @Override
    public void launch() {
        Scanner in = new Scanner(System.in);

        System.out.println("- Vérification d'un membre -");
        System.out.println();

        System.out.println("Entrez son ID (numéro unique)");
        Member membre = InputReader.askExistingMemberLoop(in);

        System.out.println(membre.isEnabled() ? "Validé" : "Membre suspendu");
    }
}
