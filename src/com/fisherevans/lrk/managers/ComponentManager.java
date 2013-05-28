package com.fisherevans.lrk.managers;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:27 PM
 */
public abstract class ComponentManager
{
    public abstract void saveProperties(PrintWriter out);
    public abstract void setProperty(String key, String value);
}
