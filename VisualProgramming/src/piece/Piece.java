package piece;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

import values.Value;


public abstract class Piece implements Serializable{
	private static final long serialVersionUID = 898344351816270281L;

	private static final Color background = new Color(200,200,200);
	
	protected int x, y, width, height;
	protected Input input;
	protected Output output;
	protected Title title;
	public Piece(int x, int y, int width, int height, int inputs, int outputs){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.input = new Input(inputs, this);
		this.output = new Output(outputs, this);
		this.title = new Title(this.getClass().getSimpleName(), this);
	}
	public abstract void draw(Graphics2D g);
	public void drawBackground(Graphics2D g){
		g.setColor(background);
		g.fillRoundRect(x, y, width, height,10,10);
		input.draw(g);
		output.draw(g);
	}
	public void drawConnections(Graphics2D g){
		output.drawConnections(g);
	}
	public void connect(Piece other, int outputPort, int inputPort){
		output.connect(other, outputPort, inputPort);
		update();
	}
	public void disconnect(int outputPort){
		output.disconnect(outputPort);
		update();
	}
	public void update(){
		output.update();
	}
	public abstract Value send(int outputPort);
	public abstract void recieve(int inputPort, Value v);
	public abstract void doubleClicked();
	public String toString(){
		return this.getClass().getSimpleName();
	}
	public void setPosition(Point point) {
		x = point.x;
		y = point.y;
	}
	public boolean contains(Point p){
		return (x < p.getX() && 
				y < p.getY() &&
		        x + width > p.getX()  &&
		        y + height > p.getY());
	}
	public Integer getOutputPortFromPoint(Point p){
		if(output.contains(p)){
			if(output.ports == 0)
				return null;
			System.out.println(output.getOuputFromY(p.y));
			return output.getOuputFromY(p.y);
		}
		return null;
	}
	public Integer getInputPortFromPoint(Point p){
		if(input.contains(p)){
			if(input.ports == 0)
				return null;

			System.out.println(input.getInputFromY(p.y));
			return input.getInputFromY(p.y);
		}
		return null;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Point getPointFromOutputPort(int portSelected) {
		return output.getPointFromPort(portSelected);
	}
}
