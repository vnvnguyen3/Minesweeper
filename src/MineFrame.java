import javax.swing.JFrame;
public class MineFrame extends JFrame{
	
	private MinePanel panel;
	
	public MineFrame() {
		setBounds(600,200,186,265);
		setTitle("Minesweeper");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MinePanel(this);
		add(panel);
	}
	
	/*
	 * Resizes the window according to the difficulty level
	 */
	public void setDifficulty(String difficulty) {
		if(difficulty.equals("Beginner")) {
			setBounds(600,200,186,265);
		}
		else if(difficulty.equals("Intermediate")) {
			setBounds(600,200,326,405);
		}
		else if(difficulty.equals("Expert")){
			setBounds(600,200,606,405);
		}
	}
}
