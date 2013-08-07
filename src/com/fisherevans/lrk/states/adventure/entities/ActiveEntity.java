package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.entities.controllers.ActiveEntityController;
import com.fisherevans.lrk.states.adventure.entities.controllers.NullController;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActiveEntity extends AdventureEntity
{
    private ActiveEntityController _controller;

    private float _speed;

    protected ActiveEntity(RPGEntity rpgEntity, AdventureState state, float speed)
    {
        super(rpgEntity, state);
        _controller = new NullController();
        _speed = speed;
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);
        _controller.update(delta);
    }

    public float getSpeed()
    {
        return _speed;
    }

    public void setSpeed(float speed)
    {
        _speed = speed;
    }

    public ActiveEntityController getController()
    {
        return _controller;
    }

    public void setController(ActiveEntityController controller)
    {
        _controller = controller;
    }
}
