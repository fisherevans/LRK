package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.managers.MusicManager;
import com.fisherevans.lrk.rpg.RPGEntityGenerator;
import com.fisherevans.lrk.states.adventure.entities.DumbBlob;
import com.fisherevans.lrk.states.adventure.entities.PlayerEntity;
import com.fisherevans.lrk.states.adventure.lights.Light;
import com.fisherevans.lrk.states.adventure.lights.LightManager;
import com.fisherevans.lrk.states.adventure.lights.PlayerLight;
import com.fisherevans.lrk.states.adventure.lights.ShadowMap;
import com.fisherevans.lrk.states.adventure.lights.light_controllers.TorchController;
import com.fisherevans.lrk.states.character.CharacterState;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.entities.Wall;
import com.fisherevans.lrk.states.adventure.ui.PlayerStats;
import com.fisherevans.lrk.states.transitions.SimpleFadeTransition;
import com.fisherevans.lrk.tools.JBox2DUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Collections;

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
        DEFAULT_RENDER_DISTANCE = 5f;

    public static float TILES_WIDE, TILES_HIGH;

    private ArrayList<AdventureEntity> _entities, _entitiesToDelete, _walls;
    private Vec2[] _entitiesDrawPos;
    private PlayerEntity _playerEntity, _camera;
    private TiledMap _map;
    private World _world;
    private Vec2 _aimShift;

    private EntityEffectQueue _entityEffectQueue;

    private SpriteSystem _backgroundSpriteSystem, _foregroundSpriteSystem;

    private LightManager _lightManager;

    private Image _vignette;

    private ShadowMap _shadowMap;

    public AdventureState() throws SlickException
    {
        super(StateLibrary.getTempID());
        addUIComponent(new PlayerStats(this));
        setGrabMouse(true);
        _entityEffectQueue = new EntityEffectQueue(this);
        _backgroundSpriteSystem = new SpriteSystem(this);
        _foregroundSpriteSystem = new SpriteSystem(this);
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

        _playerEntity = new PlayerEntity(18, 18, _world, this);
        _camera = _playerEntity;

        _entities.add(_playerEntity);

        _lightManager = new LightManager(this, new Color(0.2f, 0.2f, 0.3f));
        //_lightManager = new LightManager(this, new Color(0.7f, 0.7f, 0.85f));
        Light playerLight = new PlayerLight(_playerEntity, _lightManager);
        _lightManager.addLight(playerLight);
        _lightManager.setCameraLight(playerLight);

        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(9, 16), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(9, 20), "torch", _lightManager));

        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(16, 9), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(20, 9), "torch", _lightManager));

        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(27, 16), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(27, 20), "torch", _lightManager));

        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(16, 27), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.7f), new Vec2(20, 27), "torch", _lightManager));

        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(15, 15), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(21, 15), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(15, 21), "torch", _lightManager));
        _lightManager.addLight(new Light(3.5f, new Color(1f, 0.95f, 0.8f), new Vec2(21, 21), "torch", _lightManager));

        try // load the map
        {
            _map = new TiledMap("res/maps/level_arena.tmx");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("FAILED TO LOAD MAP");
            System.exit(111);
        }

        // a list of wall's in the world
        _shadowMap = new ShadowMap(this);
        _walls = new ArrayList<AdventureEntity>();
        int layerId = _map.getLayerIndex("collision");
        Wall wall;
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
                        wall = new Wall(x, y, def, _world, this);
                        _walls.add(wall); // but if it does, add the tile shape to the world
                        if(id == 1)
                            _shadowMap.addLines(wall.getLines());
                    }
                }
            }
        }

        _aimShift = new Vec2(0, 0);

        Game.lrk.getPlayer().reset();
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
        // WHEN DRAWING ENTITIES, SHIFT EACH BY X AND Y SHIFT
        // SUBTRACT THE CAMERA POS AND ADD HALF THE DISPLAY AND SUBTRACT THE AIM SHIFT
        float xShift = (TILES_WIDE/2f - _camera.getX())*TILE_SIZE - _aimShift.x;
        float yShift = (TILES_HIGH/2f - _camera.getY())*TILE_SIZE - _aimShift.y;

        // WHAT TILE TO START DRAWING AT
        int startX = (int)(_camera.getX()+_aimShift.x/TILE_SIZE) - (int)TILES_WIDE/2 - 1;
        int startY = (int)(_camera.getY()+_aimShift.y/TILE_SIZE) - (int)TILES_HIGH/2 - 1;

        // CALC THE RENDER SIZE FOR THE VIGNETTE
        int vignetteSize = (int) (getRenderDistance() *TILE_SIZE*2);

        // PRE-CALC THE DRAW COORID'S FOR THE ENTITIES
        for(AdventureEntity ent:_entities) // FOR EACH ENTITY
        {
            if(inRenderArea(ent)) // IF THEIR IN RENDER DISTANCE
                ent.getDrawPosition().set(getDrawPosition(ent.getBody().getPosition(), xShift, yShift)); // DRAW THEM WITH THE X AND Y SHIFTS
            else
                ent.getDrawPosition().set(new Vec2(-1000, -1000));
        }


        // CLIP THE DRAWING AROUND THE RENDER DISTANCE
        GFX.clip(xShift + _camera.getX()*TILE_SIZE-vignetteSize/2f + 1,
                yShift + _camera.getY()*TILE_SIZE-vignetteSize/2f + 1,
                vignetteSize - 2, vignetteSize - 2,
                DisplayManager.getBackgroundScale());

        // DRAW THE BACKGROUND LAYER
        drawMapLayer(xShift, yShift, startX, startY, getLayerIds("background"));

        // DRAW THE ENTITIES
        for(AdventureEntity ent:_entities)
            if(inRenderArea(ent))
                ent.render(gfx);

        // DRAW THE BACKGROUND SPRITES
        _backgroundSpriteSystem.render(gfx, xShift, yShift);

        // DRAW THE FOREGROUND LAYER
        drawMapLayer(xShift, yShift, startX, startY, getLayerIds("foreground"));

        // DRAW THE ENTITIE IDENTIFIERS (NAME, HEALTHBAR, ETC)
        for(AdventureEntity ent:_entities)
            if(inRenderArea(ent))
                ent.renderIdentifiers(gfx);

        // DRAW THE FOREGROUND SPRITES
        _foregroundSpriteSystem.render(gfx, xShift, yShift);

        // UN-CLIP THE GRAPHICS ELEMENT
        GFX.unClip();

        // LIGHTING SYSTEM
        _lightManager.render(gfx, xShift, yShift);

        //*/ DRAW THE PRETTY VIGNETTE
        GFX.drawImageCentered(xShift + _camera.getX()*TILE_SIZE,
                yShift + _camera.getY()*TILE_SIZE,
                vignetteSize, vignetteSize,
                _vignette);
        //*/
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
        // NORMALIZE SHIFT TO AVOID TEARING
        xShift = GFX.filterDrawPosition(xShift, DisplayManager.getBackgroundScale());
        yShift = GFX.filterDrawPosition(yShift, DisplayManager.getBackgroundScale());

        Image tile;
        for(int y = startY;y <= startY+TILES_WIDE+1;y++)
        {
            for(int x = startX;x <= startX+TILES_WIDE+1;x++) // FOR EACH TIME ON THE SCREEN
            {
                for(Integer layerId:layerIds) // FOR EACH LAYER
                {
                    tile = _map.getTileImage(x, y, layerId); // GET THE TILE FOR THAT LAYER | v- IF IT'S NOT NULL AND IT'S IN THE RENDER DISTANCE
                    if(tile != null)
                        GFX.drawImageCentered(x*TILE_SIZE + xShift, y*TILE_SIZE + yShift, tile); // DRAW THE TILE WITH THE X AND Y SHIFT
                }
            }
        }
    }

    private float lastSpawn = 0;
    private float spawnRate = 20;

    @Override
    public void update(float delta) throws SlickException
    {
        lastSpawn += delta;
        if(lastSpawn >= spawnRate)
        {
            lastSpawn -= spawnRate;
            if(Math.random() > 0.5)
                _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 8, 18, _world, this));
            if(Math.random() > 0.5)
                _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 18, 8, _world, this));
            if(Math.random() > 0.5)
                _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 18, 28, _world, this));
            if(Math.random() > 0.5)
                _entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), 28, 18, _world, this));
        }

        // AIM SHIFT BASED ON MOUSE
        _aimShift.set(InputManager.getMouseXOrigin(), InputManager.getMouseYOrigin());
        _aimShift.mulLocal(0.3f/DisplayManager.getBackgroundScale()); // scale it by about a third (for moving the viewport)

        // EFFECT QUEUE
        if(_entityEffectQueue.getQueueSize() > 0)
        {
            _entityEffectQueue.update(delta);
            for(AdventureEntity entity: _entities)
                _entityEffectQueue.processEntity(entity);
            _entityEffectQueue.clearComplete();
        }

        // UPDATE EACH ENTITY
        for(AdventureEntity entity: _entities)
            entity.update(delta); // logic

        // DELETING DEAD ENTITIES
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

        _backgroundSpriteSystem.update(delta);
        _foregroundSpriteSystem.update(delta);

        _lightManager.update(delta);

        // UPDATE THE 2D PHYSICS WORLD
        _world.step(delta, 5, 5);

        Collections.sort(_entities, new EntityCompareY());
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

        if(_lightManager != null)
            _lightManager.resize();
    }

    @Override
    public void keyMenu()
    {
        try
        {
            StateLibrary.setActiveState(new SimpleFadeTransition(StateLibrary.getTempID(), StateLibrary.getActiveState(), StateLibrary.getState("options"), 0.5f));
        }
        catch (SlickException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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

    public int getRenderDistance()
    {
        return (int) (DEFAULT_RENDER_DISTANCE + Game.lrk.getPlayer().getLightStength());
    }

    public boolean inRenderArea(AdventureEntity entity)
    {
        return inRenderArea(entity, 0);
    }

    public boolean inRenderArea(AdventureEntity entity, float renderPadding)
    {
        return inRenderArea(entity.getBody().getPosition(), renderPadding);
    }

    public boolean inRenderArea(Vec2 position, float renderPadding)
    {
        return Math.abs(position.x-_camera.getX())-renderPadding <= getRenderDistance()
                && Math.abs(position.y-_camera.getY())-renderPadding <= getRenderDistance();
    }

    public SpriteSystem getBackgroundSpriteSystem()
    {
        return _backgroundSpriteSystem;
    }

    public SpriteSystem getForegroundSpriteSystem()
    {
        return _foregroundSpriteSystem;
    }

    public PlayerEntity getCamera()
    {
        return _camera;
    }

    public ShadowMap getShadowMap()
    {
        return _shadowMap;
    }

    public static Vec2 getDrawPosition(Vec2 worldPos, float xShift, float yShift)
    {
        return new Vec2(xShift + worldPos.x * TILE_SIZE, yShift + worldPos.y * TILE_SIZE);
    }

    public LightManager getLightManager()
    {
        return _lightManager;
    }
}
