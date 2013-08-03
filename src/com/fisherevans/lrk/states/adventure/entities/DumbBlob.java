package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.notifications.types.Notification;
import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class DumbBlob extends AdventureEntity
{
    private float _speed = 2.25f;

    private CircleShape _shape;

    private long attackSpeed = 1750;
    private long nextAttack = 0;

    public DumbBlob(RPGEntity rpgEntity, float x, float y, World world, AdventureState state)
    {
        super(rpgEntity, state);

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

        setImage(Resources.getImage("entities/dummy-blob")); // the image of this entity
    }

    @Override
    public void update(float delta)
    {
        Vec2 aimVector = getState().getPlayer().getBody().getPosition().sub(getBody().getPosition());
        long time = System.currentTimeMillis();

        if(aimVector.length() < 1 && time >= nextAttack)
        {
            getState().getPlayer().getRpgEntity().getHealth().subtractHealth(150);
            nextAttack = time + attackSpeed;
            Game.lrk.getNotifications().addNotification(new Notification("A Blob Attacked you!", Notification.BLUE));
        }

        if(aimVector.length() < 8)
        {
            aimVector.normalize();
            aimVector.mulLocal(_speed);
            getBody().setLinearVelocity(aimVector);
            setDegrees((float) Math.toDegrees(Math.atan2(aimVector.y, aimVector.x)));
            //LRK.log("Blob Speed: " + aimVector.length());
        }
        else
        {
            getBody().setLinearVelocity(new Vec2(0, 0));
        }
    }
}
