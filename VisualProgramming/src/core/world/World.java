package core.world;

import java.awt.Graphics2D;

import core.level.LevelManager;

public class World {
	LevelManager levelManager;
	int width, height;
	
	public World(int width, int height){
		this.width = width;
		this.height = height;
		this.levelManager = new LevelManager();
	}
	public void draw(Graphics2D g){
		levelManager.draw(g);
	}
	public void update(){
		levelManager.update();
	}
}
