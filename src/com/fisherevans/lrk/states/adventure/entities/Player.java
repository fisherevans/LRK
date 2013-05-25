package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:31 PM
 */
public class Player extends LRKEntity
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
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;

        _shape = new CircleShape();
        _shape.m_radius = 0.40625f;

        FixtureDef fd = new FixtureDef();
        fd.shape = _shape;
        fd.density = 1f;
        fd.friction = 0.2f;
        fd.restitution = -1.0f;

        Body body = world.createBody(bd);
        body.createFixture(fd);

        setBody(body);

        _image = Resources.getImage("res/test/images/32x32char.png");
    }

    @Override
    public void update(int delta)
    {
        Vec2 v = new Vec2(0, 0);

        if(Game.lrk.getInput().isKeyDown(Options.getControlUp1()) || Game.lrk.getInput().isKeyDown(Options.getControlUp2()))
            v.y -= 1f;
        if(Game.lrk.getInput().isKeyDown(Options.getControlDown1()) || Game.lrk.getInput().isKeyDown(Options.getControlDown2()))
            v.y += 1f;
        if(Game.lrk.getInput().isKeyDown(Options.getControlLeft1()) || Game.lrk.getInput().isKeyDown(Options.getControlLeft2()))
            v.x -= 1f;
        if(Game.lrk.getInput().isKeyDown(Options.getControlRight1()) || Game.lrk.getInput().isKeyDown(Options.getControlRight2()))
            v.x += 1f;

        if(v.x != 0 || v.y != 0)
        {
            float aimAngle = (float)Math.toRadians(getDegrees());
            double moveAngle = Math.atan2(v.y, v.x);
            float diff = (float)Math.abs(Math.atan2(Math.sin(aimAngle - moveAngle), Math.cos(aimAngle - moveAngle)));
            float aimScale = (float) ((Math.PI*2-diff)/Math.PI*2*0.666f + 0.333f);

            v.normalize();
            v.mulLocal(_speed*aimScale);
        }

        getBody().setLinearVelocity(v);
    }

    @Override
    public Image getImage()
    {
        _image.setRotation(getDegrees());
        return _image;
    }

    public float getRadius()
    {
        return _shape.m_radius;
    }
}
