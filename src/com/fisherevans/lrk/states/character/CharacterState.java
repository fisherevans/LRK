package com.fisherevans.lrk.states.character;

import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.rpg.items.*;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.character.components.InventoryList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private float _foreHalfWidth, _foreHalfHeight;

    private InventoryType _currentType;
    private ArrayList<Item> _currentList;
    private Map<InventoryType, Integer> _currentPositions;

    private InventoryList _inventoryList;

    public CharacterState(LRKState lastState) throws SlickException
    {
        super(StateLibrary.getTempID());
        _lastState = lastState;
        _inventoryList = new InventoryList(this);

        addUIComponent(_inventoryList);
    }

    @Override
    public void init() throws SlickException
    {
        resize();
        _currentPositions = new HashMap<InventoryType, Integer>();
        _currentPositions.put(InventoryType.Equipment, 0);
        _currentPositions.put(InventoryType.Consumables, 0);
    }

    @Override
    public void enter() throws SlickException
    {

    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        _lastState.render(gfx);

        GFX.fill(gfx, new Color(0, 0, 0, 0.75f));
    }

    @Override
    public void exit() throws SlickException
    {

    }

    /** Use as e1 compared to e2. (e1's color compared to e2) */
    public Color getStatPowerColor(Equipment e1, Equipment e2)
    {
        if(e2 == null) return Color.green;
        if(e1.getPower() > e2.getPower()) return Color.green;
        else if(e2.getPower() > e1.getPower()) return Color.red;
        else return Color.white;
    }

    /** Use as e1 compared to e2. (e1's color compared to e2) */
    public Color getStatDefenceColor(Equipment e1, Equipment e2)
    {
        if(e2 == null) return Color.green;
        if(e1.getDefence() > e2.getDefence()) return Color.green;
        else if(e2.getDefence() > e1.getDefence()) return Color.red;
        else return Color.white;
    }

    private void clip(float x, float y, float width, float height)
    {
        Game.getContainer().getGraphics().setClip(
                (int) (x * DisplayManager.getBackgroundScale()),
                (int) (y * DisplayManager.getBackgroundScale()),
                (int) (width * DisplayManager.getBackgroundScale()),
                (int) (height * DisplayManager.getBackgroundScale()));
    }

    private void unClip()
    {
        Game.getContainer().getGraphics().clearClip();
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
        StateLibrary.setActiveState(_lastState);
    }

    public float getForeHalfWidth()
    {
        return _foreHalfWidth;
    }

    public float getForeHalfHeight()
    {
        return _foreHalfHeight;
    }
}
