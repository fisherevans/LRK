package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.rpg.items.Item;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.character.CharacterState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.List;
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
    public enum ListType { Equipment, Consumables }

    public final int WIDTH = 300;
    public final int HEIGHT = 200;
    public final int X_OFFSET = 0;
    public final int Y_OFFSET = 0;

    public final int TITLE_HEIGHT = 24;

    public final int LIST_PADDING = 10;
    public final int LIST_MARGIN = 10;
    public final int LIST_LINE_HEIGHT = 20;
    public final int LIST_CENTER_Y = (TITLE_HEIGHT+HEIGHT)/2;

    public final int SCROLL_HEIGHT = HEIGHT-TITLE_HEIGHT-LIST_MARGIN-LIST_PADDING*2;
    public final int SCROLL_WIDTH = 10;
    public final int SCROLL_Y = TITLE_HEIGHT+LIST_PADDING;
    public final int SCROLL_X = WIDTH - LIST_MARGIN - LIST_PADDING - SCROLL_WIDTH;

    public final int ICON_SIZE = 16;

    private CharacterState _parent;

    private ListType _tab = ListType.Equipment;

    private Map<ListType, Integer> _currentPosition;

    public InventoryList(CharacterState parent)
    {
        super(parent, true, false);
        _parent = parent;
        _currentPosition = new HashMap<>();
        _currentPosition.put(ListType.Equipment, 0);
        _currentPosition.put(ListType.Consumables, 0);
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        // box
        gfx.setColor(Color.red);
        gfx.fillRect(startX(), startY(), WIDTH, HEIGHT);
        // title
        gfx.setColor(Color.green);
        gfx.fillRect(startX(), startY(), WIDTH, TITLE_HEIGHT);
        // list box
        gfx.setColor(Color.yellow);
        gfx.fillRect(startX()+LIST_MARGIN, startY()+TITLE_HEIGHT, WIDTH-LIST_MARGIN*2, HEIGHT-TITLE_HEIGHT-LIST_MARGIN);
        // list content
        gfx.setColor(Color.blue);
        gfx.fillRect(startX()+LIST_MARGIN+LIST_PADDING, startY()+TITLE_HEIGHT, WIDTH-LIST_MARGIN*2-LIST_PADDING*2, HEIGHT-TITLE_HEIGHT-LIST_MARGIN);

        GFX.drawText(startX(), startY(), WIDTH, TITLE_HEIGHT, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(2), Color.white, _tab.toString());

        List<Item> items = _tab == ListType.Equipment ? Game.lrk.getPlayer().getInventory().getEquipment() : Game.lrk.getPlayer().getInventory().getConsumables();

        float lineX, lineY;
        Color textColor;
        int pastDrawnCount = 0;
        for(int id = 0;id < items.size();id++)
        {
            Item item = items.get(id);
            lineX = startX() + LIST_MARGIN + LIST_PADDING;
            lineY = startY() + LIST_CENTER_Y - (ICON_SIZE/2 ) + ((pastDrawnCount-getCurrentPosition())*LIST_LINE_HEIGHT);
            textColor = id == getCurrentPosition() ? Color.white : Color.gray;
            GFX.drawImage(lineX, lineY, ICON_SIZE, ICON_SIZE, item.getImage());
            GFX.drawTextCenteredV(lineX + ICON_SIZE + LIST_PADDING, lineY, ICON_SIZE, Resources.getFont(1), textColor, item.getName());
            pastDrawnCount++;
        }

        gfx.setColor(Color.lightGray);
        gfx.fillRect(startX()+SCROLL_X, startY()+SCROLL_Y, SCROLL_WIDTH, SCROLL_HEIGHT);

        float scrollBarHeight = SCROLL_HEIGHT/((float)items.size());
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
        movePositionUp();
    }

    @Override
    public void keyDown()
    {
        movePositionDown();
    }

    @Override
    public void keyLeft()
    {
        super.keyLeft();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void keyRight()
    {
        super.keyRight();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void keySelect()
    {
        super.keySelect();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private float startX()
    {
        return _parent.getForeHalfWidth()-WIDTH+X_OFFSET;
    }

    private float startY()
    {
        return _parent.getForeHalfHeight()-HEIGHT+Y_OFFSET;
    }

    private int getCurrentPosition()
    {
        return _currentPosition.get(_tab);
    }

    private void movePositionUp()
    {
        _currentPosition.put(_tab, getCurrentPosition()-1);
    }

    private void movePositionDown()
    {
        _currentPosition.put(_tab, getCurrentPosition()+1);
    }
}
