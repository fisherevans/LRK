package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * User: Fisher evans
 * Date: 5/30/13
 * Time: 5:40 PM
 */
public class CombatSkill extends EntityComponent
{
    public static final int BASE_MAGIC = 20;
    public static final int BASE_STRENGTH = 20;
    public static final int BASE_WILL_POWER = 30;
    public static final int BASE_ENDURANCE = 30;

    private int _skillLevel;
    private CombatSkillType _type;
    private ArrayList<CombatSkillListener> _listeners;

    /**
     * holds combat skill information
     * @param parentEntity the entity this skill belongs to
     * @param type the type of this skill
     * @param skillLevel the initial level of this skill
     */
    public CombatSkill(RPGEntity parentEntity, CombatSkillType type, int skillLevel)
    {
        super(parentEntity);
        _type = type;
        _skillLevel = skillLevel;

        _listeners = new ArrayList<>();
    }

    @Override
    public void readFromFile(String key, String value)
    {
    }

    @Override
    public void saveToFile(PrintWriter out)
    {
    }

    /**
     * adds a listener to this object
     * @param listener the listener to call during events
     */
    public void addListener(CombatSkillListener listener)
    {
        _listeners.add(listener);
    }

    /**
     * removes the event listener from this object
     * @param listener the listener to stop calling
     * @return returns false if the passes listener is not currently listening
     */
    public boolean removeListener(CombatSkillListener listener)
    {
        if(!_listeners.contains(listener))
            return false;

        _listeners.remove(listener);
        return true;
    }

    /**
     * increments this skill level by 1
     * @return the skill level after 1 is added to it
     */
    public int incrementSkillLevel()
    {
        return addSkillLevel(1);
    }

    /**
     * increments this skill level by a given amount
     * @param amount the amount to add to the skill level
     * @return the skill level after the amount is added
     */
    public int addSkillLevel(int amount)
    {
        _skillLevel += amount;
        callSkillLevelChangedListeners();
        return _skillLevel;
    }

    // PRIVATES

    private void callSkillLevelChangedListeners()
    {
        for(CombatSkillListener listener:_listeners)
            listener.skillLevelChanged(getParentEntity());
    }

    // GETTERS


    public int getSkillLevel()
    {
        return _skillLevel;
    }

    public CombatSkillType getType()
    {
        return _type;
    }

    public String getName()
    {
        return _type.name();
    }

    // SUB CLASSES

    public abstract class CombatSkillListener
    {
        /**
         * When ever a given skill type is changed
         * @param entity the entity that had the change
         */
        public abstract void skillLevelChanged(RPGEntity entity);
    }

    public enum CombatSkillType
    {
        Intelligence,
        Strength,
        WillPower,
        Endurance,
    }
}
