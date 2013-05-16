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

    private float _speed = 4f;

    private CircleShape _shape;

    public Player(float x, float y, World world)
    {
        BodyDef bd = new BodyDef();
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;

        _shape = new CircleShape();
        _shape.m_radius = 0.45f;

        FixtureDef fd = new FixtureDef();
        fd.shape = _shape;
        fd.density = 1f;
        fd.friction = 0.2f;
        fd.restitution = 0.0f;

        Body body = world.createBody(bd);
        body.createFixture(fd);

        setBody(body);

        _image = Resources.getImage("res/test/images/32x32char.png");
    }

    @Override
    public void update(int delta)
    {
        Vec2 v = new Vec2(0, 0);

        if(Game.lrk.getInput().isKeyDown(Options.getControlUp()))
            v.y -= 1f;
        if(Game.lrk.getInput().isKeyDown(Options.getControlDown()))
            v.y += 1f;
        if(Game.lrk.getInput().isKeyDown(Options.getControlLeft()))
            v.x -= 1f;
        if(Game.lrk.getInput().isKeyDown(Options.getControlRight()))
            v.x += 1f;

        if(v.x != 0 || v.y != 0)
        {
            float moveAngle = (float) Math.toDegrees(Math.atan2(v.y, v.x));
            float angleDiff = Math.abs((180f-Math.abs(Math.abs((moveAngle + 180f -  getDegrees()) % 360f - 180f)))/180f*0.333f + 0.667f);
            LRK.log(angleDiff+"");
            v.normalize();
            v.mulLocal(_speed*angleDiff);
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
