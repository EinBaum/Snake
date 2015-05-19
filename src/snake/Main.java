package snake;

import snake.ai.AI;
import snake.ai.StupidAI;
import game.Field;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws Exception {
		int choice = JOptionPane.showOptionDialog(null, "Choose Player:", "Snake",
				0, JOptionPane.PLAIN_MESSAGE, null, new String[] {"User", "AI"}, null);
		
		if (choice == 0 || choice == 1) {
			Field field	= new Field(20, 20);
			AI ai		= (choice == 1) ? new StupidAI() : null;
			GUI gui		= new GUI("Snake", field, 30);
			
			Game game = new Game(field, ai, gui);
			game.start();
		}
	}
}