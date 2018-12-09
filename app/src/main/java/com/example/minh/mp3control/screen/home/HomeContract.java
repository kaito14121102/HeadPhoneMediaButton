package com.example.minh.mp3control.screen.home;

import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.ultils.BasePresenter;

import java.util.ArrayList;

public interface HomeContract {
    interface View {
        void onGetDataSuccess(ArrayList<Song> songs);
    }

    interface Presenter extends BasePresenter<View> {
        void getSongs();
    }
}
