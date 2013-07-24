package com.fisherevans.lrk.states;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.rpg.Inventory;
import com.fisherevans.lrk.rpg.items.*;
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

    private Inventory _inventory;

    public CharacterState(LRKState lastState) throws SlickException
    {
        super(StateLibrary.getTempID());
        _lastState = lastState;

        _inventory = new Inventory();

        _inventory.getItems().add(new ArmorItem("Steel Plate", "A heavy duty chest plate", ItemType.Chest, null, 1, 7));
        _inventory.getItems().add(new ArmorItem("Wizard's Hat", "A wizard's hat", ItemType.Head, null, 3, 0));
        _inventory.getItems().add(new ArmorItem("Leather Boots", "A simple set of leather boots", ItemType.Legs, null, 1, 3));
        _inventory.getItems().add(new ArmorItem("Novice Robes", "A novice's robes", ItemType.Chest, null, 4, 0));

        _inventory.getItems().add(new WeaponItem("Steal Shortsword", "A simple sword", ItemType.MainHand, null, 3, 0, 0.4f));
        _inventory.getItems().add(new WeaponItem("War Axe", "A bloody axe", ItemType.MainHand, null, 4, 0, 0.8f));
        _inventory.getItems().add(new WeaponItem("Wood Shield", "Won't do much for you", ItemType.OffHand, null, 2, 0, 5f));
        _inventory.getItems().add(new WeaponItem("Fire Staff", "A staff that shoots fireballs", ItemType.MainHand, null, 2, 0, 1f));
        _inventory.getItems().add(new WeaponItem("Arcane Talisman", "Creates a ward that increases your magic by 10", ItemType.OffHand, null, 2, 0, 15f));

        _inventory.getItems().add(new ConsumableItem("Rotten Flesh", "I wouldn't eat this...", null));
        _inventory.getItems().add(new ConsumableItem("Weak Mana Potion", "This isn't actually used in the game", null));
        _inventory.getItems().add(new ConsumableItem("Weapon Poison", "Applies a poison to a melee weapon", null));
        _inventory.getItems().add(new ConsumableItem("Strong Health Potion", "Heals you for 40% of your health", null));
        _inventory.getItems().add(new ConsumableItem("Steel Skin Potion", "Increases your endurance by 10", null));
        _inventory.getItems().add(new ConsumableItem("Holy Water", "Not sure what this does", null));
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

        int itemId = 0;
        for(Item item:_inventory.getItems())
        {
            if((type == InventoryType.Consumables && item.getItemType() == ItemType.Consumable) ||
                    (type == InventoryType.Equipment && item.getItemType() != ItemType.Consumable))
            {
                GFX.drawTextAbsolute(10, INV_TAB_HEIGHT + 10 + itemId*24, Resources.getFont(1), Color.white, item.getName());
                itemId++;
            }
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
