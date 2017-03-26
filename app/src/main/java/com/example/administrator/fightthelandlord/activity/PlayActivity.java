package com.example.administrator.fightthelandlord.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.fightthelandlord.R;
import com.example.administrator.fightthelandlord.service.PlayService;
import com.example.administrator.fightthelandlord.tool.CardUtil;
import com.example.administrator.fightthelandlord.tool.ChooseUtil;
import com.example.administrator.fightthelandlord.tool.TransmitFlag;
import com.example.administrator.fightthelandlord.view.TableView;
import com.example.administrator.fightthelandlord.view.TableViewResult;

import java.util.ArrayList;


/**
 * 游戏
 **/
public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbtnBack, mbtnPass, mbtnHint, mbtnPlay;
    private TextView mtvLandlord, mtvRestComputer1, mtvWordsComputer1, mtvRestComputer2, mtvWordsComputer2, mtvRestPlayer, mtvWordsPlayer;
    private ImageView ivComputer1, ivComputer2;
    private TableView mvTableComputer1, mvTableComputer2, mvTablePlayer, mvTableLandlord;
    private TableViewResult mvTableResult;
    private LinearLayout mllComputer1, mllComputer2, mllPlayer, mllButton, mllPlayerCards;

    private Handler PlayHandler = new Handler();
    protected PlayService.ServiceBinder binder;
    protected PlayActivityReceiver playActivityReceiver = new PlayActivityReceiver();
    private ServiceConnection serviceConnection;
    private View.OnClickListener PlayerCardsListener;

    //用户数据
    private String UserID = "";
    //本局信息
    private String Landlord = "", NowPlayer = "";
    //场牌
    private ArrayList<String> ArrayNowCards = new ArrayList<>();
    //玩家手牌
    private ArrayList<String> ArrayPlayerCards = new ArrayList<>();
    //玩家选牌
    private ArrayList<String> ArrayChooseCards = new ArrayList<>();
    //地主牌
    private ArrayList<String> ArrayLandlordCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                IntentFilter intentFilter = new IntentFilter(TransmitFlag.PlayActivity);
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

        mtvLandlord = (TextView) findViewById(R.id.tvLandlord);
        mtvRestComputer1 = (TextView) findViewById(R.id.tvRestComputer1);
        mtvWordsComputer1 = (TextView) findViewById(R.id.tvWordsComputer1);
        mtvRestComputer2 = (TextView) findViewById(R.id.tvRestComputer2);
        mtvWordsComputer2 = (TextView) findViewById(R.id.tvWordsComputer2);
        mtvRestPlayer = (TextView) findViewById(R.id.tvRestPlayer);
        mtvWordsPlayer = (TextView) findViewById(R.id.tvWordsPlayer);

        ivComputer1 = (ImageView) findViewById(R.id.ivComputer1);
        ivComputer2 = (ImageView) findViewById(R.id.ivComputer2);

        mvTableComputer1 = (TableView) findViewById(R.id.vTableComputer1);
        mvTableComputer1.setColumnAndRow(5, 4);
        mvTableComputer2 = (TableView) findViewById(R.id.vTableComputer2);
        mvTableComputer2.setColumnAndRow(5, 4);
        mvTablePlayer = (TableView) findViewById(R.id.vTablePlayer);
        mvTablePlayer.setColumnAndRow(10, 2);
        mvTableLandlord = (TableView) findViewById(R.id.vTableLandlord);
        mvTableLandlord.setColumnAndRow(3, 1);
        mvTableResult = (TableViewResult) findViewById(R.id.vTableResult);

        mllButton = (LinearLayout) findViewById(R.id.llButton);
        mllButton.setVisibility(View.INVISIBLE);
        mllComputer1 = (LinearLayout) findViewById(R.id.llComputer1);
        mllComputer2 = (LinearLayout) findViewById(R.id.llComputer2);
        mllPlayer = (LinearLayout) findViewById(R.id.llPlayer);
        mllPlayerCards = (LinearLayout) findViewById(R.id.llPlayerCards);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Save();
                break;
            case R.id.btnPass:
                for (int i = 0; i < mllPlayerCards.getChildCount(); i++) {
                    mllPlayerCards.getChildAt(i).setBackgroundColor(Color.GRAY);
                }
                ArrayChooseCards.clear();
                ChooseCards();
                break;
            case R.id.btnHint:
                ArrayChooseCards.clear();
                ChooseUtil chooseUtil = new ChooseUtil(ArrayPlayerCards);
                ArrayChooseCards = chooseUtil.chooseGroup(ArrayNowCards);

                for (int i = 0; i < mllPlayerCards.getChildCount(); i++) {
                    mllPlayerCards.getChildAt(i).setBackgroundColor(Color.GRAY);
                }
                Log.e("ArrayNowCards", ArrayNowCards.size() + "");
                if (ArrayNowCards.size() == 0) {
                    ArrayChooseCards = chooseUtil.chooseGroup();
                } else {
                    ArrayChooseCards = chooseUtil.chooseGroup(ArrayNowCards);
                    for (int i = 0; i < ArrayChooseCards.size(); i++) {
                        for (int j = 0; j < mllPlayerCards.getChildCount(); j++) {
                            if (((TextView) mllPlayerCards.getChildAt(j).findViewById(R.id.tvItemCard)).getText().equals(ArrayChooseCards.get(i))) {
                                mllPlayerCards.getChildAt(i).setBackgroundColor(Color.BLUE);
                                break;
                            }
                        }
                    }
                }
                break;
            case R.id.btnPlay:
                if (ArrayChooseCards.size() == 0) return;
                ArrayChooseCards = CardUtil.SortByWeight(ArrayChooseCards);
                Log.e("getType", CardUtil.getType(ArrayChooseCards));
                if (CardUtil.getType(ArrayChooseCards) != CardUtil.Type_Wrong) {
                    if (ArrayNowCards.size() != 0) {
                        if (CardUtil.getGroupWeight(ArrayChooseCards) <= CardUtil.getGroupWeight(ArrayNowCards)) {
                            return;
                        }
                    }
                    int length = mllPlayerCards.getChildCount();
                    for (int i = length; i > 0; i--) {
                        if (((ColorDrawable) mllPlayerCards.getChildAt(i - 1).getBackground()).getColor() == Color.BLUE) {
                            mllPlayerCards.removeViewAt(i - 1);
                        }
                    }
                    ChooseCards();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 选牌
     **/
    public void ChooseCards() {
        ArrayChooseCards = CardUtil.SortByWeight(ArrayChooseCards);
        mllButton.setVisibility(View.INVISIBLE);
        Intent intent_ChooseCards = new Intent(TransmitFlag.PlayService);
        intent_ChooseCards.putExtra(TransmitFlag.State, TransmitFlag.ChooseCards);
        intent_ChooseCards.putExtra(TransmitFlag.ChooseCards, ArrayChooseCards);
        sendBroadcast(intent_ChooseCards);
        for (String str : ArrayChooseCards) {
            ArrayPlayerCards.remove(str);
        }
        ArrayChooseCards.clear();
    }

    /**
     * 加载玩家手牌
     **/
    private void LoadPlayerCards() {
        PlayerCardsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ColorDrawable) v.getBackground()).getColor() == Color.GRAY) {
                    v.setBackgroundColor(Color.BLUE);
                    ArrayChooseCards.add(((TextView) v.findViewById(R.id.tvItemCard)).getText().toString());
                } else if (((ColorDrawable) v.getBackground()).getColor() == Color.BLUE) {
                    v.setBackgroundColor(Color.GRAY);
                    ArrayChooseCards.remove(((TextView) v.findViewById(R.id.tvItemCard)).getText().toString());
                }
            }
        };

        LayoutInflater layoutInflater = LayoutInflater.from(PlayActivity.this);
        for (String string : ArrayPlayerCards) {
            View view = layoutInflater.inflate(R.layout.item_card, null);
            view.setMinimumWidth(100);
            view.setMinimumHeight(mllPlayerCards.getHeight());
            view.setBackgroundColor(Color.GRAY);
            view.setOnClickListener(PlayerCardsListener);
            TextView textview = (TextView) view.findViewById(R.id.tvItemCard);
            textview.setWidth(view.getMinimumWidth());
            textview.setText(string);
            textview.setGravity(Gravity.CENTER);
            mllPlayerCards.addView(view);
        }
    }

    /**
     * 单回合显示
     **/
    private void NowPlayer(int rest) {
        mllComputer1.setBackgroundColor(Color.LTGRAY);
        mllComputer2.setBackgroundColor(Color.LTGRAY);
        mllPlayer.setBackgroundColor(Color.LTGRAY);
        switch (NowPlayer) {
            case TransmitFlag.Computer1:
                mllComputer1.setBackgroundColor(Color.RED);
                mtvRestComputer1.setText(rest + "");
                break;
            case TransmitFlag.Computer2:
                mllComputer2.setBackgroundColor(Color.RED);
                mtvRestComputer2.setText(rest + "");
                break;
            case TransmitFlag.Player:
                mllPlayer.setBackgroundColor(Color.RED);
                mtvRestPlayer.setText(rest + "");
                break;
            default:
                break;
        }
    }


    /**
     * 保存进度
     **/
    private void Save() {
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
                        Intent intent_Save = new Intent(TransmitFlag.PlayService);
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
    }

    /**
     * 接收器
     **/
    class PlayActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String strState = intent.getStringExtra(TransmitFlag.State);
            Log.e("PlayActivityReceiver", "" + strState);
            switch (strState) {
                case TransmitFlag.PlayerCards:
                    ArrayPlayerCards = intent.getStringArrayListExtra(TransmitFlag.PlayerCards);
                    Landlord = intent.getStringExtra(TransmitFlag.Landlord);
                    mtvLandlord.setText(Landlord);
                    ArrayLandlordCards = intent.getStringArrayListExtra(TransmitFlag.LandlordCards);
                    mvTableLandlord.setContent(ArrayLandlordCards);
                    mvTableLandlord.invalidate();
                    LoadPlayerCards();
                    break;
                case TransmitFlag.NowPlayer:
                    NowPlayer = intent.getStringExtra(TransmitFlag.NowPlayer);
                    int rest1 = intent.getIntExtra(TransmitFlag.RestCards, 0);
                    NowPlayer(rest1);
                    break;
                case TransmitFlag.NowCards:
                    ArrayList<String> arrayList = intent.getStringArrayListExtra(TransmitFlag.NowCards);
                    int rest2 = intent.getIntExtra(TransmitFlag.RestCards, 0);
                    if (arrayList.size() != 0) {
                        ArrayNowCards = arrayList;
                    }
                    switch (NowPlayer) {
                        case TransmitFlag.Computer1:
                            mtvRestComputer1.setText(rest2 + "");
                            mvTableComputer1.setContent(arrayList);
                            mvTableComputer1.invalidate();
                            break;
                        case TransmitFlag.Computer2:
                            mtvRestComputer2.setText(rest2 + "");
                            mvTableComputer2.setContent(arrayList);
                            mvTableComputer2.invalidate();
                            break;
                        case TransmitFlag.Player:
                            mtvRestPlayer.setText(rest2 + "");
                            mvTablePlayer.setContent(arrayList);
                            mvTablePlayer.invalidate();
                            break;
                        default:
                            break;
                    }
                    break;
                case TransmitFlag.ChooseCards:
                    ArrayNowCards = intent.getStringArrayListExtra(TransmitFlag.ChooseCards);
                    mllButton.setVisibility(View.VISIBLE);
                    break;
                case TransmitFlag.TurnEnd:

                    break;
                default:
                    break;
            }
        }
    }
}