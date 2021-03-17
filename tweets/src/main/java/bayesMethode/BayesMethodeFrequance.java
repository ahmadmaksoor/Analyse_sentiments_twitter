package bayesMethode;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import annalyseExperimentale.CrossValidation;

public class BayesMethodeFrequance extends BayesMethode{
	private Integer nb = 10;
	private Integer curseur = -1;

	public BayesMethodeFrequance() {}







	public int frequance(String tweet, String mot) {
		int cpt = 0;
		tweet = tweet.toLowerCase ();
		mot = mot.toLowerCase ();
		if (appartenance(tweet,mot) == true) {
			for (String word : tweet.split(" ")) {
				if (mot.equals(word)) {
					cpt++;
				}
			}
		}
		return cpt;

	}




	public List<Float> bayes_mot(String mot) throws IOException {
		float var;
		List<Float> total_mot = comptage_annotation_mot(mot);
		List<Integer> total = comptage_annotation();

		List<Float> res = new ArrayList<>(); 
		for (int i=0; i<3; i ++) {
			var = total_mot.get(i)/total.get(i);
			res.add(var);

		}
		return res;
	}

	public List<String> bigrammes(String tweet) {
		String[] tweetList;
		List<String> tweetList1 = new ArrayList<>();
		tweetList = tweet.split(" ");

		for (int i = 0; i < tweetList.length - 1; i++) {
			String word = tweetList[i] + " " + tweetList[i+1] ; 
			tweetList1.add(word);			
		}
		return tweetList1;
	}


	public List<Float> bayes_tweet_bigramme(String tweet) throws IOException {
		int taille_base = comptage_annotation().get(0) + comptage_annotation().get(1) + comptage_annotation().get(2);
		List<Float> probabilité = new ArrayList<>();
		List<Float> proba0 = new ArrayList<>();
		List<Float> proba2 = new ArrayList<>();
		List<Float> proba4 = new ArrayList<>();
		float pro0 = 0;
		float pro2 = 0;
		float pro4 = 0;
		int bouton0 =0;
		int bouton2 =0;
		int bouton4 =0;
		String[] uni_gramme = tweet.split(" ");
		List<String> wordList = Arrays.asList(uni_gramme); 
		List<String> tweetList_uniAndBiGrammes = new ArrayList<>();
		tweetList_uniAndBiGrammes.addAll(bigrammes(tweet));
		tweetList_uniAndBiGrammes.addAll(wordList);


		List<Float> res = new ArrayList<>();

		for (String word : bigrammes(tweet)) {
			probabilité = bayes_mot(word);
			proba0.add(probabilité.get(0));
			proba2.add(probabilité.get(1));
			proba4.add(probabilité.get(2));

			pro0 = 1;
			pro2 = 1;
			pro4 = 1;

			for (int i = 0; i < proba0.size(); i++ ) {
				if (proba0.get(i) > 0){
					pro0 = (float) Math.pow((pro0 * proba0.get(i)), (frequance(tweet,word ))) ;
					bouton0 = 1;

				}

				if (proba2.get(i) > 0){
					pro2 = (float) Math.pow((pro2 * proba2.get(i)), (frequance(tweet,word ))) ;
					bouton2 = 1;

				}
				if (proba4.get(i) > 0){
					pro4 = (float) Math.pow((pro4 * proba4.get(i)), (frequance(tweet,word ))) ;
					bouton4 = 1;

				}
			}
		}
		if (bouton0 == 1) {
			res.add(pro0 * comptage_annotation().get(0) /taille_base);
		}
		else {
			res.add((float) 0);
		}

		if (bouton2 == 1) {
			res.add(pro2 * comptage_annotation().get(1) /taille_base);
		}
		else {
			res.add((float) 0);
		}
		if (bouton4 == 1) {
			res.add(pro4 * comptage_annotation().get(2) /taille_base);
		}
		else {
			res.add((float) 0);
		}
		return res;
	}



