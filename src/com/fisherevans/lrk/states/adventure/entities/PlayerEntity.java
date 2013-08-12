package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.managers.SoundManager;
import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.skills.Slash;
import com.fisherevans.lrk.states.adventure.entities.controllers.PlayerController;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:31 PM
 */
public class PlayerEntity extends ActiveEntity
{
    /**
     * create a new player object in the given world starting at the given position
     * @param x the x position to start the player entity at
     * @param y the y position to start the player entity at
     * @param world the world to keep the player in.
     */
    public PlayerEntity(float x, float y, World world, AdventureState state)
    {
        super(Game.lrk.getPlayer().getEntity(), state, 3.5f);
        setTeam(Team.Ally);
        setController(new PlayerController(this));
        setBody(JBox2DUtils.getCircleBody(world, x, y, JBox2DUtils.DEFAULT_CIRCLE_RADIUS));
        setImage(Resources.getImage("entities/dummy-player"));
        setDrawHealthBar(false);
        setNumColorDamage(new Color(1f, 0f, 0f));
        setNumColorHeal(new Color(0f, 1f, 0f));
    }

    public void leftMousePress(float x, float y)
    {
        //getController().executeMainSkill();
    }

    public void rightMousePress(float x, float y)
    {
        //getController().executeSecondarySkill();
    }

    @Override
    public void healthDecreased(RPGEntity entity, float amount)
    {
        super.healthDecreased(entity, amount);
        SoundManager.play("human-hurt_01");
    }
}
