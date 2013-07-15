package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 6/1/13
 * Time: 1:03 PM
 */
public abstract class EntityComponent
{
    private RPGEntity _parentEntity;

    public EntityComponent(RPGEntity parentEntity)
    {
        _parentEntity = parentEntity;
    }

    public RPGEntity getParentEntity()
    {
        return _parentEntity;
    }

    /**
     * Loads a setting from a setting line
     * @param key the key of the property
     * @param value the sting value
     */
    public abstract void readFromFile(String key, String value);

    /**
     * Saves the settings of this object to a text file (to be read later)
     * @param out the stream to print out of
     */
    public abstract void saveToFile(PrintWriter out);
}
