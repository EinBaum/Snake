package snake;

import game.Field;
import game.FieldEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

/**
 *
 * @author EinBaum
 */
public class GUI extends JFrame {
	
	private final int scale;
	private final Field<GraphicsFieldObject> field;
	
	private final Image image;
	
	private int mouseX, mouseY;
	
	public GUI(String title, Field<GraphicsFieldObject> field, int scale) {
		super(title);
		
		int width = field.getWidth()*scale;
		int height = field.getHeight()*scale;
		
		this.scale = scale;
		this.field = field;
		this.image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		image.getGraphics().fillRect(0, 0, width, height);
		
		this.field.addEventListener(evt -> {
			drawEvent(evt);
			repaint();
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt)
			{		
				setLocation(evt.getXOnScreen()-mouseX, evt.getYOnScreen()-mouseY);
			}
		});
		
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setVisible(true);
	}

	private void drawEvent(FieldEvent<GraphicsFieldObject> evt) {
		Graphics g = image.getGraphics();
		if (evt.getNewElement() == null) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(evt.getNewElement().getColor());
		}
		g.fillRect(evt.getX()*scale, evt.getY()*scale, scale, scale);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
}
