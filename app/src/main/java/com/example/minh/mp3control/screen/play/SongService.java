package com.example.minh.mp3control.screen.play;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;

import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.screen.home.HomeActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class SongService extends Service {
    private static Context mContext;
    private ArrayList<Song> mSongs;
    private int mPosition = 0;
    private MediaPlayer mMediaPlayer;
    private MediaSessionCompat mSessionCompat;
    Handler handler = new Handler();

    int i = 0;
    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            i++;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (i == 2) {
                        playPauseMedia();
                    } else if (i == 4) {
                        nextMedia();
                    } else if (i == 6) {
                        backMedia();
                    }
                    i = 0;
                }
            }, 500);
            return false;
        }
    };

    public static Intent getIntentService(Context context, int position, ArrayList<Song> songs) {
        mContext = context;
        Intent intent = new Intent(context, SongService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(HomeActivity.SONG_LIST, (Serializable) songs);
        intent.putExtras(bundle);
        intent.putExtra(HomeActivity.SONG_POSITION, position);
        return intent;
    }


    private void initMediaSession() {
        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonReceiver.class);
        mSessionCompat = new MediaSessionCompat(getApplicationContext(), "Tag", mediaButtonReceiver, null);

        mSessionCompat.setCallback(mMediaSessionCallback);
        mSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
        mSessionCompat.setMediaButtonReceiver(pendingIntent);
    }

    public void onCreate() {
        mMediaPlayer = new MediaPlayer();
        initMediaSession();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mSessionCompat, intent);
        mSongs = new ArrayList<>();
        Bundle bundle = intent.getExtras();
        mSongs = (ArrayList<Song>) bundle.getSerializable(HomeActivity.SONG_LIST);
        mPosition = intent.getIntExtra(HomeActivity.SONG_POSITION, 0);
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        playMedia(mPosition);
        return super.onStartCommand(intent, flags, startId);
    }

    public void playPauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    public void nextMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        if (mPosition == (mSongs.size() - 1)) {
            mPosition = 0;
        } else {
            mPosition++;
        }
        playMedia(mPosition);
    }

    public void backMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        if (mPosition == 0) {
            mPosition = (mSongs.size() - 1);
        } else {
            mPosition--;
        }
        playMedia(mPosition);
    }

    public void playMedia(int position) {
        try {
            mMediaPlayer.setDataSource(mSongs.get(position).getUrl());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
