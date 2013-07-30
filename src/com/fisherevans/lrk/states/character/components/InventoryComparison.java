package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Item;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.character.CharacterState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
    public final int WIDTH = 300;
    public final int HEIGHT = 100;
    public final int X_OFFSET = 0;
    public final int Y_OFFSET = 150;

    public final int MARGIN = 10;

    private CharacterState _parent;

    public InventoryComparison(CharacterState parent)
    {
        super(parent, false, false);
        _parent = parent;
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
        if(Game.lrk.getPlayer().getEquipment() != null)
            equipped = Game.lrk.getPlayer().getEquipment().get(selected.getPosition());


        GFX.drawImage((int)(startX()+MARGIN*2), (int)(startY()+MARGIN*2),
                selected.getImage().getWidth()*2, selected.getImage().getHeight()*2,
                selected.getImage());
        GFX.drawTextAbsolute((int)(startX()+selected.getImage().getWidth()*2+MARGIN*3), (int)(startY()+MARGIN*2), Resources.getFont(1), Color.white, selected.getName());

        if(equipped != null)
        {
            int width = equipped.getImage().getWidth();
            int height = equipped.getImage().getHeight();
            GFX.drawImage((int)(startX()+WIDTH-width-MARGIN*2), (int)(startY()+HEIGHT-height-MARGIN*2),equipped.getImage());
            GFX.drawTextAbsolute((int)(startX()+WIDTH-width-MARGIN*2-width-70), (int)(startY()+HEIGHT-height-MARGIN*3+height), Resources.getFont(1), Color.lightGray, equipped.getName());
        }
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
}
