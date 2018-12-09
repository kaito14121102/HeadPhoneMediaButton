package com.example.minh.mp3control.data.Model;

import java.io.Serializable;

public class Song implements Serializable {
    private String mName;
    private String mUrl;

    public Song(String name, String url) {
        mName = name;
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
