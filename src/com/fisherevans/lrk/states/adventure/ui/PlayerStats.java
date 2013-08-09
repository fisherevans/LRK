package com.fisherevans.lrk.states.adventure.ui;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.entities.controllers.ActiveEntityController;
import com.fisherevans.lrk.tools.LRKMath;
import org.newdawn.slick.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerStats extends UIComponent
{
    private Health _playerHealth;
    private ActiveEntityController _controller;

    private final int STAT_BOTTOM_PADDING = 2;
    private final int STAT_HEIGHT = 56;

    private Image _bgImg, _healthImg, _manaImg, _fgImg;

    private int _imgWidth, _halfImgWidth, _imgHeight;

    private int _drawX, _drawY;

    private AngelCodeFont _font;

    public PlayerStats(LRKState parent)
    {
        super(parent, false, false);
        _playerHealth = Game.lrk.getPlayer().getEntity().getHealth();
        _controller = ((AdventureState)getParent()).getPlayerEntity().getController();

        _bgImg = Resources.getImage("gui/states/adventure/stats_bg");
        _healthImg = Resources.getImage("gui/states/adventure/stats_health");
        _manaImg = Resources.getImage("gui/states/adventure/stats_mana");
        _fgImg = Resources.getImage("gui/states/adventure/stats_fg");

        _imgWidth = _bgImg.getWidth();
        _halfImgWidth = _imgWidth/2;
        _imgHeight = _bgImg.getHeight();

        _font = Resources.getFont(0);

        resize();
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        float healthPercentage = _playerHealth.getCurrentHealth()/_playerHealth.getMaximumHealth();

        String mainName = "";
        float mainManaPercentage = 0f;
        if(_controller.getMainSkill() != null)
        {
            mainName = _controller.getMainSkill().getName();
            mainManaPercentage = LRKMath.clamp(0, _controller.getSinceLastMainSkill()/_controller.getMainSkill().getCoolDown(), 1f);
        }

        String secondaryName = "";
        float secondaryManaPercentage = 0f;
        if(_controller.getSecondarySkill() != null)
        {
            secondaryName = _controller.getSecondarySkill().getName();
            mainManaPercentage = LRKMath.clamp(0, _controller.getSinceLastSecondarySkill()/_controller.getSecondarySkill().getCoolDown(), 1f);
        }

        GFX.drawImage(_drawX, _drawY, _bgImg);

        int clipHeight, clipY;

        clipHeight = (int) (STAT_HEIGHT*healthPercentage);
        clipY = (int) (DisplayManager.getForegroundHeight()-STAT_BOTTOM_PADDING-clipHeight);
        GFX.clip(_drawX + STAT_BOTTOM_PADDING, clipY, _imgWidth, clipHeight, DisplayManager.getForegroundScale());
        GFX.drawImage(_drawX, _drawY, _healthImg);
        GFX.unClip();

        clipHeight = (int) (STAT_HEIGHT*mainManaPercentage);
        clipY = (int) (DisplayManager.getForegroundHeight()-STAT_BOTTOM_PADDING-clipHeight);
        GFX.clip(_drawX + STAT_BOTTOM_PADDING, clipY, _halfImgWidth, clipHeight, DisplayManager.getForegroundScale());
        GFX.drawImage(_drawX, _drawY, _manaImg);
        GFX.unClip();

        GFX.drawTextAbsolute(_drawX-_font.getWidth(mainName), DisplayManager.getForegroundHeight()-12, _font, Color.white, mainName);

        clipHeight = (int) (STAT_HEIGHT*secondaryManaPercentage);
        clipY = (int) (DisplayManager.getForegroundHeight()-STAT_BOTTOM_PADDING-clipHeight);
        GFX.clip(_drawX + STAT_BOTTOM_PADDING+_halfImgWidth, clipY, _halfImgWidth, clipHeight, DisplayManager.getForegroundScale());
        GFX.drawImage(_drawX, _drawY, _manaImg);
        GFX.unClip();

        GFX.drawTextAbsolute(_drawX+_imgWidth, DisplayManager.getForegroundHeight()-12, _font, Color.white, secondaryName);

        GFX.drawImage(_drawX, _drawY, _fgImg);
    }

    @Override
    public void resize()
    {

        LRK.log("Window was resized");
        _drawX = (int) (DisplayManager.getForegroundWidth()/2 - _halfImgWidth);
        _drawY = (int) (DisplayManager.getForegroundHeight() - _imgHeight);
    }
}
