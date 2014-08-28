package core.level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import core.entity.AbstractEntity;
import core.input.KeyControllable;
import core.input.MouseControllable;
import core.math.Vec2D;
import core.object.map.GameMap;
import core.object.map.MapLoader;
import core.object.map.MapViewport;

public abstract class AbstractLevel implements KeyControllable, MouseControllable {
    protected LevelManager levelManager;

    private Vec2D gravityForce;
    protected final List<AbstractEntity> entities = new LinkedList<AbstractEntity>();
    private final List<KeyControllable> keyControllableEntities = new ArrayList<KeyControllable>();
    private final List<MouseControllable> mouseControllableEntities = new ArrayList<MouseControllable>();
    private MapViewport mapViewport;
    private GameMap map;

    protected AbstractLevel(final String filename, final int screenWidth, final int screenHeight,
            final LevelManager levelManager) {
        this.levelManager = levelManager;
        try {
            setMap(MapLoader.loadMap(filename));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Logger.getLogger(AbstractLevel.class.getName()).log(Level.SEVERE, null, e);
        }
        setMapViewport(new MapViewport(screenWidth, screenHeight));
        reset(); // not required, but safer
    }

    public void addEntity(final AbstractEntity e) {
        synchronized (entities) {
            entities.add(e);
            if (e instanceof KeyControllable) {
                keyControllableEntities.add((KeyControllable) e);
            }
            if (e instanceof MouseControllable) {
                mouseControllableEntities.add((MouseControllable) e);
            }
        }
    }

    protected void resetEntities() {
        entities.clear();
        keyControllableEntities.clear();
        mouseControllableEntities.clear();
    }

    protected List<AbstractEntity> getAllEntities() {
        synchronized (entities) {
            return entities;
        }
    }

    protected List<KeyControllable> getKeyControllableEntities() {
        return keyControllableEntities;
    }

    protected List<MouseControllable> getMouseControllableEntities() {
        return mouseControllableEntities;
    }

    public abstract void draw(Graphics2D g, BufferedImage image);

    public abstract void update();

    public abstract void reset();

    public GameMap getMap() {
        return map;
    }

    public void setMap(final GameMap map) {
        this.map = map;
    }

    public Vec2D getGravityForce() {
        return gravityForce;
    }

    public void setGravityForce(final Vec2D ambientForce) {
        gravityForce = ambientForce;
    }

    public MapViewport getMapViewport() {
        return mapViewport;
    }

    public void setMapViewport(MapViewport mapViewport) {
        this.mapViewport = mapViewport;
    }
}
