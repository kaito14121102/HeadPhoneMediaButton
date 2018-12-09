package com.example.minh.mp3control.screen.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.minh.mp3control.R;
import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.data.source.SongRepository;
import com.example.minh.mp3control.data.source.local.SongLocalDataSource;
import com.example.minh.mp3control.screen.play.RemoteControlReceiver;
import com.example.minh.mp3control.screen.play.SongService;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, AdapterView.OnItemClickListener {
    private ListView mListSong;
    private Button mButton;
    private HomeAdapter mAdapter;
    private ArrayList<Song> mSongs;
    public static String SONG_LIST = "song_list";
    public static String SONG_POSITION = "song_position";
    RemoteControlReceiver receiver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidget();
        onRequestReadPermisson();
    }

    private void initData() {
        SongLocalDataSource localDataSource = SongLocalDataSource.getInstance();
        SongRepository repository = SongRepository.getInstance(localDataSource);
        HomePresenter presenter = new HomePresenter(repository);
        presenter.setView(this);
        presenter.getSongs();
    }

    private void initWidget() {
        mSongs = new ArrayList<>();
        mListSong = findViewById(R.id.list_song);
        mAdapter = new HomeAdapter(this);
        mListSong.setAdapter(mAdapter);
        mListSong.setOnItemClickListener(this);
    }

    @Override
    public void onGetDataSuccess(ArrayList<Song> songs) {
        if (songs != null) {
            mAdapter.addData(songs);
            mSongs.clear();
            mSongs.addAll(songs);
        }
    }

    private void onRequestReadPermisson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startService(SongService.getIntentService(this, i, mSongs));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
