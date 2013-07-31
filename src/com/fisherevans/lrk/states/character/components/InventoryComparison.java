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

    public final int MARGIN = 8;
    public final int PADDING = 8;
    public final int CONTENT_PADDING = 8;

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

        GFX.drawText(startContentX(), startContentY(), 0, 16, GFX.TEXT_LEFT, GFX.TEXT_TOP, Resources.getFont(2), Color.white, selected.getName());
        GFX.drawText(startContentX()+32+CONTENT_PADDING, startContentY()+16+CONTENT_PADDING, 0, 16, GFX.TEXT_LEFT, GFX.TEXT_TOP, Resources.getFont(2), Color.lightGray, "Power/Defence");
        GFX.drawText(startContentX()+32+CONTENT_PADDING, startContentY()+16*2+CONTENT_PADDING*2, 0, 16, GFX.TEXT_LEFT, GFX.TEXT_TOP, Resources.getFont(2), new Color(0.7f, 0.85f, 1f), "Enchantment");
        //GFX.drawText(startContentX()+CONTENT_PADDING, startContentY()+16*3+CONTENT_PADDING*3, 0, 8, GFX.TEXT_LEFT, GFX.TEXT_TOP, Resources.getFont(1), Color.lightGray, "Enchantment Desc.");
        GFX.drawTextAbsolute(startContentX()+CONTENT_PADDING, startContentY()+16*3+CONTENT_PADDING*3, Resources.getFont(2), Color.lightGray, "Enchantment Desc.");
        GFX.drawTextAbsolute(0, 0, Resources.getFont(1), Color.lightGray, "Testing Power Enchantment");
        GFX.drawTextAbsolute(200, 0, Resources.getFont(2), Color.lightGray, "Testing Power Enchantment");
        GFX.drawTextAbsolute(500, 0, Resources.getFont(3), Color.lightGray, "Testing Power Enchantment");
        
        /* Psudo code
        Textual
        
        drawString(selected.name, size.16, startContentX, startContentY)
        
        drawImage(select.image, getcontentstartX, getContentStartY+16+content_padding)
        
        drawString(Power: selected.power, size.8,
            startContentY+selected.image.width*2+CONTENTPADDING, startContentY+16+CONTENT_PADDING*1+8*0)
        
        drawString(Defence: selected.defence, size.8,
            startContentY+selected.image.width*2+CONTENTPADDING, startContentY+16+CONTENT_PADDING*2+8*1)
        
        drawString(Enchantment: selected.enchantment.name, size.8,
            startContentY+selected.image.width*2+CONTENTPADDING, startContentY+16+CONTENT_PADDING*3+8*2)
        
        drawString(Power: selected.enchantment.desc, size.8,
            startContentY+selected.image.width*2+CONTENTPADDING, startContentY+16+CONTENT_PADDING*4+8*3)
        
        http://www.google.com/imgres?um=1&sa=N&hl=en&biw=1676&bih=870&tbm=isch&tbnid=mU_49KVYg55GfM:&imgrefurl=http://kandipatterns.com/patterns/characters/zelda-hylian-shield-3039&docid=tq_Nai_DcR0kGM&imgurl=http://kandipatterns.com/images/patterns/characters/3039-zelda_Hylian_shield.png&w=420&h=630&ei=kQ34UYaBGY7A4AOP2YCIBg&zoom=1&ved=1t:3588,r:14,s:0,i:123&iact=rc&page=1&tbnh=189&tbnw=126&start=0&ndsp=39&tx=69&ty=78
        http://i.imgur.com/XH5qHJz.png
        */

        /*Equipment equipped = null;
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
        }*/
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
