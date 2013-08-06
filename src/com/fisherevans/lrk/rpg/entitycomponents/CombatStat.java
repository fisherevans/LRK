package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * User: Fisher evans
 * Date: 5/30/13
 * Time: 5:40 PM
 */
public class CombatStat extends EntityComponent
{
    public static final int BASE_POWER = 10;
    public static final int BASE_DEFENCE = 10;

    private int _statLevel;
    private ArrayList<CombatStatListener> _listeners;

    /**
     * holds combat skill information
     * @param parentEntity the entity this skill belongs to
     * @param statLevel the initial level of this skill
     */
    public CombatStat(RPGEntity parentEntity, int statLevel)
    {
        super(parentEntity);
        _statLevel = statLevel;

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
    public void addListener(CombatStatListener listener)
    {
        _listeners.add(listener);
    }

    /**
     * removes the event listener from this object
     * @param listener the listener to stop calling
     * @return returns false if the passes listener is not currently listening
     */
    public boolean removeListener(CombatStatListener listener)
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
    public int incrementStatLevel()
    {
        return addStatLevel(1);
    }

    /**
     * increments this skill level by a given amount
     * @param amount the amount to add to the skill level
     * @return the skill level after the amount is added
     */
    public int addStatLevel(int amount)
    {
        _statLevel += amount;
        callStatLevelChangedListeners();
        return _statLevel;
    }

    // PRIVATES

    private void callStatLevelChangedListeners()
    {
        for(CombatStatListener listener:_listeners)
            listener.statLevelChanged(getParentEntity());
    }

    // GETTERS


    public int getStatLevel()
    {
        return _statLevel;
    }

    // SUB CLASSES

    public abstract class CombatStatListener
    {
        /**
         * When ever a given skill type is changed
         * @param entity the entity that had the change
         */
        public abstract void statLevelChanged(RPGEntity entity);
    }
}
