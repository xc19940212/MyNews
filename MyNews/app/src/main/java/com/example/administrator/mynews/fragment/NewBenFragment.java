package com.example.administrator.mynews.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.mynews.BuildConfig;
import com.example.administrator.mynews.R;
import com.example.administrator.mynews.adapter.NewBenAdapter;
import com.example.administrator.mynews.entity.NewBean;
import com.example.administrator.mynews.utils.Constant;
import com.example.administrator.mynews.utils.NoHttpInstences;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class NewBenFragment extends Fragment{

    private SwipeRefreshLayout layout;
    private ListView listview;
    private List<NewBean.ResultBean.DataBean> datas;
    private String url;
    private NewBenAdapter adapter;
    public NewBenFragment(String url) {
        this.url=url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (SwipeRefreshLayout) inflater.inflate(R.layout.listview_newben_fragment_activity,
                null);
        listview= (ListView) layout.findViewById(R.id.listview_news_fragment_activity_main);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        adapter.datas.remove(0);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                layout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Request<String> stringRequest = NoHttp.createStringRequest(Constant.BASE_URL +url);
        NoHttpInstences.getInstance().add(Constant.WHAT_NENS_REQUEST, stringRequest, new OnResponseListener<String>() {



            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String result = response.get();
                NewBean newBean = JSON.parseObject(result, NewBean.class);
                datas=newBean.getResult().getData();
                adapter = new NewBenAdapter(datas,getContext());
                listview.setAdapter(adapter);

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (BuildConfig.DEBUG)
                    Log.d("NewBenFragment", "错误:"+"response.getException():" + response
                            .getException());
            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
