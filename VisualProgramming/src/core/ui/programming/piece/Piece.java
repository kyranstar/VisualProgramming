package core.ui.programming.piece;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import core.ui.programming.values.Value;


public abstract class Piece implements Serializable{
	private static final long serialVersionUID = 898344351816270281L;

	private static final Color BACKGROUND = new Color(200,200,200);
	
	protected int x, y, width, height;
	protected Input input;
	protected Output output;
	protected Title title;
	public Piece(final int x, final int y, final int width, final int height, final int inputs, final int outputs){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.input = new Input(inputs, this);
		this.output = new Output(outputs, this);
		this.title = new Title(this.getClass().getSimpleName(), this);
	}
	public abstract void draw(Graphics2D g);
	public final void drawBackground(final Graphics2D g){
		g.setColor(BACKGROUND);
		g.fillRoundRect(x, y, width, height,10,10);
		input.draw(g);
		output.draw(g);
	}
	public final void drawConnections(final Graphics2D g){
		output.drawConnections(g);
	}
	public final void connect(final Piece other, final int outputPort, final int inputPort){
		output.connect(other, outputPort, inputPort);
		update();
	}
	public final void disconnect(final int outputPort){
		output.disconnect(outputPort);
		update();
	}
	public final void update(){
		output.update();
	}
	public abstract Value send(int outputPort);
	public abstract void recieve(int inputPort, Value v);
	public abstract void doubleClicked();
	public final String toString(){
		return this.getClass().getSimpleName();
	}
	public final void setPosition(final Point point) {
		x = point.x;
		y = point.y;
	}
	public final boolean contains(final Point p){
		return (x < p.getX() && 
				y < p.getY() &&
		        x + width > p.getX()  &&
		        y + height > p.getY());
	}
	public final Integer getOutputPortFromPoint(final Point p){
		if(output.contains(p)){
			if(output.ports == 0) {
				return null;
			}
			return output.getOuputFromY(p.y);
		}
		return null;
	}
	public final Integer getInputPortFromPoint(final Point p){
		if(input.contains(p)){
			if(input.ports == 0) {
				return null;
			}

			return input.getInputFromY(p.y);
		}
		return null;
	}
	public final int getX() {
		return x;
	}
	public final int getY() {
		return y;
	}
	public final Point getPointFromOutputPort(final int portSelected) {
		return output.getPointFromPort(portSelected);
	}
	public final Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
}
