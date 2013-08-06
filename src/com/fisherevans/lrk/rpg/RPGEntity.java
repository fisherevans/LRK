package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.entitycomponents.*;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Fisher evans
 * Date: 5/29/13
 * Time: 8:33 PM
 */
public class RPGEntity
{
    public enum Profession { Warrior, Mage }

    private String _name;
    private Level _level;
    private Health _health;
    private Profession _profession;
    private CombatStat _power, _defence;

    public RPGEntity(String name, Profession profession, int level, int health, int power, int defence)
    {
        _name = name;
        _profession = profession;
        _level = new Level(this, level);
        _health = new Health(this, health);

        _power = new CombatStat(this, power);
        _defence = new CombatStat(this, defence);
    }

    public CombatStat getPower()
    {
        return _power;
    }

    public CombatStat getDefence()
    {
        return _defence;
    }

    // SIMPLE GETTERS

    public Level getLevel()
    {
        return _level;
    }

    public Health getHealth()
    {
        return _health;
    }

    public String getName()
    {
        return _name;
    }

    public Profession getProfession()
    {
        return _profession;
    }
}
