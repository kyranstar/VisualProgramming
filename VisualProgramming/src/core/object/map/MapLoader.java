package core.object.map;

import java.awt.Color;
import java.awt.Point;
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
    private MapLoader() {
    }

    public static GameMap loadMap(final String filename) throws ParserConfigurationException, SAXException, IOException {
        Tile[][] tiles;
        int width;
        int height;

        final Document doc = getDocumentFromFile(filename);

        final Element map = (Element) doc.getElementsByTagName("map").item(0);
        width = Integer.parseInt(getAttribute(map, "width"));
        height = Integer.parseInt(getAttribute(map, "height"));

        final NodeList nList = doc.getElementsByTagName("tile");
        tiles = new Tile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Node nNode = nList.item((y * width) + x);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    final Element eElement = (Element) nNode;
                    Tile tile = null;
                    try {
                        tile = Tile.getByID(Integer.parseInt(getAttribute(eElement, "gid")));
                    } catch (final TileNotFoundException e) {
                        throw new TileNotFoundException("X: " + x + " Y: " + y + " ", e);
                    }
                    tiles[x][y] = tile;
                }
            }
        }
        return new GameMap(tiles, getLights(doc), getAmbientLight(doc));
    }

    private static int getAmbientLight(final Document doc) {
        final NodeList properties = doc.getElementsByTagName("property");
        for (int i = 0; i < properties.getLength(); i++) {
            final Element property = (Element) properties.item(i);
            if (getAttribute(property, "name").replaceAll("\\s+", "").equalsIgnoreCase("ambientLight"))
                return Integer.parseInt(getAttribute(property, "value"));
        }
        throw new PropertyNotFoundException("Ambient Light property not found");
    }

    private static List<Light> getLights(final Document doc) {
        final List<Light> lights = new ArrayList<Light>();

        final NodeList objectList = doc.getElementsByTagName("object");
        for (int i = 0; i < objectList.getLength(); i++) {
            final Node objectNode = objectList.item(i);

            if (objectNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element object = (Element) objectNode;
                if (object.getElementsByTagName("ellipse").getLength() != 0) {
                    lights.add(getEllipseLight(object));
                } else if (object.getElementsByTagName("polygon").getLength() != 0) {
                    lights.add(getPolygonLight(object));
                }
            }
        }

        return lights;
    }

    private static Light getPolygonLight(final Element lightElement) {
        final int x = Integer.parseInt(getAttribute(lightElement, "x"));
        final int y = Integer.parseInt(getAttribute(lightElement, "y"));

        final String pointsAttribute = getAttribute((Element) lightElement.getElementsByTagName("polygon").item(0),
                "points");
        final String[] pointArray = pointsAttribute.split(" ");
        final List<Point> points = new ArrayList<>();
        for (final String s : pointArray) {
            points.add(parsePoint(s));
        }

        final NodeList properties = lightElement.getElementsByTagName("property");
        Element colorProperty = null;
        Element originProperty = null;
        Element exitProperty = null;
        for (int j = 0; j < properties.getLength(); j++) {
            final Element property = (Element) properties.item(j);
            if (getAttribute(property, "name").equalsIgnoreCase("color")) {
                colorProperty = property;
            } else if (getAttribute(property, "name").equalsIgnoreCase("origin")) {
                originProperty = property;
            } else if (getAttribute(property, "name").equalsIgnoreCase("exit")) {
                exitProperty = property;
            }
        }
        if (colorProperty == null || originProperty == null || exitProperty == null)
            throw new PropertyNotFoundException("Color property not found");

        final Color color = parseColor(getAttribute(colorProperty, "value"));
        final Point origin = parsePoint(getAttribute(originProperty, "value"));
        final Point exit = parsePoint(getAttribute(exitProperty, "value"));

        return Light.createPolygonLight(origin, exit, x, y, points, color);
    }

    private static Point parsePoint(final String attribute) {
        final String[] pointVals = attribute.replaceAll("[()]*", "").split(",");
        if (pointVals.length != 2) throw new IllegalArgumentException("Point value is not correct pattern");
        for (int j = 0; j < pointVals.length; j++) {
            pointVals[j] = pointVals[j].trim();
        }
        return new Point(Integer.parseInt(pointVals[0]), Integer.parseInt(pointVals[1]));
    }

    private static Light getEllipseLight(final Element lightElement) {
        final int x = Integer.parseInt(getAttribute(lightElement, "x"));
        final int y = Integer.parseInt(getAttribute(lightElement, "y"));

        // average width and height to get radius
        final int radius = (int) ((Float.parseFloat(getAttribute(lightElement, "width")) + Float
                .parseFloat(getAttribute(lightElement, "height"))) / 2);

        final NodeList properties = lightElement.getElementsByTagName("property");
        Element colorProperty = null;
        for (int j = 0; j < properties.getLength(); j++) {
            final Element property = (Element) properties.item(j);
            if (getAttribute(property, "name").equalsIgnoreCase("color")) {
                colorProperty = property;
                break;
            }
        }

        if (colorProperty == null) throw new PropertyNotFoundException("Color property not found");
        final Color color = parseColor(getAttribute(colorProperty, "value"));
        return Light.createRoundLight(x + radius / 2, y + radius / 2, radius, color);
    }

    private static Color parseColor(final String color) {
        final String[] colorVals = color.replaceAll("[()]*", "").split(",");

        if (colorVals.length != 3) throw new IllegalArgumentException("Color value is not correct pattern");

        for (int j = 0; j < colorVals.length; j++) {
            colorVals[j] = colorVals[j].trim();
        }

        final int red = Integer.parseInt(colorVals[0]);
        final int green = Integer.parseInt(colorVals[1]);
        final int blue = Integer.parseInt(colorVals[2]);
        return new Color(red, green, blue);
    }

    private static Document getDocumentFromFile(final String filename) throws ParserConfigurationException,
            SAXException, IOException {
        final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = null;
        doc = dBuilder.parse(new InputSource(MapLoader.class.getResourceAsStream(filename)));
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static String getAttribute(final Element element, final String attribute) {
        final String s = element.getAttribute(attribute);
        if (s.equals("")) throw new PropertyNotFoundException("Property " + attribute + " not found in element ");
        return s;
    }
}
