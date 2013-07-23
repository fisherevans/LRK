package com.fisherevans.lrk.states;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.managers.DisplayManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/22/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterState extends LRKState
{
    public enum InventoryType { Equipment, Consumables }

    private LRKState _lastState;

    private float _halfWidth, _halfHeight;
    private int _halfDisplayWidth, _halfDisplayHeight;

    private InventoryType _currentType;
    private Image _renderBuffer;

    private ArrayList<String> _equipment, _consumables;



    public CharacterState(LRKState lastState) throws SlickException
    {
        super(StateLibrary.getTempID());
        _lastState = lastState;
    }

    @Override
    public void init() throws SlickException
    {
        resize();
        _currentType = InventoryType.Equipment;

        _equipment = new ArrayList<>(Arrays.asList(new String[] { "Broad Sword", "Iron Dagger", "Wood Shield", "Magma Wand", "Elven Plate", "Leather Boots", "Ice Staff" }));
        _consumables = new ArrayList<>(Arrays.asList(new String[] { "Weak Mana Potion", "Weapon Poison", "Fire Rune", "Strong Health Potion"}));
    }

    @Override
    public void enter() throws SlickException
    {

    }

    @Override
    public void exit() throws SlickException
    {

    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        _lastState.render(gfx);

        renderInventory(gfx, _currentType);
        renderComparison(gfx);
        renderStats(gfx);

        if(_currentType == InventoryType.Equipment)
            renderEquipmentPanel(gfx);
        else
            renderConsumablesPanel(gfx);
    }

    private final int INV_TAB_HEIGHT = 24;
    public void renderInventory(Graphics gfx, InventoryType type)
    {
        gfx.setClip(0, 0, _halfDisplayWidth, _halfDisplayHeight);

        gfx.setColor(Color.orange.multiply(new Color(1f, 1f, 1f, 0.5f)));
        gfx.fillRect(0, 0, _halfWidth, INV_TAB_HEIGHT);
        gfx.setColor(Color.red.multiply(new Color(1f, 1f, 1f, 0.5f)));
        gfx.fillRect(0, INV_TAB_HEIGHT, _halfWidth, _halfHeight-INV_TAB_HEIGHT);

        GFX.drawTextAbsolute(10, 10, Resources.getFont(2), Color.white, type.toString());

        ArrayList<String> currentList = type == InventoryType.Equipment ? _equipment : _consumables;

        for(int id = 0;id < currentList.size();id++)
        {
            GFX.drawTextAbsolute(10, INV_TAB_HEIGHT + 10 + id*24, Resources.getFont(1), Color.white, currentList.get(id));
        }

        gfx.clearClip();
    }

    public void renderEquipmentPanel(Graphics gfx)
    {
        gfx.setClip(_halfDisplayWidth, 0, _halfDisplayWidth, _halfDisplayHeight);

        gfx.setColor(Color.blue.multiply(new Color(1f, 1f, 1f, 0.5f)));
        gfx.fillRect(_halfWidth, 0, _halfWidth, _halfHeight);

        gfx.clearClip();
    }

    public void renderConsumablesPanel(Graphics gfx)
    {
        gfx.setClip(_halfDisplayWidth, 0, _halfDisplayWidth, _halfDisplayHeight);

        gfx.setColor(Color.blue.multiply(new Color(1f, 1f, 1f, 0.5f)));
        gfx.fillRect(_halfWidth, 0, _halfWidth, _halfHeight);

        gfx.clearClip();
    }

    public void renderComparison(Graphics gfx)
    {
        gfx.setClip(0, _halfDisplayHeight, _halfDisplayWidth, _halfDisplayHeight);

        gfx.setColor(Color.green.multiply(new Color(1f, 1f, 1f, 0.5f)));
        gfx.fillRect(0, _halfHeight, _halfWidth, _halfHeight);

        gfx.clearClip();
    }

    public void renderStats(Graphics gfx)
    {
        gfx.setClip(_halfDisplayWidth, _halfDisplayHeight, _halfDisplayWidth, _halfDisplayHeight);

        gfx.setColor(Color.yellow.multiply(new Color(1f, 1f, 1f, 0.5f)));
        gfx.fillRect(_halfWidth, _halfHeight, _halfWidth, _halfHeight);

        gfx.clearClip();
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resize()
    {
        _halfWidth = DisplayManager.getRenderWidth()/2f;
        _halfHeight = DisplayManager.getRenderHeight()/2f;

        _halfDisplayWidth = DisplayManager.getWindowWidth()/2;
        _halfDisplayHeight = DisplayManager.getWindowHeight()/2;

    }

    public void keyBack()
    {
        StateLibrary.setActiveState(_lastState);
    }

    public void keyLeft()
    {
        swapType();
    }

    public void keyRight()
    {
        swapType();
    }

    private void swapType()
    {
        if(_currentType == InventoryType.Equipment)
            _currentType = InventoryType.Consumables;
        else
            _currentType = InventoryType.Equipment;
    }
}
