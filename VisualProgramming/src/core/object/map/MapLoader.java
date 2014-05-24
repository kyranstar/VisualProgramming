package core.object.map;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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
		int width;
		int height;
		
		final Document doc = getDocumentFromFile(filename);
		
		Element map = (Element) doc.getElementsByTagName("map").item(0);
		width = Integer.parseInt(getAttribute(map, "width"));
		height = Integer.parseInt(getAttribute(map, "height"));
		
		
		NodeList nList = doc.getElementsByTagName("tile");
		tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++){
				Node nNode = nList.item((y * width) + x);	 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Tile tile = null;
					try{
						tile = Tile.getByID(Integer.parseInt(getAttribute(eElement, "gid")));						
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
		NodeList properties = doc.getElementsByTagName("property");
		for(int i = 0;i < properties.getLength(); i++){
			Element property = (Element)properties.item(i);
			if(getAttribute(property, "name").replaceAll("\\s+", "").equalsIgnoreCase("ambientLight")){
				return Integer.parseInt(getAttribute(property, "value"));
			}
	}
		throw new PropertyNotFoundException("Ambient Light property not found");
	}
	private static List<Light> getLights(Document doc){
		List<Light> lights = new ArrayList<Light>();
		
		NodeList objectList = doc.getElementsByTagName("object");
		for(int i = 0; i < objectList.getLength(); i++){
			Node objectNode = objectList.item(i);
			
			if(objectNode.getNodeType() == Node.ELEMENT_NODE){
				Element object = (Element) objectNode;
				lights.add(getLight(object));
			}
		}
		
		return lights;
	}
	private static Light getLight(Element lightElement){
		int x = Integer.parseInt(getAttribute(lightElement, "x"));
		int y = Integer.parseInt(getAttribute(lightElement, "y"));
		
		//average width and height to get radius
		int radius = (int) ((Float.parseFloat(getAttribute(lightElement, "width")) + Float.parseFloat(getAttribute(lightElement, "height"))) / 2); 
	
		NodeList properties = lightElement.getElementsByTagName("property");
		Element colorProperty = null;
		for(int j = 0; j < properties.getLength(); j++){
			Element property = (Element)properties.item(j);
			if(getAttribute(property, "name").equalsIgnoreCase("color")){
				colorProperty = property;
				break;
			}
		}
		
		if(colorProperty == null)
			throw new PropertyNotFoundException("Color property not found");
		String[] colorVals = getAttribute(colorProperty, "value").replaceAll("[()]*", "").split(",");
		
		if(colorVals.length != 3)
			throw new IllegalArgumentException("Color value is not correct pattern");
		
		for(int j = 0; j < colorVals.length; j++)
			colorVals[j] = colorVals[j].trim();
	
		int red = Integer.parseInt(colorVals[0]);
		int green = Integer.parseInt(colorVals[1]);
		int blue = Integer.parseInt(colorVals[2]);
		 return new Light(x + radius/2, y + radius/2, radius, new Color(red,green,blue));
	}
	private static Document getDocumentFromFile(final String filename) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = null;
		doc = dBuilder.parse(new InputSource(MapLoader.class.getResourceAsStream(filename)));
		doc.getDocumentElement().normalize();
		return doc;
	}
	private static String getAttribute(Element element, String attribute){
		String s = element.getAttribute(attribute);
		if(s.equals("")){
			throw new PropertyNotFoundException("Property " + attribute + " not found in element ");
		}
		return s;
	}
}
