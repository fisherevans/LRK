package com.fisherevans.lrk.states.adventure.entities.controllers;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.entities.ActiveEntity;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerController extends ActiveEntityController
{
    public PlayerController(ActiveEntity entity)
    {
        super(entity);
    }

    @Override
    public void update(float delta)
    {
        getEntity().setDegrees((float) Math.toDegrees(Math.atan2(InputManager.getMouseYOrigin(), InputManager.getMouseXOrigin())));
        //LRK.log(getDegrees()+ "");

        Vec2 v = InputManager.getMoveVector();

        if(v.x != 0 || v.y != 0) // if we're moving
        {
            float aimAngle = (float)Math.toRadians(getEntity().getDegrees()); // get the angle of the mouse vs center of the screen
            double moveAngle = Math.atan2(v.y, v.x); // then the angle of the movement vector
            float diff = (float)Math.abs(Math.atan2(Math.sin(aimAngle - moveAngle), Math.cos(aimAngle - moveAngle))); // get the smallest positive angle between those two
            float aimScale = (float) ((Math.PI*2-diff)/(Math.PI*2)*0.666f + 0.333f); // the float scale to move by

            v.mulLocal(getEntity().getSpeed()*aimScale); // scale the speed by the angle difference (move slower walking backwards)
        }


        getEntity().getBody().setLinearVelocity(v);

        getEntity().getRpgEntity().getHealth().addHealth(5 * delta);
    }

    @Override
    public Skill getMainSkill()
    {
        return Game.lrk.getPlayer().getMainSkill();
    }

    @Override
    public Skill getSecondarySkill()
    {
        return Game.lrk.getPlayer().getSecondarySkill();
    }
}
