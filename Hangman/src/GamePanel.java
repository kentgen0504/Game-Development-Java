import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 500;
	static final int SCREEN_HEIGHT = 400;
	static final int DELAY = 100;
	static final int maxGuess = 5;
	static final String[] word_arr = {"program", "country", "biscuit", "journey", "subject"};
	
	String[] letter = new String[7];	
	boolean[] letterHash = new boolean[26];
	boolean[] letterInc = new boolean[26];
	String[] chosen_word = new String[7];
	
	String wrongAtt = "Wrong Attempt/s: ";
	int guess = 0;
	
	Map<Integer, Character> letterDict = new HashMap<>();
	
	Random random = new Random();
	Timer timer;
	
	boolean word_chosen = false; 
	boolean running = false;
	boolean winning = false;
	
	GamePanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		int pick = random.nextInt(5);
		
		for (int i = 0; i < 7; i++) {
			chosen_word[i] = String.valueOf(word_arr[pick].charAt(i));
			letter[i] = "_";
		}
		
		for (int i=0; i<26;i++) {
			letterHash[i] = false;
			letterInc[i] = false;
			
			char letter = (char) ('a' + i);
			letterDict.put(i, letter);
		}
		
		word_chosen = true;
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if (running) {
			Graphics2D g2d = (Graphics2D) g;
			
			// background
			g.setColor(Color.white);
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			// post
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.black);
			g2d.drawLine(200, 225, 300, 225);
			g2d.drawLine(250, 225, 250, 50);
			g2d.drawLine(325, 50, 250, 50);
			g2d.drawLine(290, 50, 250, 90);
			g2d.drawLine(325, 50, 325, 125);
			
			//chair
			if (guess != maxGuess) {
				g2d.drawRect(300, 185, 50, 40);
			}
			
			// human
			g2d.setStroke(new BasicStroke(2));
			
			if (guess > 3 && guess != maxGuess) {
				// legs
				g2d.drawLine(325, 160, 317, 185);
				g2d.drawLine(325, 160, 333, 185);
			}
			
			if (guess > 2 && guess != maxGuess) {
				// arms
				g2d.drawLine(325, 130, 317, 155);
				g2d.drawLine(325, 130, 333, 155);
			}
			
			if (guess > 1 && guess != maxGuess) {
				// body
				g2d.drawLine(325, 130, 325, 160);
			}
			
			if (guess > 0 && guess != maxGuess) {
				// head
				g2d.setColor(Color.white);
				g2d.fillOval(312, 105, 25, 25);
				g2d.setColor(Color.black);
				g2d.drawOval(312, 105, 25, 25);
			}
			
			if (guess == maxGuess) {
				// dead
				g2d.drawLine(325, 160, 322, 188);
				g2d.drawLine(325, 160, 328, 188);
				
				g2d.drawLine(325, 130, 322, 158);
				g2d.drawLine(325, 130, 328, 158);
				
				g2d.drawLine(325, 130, 325, 160);
				
				g2d.setColor(Color.white);
				g2d.fillOval(304, 112, 25, 25);
				g2d.setColor(Color.black);
				g2d.drawOval(304, 112, 25, 25);
				
			}
			
			
			// letters
			g.setColor(Color.black);
			g.setFont( new Font("Helvetica Bold", Font.PLAIN, 40));
			FontMetrics word = getFontMetrics(g.getFont());
			g.drawString(letter[0], (SCREEN_WIDTH - word.stringWidth(letter[0]))/2 - 150, 300);
			g.drawString(letter[1], (SCREEN_WIDTH - word.stringWidth(letter[1]))/2 - 100, 300);
			g.drawString(letter[2], (SCREEN_WIDTH - word.stringWidth(letter[2]))/2 - 50, 300);
			g.drawString(letter[3], (SCREEN_WIDTH - word.stringWidth(letter[3]))/2, 300);
			g.drawString(letter[4], (SCREEN_WIDTH - word.stringWidth(letter[4]))/2 + 50, 300);
			g.drawString(letter[5], (SCREEN_WIDTH - word.stringWidth(letter[5]))/2 + 100, 300);
			g.drawString(letter[6], (SCREEN_WIDTH - word.stringWidth(letter[6]))/2 + 150, 300);
			
			g.setFont( new Font("Helvetica Bold", Font.PLAIN, 20));
			g.drawString(wrongAtt, 15, 375);
		}
		else {
Graphics2D g2d = (Graphics2D) g;
			
			// background
			g.setColor(Color.white);
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			// post
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.black);
			g2d.drawLine(200, 225, 300, 225);
			g2d.drawLine(250, 225, 250, 50);
			g2d.drawLine(325, 50, 250, 50);
			g2d.drawLine(290, 50, 250, 90);
			g2d.drawLine(325, 50, 325, 125);
			
			//chair
			if (guess != maxGuess) {
				g2d.drawRect(300, 185, 50, 40);
			}
			
			// human
			g2d.setStroke(new BasicStroke(2));
			
			if (guess > 3 && guess != maxGuess) {
				// legs
				g2d.drawLine(325, 160, 317, 185);
				g2d.drawLine(325, 160, 333, 185);
			}
			
			if (guess > 2 && guess != maxGuess) {
				// arms
				g2d.drawLine(325, 130, 317, 155);
				g2d.drawLine(325, 130, 333, 155);
			}
			
			if (guess > 1 && guess != maxGuess) {
				// body
				g2d.drawLine(325, 130, 325, 160);
			}
			
			if (guess > 0 && guess != maxGuess) {
				// head
				g2d.setColor(Color.white);
				g2d.fillOval(312, 105, 25, 25);
				g2d.setColor(Color.black);
				g2d.drawOval(312, 105, 25, 25);
			}
			
			if (guess == maxGuess) {
				// dead
				g2d.drawLine(325, 160, 322, 188);
				g2d.drawLine(325, 160, 328, 188);
				
				g2d.drawLine(325, 130, 322, 158);
				g2d.drawLine(325, 130, 328, 158);
				
				g2d.drawLine(325, 130, 325, 160);
				
				g2d.setColor(Color.white);
				g2d.fillOval(304, 112, 25, 25);
				g2d.setColor(Color.black);
				g2d.drawOval(304, 112, 25, 25);
				
			}
			
			
			// letters
			g.setColor(Color.black);
			g.setFont( new Font("Helvetica Bold", Font.PLAIN, 40));
			FontMetrics word = getFontMetrics(g.getFont());
			g.drawString(letter[0], (SCREEN_WIDTH - word.stringWidth(letter[0]))/2 - 150, 300);
			g.drawString(letter[1], (SCREEN_WIDTH - word.stringWidth(letter[1]))/2 - 100, 300);
			g.drawString(letter[2], (SCREEN_WIDTH - word.stringWidth(letter[2]))/2 - 50, 300);
			g.drawString(letter[3], (SCREEN_WIDTH - word.stringWidth(letter[3]))/2, 300);
			g.drawString(letter[4], (SCREEN_WIDTH - word.stringWidth(letter[4]))/2 + 50, 300);
			g.drawString(letter[5], (SCREEN_WIDTH - word.stringWidth(letter[5]))/2 + 100, 300);
			g.drawString(letter[6], (SCREEN_WIDTH - word.stringWidth(letter[6]))/2 + 150, 300);
			
			g.setFont( new Font("Helvetica Bold", Font.PLAIN, 20));
			g.drawString(wrongAtt, 15, 375);
			g.setColor(Color.black);
			g.setFont( new Font("Helvetica Bold", Font.PLAIN, 30));
			
			if (winning) {
				g.drawString("YOU WIN!", 50, 115);
			}
			else {
				g.drawString("YOU LOSE!", 50, 115);
			}
		}
	}
	
	public void letCheck() {
		for (int i=0; i<26; i++) {
			if (letterHash[i] && !letterInc[i]) {
				boolean found = false;
				for (String element : chosen_word) {
				  if (element.equals(String.valueOf(letterDict.get(i)))) {
				    found = true;
				    break;
				  }
				}
				
				if (found) {
					for (int j=0;j<7;j++) {
						if (chosen_word[j].equals(String.valueOf(letterDict.get(i)))) {
							letter[j] = chosen_word[j].toUpperCase();	
						}
					}
				}
				else {
					guess++;
					if (guess != maxGuess) {
						wrongAtt += String.valueOf(letterDict.get(i)).toUpperCase() + ", ";
					}
					else {
						wrongAtt += String.valueOf(letterDict.get(i)).toUpperCase();					
					}
					
				}
				
				letterInc[i] = true;
			}
		}
	}
	
	public void winCheck() {
		if (guess == maxGuess) {
			running = false;
		}
		
		boolean win = true;
		for (int i = 0; i<7;i++) {
			if(letter[i].equals("_")) {
				win = false;
			}
		}
		
		if (win) {
			running = false;
			winning = true;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			letCheck();
			winCheck();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_A:
					letterHash[0] = true;
					break;
				case KeyEvent.VK_B:
					letterHash[1] = true;
					break;
				case KeyEvent.VK_C:
					letterHash[2] = true;
					break;
				case KeyEvent.VK_D:
					letterHash[3] = true;
					break;
				case KeyEvent.VK_E:
					letterHash[4] = true;
					break;
				case KeyEvent.VK_F:
					letterHash[5] = true;
					break;
				case KeyEvent.VK_G:
					letterHash[6] = true;
					break;
				case KeyEvent.VK_H:
					letterHash[7] = true;
					break;
				case KeyEvent.VK_I:
					letterHash[8] = true;
					break;
				case KeyEvent.VK_J:
					letterHash[9] = true;
					break;
				case KeyEvent.VK_K:
					letterHash[10] = true;
					break;
				case KeyEvent.VK_L:
					letterHash[11] = true;
					break;
				case KeyEvent.VK_M:
					letterHash[12] = true;
					break;
				case KeyEvent.VK_N:
					letterHash[13] = true;
					break;
				case KeyEvent.VK_O:
					letterHash[14] = true;
					break;
				case KeyEvent.VK_P:
					letterHash[15] = true;
					break;
				case KeyEvent.VK_Q:
					letterHash[16] = true;
					break;
				case KeyEvent.VK_R:
					letterHash[17] = true;
					break;
				case KeyEvent.VK_S:
					letterHash[18] = true;
					break;
				case KeyEvent.VK_T:
					letterHash[19] = true;
					break;
				case KeyEvent.VK_U:
					letterHash[20] = true;
					break;
				case KeyEvent.VK_V:
					letterHash[21] = true;
					break;
				case KeyEvent.VK_W:
					letterHash[22] = true;
					break;
				case KeyEvent.VK_X:
					letterHash[23] = true;
					break;
				case KeyEvent.VK_Y:
					letterHash[24] = true;
					break;
				case KeyEvent.VK_Z:
					letterHash[25] = true;
					break;
			}
		}
	}
}
	