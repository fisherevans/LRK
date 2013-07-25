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
    private Map<CombatSkill.Type, CombatSkill> _combatSkills;

    public RPGEntity(String name, Profession profession, int level, int health, int power, int defence)
    {
        _name = name;
        _profession = profession;
        _level = new Level(this, level);
        _health = new Health(this, health);

        _combatSkills = new HashMap<>();
        addSkill(new CombatSkill(this, CombatSkill.Type.Power, power));
        addSkill(new CombatSkill(this, CombatSkill.Type.Defence, defence));
    }

    /**
     * adds a given skill to this entity
     * @param combatSkill the skill to add
     * @return returns false if a skill of the same type already exists - this means the passed skill WAS NOT added
     */
    public boolean addSkill(CombatSkill combatSkill)
    {
        if(_combatSkills.containsKey(combatSkill.getName()))
            return false;

        _combatSkills.put(combatSkill.getType(), combatSkill);
        return true;
    }

    /**
     * gets the combat skill associated with this entity and combat type
     * @param combatSkillType the type to look for
     * @return the skill object or null if the skill type isn't found
     */
    public CombatSkill getSkill(CombatSkill.Type combatSkillType)
    {
        if(!_combatSkills.containsKey(combatSkillType))
            return null;
        else
            return _combatSkills.get(combatSkillType);
    }

    /**
     * removes a given skill from this entity
     * @param combatSkillType the type of skill to remove
     * @return returns false if the skill was not present
     */
    public boolean removeSkill(CombatSkill.Type combatSkillType)
    {
        if(!_combatSkills.containsKey(combatSkillType))
            return false;

        _combatSkills.remove(combatSkillType);
        return true;
    }

    /**
     * Sets this entity's skill no matter what is already in this entity's skillset - aka replace
     * @param combatSkill the skill to add
     */
    public void setSkill(CombatSkill combatSkill)
    {
        _combatSkills.put(combatSkill.getType(), combatSkill);
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
}
