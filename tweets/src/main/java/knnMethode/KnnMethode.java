package knnMethode;

import java.util.List;

import annalyseExperimentale.CrossValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class KnnMethode {

	private Integer nb = 10;
	private Integer curseur = -1;
	public KnnMethode() {}

	

	public int sizeTweet(String tweet) {
		String[] tableau;
		tableau = tweet.split(" ");
		return tableau.length;
	}



	public int comparaisonTweet(String tweet1, String tweet2) {
		int cpt=0;
		List<String> listeOfSecondTweet = Arrays.asList(tweet2.split(" "));
		for (String word : tweet1.split(" ")) {
			if(listeOfSecondTweet.contains(word))
				cpt++; }
		return cpt;
	}


	public float distanceTweet(String tweet1, String tweet2) {
		float size1 = sizeTweet(tweet1);
		return ((size1 - comparaisonTweet(tweet1, tweet2)) / sizeTweet(tweet1));
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





	public List<String> plusProcheVoisin(String tweet, int k) throws IOException  {

		List<String> plusProcheVoisin = new ArrayList<>(k);
		List<Float> distanceTable = new ArrayList<Float>(k);
		List<String> annotation = new ArrayList<String>(k);
		List<List<String>> baseTweets =  readData();
		String tweet1="";
		String note = "";
		float dist;
		int i;
		int j;


		// Meter les k premiers tweets dans plusProcheVoisin
		for (i = 0; i < k; i++) {

			tweet1 = baseTweets.get(i).get(0);
			plusProcheVoisin.add(tweet1);

			note = baseTweets.get(i).get(1);
			annotation.add(note);

			dist = distanceTweet(tweet,tweet1);
			distanceTable.add(dist);

		}


		// chercher le max de distanceTable
		Float distanceMax = distanceTable.get(0);
		int indiceMax = 0;
		// on parcourt distanceTable
		for (j = 1; j < k; j++) {
			if (distanceTable.get(j) > distanceMax) {
				distanceMax = distanceTable.get(j);
				indiceMax = j;
			}
		}

		// On parcourt le reste de tweetBase
		for (i = k; i < baseTweets.size(); i++) {
			tweet1 = baseTweets.get(i).get(0);

			// comparer cette distance avec la distance entre le tweet et le tweet de la table traité
			if (distanceMax > distanceTweet(tweet,tweet1)) {
				// on echange
				plusProcheVoisin.set(indiceMax, tweet1);

				note = baseTweets.get(i).get(1);
				annotation.set(indiceMax,note);

				dist = distanceTweet(tweet,tweet1);
				distanceTable.set(indiceMax, dist);
				distanceMax = dist;
				//indiceMax = i;

				for (j = 0; j < k; j++) {
					if (distanceTable.get(j) > distanceMax) {
						distanceMax = distanceTable.get(j);
						indiceMax = j;
					}
				}


			}
		}

		return annotation	;
	}	



	public int get_annotation(String tweet, int k) throws IOException {
		List<String> annotation = plusProcheVoisin(tweet, k) ;
		int cpt0=0;
		int cpt2=0;
		int cpt4=0;
		int res;
		for (int i=0; i < k; i++) {
			res = Integer.parseInt(annotation.get(i));

			if (res == 0) {
				cpt0 += 1;
			}
			else if (res == 2) {
				cpt2 += 1;
			}
			else if (res == 4) {
				cpt4 += 1;
			}

		}
		if (cpt0 > cpt2 &&  cpt0 > cpt4) {
			return 0;
		}
		else if (cpt2 > cpt0 &&  cpt2 > cpt4) {
			return 2;
		}
		else if (cpt4 > cpt0 &&  cpt4 > cpt2) {
			return 4;
		}

		else if (cpt4 == cpt0) {
			return 2;
		}

		else if (cpt0 == cpt2 &&  cpt0 > cpt4) {
			return 0;
		}
		else if (cpt4 == cpt2 &&  cpt4 > cpt0) {
			return 4;
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
				int classifierAnnotation = get_annotation(dataTest.get(n).get(0), 3);
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


	

