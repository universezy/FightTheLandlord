package com.example.administrator.fightthelandlord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView mtvName, mtvLevel, mtvRecord, mtvWinRate;
    private Button mbtnQuit, mbtnLogout,mbtnNew,mbtnContinue;
    private ImageView mivPortrait;

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

        mbtnQuit = (Button) findViewById(R.id.btnQuit);
        mbtnQuit.setOnClickListener(this);
        mbtnLogout = (Button) findViewById(R.id.btnLogout);
        mbtnLogout.setOnClickListener(this);
        mbtnNew = (Button) findViewById(R.id.btnNew);
        mbtnNew.setOnClickListener(this);
        mbtnContinue = (Button) findViewById(R.id.btnContinue);
        mbtnContinue.setOnClickListener(this);

        mivPortrait = (ImageView)findViewById(R.id.ivPortrait);
        mivPortrait.setOnClickListener(this);
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
            case R.id.btnNew:
                Intent intentNew = new Intent(MainActivity.this,PlayActivity.class);
                startActivity(intentNew);
                break;
            case R.id.btnContinue:
                Intent intentContinue = new Intent(MainActivity.this,PlayActivity.class);
                startActivity(intentContinue);
                break;
            case R.id.btnQuit:
                MainActivity.this.finish();
                break;
            case R.id.btnLogout:
                Intent intentLogout = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intentLogout);
                MainActivity.this.finish();
                break;
            case R.id.ivPortrait:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

}
