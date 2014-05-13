package core.object.map;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
		return new GameMap(tiles);
	}
	private static Document getDocumentFromFile(final String filename) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dBuilder.parse(new File(filename));
		doc.getDocumentElement().normalize();
		return doc;
	}
}
