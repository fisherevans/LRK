package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Weapon;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.character.CharacterState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryEquipped extends UIComponent
{
    public final int WIDTH = 360;
    public final int HEIGHT = 250;
    public final int X_OFFSET = WIDTH;
    public final int Y_OFFSET = 50;

    public final int TITLE_HEIGHT = 32;

    public final int CONTENT_HEIGHT = HEIGHT-TITLE_HEIGHT;

    public final int LIST_PADDING = 8;
    public final int LIST_MARGIN = 8;

    private CharacterState _parent;
    private Image _emptyImage;

    private Color _highlightBlue = new Color(0.8f, 0.8f, 1f);
    private Color _highlightRed = new Color(1f, 0.8f, 0.8f);

    private int _iconScale = 2;

    public InventoryEquipped(CharacterState parent)
    {
        super(parent, true, false);
        _parent = parent;

        _emptyImage = Resources.getImage("res/images/equipment/empty20x20.png");
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        gfx.setColor(new Color(0.3f, 0.3f, 0.3f, 0.5f));
        gfx.fillRect(startX()+LIST_MARGIN, startY()+LIST_MARGIN+TITLE_HEIGHT, WIDTH-LIST_MARGIN*2, HEIGHT-TITLE_HEIGHT-LIST_MARGIN*2);
        GFX.drawText(startX(), startY(), WIDTH, TITLE_HEIGHT,
                GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(3), Color.white, Game.lrk.getPlayer().getEntity().getName());

        drawEquipment(gfx, Equipment.Position.Head, WIDTH/2, CONTENT_HEIGHT/4*1);
        drawEquipment(gfx, Equipment.Position.Chest, WIDTH/2, CONTENT_HEIGHT/4*2);
        drawEquipment(gfx, Equipment.Position.Legs, WIDTH/2, CONTENT_HEIGHT/4*3);
        drawEquipment(gfx, Equipment.Position.MainHand, WIDTH/4*1, CONTENT_HEIGHT/2);
        drawEquipment(gfx, Equipment.Position.OffHand, WIDTH/4*3, CONTENT_HEIGHT/2);
    }

    @Override
    public void resize()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyUp()
    {

    }

    @Override
    public void keyDown()
    {

    }

    @Override
    public void keyLeft()
    {

    }

    @Override
    public void keyRight()
    {

    }

    @Override
    public void keySelect()
    {

    }

    private void drawEquipment(Graphics gfx, Equipment.Position position, int x, int y)
    {
        Equipment selected = null;
        if(_parent.getInventoryList().getCurrentItem() instanceof Equipment)
            selected = (Equipment)_parent.getInventoryList().getCurrentItem();

        Color color = Color.white;

        if(selected != null)
        {
            if(position == Equipment.Position.OffHand)
            {
                Weapon mainHand = (Weapon) Game.lrk.getPlayer().getEquipment(Equipment.Position.MainHand);
                if(mainHand != null && mainHand.isTwoHanded())
                    color = _highlightRed;
            }
            else if(position == Equipment.Position.MainHand)
            {
                Weapon mainHand = (Weapon) Game.lrk.getPlayer().getEquipment(Equipment.Position.MainHand);
                if(mainHand != null && mainHand.isTwoHanded() && selected.getPosition() == Equipment.Position.OffHand)
                    color = _highlightRed;
            }

            if(position == ((Equipment)_parent.getInventoryList().getCurrentItem()).getPosition())
                color = _highlightBlue;
        }

        GFX.drawImageCentered(startX()+x, startY()+y+TITLE_HEIGHT, _emptyImage, _iconScale, color);

        Equipment equipment = Game.lrk.getPlayer().getEquipment(position);
        if(equipment != null)
        {
            GFX.drawImageCentered(startX() + x, startY() + y + TITLE_HEIGHT, equipment.getImage(), _iconScale);
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
}
