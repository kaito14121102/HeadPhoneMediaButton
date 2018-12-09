package com.example.minh.mp3control.data.source;

import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.data.source.local.SongLocalDataSource;

public class SongRepository {
    private static SongRepository mInstance;
    private SongLocalDataSource mLocal;

    private SongRepository(SongLocalDataSource localDataSource) {
        mLocal = localDataSource;
    }

    public static SongRepository getInstance(SongLocalDataSource localDataSource) {
        if (mInstance == null) {
            mInstance = new SongRepository(localDataSource);
        }
        return mInstance;
    }

    public void getData(GetDataListener<Song> listener) {
        mLocal.getSong(listener);
    }
}
