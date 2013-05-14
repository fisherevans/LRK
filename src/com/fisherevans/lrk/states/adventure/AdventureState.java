package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.adventure.entities.LRKEntity;
import com.fisherevans.lrk.states.adventure.entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/12/13
 * Time: 5:28 PM
 */
public class AdventureState extends LRKState
{
    private static final int ID = GameStateEnum.ADVENTURE.ordinal();

    public static final float TILE_SIZE = 32;
    public static final int
        TILES_WIDE = (int) Math.ceil(Options.BASE_SCREEN_WIDTH/TILE_SIZE),
        TILES_HIGH = (int) Math.ceil(Options.BASE_SCREEN_HEIGHT/TILE_SIZE);
    public static final float
        TILES_WIDE_SHIFT = ((float)(Options.getGameWidth()))/((float)(TILE_SIZE))%1f,
        TILES_HIGH_SHIFT = ((float)Options.getGameHeight()/TILE_SIZE)%1f;

    private ArrayList<LRKEntity> _enitites;
    private Player _player, _camera;
    private TiledMap _map;

    public AdventureState()
    {
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        _enitites = new ArrayList<>();
        _player = new Player(5, 6);
        _camera = _player;

        _enitites.add(_player);

        try
        {
            _map = new TiledMap("res/maps/test.tmx");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("FAILED TO LOAD MAP");
            System.exit(111);
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        // render(int x, int y, int sx, int sy, int width, int height, int l, boolean lineByLine)

        int startY = (int)_player.getY()-TILES_HIGH/2;
        int endY = startY + TILES_HIGH;
        int startX = (int)_player.getX()-TILES_WIDE/2;
        int endX = startX + TILES_WIDE;
        _map.render((int)(-_camera.getX()%1f*TILE_SIZE), (int)(-_camera.getY()%1f*TILE_SIZE), startX, startY, TILES_WIDE, TILES_HIGH, _map.getLayerIndex("collision"), false);
        //drawMap("collision");

        GFX.drawImageCentered(Options.getGameWidth()/2, Options.getGameHeight()/2, _player.getImage());
    }

    private void drawMap(String layer)
    {
        int layerId = _map.getLayerIndex(layer);

        float xShift = (-_camera.getX() + ((float)TILES_WIDE)/2f)*TILE_SIZE;
        float yShift = (-_camera.getY() + ((float)TILES_HIGH+TILES_HIGH_SHIFT)/2f - _camera.getRadius())*TILE_SIZE;

        int startY = (int)_player.getY()-TILES_HIGH/2;
        int endY = startY + TILES_HIGH;
        int startX = (int)_player.getX()-TILES_WIDE/2;
        int endX = startX + TILES_WIDE;

        for(int y = startY-1;y <= endY;y++)
        {
            for(int x = startX-1;x <= endX;x++)
            {
                try
                {
                    GFX.drawImage(xShift + x*TILE_SIZE, yShift + y*TILE_SIZE, _map.getTileImage(x, y, layerId));
                }
                catch(Exception e) { }
            }
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        for(LRKEntity e:_enitites)
        {
            e.update(delta);
        }
    }
}
