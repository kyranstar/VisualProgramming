package core.graphics.lighting;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import core.graphics.imageutil.BlendComposite;
import core.graphics.imageutil.GraphicsUtils;

public class LightMap {

    private BufferedImage lightMap;
    private final int ambientLightLevel;
    private final int width;
    private final int height;

    private final List<Light> dynamicLights = new ArrayList<>();

    public LightMap(final int width, final int height, final int ambientLightLevel, final List<Light> lights) {
        this.width = width;
        this.height = height;
        this.ambientLightLevel = ambientLightLevel;
        setLights(lights);
    }

    public void setLights(final List<Light> lights) {
        lightMap = GraphicsUtils.createImage(width, height, BufferedImage.OPAQUE);

        final Graphics2D g = (Graphics2D) lightMap.getGraphics();

        GraphicsUtils.prettyGraphics(g);

        g.setColor(new Color(ambientLightLevel, ambientLightLevel, ambientLightLevel));
        g.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());

        for (final Light light : lights) {
            drawLight(light, g);
        }
        g.dispose();
    }

    private void drawLight(final Light light, final Graphics2D g) {
        g.drawImage(light.image, light.getX(), light.getY(), null);
    }

    public void addDynamicLight(final Light l) {
        dynamicLights.add(l);
    }

    public void draw(final Graphics2D g, final Rectangle2D view) {

        final BufferedImage staticLights = GraphicsUtils.copyImage(lightMap);

        final Graphics2D lightMapGraphics = (Graphics2D) lightMap.getGraphics();
        for (final Light light : dynamicLights) {
            drawLight(light, lightMapGraphics);
        }

        final Composite before = g.getComposite();
        g.setComposite(BlendComposite.MULTIPLY);
        g.drawImage(
                lightMap.getSubimage((int) view.getX(), (int) view.getY(), (int) view.getWidth(),
                        (int) view.getHeight()), 0, 0, null);

        g.setComposite(before);

        dynamicLights.clear();
        lightMap = staticLights;
    }
}
