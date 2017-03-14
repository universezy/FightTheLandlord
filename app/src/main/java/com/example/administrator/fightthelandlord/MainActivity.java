package com.example.administrator.fightthelandlord;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView mtvName, mtvLevel, mtvRecord, mtvWinRate, mtvAbout;
    private Button mbtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitLayout();
    }

    private void InitLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mtvName = (TextView) findViewById(R.id.tvName);
        mtvLevel = (TextView) findViewById(R.id.tvLevel);
        mtvRecord = (TextView) findViewById(R.id.tvRecord);
        mtvWinRate = (TextView) findViewById(R.id.tvWinRate);
        mtvAbout = (TextView) findViewById(R.id.tvAbout);

        mbtnLogout = (Button) findViewById(R.id.btnLogout);
        mbtnLogout.setOnClickListener(this);

    }

    /**
     * 返回键关闭抽屉界面
     **/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAbout:

                break;
            case R.id.btnLogout:

                break;
            default:
                break;
        }
    }

}
