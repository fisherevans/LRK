package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.entities.Wall;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/19/13
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollisionManager
{
    private AdventureState _state;

    private ArrayList<AdventureEntity> _collisionEntities, _collisionEntitiesToDelete;

    private int _collisionMapIndex;

    public CollisionManager(AdventureState state)
    {
        _state = state;

        _collisionEntities = new ArrayList<>();
        _collisionEntitiesToDelete = new ArrayList<>();

        _collisionMapIndex = _state.getTiledMap().getLayerIndex("collision");
    }

    public void processTile(int x, int y, TiledMap tiledMap)
    {
        processCollisionTile(x, y, tiledMap.getLocalTileId(x, y, _collisionMapIndex));
    }

    private void processCollisionTile(int x, int y, int id)
    {
        if(id < 0)
            return;

        FixtureDef collisionBodyDef = new FixtureDef();
        collisionBodyDef.density = 1f;
        collisionBodyDef.friction = 0.2f;
        collisionBodyDef.restitution = 0.0f;

        Shape shape = null;

        switch(id)
        {
            case 0:
                shape = getPolygonShape(new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f));
                break;
            case 1:
                shape = getPolygonShape(new Vec2(-0.21875f, -0.21875f), new Vec2(0.21875f, -0.21875f), new Vec2(0.21875f, 0.21875f), new Vec2(-0.21875f, 0.21875f), new Vec2(-0.21875f, -0.21875f));
                break;
        }

        if(shape != null)
        {
            collisionBodyDef.shape = shape;
            _collisionEntities.add(new Wall(x, y, collisionBodyDef, _state.getWorld(), _state));
        }
        else
            LRK.log("Failed to load the collision tile: " + id);
    }

    public Shape getPolygonShape(Vec2 ... points)
    {
        PolygonShape polygon = new PolygonShape();
        polygon.set(points, points.length-1);
        return polygon;
    }

    public void update(float delta)
    {
        // Remove to-be-deleted entities
        flushRemoveEntityQueue();
    }

    public void flushRemoveEntityQueue()
    {
        if(_collisionEntitiesToDelete.size() > 0)
        {
            for(AdventureEntity entity:_collisionEntitiesToDelete)
            {
                _collisionEntities.remove(entity);
                entity.destroy();
                _state.getWorld().destroyBody(entity.getBody());
            }
            _collisionEntitiesToDelete.clear();
        }
    }

    public void removeCollisionEntity(AdventureEntity entity)
    {
        _collisionEntitiesToDelete.add(entity);
    }
}
