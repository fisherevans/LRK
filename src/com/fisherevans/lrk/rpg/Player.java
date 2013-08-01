package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player
{
    private RPGEntity _entity;
    private Inventory _inventory;
    private Map<Equipment.Position, Equipment> _equipment;

    public Player(RPGEntity entity, Inventory inventory)
    {
        _entity = entity;
        _inventory = inventory;
        _equipment = new HashMap<>();
    }

    public Player(RPGEntity entity)
    {
        this(entity, new Inventory());
    }

    public Player()
    {
        this(null, new Inventory());
    }

    public RPGEntity getEntity()
    {
        return _entity;
    }

    public void setEntity(RPGEntity entity)
    {
        _entity = entity;
    }

    public Inventory getInventory()
    {
        return _inventory;
    }

    public void setInventory(Inventory inventory)
    {
        _inventory = inventory;
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
}
