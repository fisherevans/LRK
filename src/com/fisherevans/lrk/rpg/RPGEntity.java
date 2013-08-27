package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.entitycomponents.*;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
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
    private Map<Equipment.Position, Equipment> _equipment;

    public RPGEntity(String name, Profession profession, int level, int health, int power, int defence)
    {
        _name = name;
        _profession = profession;
        _level = new Level(this, level);
        _health = new Health(this, health);

        _power = new CombatStat(this, power);
        _defence = new CombatStat(this, defence);

        _equipment = new HashMap<>();
    }

    public Map<Equipment.Position, Equipment> getEquipmentMap()
    {
        return _equipment;
    }

    public Equipment getEquipment(Equipment.Position position)
    {
        if(_equipment.containsKey(position))
            return _equipment.get(position);
        else
            return null;
    }

    public void equip(Equipment newItem)
    {
        if(newItem.getPosition() == Equipment.Position.OffHand)
        {
            Equipment mainHand = getEquipment(Equipment.Position.MainHand);
            if(mainHand != null && mainHand instanceof Weapon && ((Weapon)mainHand).isTwoHanded())
                _equipment.remove(Equipment.Position.MainHand);
        }
        else if(newItem.getPosition() == Equipment.Position.MainHand)
        {
            if(newItem instanceof Weapon && ((Weapon)newItem).isTwoHanded() && _equipment.containsKey(Equipment.Position.OffHand))
                _equipment.remove(Equipment.Position.OffHand);
        }
        _equipment.put(newItem.getPosition(), newItem);
    }

    public float getTotalPower()
    {
        float power = _power.getStatLevel();
        for(Equipment equipment:_equipment.values())
            power += equipment.getPower();
        return power;
    }

    public float getTotalDefence()
    {
        float defence = _defence.getStatLevel();
        for(Equipment equipment:_equipment.values())
            defence += equipment.getDefence();
        return defence;
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
