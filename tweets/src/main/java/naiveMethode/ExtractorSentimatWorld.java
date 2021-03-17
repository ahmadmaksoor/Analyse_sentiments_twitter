package naiveMethode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ExtractorSentimatWorld {
	public static String[] getListeMots(String fichier, String langue) {
		ArrayList<String> lignes = new ArrayList<String>();

		try{
			InputStream flux = new FileInputStream(fichier); 
			InputStreamReader r = new InputStreamReader(flux);
			BufferedReader bf = new BufferedReader(r);
			String ligne;
			while ((ligne=bf.readLine())!=null){
				lignes.add(ligne);
			}
			bf.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}	

		String motsSelectionne;
			motsSelectionne = lignes.get(0);
		
		String[] tabMots;

		tabMots = motsSelectionne.split(",");

		// Suppression des espaces avant et après
		for (int i = 0; i < tabMots.length; i++) {
			tabMots[i] = tabMots[i].trim();
		}

		return tabMots;
	}

}
