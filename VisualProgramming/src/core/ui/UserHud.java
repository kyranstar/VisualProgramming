package core.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import machine.GamePanel;
import core.ui.programming.ProgrammingSpace;

public class UserHud {
	private ProgrammingSpace space;
	private static final int PROGRAMMING_SPACE_DEFAULT_WIDTH = 550;
	private static final int PROGRAMMING_SPACE_DEFAULT_HEIGHT = 200;
	private static final int BUFFER_SPACE = 50;
	
	public UserHud(final int opacity, final GamePanel panel){
		space = new ProgrammingSpace(new Rectangle(BUFFER_SPACE, 
							GamePanel.HEIGHT - PROGRAMMING_SPACE_DEFAULT_HEIGHT - BUFFER_SPACE, 
							PROGRAMMING_SPACE_DEFAULT_WIDTH,
							PROGRAMMING_SPACE_DEFAULT_HEIGHT), 
							opacity, panel);
	}
	public final void draw(final Graphics2D g){
		space.draw(g);
	}
	public final void update(){
		space.update();
	}
	public final void keyReleased(final KeyEvent e) {
		space.keyReleased(e);
	}
	public final void keyPressed(final KeyEvent e) {
		space.keyPressed(e);
	}
	public final void mouseClicked(final MouseEvent e) {
		space.mouseClicked(e);
	}
	public final void mousePressed(final MouseEvent e) {
		space.mousePressed(e);
	}
	public final void mouseDragged(final MouseEvent e) {
		space.mouseDragged(e);
	}
	public final void mouseReleased(final MouseEvent e) {
		space.mouseReleased(e);
	}
}
