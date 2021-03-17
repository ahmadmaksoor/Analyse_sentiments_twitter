package twitter;
import java.util.Scanner;
import java.util.regex.*;

import bayesMethode.BayesMethodeFrequance;
import bayesMethode.BayesMethodePresence;
import knnMethode.KnnMethode;
import naiveMethode.ExtractorSentimatWorld;
import naiveMethode.NaiveMethode;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;


public class App{
	private String a;
	public  App(String a) throws TwitterException {
		this.a=a;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("secret")
		.setOAuthConsumerSecret("secret")
		.setOAuthAccessToken("secret")
		.setOAuthAccessTokenSecret("secret")
		.setTweetModeExtended(true)
		.setHttpProxyHost("cache-etu.univ-lille1.fr")
		.setHttpProxyPort(3128);

		Twitter twitter = TwitterFactory.getSingleton();


		twitter.getRateLimitStatus("users").get("/users/search");
	}


	public String filtres(String text) throws TwitterException {

		text = text.toLowerCase();


		Pattern p0 = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Matcher m0 = p0.matcher(text);
		text=m0.replaceAll("");

		Pattern p = Pattern.compile("@\\w+ *");
		Matcher m = p.matcher(text);
		text=m.replaceAll("");

		Pattern p1 = Pattern.compile("#[-a-z]*");
		Matcher m1 = p1.matcher(text);
		text=m1.replaceAll("");

		text=text.replaceAll("\'", " ");

		text=text.replaceAll("’", " ");

		text=text.replaceAll("\"", " ");

		text=text.replaceAll("«", " ");

		text=text.replaceAll("»", " ");

		text= text.replaceAll("\n", " ");


		text=text.replaceAll("\t"," ");

		Pattern p2 = Pattern.compile("\\p{Punct}");
		Matcher m2 = p2.matcher(text);
		text=m2.replaceAll(" ");

		text=text.replaceAll("ç","c");

		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

	}

	public String getTexte1() throws TwitterException, IOException {
		String res = "";
		new ExtractorSentimatWorld();
		String filename = "src/main/java/naiveMethode/Liste/positive.txt";
		String repertoireCourant= System.getProperty("user.dir");
		String absoluteFilePath = "";
		absoluteFilePath = repertoireCourant+ File.separator + filename;
		String[] positive=ExtractorSentimatWorld.getListeMots(absoluteFilePath, "FR");
		filename = "src/main/java/naiveMethode/Liste/negative.txt";
		absoluteFilePath = repertoireCourant+ File.separator + filename;
		String[] negative=ExtractorSentimatWorld.getListeMots(absoluteFilePath, "FR");
		NaiveMethode nat = new NaiveMethode(positive,negative);
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(this.a + "+exclude:retweets -filter:links -filter:replies -filter:images" );
		query.setLang("fr");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {

			String r = status.getText();
			r = filtres(r);

			res+= status.getId()+"\t"+status.getUser().getScreenName()+ ":\t"+ r+"\t" +status.getCreatedAt()+"\t"+this.a+"\t"+nat.getAnnotation(r)+ "\n" ;

		}

		return res;
	}

	public String getPercentageNaiveMethode() throws TwitterException, IOException {
		String line = "";
		String annotaion = ""; 
		int cpt0= 0;
		int cpt2= 0;
		int cpt4= 0;
		String resKnn= getTexte1();
		Scanner scanner = new Scanner(resKnn);
		while (scanner.hasNextLine()) {
			line += scanner.nextLine();
			annotaion += line.charAt(line.length()-1);
		}

		for(int i=0 ; i < annotaion.length(); i++) {
			if(annotaion.charAt(i) == '0') {
				cpt0++;
			}
			else if(annotaion.charAt(i) == '2') {
				cpt2++;
			}
			else if(annotaion.charAt(i) == '4') {
				cpt4++;
			}

		}

		scanner.close();
		String res="pourcentage des tweets negatives "+(float)cpt0/annotaion.length()+"\n";
		res+="pourcentage des tweets neutres "+(float)cpt2/annotaion.length()+"\n";
		res+="pourcentage des tweets positives "+(float)cpt4/annotaion.length()+"\n";
		return res;

	}




	public String getTexte2() throws TwitterException, IOException {
		String res = "";
		new ExtractorSentimatWorld();

		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(this.a + "+exclude:retweets -filter:links -filter:replies -filter:images" );
		query.setLang("fr");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {

			String r = status.getText();
			r = filtres(r);

			res+= status.getId()+"\t"+status.getUser().getScreenName()+ ":\t"+ r+"\t" +status.getCreatedAt()+"\t"+this.a+"\t-1\n" ;
		}
		return res;
	}




