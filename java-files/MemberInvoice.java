package gym;

import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Classe qui sert à stocker les informations d'une facture membre et qui
 * peut sauvegarder la facture lors d'un appel à save
 */
public class MemberInvoice {

	private String memberName;
	private List<String> infos;

	/**
	 * Constructeur de classe
	 *
	 * @param infos (Infos membre, Infos pour chaque service)
	 */
	public MemberInvoice(String memberName, List<String> infos) {
		this.memberName = memberName;
		this.infos = infos;
	}

	/**
	 * Méthode pour sauvegarder la facture membre
	 * @param strPath chemin dans lequel le fichier sera sauvegardé.
	 */
	void save(String strPath) {
		DateFormat todayFormat = new SimpleDateFormat("dd-MM-yyyy");
		String today = todayFormat.format(new Date());

		String fileName = memberName + " - " + today + ".txt";

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

			System.out.println("Fichier facture membre créé : " + fileName);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
