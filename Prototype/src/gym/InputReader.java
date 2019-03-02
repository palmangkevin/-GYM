package gym;

import java.nio.file.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe statique gérant les limitations et les formats des données entrées dans la console par l'utilisateur
 */
public class InputReader {


	/**
	 * Obtient un int valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return int
	 */
	static int askIntLoop(Scanner in) {
		int select = -1;

		while(true) {
			String strInput = in.nextLine();

			try {
				select = Integer.parseInt(strInput);
			} catch(Exception e) { }

			if(select >= 0)
				return select;

			System.out.println("Erreur");
			System.out.println("Veuillez entrer un nombre superieur ou egale à 0");
		}
	}

	/**
	 * Obtient un int valide, en dessous de la borne max
	 *
	 * @param in le Scanner
	 * @param excludedMax la borne max
	 * @return int
	 */
	static int askIntLoop(Scanner in, int excludedMax) {
		int select = -1;

		while(true) {
			String strInput = in.nextLine();

			try {
				select = Integer.parseInt(strInput);
			} catch(Exception e) { }

			if(select >= 0 && select < excludedMax)
				return select;

			System.out.println("Erreur");
			System.out.println("Veuillez entrer un nombre compris entre 0 et " + (excludedMax - 1));
		}
	}


	/**
	 * Obtient un Double valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return Double
	 */
	static Double askDoubleLoop(Scanner in) {
		Double input = -1.0;

		while(true) {
			String strInput = in.nextLine();

			try {
				input = Double.parseDouble(strInput);
			} catch(Exception e) { }

			if(input >= 0)
				return input;

			System.out.println("Erreur");
			System.out.println("Veuillez entrer un nombre superieur ou egale à 0.0");
		}
	}

	/**
	 * Obtient un Double valide, en dessous ou égal à la borne max
	 *
	 * @param in le Scanner
	 * @param includedMax la borne max
	 * @return Double
	 */
	static Double askDoubleLoop(Scanner in, int includedMax) {
		Double input = -1.0;

		while(true) {
			String strInput = in.nextLine();

			try {
				input = Double.parseDouble(strInput);
			} catch(Exception e) { }

			if(input >= 0 && input <= includedMax)
				return input;

			System.out.println("Erreur");
			System.out.println("Veuillez entrer un nombre compris entre 0.0 et " + new DecimalFormat("0.0").format(includedMax));
		}
	}

	/**
	 * Obtient un String qui ne dépasse pas la limite
	 *
	 * @param in le Scanner
	 * @param includedMax limite max du length
	 * @return String
	 */
	static String askStringUnderLimitLoop(Scanner in, int includedMax) {
		while(true) {
			String strInput = in.nextLine();

			if(strInput.length() <= includedMax)
				return strInput;

			System.out.println("Erreur");
			System.out.println("La chaine de caractère ne doit pas dépasser " + includedMax + " caractères");
		}
	}


	/**
	 * Obtient un genre valide (M ou F) de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return String
	 */
	static String askGenderLoop(Scanner in) {
		while(true) {
			String strInput = in.nextLine().toUpperCase();

			if(strInput.equals("M") || strInput.equals("F") || strInput.equals("AUTRE"))
				return strInput;

			System.out.println("Erreur");
			System.out.println("Répondez seulement par 'M', 'F' ou 'AUTRE'");
		}
	}

	/**
	 * Obtient un Date valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @param strFormat le format de Date
	 * @return Date
	 */
	static Date askDateLoop(Scanner in, String strFormat) {
		DateFormat format = new SimpleDateFormat(strFormat, Locale.ENGLISH);

		Date date = null;

		while(true) {
			String strInput = in.nextLine();

			try {
				date = format.parse(strInput);
			} catch(Exception e) { }

			if(date != null)
				return date;

			System.out.println("Erreur");
			System.out.println("Veuillez respecter ce format " + strFormat);
		}
	}

	/**
	 * Obtient (Oui ou Non) de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return boolean (true si oui, false si non)
	 */
	static boolean askYesNoLoop(Scanner in) {
		while(true) {
			String strInput = in.nextLine().toLowerCase();

			boolean isOui = strInput.equals("oui");
			boolean isNon = strInput.equals("non");

			if(isOui || isNon)
				return isOui;

			System.out.println("Erreur");
			System.out.println("Répondez seulement par 'Oui' ou par 'Non'");
		}
	}

	/**
	 * Obtient un ID de membre valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return String l'ID valide tapé
	 */
	static String askExistingMemberIDLoop(Scanner in) {
		while(true) {
			String strInput = in.nextLine();

			if(DataCenter.database.getMemberFromId(strInput) != null)
				return strInput;

			System.out.println("Erreur");
			System.out.println("L'ID entré n'est pas valide, ou alors le membre n'existe pas");
		}
	}

	/**
	 * Obtient un ID de pro valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return String l'ID valide tapé
	 */
	static String askExistingProIDLoop(Scanner in) {
		while(true) {
			String strInput = in.nextLine();

			if(DataCenter.database.getProFromId(strInput) != null)
				return strInput;

			System.out.println("Erreur");
			System.out.println("L'ID entré n'est pas valide, ou alors le professionnel n'existe pas");
		}
	}

	/**
	 * Obtient un Member valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return Member
	 */
	static Member askExistingMemberLoop(Scanner in) {
		String id = askExistingMemberIDLoop(in);

		return DataCenter.database.getMemberFromId(id);
	}

	/**
	 * Obtient un Pro valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return Pro
	 */
	static Pro askExistingProLoop(Scanner in) {
		String id = askExistingProIDLoop(in);

		return DataCenter.database.getProFromId(id);
	}

	/**
	 * Obtient un ID de service valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return String l'ID de service
	 */
	static String askExistingServiceIDLoop(Scanner in) {
		while(true) {
			String strInput = in.nextLine();

			if(DataCenter.database.getServiceFromId(strInput) != null)
				return strInput;

			System.out.println("Erreur");
			System.out.println("Le code de service entré n'est pas valide");
		}
	}

	/**
	 * Obtient un Service valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return Service
	 */
	static Service askExistingServiceLoop(Scanner in) {
		String id = askExistingServiceIDLoop(in);

		return DataCenter.database.getServiceFromId(id);
	}

	/**
	 * Obtient une liste de Day de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return ArrayList<Session.Day>
	 */
	static ArrayList<Session.Day> askDaysLoop(Scanner in) {
		ArrayList<Session.Day> days = new ArrayList();

		while(true) {
			String input = in.nextLine().toUpperCase();

			if(input.equals("STOP"))
				break;

			Session.Day day = null;

			try {
				day = Session.Day.valueOf(input);
			} catch(Exception e) { }

			if(day == null || days.contains(day)) {
				System.out.println("Erreur");
				System.out.println("Le jour entré n'est pas correct, ou a déjà été entré");

				continue;
			}

			days.add(day);
		}

		return days;
	}

	/**
	 * Obtient un path valide de la part de l'utilisateur
	 *
	 * @param in le Scanner
	 * @return String le path
	 */
	static String askDirectoryLoop(Scanner in) {
		System.out.println("Exemple :");
		System.out.println("   /Users/" + System.getProperty("user.name") + "/Desktop (sous Mac)");
		System.out.println("   C:\\Users\\" + System.getProperty("user.name") + "\\Desktop (sous Windows)");

		while(true) {
			String strInput = in.nextLine();

			if(Files.isDirectory(Paths.get(strInput)))
				return strInput;

			System.out.println("Erreur");
			System.out.println("Veuillez entrer un chemin valide (qui pointe vers un dossier)");
		}
	}
}
