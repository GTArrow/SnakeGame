import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] xpoint = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
			525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int[] ypoint = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
			525,550,575,600,625};
	
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	private boolean right = false;
	private boolean up = false;
	private boolean left = false;
	private boolean down = false;
	private boolean death = false;
	private boolean moveable = true;
	
	private ImageIcon titleImage;
	private ImageIcon head;
	private ImageIcon body;
	private ImageIcon point;
	private ImageIcon end;
	
	private Random random = new Random();
	private int randxpoint = random.nextInt(34);
	private int randypoint = random.nextInt(23);
	private int lengthofsnake = 3;
	private int move =0;
	private int score =0;
	private int score2 =0;
	
	private Timer timer;
	private int delay =100;
	
	private String filename = "highestscore";
	private String line;
	
	
	public GamePlay() throws IOException {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedreader = new BufferedReader(fileReader);
			while((line = bufferedreader.readLine())!= null) {score2= Integer.parseInt(line);}
			bufferedreader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics g) {
		if(move==0) {
			snakexlength[2]=50;
			snakexlength[1]=75;
			snakexlength[0]=100;
			
			snakeylength[2]=100;
			snakeylength[1]=100;
			snakeylength[0]=100;
		}
		
		//draw title image boarder
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		//draw title image
		titleImage = new ImageIcon("SankeTitle.png");
		titleImage.paintIcon(this, g, 25, 11);
		//draw border for game
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		//draw background
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);
		//draw score
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,14));
		g.drawString("Scores: "+score, 780, 30);
		//draw highest score
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.PLAIN,14));
		g.drawString("Highest Score: "+score2, 760, 50);
		//draw snake head
		head = new ImageIcon("SnakeHead.png");
		head.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		//draw body
		for(int i=0; i<lengthofsnake;i++ ) {
			body = new ImageIcon("SnakeHead.png");
			body.paintIcon(this, g, snakexlength[i], snakeylength[i]);
		}
		//draw points
		point = new ImageIcon("point.png");
		point.paintIcon(this, g, xpoint[randxpoint],ypoint[randypoint]);
		//draw end of the game
		end = new ImageIcon("GameEnd.png");
		if(collide(snakexlength,snakeylength,lengthofsnake)) {
			death = true;
			left = false;
			right = false;
			up = false;
			down =false;
			moveable = false;
		}
		if(death) {
			end.paintIcon(this, g, 200, 250);
			try {
				if(score>score2) {
				BufferedWriter writer = new BufferedWriter(new FileWriter("highestscore"));
				
					writer.write(String.valueOf(score));
				writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedreader = new BufferedReader(fileReader);
				while((line = bufferedreader.readLine())!= null) {score2= Integer.parseInt(line);}
				bufferedreader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		g.dispose();
		
		
		
	}
	public boolean collide( int[] x, int[] y, int c) {
		int xh= x[0];
		int yh= y[0];
		for (int i=1 ; i<c; i++) {
			if(xh==x[i] && yh==y[i]) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (xpoint[randxpoint] == snakexlength[0] && ypoint[randypoint] == snakeylength[0]) {
			lengthofsnake+=1;
			score+=1;
			randxpoint = random.nextInt(34);
			randypoint = random.nextInt(23);
			repaint();
		}
		if(moveable) {
		if(right) {
			for(int r = lengthofsnake-1;r>=0;r--) {
				snakeylength[r+1]= snakeylength[r];
			}
			for(int r = lengthofsnake-1;r>=0;r--) {
				if(r==0) {
					snakexlength[r]=snakexlength[r]+25;
					
				}else {
					snakexlength[r] = snakexlength[r-1];
					
				}if(snakexlength[r]>850){
					snakexlength[r]=25;
					
				}
			}
			repaint();
			
		}
		if(left) {
			for(int r = lengthofsnake-1;r>=0;r--) {
				snakeylength[r+1]= snakeylength[r];
			}
			for(int r = lengthofsnake-1;r>=0;r--) {
				if(r==0) {
					snakexlength[r]=snakexlength[r]-25;
					
				}else {
					snakexlength[r] = snakexlength[r-1];
					
				}if(snakexlength[r]<25){
					snakexlength[r]=850;
					
				}
			}
			repaint();
			
		}
		if(up) {
			for(int r = lengthofsnake-1;r>=0;r--) {
				snakexlength[r+1]= snakexlength[r];
			}
			for(int r = lengthofsnake-1;r>=0;r--) {
				if(r==0) {
					snakeylength[r]=snakeylength[r]-25;
					
				}else {
					snakeylength[r] = snakeylength[r-1];
					
				}if(snakeylength[r]<75){
					snakeylength[r]=625;
					
				}
			}
			repaint();
			
		}
		if(down) {
			for(int r = lengthofsnake-1;r>=0;r--) {
				snakexlength[r+1]= snakexlength[r];
			}
			for(int r = lengthofsnake-1;r>=0;r--) {
				if(r==0) {
					snakeylength[r]=snakeylength[r]+25;
					
				}else {
					snakeylength[r] = snakeylength[r-1];
					
				}if(snakeylength[r]>625){
					snakeylength[r]=75;
					
				}
			}
			repaint();
			
		}
		}
	}
		// TODO Auto-generated method stub

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			move++;
			right = true;
			if(!left) {
				right=true;
			}else {
				right= false;
				left = true;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_LEFT) {
			move++;
			left = true;
			if(!right) {
				left=true;
			}else {
				left= false;
				right = true;
			}
			up = false;
			down = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_UP) {
			move++;
			up = true;
			if(!down) {
				up=true;
			}else {
				up= false;
				down = true;
			}
			left = false;
			right = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_DOWN) {
			move++;
			down = true;
			if(!up) {
				down=true;
			}else {
				down= false;
				up = true;
			}
			left = false;
			right = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_R) {
			move=0;
			death = false;
			lengthofsnake=3;
			score=0;
			moveable = true;
			repaint();
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
