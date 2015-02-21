package com.mycompany.festivalhackmusic;

import android.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by AmilaPrabandhika on 21/02/2015.
 */
public class MediaPlayerService {

    static MediaPlayer mediaPlayer;

    public MediaPlayerService()
    {
        if(mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
        }
    }

    public void PlayMusic(File trackFile) throws IOException {
        FileInputStream fis = new FileInputStream(trackFile);

        if(mediaPlayer.isPlaying())
        {
            FadeIn();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }


        mediaPlayer.setDataSource(fis.getFD());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void FadeOut()
    {
        float volume = 1;
        float speed = 0.05f;

        mediaPlayer.setVolume(volume, volume);

    }

    private void FadeIn()
    {
        float volume = 1;
        float speed = 0.05f;

        mediaPlayer.setVolume(volume, volume);

    }

}
