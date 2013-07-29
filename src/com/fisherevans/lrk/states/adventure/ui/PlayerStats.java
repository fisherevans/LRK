package com.fisherevans.lrk.states.adventure.ui;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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

    private final int HEALTH_HEIGHT = 100;
    private final int HEALTH_WIDTH = 20;

    public PlayerStats(LRKState parent)
    {
        super(parent, false, false);
        _playerHealth = Game.lrk.getPlayer().getEntity().getHealth();
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        int currentHealth = (int) (_playerHealth.getCurrentHealth()/((float)_playerHealth.getMaximumHealth())*HEALTH_HEIGHT);

        //LRK.log("Health: " + _playerHealth.getCurrentHealth() + "/" + _playerHealth.getMaximumHealth() + " - " + currentHealth);

        gfx.setColor(Color.darkGray);
        gfx.fillRect(DisplayManager.getForegroundWidth()-HEALTH_WIDTH,
                DisplayManager.getForegroundHeight()-HEALTH_HEIGHT,
                HEALTH_WIDTH,
                HEALTH_HEIGHT);

        gfx.setColor(Color.red);
        gfx.fillRect(DisplayManager.getForegroundWidth()-HEALTH_WIDTH,
                DisplayManager.getForegroundHeight()-currentHealth,
                HEALTH_WIDTH,
                currentHealth);

        GFX.drawTextAbsolute(DisplayManager.getForegroundWidth()-64-15, DisplayManager.getForegroundHeight()-16, Resources.getFont(1), Color.white, "Health");
    }

    @Override
    public void resize()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
