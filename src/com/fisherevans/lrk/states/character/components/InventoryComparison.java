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
 * Date: 7/29/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryComparison extends UIComponent
{
    public static final int WIDTH = 360;
    public static final int HEIGHT = 100;
    public int X_OFFSET = 0;
    public int Y_OFFSET = 200;

    public final int MARGIN = 8;
    public final int PADDING = 8;
    public final int CONTENT_PADDING = 4;

    private final Color RATING_SAME = new Color(1f, 1f, 1f);
    private final Color RATING_BETTER = new Color(0.4f, 1f, 0.4f);
    private final Color RATING_WORSE = new Color(1f, 0.4f, 0.4f);

    private final Color IS_ENCHANTED = new Color(0.4f, 0.5f, 1f);

    private CharacterState _parent;

    private Image _power, _defence, _time;

    private boolean _displaySelected;

    public InventoryComparison(CharacterState parent, boolean displaySelected, int xOffset, int yOffset)
    {
        super(parent, false, false);
        _parent = parent;
        _power = Resources.getImage("gui/power_icon");
        _defence = Resources.getImage("gui/defence_icon");
        _time = Resources.getImage("gui/time_icon");

        _displaySelected = displaySelected;

        X_OFFSET = xOffset;
        Y_OFFSET = yOffset;
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        if(_parent.getInventoryList().getTab() != Equipment.class)
            return;

        gfx.setColor(new Color(0.3f, 0.3f, 0.3f, 0.5f));
        gfx.fillRect(startX()+MARGIN, startY()+MARGIN, WIDTH-MARGIN*2, HEIGHT-MARGIN*2);

        Equipment displayItem = null;
        Equipment compareItem = null;

        if(_displaySelected)
        {
            displayItem = (Equipment) _parent.getInventoryList().getCurrentItem();
            compareItem = Game.lrk.getPlayer().getEquipment(displayItem.getPosition());
        }
        else
        {
            compareItem = (Equipment) _parent.getInventoryList().getCurrentItem();
            displayItem = Game.lrk.getPlayer().getEquipment(compareItem.getPosition());
        }

        if(displayItem != null)
        {
            GFX.drawTextAbsolute(startContentX() + 64 + PADDING*2, startContentY() - 2, Resources.getFont(2), Color.white, displayItem.getName());
            GFX.drawImage((int)startContentX(), (int)(startContentY()+2), 64, 64, displayItem.getImage());

            GFX.drawImage((int) (startContentX() + 64 + PADDING*2), (int) (startContentY() + 4 + (16 + CONTENT_PADDING)), _power);
            GFX.drawTextAbsolute(startContentX()+64+PADDING*2+20, startContentY()+4 + (16 + CONTENT_PADDING)-1, Resources.getFont(2), getPowerComparisonColor(displayItem, compareItem), displayItem.getPower()+"");

            GFX.drawImage((int) (startContentX() + 64 + PADDING*2 + 64), (int) (startContentY() + 4 + (16 + CONTENT_PADDING)), _defence);
            GFX.drawTextAbsolute(startContentX()+64+PADDING*2 + 64+20, startContentY()+4 + (16 + CONTENT_PADDING)-1, Resources.getFont(2), getDefenceComparisonColor(displayItem, compareItem), displayItem.getDefence()+"");

            if(displayItem instanceof Weapon)
            {
                GFX.drawImage((int) (startContentX() + 64 + PADDING*2 + 64*2), (int) (startContentY() + 4 + (16 + CONTENT_PADDING)), _time);
                GFX.drawTextAbsolute(startContentX()+64+PADDING*2 + 64*2+20, startContentY()+4 + (16 + CONTENT_PADDING)-1, Resources.getFont(2), getTimeComparisonColor((Weapon)displayItem, (Weapon)compareItem), ((Weapon)displayItem).getSpeed()+"");

                if(((Weapon)displayItem).isTwoHanded())
                    GFX.drawTextAbsolute(startContentX()+64+PADDING*2+64*2, startContentY()+8 + (16 + CONTENT_PADDING) * 2, Resources.getFont(1), Color.white, "Two-Handed");
            }

            if(displayItem.isEnchanted())
            {
                GFX.drawTextAbsolute(startContentX()+64+PADDING*2, startContentY()+8 + (16 + CONTENT_PADDING) * 2, Resources.getFont(1), IS_ENCHANTED, displayItem.getEnchantment().getName());
                GFX.drawTextAbsolute(startContentX()+64+PADDING*2, startContentY()+8+(16+CONTENT_PADDING)*2+8+CONTENT_PADDING, Resources.getFont(1), Color.lightGray, displayItem.getEnchantment().getDescription());
            }
        }
    }

    private Color getPowerComparisonColor(Equipment display, Equipment compare)
    {
        if(compare == null)
        {
            if(display.getPower() <= 0)
                return RATING_SAME;
            else
                return RATING_BETTER;
        }
        else if(display.getPower() > compare.getPower())
            return RATING_BETTER;
        else if(compare.getPower() > display.getPower())
            return RATING_WORSE;
        else
            return RATING_SAME;
    }

    private Color getDefenceComparisonColor(Equipment display, Equipment compare)
    {
        if(compare == null)
        {
            if(display.getDefence() <= 0)
                return RATING_SAME;
            else
                return RATING_BETTER;
        }
        else if(display.getDefence() > compare.getDefence())
            return RATING_BETTER;
        else if(compare.getDefence() > display.getDefence())
            return RATING_WORSE;
        else
            return RATING_SAME;
    }

    private Color getTimeComparisonColor(Weapon display, Weapon compare)
    {
        if(compare == null)
            return RATING_BETTER;

        else if(display.getSpeed() > compare.getSpeed())
            return RATING_BETTER;
        else if(compare.getSpeed() > display.getSpeed())
            return RATING_WORSE;
        else
            return RATING_SAME;
    }

    @Override
    public void resize()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private float startX()
    {
        return _parent.getForeHalfWidth()-WIDTH+X_OFFSET;
    }

    private float startY()
    {
        return _parent.getForeHalfHeight()-HEIGHT+Y_OFFSET;
    }

    private float startContentX()
    {
        return startX()+MARGIN+PADDING;
    }

    private float startContentY()
    {
        return startY()+MARGIN+PADDING;
    }
}
