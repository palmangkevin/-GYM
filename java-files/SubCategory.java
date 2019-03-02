package gym;

import java.util.List;
import java.util.Scanner;

/**
 * Classe pour créer une sous-catégories d'actions. La sous-catégorie porte un nom et une liste d'actions qui peuvent
 * être lancées.
 * Implémente l'interface Action
 */
public class SubCategory implements Action {

	String name;

	List<Action> actions;

	/**
	 * Constructeur de classe
	 *
	 * @param name nom de la sous-catégorie
	 * @param actions liste d'actions qui peuvent être lancées
	 */
	public SubCategory(String name, List<Action> actions) {
		this.name = name;
		this.actions = actions;
	}

	@Override
	public String getTitle() { return name; }

	@Override
	public void launch() {
		Scanner in = new Scanner(System.in);

		System.out.println("- " + name + " -");
		System.out.println();

		//Afficher les choix
		for(int i = 0; i < actions.size(); i++)
			System.out.println(Integer.toString(i) + " - " + actions.get(i).getTitle());

		System.out.println(actions.size() + " - Annuler");

		//Selection
		int select = InputReader.askIntLoop(in, actions.size() + 1);

		//Retour au menu principal
		if(select == actions.size()) return;

		//On ouvre l'Action associée
		actions.get(select).launch();
	}
}
