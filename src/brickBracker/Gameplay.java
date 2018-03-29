package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay  extends JPanel implements KeyListener,ActionListener{ //top hareketi i�in bunu yapt�k
	private boolean play = false;//oyun hemen ba�lam�yor o y�zden false
	private int score = 0; // ba�lang�� puan� 
	
	private int totalBricks = 21; //toplam k�r�lmas� gereken 21 tu�la vard�r
	
	private Timer timer;//Timer i�in pakete ekledik
	private int delay = 8;//topun h�z�
	
	private int playerX = 310;//Top ile ye��l �izgi aras�ndaki ba�lang�� pozisyonu
	
	private int ballposX = 120;//topun X y�n�ndeki ba�lang��� 
	private int ballposY = 350;//topun Y y�n�ndeki ba�lang���
	private int ballXdir = -1;//topun X y�n�n� ayarlamak i�in
	private int ballYdir = -2;//topun Y y�n�n� ayarlamak i�in
	
	private MapGenerator map;
	
	public Gameplay() {  //tugla bilgilerini giricez
		map=new MapGenerator(3,7); //tu�la g�r�n�m� 3 s�tun 7 sat�rd�r
		addKeyListener(this);//anahtar dinleyici eklemek
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g) {
		//oyun arka rengi ve �zellikleri (x,y,width,hight)
		g.setColor(Color.black); 
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
	   //form ekran� kenarl�k �zellikleri
		g.setColor(Color.red);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score de�eri �zelleikleri
		g.setColor(Color.blue);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		
		//alttaki ye�il �izgimizin �zelli�i
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//top �zellikleri
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		
		//bu if ko�ulunda oyunu oynad�kdan sonra kazanma sonucunda ��kan mesaj k�sm�
		if(totalBricks <=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("TEBR�KLER:", 260, 300);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Oyunu Kazand�n�z", 230, 350);
			
		}
		
		//bu if ko�ulunda da oyunu oynad�kdan sonra kaybetme sonucunda ��kan mesaj k�sm�
		if(ballposY >570) { 
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over, Scores:", 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Oyunu Kaybettiniz", 230, 350);
			
		}
		
		g.dispose();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start(); //oyunu ba�lat�yor.
		if(play) {  //topun var olup olmad���n� alg�l�yor.
			if(new Rectangle(ballposX,ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) { //topun xy y�n�nde ye�il �izginin kesi�mesi
				ballYdir = -ballYdir; // topun y y�n�ne gidiyorsa tekrar y y�n�nde geri d�n�yor
			}
			A: for(int i=0; i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length; j++) {
					if(map.map[i][j]>0) {
						int brickX =j* map.brickWidth + 80;
						int brickY =i* map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int bricktHeigt = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,bricktHeigt);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect  =  rect;
						
						if(ballRect.intersects(brickRect)) { // topun tu�layla kesi�ti�i zamanki her tu�la i�in 5 score artt�r�yo
						
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1>=brickRect.x+brickRect.width) {     	
								ballXdir = -ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			//topun pozisyonlar�n� belirtiyor
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint();
		
	}

	
    @Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
		
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >=600) {
				playerX = 600;
			}else {
				moveRight();
			}
			
		}
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
        	if(playerX < 10) {
				playerX = 10;
			}else {
				moveLeft();
			}
		}
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        	if(play) {
        		play=true;
        		ballposX = 120;
        		ballposY = 350;
        		ballXdir = -1;
        		ballYdir = -2;
        		playerX  = 310;
        		score = 0;
        		totalBricks = 21;
        		map = new MapGenerator(3,7);
        		
        		repaint();
        	}
        }
		
	}	
   public void moveRight() { // sa� tarafa hareket ettirme
	   play = true;
	   playerX+=20;
   }
   public void moveLeft() { //sol tarafa hareket ettirme
	   play = true;
	   playerX-=20;
   }

}
