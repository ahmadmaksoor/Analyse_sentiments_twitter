package bayesMethode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import annalyseExperimentale.CrossValidation;

public class BayesMethode {


	private Integer nb = 10;
	private Integer curseur = -1;


	public boolean appartenance(String tweet, String mot) {
		tweet = tweet.toLowerCase ();
		mot = mot.toLowerCase ();
		List<String> listeOfSecondTweet = Arrays.asList(mot.split(" "));
		for (String word : tweet.split(" ")) {
			if(listeOfSecondTweet.contains(word))
				return true; 
		}
		return false;
	}

	public boolean appartenance_mot_important(String tweet, String mot) {
		tweet = tweet.toLowerCase ();
		mot = mot.toLowerCase ();
		List<String> listeOfSecondTweet = Arrays.asList(mot.split(" "));
		for (String word : tweet.split(" ")) {
			if(listeOfSecondTweet.contains(word) & word.length() > 3)
				return true; 
		}
		return false;
	}



	public List<List<String>> readData() throws IOException { 
		if (curseur == -1) {
			CrossValidation cv = new CrossValidation();
			return cv.readData();
		}
		else { 
			CrossValidation crossValidation = new CrossValidation();
			List<List<List<String>>> bdd = crossValidation.splitKTweetList(nb);
			List<List<String>> bdd2 = new ArrayList<>();
			for (int i = 0; i < nb; i++) {
				if (i != curseur) {
					for (int n = 0; n < bdd.get(i).size(); n++)
						bdd2.add(bdd.get(i).get(n));
				}
			}
			return bdd2;
		}
	}


	public List<Integer> comptage_annotation() throws IOException {
		List<List<String>> file = readData();
		List<Integer> res = new ArrayList<>();
		int cpt0=0;
		int cpt2=0;
		int cpt4=0;

		for(int i=0; i < file.size(); i++) {
			int r = Integer.parseInt(file.get(i).get(1));

			if (r == 0){
				cpt0 ++;
			}
			else if (r == 2){
				cpt2 ++;
			}
			else if (r == 4){
				cpt4 ++;
			}
		}
		res.add(cpt0);
		res.add(cpt2);
		res.add(cpt4);
		return res;
	}

	public List<Float> comptage_annotation_mot(String mot) throws IOException{

		List<List<String>> file = readData();
		List<Float> res = new ArrayList<>();
		float cpt0=0;
		float cpt2=0;
		float cpt4=0;


		for(int i=0; i < file.size(); i++) {
			if (appartenance(file.get(i).get(0), mot) == true) {
				float r = Float.parseFloat(file.get(i).get(1));

				if (r == 0){
					cpt0 ++;
				}
				else if (r == 2){
					cpt2 ++;
				}
				else if (r == 4){
					cpt4 ++;
				}
			}
		}
		res.add(cpt0);
		res.add(cpt2);
		res.add(cpt4);
		return res;
	}



}
