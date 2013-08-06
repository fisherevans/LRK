package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:30 PM
 */
public abstract class AdventureEntity
{
    private Body _body;

    private RPGEntity _rpgEntity;

    private float _angle = 0f;

    private Image _image;

    private AdventureState _state;

    private boolean _isHostile = false;

    protected AdventureEntity(RPGEntity rpgEntity, AdventureState state)
    {
        _rpgEntity = rpgEntity;
        _state = state;
    }

    protected AdventureEntity(RPGEntity rpgEntity, AdventureState state, boolean isHostile)
    {
        _rpgEntity = rpgEntity;
        _state = state;
        _isHostile = isHostile;
    }

    /**
     * updates the entity with each step of the main game loop
     * @param delta ms since the last update
     */
    public abstract void update(float delta);

    public void render(Graphics gfx, float drawX, float drawY)
    {
        GFX.drawImageCentered(drawX, drawY, getImage());
    }

    /**
     * gets the jbox body object of this entity
     * @return the body
     */
    public Body getBody()
    {
        return _body;
    }

    /**
     * gets the jbox body object of this entity
     * @param body the new body
     */
    public void setBody(Body body)
    {
        _body = body;
    }

    /**
     * @return the x position of the entity in it's jbox world
     */
    public float getX()
    {
        return _body.getPosition().x;
    }

    /**
     * @return the j position of the entity in it's jbox world
     */
    public float getY()
    {
        return _body.getPosition().y;
    }

    /**
     * @return get the rotation of the jbox body (in degrees)
     */
    public float getDegrees()
    {
        return _angle;
    }

    /**
     * @param angle the new angle of rotation for the jbox body (in degrees)
     */
    public void setDegrees(float angle)
    {
        _angle = angle;
    }

    public void setImage(Image image)
    {
        _image = image;
    }

    /**
     * gets the current image of the entity
     * @return the entity's image
     */
    public Image getImage()
    {
        _image.setRotation(getDegrees());
        return _image;
    }

    public AdventureState getState()
    {
        return _state;
    }

    public RPGEntity getRpgEntity()
    {
        return _rpgEntity;
    }
}
