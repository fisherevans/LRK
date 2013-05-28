package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.states.options.menu_items.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/8/13
 * Time: 9:51 PM
 */
public class Menu extends MenuItem
{
    private ArrayList<MenuItem> _items;
    private int _currentId;

    public Menu()
    {
        super("","");
        _items = new ArrayList<>();
    }

    /**
     * Creates a sub menu item
     * @param text The label to display on it's parent menu
     * @param description A description of this sub menu
     */
    public Menu(String text, String description)
    {
        super(text, description);
        _items = new ArrayList<>();
    }

    /**
     * Adds menu items to this submenu
     * @param items the items to add (as separate elements)
     */
    public void add(MenuItem... items)
    {
        List<MenuItem> itemList = Arrays.asList(items);
        _items.addAll(itemList);

        for(MenuItem i:itemList)
            i.setParent(this);
    }

    @Override
    public Menu select()
    {
        return this;
    }

    @Override
    public boolean isActive()
    {
        return false;
    }

    /**
     * Move the current select of this menu
     * @param shift negative for "up" shift amount of items, positive for downt hat many
     * @return returns false if there is the shift moves the current id out of bounds
     */
    public boolean shiftCurrentId(int shift)
    {
        int newId = _currentId + shift;
        newId = 0 > newId ? 0 : newId;
        newId = _items.size()-1 < newId ? _items.size()-1 : newId;
        boolean change = newId != _currentId;
        _currentId = newId;
        return change;
    }

    /**
     * get the current item that this menu has selected
     * @return the selected menu item
     */
    public MenuItem getCurrentItem()
    {
        return _items.get(_currentId);
    }

    /**
     * gets the item at a give id
     * @param id the id of the menu item you're after
     * @return the item, null if out of bounds or non-existant
     */
    public MenuItem getItem(int id)
    {
        if(id < 0 || id >= _items.size())
            return null;

        return _items.get(id);
    }

    /**
     * get the current integer id of the selected menu item
     * @return
     */
    public int getCurrentId()
    {
        return _currentId;
    }

    /**
     * set the current select menu item ID
     * @param currentId then new id to use as thhe selected item
     * @return true if the given id is valid, false if its out of bounds
     */
    public boolean setCurrentId(int currentId)
    {
        if(currentId < 0 || currentId >= _items.size())
            return false;

        _currentId = currentId;
        return true;
    }

    /**
     * gets the arraylist of this sub menu's items. WARNING, THIS RETURNS THE ACTUAL ARRAYLIST OBJECT
     * @return the arraylist of this menu's items
     */
    public ArrayList<MenuItem> getItems()
    {
        return _items;
    }
}
