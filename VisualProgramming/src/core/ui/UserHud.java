package core.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import machine.GamePanel;
import core.ui.programming.ProgrammingSpace;

public class UserHud {
	private ProgrammingSpace space;
	
	public UserHud(Rectangle pos, GamePanel panel){
		space = new ProgrammingSpace(pos, panel);
	}
	public void draw(Graphics2D g){
		space.draw(g);
	}
	public void update(){
		space.update();
	}
	public void keyReleased(KeyEvent e) {
		space.keyReleased(e);
	}
	public void keyPressed(KeyEvent e) {
		space.keyPressed(e);
	}
	public void mouseClicked(MouseEvent e) {
		space.mouseClicked(e);
	}
	public void mousePressed(MouseEvent e) {
		space.mousePressed(e);
	}
	public void mouseDragged(MouseEvent e) {
		space.mouseDragged(e);
	}
	public void mouseReleased(MouseEvent e) {
		space.mouseReleased(e);
	}
}
