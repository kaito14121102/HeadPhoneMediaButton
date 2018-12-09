package com.example.minh.mp3control.data.source.local;

import android.os.Environment;

import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.data.source.GetDataListener;

import java.io.File;
import java.util.ArrayList;

public class SongLocalDataSource {
    private static SongLocalDataSource mInstance;
    String mp3Pattern = ".mp3";

    private SongLocalDataSource() {
    }

    public static SongLocalDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new SongLocalDataSource();
        }
        return mInstance;
    }

    public void getSong(GetDataListener<Song> listener) {
        listener.onSuccess(getPlayList());
    }

    public ArrayList<Song> getPlayList() {
        String MEDIA_PATH = Environment.getExternalStorageDirectory()
                .getPath() + "/";
        ArrayList<Song> songsList = new ArrayList<>();

        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file, songsList);
                    } else {
                        addSongToList(file, songsList);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }

    private void scanDirectory(File directory, ArrayList<Song> songs) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file, songs);
                    } else {
                        addSongToList(file, songs);
                    }
                }
            }
        }
    }

    private void addSongToList(File file, ArrayList<Song> songs) {
        if (file.getName().endsWith(mp3Pattern)) {
            songs.add(new Song(file.getName(), file.getAbsolutePath()));
        }
    }
}
