package core.object.map;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.graphics.lighting.Light;
import core.object.Tile;
import core.object.TileNotFoundException;

/*
 * In charge of producing maps from file
 */
public final class MapLoader {
	private MapLoader(){}
	
	public static GameMap loadMap(final String filename) throws ParserConfigurationException, SAXException, IOException{
		Tile[][] tiles;
		int width, height;
		
		Document doc = getDocumentFromFile(filename);
		
		Element map = (Element) doc.getElementsByTagName("map").item(0);
		width = Integer.parseInt(map.getAttribute("width"));
		height = Integer.parseInt(map.getAttribute("height"));
		
		
		NodeList nList = doc.getElementsByTagName("tile");
		tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++){
				Node nNode = nList.item((y * width) + x);	 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Tile tile = null;
					try{
						tile = Tile.getByID(Integer.parseInt(eElement.getAttribute("gid")));						
					}catch (TileNotFoundException e){
						throw new TileNotFoundException("X: " + x + " Y: " + y + " ",e);
					}
					tiles[x][y] = tile;
				}
			}
		}
		return new GameMap(tiles, getLights(doc), getAmbientLight(doc));
	}
	private static int getAmbientLight(Document doc){
		return Integer.parseInt(((Element)doc.getElementsByTagName("property").item(0)).getAttribute("value"));
	}
	private static List<Light> getLights(Document doc){
		List<Light> lights = new ArrayList<Light>();
		
		NodeList objectList = doc.getElementsByTagName("object");
		for(int i = 0; i < objectList.getLength(); i++){
			Node objectNode = objectList.item(i);
			
			if(objectNode.getNodeType() == Node.ELEMENT_NODE){
				Element object = (Element) objectNode;
				int x = Integer.parseInt(object.getAttribute("x"));
				int y = Integer.parseInt(object.getAttribute("y"));
				
				//average width and height to get radius
				int radius = (int) ((Float.parseFloat(object.getAttribute("width")) + Float.parseFloat(object.getAttribute("height"))) / 2); 
			
				String[] colorVals = ((Element)object.getElementsByTagName("property").item(0)).getAttribute("value").split(",");
				for(int j = 0; j < colorVals.length; j++)
					colorVals[j] = colorVals[j].trim();
			
				int r = Integer.parseInt(colorVals[0]);
				int g = Integer.parseInt(colorVals[1]);
				int b = Integer.parseInt(colorVals[2]);
				lights.add(new Light(x + radius/2, y + radius/2, radius, new Color(r,g,b)));
			}
		}
		
		return lights;
	}
	private static Document getDocumentFromFile(final String filename) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dBuilder.parse(new File(filename));
		doc.getDocumentElement().normalize();
		return doc;
	}
}
