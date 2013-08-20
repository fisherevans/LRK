package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.entities.Wall;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.tiled.TiledMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

    private Map<Integer, Shape> _collisionShapes;

    private int _collisionMapIndex;

    public CollisionManager(AdventureState state)
    {
        _state = state;

        _collisionEntities = new ArrayList<>();
        _collisionEntitiesToDelete = new ArrayList<>();

        _collisionMapIndex = _state.getTiledMap().getLayerIndex("collision");

        loadCollisionShapes();
    }

    private void loadCollisionShapes()
    {
        _collisionShapes = new HashMap<>();
        try
        {
            Scanner input = new Scanner(new File("res/tilesheets/collision_definitions.txt")); // read the file
            String line;
            String[] lineSplit, pointStrings, coords;
            ArrayList<Vec2> points;
            while(input.hasNextLine()) // for each line
            {
                try
                {
                    line = input.nextLine().replaceAll(" +", ""); // strip white spaces
                    lineSplit = line.split("="); // split up id and points
                    if(lineSplit[0].startsWith("#") || lineSplit.length < 2) // if it's a comment or there's no points, skip
                        continue;
                    pointStrings = lineSplit[1].split(";"); // get the points in string form
                    PolygonShape shape = new PolygonShape();
                    points = new ArrayList<>();
                    for(String pointString:pointStrings) // for each point
                    {
                        coords = pointString.split(","); // split up x and y
                        if(coords.length < 2) // if there's not 2 coords, skip it
                            continue;
                        points.add(new Vec2(new Float(coords[0]), new Float(coords[1]))); // add the new point
                    }
                    points.add(points.get(0).clone()); // loop back and add the first point
                    shape.set(points.toArray(new Vec2[] { }), points.size()-1);
                    _collisionShapes.put(new Integer(lineSplit[0]), shape); // store the polygon
                } catch (Exception e) // if there is an error parsing a line
                {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) // if there is an error loading the file
        {
            e.printStackTrace();
        }
    }

    public void processTile(int x, int y, TiledMap tiledMap)
    {
        processCollisionTile(x, y, tiledMap.getLocalTileId(x, y, _collisionMapIndex));
    }

    private void processCollisionTile(int x, int y, int id)
    {
        if(_collisionShapes != null && _collisionShapes.containsKey(id))
        {
            FixtureDef collisionBodyDef = new FixtureDef();
            collisionBodyDef.density = 1f;
            collisionBodyDef.friction = 0.2f;
            collisionBodyDef.restitution = 0.0f;
            collisionBodyDef.shape = _collisionShapes.get(id);
            _collisionEntities.add(new Wall(x, y, collisionBodyDef, _state.getWorld(), _state));
        }
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

    public Map<Integer, Shape> getCollisionShapes()
    {
        return _collisionShapes;
    }
}
