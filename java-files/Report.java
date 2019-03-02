package gym;

import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Classe pour stocker les informations d'un rapport. Elle implémente un dictionnaire contenant des éléments de rapport
 * associés à des professionnels. Elle comprend aussi une méthode pour sauvegarder un rapport sur le disque dans le
 * format .txt.
 */
public class Report {

	private HashMap<Pro, ReportElement> reports;

	public Report(HashMap<Pro, ReportElement> reports) {
		this.reports = reports;
	}

	void save(String strPath) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Date today = new Date();

		Date sevenDayBefore = new Date();
		sevenDayBefore.setTime(sevenDayBefore.getTime() - 7 * 1000 * 60 * 60 * 24);

		String todayStr = dateFormat.format(today);
		String sevenDayBeforeStr = dateFormat.format(sevenDayBefore);

		String fileName = "Rapport - " + todayStr + ".txt";

		try {
			strPath = Paths.get(strPath).resolve(fileName).toString();

			FileWriter fw = new FileWriter(strPath);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("Rapport de synthèse de la semaine du " + sevenDayBeforeStr + " au " + todayStr);

			bw.newLine();
			bw.newLine();

			Double totalAmount = 0.0;
			int totalServices = 0;

			for (Map.Entry<Pro, ReportElement> entry : reports.entrySet()) {
				Pro pro = entry.getKey();
				ReportElement el = entry.getValue();

				Double amount = el.getAmount();
				int servicesCount = el.getServicesCount();

				bw.write(pro.getName());
				bw.newLine();
				bw.write(pro.getId());
				bw.newLine();
				bw.write(el.getServicesCount() + " services");
				bw.newLine();
				bw.write("Frais de la semaine : " + amount);

				totalAmount += amount;
				totalServices += servicesCount;

				bw.newLine();
				bw.newLine();
			}

			bw.write("______________________");
			bw.newLine();
			bw.newLine();
			bw.write("Professionnels qui ont fourni des services : " + reports.size());
			bw.newLine();
			bw.write("Total des frais de la semaine : " + totalAmount);
			bw.newLine();
			bw.write("Total des services de la semaine : " + totalServices);

			bw.flush();
			fw.close();
			bw.close();

			System.out.println("Rapport créé");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
