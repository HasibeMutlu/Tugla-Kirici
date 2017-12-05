package brickBracker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();//form ekraný oluþturma
		Gameplay gamePlay = new Gameplay(); //oyun nesnesi oluþturduk yeni bir sýnýfým yani
		obj.setBounds(10,10,700,600); //form ekraný boyutlarý
		obj.setTitle("TUÐLA KIRMA OYUNU");//form ekraný baþlýðýný oluþturduk
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		// Boþ bir form ekranýný oluþturduk burda boyutunu kendimiz belirledik
	}

}
