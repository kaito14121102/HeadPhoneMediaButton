package com.example.minh.mp3control.screen.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minh.mp3control.data.Model.Song;
import com.example.minh.mp3control.R;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private ArrayList<Song> mSongs;
    private Context mContext;

    public HomeAdapter(Context context) {
        mContext = context;
        mSongs = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mSongs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addData(ArrayList<Song> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView mTextName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_song, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextName = view.findViewById(R.id.text_name_song);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Song song = mSongs.get(i);
        viewHolder.mTextName.setText(song.getName());
        return view;
    }
}
