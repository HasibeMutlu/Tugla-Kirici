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

public class Gameplay  extends JPanel implements KeyListener,ActionListener{ //top hareketi için bunu yaptýk
	private boolean play = false;//oyun hemen baþlamýyor o yüzden false
	private int score = 0; // baþlangýç puaný 
	
	private int totalBricks = 21; //toplam kýrýlmasý gereken 21 tuðla vardýr
	
	private Timer timer;//Timer için pakete ekledik
	private int delay = 8;//topun hýzý
	
	private int playerX = 310;//Top ile yeþýl çizgi arasýndaki baþlangýç pozisyonu
	
	private int ballposX = 120;//topun X yönündeki baþlangýçý 
	private int ballposY = 350;//topun Y yönündeki baþlangýçý
	private int ballXdir = -1;//topun X yönünü ayarlamak için
	private int ballYdir = -2;//topun Y yönünü ayarlamak için
	
	private MapGenerator map;
	
	public Gameplay() {  //tugla bilgilerini giricez
		map=new MapGenerator(3,7); //tuðla görünümü 3 sütun 7 satýrdýr
		addKeyListener(this);//anahtar dinleyici eklemek
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g) {
		//oyun arka rengi ve özellikleri (x,y,width,hight)
		g.setColor(Color.black); 
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
	   //form ekraný kenarlýk özellikleri
		g.setColor(Color.red);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score deðeri özelleikleri
		g.setColor(Color.blue);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		
		//alttaki yeþil çizgimizin özelliði
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//top özellikleri
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		
		//bu if koþulunda oyunu oynadýkdan sonra kazanma sonucunda çýkan mesaj kýsmý
		if(totalBricks <=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("TEBRÝKLER:", 260, 300);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Oyunu Kazandýnýz", 230, 350);
			
		}
		
		//bu if koþulunda da oyunu oynadýkdan sonra kaybetme sonucunda çýkan mesaj kýsmý
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
		timer.start(); //oyunu baþlatýyor.
		if(play) {  //topun var olup olmadýðýný algýlýyor.
			if(new Rectangle(ballposX,ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) { //topun xy yönünde yeþil çizginin kesiþmesi
				ballYdir = -ballYdir; // topun y yönüne gidiyorsa tekrar y yönünde geri dönüyor
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
						
						if(ballRect.intersects(brickRect)) { // topun tuðlayla kesiþtiði zamanki her tuðla için 5 score arttýrýyo
						
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
			//topun pozisyonlarýný belirtiyor
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
   public void moveRight() { // sað tarafa hareket ettirme
	   play = true;
	   playerX+=20;
   }
   public void moveLeft() { //sol tarafa hareket ettirme
	   play = true;
	   playerX-=20;
   }

}
