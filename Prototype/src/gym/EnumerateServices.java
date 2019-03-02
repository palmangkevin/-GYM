package gym;

import java.util.Scanner;

/**
 * Classe qui énumère les services offerts.
 * Implémente l'interface Action
 */
public class EnumerateServices implements Action {

    @Override
    public String getTitle() {
        return "Afficher les codes de service";
    }

    @Override
    public void launch() {
        Scanner in = new Scanner(System.in);

        for (Service service : DataCenter.database.getServices())
            System.out.println(service.getTitle() + " - " + service.getId());
    }
}
