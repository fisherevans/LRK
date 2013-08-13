package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
    private ArrayList<Light> _lights;
    private Image _lightBuffer;
    private Graphics _bufferGfx;
    private Color _ambientLight;

    public LightManager(AdventureState parent, Color ambientLight)
    {
        _parent = parent;
        _ambientLight = ambientLight;
        _lights = new ArrayList<>();
        resize();
    }

    public void update(float delta)
    {
        for(Light light:_lights)
            light.update(delta);
    }

    public void render(Graphics gfx, float xShift, float yShift)
    {
        float x, y, fullSize, halfSize;

        _bufferGfx.clear();
        _bufferGfx.setColor(_ambientLight);
        _bufferGfx.fillRect(0, 0, _lightBuffer.getWidth(), _lightBuffer.getHeight());
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        //GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        for(Light light:_lights)
        {
            if(_parent.inRenderArea(light.getPosition(), light.getRadius()))
            {
                halfSize = light.getRadius()*AdventureState.TILE_SIZE;
                fullSize = halfSize*2f;
                x = xShift+light.getPosition().x*AdventureState.TILE_SIZE - halfSize;
                y = yShift+light.getPosition().y*AdventureState.TILE_SIZE - halfSize;
                _bufferGfx.drawImage(light.getImage().getScaledCopy((int)fullSize, (int)fullSize), x, y, light.getColor());
            }
        }
        gfx.setDrawMode(Graphics.MODE_NORMAL);
        _bufferGfx.flush();

        //paintBuffer(gfx);
        paintBufferDebug(gfx);
    }

    private void paintBuffer(Graphics gfx)
    {
        GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_ZERO);
        _lightBuffer.draw(0, 0);
        gfx.setDrawMode(Graphics.MODE_NORMAL);
    }

    private void paintBufferDebug(Graphics gfx)
    {
        _lightBuffer.draw(0, 0, new Color(1f, 1f, 1f, 0.9f));
    }

    public void addLight(Light newLight)
    {
        if(!_lights.contains(newLight))
            _lights.add(newLight);
    }

    public void removeLight(Light oldLight)
    {
        if(_lights.contains(oldLight))
            _lights.remove(oldLight);
    }

    public void resize()
    {
        try
        {
            _lightBuffer = new Image((int)DisplayManager.getBackgroundWidth(), (int)DisplayManager.getBackgroundHeight());
            _bufferGfx = _lightBuffer.getGraphics();
        }
        catch (SlickException e)
        {
            LRK.log("Failed to create the image buffer for th lighting system");
            e.printStackTrace();
            System.exit(89133);
        }
    }
}
