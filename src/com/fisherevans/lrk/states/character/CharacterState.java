package com.fisherevans.lrk.states.character;

import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.character.components.InventoryComparison;
import com.fisherevans.lrk.states.character.components.InventoryEquipped;
import com.fisherevans.lrk.states.character.components.InventoryList;
import com.fisherevans.lrk.states.character.components.InventoryStats;
import com.fisherevans.lrk.states.transitions.SimpleFadeTransition;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/22/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterState extends LRKState
{
    private LRKState _lastState;

    private float _foreHalfWidth, _foreHalfHeight;

    private InventoryList _inventoryList;
    private InventoryComparison _inventoryComparisonSelected;
    private InventoryComparison _inventoryComparisonEquipped;
    private InventoryEquipped _inventoryEquipped;
    private InventoryStats _inventoryStats;

    public CharacterState(LRKState lastState) throws SlickException
    {
        super();
        _lastState = lastState;

        _inventoryList = new InventoryList(this);
        _inventoryComparisonSelected = new InventoryComparison(this, true, 0, 200);
        _inventoryComparisonEquipped = new InventoryComparison(this, false, InventoryComparison.WIDTH, 200);
        _inventoryEquipped = new InventoryEquipped(this);
        _inventoryStats = new InventoryStats(this);

        addUIComponent(_inventoryList);
        addUIComponent(_inventoryComparisonSelected);
        addUIComponent(_inventoryComparisonEquipped);
        addUIComponent(_inventoryEquipped);
    }

    @Override
    public void init() throws SlickException
    {
        resize();
    }

    @Override
    public void enter() throws SlickException
    {

    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        _lastState.render(gfx);

        GFX.fill(gfx, new Color(0, 0, 0, 0.65f));
    }

    @Override
    public void exit() throws SlickException
    {

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
        _foreHalfWidth = DisplayManager.getForegroundWidth()/2f;
        _foreHalfHeight = DisplayManager.getForegroundHeight()/2f;
    }

    public void keyBack()
    {
        try {
            StateLibrary.setNewActiveState(new SimpleFadeTransition(this.getID(), _lastState.getID(), 0.5f));
        } catch (SlickException e) {
            e.printStackTrace();
            StateLibrary.setActiveState(_lastState.getID());
        }
    }

    public float getForeHalfWidth()
    {
        return _foreHalfWidth;
    }

    public float getForeHalfHeight()
    {
        return _foreHalfHeight;
    }

    public InventoryList getInventoryList()
    {
        return _inventoryList;
    }
}
