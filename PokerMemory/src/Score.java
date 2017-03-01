
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Score extends JLabel {

	public int score = 0; //Set initial score to 0
	public boolean pass = false; 
	
	static ArrayList buttons = new ArrayList(); //Stores the strings for the buttons 

	public Score() {
		update();
	}
	//Displays the score on the game.
	public void update() {
		setText("Score: " + score);
		setHorizontalTextPosition(JLabel.LEFT);
	}
	//Getter
	//@return returns the score (integer).
	public int getScore() {
		return this.score;
	}
	//@param accepts an amount to update the score 
	public void updateScoreByAmount(int amount) {
		this.score = this.score + amount;
		if (amount == 0){
			pass = true;
		}
		System.out.println("SCORE: " + score);
		update();
	}
	//@param accepts a String, which is the Label of the button.
	public void addToButtons(String buttonName){
		buttons.add(buttonName);
	}
	//@return returns the integer value from the chosen button 
	public int buttonFrame() {
		JFrame frame = new JFrame();
	
		Object[] buttonArray = buttons.toArray();
		int element = JOptionPane.showOptionDialog(frame, "Choose hand/s:", "CHOOSE HAND", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null, buttonArray, null);
		buttons.removeAll(buttons);
		
		if (element == JOptionPane.CLOSED_OPTION){
			return buttonArray.length - 1;
		}
		else{
			return element;
		}
	}
	
	public void gameOver(){
		JFrame gameOverFrame = new JFrame();
		JOptionPane.showMessageDialog(gameOverFrame, "GAME OVER: \nScore: " + this.score);
		SoundClass.win();
	}

}
