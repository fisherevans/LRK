package com.fisherevans.lrk.managers;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:26 PM
 */
public class AudioManager extends ComponentManager
{
    private static float
            _audioMaster = 1f,
            _audioMusic = 0.8f,
            _audioSFX = 1f;

    @Override
    public void saveProperties(PrintWriter out)
    {
        out.println("audio.master=" + getAudioMaster());
        out.println("audio.music=" + getAudioMusic());
        out.println("audio.sfx=" + getAudioSFX());
    }

    @Override
    public void setProperty(String key, String value)
    {
        switch(key)
        {
            case "master": _audioMaster = Float.parseFloat(value); break;
            case "music": _audioMusic = Float.parseFloat(value); break;
            case "sfx": _audioSFX = Float.parseFloat(value); break;
        }
    }

    public static float getAudioMaster()
    {
        return _audioMaster;
    }

    public static void setAudioMaster(float audioMaster)
    {
        _audioMaster = audioMaster;
    }

    public static float getAudioMusic()
    {
        return _audioMusic;
    }

    public static void setAudioMusic(float audioMusic)
    {
        _audioMusic = audioMusic;
    }

    public static float getAudioSFX()
    {
        return _audioSFX;
    }

    public static void setAudioSFX(float audioSFX)
    {
        _audioSFX = audioSFX;
    }
}
