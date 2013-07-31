package com.fisherevans.lrk;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:49 PM
 */
public class Resources
{
    private static final String[] _fntFiles = {
            "res/fonts/8bm1.fnt",
            "res/fonts/8bm2.fnt",
            "res/fonts/8bm3.fnt",
            "res/fonts/8bm4.fnt",
    };

    private static final String[] _pngFiles = {
            "res/fonts/8bm1_0.png",
            "res/fonts/8bm2_0.png",
            "res/fonts/8bm3_0.png",
            "res/fonts/8bm4_0.png",
    };

    private static AngelCodeFont[] _fonts = null;

    /**
     * the sizes are based on this object's static vars
     * @param size the size of the font to get
     * @return the font of that size
     */
    public static AngelCodeFont getFont(int size)
    {
        if(_fonts == null)
            generateFonts();

        if(size < 0)
            return _fonts[0];
        else if(size >= _fntFiles.length)
            return _fonts[_fonts.length-1];
        else
            return _fonts[size];
    }

    /**
     * generates premade fonts for general use
     */
    public static void generateFonts()
    {
        try
        {
            _fonts = new AngelCodeFont[_fntFiles.length];
            for(int id = 0;id < _fntFiles.length;id++)
                _fonts[id] = new AngelCodeFont(_fntFiles[id], getImage(_pngFiles[id]));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load fonts:");
            e.printStackTrace();
            System.exit(8);
        }
    }

    /**
     * loads an image
     * @param location file name of the image (relative)
     * @return the image
     */
    public static Image getImage(String location)
    {
        try
        {
            return new Image(location, false, Image.FILTER_NEAREST);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to load the image: " + location);
            return null;
        }
    }
}