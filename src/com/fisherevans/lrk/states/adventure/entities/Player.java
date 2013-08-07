package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.skills.Slash;
import com.fisherevans.lrk.states.adventure.entities.controllers.PlayerController;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:31 PM
 */
public class Player extends ActiveEntity
{
    private Skill _skill;

    /**
     * create a new player object in the given world starting at the given position
     * @param x the x position to start the player entity at
     * @param y the y position to start the player entity at
     * @param world the world to keep the player in.
     */
    public Player(float x, float y, World world, AdventureState state)
    {
        super(Game.lrk.getPlayer().getEntity(), state, 3.5f);
        setTeam(Team.Ally);
        setController(new PlayerController(this));
        setBody(JBox2DUtils.getCircleBody(world, x, y, JBox2DUtils.DEFAULT_CIRCLE_RADIUS));
        setImage(Resources.getImage("entities/dummy-player"));

        _skill = new Slash(this, Team.Hostile);
    }

    public void mousePress(float x, float y)
    {
        _skill.execute(this);
    }
}
