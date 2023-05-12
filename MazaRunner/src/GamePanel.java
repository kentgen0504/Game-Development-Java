import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 500;
	static final int SCREEN_HEIGHT = 500;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	boolean running = false;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		mazeGenerator();
		startGame();
	}
	
	public void mazeGenerator() {
		boolean[][] path = new boolean[8][8];

		for (int i = 0; i < path.length; i++) {
		    for (int j = 0; j < path[i].length; j++) {
		        path[i][j] = false;
		    }
		}
		
		path[0][0] = true;
		int[] cur_pos = {0,0} ;
		
		int[][] cur_path = new int[100][2];
		cur_path[0][0] = 0;
		cur_path[0][1] = 0;
		
		int pathNum = 0;
		path[0][0] = true;
		boolean path_avail = true;
		
		while (path_avail) {
			int randNum = random.nextInt(4) + 1;
			if (randNum == 1 && cur_pos[1] - 1 >= 0) {
				if (path[]) {
					
				}
			}
		}
	}
	
	public void startGame() {
		running = true;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			g.setColor(Color.white);
			g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			/*WRITE THINGS THAT WILL BE HAPPENING IN THE BACKGROUND*/
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				
			case KeyEvent.VK_RIGHT:
				
			case KeyEvent.VK_UP:
				
			case KeyEvent.VK_DOWN:
				
			}
		}
	}
}