	public List<Float> bayes_tweet_unigramme(String tweet) throws IOException {
		int taille_base = comptage_annotation().get(0) + comptage_annotation().get(1) + comptage_annotation().get(2);
		List<Float> probabilité = new ArrayList<>();
		List<Float> proba0 = new ArrayList<>();
		List<Float> proba2 = new ArrayList<>();
		List<Float> proba4 = new ArrayList<>();
		float pro0 = 0;
		float pro2 = 0;
		float pro4 = 0;
		int bouton0 =0;
		int bouton2 =0;
		int bouton4 =0;
		String[] uni_gramme = tweet.split(" ");
		List<String> wordList = Arrays.asList(uni_gramme); 
		List<String> tweetList_uniAndBiGrammes = new ArrayList<>();
		tweetList_uniAndBiGrammes.addAll(bigrammes(tweet));
		tweetList_uniAndBiGrammes.addAll(wordList);


		List<Float> res = new ArrayList<>();

		for (String word : uni_gramme) {
			probabilité = bayes_mot(word);
			proba0.add(probabilité.get(0));
			proba2.add(probabilité.get(1));
			proba4.add(probabilité.get(2));

			pro0 = 1;
			pro2 = 1;
			pro4 = 1;

			for (int i = 0; i < proba0.size(); i++ ) {
				if (proba0.get(i) > 0){
					pro0 = (float) Math.pow((pro0 * proba0.get(i)), (frequance(tweet,word ))) ;
					bouton0 = 1;

				}

				if (proba2.get(i) > 0){
					pro2 = (float) Math.pow((pro2 * proba2.get(i)), (frequance(tweet,word ))) ;
					bouton2 = 1;

				}
				if (proba4.get(i) > 0){
					pro4 = (float) Math.pow((pro4 * proba4.get(i)), (frequance(tweet,word ))) ;
					bouton4 = 1;

				}
			}
		}
		if (bouton0 == 1) {
			res.add(pro0 * comptage_annotation().get(0) /taille_base);
		}
		else {
			res.add((float) 0);
		}

		if (bouton2 == 1) {
			res.add(pro2 * comptage_annotation().get(1) /taille_base);
		}
		else {
			res.add((float) 0);
		}
		if (bouton4 == 1) {
			res.add(pro4 * comptage_annotation().get(2) /taille_base);
		}
		else {
			res.add((float) 0);
		}
		return res;
	}




	public List<Float> bayes_tweet_unigramme_bigramme(String tweet) throws IOException {
		int taille_base = comptage_annotation().get(0) + comptage_annotation().get(1) + comptage_annotation().get(2);
		List<Float> probabilité = new ArrayList<>();
		List<Float> proba0 = new ArrayList<>();
		List<Float> proba2 = new ArrayList<>();
		List<Float> proba4 = new ArrayList<>();
		float pro0 = 0;
		float pro2 = 0;
		float pro4 = 0;
		int bouton0 =0;
		int bouton2 =0;
		int bouton4 =0;
		String[] uni_gramme = tweet.split(" ");
		List<String> wordList = Arrays.asList(uni_gramme); 
		List<String> tweetList_uniAndBiGrammes = new ArrayList<>();
		tweetList_uniAndBiGrammes.addAll(bigrammes(tweet));
		tweetList_uniAndBiGrammes.addAll(wordList);


		List<Float> res = new ArrayList<>();

		for (String word : tweetList_uniAndBiGrammes) {
			probabilité = bayes_mot(word);
			proba0.add(probabilité.get(0));
			proba2.add(probabilité.get(1));
			proba4.add(probabilité.get(2));

			pro0 = 1;
			pro2 = 1;
			pro4 = 1;

			for (int i = 0; i < proba0.size(); i++ ) {
				if (proba0.get(i) > 0){
					pro0 = (float) Math.pow((pro0 * proba0.get(i)), (frequance(tweet,word ))) ;
					bouton0 = 1;

				}

				if (proba2.get(i) > 0){
					pro2 = (float) Math.pow((pro2 * proba2.get(i)), (frequance(tweet,word ))) ;
					bouton2 = 1;

				}
				if (proba4.get(i) > 0){
					pro4 = (float) Math.pow((pro4 * proba4.get(i)), (frequance(tweet,word ))) ;
					bouton4 = 1;

				}
			}
		}
		if (bouton0 == 1) {
			res.add(pro0 * comptage_annotation().get(0) /taille_base);
		}
		else {
			res.add((float) 0);
		}

		if (bouton2 == 1) {
			res.add(pro2 * comptage_annotation().get(1) /taille_base);
		}
		else {
			res.add((float) 0);
		}
		if (bouton4 == 1) {
			res.add(pro4 * comptage_annotation().get(2) /taille_base);
		}
		else {
			res.add((float) 0);
		}
		return res;
	}











