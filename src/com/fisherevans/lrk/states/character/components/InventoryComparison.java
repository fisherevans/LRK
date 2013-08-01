package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.rpg.items.Equipment;
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
    public final int WIDTH = 360;
    public final int HEIGHT = 100;
    public final int X_OFFSET = 0;
    public final int Y_OFFSET = 200;

    public final int MARGIN = 8;
    public final int PADDING = 8;
    public final int CONTENT_PADDING = 4;

    private final Color RATING_SAME = new Color(1f, 1f, 1f);
    private final Color RATING_BETTER = new Color(0.4f, 1f, 0.4f);
    private final Color RATING_WORSE = new Color(1f, 0.4f, 0.4f);

    private final Color IS_ENCHANTED = new Color(0.4f, 0.5f, 1f);

    private CharacterState _parent;

    private Image _power, _defence;

    public InventoryComparison(CharacterState parent)
    {
        super(parent, false, false);
        _parent = parent;
        _power = Resources.getImage("res/images/gui/power_icon.png");
        _defence = Resources.getImage("res/images/gui/defence_icon.png");
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

        Equipment selected = (Equipment) _parent.getInventoryList().getCurrentItem();

        Equipment equipped = null;
        if(Game.lrk.getPlayer().getEquipmentMap() != null)
            equipped = Game.lrk.getPlayer().getEquipmentMap().get(selected.getPosition());

        GFX.drawTextAbsolute(startContentX()+64+PADDING, startContentY(), Resources.getFont(2), Color.white, selected.getName());
        GFX.drawImage((int)startContentX(), (int)(startContentY()+2), 64, 64, selected.getImage());

        GFX.drawImage((int)(startContentX()+64+PADDING), (int)(startContentY()+4 + (16 + CONTENT_PADDING)), _power);
        GFX.drawTextAbsolute(startContentX()+64+PADDING+20, startContentY()+4 + (16 + CONTENT_PADDING)-1, Resources.getFont(2), getPowerComparisonColor(selected, equipped), selected.getPower()+"");

        GFX.drawImage((int)(startContentX()+64+PADDING+64), (int)(startContentY()+4 + (16 + CONTENT_PADDING)), _defence);
        GFX.drawTextAbsolute(startContentX()+64+PADDING + 64+20, startContentY()+4 + (16 + CONTENT_PADDING)-1, Resources.getFont(2), getDefenceComparisonColor(selected, equipped), selected.getDefence()+"");

        if(selected.isEnchanted())
        {
            GFX.drawTextAbsolute(startContentX()+64+PADDING, startContentY()+8 + (16 + CONTENT_PADDING) * 2, Resources.getFont(1), IS_ENCHANTED, selected.getEnchantment().getName());
            GFX.drawTextAbsolute(startContentX()+64+PADDING, startContentY()+8+(16+CONTENT_PADDING)*2+8+CONTENT_PADDING, Resources.getFont(1), Color.lightGray, selected.getEnchantment().getDescription());
        }
    }

    private Color getPowerComparisonColor(Equipment selected, Equipment equipped)
    {
        if(equipped == null)
        {
            if(selected.getPower() <= 0)
                return RATING_SAME;
            else
                return RATING_BETTER;
        }
        else if(selected.getPower() > equipped.getPower())
            return RATING_BETTER;
        else if(equipped.getPower() > selected.getPower())
            return RATING_WORSE;
        else
            return RATING_SAME;
    }

    private Color getDefenceComparisonColor(Equipment selected, Equipment equipped)
    {
        if(equipped == null)
        {
            if(selected.getDefence() <= 0)
                return RATING_SAME;
            else
                return RATING_BETTER;
        }
        else if(selected.getDefence() > equipped.getDefence())
            return RATING_BETTER;
        else if(equipped.getDefence() > selected.getDefence())
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
