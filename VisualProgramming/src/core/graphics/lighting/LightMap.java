package core.graphics.lighting;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

import core.graphics.BlendComposite;

public class LightMap {
	private final Color TRANSPARENT = new Color(0,0,0,0);
	
	private BufferedImage lightMap;
	private int ambientLightLevel;
	
	public LightMap(final int width, final int height, final int ambientLightLevel){
		this.lightMap = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		this.ambientLightLevel = ambientLightLevel;
	}
	public void setLights(List<Light> lights){
		Graphics2D g = (Graphics2D) lightMap.getGraphics();
		
		g.setColor(new Color(ambientLightLevel, ambientLightLevel, ambientLightLevel));
		g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
		
		for(Light light : lights){
			Point2D center = new Point2D.Double(light.getPosition().getX(), light.getPosition().getY());
		     float radius = light.getRadius();
		     float[] dist = {0.05f, 1.0f};
		     Color[] colors = {light.getColor(), TRANSPARENT};
		     RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
		     g.setPaint(p);
		     g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
		}
		
	}
	public void draw(Graphics2D g){
		Composite before = g.getComposite();
		
		g.setComposite(BlendComposite.Multiply);
		g.drawImage(lightMap, 0, 0, null);
		
		g.setComposite(before);
	}
}
