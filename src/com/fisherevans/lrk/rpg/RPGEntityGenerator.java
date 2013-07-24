package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.entitycomponents.Characteristics;
import com.fisherevans.lrk.rpg.entitycomponents.CombatSkill;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.rpg.entitycomponents.Level;

/**
 * User: Fisher evans
 * Date: 5/31/13
 * Time: 8:03 AM
 */
public class RPGEntityGenerator
{
    public static RPGEntity getBasePlayer(String name, Characteristics.Gender gender)
    {
        return new RPGEntity(name, gender, Level.BASE_LEVEL, Health.BASE_HEALTH, CombatSkill.BASE_MAGIC, CombatSkill.BASE_STRENGTH, CombatSkill.BASE_WILL_POWER, CombatSkill.BASE_ENDURANCE);
    }

    public static Character getAnarok()
    {
        RPGEntity entity = new RPGEntity("Anorak", Characteristics.Gender.Male, 99, 999, 999, 999, 999, 999);
        Character character = new Character(entity);

        return character;
    }
}
