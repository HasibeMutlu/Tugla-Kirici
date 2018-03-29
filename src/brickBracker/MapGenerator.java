package brickBracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;



public class MapGenerator { // tu�la g�r�nt�s� olu�tturuyor
   public  int map[][];
   public int brickWidth; //tu�la geni�li�i
   public int brickHeight;//tu�la y�ksekli�i
   public MapGenerator(int row, int col) {
	   map=new int[row][col];
	   for(int i=0; i< map.length; i++) {
		   for(int j=0; j<map[0].length; j++) {
			   map[i][j]= 1;
		   }
	   }
	   brickWidth = 540/col;// s�tun geni�li�i
	   brickHeight = 150/row; //sat�r y�ksekli�i
   }
   public void draw(Graphics2D g) { //tu�la boyutu ve rengi yap�ld� bu k�s�mda
	   for(int i=0; i< map.length; i++) {
		   for(int j=0; j<map[0].length; j++) {
			   if(map[i][j] > 0) {
				   g.setColor(Color.white);//tu�la renkleri
				   g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight);
				   
				   g.setStroke(new BasicStroke(3));
				   g.setColor(Color.black);//tu�la kenarl�klar� rengi
				   g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight);
				   
			   }
        } 
     }
   }
   public void setBrickValue(int value,int row, int col) {
	   map[row][col] =value;
	   
   }
}
  
