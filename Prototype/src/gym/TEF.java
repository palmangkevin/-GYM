package gym;

import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui sert à stocker les informations concernant les montants à payer à chaque professionnel et qui
 * peut sauvegarder un fichier TEF contenant ces informations dans le chemin désiré.
 */
public class TEF {

	private HashMap<Pro, Double> amounts;

	/**
	 * Constructeur de classe
	 *
	 * @param amounts un dictionnaire associant un montant à payer à un professionnel
	 */
	public TEF(HashMap<Pro, Double> amounts) {
		this.amounts = amounts;
	}

	/**
	 * Méthode pour configuer un fichier TEF dans le format .txt et le sauvegarder dans le chemin passé en paramètre
	 * @param strPath chemin dans lequel le fichier sera sauvegardé.
	 */
	void save(String strPath) {

		DateFormat todayFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = todayFormat.format(new Date());

		String fileName = "TEF " + today + ".txt";

		try {
			strPath = Paths.get(strPath).resolve(fileName).toString();

			FileWriter fw = new FileWriter(strPath);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("Fichier TEF");
			bw.newLine();
			bw.newLine();

			for (Map.Entry<Pro, Double> entry : amounts.entrySet()) {
				Pro pro = entry.getKey();
				Double amount = entry.getValue();

				bw.write("Nom = " + pro.getName());
				bw.newLine();
				bw.write("ID = " + pro.getId());
				bw.newLine();
				bw.write("Montant = " + amount);
				bw.newLine();
				bw.newLine();
			}

			bw.flush();
			fw.close();
			bw.close();

			System.out.println("Fichier TEF créé : " + fileName);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
