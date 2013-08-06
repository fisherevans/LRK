package com.fisherevans.lrk.tools;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/16/13
 * Time: 8:46 PM
 */
public class JBox2DUtils
{
    /**
     * returns a predefined fixturedef for a shape predefined specifically for the collision layer of the tiled map.
     * @param id the tile id to get the shape for
     * @return the fixture def that has the predefined shape in it
     */
    public static FixtureDef generateFixture(int id)
    {
        FixtureDef fd = new FixtureDef();
        fd.density = 1f;
        fd.friction = 0.2f;
        fd.restitution = 0.0f;

        PolygonShape shape = new PolygonShape();
        Vec2[] vs = {};
        switch(id)
        {
            case 1: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 2: vs = new Vec2 [] { new Vec2(-0.5f, 0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f),  }; break;
            case 3: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 4: vs = new Vec2 [] { new Vec2(-0.5f, 0.5f), new Vec2(0.5f, -0.0f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f),  }; break;
            case 5: vs = new Vec2 [] { new Vec2(-0.5f, -0.0f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.0f),  }; break;
            case 6: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.0f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 7: vs = new Vec2 [] { new Vec2(-0.5f, -0.0f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.0f),  }; break;
            case 10: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 11: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 12: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, -0.0f), new Vec2(-0.5f, -0.5f),  }; break;
            case 13: vs = new Vec2 [] { new Vec2(-0.5f, -0.0f), new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, -0.0f),  }; break;
            case 14: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, -0.0f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 15: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(-0.5f, -0.0f), new Vec2(-0.5f, -0.5f),  }; break;
            case 17: vs = new Vec2 [] { new Vec2(0.0f, 0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(0.0f, 0.5f),  }; break;
            case 18: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.0f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 19: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(0.0f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 20: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.0f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 21: vs = new Vec2 [] { new Vec2(0.0f, -0.0f), new Vec2(0.5f, -0.0f), new Vec2(0.5f, 0.5f), new Vec2(0.0f, 0.5f), new Vec2(0.0f, -0.0f),  }; break;
            case 22: vs = new Vec2 [] { new Vec2(-0.5f, -0.0f), new Vec2(0.0f, -0.0f), new Vec2(0.0f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.0f),  }; break;
            case 23: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, -0.0f), new Vec2(-0.5f, -0.0f), new Vec2(-0.5f, -0.5f),  }; break;
            case 24: vs = new Vec2 [] { new Vec2(-0.5f, -0.0f), new Vec2(0.5f, -0.0f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.0f),  }; break;
            case 25: vs = new Vec2 [] { new Vec2(-0.5f, 0.5f), new Vec2(0.0f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f),  }; break;
            case 26: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.0f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 27: vs = new Vec2 [] { new Vec2(0.0f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(0.0f, -0.5f),  }; break;
            case 28: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.0f, -0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 29: vs = new Vec2 [] { new Vec2(0.0f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, -0.0f), new Vec2(0.0f, -0.0f), new Vec2(0.0f, -0.5f),  }; break;
            case 30: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.0f, -0.5f), new Vec2(0.0f, -0.0f), new Vec2(-0.5f, -0.0f), new Vec2(-0.5f, -0.5f),  }; break;
            case 31: vs = new Vec2 [] { new Vec2(-0.5f, -0.5f), new Vec2(0.0f, -0.5f), new Vec2(0.0f, 0.5f), new Vec2(-0.5f, 0.5f), new Vec2(-0.5f, -0.5f),  }; break;
            case 32: vs = new Vec2 [] { new Vec2(0.0f, -0.5f), new Vec2(0.5f, -0.5f), new Vec2(0.5f, 0.5f), new Vec2(0.0f, 0.5f), new Vec2(0.0f, -0.5f),  }; break;
        }

        if(vs.length <= 3) return null; // if it's not a valid shape (not even a triangle) return null

        try
        {
            shape.set(vs, vs.length-1);
            fd.shape = shape;
        }
        catch(Exception e)
        {
            System.out.println("Error generating shape - ID: "+ id);
            return null;
        }

        return fd;
    }
}
