package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.rpg.items.Consumable;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Item;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.character.CharacterState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryList extends UIComponent
{
    public final int WIDTH = 360;
    public final int HEIGHT = 300;
    public final int X_OFFSET = 0;
    public final int Y_OFFSET = 100;

    public final int TITLE_HEIGHT = 32;

    public final int LIST_PADDING = 8;
    public final int LIST_MARGIN = 8;
    public final int LIST_LINE_HEIGHT = 28;
    public final int LIST_CENTER_Y = (TITLE_HEIGHT+LIST_MARGIN+HEIGHT)/2 - 2; // -2 to offset it not being centered

    public final Color LIST_EQUIPPED = new Color(0.3f, 0.4f, 0.6f);
    public final Color LIST_NOT_EQUIPPED = new Color(0.5f, 0.5f, 0.5f);
    public final Color LIST_SELECTED_EQUIPPED = new Color(0.4f, 0.6f, 1f);
    public final Color LIST_SELECTED_NOT_EQUIPPED = new Color(1f, 1f, 1f);

    public final int SCROLL_HEIGHT = HEIGHT-TITLE_HEIGHT-LIST_MARGIN*2-LIST_PADDING*2;
    public final int SCROLL_WIDTH = 10;
    public final int SCROLL_Y = TITLE_HEIGHT+LIST_PADDING+LIST_MARGIN;
    public final int SCROLL_X = WIDTH - LIST_MARGIN - LIST_PADDING - SCROLL_WIDTH;

    public final int ICON_SIZE = 16;

    private CharacterState _parent;

    private Class _tab;
    private ArrayList<Item> _currentItems;
    private Map<Class, Integer> _position;

    public InventoryList(CharacterState parent)
    {
        super(parent, true, false);
        _parent = parent;

        _tab = Equipment.class;
        _position = new HashMap<>();
        _position.put(Equipment.class, 0);
        _position.put(Consumable.class, 0);
        fetchCurrentItems();
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        //gfx.setColor(Color.blue);
        //gfx.fillRect(startX(), startY(), WIDTH, HEIGHT);

        // ACTUAL LIST
        gfx.setColor(new Color(0.3f, 0.3f, 0.3f, 0.5f));
        gfx.fillRect(startX()+LIST_MARGIN, startY()+LIST_MARGIN+TITLE_HEIGHT, WIDTH-LIST_MARGIN*2, HEIGHT-TITLE_HEIGHT-LIST_MARGIN*2);
        GFX.drawText(startX(), startY(), WIDTH, TITLE_HEIGHT,
                GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(3), Color.white, getTabName());

        _parent.clip(startX() + LIST_MARGIN,
                startY() + TITLE_HEIGHT + LIST_MARGIN,
                WIDTH - LIST_MARGIN * 2,
                HEIGHT - TITLE_HEIGHT - LIST_MARGIN*2,
                DisplayManager.getForegroundScale());

        int lineX, lineY;
        for(int id = 0;id < _currentItems.size();id++)
        {
            Item item = _currentItems.get(id);
            lineX = (int) (startX() + LIST_MARGIN + LIST_PADDING*3);
            lineY = (int) (startY() + LIST_CENTER_Y - (LIST_LINE_HEIGHT/2 ) + ((id-getCurrentPosition())*LIST_LINE_HEIGHT));
            GFX.drawImage(lineX, lineY, item.getImage());
            GFX.drawTextCenteredV(lineX + ICON_SIZE + LIST_PADDING*2, lineY, ICON_SIZE,
                    Resources.getFont(1),
                    getItemColor(item, id == getCurrentPosition()),
                    getItemName(item, id == getCurrentPosition()));
        }
        _parent.unClip();

        gfx.setColor(Color.darkGray);
        gfx.fillRect(startX()+SCROLL_X, startY()+SCROLL_Y, SCROLL_WIDTH, SCROLL_HEIGHT);

        float scrollBarHeight = SCROLL_HEIGHT/((float)_currentItems.size());
        float scrollBarY = getCurrentPosition()*scrollBarHeight;
        gfx.setColor(Color.white);
        gfx.fillRect(startX()+SCROLL_X, startY()+SCROLL_Y+scrollBarY, SCROLL_WIDTH, scrollBarHeight);
    }

    @Override
    public void resize()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyUp()
    {
        decreaseCurrentPosition();
    }

    @Override
    public void keyDown()
    {
        increaseCurrentPosition();
    }

    @Override
    public void keyLeft()
    {
        swapTab();
    }

    @Override
    public void keyRight()
    {
        swapTab();
    }

    @Override
    public void keySelect()
    {
        if(_tab == Equipment.class)
        {
            if(((Equipment)getCurrentItem()).isEquipped())
                Game.lrk.getPlayer().getEquipmentMap().remove(((Equipment) getCurrentItem()).getPosition());
            else
            Game.lrk.getPlayer().equip((Equipment)getCurrentItem());
            //decreaseCurrentPosition();
            //fetchCurrentItems();
        }
    }

    private float startX()
    {
        return _parent.getForeHalfWidth()-WIDTH+X_OFFSET;
    }

    private float startY()
    {
        return _parent.getForeHalfHeight()-HEIGHT+Y_OFFSET;
    }

    public void fetchCurrentItems()
    {
        if(_tab == Equipment.class)
        {
            _currentItems = Game.lrk.getPlayer().getInventory().getEquipment();
            /*_currentItems = new ArrayList<>();
            for(Item item:Game.lrk.getPlayer().getInventory().getEquipmentMap())
                if(!((Equipment)item).isEquipped())
                    _currentItems.add(item);*/
        }
        else if(_tab == Consumable.class)
        {
            _currentItems = Game.lrk.getPlayer().getInventory().getConsumables();
        }
    }

    public void swapTab()
    {
        if(_tab == Equipment.class) _tab = Consumable.class;
        else if (_tab == Consumable.class) _tab = Equipment.class;
        fetchCurrentItems();
    }

    private int getCurrentPosition()
    {
        return _position.get(_tab);
    }

    public void decreaseCurrentPosition()
    {
        if(getCurrentPosition() <= 0)
            return;
        else
            _position.put(_tab, getCurrentPosition()-1);
    }

    public void increaseCurrentPosition()
    {
        if(getCurrentPosition() >= _currentItems.size()-1)
            return;
        else
            _position.put(_tab, getCurrentPosition()+1);
    }

    public Color getItemColor(Item item, boolean selected)
    {
        if(selected)
        {
            if(item instanceof Equipment && ((Equipment)item).isEquipped()) return LIST_SELECTED_EQUIPPED;
            else  return LIST_SELECTED_NOT_EQUIPPED;
        }
        else
        {
            if(item instanceof Equipment && ((Equipment)item).isEquipped()) return LIST_EQUIPPED;
            else  return LIST_NOT_EQUIPPED;
        }
    }

    public String getItemName(Item item, boolean selected)
    {
        String name = item.getName();
        if(item instanceof Equipment)
        {
            if(((Equipment) item).isEnchanted()) name += " *";
            if(((Equipment) item).isEquipped()) name += " <";
        }
        return name;
    }

    public Item getCurrentItem()
    {
        return _currentItems.get(getCurrentPosition());
    }

    private String getTabName()
    {
        if(_tab == Equipment.class) return "Equipment";
        if(_tab == Consumable.class) return "Consumables";
        else return "ERROR";
    }

    public Class getTab()
    {
        return _tab;
    }
}
