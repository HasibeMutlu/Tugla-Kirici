package brickBracker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();//form ekran� olu�turma
		Gameplay gamePlay = new Gameplay(); //oyun nesnesi olu�turduk yeni bir s�n�f�m yani
		obj.setBounds(10,10,700,600); //form ekran� boyutlar�
		obj.setTitle("TU�LA KIRMA OYUNU");//form ekran� ba�l���n� olu�turduk
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
		// Bo� bir form ekran�n� olu�turduk burda boyutunu kendimiz belirledik
	}

}
