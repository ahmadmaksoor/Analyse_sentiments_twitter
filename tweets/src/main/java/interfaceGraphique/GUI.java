package interfaceGraphique;

import javax.swing.*;

import twitter4j.TwitterException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import twitter.App;

import java.io.*;


public class GUI extends JFrame {


	private static final long serialVersionUID = 1L;
	private TextArea text = new TextArea("Display the Result:");// for displaying the result

	private JTextField searchField = new JTextField();
	private JButton save   = new JButton("Save to File"); 


	private JButton searchNaive   = new JButton("Search with naive method");
	private JButton searchManual   = new JButton("Search with manual method");
	private JButton searchKNN   = new JButton("Search with KNN method");
	private JButton searchBayesPresence   = new JButton("Search with Bayes Presence method");
	private JButton searchFreqPresence   = new JButton("Search with Bayes Frequence method");



	private String content = "";
	
	private Integer bouton = 0;

	public GUI() {
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new GridLayout(1,2));
		JPanel p6 = new JPanel(new GridLayout(1,2));
		JPanel p7 = new JPanel(new GridLayout(1,2));

		p6.add(save);
		JPanel p3 = new JPanel(new BorderLayout());
		JPanel p5 = new JPanel(new BorderLayout());


		p3.add(new Label("Taper un mot :"),BorderLayout.WEST);
		p3.add(searchField,BorderLayout.CENTER);
		p5.add(searchNaive,BorderLayout.EAST);
		p5.add(searchManual,BorderLayout.WEST);
		p5.add(searchKNN,BorderLayout.CENTER);
		p5.add(searchBayesPresence,BorderLayout.NORTH);
		p5.add(searchFreqPresence,BorderLayout.SOUTH);


		JPanel p4 = new JPanel(new GridLayout(6,1));
		p4.add(p1);
		p4.add(p2);
		p4.add(p6);
		p4.add(p7);
		p4.add(p3);
		p4.add(p5);
		p4.setBorder(new TitledBorder("Recherche de Tweets"));
		add(p4,BorderLayout.NORTH);
		add(text,BorderLayout.CENTER);


		searchNaive.addActionListener(new  searchListenerNaive());
		searchManual.addActionListener(new  searchListenerManual());
		searchKNN.addActionListener(new  searchListenerKNN());
		searchBayesPresence.addActionListener(new  searchListenerBayesPresence());
		searchFreqPresence.addActionListener(new  searchListenerFrequencePresence());

		save.addActionListener(new GUI. saveListener());



	}

	public int get_bouton() {
		return bouton;
	}


	private class searchListenerManual implements ActionListener  {
		public void actionPerformed(ActionEvent ae) {

			String terms = searchField.getText();

			text.setText( "\"Please wait a few second!\"");
			try {
				bouton = 2;
				App app=new App(terms);
				text.setText((app.getTexte2()));
				content+= (app.getTexte2());
				if (content.length()==0){
					text.setText( "\"Sorry, we do not found Tweets!\"");
				}
			} catch (TwitterException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			} catch (IOException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			}
		}
	}


	private class searchListenerNaive implements ActionListener  {
		public void actionPerformed(ActionEvent ae) {
			String terms = searchField.getText();

			text.setText( "\"Please wait a few second!\"");
			try {
				bouton = 1;
				App app=new App(terms);
				text.setText((app.getTexte1()));
				text.append("----------------------------------------------------------------------------------\n");
				text.append(app.getPercentageNaiveMethode());
				content+= (app.getTexte1());
				content+= app.getPercentageNaiveMethode();

				if (content.length()==0){
					text.setText( "\"Sorry, we do not found Tweets!\"");
				}
			} catch (TwitterException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			} catch (IOException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			}
		}
	}


	private class searchListenerKNN implements ActionListener  {
		public void actionPerformed(ActionEvent ae) {
			String terms = searchField.getText();

			text.setText( "\"Please wait a few second!\"");
			try {
				bouton = 3;
				App app=new App(terms);
			//	text.setText(app.getPercentageKnn());
				text.setText((app.getTexte3()));
				text.append("----------------------------------------------------------------------------------\n");
				text.append(app.getPercentageKnn());
				content+= (app.getTexte3());
				content+= app.getPercentageKnn();

				if (content.length()==0){
					text.setText( "\"Sorry, we do not found Tweets!\"");
				}
			} catch (TwitterException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			} catch (IOException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			}
		}
	}


	private class searchListenerBayesPresence implements ActionListener  {
		public void actionPerformed(ActionEvent ae) {
			String terms = searchField.getText();

			text.setText( "\"Please wait a few second!\"");
			try {
				bouton = 4;
				App app=new App(terms);
				text.setText((app.getTexte4()));
				text.append("----------------------------------------------------------------------------------\n");
				text.append(app.getPercentageBayesPresenceMethode());
				content+= (app.getTexte4());
				if (content.length()==0){
					text.setText( "\"Sorry, we do not found Tweets!\"");
				}
			} catch (TwitterException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			} catch (IOException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			}
		}
	}
	
	private class searchListenerFrequencePresence implements ActionListener  {
		public void actionPerformed(ActionEvent ae) {
			String terms = searchField.getText();

			text.setText( "\"Please wait a few second!\"");
			try {
				bouton = 5;
				App app=new App(terms);
				text.setText((app.getTexte5()));
				text.append("----------------------------------------------------------------------------------\n");
				text.append(app.getPercentageBayesFrequenceMethode());
				content+= (app.getTexte5());
				if (content.length()==0){
					text.setText( "\"Sorry, we do not found Tweets!\"");
				}
			} catch (TwitterException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			} catch (IOException e) {
				text.setText( "\"Sorry, we do not found Tweets!\"");
				e.printStackTrace();
			}
		}
	}






	private class saveListener implements ActionListener {
		public void actionPerformed(ActionEvent ae)  {
			if (bouton == 1) {
				try

				(FileWriter file = new FileWriter("NaiveMethod.csv", true);
						BufferedWriter bw = new BufferedWriter(file);
						PrintWriter output = new PrintWriter(bw))
				{
					output.println(content);
					JOptionPane.showMessageDialog(null,"Data has saved to file");
					output.close();
				}
				catch (IOException ex){JOptionPane.showMessageDialog(null,"File not found.");}
			}

			else if (bouton == 2) {
				try

				(FileWriter file = new FileWriter("ManualMethod.csv", true);
						BufferedWriter bw = new BufferedWriter(file);
						PrintWriter output = new PrintWriter(bw))
				{
					output.println(content);
					JOptionPane.showMessageDialog(null,"Data has saved to file");
					output.close();
				}
				catch (IOException ex){JOptionPane.showMessageDialog(null,"File not found.");}
			}
			else if (bouton == 3) {
				try

				(FileWriter file = new FileWriter("KNNMethod.csv", true);
						BufferedWriter bw = new BufferedWriter(file);
						PrintWriter output = new PrintWriter(bw))
				{
					output.println(content);
					JOptionPane.showMessageDialog(null,"Data has saved to file");
					output.close();
				}
				catch (IOException ex){JOptionPane.showMessageDialog(null,"File not found.");}


			}
		}
	}

}










