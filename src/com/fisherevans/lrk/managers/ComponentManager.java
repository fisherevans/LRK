package com.fisherevans.lrk.managers;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:27 PM
 */
public abstract class ComponentManager
{
    /**
     * Prints the current settings in an ini format to a given print write
     * @param out the print writer to print the settings to
     */
    public abstract void saveProperties(PrintWriter out);

    /**
     * sets a key with a given value
     * @param key the key to set
     * @param value the string form of the value
     */
    public abstract void setProperty(String key, String value);
}
