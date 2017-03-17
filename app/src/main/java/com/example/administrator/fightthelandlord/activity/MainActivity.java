package com.example.administrator.fightthelandlord.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.fightthelandlord.R;
import com.example.administrator.fightthelandlord.tool.TransmitFlag;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView mtvName, mtvLevel, mtvRecord, mtvWinRate;
    private Button mbtnQuit, mbtnLogout, mbtnNew, mbtnContinue;
    private ImageView mivPortrait;

    //用户数据
    private String UserID, UserName, UserLevel, UserRecord_win, UserRecord_lose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserID = getIntent().getStringExtra(TransmitFlag.UserID);

        InitLayout();
        InitUserData();
    }

    /**
     * 初始化布局
     **/
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

        mivPortrait = (ImageView) findViewById(R.id.ivPortrait);
        mivPortrait.setOnClickListener(this);
    }

    /**
     * 初始化用户数据
     **/
    private void InitUserData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("InitUserData","InitUserData");
                File UserFile = new File(MainActivity.this.getFilesDir(), UserID + "_user_data.xml");
                try {
                    FileInputStream is = new FileInputStream(UserFile);
                    //调用我们定义  解析xml的业务方法
                    XmlPullParser xmlPullParser = Xml.newPullParser();
                    xmlPullParser.setInput(is, "utf-8");
                    //开始解析事件
                    int eventType = xmlPullParser.getEventType();
                    //处理事件，不碰到文档结束就一直处理
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        Log.e("eventType", eventType + "");
                        //因为定义了一堆静态常量，所以这里可以用switch
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                // 不做任何操作或初始化数据
                                break;
                            case XmlPullParser.START_TAG:
                                // 解析XML节点数据
                                // 获取当前标签名字
                                String tagName = xmlPullParser.getName();
                                if (tagName.equals("name")) {
                                    UserName = xmlPullParser.nextText();
                                    mtvName.setText(UserName);
                                } else if (tagName.equals("level")) {
                                    UserLevel = xmlPullParser.nextText();
                                    mtvLevel.setText(UserLevel);
                                } else if (tagName.equals("record_win")) {
                                    UserRecord_win = xmlPullParser.nextText();
                                } else if (tagName.equals("record_lose")) {
                                    UserRecord_lose = xmlPullParser.nextText();
                                    mtvRecord.setText("Win" + UserRecord_win + "Lose" + UserRecord_lose);
                                    // 创建一个数值格式化对象
                                    NumberFormat numberFormat = NumberFormat.getInstance();
                                    // 设置精确到小数点后2位
                                    numberFormat.setMaximumFractionDigits(2);
                                    float rate;
                                    int total = Integer.parseInt(UserRecord_win) + Integer.parseInt(UserRecord_lose);
                                    if (total == 0) {
                                        rate = 0;
                                    } else {
                                        rate = (float) Integer.parseInt(UserRecord_win) / (float) total * 100;
                                    }
                                    mtvWinRate.setText(numberFormat.format(rate) + "%");
                                }
                                break;
                            case XmlPullParser.TEXT:
                                String text = xmlPullParser.getText();
                                Log.e(text, xmlPullParser.getText() + "");
                                break;
                            case XmlPullParser.END_TAG:
                                // 单节点完成，可往集合里边添加新的数据
                                break;
                            case XmlPullParser.END_DOCUMENT:
                                break;
                            default:
                                break;
                        }
                        // 用next方法处理下一个事件
                        eventType = xmlPullParser.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                Intent intentNew = new Intent(MainActivity.this, PlayActivity.class);
                intentNew.putExtra(TransmitFlag.StartType,TransmitFlag.NewGame);
                intentNew.putExtra(TransmitFlag.UserID, UserID);
                startActivity(intentNew);
                break;
            case R.id.btnContinue:
                Intent intentContinue = new Intent(MainActivity.this, PlayActivity.class);
                intentContinue.putExtra(TransmitFlag.StartType,TransmitFlag.ContinueGame);
                intentContinue.putExtra(TransmitFlag.UserID, UserID);
                startActivity(intentContinue);
                break;
            case R.id.btnQuit:
                MainActivity.this.finish();
                break;
            case R.id.btnLogout:
                Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
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
