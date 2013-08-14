package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShadowMap
{
    private AdventureState _parent;
    private ArrayList<ShadowLine> _lines;

    public ShadowMap(AdventureState parent)
    {
        _parent = parent;
        _lines = new ArrayList<>();
    }

    public void addLine(float x1, float y1, float x2, float y2)
    {
        addLine(new ShadowLine(x1, y1, x2, y2));
    }

    public void addLines(ShadowLine ... lines)
    {
        int count = 0;
        for(ShadowLine line:lines)
            if(addLine(line))
                count++;

        LRK.log("Added " + count + " shadow lines");
    }

    public boolean addLine(ShadowLine line)
    {
        if(_lines.contains(line))
            return false;

        _lines.add(line);
        return true;
    }

    public ArrayList<ShadowLine> getLines()
    {
        return _lines;
    }
}
