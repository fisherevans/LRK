package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/14/13
 * Time: 10:34 PM
 */
public class Wall extends AdventureEntity
{
    /**
     * create a wall entity in the given world. This entity does not move and the shape of the entity is defined by the
     * given id
     * @param x the x position to center this entity
     * @param y the y position to center this entity
     * @param def the fixture definationt o use as the shape of this wall
     * @param world the world to put the wal in
     */
    public Wall(float x, float y, FixtureDef def, World world, AdventureState state)
    {
        super(null, state);
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.STATIC;

        Body body = world.createBody(bd);
        body.createFixture(def);

        setBody(body);
    }

    @Override
    public void update(float delta)
    {
    }

    @Override
    public Image getImage()
    {
        return null;
    }
}
