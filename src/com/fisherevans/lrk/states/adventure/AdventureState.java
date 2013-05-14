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

    private ArrayList<LRKEntity> _entities;
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
        _entities = new ArrayList<>();
        _player = new Player(5f, 6f);
        _camera = _player;

        _entities.add(_player);

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
        drawMap("collision");

        GFX.drawImageCentered(Options.getGameWidth()/2, Options.getGameHeight()/2, _player.getImage());
    }

    private void drawMap(String layer)
    {
        int layerId = _map.getLayerIndex(layer);

        float xShift = (-_camera.getX()%1f - _camera.getRadius())*TILE_SIZE;
        float yShift = (-_camera.getY()%1f + TILES_HIGH_SHIFT/2f - 1f)*TILE_SIZE;

        int startY = (int)_camera.getY()-TILES_HIGH/2;
        int startX = (int)_camera.getX()-TILES_WIDE/2;

        _map.render(xShift, yShift, startX-1, startY-1, TILES_WIDE+2, TILES_HIGH+2, _map.getLayerIndex("collision"), true);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        for(LRKEntity e: _entities)
        {
            e.update(delta);
        }
    }
}
