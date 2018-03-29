package brickBracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;



public class MapGenerator { // tuðla görüntüsü oluþtturuyor
   public  int map[][];
   public int brickWidth; //tuðla geniþliði
   public int brickHeight;//tuðla yüksekliði
   public MapGenerator(int row, int col) {
	   map=new int[row][col];
	   for(int i=0; i< map.length; i++) {
		   for(int j=0; j<map[0].length; j++) {
			   map[i][j]= 1;
		   }
	   }
	   brickWidth = 540/col;// sütun geniþliði
	   brickHeight = 150/row; //satýr yüksekliði
   }
   public void draw(Graphics2D g) { //tuðla boyutu ve rengi yapýldý bu kýsýmda
	   for(int i=0; i< map.length; i++) {
		   for(int j=0; j<map[0].length; j++) {
			   if(map[i][j] > 0) {
				   g.setColor(Color.white);//tuðla renkleri
				   g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight);
				   
				   g.setStroke(new BasicStroke(3));
				   g.setColor(Color.black);//tuðla kenarlýklarý rengi
				   g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight);
				   
			   }
        } 
     }
   }
   public void setBrickValue(int value,int row, int col) {
	   map[row][col] =value;
	   
   }
}
  
