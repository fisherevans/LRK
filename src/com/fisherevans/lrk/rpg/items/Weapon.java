package com.fisherevans.lrk.rpg.items;

import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.skills.Splash;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Weapon extends Equipment
{
    private Skill _skill, _secondarySkill;

    private boolean _twoHanded;

    public Weapon(String name, String description, Image image, Position position, int power, int defence, boolean twoHanded)
    {
        super(name, description, image, position, power, defence);
        _twoHanded = twoHanded;
        _skill = new Splash();
        _secondarySkill = null;
    }

    public Weapon(String name, String description, Image image, Position position, int power, int defence)
    {
        this(name, description, image, position, power, defence, false);
    }

    public Skill getSkill()
    {
        return _skill;
    }

    public void setSkill(Skill skill)
    {
        _skill = skill;
    }

    public boolean isTwoHanded()
    {
        return _twoHanded;
    }

    public void setTwoHanded(boolean twoHanded)
    {
        _twoHanded = twoHanded;
    }

    public Skill getSecondarySkill()
    {
        return _secondarySkill;
    }

    public void setSecondarySkill(Skill secondarySkill)
    {
        _secondarySkill = secondarySkill;
    }
}
