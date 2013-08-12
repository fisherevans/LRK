package com.fisherevans.lrk.states.adventure.combat;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.rpg.items.Weapon;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/4/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Skill
{
    private String _name, _description;

    private float _coolDown = 1f;

    private float _actionTime;

    protected Skill(String name, String description, float coolDown, float actionTime)
    {
        _name = name;
        _description = description;
        _coolDown = coolDown;
        _actionTime = actionTime;
    }

    public String getName()
    {
        return _name;
    }

    public String getDescription()
    {
        return _description;
    }

    public float getCoolDown()
    {
        return _coolDown;
    }

    public float getActionTime()
    {
        return _actionTime;
    }

    /** Executes a skill from an AdventureEntity.
     * param owner the owner of the skill
     * returns true if the Skill is finished (false for lating effect - ward, etc)
     */
    public abstract boolean execute(AdventureEntity owner);

    public static float getDamage(RPGEntity entity, Weapon weapon)
    {
        float weaponStat = weapon == null ? 0 : weapon.getPower();
        return (entity.getPower().getStatLevel()+weaponStat);
    }
}
