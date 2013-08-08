package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.entities.controllers.MindlessZombieController;
import org.jbox2d.dynamics.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class DumbBlob extends ActiveEntity implements Health.HealthListener
{
    public DumbBlob(RPGEntity rpgEntity, float x, float y, World world, AdventureState state)
    {
        super(rpgEntity, state, 2.25f);
        setTeam(Team.Hostile);
        setController(new MindlessZombieController(this, getState().getPlayerEntity(), 10));
        setBody(JBox2DUtils.getCircleBody(world, x, y, JBox2DUtils.DEFAULT_CIRCLE_RADIUS));
        setImage(Resources.getImage("entities/dummy-blob"));

        getRpgEntity().getHealth().addListener(this);
    }
}
