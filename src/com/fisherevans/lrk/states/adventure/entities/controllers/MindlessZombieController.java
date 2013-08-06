package com.fisherevans.lrk.states.adventure.entities.controllers;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.notifications.types.Notification;
import com.fisherevans.lrk.states.adventure.entities.ActiveEntity;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MindlessZombieController extends ActiveEntityController
{
    private AdventureEntity _target;
    private float _sightDistance;

    private long _attackSpeed = 1750;
    private long _nextAttack = 0;

    public MindlessZombieController(ActiveEntity entity, AdventureEntity target, float sightDistance)
    {
        super(entity);
        _target = target;
        _sightDistance = sightDistance;
    }

    public MindlessZombieController(ActiveEntity entity, AdventureEntity target, float sightDistance, long attackSpeed)
    {
        super(entity);
        _target = target;
        _sightDistance = sightDistance;
        _attackSpeed = attackSpeed;
    }

    @Override
    public void update(float delta)
    {
        Vec2 aimVector = _target.getBody().getPosition().sub(getEntity().getBody().getPosition());
        long time = System.currentTimeMillis();

        if(aimVector.length() < 1 && time >= _nextAttack)
        {
            _target.getRpgEntity().getHealth().subtractHealth(150);
            _nextAttack = time + _attackSpeed;
            Game.lrk.getNotifications().addNotification(new Notification("A Blob Attacked you!", Notification.BLUE));
        }

        if(aimVector.length() < _sightDistance)
        {
            aimVector.normalize();
            aimVector.mulLocal(getEntity().getSpeed());
            getEntity().getBody().setLinearVelocity(aimVector);
            getEntity().setDegrees((float) Math.toDegrees(Math.atan2(aimVector.y, aimVector.x)));
            //LRK.log("Blob Speed: " + aimVector.length());
        }
        else
        {
            getEntity().getBody().setLinearVelocity(new Vec2(0, 0));
        }
    }
}
