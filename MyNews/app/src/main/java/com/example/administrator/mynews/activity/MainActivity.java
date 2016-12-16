package com.example.administrator.mynews.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.administrator.mynews.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_activity_main)
    Toolbar toolbarActivityMain;


    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawlayout_activity_main)
    DrawerLayout drawlayoutActivityMain;
    @BindView(R.id.fl_content_activity_main)
    FrameLayout flContentActivityMain;

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentList;
    private Fragment currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarActivityMain.setTitle("新闻");
        toolbarActivityMain.setLogo(R.mipmap.ic_launcher);
        toolbarActivityMain.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbarActivityMain);
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new ImageFragment());
        fragmentList.add(new LikeFragment());
        fragmentList.add(new ChatFragment());
        //默认选择新闻
        navigation.setCheckedItem(R.id.new_navgation);
        currFragment = fragmentList.get(0);
        fragmentManager.beginTransaction().add(R.id.fl_content_activity_main, currFragment).commit();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawlayoutActivityMain, toolbarActivityMain, R
                .string.open, R.string.close);
        toggle.syncState();
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int index = 0;
                switch (item.getItemId()) {
                    case R.id.new_navgation:
                        index = 0;
                        toolbarActivityMain.setTitle("新闻");
                        break;
                    case R.id.image_navgation:

                        index = 1;
                        toolbarActivityMain.setTitle("图片");
                        break;
                    case R.id.like_navgation:
                        index = 2;
                        toolbarActivityMain.setTitle("收藏");
                        break;
                    case R.id.chat_navgation:
                        index = 3;
                        toolbarActivityMain.setTitle("对话");
                        break;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment nextFragment = fragmentList.get(index);
                //判断后续碎片不等于当前碎片
                if (nextFragment != currFragment) {
                    //判断碎片是是否已经加载过
                    if (!nextFragment.isAdded()) {
                        //严谨性判断
                        if (currFragment != null) {
                            //将上一个碎片隐藏
                            transaction.hide(currFragment);
                        }
                        //添加下一个碎片
                        transaction.add(R.id.fl_content_activity_main, nextFragment);
                    }else {
                        //防止碎片加载混乱
                        if (currFragment!=null){

                            transaction.hide(currFragment);
                        }
                        transaction.show(nextFragment);
                    }
                    //将新添加的碎片赋值为当前碎片
                   currFragment=nextFragment;
                }


                transaction.commit();
                drawlayoutActivityMain.closeDrawers();
                return true;
            }
        });
    }

}
