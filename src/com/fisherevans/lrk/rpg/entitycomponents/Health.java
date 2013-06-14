package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * User: Fisher evans
 * Date: 5/29/13
 * Time: 9:19 PM
 */
public class Health extends EntityComponent
{
    public static final int BASE_HEALTH = 50;

    private int _maximumHealth, _currentHealth;
    private ArrayList<HealthListener> _listeners;

    /**
     * Creates the health object with a maximum health, sets it to it
     * @param parentEntity the entity this health object belongs to
     * @param health the maximum health
     */
    public Health(RPGEntity parentEntity, int health)
    {
        this(parentEntity, health, health);
    }

    /**
     * creates the health object for a given entity with a maximum and current health
     * @param parentEntity entity this health belongs to
     * @param maximumHealth max health
     * @param currentHealth current health
     */
    public Health(RPGEntity parentEntity, int maximumHealth, int currentHealth)
    {
        super(parentEntity);
        _maximumHealth = maximumHealth;
        _currentHealth = currentHealth;

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
     * increases the maximum amount of health this entity can have
     * @param amount the amount to increase by
     * @param increaseCurrent whether or not to increase the current health by the same amount
     */
    public void increaseMaximumHealth(int amount, boolean increaseCurrent)
    {
        _maximumHealth += amount;
        if(increaseCurrent)
            _currentHealth += amount;
    }

    /**
     * adds a listener to this object
     * @param listener the listener to call during events
     */
    public void addListener(HealthListener listener)
    {
        _listeners.add(listener);
    }

    /**
     * removes the event listener from this object
     * @param listener the listener to stop calling
     * @return returns false if the passes listener is not currently listening
     */
    public boolean removeListener(HealthListener listener)
    {
        if(!_listeners.contains(listener))
            return false;

        _listeners.remove(listener);
        return true;
    }

    /**
     * Adds an amount of health to the current health. will not surpass max
     * @param amount the amount to add
     */
    public void addHealth(int amount)
    {
        if(_currentHealth >= _maximumHealth)
            return;

        _currentHealth += amount;
        if(_currentHealth >= _maximumHealth)
        {
            callFullListeners();
            _currentHealth = _maximumHealth;
        }
    }


    /**
     * subtracts an amount of health from the current health. will not go below 0
     * @param amount the amount to subtract
     */
    public void subtractHealth(int amount)
    {
        if(_currentHealth <= 0)
            return;

        _currentHealth -= amount;
        if(_currentHealth <= 0)
        {
            callDepletedListeners();
            _currentHealth = 0;
        }
    }

    /**
     * @return if no more health can be added
     */
    public boolean isHealthFull()
    {
        return _currentHealth >= _maximumHealth;
    }

    /**
     * @return if the current health can't go any lower
     */
    public boolean isDead()
    {
        return _currentHealth <= 0;
    }

    // PRIVATE

    private void callFullListeners()
    {
        for(HealthListener listener:_listeners)
            listener.healthFull(getParentEntity());
    }
    private void callDepletedListeners()
    {
        for(HealthListener listener:_listeners)
            listener.healthDepleted(getParentEntity());
    }

    // GETTERS

    public int getMaximumHealth()
    {
        return _maximumHealth;
    }

    public int getCurrentHealth()
    {
        return _currentHealth;
    }

    // SUB CLASSES
    public abstract class HealthListener
    {
        /**
         * called when an entity's health is changed to 0
         * @param entity the entity who "died"
         */
        public abstract void healthDepleted(RPGEntity entity);

        /**
         * Called when the given entity's health changed to it's maximum
         * @param entity the entity who's health maxed out
         */
        public abstract void healthFull(RPGEntity entity);
    }
}