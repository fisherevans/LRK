package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.managers.MusicManager;
import com.fisherevans.lrk.rpg.RPGEntityGenerator;
import com.fisherevans.lrk.states.adventure.entities.PlayerEntity;
import com.fisherevans.lrk.states.character.CharacterState;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.entities.DumbBlob;
import com.fisherevans.lrk.states.adventure.entities.Wall;
import com.fisherevans.lrk.states.adventure.ui.PlayerStats;
import com.fisherevans.lrk.tools.JBox2DUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.*;
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

    public static final float
        TILE_SIZE = 32f,
        RENDER_DISTANCE = 10f;


    public static float TILES_WIDE, TILES_HIGH;

    private ArrayList<AdventureEntity> _entities, _entitiesToDelete, _walls;
    private PlayerEntity _playerEntity, _camera;
    private TiledMap _map;
    private World _world;
    private Vec2 _aimShift;

    private EntityEffectQueue _entityEffectQueue;

    private Image _vignette;

    public AdventureState() throws SlickException
    {
        super(StateLibrary.getTempID());
        addUIComponent(new PlayerStats(this));
        setGrabMouse(true);
        _entityEffectQueue = new EntityEffectQueue(this);

        _vignette = Resources.getImage("gui/states/adventure/vignette");
    }

    @Override
    public void init() throws SlickException
    {
        MusicManager.play("calm_wip");
        resize();

        //setCursor(Resources.getAbsoluteImage("res/test/images/cursor.png").getScaledCopy(2f));

        _world = new World(new Vec2(0, 0f), true);

        // a list of entities in the world
        _entities = new ArrayList<>();
        _entitiesToDelete = new ArrayList<>();

        _playerEntity = new PlayerEntity(24f, 14f, _world, this);
        _camera = _playerEntity;

        _entities.add(_playerEntity);
        _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 33, 14, _world, this));
        _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 33, 16, _world, this));
        _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 27, 27, _world, this));
        _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 25, 27, _world, this));
        _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 23, 27, _world, this));

        try // load the map
        {
            _map = new TiledMap("res/maps/test.tmx");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("FAILED TO LOAD MAP");
            System.exit(111);
        }

        // a list of wall's in the world
        _walls = new ArrayList<AdventureEntity>();
        int layerId = _map.getLayerIndex("collision");
        for(int y = 0;y < _map.getHeight();y++)
        {
            for (int x = 0; x < _map.getWidth(); x++) // loop through each tile in the map
            {
                int id = _map.getTileId(x, y, layerId); // get the global id
                if(id > 0) // if the tile isn't empty
                {
                    FixtureDef def = JBox2DUtils.generateFixture(id); // get the shape based on the id
                    if (def != null) // null means the tile id doesn't have a predefined shape
                    {
                        _walls.add(new Wall(x, y, def, _world, this)); // but if it does, add the tile shape to the world
                    }
                }
            }
        }

        _aimShift = new Vec2(0, 0);
    }

    @Override
    public void enter() throws SlickException
    {
        InputManager.setQueryControllerMovement(false);
        resize();
    }

    @Override
    public void exit() throws SlickException
    {
        InputManager.setQueryControllerMovement(true);
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        // when drawing entities, shift each by x and y shift
        // subtract the camera pos and add half the display and subtract the aim shift
        float xShift = (TILES_WIDE/2f - _camera.getX())*TILE_SIZE - _aimShift.x;
        float yShift = (TILES_HIGH/2f - _camera.getY())*TILE_SIZE - _aimShift.y;

        // what tile to start drawing at
        int startX = (int)(_camera.getX()+_aimShift.x/TILE_SIZE) - (int)TILES_WIDE/2 - 1;
        int startY = (int)(_camera.getY()+_aimShift.y/TILE_SIZE) - (int)TILES_HIGH/2 - 1;


        float vignetteSize = RENDER_DISTANCE*TILE_SIZE*2;
        GFX.clip(xShift + _camera.getX()*TILE_SIZE-vignetteSize/2f, yShift + _camera.getY()*TILE_SIZE-vignetteSize/2f, vignetteSize, vignetteSize, DisplayManager.getBackgroundScale());

        drawMapLayer(xShift, yShift, startX, startY, getLayerIds("background"));

        float xDiff, yDiff;
        for(AdventureEntity ent:_entities) // for each entity
        {
            xDiff = ent.getX() > _camera.getX() ? ent.getX() - _camera.getX() : _camera.getX() - ent.getX();
            yDiff = ent.getY() > _camera.getY() ? ent.getY() - _camera.getY() : _camera.getY() - ent.getY();
            if(Math.abs(xDiff) > RENDER_DISTANCE || Math.abs(yDiff) > RENDER_DISTANCE)
                continue;

            // draw them with the x and y shifts
            ent.render(gfx, xShift + ent.getX()*TILE_SIZE, yShift + ent.getY()*TILE_SIZE);
        }

        GFX.drawImageCentered(xShift + _camera.getX()*TILE_SIZE, yShift + _camera.getY()*TILE_SIZE, vignetteSize, vignetteSize, _vignette);

        GFX.unClip();
    }

    /**
     * get the int ids of the given string layers
     * @param layers string layers to lookup
     * @return the array of int containing the layers in the same order
     */
    private int[] getLayerIds(String... layers)
    {
        int[] layerIds = new int[layers.length];

        for(int layerNumber = 0;layerNumber < layers.length;layerNumber++)
            layerIds[layerNumber] = _map.getLayerIndex(layers[layerNumber]);

        return layerIds;
    }

    /**
     * Draws a tiled map layer on the primary graphics element
     * @param xShift the sub tile x shift to move the whole layer by
     * @param yShift the sub tile x shift to move the whole layer by
     * @param startX the x index of the tile to begin drawing at
     * @param startY the y index of the tile to begin drawing at
     * @param layerIds the layers to draw
     */
    private void drawMapLayer(float xShift, float yShift, int startX, int startY, int[] layerIds)
    {
        xShift = GFX.filterDrawPosition(xShift, DisplayManager.getBackgroundScale());
        yShift = GFX.filterDrawPosition(yShift, DisplayManager.getBackgroundScale());
        Image tile;
        for(int y = startY;y <= startY+TILES_WIDE+2;y++)
        {
            for(int x = startX;x <= startX+TILES_WIDE+2;x++) // for each tile on the screen
            {
                for(Integer layerId:layerIds)
                {
                    tile = _map.getTileImage(x, y, layerId);
                    if(tile != null)
                    {
                        if(Math.abs(x-_camera.getX()) < RENDER_DISTANCE && Math.abs(y-_camera.getY()) < RENDER_DISTANCE)
                            GFX.drawImageCentered(x*TILE_SIZE + xShift, y*TILE_SIZE + yShift, tile);
                    }
                }
            }
        }
    }

    @Override
    public void update(float delta) throws SlickException
    {
        _aimShift.set(InputManager.getMouseXOrigin(), InputManager.getMouseYOrigin());
        _aimShift.mulLocal(0.3f/DisplayManager.getBackgroundScale()); // scale it by about a third (for moving the viewport)

        boolean processEffects = _entityEffectQueue.getQueueSize() > 0;
        if(processEffects) _entityEffectQueue.update(delta);
        for(AdventureEntity entity: _entities)
        {
            if(processEffects) _entityEffectQueue.processEntity(entity);
            entity.update(delta); // logic
        }
        _entityEffectQueue.clearComplete();

        if(_entitiesToDelete.size() > 0)
        {
            for(AdventureEntity entity:_entitiesToDelete)
            {
                entity.destroy();
                _world.destroyBody(entity.getBody());
                _entities.remove(entity);
            }
            _entitiesToDelete.clear();
        }

        _world.step(delta, 5, 5); // physics

    }

    @Override
    public void destroy() throws SlickException
    {
    }

    @Override
    public void resize()
    {
        TILES_WIDE = DisplayManager.getBackgroundWidth()/TILE_SIZE;
        TILES_HIGH = DisplayManager.getBackgroundHeight()/TILE_SIZE;
    }

    @Override
    public void keyMenu()
    {
        StateLibrary.setActiveState("options");
    }

    @Override
    public void keyBack()
    {
        try
        {
            StateLibrary.setActiveState(new CharacterState(this));
        } catch (SlickException e)
        {
            LRK.log("Failed to change states - code 207");
            e.printStackTrace();
        }
    }

    @Override
    public void mouseEvent(MouseInputType type, float x, float y)
    {
        if(type == MouseInputType.LeftPressed)
            _playerEntity.leftMousePress(x, y);
        if(type == MouseInputType.RightPressed)
            _playerEntity.rightMousePress(x, y);
    }

    public PlayerEntity getPlayerEntity()
    {
        return _playerEntity;
    }

    public ArrayList<AdventureEntity> getEntities()
    {
        return _entities;
    }

    public EntityEffectQueue getEntityEffectQueue()
    {
        return _entityEffectQueue;
    }

    public void killEntity(AdventureEntity entity)
    {
        _entitiesToDelete.add(entity);
    }
}
