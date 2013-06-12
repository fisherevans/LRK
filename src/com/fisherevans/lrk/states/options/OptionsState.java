package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.menu_items.*;
import com.fisherevans.lrk.states.options.menu_items.MenuItem;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import java.awt.*;

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

        // construct the menu system
        Menu video = new Menu("Video", "Various video and display settings.");
        // fill resolutions based on aspect ratio
        Menu resolution = DetermineResolutionsToUse();

        Menu displayMode = new Menu("Display Mode", "Choose between Windowed and Fullscreen mode.");
        displayMode.add(
                new MenuItemStub("Windowed", "Display the game in a window."),
                new MenuItemStub("Fullscreen", "Use your entire screen (primary) to display the game.")
        );
        video.add(resolution, displayMode);

        Menu audio = new Menu("Audio", "Audio levels for the game.");
        audio.add(
                new MenuItemStub("Master Volume", "Volume for the whole game."),
                new MenuItemStub("Music", "Volume for the just the background music."),
                new MenuItemStub("Special Effects", "Volume for just the special effects.")
        );

        MenuItemPlayTest playTest = new MenuItemPlayTest("Playtest", "play a test level in the Adventure Mode");

        MenuItemQuit quit = new MenuItemQuit("Quit", "Exit the game without saving");

        _mainMenu.add(video, audio, playTest, quit);
        _currentMenu = _mainMenu; // set the current menu to the main menu
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        // used for positioning the elements
        int thirdWidth = (int) (DisplayManager.BASE_SCREEN_WIDTH/3f);

        // draw the parent (smaller) menu
        displayMenu(_currentMenu.getParent(), 1, lastMenuColorScale+(thisMenuColorScale-lastMenuColorScale)*(Math.abs(_hTran)), 0, (int)(0 + _hTran*thirdWidth), thirdWidth);

        // then draw the current menu
        displayMenu(_currentMenu, 2, thisMenuColorScale*(1f-Math.abs(_hTran)), _vTran, (int)(thirdWidth + _hTran*thirdWidth), thirdWidth);

        // draw the description of the hovered item
        Color descColor = (new Color(1f, 1f, 1f)).scaleCopy(1-Math.abs(_vTran+_hTran));
        GFX.drawTextWrap(2.2f*thirdWidth, 0, thirdWidth*0.6f, DisplayManager.BASE_SCREEN_HEIGHT, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(1), descColor, _currentMenu.getCurrentItem().getDescription());
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
        UnicodeFont font = Resources.getFont(scale);
        int fontHeight = (int) (font.getLineHeight()*1.5f);
        for(int displayId = 0;displayId < menu.getItems().size();displayId++) // for each menu item
        {
            yShift = (displayId-menu.getCurrentId())*fontHeight - fontHeight*yDiffScale; // draw it offset based on it's distance form the selected menu item
            displayItem = menu.getItem(displayId); // the item to draw
            displayColor = (menu.getCurrentId() == displayId ? displayItem.getColorHover() : displayItem.getColor()).scaleCopy(colorScale); // the color of the item

            // draw the text
            GFX.drawText(xDiff, GFX.filterDrawPosition(DisplayManager.BASE_SCREEN_HEIGHT/2f + yShift), width, 0, GFX.TEXT_CENTER, GFX.TEXT_CENTER, font, displayColor, displayItem.getText());
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

    private Menu DetermineResolutionsToUse()
    {

        Menu resolution = new Menu("Resolution", "The resolution the game is rendered at.");

        resolution.add(
                new MenuItemResolution("480x270","Sets the resolution to: 480x270", 1),
                new MenuItemResolution("960x540","Sets the resolution to: 960x540", 2),
                new MenuItemResolution("1440x810","Sets the resolution to: 1440x810", 3),
                new MenuItemResolution("1920x1080","Sets the resolution to: 1920x1080", 4)
        );

        return resolution;
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

    @Override
    public void keyUp()     { up(); }

    @Override
    public void keyDown()   { down(); }

    @Override
    public void keyLeft()   { back(); }

    @Override
    public void keyRight()  { select(); }

    @Override
    public void keySelect() { select(); }

    @Override
    public void keyBack()   { back(); }
}
