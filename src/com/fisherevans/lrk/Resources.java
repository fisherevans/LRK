package com.fisherevans.lrk;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:49 PM
 */
public class Resources
{
    private static final String[] _fontFNTFiles = {
            "res/fonts/8bm1.fnt",
            "res/fonts/8bm2.fnt",
            "res/fonts/8bm3.fnt",
            "res/fonts/8bm4.fnt",
            "res/fonts/8bm5.fnt",
            "res/fonts/8bm6.fnt",
            "res/fonts/8bm7.fnt",
            "res/fonts/8bm8.fnt",
    };

    private static final String[] _fontPNGFiles = {
            "res/fonts/8bm1_0.png",
            "res/fonts/8bm2_0.png",
            "res/fonts/8bm3_0.png",
            "res/fonts/8bm4_0.png",
            "res/fonts/8bm5_0.png",
            "res/fonts/8bm6_0.png",
            "res/fonts/8bm7_0.png",
            "res/fonts/8bm8_0.png",
    };

    private static AngelCodeFont[] _fonts = null;

    public static String ERROR_IMAGE_KEY = "error";
    public static String ERROR_IMAGE_LOCATION = "res/images/error.png";

    private static Map<String, Image> _images = null;

    /**
     * the sizes are based on this object's static vars
     * @param size the size of the font to get
     * @return the font of that size
     */
    public static AngelCodeFont getFont(int size)
    {
        if(_fonts == null)
            generateFonts();

        size--;

        if(size < 0)
            return _fonts[0];
        else if(size >= _fontFNTFiles.length)
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
            _fonts = new AngelCodeFont[_fontFNTFiles.length];
            for(int id = 0;id < _fontFNTFiles.length;id++)
                _fonts[id] = new AngelCodeFont(_fontFNTFiles[id], getAbsoluteImage(_fontPNGFiles[id]));
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
    public static Image getAbsoluteImage(String location)
    {
        try
        {
            return new Image(location, false, Image.FILTER_NEAREST);
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
            System.out.println("Failed to load the image: " + location);

            if(location == ERROR_IMAGE_LOCATION)
                return getAbsoluteImage(ERROR_IMAGE_LOCATION);
            else
                return null;
        }
    }

    public static Image getImage(String key)
    {
        if(_images == null)
            loadImages();

        Image image = _images.get(key).copy();

        if(image == null && key != ERROR_IMAGE_KEY)
            return getImage(ERROR_IMAGE_KEY);
        else
            return image;
    }

    public static void loadImages()
    {
        _images = new HashMap<String, Image>();
        loadImages(_images, "", "res/images");

        for(String key:_images.keySet())
            System.out.printf("%40s - %s\n", key, _images.get(key).getResourceReference());
    }

    private static void loadImages(Map<String, Image> map, String prefix, String location)
    {
        File parent = new File(location);
        if(parent.isDirectory())
        {
            for(File child:parent.listFiles())
            {
                if(child.isDirectory())
                    loadImages(map, prefix + child.getName() + "/", child.getAbsolutePath());
                else if(child.isFile())
                    map.put(prefix + child.getName().replaceAll("\\..*", ""), getAbsoluteImage(child.getAbsolutePath()));
            }
        }
    }
}