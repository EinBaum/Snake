package snake;

import snake.ai.AI;
import snake.objects.Food;
import game.Field;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author EinBaum
 */
public class Game {
	
	private final AI ai;
	private final Field<GraphicsFieldObject> field;
	private final GUI gui;
	
	private final Random random;
	private boolean gameEnd;
	private long tickTime;
	private final long tickTimeMinimum;
	private long score;
	
	private final Snake snake;
	private Food food;
	
	
	public Game(Field field, AI ai, GUI gui) {
		this.field = field;
		this.ai = ai;
		this.gui = gui;
		
		this.random = new Random();
		this.gameEnd = false;
		this.tickTime        = (ai == null ? 200 : 50);
		this.tickTimeMinimum = (ai == null ? 50  : 25);
		this.score = 0;
		
		this.snake = new Snake(field, 2);
		this.food = putFood();
		
		if (ai == null) {
			gui.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					Direction dir;
					switch(e.getKeyCode()) {
						case KeyEvent.VK_DOWN:
							dir = Direction.DOWN; break;
						case KeyEvent.VK_UP:
							dir = Direction.UP; break;
						case KeyEvent.VK_LEFT:
							dir = Direction.LEFT; break;
						case KeyEvent.VK_RIGHT:
							dir = Direction.RIGHT; break;
						default:
							dir = null; break;
					}
					if (dir != snake.getDirection()
							&& dir != snake.getLastDirection().opposite()) {
						snake.setDirection(dir);
					}
				}
			});
		}
	}

	public void start() throws InterruptedException {
		while (!gameEnd) {
			step();
			Thread.sleep(tickTime);
		}
	}
	
	private void step() {
		if (ai != null) {
			snake.setDirection(ai.getDirection(field, food, snake));
		}
		moveSnake();
	}
	
	private Food putFood() {
		int randomX, randomY;
		do {
			randomX = random.nextInt(field.getWidth());
			randomY = random.nextInt(field.getHeight());
		} while (field.get(randomX, randomY) != null);
		Food newFood = new Food();
		field.set(randomX, randomY, newFood);
		return newFood;
	}
	
	private void moveSnake() {
		Snake.MoveResult moveResult = snake.tryMove();
		switch (moveResult) {
			case CRASHED_SELF:
				gameEnd = true;
				JOptionPane.showMessageDialog(gui,
						"Crashed into yourself!\nScore: " + score,
						"Game Over", JOptionPane.PLAIN_MESSAGE);
				break;
				
			case CRASHED_WALL:
				gameEnd = true;
				JOptionPane.showMessageDialog(gui,
						"Crashed into a wall!\nScore: " + score,
						"Game Over", JOptionPane.PLAIN_MESSAGE);
				break;
				
			case ATE_FOOD:
				
				this.score++;
				
				if (tickTime > tickTimeMinimum) {
					tickTime *= 0.95;
				}
				
				if (snake.getLength() == field.getWidth()*field.getHeight()) {
					gameEnd = true;
					JOptionPane.showMessageDialog(gui,
							"You win the game!\nScore: " + score,
							"You win!", JOptionPane.PLAIN_MESSAGE);
				} else {
					this.food = putFood();
				}
				break;
				
			case MOVED:
				break;
			default:
				break;
		}
	}
}
