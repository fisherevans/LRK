package com.fisherevans.lrk.managers;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:26 PM
 */
public class AudioManager
{
    private static float
            _audioMaster = 1f,
            _audioMusic = 0.8f,
            _audioSFX = 1f;

    public static void saveProperties(PrintWriter out)
    {
        out.println("audio.master=" + getAudioMaster());
        out.println("audio.music=" + getAudioMusic());
        out.println("audio.sfx=" + getAudioSFX());
    }

    public static void setProperty(String key, String value)
    {
        switch(key)
        {
            case "audio.master": _audioMaster = Float.parseFloat(value); break;
            case "audio.music": _audioMusic = Float.parseFloat(value); break;
            case "audio.sfx": _audioSFX = Float.parseFloat(value); break;
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
