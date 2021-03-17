package interfaceGraphique;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnRechercher = new JButton("Rechercher");

	private JTextField txtA = new JTextField();

	private JLabel lblA = new JLabel("Taper un mot : ");


	public Frame(){
		setTitle("Recherche de Tweets");
		setSize(400,200);
		setLocation(new Point(300,200));
		setLayout(null);    
		setResizable(false);

		initComponent();    
		initEvent();    
	}

	private void initComponent(){
		btnRechercher.setBounds(300,130, 80,25);


		txtA.setBounds(100,10,100,20);


		lblA.setBounds(20,10,100,20);


		add(btnRechercher);
		add(lblA);
		add(txtA);

	}

	private void initEvent(){

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(1);
			}
		});

		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTutupClick(e);
			}
		});

		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTambahClick(e);
			}
		});
	}

	private void btnTutupClick(ActionEvent evt){
		System.exit(0);
	}

	private void btnTambahClick(ActionEvent evt){
		try{
			Integer.parseInt(txtA.getText());

		}catch(Exception e){
			System.out.println(e);
			JOptionPane.showMessageDialog(null, 
					e.toString(),
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args){
		Frame f = new Frame();
		f.setVisible(true);
	}
}

