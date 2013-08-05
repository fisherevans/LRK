package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import org.newdawn.slick.Sound;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/3/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class SoundManager
{
    private static SoundManager _itself;

    public static void init(SoundManager itself)
    {
        _itself = itself;
    }

    public static void play(String soundKey)
    {
        Sound sound = Resources.getSound(soundKey);
        if(sound != null)
        {
            sound.play();
        }
        else
            LRK.log("Failed to play the sound: " + soundKey);
    }
}
