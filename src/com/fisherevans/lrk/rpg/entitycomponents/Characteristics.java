package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/30/13
 * Time: 6:00 PM
 */
public class Characteristics extends EntityComponent
{
    public enum Gender { Male, Female }

    private String _name;
    private Gender _gender;

    public Characteristics(RPGEntity parentEntity, String name, Gender gender)
    {
        super(parentEntity);

        _name = name;
        _gender = gender;
    }

    // GETTERS

    public String getName()
    {
        return _name;
    }

    public Gender getGender()
    {
        return _gender;
    }

    @Override
    public void readFromFile(String key, String value)
    {
    }

    @Override
    public void saveToFile(PrintWriter out)
    {
    }
}
