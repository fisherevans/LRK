package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.rpg.RPGEntityGenerator;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.entities.DumbBlob;
import com.fisherevans.lrk.states.adventure.entities.PlayerEntity;
import com.fisherevans.lrk.states.adventure.entities.Wall;
import com.fisherevans.lrk.tools.JBox2DUtils;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/18/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityManager
{
    private AdventureState _state;

    private ArrayList<AdventureEntity> _entities, _entitiesToDelete, _collisionEntities, _collisionEntitiesToDelete;
    private AdventureEntity _camera;
    private PlayerEntity _player;

    private EntityCompareY _entityCompareY;

    private World _world;

    public EntityManager(AdventureState state, TiledMap map)
    {
        _state = state;

        _entityCompareY = new EntityCompareY();

        _entities = new ArrayList<>();
        _entitiesToDelete = new ArrayList<>();

        _collisionEntities = new ArrayList<>();
        _collisionEntitiesToDelete = new ArrayList<>();

        _world = new World(new Vec2(0, 0f), true);

        _player = new PlayerEntity(0, 0, _world, _state);
        _entities.add(_player);
        _camera = _player;

        loadMap(map);
    }

    private void loadMap(TiledMap map)
    {
        int collisionIndex = map.getLayerIndex("collision");
        int entitiesIndex = map.getLayerIndex("entities");

        for(int y = 0;y < map.getHeight();y++)
        {
            for (int x = 0; x < map.getWidth(); x++) // loop through each tile in the map
            {
                processCollisionTile(x, y, map.getTileId(x, y, collisionIndex));
                processEntitiesTile(x, y, map.getTileId(x, y, entitiesIndex));
            }
        }
    }

    private void processCollisionTile(int x, int y, int id)
    {
        if(id == 0)
            return;

        FixtureDef collisionBodyDef = new FixtureDef();
        collisionBodyDef.density = 1f;
        collisionBodyDef.friction = 0.2f;
        collisionBodyDef.restitution = 0.0f;

        PolygonShape bodyDefShape = new PolygonShape();
        Vec2[] shapePoints = {};

        boolean noMatch = false;
        switch(id)
        {
            case 1:
                shapePoints = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  };
                break;
            case 2:
                shapePoints = new Vec2 [] { new Vec2(-0.21875f, -0.21875f), new Vec2(0.21875f, -0.21875f), new Vec2(0.21875f, 0.21875f), new Vec2(-0.21875f, 0.21875f), new Vec2(-0.21875f, -0.21875f),  };
                break;
            default:
                noMatch = true;
        }

        if(!noMatch)
        {
            bodyDefShape.set(shapePoints, shapePoints.length-1);
            collisionBodyDef.shape = bodyDefShape;
            _collisionEntities.add(new Wall(x, y, collisionBodyDef, _world, _state));
        }
        else
            LRK.log("Failed to load the collision tile: " + id);
    }

    private void processEntitiesTile(int x, int y, int id)w
    {
        if(id == 0)
            return;

        boolean noMatch = false;
        switch(id)
        {
            case 193: // PLAYER
                _player.getBody().getPosition().set(x, y);
                break;
            case 194: // DUMB BLOB
                //_entities.add(new DumbBlob(RPGEntityGenerator.getBlob(), x, y, _world, _state));
                break;
            default:
                noMatch = true;
        }

        if(noMatch)
            LRK.log("Failed to load the entities tile: " + id);
    }

    public void update(float delta)
    {
        // Remove to-be-deleted entities
        flushRemoveEntityQueue();

        // Update the entities
        for(AdventureEntity entity:_entities)
            entity.update(delta);

        // Update the physics world
        _world.step(delta, 5, 5);

        // Sort the entities by their Y value
        Collections.sort(_entities, _entityCompareY);
    }

    public void flushRemoveEntityQueue()
    {
        if(_entitiesToDelete.size() > 0)
        {
            for(AdventureEntity entity:_entitiesToDelete)
            {
                _entities.remove(entity);
                _world.destroyBody(entity.getBody());
            }
            _entitiesToDelete.clear();
        }

        if(_collisionEntitiesToDelete.size() > 0)
        {
            for(AdventureEntity entity:_entitiesToDelete)
            {
                _collisionEntities.remove(entity);
                _world.destroyBody(entity.getBody());
            }
            _collisionEntitiesToDelete.clear();
        }
    }

    public void removeEntity(AdventureEntity entity)
    {
        _entitiesToDelete.add(entity);
    }

    public void removeCollisionEntity(AdventureEntity entity)
    {
        _collisionEntitiesToDelete.add(entity);
    }

    public ArrayList<AdventureEntity> getEntities()
    {
        return _entities;
    }

    public AdventureEntity getCamera()
    {
        return _camera;
    }

    public PlayerEntity getPlayer()
    {
        return _player;
    }
}
