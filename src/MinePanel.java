import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MinePanel extends JPanel implements MouseListener{
	
	private int cols = 9, rows = 9, mines = 10, safeCount, flags;
	private int[][] field, cover;
	private boolean start = false, win = false, lose = false;
	private MineFrame frame;
	private boolean running = false;
	private double begin = 0, end = 0, time = 0;
	
	public MinePanel(MineFrame frame) {
		field = new int[rows][cols];
		cover = new int[rows][cols];
		this.frame = frame;
		flags = mines;
		safeCount = cols * rows - mines;
		addMouseListener(this);
	}
	
	/*
	 * Creates visual of the game
	 */
	public void paint(Graphics g) {
		//Background
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 800, 800);
		
		Assets.init();
		//Top
		g.setColor(Color.BLACK);
		g.fillRect(15, 10, 45, 25);
		g.fillRect(120, 10, 45, 25);
		g.setFont(new Font("Impact", Font.BOLD, 20));
		g.setColor(Color.RED);
		g.drawString(""+flags, 20, 30);
		g.drawString(""+(int)Math.round(time/10)/100, 125, 30);
		if(win) {
			g.drawImage(Assets.win, 80, 15, null);
		}
		else if(lose) {
			g.drawImage(Assets.lose, 80, 15, null);
		}
		else {
			g.drawImage(Assets.smile, 80, 15, null);
		}
		
		//Tiles
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				int covered = cover[i][j];
				if(covered == 0) {
					g.drawImage(Assets.cover, j*20, 50+i*20, null);
				}
				else if(covered == -1) {
					g.drawImage(Assets.flag, j*20, 50+i*20, null);
				}
				else {
					int cell = field[i][j];
					if(cell == -1) {
						g.drawImage(Assets.mine, j*20, 50+i*20, null);
					}
					else if(cell == -2) {
						g.drawImage(Assets.explode, j*20, 50+i*20, null);
					}
					else if(cell == -3) {
						g.drawImage(Assets.x, j*20, 50+i*20, null);
					}
					else if(cell == 1) {
						g.drawImage(Assets.one, j*20, 50+i*20, null);
					}
					else if(cell == 2) {
						g.drawImage(Assets.two, j*20, 50+i*20, null);
					}
					else if(cell == 3) {
						g.drawImage(Assets.three, j*20, 50+i*20, null);
					}
					else if(cell == 4) {
						g.drawImage(Assets.four, j*20, 50+i*20, null);
					}
					else if(cell == 5) {
						g.drawImage(Assets.five, j*20, 50+i*20, null);
					}
					else if(cell == 6) {
						g.drawImage(Assets.six, j*20, 50+i*20, null);
					}
					else if(cell == 7) {
						g.drawImage(Assets.seven, j*20, 50+i*20, null);
					}
					else if(cell == 8) {
						g.drawImage(Assets.eight, j*20, 50+i*20, null);
					}
					else {
						g.drawImage(Assets.blank, j*20, 50+i*20, null);
					}
				}
			}
		}
	}
	
	/*
	 * Places the mines in random locations of the grid
	 */
	public void setMines(int tileX, int tileY) {
		field[tileY][tileX] = -1;
		Random rand = new Random();
		for(int i = 0; i < mines; i++) {
			boolean mineSet = false;
			while(!mineSet) {
				int x = rand.nextInt(cols);
				int y = rand.nextInt(rows);
				if (field[y][x] == 0) {
					field[y][x] = -1;
					mineSet = true;
				}
			}
		}
		field[tileY][tileX] = 0;
		setNums();
	}
	
	/*
	 * Sets numbers near the mines
	 */
	public void setNums() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(field[i][j] == -1) {
					if(j != 0 && field[i][j-1] != -1) {
						field[i][j-1]++;
					}
					if(j != 0 && i != rows-1 && field[i+1][j-1] != -1) {
						field[i+1][j-1]++;
					}
					if(i != rows-1 && field[i+1][j] != -1) {
						field[i+1][j]++;
					}
					if(i != rows-1 && j != cols-1 && field[i+1][j+1] != -1) {
						field[i+1][j+1]++;
					}
					if(j != cols-1 && field[i][j+1] != -1) {
						field[i][j+1]++;
					}
					if(i != 0 && j != cols-1 && field[i-1][j+1] != -1) {
						field[i-1][j+1]++;
					}
					if(i != 0 && field[i-1][j] != -1) {
						field[i-1][j]++;
					}
					if(i != 0 && j != 0 && field[i-1][j-1] != -1) {
						field[i-1][j-1]++;
					}
				}
			}
		}
	}
	
	/*
	 * Clears the grid and starts a new game
	 */
	public void reset() {
		field = new int[rows][cols];
		cover = new int[rows][cols];
		start = false;
		win = false;
		lose = false;
		flags = mines;
		safeCount = cols * rows - mines;
		time = 0;
		repaint();
	}
	
	/*
	 * Prints the map in text
	 * Used only for testing
	 */
	private void print() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				int cell = field[i][j];
				if(cell != -1) {
					System.out.print(cell+" ");
				}
				else {
					System.out.print("X ");
				}
			}
			System.out.println("");
		}
	}
	
	/*
	 * Click on the face to change the difficulty
	 * Click on the tile to uncover the tile
	 */
	public void click(int x, int y) {
		if(x >= 80 && x <= 100 && y >= 15 && y <= 35) {
			String[] difficulty = {"Beginner", "Intermediate", "Expert"};
			int setting = JOptionPane.showOptionDialog(null, null, "What difficulty would you like?", JOptionPane.NO_OPTION,
	                JOptionPane.QUESTION_MESSAGE, null,difficulty,difficulty[0]);
			frame.setDifficulty(difficulty[setting]);
			setDifficulty(difficulty[setting]);
		}
		if(y >= 50 && !lose && !win) {
			int tileX = x / 20;
			int tileY = (y - 50) / 20;
			int cell = cover[tileY][tileX];
			if(cell == 0) {
				uncover(tileX, tileY);
			}
			else if(cell == 1 && field[tileY][tileX] > 0) {
				check(tileX, tileY);
			}
		}
	}
	
	/*
	 * Checks surrounding tiles for flagged tiles
	 * if flags match tile number then uncover the surrounding tiles not including flags
	 */
	public void check(int x, int y) {
		int flagged = 0;
		if(x != 0 && cover[y][x-1] == -1) {
			flagged++;
		}
		if(x != 0 && y != rows-1 && cover[y+1][x-1] == -1) {
			flagged++;
		}
		if(y != rows-1 && cover[y+1][x] == -1) {
			flagged++;
		}
		if(y != rows-1 && x != cols-1 && cover[y+1][x+1] == -1) {
			flagged++;
		}
		if(x != cols-1 && cover[y][x+1] == -1) {
			flagged++;
		}
		if(y != 0 && x != cols-1 && cover[y-1][x+1] == -1) {
			flagged++;
		}
		if(y != 0 && cover[y-1][x] == -1) {
			flagged++;
		}
		if(y != 0 && x != 0 && cover[y-1][x-1] == -1) {
			flagged++;
		}
		if(flagged == field[y][x]) {
			uncoverArea(x,y);
		}
	}
	
	/*
	 * Right click a tile to set a flag or unflag
	 */
	public void flag(int x, int y) {
		if(y >= 50 && !lose && !win) {
			int tileX = x / 20;
			int tileY = (y - 50) / 20;
			int cell = cover[tileY][tileX];
			if(cell == 0) {
				cover[tileY][tileX] = -1;
				flags--;
			}
			else if(cell == -1) {
				cover[tileY][tileX] = 0;
				flags++;
			}
			repaint();
		}
	}
	
	/*
	 * When the user wins, all mines are flagged
	 */
	public void flagAll() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(field[i][j] == -1) {
					cover[i][j] = -1;
				}
			}
		}
		repaint();
	}
	
	/*
	 * Uncovers entire grid when player loses
	 */
	public void uncover() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(cover[i][j] == -1 && field[i][j] != -1) {
					field[i][j] = -3;
				}
				cover[i][j] = 1;
			}
		}
		repaint();
	}
	
	/*
	 * Uncovers surrounding tiles recursively
	 */
	public void uncoverArea(int x, int y) {
		if(x != 0 && cover[y][x-1] == 0) {
			uncover(x-1, y);
		}
		if(x != 0 && y != rows-1 && cover[y+1][x-1] == 0) {
			uncover(x-1, y+1);
		}
		if(y != rows-1 && cover[y+1][x] == 0) {
			uncover(x, y+1);
		}
		if(y != rows-1 && x != cols-1 && cover[y+1][x+1] == 0) {
			uncover(x+1, y+1);
		}
		if(x != cols-1 && cover[y][x+1] == 0) {
			uncover(x+1, y);
		}
		if(y != 0 && x != cols-1 && cover[y-1][x+1] == 0) {
			uncover(x+1,y-1);
		}
		if(y != 0 && cover[y-1][x] == 0) {
			uncover(x, y-1);
		}
		if(y != 0 && x != 0 && cover[y-1][x-1] == 0) {
			uncover(x-1, y-1);
		}
	}
	
	/*
	 * Uncovers selected tile
	 * If it is the first tile uncovered set up mines
	 * If tile is a mine the player loses
	 * If tile is the last uncovered tile that is not a mine the player wins
	 */
	public void uncover(int x, int y) {
		if(!start) {
			start = true;
			setMines(x, y);
			begin = System.currentTimeMillis();
		}
		if(field[y][x] == -1) {
			lose = true;
			field[y][x] = -2;
			end = System.currentTimeMillis();
			time = end - begin;
			uncover();
		}
		else if(cover[y][x] == 0){
			safeCount--;
			if(safeCount <= 0) {
				win = true;
				end = System.currentTimeMillis();
				time = end - begin;
				flagAll();
			}
		}
		cover[y][x] = 1;
		if(field[y][x] == 0) {
			uncoverArea(x,y);
		}
		repaint();
	}
	
	/*
	 * Resizes the grid and changes number of mines based on difficulty
	 */
	public void setDifficulty(String difficulty) {
		if(difficulty.equals("Beginner")) {
			cols = 9;
			rows = 9;
			mines = 10;
		}
		else if(difficulty.equals("Intermediate")) {
			cols = 16;
			rows = 16;
			mines = 40;
		}
		else if(difficulty.equals("Expert")){
			cols = 30;
			rows = 16;
			mines = 99;
		}
		reset();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			click(e.getX(), e.getY());
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			flag(e.getX(), e.getY());
		}
	}

}
