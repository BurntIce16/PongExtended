package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager
{
    private Sound sound;
    private Sound bad;

    public SoundManager()
    {
        sound = Gdx.audio.newSound(Gdx.files.internal("PingSound.wav"));
        bad = Gdx.audio.newSound(Gdx.files.internal("MinusPt.wav"));
    }
    public void playBad () {bad.play(3.0f);}

    public void playSound()
    {
        sound.play(3.0f);
    }
    public void dispose()
    {
        sound.dispose();
        bad.dispose();
    }
}
