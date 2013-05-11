package com.fisherevans.lrk.states.options;

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

    public Menu(String text, String description)
    {
        super(text, description);
        _items = new ArrayList<>();
    }

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
    public boolean isSelected()
    {
        return false;
    }

    public boolean shiftCurrentId(int shift)
    {
        int newId = _currentId + shift;
        newId = 0 > newId ? 0 : newId;
        newId = _items.size()-1 < newId ? _items.size()-1 : newId;
        boolean change = newId != _currentId;
        _currentId = newId;
        return change;
    }

    public MenuItem getCurrentItem()
    {
        return _items.get(_currentId);
    }

    public MenuItem getItem(int id)
    {
        return _items.get(id);
    }

    public int getCurrentId()
    {
        return _currentId;
    }

    public void setCurrentId(int currentId)
    {
        _currentId = currentId;
    }

    public ArrayList<MenuItem> getItems()
    {
        return _items;
    }
}
