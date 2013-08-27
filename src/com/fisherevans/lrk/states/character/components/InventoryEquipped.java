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
    
    public final int EQUIPMENT_BASE_X = (HEIGHT-TITLE_HEIGHT)/4;
    public final int EQUIPMENT_BASE_Y = EQUIPMENT_BASE_X + TITLE_HEIGHT;
    public final int EQUIPMENT_DELTA = EQUIPMENT_BASE_X;
    

    private CharacterState _parent;
    private Image _emptyImage;

    private Color _highlightBlue = new Color(0.8f, 0.8f, 1f);
    private Color _highlightRed = new Color(1f, 0.8f, 0.8f);

    private int _iconScale = 2;

    public InventoryEquipped(CharacterState parent)
    {
        super(parent, true, false);
        _parent = parent;

        _emptyImage = Resources.getImage("equipment/empty20x20");
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

        // armor
        drawEquipment(gfx, Equipment.Position.Head, // HEAD
                EQUIPMENT_BASE_X+EQUIPMENT_DELTA,
                EQUIPMENT_BASE_Y);
        drawEquipment(gfx, Equipment.Position.Chest, // CHEST
                EQUIPMENT_BASE_X+EQUIPMENT_DELTA,
                EQUIPMENT_BASE_Y+EQUIPMENT_DELTA*1);
        drawEquipment(gfx, Equipment.Position.Legs, // LEGS
                EQUIPMENT_BASE_X+EQUIPMENT_DELTA,
                EQUIPMENT_BASE_Y+EQUIPMENT_DELTA*2);
        
        // weapons
        drawEquipment(gfx, Equipment.Position.MainHand, // MAIN HAND
                EQUIPMENT_BASE_X,
                EQUIPMENT_BASE_Y+EQUIPMENT_DELTA*1);
        drawEquipment(gfx, Equipment.Position.OffHand, // OFF HAND
                EQUIPMENT_BASE_X+EQUIPMENT_DELTA*2,
                EQUIPMENT_BASE_Y+EQUIPMENT_DELTA*1);

        // light
        drawEquipment(gfx, Equipment.Position.Light, // OFF HAND
                EQUIPMENT_BASE_X+EQUIPMENT_DELTA*2,
                EQUIPMENT_BASE_Y+EQUIPMENT_DELTA*0);
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
                Weapon mainHand = (Weapon) Game.lrk.getPlayer().getEntity().getEquipment(Equipment.Position.MainHand);
                if(mainHand != null && mainHand.isTwoHanded())
                    color = _highlightRed;
            }
            else if(position == Equipment.Position.MainHand)
            {
                Weapon mainHand = (Weapon) Game.lrk.getPlayer().getEntity().getEquipment(Equipment.Position.MainHand);
                if(mainHand != null && mainHand.isTwoHanded() && selected.getPosition() == Equipment.Position.OffHand)
                    color = _highlightRed;
            }

            if(position == ((Equipment)_parent.getInventoryList().getCurrentItem()).getPosition())
                color = _highlightBlue;
        }

        GFX.drawImageCenteredRounded(startX()+x, startY()+y, _emptyImage, _iconScale, color);

        Equipment equipment = Game.lrk.getPlayer().getEntity().getEquipment(position);
        if(equipment != null)
        {
            GFX.drawImageCenteredRounded(startX() + x, startY() + y, equipment.getImage(), _iconScale, Color.white);
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
