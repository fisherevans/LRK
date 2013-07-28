package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.managers.InputManager;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:31 PM
 */
public class Player extends AdventureEntity
{
    private Image _image;

    private float _speed = 1.5f;

    private CircleShape _shape;

    /**
     * create a new player object in the given world starting at the given position
     * @param x the x position to start the player entity at
     * @param y the y position to start the player entity at
     * @param world the world to keep the player in.
     */
    public Player(float x, float y, World world)
    {
        BodyDef bd = new BodyDef(); // define the body of the entity
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;

        _shape = new CircleShape(); // create the shape of the entity
        _shape.m_radius = 0.40625f;

        FixtureDef fd = new FixtureDef(); // define the body fixture
        fd.shape = _shape;
        fd.density = 1f;
        fd.friction = 0.2f;
        fd.restitution = -1.0f;

        Body body = world.createBody(bd); // combine it all and add the entity to the world
        body.createFixture(fd);

        setBody(body);

        _image = Resources.getImage("res/test/images/32x32char.png"); // the image of this entity
    }

    @Override
    public void update(float delta)
    {
        setDegrees((float) Math.toDegrees(Math.atan2(InputManager.getMouseYOrigin(), InputManager.getMouseXOrigin())));
        //LRK.log(getDegrees()+ "");

        Vec2 v = InputManager.getMoveVector();

        if(v.x != 0 || v.y != 0) // if we're moving
        {
            float aimAngle = (float)Math.toRadians(getDegrees()); // get the angle of the mouse vs center of the screen
            double moveAngle = Math.atan2(v.y, v.x); // then the angle of the movement vector
            float diff = (float)Math.abs(Math.atan2(Math.sin(aimAngle - moveAngle), Math.cos(aimAngle - moveAngle))); // get the smallest positive angle between those two
            float aimScale = (float) ((Math.PI*2-diff)/Math.PI*2*0.666f + 0.333f); // the float scale to move by
            
            v.mulLocal(_speed*aimScale); // scale the speed by the angle difference (move slower walking backwards)
        }

        getBody().setLinearVelocity(v);
    }

    @Override
    public Image getImage()
    {
        _image.setRotation(getDegrees());
        return _image;
    }
}
