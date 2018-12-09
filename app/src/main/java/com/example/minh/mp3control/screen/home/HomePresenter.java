package com.example.minh.mp3control.screen.home;

import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.data.source.GetDataListener;
import com.example.minh.mp3control.data.source.SongRepository;

import java.util.ArrayList;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private SongRepository mRepository;

    public HomePresenter(SongRepository repository) {
        mRepository = repository;
    }

    @Override
    public void getSongs() {
        mRepository.getData(new GetDataListener<Song>() {
            @Override
            public void onSuccess(ArrayList<Song> data) {
                mView.onGetDataSuccess(data);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }
}
