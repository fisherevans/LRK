package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/3/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class MusicManager implements MusicListener
{
    private static MusicManager _itself;

    private static Music _currentMusic, _nextMusic;
    private static boolean _loopLastSong = true;

    private static final int FADE_IN_DURATION = 10000;
    private static final int FADE_OUT_DURATION = 3000;

    public static void init(MusicManager itself)
    {
        _itself = itself;

        _currentMusic = null;
        _nextMusic = null;
    }

    public static void play(String musicKey)
    {
        LRK.log("play() " + musicKey);

        Music music = Resources.getMusic(musicKey);
        if(music == null)
            LRK.log("Failed to load the music item: " + musicKey);
        else
        {
            music.removeListener(_itself);
            music.addListener(_itself);
            _nextMusic = music;
            skipTrack();
        }
    }

    private static void skipTrack()
    {
        LRK.log("skipTrack()");

        if(_currentMusic != null && _currentMusic.playing())
            _currentMusic.fade(FADE_OUT_DURATION, 0f, true);
        else
            playNext();
    }

    private static void playNext()
    {
        LRK.log(String.format("playNext() Current: %s - Next: %s",
                (_currentMusic == null ? "null" : _currentMusic.toString()),
                (_nextMusic == null ? "null" : _nextMusic.toString())));

        if(_currentMusic != null)
            _currentMusic.stop();

        if(_nextMusic != null)
            _currentMusic = _nextMusic;
        else if(!_loopLastSong)
            _currentMusic = null;

        if(_currentMusic != null)
        {
            _currentMusic.stop();
            _currentMusic.setVolume(1f);
            _currentMusic.play();
        }
    }

    @Override
    public void musicEnded(Music music)
    {
        LRK.log("musicEnded() " + (music == null ? "null" : music.toString()));
        playNext();
    }

    @Override
    public void musicSwapped(Music music, Music newMusic)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void close()
    {
        if(_currentMusic != null)
            _currentMusic.stop();
    }
}
