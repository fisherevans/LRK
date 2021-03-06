package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import shader.Shader;
import sun.security.krb5.internal.KRBCred;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LightManager
{
    private AdventureState _state;
    private ArrayList<Light> _lights, _toRemove;
    private Image _lightBuffer, _mapBuffer, _blurBuffer;
    private Graphics _lightGfx, _mapGfx, _blurGfx;
    private Color _ambientLight;
    private Shader _blurH, _blurV;

    private int _lightsMapIndex, _shadowMapIndex;
    private ArrayList<ShadowLine> _shadowLines;

    private Image _vignette;

    private boolean userShaders = true;
    private boolean blendLights = true;
    private float shadowBlurSize = 1.5f;

    private final int LIGHT_SIZE = 256;
    private final float DEFAULT_LIGHT_RADIUS = 4f;

    public LightManager(AdventureState parent, Color ambientLight)
    {
        _state = parent;
        _ambientLight = ambientLight;

        _lights = new ArrayList<>();
        _toRemove = new ArrayList<>();

        _shadowLines = new ArrayList<>();

        _vignette = Resources.getImage("gui/states/adventure/vignette");

        try
        {
            _blurH = Shader.makeShader("res/shaders/bassicBlur.vrt", "res/shaders/blurHorz.frg");
            _blurV = Shader.makeShader("res/shaders/bassicBlur.vrt", "res/shaders/blurVert.frg");

            _lightBuffer = new Image(LIGHT_SIZE, LIGHT_SIZE);
            _lightGfx = _lightBuffer.getGraphics();
        }
        catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        resize();

        _lightsMapIndex = _state.getTiledMap().getLayerIndex("lights");
        _shadowMapIndex = _state.getTiledMap().getLayerIndex("shadows");
    }

    public void update(float delta)
    {
        if(_toRemove.size() > 0)
        {
            for(Light light:_toRemove)
                _lights.remove(light);
            _toRemove.clear();
        }

        for(Light light:_lights)
            light.update(delta);
    }

    public void render(Graphics gfx, float xShift, float yShift)
    {
        // CLEAR THE LIGHT FRAME WITH AMBIENT LIGHT
        Graphics.setCurrent(_blurGfx);
        _blurGfx.clear();
        _blurGfx.setColor(_ambientLight);
        _blurGfx.fillRect(0, 0, _blurBuffer.getWidth(), _blurBuffer.getHeight());

        float x, y, fullSize, halfSize;
        Light light;
        for(int id = _lights.size()-1;id >= 0;id--) // FOR EACH LIGHT, GOING BACKWARDS
        {
            light = _lights.get(id);
            if(_state.inRenderArea(light.getPosition(), light.getRadius())) // IF THE LIGHT IS IN THE RENDER AREA
            {
                halfSize = light.getRadius()*AdventureState.TILE_SIZE;
                fullSize = halfSize*2f;
                x = xShift+light.getPosition().x*AdventureState.TILE_SIZE - halfSize;
                y = yShift+light.getPosition().y*AdventureState.TILE_SIZE - halfSize;

                // RENDER THE LIGHT IMAGE TO THE LIGHT BUFFER, THEN DRAW THE SHADOWS
                Graphics.setCurrent(_lightGfx);
                _lightGfx.clear();
                _lightGfx.drawImage(light.getImage().getScaledCopy(_lightBuffer.getWidth(), _lightBuffer.getHeight()), 0, 0, light.getColor());
                paintShadows(_lightGfx, light.getRadius(), (DEFAULT_LIGHT_RADIUS / light.getRadius()) * AdventureState.TILE_SIZE, light.getPosition());
                _lightGfx.flush();

                // DRAW THE LIGHT TO THE FRAME BUFFER
                Graphics.setCurrent(_blurGfx);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                _blurGfx.drawImage(_lightBuffer.getScaledCopy((int) fullSize, (int) fullSize), x, y);//, light.getColor());
                gfx.setDrawMode(Graphics.MODE_NORMAL);
            }
        }
        _blurGfx.flush();

        if(userShaders)
        {
            // BLUR HORIZONTALLY TO THE BLUR BUFFER
            _blurH.startShader();
            _blurH.setUniformFloatVariable("resolution", DisplayManager.getWindowWidth()*shadowBlurSize);
            _blurH.setUniformFloatVariable("radius", DisplayManager.getBackgroundScale()*shadowBlurSize);

            Graphics.setCurrent(_mapGfx);
            _mapGfx.drawImage(_blurBuffer, 0, 0);
            _mapGfx.flush();
            Shader.forceFixedShader();

            // BLUR VERTICALLY TO THE GAME GRAPHICS - THIS TIME APPLY THE BLENDING FUNCTION

            _blurV.startShader();
            _blurV.setUniformFloatVariable("resolution", DisplayManager.getWindowWidth()*shadowBlurSize);
            _blurV.setUniformFloatVariable("radius", DisplayManager.getBackgroundScale()*shadowBlurSize);
            Graphics.setCurrent(gfx);

            if(blendLights)
                GL14.glBlendFuncSeparate(GL11.GL_DST_COLOR, GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ONE);
            gfx.drawImage(_mapBuffer, 0, 0);
            gfx.setDrawMode(Graphics.MODE_NORMAL);
            Shader.forceFixedShader();
        }
        else
        {
            if(blendLights)
                GL14.glBlendFuncSeparate(GL11.GL_DST_COLOR, GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ONE);
            gfx.drawImage(_blurBuffer, 0, 0);
            gfx.setDrawMode(Graphics.MODE_NORMAL);
        }
    }

    public void renderVignette(Graphics gfx, float xShift, float yShift, float size)
    {
        //*/ DRAW THE PRETTY VIGNETTE
        GFX.drawImageCentered(xShift + getState().getEntityManager().getCamera().getX() * AdventureState.TILE_SIZE,
                yShift + getState().getEntityManager().getCamera().getY() * AdventureState.TILE_SIZE,
                size, size,
                _vignette);
        //*/
    }

    private void paintShadows(Graphics gfx, float lightRadius, float pixelPerUnit, Vec2 lightPoint)
    {
        paintShadows(gfx, Color.black, lightRadius, pixelPerUnit, lightPoint);
    }

    private void paintShadows(Graphics gfx, Color color, float lightRadius, float pixelPerUnit, Vec2 lightPoint)
    {
        gfx.setColor(color);

        Rectangle lightRect = new Rectangle(lightPoint.x - lightRadius, lightPoint.y - lightRadius, lightRadius*2f, lightRadius*2f);

        Vec2 pA, pB, pX, pY;
        float[] points = new float[8];
        Polygon fillShape;
        for(ShadowLine line:_shadowLines)
        {
            if(line.getBoundingBox().intersects(lightRect))
            {
                pA = line.getPointA();
                pB = line.getPointB();
                pX = pA.add(pA.sub(lightPoint).mul(lightRadius*1.5f));
                pY = pB.add(pB.sub(lightPoint).mul(lightRadius*1.5f));
                points[0] = (pA.x-lightPoint.x+lightRadius)*pixelPerUnit; points[1] = (pA.y-lightPoint.y+lightRadius)*pixelPerUnit;
                points[2] = (pX.x-lightPoint.x+lightRadius)*pixelPerUnit; points[3] = (pX.y-lightPoint.y+lightRadius)*pixelPerUnit;
                points[4] = (pY.x-lightPoint.x+lightRadius)*pixelPerUnit; points[5] = (pY.y-lightPoint.y+lightRadius)*pixelPerUnit;
                points[6] = (pB.x-lightPoint.x+lightRadius)*pixelPerUnit; points[7] = (pB.y-lightPoint.y+lightRadius)*pixelPerUnit;
                fillShape = new Polygon(points);
                gfx.fill(fillShape);
            }
        }
    }

    public void addLight(Light newLight)
    {
        if(!_lights.contains(newLight))
            _lights.add(newLight);
    }

    public void removeLight(Light oldLight)
    {
        if(_lights.contains(oldLight))
            _toRemove.add(oldLight);
    }

    public void resize()
    {
        try
        {
            _mapBuffer = new Image((int)DisplayManager.getBackgroundWidth()+1, (int)DisplayManager.getBackgroundHeight()+1);
            _mapGfx = _mapBuffer.getGraphics();

            _blurBuffer = new Image((int)DisplayManager.getBackgroundWidth()+1, (int)DisplayManager.getBackgroundHeight()+1);
            _blurGfx = _blurBuffer.getGraphics();
        }
        catch (SlickException e)
        {
            LRK.log("Failed to create the image buffer for th lighting system");
            e.printStackTrace();
            System.exit(89133);
        }
    }

    public void processTile(int x, int y, TiledMap tiledMap)
    {
        processLightTile(x, y, tiledMap.getLocalTileId(x, y, _lightsMapIndex));
        processShadowTile(x, y, tiledMap.getLocalTileId(x, y, _shadowMapIndex));
    }

    private void processLightTile(int x, int y, int id)
    {
        if(id < 0)
            return;

        switch(id)
        {
            case 0: // PLAYER
                Light torchLight = new Light(Resources.getImage("lights/torch"), 3, new Color(1f, 0.9f, 0.75f), new Vec2(x, y), this);
                torchLight.setController("torch");
                addLight(torchLight);
                break;
        }
    }

    private void processShadowTile(int x, int y, int id)
    {
        if(_state.getCollisionManager().getCollisionShapes() != null)
        {
            Shape shape = _state.getCollisionManager().getCollisionShapes().get(id);
            if(shape != null && shape instanceof PolygonShape)
            {
                PolygonShape polygon = ((PolygonShape)shape);
                int delta;
                for(int pointId = 0;pointId < polygon.getVertexCount();pointId++)
                {
                    delta = pointId < polygon.getVertexCount()-1 ? 1 : -pointId;
                    addShadowLine(new ShadowLine(polygon.getVertex(pointId).x + x, polygon.getVertex(pointId).y + y,
                            polygon.getVertex(pointId+delta).x + x, polygon.getVertex(pointId+delta).y + y));
                }
            }
        }
        else
            LRK.log("INIT COLLISION MANAGER FIRST!!!");
    }

    public boolean addShadowLine(ShadowLine line)
    {
        if(_shadowLines.contains(line))
            return false;

        _shadowLines.add(line);
        return true;
    }

    public ArrayList<ShadowLine> getShadowLines()
    {
        return _shadowLines;
    }

    public AdventureState getState()
    {
        return _state;
    }
}