	public String getTexte3() throws TwitterException, IOException {
		String res = "";
		new ExtractorSentimatWorld();
		KnnMethode knn = new KnnMethode();
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(this.a + "+exclude:retweets -filter:links -filter:replies -filter:images" );
		query.setLang("fr");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {

			String r = status.getText();

			r = filtres(r);

			res+= status.getId()+"\t"+status.getUser().getScreenName()+ ":\t"+ r+"\t" +status.getCreatedAt()+"\t"+this.a+"\t"+knn.get_annotation(status.getText(), 5)+ "\n" ;
		}





		return res;
	}

	public String getPercentageKnn() throws TwitterException, IOException {
		String line = "";
		String annotaion = ""; 
		int cpt0= 0;
		int cpt2= 0;
		int cpt4= 0;
		String resKnn= getTexte3();
		Scanner scanner = new Scanner(resKnn);
		while (scanner.hasNextLine()) {
			line += scanner.nextLine();
			annotaion += line.charAt(line.length()-1);
		}

		for(int i=0 ; i < annotaion.length(); i++) {
			if(annotaion.charAt(i) == '0') {
				cpt0++;
			}
			else if(annotaion.charAt(i) == '2') {
				cpt2++;
			}
			else if(annotaion.charAt(i) == '4') {
				cpt4++;
			}

		}

		scanner.close();
		String res="pourcentage des tweets negatives "+(float)cpt0/annotaion.length()+"\n";
		res+="pourcentage des tweets neutres "+(float)cpt2/annotaion.length()+"\n";
		res+="pourcentage des tweets positives "+(float)cpt4/annotaion.length()+"\n";
		return res;

	}


	public String getTexte4() throws TwitterException, IOException {
		String res = "";
		new ExtractorSentimatWorld();
		BayesMethodePresence bayes = new BayesMethodePresence();
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(this.a + "+exclude:retweets -filter:links -filter:replies -filter:images" );
		query.setLang("fr");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {

			String r = status.getText();

			r = filtres(r);

			res+= status.getId()+"\t"+status.getUser().getScreenName()+ ":\t"+ r+"\t" +status.getCreatedAt()+"\t"+this.a+"\t"+bayes.get_annotation_unigramme(status.getText())+ "\n" ;
		}

		return res;
	}
	public String getPercentageBayesPresenceMethode() throws TwitterException, IOException {
		String line = "";
		String annotaion = ""; 
		int cpt0= 0;
		int cpt2= 0;
		int cpt4= 0;
		String resKnn= getTexte4();
		Scanner scanner = new Scanner(resKnn);
		while (scanner.hasNextLine()) {
			line += scanner.nextLine();
			annotaion += line.charAt(line.length()-1);
		}

		for(int i=0 ; i < annotaion.length(); i++) {
			if(annotaion.charAt(i) == '0') {
				cpt0++;
			}
			else if(annotaion.charAt(i) == '2') {
				cpt2++;
			}
			else if(annotaion.charAt(i) == '4') {
				cpt4++;
			}

		}

		scanner.close();
		String res="pourcentage des tweets negatives "+(float)cpt0/annotaion.length()+"\n";
		res+="pourcentage des tweets neutres "+(float)cpt2/annotaion.length()+"\n";
		res+="pourcentage des tweets positives "+(float)cpt4/annotaion.length()+"\n";
		return res;

	}

	public String getTexte5() throws TwitterException, IOException {
		String res = "";
		new ExtractorSentimatWorld();
		BayesMethodeFrequance bayes = new BayesMethodeFrequance();
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(this.a + "+exclude:retweets -filter:links -filter:replies -filter:images" );
		query.setLang("fr");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {

			String r = status.getText();

			r = filtres(r);

			res+= status.getId()+"\t"+status.getUser().getScreenName()+ ":\t"+ r+"\t" +status.getCreatedAt()+"\t"+this.a+"\t"+bayes.get_annotation_unigramme(status.getText())+ "\n" ;
		}

		return res;
	}	

	public String getPercentageBayesFrequenceMethode() throws TwitterException, IOException {
		String line = "";
		String annotaion = ""; 
		int cpt0= 0;
		int cpt2= 0;
		int cpt4= 0;
		String resKnn= getTexte5();
		Scanner scanner = new Scanner(resKnn);
		while (scanner.hasNextLine()) {
			line += scanner.nextLine();
			annotaion += line.charAt(line.length()-1);
		}

		for(int i=0 ; i < annotaion.length(); i++) {
			if(annotaion.charAt(i) == '0') {
				cpt0++;
			}
			else if(annotaion.charAt(i) == '2') {
				cpt2++;
			}
			else if(annotaion.charAt(i) == '4') {
				cpt4++;
			}

		}

		scanner.close();
		String res="pourcentage des tweets negatives "+(float)cpt0/annotaion.length()+"\n";
		res+="pourcentage des tweets neutres "+(float)cpt2/annotaion.length()+"\n";
		res+="pourcentage des tweets positives "+(float)cpt4/annotaion.length()+"\n";
		return res;

	}


}