	public int get_annotation_unigramme(String tweet) throws IOException {
		List<Float> list_bayes = bayes_tweet_unigramme(tweet);
		Float obj = Collections.max(list_bayes);
		int index=list_bayes.indexOf(obj);
		if (index == 0) {
			return 0;
		}
		else if (index == 1) {
			return 2;
		}
		else if (index == 2){
			return 4; 
		}
		else {
			return -1;
		}
	}




	public int get_annotation_bigramme(String tweet) throws IOException {
		List<Float> list_bayes = bayes_tweet_bigramme(tweet);
		Float obj = Collections.max(list_bayes);
		int index=list_bayes.indexOf(obj);
		if (index == 0) {
			return 0;
		}
		else if (index == 1) {
			return 2;
		}
		else if (index == 2){
			return 4; 
		}
		else {
			return -1;
		}
	}




	public int get_annotation_unigramme_bigramme(String tweet) throws IOException {
		List<Float> list_bayes = bayes_tweet_unigramme_bigramme(tweet);
		Float obj = Collections.max(list_bayes);
		int index=list_bayes.indexOf(obj);
		if (index == 0) {
			return 0;
		}
		else if (index == 1) {
			return 2;
		}
		else if (index == 2){
			return 4; 
		}
		else {
			return -1;
		}
	}










	public  double classifier_unigramme() throws IOException{
		double  nb_Faux =0;
		double taux =0;
		CrossValidation crossValidation = new CrossValidation();
		List<List<List<String>>> bdd = crossValidation.splitKTweetList(nb);
		int tailleDataTest = bdd.get(0).size();
		List<List<String>> dataTest = new ArrayList<>();
		for (curseur = 0; curseur < nb; curseur++) {
			dataTest = bdd.get(curseur);
			for (int n = 0; n < tailleDataTest; n++) {
				int annotation = Integer.parseInt(dataTest.get(curseur).get(1));
				int classifierAnnotation = get_annotation_unigramme(dataTest.get(n).get(0));
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



	public  double classifier_bigramme() throws IOException{
		double  nb_Faux =0;
		double taux =0;
		CrossValidation crossValidation = new CrossValidation();
		List<List<List<String>>> bdd = crossValidation.splitKTweetList(nb);
		int tailleDataTest = bdd.get(0).size();
		List<List<String>> dataTest = new ArrayList<>();
		for (curseur = 0; curseur < nb; curseur++) {
			dataTest = bdd.get(curseur);
			for (int n = 0; n < tailleDataTest; n++) {
				int annotation = Integer.parseInt(dataTest.get(curseur).get(1));
				int classifierAnnotation = get_annotation_bigramme(dataTest.get(n).get(0));
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





	public  double classifier_unigramme_bigramme() throws IOException{
		double  nb_Faux =0;
		double taux =0;
		CrossValidation crossValidation = new CrossValidation();
		List<List<List<String>>> bdd = crossValidation.splitKTweetList(nb);
		int tailleDataTest = bdd.get(0).size();
		List<List<String>> dataTest = new ArrayList<>();
		for (curseur = 0; curseur < nb; curseur++) {
			dataTest = bdd.get(curseur);
			for (int n = 0; n < tailleDataTest; n++) {
				int annotation = Integer.parseInt(dataTest.get(curseur).get(1));
				int classifierAnnotation = get_annotation_unigramme_bigramme(dataTest.get(n).get(0));
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
