package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.*;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.menu_items.*;
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
        _mainMenu = new Menu();

        Menu video = new Menu("Video", "Various video and display settings.");
        Menu resolution = new Menu("Resolution", "The resolution the game is rendered at.");
        resolution.add(
                new MenuItemResolution("480x270","Sets the resolution to: 480x270", 1),
                new MenuItemResolution("960x540","Sets the resolution to: 960x540", 2),
                new MenuItemResolution("1440x810","Sets the resolution to: 1440x810", 3),
                new MenuItemResolution("1920x1080","Sets the resolution to: 1920x1080", 4)
        );
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
        _currentMenu = _mainMenu;
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        int thirdWidth = (int) (DisplayManager.BASE_SCREEN_WIDTH/3f);
        int halfWidth = (int) (DisplayManager.BASE_SCREEN_WIDTH/2f);
        displayMenu(_currentMenu.getParent(), 1, lastMenuColorScale+(thisMenuColorScale-lastMenuColorScale)*(Math.abs(_hTran)), 0, (int)(0 + _hTran*thirdWidth), thirdWidth);
        displayMenu(_currentMenu, 2, thisMenuColorScale*(1f-Math.abs(_hTran)), _vTran, (int)(thirdWidth + _hTran*thirdWidth), thirdWidth);
        //displayMenu(_currentMenu.getParent(), 2, lastMenuColorScale, 0, 0, halfWidth);
        //displayMenu(_currentMenu, 1, thisMenuColorScale*(1f-Math.abs(_hTran)), _vTran, 0, halfWidth);

        Color descColor = (new Color(1f, 1f, 1f)).scaleCopy(1-Math.abs(_vTran+_hTran));
        GFX.drawTextWrap(2.2f*thirdWidth, 0, thirdWidth*0.6f, DisplayManager.BASE_SCREEN_HEIGHT, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(1), descColor, _currentMenu.getCurrentItem().getDescription());
    }

    private void displayMenu(Menu menu, int scale, float colorScale, float yDiffScale, int xDiff, int width)
    {
        if(menu == null) return;

        float yShift;
        MenuItem displayItem;
        Color displayColor;
        UnicodeFont font = Resources.getFont(scale);
        int fontHeight = (int) (font.getLineHeight()*1.5f);
        for(int displayId = 0;displayId < menu.getItems().size();displayId++)
        {
            yShift = (displayId-menu.getCurrentId())*fontHeight - fontHeight*yDiffScale;
            displayItem = menu.getItem(displayId);
            displayColor = (menu.getCurrentId() == displayId ? displayItem.getColorHover() : displayItem.getColor()).scaleCopy(colorScale);
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

    private void up()
    {
        if(_hTran != 0 || _vTran != 0) return;
        if(_currentMenu.shiftCurrentId(-1))
            _vTran += 1f;
    }

    private void down()
    {
        if(_hTran != 0 || _vTran != 0) return;
        if(_currentMenu.shiftCurrentId(1))
            _vTran += -1f;
    }

    private void select()
    {
        if(_hTran != 0 || _vTran != 0) return;

        Menu newMenu = null;
        try
        {
            newMenu = _currentMenu.getCurrentItem().select();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("problem issueing menu item command");
            System.exit(1);
        }

        if(newMenu != null)
        {
            _currentMenu = newMenu;
            _hTran = 1f;
        }
    }

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
