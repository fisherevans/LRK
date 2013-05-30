package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * User: Fisher evans
 * Date: 5/29/13
 * Time: 8:37 PM
 */
public class Level
{
    private int _level, _levelStartXP, _progressXP, _levelUpRequiredXP;
    private ArrayList<LevelListener> _listeners;
    private RPGEntity _parentEntity;

    /**
     * Creates the level object for a given entity
     * @param parentEntity the entity who has this level object
     * @param level the current level of this entity
     * @param progressXP the XP surpassing that of the total required to reach the current level
     */
    public Level(RPGEntity parentEntity, int level, int progressXP)
    {
        _parentEntity = parentEntity;

        _level = level;
        _progressXP = progressXP;

        calculateLevelStats();

        _listeners = new ArrayList<>();
    }

    /**
     * adds a listener to this object
     * @param listener the listener to call during events
     */
    public void addListener(LevelListener listener)
    {
        _listeners.add(listener);
    }

    /**
     * removes the event listener from this object
     * @param listener the listener to stop calling
     * @return returns false if the passes listener is not currently listening
     */
    public boolean removeListener(LevelListener listener)
    {
        if(!_listeners.contains(listener))
            return false;

        _listeners.remove(listener);
        return true;
    }

    /**
     * adds a given amount of XP to this entity
     * @param amount the amount to add
     */
    public void addXP(int amount)
    {
        int next = getXPLeft();

        if(amount < next)
            _progressXP += amount;
        else
        {
            levelUp();
            addXP(amount-next);
        }
    }

    /**
     * increases the level of this entity by 1 without adjust it's progress amount (i.e. adds exactly one level)
     */
    public void increaseLevel()
    {
        _level++;
        calculateLevelStats();

        callListeners();
    }

    /**
     * increases the level of this entity by 1 and setting the current progress to 0.
     */
    public void levelUp()
    {
        _level++;
        _progressXP = 0;
        calculateLevelStats();

        callListeners();
    }

    // PRIVATE

    private void callListeners()
    {
        for(LevelListener listener:_listeners)
            listener.leveledUp(getLevel(), _parentEntity);
    }

    private void calculateLevelStats()
    {
        _levelStartXP = totalXPRequired(_level);
        _levelUpRequiredXP = totalXPRequired(_level+1);
    }

    // GETTERS

    public RPGEntity getParentEntity()
    {
        return _parentEntity;
    }

    public int getXPLeft()
    {
        return _levelUpRequiredXP - getTotalXP();
    }

    public int getTotalXP()
    {
        return _levelStartXP + _progressXP;
    }

    public int getLevel()
    {
        return _level;
    }

    public int getLevelStartXP()
    {
        return _levelStartXP;
    }

    public int getProgressXP()
    {
        return _progressXP;
    }

    public int getLevelUpRequiredXP()
    {
        return _levelUpRequiredXP;
    }

    // STATIC METHODS

    public static String xpToString(int xp)
    {
        return NumberFormat.getInstance().format(xp);
    }

    public static int totalXPRequired(int level)
    {
        return (level*(level-1)) + level*50;
    }
}
