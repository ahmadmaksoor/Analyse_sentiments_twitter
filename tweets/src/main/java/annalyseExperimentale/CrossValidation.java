package annalyseExperimentale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import naiveMethode.NaiveMethode;
import bayesMethode.BayesMethodeFrequance;
import bayesMethode.BayesMethodePresence;
import naiveMethode.ExtractorSentimatWorld;
import knnMethode.KnnMethode;

import java.util.Collections;


public class CrossValidation {

	public CrossValidation() {}



	public List<List<String>> readData() throws IOException { 
		List<List<String>> records = new ArrayList<>();
		List<List<String>> list = new ArrayList<>();
		List<String> list1 = new ArrayList<>();
		String tweet = "";
		String annotation = "";
		String filename = "tweets_ManualMethod.csv";
		String repertoireCourant= System.getProperty("user.dir");
		String absoluteFilePath = "";
		absoluteFilePath = repertoireCourant+ File.separator + filename;
		
		try (BufferedReader br = new BufferedReader(new FileReader(absoluteFilePath))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] values = line.split("\t");
				records.add(Arrays.asList(values));
			}
			for (int i = 0; i <records.size() ; i++) {
				tweet = records.get(i).get(2);
				annotation = records.get(i).get(5);
				list1.add(tweet);
				list1.add(annotation);
				list.add(list1);
				list1 =new ArrayList<>();
			}
			Collections.shuffle(list);
			return list;
		}
	}






	
	public  List<List<List<String>>> splitKTweetList(int k) throws IOException{

		List<List<String>> r= readData();
		List<List<String>> l = new ArrayList<List<String>>();
		List<List<List<String>>> listOLists = new ArrayList<List<List<String>>>();


		if (k > r.size()) {
			System.err.println("La tweetBase ne contient pas assez de tweets, choississez un k plus petit ou ajouter des tweets à la base");
			System.exit(-1);
		}


		else {
			int taille = r.size()/k;
			int i = 0;
			while(k != 0) {
				for (i = taille * (k-1); i < taille * k; i ++) {
					l.add(r.get(i));
				}
				
				listOLists.add(l);
				l = new ArrayList<List<String>>();
				k--;
			}
		}
		return listOLists;
	}
	
	public void affichage_taux_erreur() throws IOException {
		KnnMethode knn = new KnnMethode();
		System.out.println("La taux d'erreur pour La classification KNN est : " + knn.classifier());
		
		System.out.println("--------------------------------------------------------------------------");
		String filename = "src/main/java/naiveMethode/Liste/positive.txt";
		String repertoireCourant= System.getProperty("user.dir");
		String absoluteFilePath = "";
		absoluteFilePath = repertoireCourant+ File.separator + filename;
		String[] positive=ExtractorSentimatWorld.getListeMots(absoluteFilePath, "FR");
		filename = "src/main/java/naiveMethode/Liste/negative.txt";
		absoluteFilePath = "";
		absoluteFilePath = repertoireCourant+ File.separator + filename;
		String[] negative=ExtractorSentimatWorld.getListeMots(absoluteFilePath, "FR");

		NaiveMethode nat = new NaiveMethode(positive,negative);
		System.out.println("La taux d'erreur pour La classification Naive Methode est : " + nat.classifier());
		BayesMethodePresence bayes = new BayesMethodePresence();
		System.out.println("--------------------------------------------------------------------------");

		System.out.println("La taux d'erreur pour La classification Bayes Presence uni gramme est : " + bayes.classifier_unigramme());
		System.out.println("La taux d'erreur pour La classification Bayes Presence bi gramme est : " + bayes.classifier_bigramme());
		System.out.println("La taux d'erreur pour La classification Bayes Presence uni gramme et bi gramme est : " + bayes.classifier_unigramme_bigramme());

		System.out.println("--------------------------------------------------------------------------");

		
		BayesMethodeFrequance bayesf = new BayesMethodeFrequance();

		System.out.println("La taux d'erreur pour La classification Bayes Frequance uni gramme est : " + bayesf.classifier_unigramme());
		System.out.println("La taux d'erreur pour La classification Bayes Frequance bi gramme est : " + bayesf.classifier_bigramme());
		System.out.println("La taux d'erreur pour La classification Bayes Frequance uni gramme et bi gramme est : " + bayesf.classifier_unigramme_bigramme());



	}
	
}


