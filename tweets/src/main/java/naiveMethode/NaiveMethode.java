package naiveMethode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import annalyseExperimentale.CrossValidation;


public class NaiveMethode {
	private Integer nb = 10;
	private Integer curseur = -1;


	String[] positives; 
	String[] negatives; 

	public NaiveMethode(String[] positives, String[] negatives) {
		this.positives = positives;
		this.negatives = negatives;
	}

	private static int getNbOccurences(String[] tableau, String texte) {
		int nb = 0;
		for (int i = 0; i < tableau.length; i++) {
			Pattern pattern = Pattern.compile (tableau[i]);
			Matcher matcher = pattern.matcher(texte);
			if (matcher.find())
				nb++;
		}
		return nb;
	}

	public int getAnnotation(String status){

		int nbGoodWords = getNbOccurences(positives, status);
		int nbBadWords = getNbOccurences(negatives, status);

		if (nbGoodWords > nbBadWords) {
			return 4;
		}
		else if (nbGoodWords < nbBadWords) {
			return 0;
		}
		else {
			return 2;
		}
	}

	public  double classifier() throws IOException{
		double  nb_Faux =0;
		double taux =0;
		CrossValidation crossValidation = new CrossValidation();
		List<List<List<String>>> bdd = crossValidation.splitKTweetList(nb);
		int tailleDataTest = bdd.get(0).size();
		List<List<String>> dataTest = new ArrayList<>();
		for (curseur = 0; curseur < nb; curseur++) {
			dataTest = bdd.get(curseur);
			for (int n = 0; n < tailleDataTest; n++) {
				int annotation = Integer.parseInt(dataTest.get(n).get(1));
				int classifierAnnotation = getAnnotation(dataTest.get(n).get(0));
				if(annotation !=classifierAnnotation){
					nb_Faux++;
				}
			}
			taux +=  nb_Faux / tailleDataTest;
			nb_Faux = 0;
		}
		curseur = -1;
		return taux / nb;
	}

}	
