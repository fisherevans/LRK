package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.sprites.Sprite;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/9/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteManager
{
    private AdventureState _parent;

    private ArrayList<Sprite> _sprites;

    public SpriteManager(AdventureState parent)
    {
        _parent = parent;
        _sprites = new ArrayList<>();
    }

    public void update(float delta)
    {
        Sprite sprite;
        for(int id = 0;id < _sprites.size();id++)
        {
            sprite = _sprites.get(id);
            sprite.update(delta);
            if(sprite.isComplete())
            {
                _sprites.remove(id);
                id--;
            }
        }
    }

    public void render(Graphics gfx, float xShift, float yShift)
    {
        for(Sprite sprite:_sprites)
            if(sprite.doRender())
                sprite.render(gfx, xShift, yShift);
    }

    public void addSprite(Sprite sprite)
    {
        if(!_sprites.contains(sprite))
            _sprites.add(sprite);
    }
}
