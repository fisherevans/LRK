package com.fisherevans.lrk;

import org.newdawn.slick.*;

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

    public static String ERROR_IMAGE_LOCATION = "res/images/error.png";

    private static Map<String, Object> _images = null;
    private static Map<String, Object> _music = null;
    private static Map<String, Object> _sound = null;

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
            loadResources();

        Object o = _images.get(key);
        if(o != null && o instanceof Image)
            return (Image) o;
        else
            return null;
    }

    public static Music getMusic(String key)
    {
        if(_music == null)
            loadResources();

        Object o = _music.get(key);
        if(o != null && o instanceof Music)
            return (Music) o;
        else
            return null;
    }

    public static Sound getSound(String key)
    {
        if(_sound == null)
            loadResources();

        Object o = _sound.get(key);
        if(o != null && o instanceof Sound)
            return (Sound) o;
        else
            return null;
    }

    public static void loadResources()
    {
        if(_sound == null)
            loadResources(Sound.class, (_sound = new HashMap<>()), "", "res/sounds");

        if(_music == null)
            loadResources(Music.class, (_music = new HashMap<>()), "", "res/music");

        if(_images == null)
            loadResources(Image.class, (_images = new HashMap<>()), "", "res/images");
    }

    private static void loadResources(Class type, Map<String, Object> map, String prefix, String location)
    {
        File parent = new File(location);
        if(parent.isDirectory())
        {
            for(File child:parent.listFiles())
            {
                if(child.isDirectory())
                    loadResources(type, map, prefix + child.getName() + "/", child.getAbsolutePath());
                else
                {
                    if (child.isFile())
                        try
                        {
                            Object o = null;

                            if(type == Image.class)
                                o = getAbsoluteImage(child.getAbsolutePath());
                            else if(type == Sound.class)
                                o = new Sound(child.getAbsolutePath());
                            else if(type == Music.class)
                                o = new Music(child.getAbsolutePath());

                            map.put(prefix + child.getName().replaceAll("\\..*", ""), o);
                        } catch (SlickException e)
                        {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                }
            }
        }
    }
}