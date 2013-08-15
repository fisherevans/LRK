package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import shader.Shader;

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
    private AdventureState _parent;
    private ArrayList<Light> _lights, _toRemove;
    private Image _lightBuffer, _lightFrameBuffer, _visibleBuffer;
    private Graphics _lightGfx, _lightFrameGfx, _visibleGfx;
    private Color _ambientLight;
    private Light _cameraLight;
    private Shader _blurH, _blurV;

    private final int LIGHT_SIZE = 256;
    private final float DEFAULT_LIGHT_RADIUS = 4f;
    private final Color LIGHT_BUFFER_CLEAR_COLOR = new Color(1f, 1f, 1f, 0f);

    public LightManager(AdventureState parent, Color ambientLight)
    {
        _parent = parent;
        _ambientLight = ambientLight;
        _lights = new ArrayList<>();
        _toRemove = new ArrayList<>();
        resize();

        try
        {
            _blurH = Shader.makeShader("res/shaders/bassicBlur.vrt", "res/shaders/blurHorz.frg");
            _blurV = Shader.makeShader("res/shaders/bassicBlur.vrt", "res/shaders/blurVert.frg");

            _lightBuffer = new Image(LIGHT_SIZE, LIGHT_SIZE);
            _lightGfx = _lightBuffer.getGraphics();
        } catch (SlickException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
        GL11.glEnable(GL11.GL_BLEND);
        float x, y, fullSize, halfSize;

        _lightFrameGfx.clear();
        _lightFrameGfx.setColor(_ambientLight);
        _lightFrameGfx.fillRect(0, 0, _lightFrameBuffer.getWidth(), _lightFrameBuffer.getHeight());
        Light light;
        for(int id = _lights.size()-1;id >= 0;id--)
        {
            light = _lights.get(id);
            if(_parent.inRenderArea(light.getPosition(), light.getRadius()))
            {
                halfSize = light.getRadius()*AdventureState.TILE_SIZE;
                fullSize = halfSize*2f;

                x = xShift+light.getPosition().x*AdventureState.TILE_SIZE - halfSize;
                y = yShift+light.getPosition().y*AdventureState.TILE_SIZE - halfSize;

                _lightGfx.clear();
                _lightGfx.drawImage(light.getImage().getScaledCopy(_lightBuffer.getWidth(), _lightBuffer.getHeight()), 0, 0);
                removeShadows(_lightGfx, light.getRadius(), (DEFAULT_LIGHT_RADIUS/light.getRadius())*AdventureState.TILE_SIZE, light.getPosition());
                _lightGfx.flush();
                //GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                //GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                gfx.setDrawMode(Graphics.MODE_ADD);
                _lightFrameGfx.drawImage(_lightBuffer.getScaledCopy((int)fullSize, (int)fullSize), x, y);//, light.getColor());
                gfx.setDrawMode(Graphics.MODE_NORMAL);
            }
        }

        _lightFrameGfx.flush();
        //paintBuffer(gfx);
        paintBufferDebug(gfx);

        /*_visibleGfx.clear();
        _visibleGfx.setColor(Color.white);
        _visibleGfx.fillRect(0, 0, _lightFrameBuffer.getWidth(), _lightFrameBuffer.getHeight());
        _visibleGfx.setColor(_ambientLight);
        Rectangle lightRect = new Rectangle(_parent.getCamera().getX() - _parent.getRenderDistance(), _parent.getCamera().getY() - _parent.getRenderDistance(),
                _parent.getRenderDistance()*2, _parent.getRenderDistance()*2);
        float rayLength = new Vec2(lightRect.getX(), lightRect.getY()).sub(new Vec2(lightRect.getCenterX(), lightRect.getCenterY())).length();
        Vec2 pL = _parent.getCamera().getBody().getPosition().clone();
        Vec2 pA, pB, pX, pY;
        float[] points = new float[8];
        Polygon fillShape;
        *//*
          X _______ Y
            \    /
           A \__/ B

              * L
         *//*
        for(ShadowLine line:_parent.getShadowMap().getLines())
        {
            if(line.getBoundingBox().intersects(lightRect))
            {
                pA = line.getPointA();
                pB = line.getPointB();
                pX = pA.add(pA.sub(pL).mul(rayLength));
                pY = pB.add(pB.sub(pL).mul(rayLength));
                points[0] = (pA.x*AdventureState.TILE_SIZE) + xShift; points[1] = (pA.y*AdventureState.TILE_SIZE) + yShift;
                points[2] = (pX.x*AdventureState.TILE_SIZE) + xShift; points[3] = (pX.y*AdventureState.TILE_SIZE) + yShift;
                points[4] = (pY.x*AdventureState.TILE_SIZE) + xShift; points[5] = (pY.y*AdventureState.TILE_SIZE) + yShift;
                points[6] = (pB.x*AdventureState.TILE_SIZE) + xShift; points[7] = (pB.y*AdventureState.TILE_SIZE) + yShift;
                fillShape = new Polygon(points);
                _visibleGfx.fill(fillShape);
                //LRK.log("Filled shape: " + points[0]+","+points[1] + " ; " + points[2]+","+points[3] + " ; " + points[4]+","+points[5] + " ; " + points[6]+","+points[7]);
            }
        }
        _visibleGfx.flush();


        _blurH.startShader();
        _blurH.setUniformFloatVariable("resolution", DisplayManager.getWindowWidth());
        _blurH.setUniformFloatVariable("radius", DisplayManager.getBackgroundScale()*2);

        _blurV.startShader();
        _blurV.setUniformFloatVariable("resolution", DisplayManager.getWindowHeight());
        _blurV.setUniformFloatVariable("radius", DisplayManager.getBackgroundScale()*2);

        GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_ONE_MINUS_SRC_ALPHA);
        gfx.drawImage(_visibleBuffer, 0, 0);
        gfx.setDrawMode(Graphics.MODE_NORMAL);

        Shader.forceFixedShader();*/
    }

    private void removeShadows(Graphics gfx, float lightRadius, float pixelPerUnit, Vec2 lightPoint)
    {
        gfx.setColor(Color.black);

        //GL14.glBlendFuncSeparate(GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ZERO, GL11.GL_SRC_COLOR);
        //GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_SRC_COLOR);

        Rectangle lightRect = new Rectangle(lightPoint.x - lightRadius, lightPoint.y - lightRadius, lightRadius*2f, lightRadius*2f);

        Vec2 pA, pB, pX, pY;
        float[] points = new float[8];
        Polygon fillShape;
        for(ShadowLine line:_parent.getShadowMap().getLines())
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

        //gfx.setDrawMode(Graphics.MODE_NORMAL);
    }

    private void paintBuffer(Graphics gfx)
    {
        GL14.glBlendFuncSeparate(GL11.GL_DST_COLOR, GL11.GL_ZERO, GL11.GL_ONE, GL11.GL_ONE);
        //GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_ZERO);
        _lightFrameBuffer.draw(0, 0);
        gfx.setDrawMode(Graphics.MODE_NORMAL);
    }

    private void paintBufferDebug(Graphics gfx)
    {
        gfx.drawImage(_lightFrameBuffer, 0, 0);
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
            _lightFrameBuffer = new Image((int)DisplayManager.getBackgroundWidth()+2, (int)DisplayManager.getBackgroundHeight()+2);
            _lightFrameGfx = _lightFrameBuffer.getGraphics();
            _lightFrameGfx.setColor(_ambientLight);

            _visibleBuffer = new Image((int)DisplayManager.getBackgroundWidth()+2, (int)DisplayManager.getBackgroundHeight()+2);
            _visibleGfx = _visibleBuffer.getGraphics();
            _visibleGfx.setColor(Color.black);
        }
        catch (SlickException e)
        {
            LRK.log("Failed to create the image buffer for th lighting system");
            e.printStackTrace();
            System.exit(89133);
        }
    }

    public Light getCameraLight()
    {
        return _cameraLight;
    }

    public void setCameraLight(Light cameraLight)
    {
        _cameraLight = cameraLight;
    }
}
