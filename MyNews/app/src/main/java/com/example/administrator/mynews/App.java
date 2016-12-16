package com.example.administrator.mynews;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

/**
 * Created by Administrator on 2016/12/13.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
    }
}
