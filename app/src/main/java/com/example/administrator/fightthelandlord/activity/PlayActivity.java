package com.example.administrator.fightthelandlord.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fightthelandlord.R;
import com.example.administrator.fightthelandlord.service.PlayService;
import com.example.administrator.fightthelandlord.tool.TransmitFlag;
import com.example.administrator.fightthelandlord.view.TableView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtnBack, mbtnPass, mbtnHint, mbtnPlay;
    private TextView mtvTime, mtvRestComputer1, mtvWordsComputer1, mtvRestComputer2, mtvWordsComputer2, mtvRestPlayer, mtvWordsPlayer;
    private ImageView ivComputer1, ivComputer2;
    private TableView mvTable;
    private LinearLayout mllComputer1, mllComputer2, mllPlayer, mllButton;

    private Handler PlayHandler = new Handler();
    protected PlayService.ServiceBinder binder;
    protected PlayActivityReceiver playActivityReceiver = new PlayActivityReceiver();
    private ServiceConnection serviceConnection;

    //用户数据
    private String UserID = "";
    //本局信息
    private String Landlord = "", Countdown = "", NowPlayer = "";
    //剩余卡牌
    private ArrayList<String> ArrayCardComputer1 = new ArrayList<>();
    private ArrayList<String> ArrayCardComputer2 = new ArrayList<>();
    private ArrayList<String> ArrayCardPlayer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        UserID = getIntent().getStringExtra("UserID");

        InitLayout();
        InitUserProgress(UserID);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (PlayService.ServiceBinder) service;  //获取其实例
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };

        //启动Service
        PlayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //注册接收器
                IntentFilter intentFilter = new IntentFilter(TransmitFlag.PlayService);
                registerReceiver(playActivityReceiver, intentFilter);
                //绑定服务
                Intent intent = new Intent(PlayActivity.this, PlayService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(playActivityReceiver);
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private void InitLayout() {
        mbtnBack = (Button) findViewById(R.id.btnBack);
        mbtnBack.setOnClickListener(this);
        mbtnPass = (Button) findViewById(R.id.btnPass);
        mbtnPass.setOnClickListener(this);
        mbtnHint = (Button) findViewById(R.id.btnHint);
        mbtnHint.setOnClickListener(this);
        mbtnPlay = (Button) findViewById(R.id.btnPlay);
        mbtnPlay.setOnClickListener(this);

        mtvTime = (TextView) findViewById(R.id.tvTime);
        mtvTime.setOnClickListener(this);
        mtvRestComputer1 = (TextView) findViewById(R.id.tvRestComputer1);
        mtvWordsComputer1 = (TextView) findViewById(R.id.tvWordsComputer1);
        mtvRestComputer2 = (TextView) findViewById(R.id.tvRestComputer2);
        mtvWordsComputer2 = (TextView) findViewById(R.id.tvWordsComputer2);
        mtvRestPlayer = (TextView) findViewById(R.id.tvRestPlayer);
        mtvWordsPlayer = (TextView) findViewById(R.id.tvWordsPlayer);

        ivComputer1 = (ImageView) findViewById(R.id.ivComputer1);
        ivComputer2 = (ImageView) findViewById(R.id.ivComputer2);

        mvTable = (TableView) findViewById(R.id.vTable);

        mllButton = (LinearLayout) findViewById(R.id.llButton);
        mllButton.setVisibility(View.INVISIBLE);
    }

    private void InitUserProgress(String userID) {
        final String userid = userID;
        new Thread(new Runnable() {
            @Override
            public void run() {
                File UserFile = new File(PlayActivity.this.getFilesDir(), userid + "_user_progress.xml");
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
                                if (tagName.equals("landlord")) {
                                    Landlord = xmlPullParser.nextText();
                                } else if (tagName.equals("countdown")) {
                                    Countdown = xmlPullParser.nextText();
                                } else if (tagName.equals("nowplayer")) {
                                    NowPlayer = xmlPullParser.nextText();
                                } else if (tagName.equals("computer1")) {
                                    ArrayCardComputer1.clear();
                                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                        ArrayCardComputer1.add(xmlPullParser.getAttributeName(i));
                                    }
                                } else if (tagName.equals("computer2")) {
                                    ArrayCardComputer2.clear();
                                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                        ArrayCardComputer2.add(xmlPullParser.getAttributeName(i));
                                    }
                                } else if (tagName.equals("player")) {
                                    ArrayCardPlayer.clear();
                                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                        ArrayCardPlayer.add(xmlPullParser.getAttributeName(i));
                                    }
                                }
                                break;
                            case XmlPullParser.TEXT:
                                String text = xmlPullParser.getText();
                                Log.e(text, xmlPullParser.getText() + "");
                                break;
                            case XmlPullParser.END_TAG:
                                // 单节点完成，可往集合里边添加新的数据
                                break;
                            default:
                                break;
                        }
                        // 用next方法处理下一个事件
                        eventType = xmlPullParser.next();
                    }
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTime:
                break;
            case R.id.btnBack:
                new AlertDialog.Builder(PlayActivity.this)
                        .setMessage("Do you want to save current progress?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Save(UserID)) {
                                    Toast.makeText(PlayActivity.this, "Save successful.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    PlayActivity.this.finish();
                                    return;
                                }
                                Toast.makeText(PlayActivity.this, "Save unsuccessfully.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("Don't", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                PlayActivity.this.finish();
                            }
                        })
                        .show();
                break;
            case R.id.btnPass:
                mllButton.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnHint:

                break;
            case R.id.btnPlay:
                mvTable.setContent("test");
                mvTable.invalidate();
                break;
            default:
                break;
        }
    }

    private boolean Save(String userID) {
        try {
            File UserFile = new File(this.getFilesDir(), userID + "_user_progress.xml");
            //获取XmlSerializer类的实例  通过xml这个工具类去获取
            XmlSerializer xmlSerializer = Xml.newSerializer();
            //设置XmlSerializer序列化参数
            FileOutputStream fos = new FileOutputStream(UserFile);
            xmlSerializer.setOutput(fos, "utf-8");
            //开始写xml文档开头
            xmlSerializer.startDocument("utf-8", true);
            //写xml的根节点     namespace 命名空间
            //用户数据

            xmlSerializer.startTag(null, "landlord");
            xmlSerializer.text(Landlord);
            xmlSerializer.endTag(null, "landlord");

            xmlSerializer.startTag(null, "countdown");
            xmlSerializer.text(Countdown);
            xmlSerializer.endTag(null, "countdown");

            xmlSerializer.startTag(null, "nowplayer");
            xmlSerializer.text(NowPlayer);
            xmlSerializer.endTag(null, "nowplayer");

            xmlSerializer.startTag(null, "computer1");
            for (String string : ArrayCardComputer1) {
                xmlSerializer.attribute(null, "rest", string);
            }
            xmlSerializer.endTag(null, "computer1");

            xmlSerializer.startTag(null, "computer2");
            for (String string : ArrayCardComputer2) {
                xmlSerializer.attribute(null, "rest", string);
            }
            xmlSerializer.endTag(null, "computer2");

            xmlSerializer.startTag(null, "player");
            for (String string : ArrayCardPlayer) {
                xmlSerializer.attribute(null, "rest", string);
            }
            xmlSerializer.endTag(null, "player");

            //結束xml結尾
            xmlSerializer.endDocument();
            //关闭流
            fos.close();

            Toast.makeText(PlayActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(PlayActivity.this, "Save unsuccessful.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class PlayActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String strState = intent.getStringExtra(TransmitFlag.State);
            Log.e("PlayActivityReceiver", strState+"");
            switch (strState) {

                default:
                    break;
            }
        }
    }
}