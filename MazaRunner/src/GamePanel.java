import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 700;
	static final int SCREEN_HEIGHT = 700;
	static final int UNIT_SIZE = 40;
	static final int PLAYER_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 100;
	static final int ROWS = 15;
	static final int COLS = 15;
	
	int[][][] crossable = new int[ROWS][COLS][4];
	int[] player_pos = {50 + (UNIT_SIZE-PLAYER_SIZE)/2, SCREEN_HEIGHT - UNIT_SIZE - 50 + (UNIT_SIZE-PLAYER_SIZE)/2};
	int[] player_cords = {0, COLS - 1};
	
	boolean running = false;
	
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		mazeGenerator();
		startGame();
	}
	
	public void mazeGenerator() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				for (int k = 0; k < 4; k++) {
					crossable[i][j][k] = 0;
				}
			}
		}
		
		boolean[][] explored = new boolean[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
		    for (int j = 0; j < COLS; j++) {
		        explored[i][j] = false;
		    }
		}
		
		explored[0][0] = true;
		
		int[] pos = {0,0};
		int[][] path = new int[ROWS*COLS][2];
		int pathNum = 0;
		
		path[0][0] = 0;
		path[0][1] = 0;
		
		int[] dirNum = {1, 2, 3, 4};
		boolean exploring = true;
		boolean path_found = true;
		
		while (exploring) {
			shuffleArray(dirNum);
			path_found = false;
			/* radar */
			for (int i = 0; i < 4; i++) {
				if (dirNum[i] == 1 && pos[1] - 1 >= 0) {
					if (!explored[pos[0]][pos[1]-1]) {
						crossable[pos[0]][pos[1]][dirNum[i]-1] = 1;
						crossable[pos[0]][pos[1]-1][2] = 1;
						pos[1]--;
						path_found = true;
						break;
					}
				}
				if (dirNum[i] == 2 && pos[0] + 1 <= ROWS - 1) {
					if (!explored[pos[0]+1][pos[1]]) {
						crossable[pos[0]][pos[1]][dirNum[i]-1] = 1;
						crossable[pos[0]+1][pos[1]][3] = 1;
						pos[0]++;
						path_found = true;
						break;
					}
				}
				if (dirNum[i] == 3 && pos[1] + 1 <= COLS - 1) {
					if (!explored[pos[0]][pos[1]+1]) {
						crossable[pos[0]][pos[1]][dirNum[i]-1] = 1;
						crossable[pos[0]][pos[1]+1][0] = 1;
						pos[1]++;
						path_found = true;
						break;
					}
				}
				if (dirNum[i] == 4 && pos[0] - 1 >= 0) {
					if (!explored[pos[0]-1][pos[1]]) {
						crossable[pos[0]][pos[1]][dirNum[i]-1] = 1;
						crossable[pos[0]-1][pos[1]][1] = 1;
						pos[0]--;
						path_found = true;
						break;
					}
				}
			}	
			
			if (path_found) {
				pathNum++;
				explored[pos[0]][pos[1]] = true;
				path[pathNum][0] = pos[0];
				path[pathNum][1] = pos[1];
			}
			else {
				if (pos[0] == 0 && pos[1] == 1) {
					exploring = false;
					System.out.println("Maze Generation: DONE");
				}
				pathNum--;
				pos[0] = path[pathNum][0];
				pos[1] = path[pathNum][1];
			}
			
		}	
	}
	
	public static void shuffleArray(int[] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
	
	public void startGame() {
		
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			g.setColor(Color.green);
			g.fillRect(50, SCREEN_HEIGHT - UNIT_SIZE - 50, UNIT_SIZE, UNIT_SIZE);
			
			g.setColor(Color.red);
			g.fillRect(SCREEN_WIDTH - UNIT_SIZE - 50, 50, UNIT_SIZE, UNIT_SIZE);
			
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++) {
					for (int k = 0; k < 4; k++) {
						g.setColor(Color.BLACK);
						if (crossable[i][j][k] == 0) {
							if (k==0) {
								g.drawLine(i*UNIT_SIZE + 50, j*UNIT_SIZE + 50, (i+1)*UNIT_SIZE + 50, j*UNIT_SIZE + 50);
							}
							if (k==1) {
								g.drawLine((i+1)*UNIT_SIZE + 50, j*UNIT_SIZE + 50, (i+1)*UNIT_SIZE + 50, (j+1)*UNIT_SIZE + 50);
							}
							if (k==2) {
								g.drawLine(i*UNIT_SIZE + 50, (j+1)*UNIT_SIZE + 50, (i+1)*UNIT_SIZE + 50, (j+1)*UNIT_SIZE + 50);
							}
							if (k==3) {
								g.drawLine(i*UNIT_SIZE + 50, j*UNIT_SIZE + 50, i*UNIT_SIZE + 50, (j+1)*UNIT_SIZE + 50);
							}
						}
					}
				}
			}
			
			g.setColor(Color.blue);
			g.fillOval(player_pos[0], player_pos[1], PLAYER_SIZE, PLAYER_SIZE);
		}
		else {
			winScreen(g);
			
		}
	}
	
	public void winScreen(Graphics g) {
		g.setColor(Color.black);
		g.setFont( new Font("Helvetica Bold", Font.PLAIN, 80));
		FontMetrics winFont = getFontMetrics(g.getFont());
		g.drawString("YOU WIN!", (SCREEN_WIDTH - winFont.stringWidth("YOU WIN!"))/2, SCREEN_HEIGHT/2);
	}
	
	public void winCheck(){
		if (player_cords[0] == COLS - 1 && player_cords[1] == 0) {
			running = false;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			winCheck();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(crossable[player_cords[0]][player_cords[1]][3] == 1) {
					player_cords[0]--;
					player_pos[0] -= UNIT_SIZE;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(crossable[player_cords[0]][player_cords[1]][1] == 1) {
					player_cords[0]++;
					player_pos[0] += UNIT_SIZE;
				}
				break;
			case KeyEvent.VK_UP:
				if(crossable[player_cords[0]][player_cords[1]][0] == 1) {
					player_cords[1]--;
					player_pos[1] -= UNIT_SIZE;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(crossable[player_cords[0]][player_cords[1]][2] == 1) {
					player_cords[1]++;
					player_pos[1] += UNIT_SIZE;
				}
				break;
			}
		}
	}
}