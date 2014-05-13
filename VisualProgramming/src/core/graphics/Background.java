package core.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import machine.GamePanel;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double velX;
	private double velY;
	private double scaleX;
	private double scaleY;
	
	public Background(String s) {
		this(s, 0.1);
	}
	
	public Background(String s, double scale) {
		this(s, scale, scale);
	}
	
	public Background(String s, double scaleX, double scaleY) {
		try{
			this.image = ImageLoader.loadImage(s);
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Background(String filename, double ms, int x, int y, int w, int h) {
		try {
			image = ImageLoader.loadImage(filename);
			image = image.getSubimage(x, y, w, h);
			scaleX = ms;
			scaleY = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * scaleX) % image.getWidth();
		this.y = (y * scaleY) % image.getHeight();
	}
	
	public void setVel(double dx, double dy) {
		this.velX = dx;
		this.velY = dy;
	}
	
	public void setScale(double xscale, double yscale) {
		this.scaleX = xscale;
		this.scaleY = yscale;
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	
	public void update() {
		x += velX;
		while(x <= -image.getWidth()) x += image.getWidth();
		while(x >= image.getWidth()) x -= image.getWidth();
		y += velY;
		while(y <= -image.getHeight()) y += image.getHeight();
		while(y >= image.getHeight()) y -= image.getHeight();
	}
	
	public void draw(Graphics2D g) {
		for(double x = this.x - image.getWidth(); x < GamePanel.WIDTH; x += image.getWidth()){
			for(double y = this.y - image.getHeight(); y < GamePanel.HEIGHT; y += image.getHeight()){
				g.drawImage(image, (int)x, (int)y, null);
			}
		}
	}
	
}







