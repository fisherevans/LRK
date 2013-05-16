package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.adventure.entities.LRKEntity;
import com.fisherevans.lrk.states.adventure.entities.Player;
import com.fisherevans.lrk.states.adventure.entities.Wall;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

    private ArrayList<LRKEntity> _entities, _walls;
    private Player _player, _camera;
    private TiledMap _map;

    private World _world;

    private Image _cursor;

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
        _cursor = Resources.getImage("res/test/images/cursor.png");

        _world = new World(new Vec2(0, 0f), true);

        _entities = new ArrayList<>();
        _player = new Player(38f, 31f, _world);
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

        _walls = new ArrayList<LRKEntity>();
        int layerId = _map.getLayerIndex("collision");
        int wallId = 1;
        for(int x = 0;x < _map.getWidth();x++)
        {
            for(int y = 0;y < _map.getHeight();y++)
            {
                int id = _map.getTileId(x, y, layerId);
                if(id == wallId)
                {
                    _walls.add(new Wall(x, y, _world));
                    LRK.log("WALL");
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        Vec2 mouseShift = new Vec2(Game.lrk.getInput().getMouseX()-Options.getGameWidth()/2f, Game.lrk.getInput().getMouseY()-Options.getGameHeight()/2f);
        mouseShift.mulLocal(0.3f);
        _player.setDegrees((float) Math.toDegrees(Math.atan2(mouseShift.y, mouseShift.x)));

        float xShift = Options.getGameWidth()/2f - _camera.getX()*TILE_SIZE - mouseShift.x;
        float yShift = Options.getGameHeight()/2f - _camera.getY()*TILE_SIZE - mouseShift.y;

        _map.render(xShift - TILE_SIZE/2f, yShift - TILE_SIZE/2);//, _map.getLayerIndex("background"));

        for(LRKEntity ent:_entities)
            GFX.drawImageCentered(xShift + ent.getX()*TILE_SIZE, yShift + ent.getY()*TILE_SIZE, ent.getImage());

        GFX.drawImageCentered(Game.lrk.getInput().getMouseX(), Game.lrk.getInput().getMouseY(), _cursor);
        //LRK.log(Game.lrk.getInput().getMouseX() + " - " + Game.lrk.getInput().getMouseY());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        float step = delta/1000f;
        for(LRKEntity e: _entities)
        {
            e.update(delta);
        }
        _world.step(step, 5, 5);
    }
}
