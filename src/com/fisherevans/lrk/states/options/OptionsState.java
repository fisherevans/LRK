package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.menu_items.MenuItemPlayTest;
import com.fisherevans.lrk.states.options.menu_items.MenuItemQuit;
import com.fisherevans.lrk.states.options.menu_items.MenuItemResolution;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:24 PM
 */
public class OptionsState extends LRKState
{
    public static final int ID = GameStateEnum.OPTIONS.ordinal();

    private static final float
        lastMenuColorScale = 0.5f,
        thisMenuColorScale = 1f;
    private static final Color
        currentItemColor = Color.white;

    private Image _testImg;
    private float _hTran = 0, _vTran = 0;

    private Menu _mainMenu, _currentMenu;

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        _testImg = Resources.getImage("res/test/images/32x32char.png");

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
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        int thirdWidth = (int) (Options.BASE_SCREEN_WIDTH/3f);
        int halfWidth = (int) (Options.BASE_SCREEN_WIDTH/2f);
        displayMenu(_currentMenu.getParent(), 1, lastMenuColorScale+(thisMenuColorScale-lastMenuColorScale)*(Math.abs(_hTran)), 0, (int)(0 + _hTran*thirdWidth), thirdWidth);
        displayMenu(_currentMenu, 2, thisMenuColorScale*(1f-Math.abs(_hTran)), _vTran, (int)(thirdWidth + _hTran*thirdWidth), thirdWidth);
        //displayMenu(_currentMenu.getParent(), 2, lastMenuColorScale, 0, 0, halfWidth);
        //displayMenu(_currentMenu, 1, thisMenuColorScale*(1f-Math.abs(_hTran)), _vTran, 0, halfWidth);

        Color descColor = (new Color(1f, 1f, 1f)).scaleCopy(1-Math.abs(_vTran+_hTran));
        GFX.drawTextWrap(2.2f*thirdWidth, 0, thirdWidth*0.6f, Options.BASE_SCREEN_HEIGHT, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(1), descColor, _currentMenu.getCurrentItem().getDescription());
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
            GFX.drawText(xDiff, Options.BASE_SCREEN_HEIGHT/2f + yShift, width, 0, GFX.TEXT_CENTER, GFX.TEXT_CENTER, font, displayColor, displayItem.getText());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        _hTran *= 1f - 0.01f*delta;
        _hTran += -Math.signum(_hTran)*0.001*delta;
        _hTran = (Math.abs(_hTran)) < 0.005 ? 0 : _hTran;

        _vTran *= 1f - 0.02f*delta;
        _vTran += -Math.signum(_vTran)*0.003*delta;
        _vTran = (Math.abs(_vTran)) < 0.005 ? 0 : _vTran;
    }


    public void keyPressed(int key, char c)
    {
        LRK.log("Hello");
        if(Options.isUp(key))
        {
            if(_hTran != 0 || _vTran != 0) return;
            if(_currentMenu.shiftCurrentId(-1))
                _vTran += 1f;
        }
        else if(Options.isDown(key))
        {
            if(_hTran != 0 || _vTran != 0) return;
            if(_currentMenu.shiftCurrentId(1))
                _vTran += -1f;
        }
        else if(Options.isRight(key) || Options.isSelect(key))
        {
            if(_hTran != 0 || _vTran != 0) return;
            Menu newMenu = _currentMenu.getCurrentItem().select();
            if(newMenu != null)
            {
                _currentMenu = newMenu;
                _hTran = 1f;
            }
        }
        else if(Options.isLeft(key) || Options.isBack(key))
        {
            if(_hTran != 0 || _vTran != 0) return;
            Menu newMenu = _currentMenu.getParent();
            if(newMenu != null)
            {
                _currentMenu = newMenu;
                _hTran = -1f;
            }
        }
    }
}
