package com.fisherevans.lrk.states.adventure;

import java.util.Comparator;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/10/13
 * Time: 1:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntityCompareY implements Comparator<AdventureEntity>
{
    @Override
    public int compare(AdventureEntity ent1, AdventureEntity ent2)
    {
        if(ent1.getY() > ent2.getY())
            return 1;
        else if(ent1.getY() < ent2.getY())
            return -1;
        else
            return 0;
    }
}
