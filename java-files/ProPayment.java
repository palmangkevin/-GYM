package gym;

import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Classe qui sert à stocker les informations d'un avis de paiement de professionnel et qui
 * peut sauvegarder l'avis de paiement lors d'un appel à save
 */
public class ProPayment {

	private String proName;
	private List<String> infos;

	/**
	 * Constructeur de classe
	 *
	 * @param infos Infos de l'avis de paiement
	 */
	public ProPayment(String proName, List<String> infos) {
		this.proName = proName;
		this.infos = infos;
	}

	/**
	 * Méthode pour sauvegarder l'avis de paiement du pro
	 * @param strPath chemin dans lequel le fichier sera sauvegardé.
	 */
	void save(String strPath) {
		DateFormat todayFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = todayFormat.format(new Date());

		String fileName = proName + " - " + today + ".txt";

		try {
			strPath = Paths.get(strPath).resolve(fileName).toString();

			FileWriter fw = new FileWriter(strPath);
			BufferedWriter bw = new BufferedWriter(fw);

			for (String info : infos) {
				bw.write(info);
				bw.newLine();
			}

			bw.flush();
			fw.close();
			bw.close();

			System.out.println("Fichier Avis de paiement créé : " + fileName);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
