package com.fisherevans.lrk;

import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import sun.security.util.UntrustedCertificates;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:49 PM
 */
public class Resources
{
    public static final int
            FONT_SIZE_BASE = 8,
            FONT_MAX_SIZES = 4;

    private static UnicodeFont[] fonts = null;

    public static UnicodeFont getFont(int size)
    {
        if(fonts == null)
            generateFonts();
        size = size > FONT_MAX_SIZES ? FONT_MAX_SIZES : size;
        size = size < 1 ? 1 : size;
        return fonts[size-1];
    }

    public static void generateFonts()
    {
        try
        {
            fonts = new UnicodeFont[FONT_MAX_SIZES];
            for(int size = 1;size <= FONT_MAX_SIZES;size++)
            {
                UnicodeFont font = new UnicodeFont("res/fonts/8bm.ttf", FONT_SIZE_BASE*size*2, false, false);
                font.addAsciiGlyphs();
                font.getEffects().add(new ColorEffect());
                font.loadGlyphs();

                fonts[size-1] = font;
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to load fonts:");
            e.printStackTrace();
            System.exit(8);
        }
    }

    public static Image getImage(String location)
    {
        try
        {
            Image img = new Image(location, Image.FILTER_NEAREST);
            return img;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to load the image: " + location);
            return null;
        }
    }
}