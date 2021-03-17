import interfaceGraphique.GUI;

import java.io.IOException;

import javax.swing.JFrame;

import annalyseExperimentale.CrossValidation;

public class Main{

	public static void main(String[] args) throws IOException {

		
		
		GUI frame = new GUI();
		frame.pack();
		frame.setTitle("Recherche de Tweets");
		frame.setSize(600,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		CrossValidation cv = new CrossValidation();
		cv.affichage_taux_erreur();

	}
}

