package com.fisherevans.lrk.states.adventure.entities;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 8:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class JBox2DUtils
{
    public static final float DEFAULT_CIRCLE_RADIUS = 0.40625f; // 12px

    public static Body getCircleBody(World world, float x, float y, float radius)
    {
        BodyDef bd = new BodyDef(); // define the body of the entity
        bd.position.set(x, y);
        bd.type = BodyType.DYNAMIC;

        CircleShape circle = new CircleShape(); // create the shape of the entity
        circle.m_radius = radius;

        FixtureDef fd = new FixtureDef(); // define the body fixture
        fd.shape = circle;
        fd.density = 1f;
        fd.friction = 0.2f;
        fd.restitution = -1.0f;

        Body body = world.createBody(bd); // combine it all and add the entity to the world
        body.createFixture(fd);

        return body;
    }
}
