package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.SoundManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.menu_items.*;
import com.fisherevans.lrk.states.options.menu_items.MenuItem;
import org.newdawn.slick.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:24 PM
 */
public class OptionsState extends LRKState
{
    private static final float
        lastMenuColorScale = 0.5f,
        thisMenuColorScale = 1f;

    private float _hTran = 0, _vTran = 0;

    private Menu _mainMenu, _currentMenu;

    public OptionsState() throws SlickException
    {
        super(StateLibrary.getID("options"));
    }

    @Override
    public int getID()
    {
        return StateLibrary.getID("options");
    }

    @Override
    public void init() throws SlickException
    {


        _mainMenu = new Menu(); // the main menu - no parnet means can't go up the tree

        Menu audio = new Menu("Audio", "Audio levels for the game.");
        audio.add(
                new MenuItemStub("Master Volume", "Volume for the whole game."),
                new MenuItemStub("Music", "Volume for the just the background music."),
                new MenuItemStub("Special Effects", "Volume for just the special effects.")
        );

        MenuItemPlayTest playTest = new MenuItemPlayTest("Playtest", "play a test level in the Adventure Mode");

        MenuItemQuit quit = new MenuItemQuit("Quit", "Exit the game without saving");

        _mainMenu.add(audio, playTest, quit);
        _currentMenu = _mainMenu; // set the current menu to the main menu
    }

    @Override
    public void enter() throws SlickException
    {
    }

    @Override
    public void exit() throws SlickException
    {
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        // used for positioning the elements
        int thirdWidth = (int) (DisplayManager.getBackgroundWidth()/3f);

        // draw the parent (smaller) menu
        displayMenu(_currentMenu.getParent(), 1, lastMenuColorScale+(thisMenuColorScale-lastMenuColorScale)*(Math.abs(_hTran)), 0, (int)(0 + _hTran*thirdWidth), thirdWidth);

        // then draw the current menu
        displayMenu(_currentMenu, 2, thisMenuColorScale*(1f-Math.abs(_hTran)), _vTran, (int)(thirdWidth + _hTran*thirdWidth), thirdWidth);

        // draw the description of the hovered item
        Color descColor = (new Color(1f, 1f, 1f)).scaleCopy(1-Math.abs(_vTran+_hTran));
        GFX.drawTextWrap(2.2f*thirdWidth, 0, thirdWidth*0.6f, DisplayManager.getBackgroundHeight(), GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(1), descColor, _currentMenu.getCurrentItem().getDescription());
    }

    /**
     *  draws a menu on the screen, centering the hovered item
     * @param menu the menu to draw
     * @param scale the font size to use
     * @param colorScale the alpha of the color to use (to fade it)
     * @param yDiffScale how much to shift the menu in the y direction
     * @param xDiff how from from the left to draw the menu
     * @param width the width to center the menu in
     */
    private void displayMenu(Menu menu, int scale, float colorScale, float yDiffScale, int xDiff, int width)
    {
        if(menu == null) return; // if drawing a null parent, etc

        float yShift;
        MenuItem displayItem;
        Color displayColor;
        AngelCodeFont font = Resources.getFont(scale);
        int fontHeight = (int) (font.getLineHeight()*1.5f);
        for(int displayId = 0;displayId < menu.getItems().size();displayId++) // for each menu item
        {
            yShift = (displayId-menu.getCurrentId())*fontHeight - fontHeight*yDiffScale; // draw it offset based on it's distance form the selected menu item
            displayItem = menu.getItem(displayId); // the item to draw
            displayColor = (menu.getCurrentId() == displayId ? displayItem.getColorHover() : displayItem.getColor()).scaleCopy(colorScale); // the color of the item

            // draw the text
            GFX.drawText(xDiff, DisplayManager.getBackgroundHeight()/2f + yShift, width, 0, GFX.TEXT_CENTER, GFX.TEXT_CENTER, font, displayColor, displayItem.getText());
        }
    }

    @Override
    public void update(float delta) throws SlickException
    {
        _hTran *= 1f - 10f*delta;
        _hTran += -Math.signum(_hTran)*delta;
        _hTran = (Math.abs(_hTran)) < 0.1 ? 0 : _hTran;

        _vTran *= 1f - 25*delta;
        _vTran += -Math.signum(_vTran)*0.003*delta;
        _vTran = (Math.abs(_vTran)) < 0.1 ? 0 : _vTran;
    }

    @Override
    public void destroy() throws SlickException
    {

    }

    /** move up in the menu */
    private void up()
    {
        if(_hTran != 0 || _vTran != 0) return;
        if(_currentMenu.shiftCurrentId(-1))
            _vTran += 1f;
    }

    /** move down in the menu */
    private void down()
    {
        if(_hTran != 0 || _vTran != 0) return;
        if(_currentMenu.shiftCurrentId(1))
            _vTran += -1f;
    }

    /** select the hovered/highlighted item */
    private void select()
    {
        if(_hTran != 0 || _vTran != 0) return; // nly when here is no animation going on

        Menu newMenu = null;
        try // issue the menu item's command
        {
            newMenu = _currentMenu.getCurrentItem().select();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("problem issueing menu item command");
            System.exit(1);
        }

        if(newMenu != null) // if the menu item returns a menu, it means it was a sub menu. set the current to that new menu
        {
            _currentMenu = newMenu;
            _hTran = 1f;
        }
    }

    /** go up the menu tree */
    private void back()
    {
        if(_hTran != 0 || _vTran != 0) return;
        Menu newMenu = _currentMenu.getParent();
        if(newMenu != null)
        {
            _currentMenu = newMenu;
            _hTran = -1f;
        }
    }

    private void playInteraction()
    {
        SoundManager.play("select");
    }

    @Override
    public void keyUp()     { playInteraction(); up(); }

    @Override
    public void keyDown()   { playInteraction(); down(); }

    @Override
    public void keyLeft()   { playInteraction(); back(); }

    @Override
    public void keyRight()  { playInteraction(); select(); }

    @Override
    public void keySelect() { playInteraction(); select(); }

    @Override
    public void keyBack()   { playInteraction(); back(); }

    @Override
    public void resize()
    {
    }
}
