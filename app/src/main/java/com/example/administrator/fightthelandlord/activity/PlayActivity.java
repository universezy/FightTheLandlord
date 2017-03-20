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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.fightthelandlord.R;
import com.example.administrator.fightthelandlord.service.PlayService;
import com.example.administrator.fightthelandlord.tool.TransmitFlag;
import com.example.administrator.fightthelandlord.view.TableView;

import java.util.ArrayList;

/**
 * 游戏
 **/
public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtnBack, mbtnPass, mbtnHint, mbtnPlay;
    private TextView mtvTime, mtvRestComputer1, mtvWordsComputer1, mtvRestComputer2, mtvWordsComputer2, mtvRestPlayer, mtvWordsPlayer;
    private ImageView ivComputer1, ivComputer2;
    private TableView mvTable;
    private LinearLayout mllComputer1, mllComputer2, mllPlayer, mllButton;

    public static PlayActivity playActivity;
    private Handler PlayHandler = new Handler();
    protected PlayService.ServiceBinder binder;
    protected PlayActivityReceiver playActivityReceiver = new PlayActivityReceiver();
    private ServiceConnection serviceConnection;

    //用户数据
    private String UserID = "";
    //本局信息
    private String Landlord = "", NowPlayer = "";
    //玩家手牌
    private ArrayList<String> ArrayCardPlayer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.playActivity = this;
        setContentView(R.layout.activity_play);
        UserID = getIntent().getStringExtra(TransmitFlag.UserID);
        final String StartType = getIntent().getStringExtra(TransmitFlag.StartType);
        InitLayout();

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
                intent.putExtra(TransmitFlag.UserID, UserID);
                intent.putExtra(TransmitFlag.StartType, StartType);
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

    /**
     * 初始化布局
     **/
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
                                //TODO
                                Intent intent_Save = new Intent(TransmitFlag.PlayActivity);
                                intent_Save.putExtra(TransmitFlag.State, TransmitFlag.Save);
                                sendBroadcast(intent_Save);
                                dialog.dismiss();
                                PlayActivity.this.finish();
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

                break;
            default:
                break;
        }
    }

    /**
     * 选牌
     **/
    public ArrayList<String> ChooseCards(ArrayList<String> cards) {
        ArrayList<String> chooseCards = new ArrayList<>();
        mllButton.setVisibility(View.VISIBLE);
        //实现选牌
        for (String chooseCard : chooseCards) {
            int index = ArrayCardPlayer.indexOf(chooseCard);
            chooseCards.add(chooseCard);
            ArrayCardPlayer.remove(index);
        }

        return chooseCards;
    }


    /**
     * 接收器
     **/
    class PlayActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String strState = intent.getStringExtra(TransmitFlag.State);
            switch (strState) {
                case TransmitFlag.NowTurn:
                    ArrayList<String> ArrayNowCards = intent.getStringArrayListExtra(TransmitFlag.NowCards);
                    NowPlayer = intent.getStringExtra(TransmitFlag.NowPlayer);
                    mvTable.setContent(ArrayNowCards);
                    mvTable.invalidate();
                    break;
                case TransmitFlag.PlayerCards:
                    ArrayCardPlayer = intent.getStringArrayListExtra(TransmitFlag.PlayerCards);

                    break;
                case TransmitFlag.TurnEnd:

                    break;
                default:
                    break;
            }
        }
    }
}