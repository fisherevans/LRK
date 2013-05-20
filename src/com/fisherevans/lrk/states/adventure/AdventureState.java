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
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TileSet;
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

    public static final float
        TILE_SIZE = 32f;
    public static final float
        TILES_WIDE = (float) Math.floor(Options.BASE_SCREEN_WIDTH/TILE_SIZE),
        TILES_HIGH = (float) Math.floor(Options.BASE_SCREEN_HEIGHT/TILE_SIZE);

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
        _player = new Player(12f, 9f, _world);
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
        for(int y = 0;y < _map.getHeight();y++)
            for (int x = 0; x < _map.getWidth(); x++)
            {
                int id = _map.getTileId(x, y, layerId);
                if(id > 0)
                {
                    FixtureDef def = JBox2DUtils.generateFixture(id);
                    if (def != null)
                    {
                        _walls.add(new Wall(x, y, def, _world));
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

        float xShift = (-_camera.getX() + TILES_WIDE/2f)*TILE_SIZE - mouseShift.x;
        float mapXShift = (int)(xShift*Options.getDisplayScale());
        mapXShift /= Options.getDisplayScale();

        float yShift = (-_camera.getY() + TILES_HIGH/2f)*TILE_SIZE - mouseShift.y;
        float mapYShift = (int)(yShift*Options.getDisplayScale());
        mapYShift /= Options.getDisplayScale();

        int startX = (int)(_camera.getX()+mouseShift.x/TILE_SIZE) - (int)TILES_WIDE/2;
        int startY = (int)(_camera.getY()+mouseShift.y/TILE_SIZE) - (int)TILES_HIGH/2;

        drawMapLayer("collision", mapXShift, mapYShift, startX, startY);
        drawMapLayer("background", mapXShift, mapYShift, startX, startY);

        for(LRKEntity ent:_entities)
        {
            GFX.drawImageCentered(xShift + ent.getX()*TILE_SIZE, yShift + ent.getY()*TILE_SIZE, ent.getImage());
        }

        GFX.drawImageCentered(Game.lrk.getInput().getMouseX(), Game.lrk.getInput().getMouseY(), _cursor);
    }

    private void drawMapLayer(String layer, float xShift, float yShift, int startX, int startY)
    {
        int layerId = _map.getLayerIndex(layer);
        startX--;
        startY--;

        for(int y = startY;y <= startY+TILES_HIGH+2;y++)
        {
            for(int x = startX;x <= startX+TILES_WIDE+2;x++)
            {
                try
                {
                    GFX.drawImageCentered(x*TILE_SIZE + xShift, y*TILE_SIZE + yShift, _map.getTileImage(x, y, layerId));
                }
                catch(Exception e) { }
            }
        }
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

    public void keyPressed(int key, char c)
    {
        if(key == Input.KEY_BACKSLASH) System.exit(0);
    }
}
