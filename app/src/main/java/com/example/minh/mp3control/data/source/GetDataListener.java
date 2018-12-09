package com.example.minh.mp3control.data.source;

import java.util.ArrayList;

public interface GetDataListener<T> {
    void onSuccess(ArrayList<T> data);

    void onError();
}
