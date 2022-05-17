package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager
{
    private Sound sound;

    public SoundManager()
    {
        sound = Gdx.audio.newSound(Gdx.files.internal("PingSound.wav"));
    }

    public void playSound()
    {
        sound.play(3.0f);
    }
    public void dispose()
    {
        sound.dispose();
    }
}
