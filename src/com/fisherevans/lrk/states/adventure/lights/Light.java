package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.adventure.lights.light_controllers.TorchController;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.QuadBasedLineStripRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Light
{
    private static final String DEFAULT_LIGHT = "lights/default";

    private Image _image;
    private float _radius;
    private Color _color;
    private Vec2 _position;
    private LightController _controller;

    private LightManager _manager;

    public Light(Image image, float radius, Color color, Vec2 position, LightController controller, LightManager manager)
    {
        _image = image;
        _radius = radius;
        _color = color;
        _position = position;
        _controller = controller;
        _manager = manager;
    }

    public Light(Image image, float radius, Color color, Vec2 position, LightManager manager)
    {
        this(image, radius, color, position, null, manager);
    }

    public Light(float radius, Color color, Vec2 position, LightManager manager)
    {
        this(null, radius, color, position, null, manager);
        _image = getDefaultImage();
    }

    public Light(float radius, Color color, Vec2 position, String controller, LightManager manager)
    {
        this(null, radius, color, position, null, manager);
        _image = getDefaultImage();
        setController(controller);
    }

    public void update(float delta)
    {
        if(_controller != null)
            _controller.update(delta);
    }

    private Image getDefaultImage()
    {
        Image lightImage;
        try
        {
            lightImage = new Image("res/images/lights/default.png");
        }
        catch (Exception e)
        {
            lightImage = Resources.getImage(DEFAULT_LIGHT);
        }
        return lightImage;
    }

    public Image getImage()
    {
        return _image;
    }

    public void setImage(Image image)
    {
        _image = image;
    }

    public float getRadius()
    {
        return _radius;
    }

    public void setRadius(float radius)
    {
        _radius = radius;
    }

    public Color getColor()
    {
        return _color;
    }

    public void setColor(Color color)
    {
        _color = color;
    }

    public Vec2 getPosition()
    {
        return _position;
    }

    public void setPosition(Vec2 position)
    {
        _position = position;
    }

    public LightController getController()
    {
        return _controller;
    }

    public void setController(LightController controller)
    {
        _controller = controller;
    }

    public LightManager getManager()
    {
        return _manager;
    }

    public void setController(String key)
    {
        switch(key)
        {
            case "torch": setController(new TorchController(this)); break;
            default: LRK.log("WARNING - unable to set light controller: " + key);
        }
    }
}
