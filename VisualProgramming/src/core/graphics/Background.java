package core.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import machine.GamePanel;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double distance;
	
	public Background(String s, double distance) {
		try{
			this.image = ImageLoader.loadAndBufferImage(s);
			this.distance = distance;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Background(String filename, double ms, int x, int y, int w, int h) {
		try {
			image = ImageLoader.loadAndBufferImage(filename);
			image = image.getSubimage(x, y, w, h);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setRelativePosition(double x, double y) {
		this.x = (-x / distance) % image.getWidth();
		this.y = (-y / distance) % image.getHeight();
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	public void draw(Graphics2D g) {
		for(double x = this.x - image.getWidth(); x < GamePanel.WIDTH; x += image.getWidth()){
			for(double y = this.y - image.getHeight(); y < GamePanel.HEIGHT; y += image.getHeight()){
				g.drawImage(image, (int)x, (int)y, null);
			}
		}
	}
	
}







