package com.example.administrator.fightthelandlord.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.example.administrator.fightthelandlord.entity.ComputerEntity;
import com.example.administrator.fightthelandlord.entity.CustomEntity;
import com.example.administrator.fightthelandlord.entity.PlayerEntity;
import com.example.administrator.fightthelandlord.tool.CardUtil;
import com.example.administrator.fightthelandlord.tool.TransmitFlag;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * 游戏服务
 **/
public class PlayService extends Service {
    //接收器
    protected PlayServiceReceiver playServiceReceiver = new PlayServiceReceiver();
    //处理器
    private Handler handleService = new Handler();
    //启动类型
    private String StartType;
    //用户数据
    private String UserID = "";
    //回合信息
    private String Landlord = "", NowPlayer = "";
    //剩余卡牌
    private ArrayList<String> RestCardComputer1 = new ArrayList<>();
    private ArrayList<String> RestCardComputer2 = new ArrayList<>();
    private ArrayList<String> RestCardPlayer = new ArrayList<>();
    //回合结束标识
    private boolean TurnEnd = true;
    //无接牌回合数
    private int Count = 0;
    //底牌
    private ArrayList<String> ArrayCardBanker = new ArrayList<>();
    //场牌
    private ArrayList<String> ArrayNowCards = new ArrayList<>();
    //电脑实体
    private ComputerEntity computer1Entity, computer2Entity;
    //玩家实体
    private PlayerEntity playerEntity;

    @Override
    public void onCreate() {
        //注册接收器
        IntentFilter intentFilter = new IntentFilter(TransmitFlag.PlayActivity);
        registerReceiver(playServiceReceiver, intentFilter);

        InitPlayer();
        InitCard();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        UserID = intent.getStringExtra(TransmitFlag.UserID);
        StartType = intent.getStringExtra(TransmitFlag.StartType);
        switch (StartType) {
            case TransmitFlag.NewGame:
                Play();
                break;
            case TransmitFlag.ContinueGame:
                InitUserProgress();
                Play(NowPlayer, RestCardComputer1, RestCardComputer2, RestCardPlayer);
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void onDestroy() {
        TurnEnd = true;
        unregisterReceiver(playServiceReceiver);
        super.onDestroy();
    }

    /**
     * 继续游戏时读取上局信息
     **/
    private void InitUserProgress() {
        File UserFile = new File(getFilesDir(), UserID + "_user_progress.xml");
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
                        } else if (tagName.equals("nowplayer")) {
                            NowPlayer = xmlPullParser.nextText();
                        } else if (tagName.equals("computer1")) {
                            RestCardComputer1.clear();
                            for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                RestCardComputer1.add(xmlPullParser.getAttributeValue(i));
                            }
                        } else if (tagName.equals("computer2")) {
                            RestCardComputer2.clear();
                            for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                RestCardComputer2.add(xmlPullParser.getAttributeValue(i));
                            }
                        } else if (tagName.equals("player")) {
                            RestCardPlayer.clear();
                            for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                RestCardPlayer.add(xmlPullParser.getAttributeValue(i));
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

    /**
     * 初始化玩家
     **/
    private void InitPlayer() {
        computer1Entity = new ComputerEntity(TransmitFlag.Computer1);
        computer2Entity = new ComputerEntity(TransmitFlag.Computer2);
        playerEntity = new PlayerEntity(TransmitFlag.Player);
    }

    /**
     * 初始化卡牌
     **/
    private void InitCard() {
        for (int i = 0; i < 4; i++) {
            ArrayCardBanker.add("A");
            ArrayCardBanker.add("J");
            ArrayCardBanker.add("Q");
            ArrayCardBanker.add("K");
        }
        for (int i = 2; i < 11; i++) {
            ArrayCardBanker.add("" + i);
            ArrayCardBanker.add("" + i);
            ArrayCardBanker.add("" + i);
            ArrayCardBanker.add("" + i);
        }
        ArrayCardBanker.add("joker");
        ArrayCardBanker.add("Joker");
    }

    /**
     * 初始化地主
     **/
    private void InitLandlord() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                Landlord = TransmitFlag.Computer1;
                break;
            case 1:
                Landlord = TransmitFlag.Computer2;
                break;
            case 2:
                Landlord = TransmitFlag.Player;
                break;
            default:
                break;
        }
    }

    /**
     * 洗牌
     **/
    private void ShuffleCard() {
        Random random = new Random();
        for (int i = 0; i < 54; i++) {
            int tempIndex = random.nextInt(54);
            String tempCard = ArrayCardBanker.get(i);
            ArrayCardBanker.set(i, ArrayCardBanker.get(tempIndex));
            ArrayCardBanker.set(tempIndex, tempCard);
        }

        computer1Entity.ClearCard();
        computer2Entity.ClearCard();
        playerEntity.ClearCard();
    }

    /**
     * 随机发牌
     **/
    private void DistributeCard() {
        for (int i = 0; i < 17; i++) {
            computer1Entity.AddCard(ArrayCardBanker.get(i * 3));
            computer2Entity.AddCard(ArrayCardBanker.get(i * 3 + 1));
            playerEntity.AddCard(ArrayCardBanker.get(i * 3 + 2));
        }
        if (computer1Entity.getName().equals(Landlord)) {
            computer1Entity.AddCard(ArrayCardBanker.get(51));
            computer1Entity.AddCard(ArrayCardBanker.get(52));
            computer1Entity.AddCard(ArrayCardBanker.get(53));
        } else if (computer2Entity.getName().equals(Landlord)) {
            computer2Entity.AddCard(ArrayCardBanker.get(51));
            computer2Entity.AddCard(ArrayCardBanker.get(52));
            computer2Entity.AddCard(ArrayCardBanker.get(53));
        } else if (playerEntity.getName().equals(Landlord)) {
            playerEntity.AddCard(ArrayCardBanker.get(51));
            playerEntity.AddCard(ArrayCardBanker.get(52));
            playerEntity.AddCard(ArrayCardBanker.get(53));
        }
        computer1Entity.setArrayCard(SortByWeight(computer1Entity.getArrayCard()));
        computer2Entity.setArrayCard(SortByWeight(computer2Entity.getArrayCard()));
        playerEntity.setArrayCard(SortByWeight(playerEntity.getArrayCard()));

        Intent intent_PlayerCards = new Intent(TransmitFlag.PlayService);
        intent_PlayerCards.putExtra(TransmitFlag.State, TransmitFlag.PlayerCards);
        intent_PlayerCards.putExtra(TransmitFlag.PlayerCards, playerEntity.getArrayCard());
        sendBroadcast(intent_PlayerCards);
    }

    /**
     * 按游戏存档发牌
     **/
    private void DistributeCard(ArrayList<String> ArrayCardComputer1, ArrayList<String> ArrayCardComputer2, ArrayList<String> ArrayCardPlayer3) {
        computer1Entity.setArrayCard(ArrayCardComputer1);
        computer2Entity.setArrayCard(ArrayCardComputer2);
        playerEntity.setArrayCard(ArrayCardPlayer3);

        Intent intent_PlayerCards = new Intent(TransmitFlag.PlayService);
        intent_PlayerCards.putExtra(TransmitFlag.State, TransmitFlag.PlayerCards);
        intent_PlayerCards.putExtra(TransmitFlag.PlayerCards, playerEntity.getArrayCard());
        sendBroadcast(intent_PlayerCards);
    }

    /**
     * 循环进行游戏
     **/
    private void CyclicFight(CustomEntity customEntity1, CustomEntity customEntity2, CustomEntity customEntity3) {
        final CustomEntity Entity1 = customEntity1;
        final CustomEntity Entity2 = customEntity2;
        final CustomEntity Entity3 = customEntity3;
        if (!TurnEnd) {
            Fight(customEntity1);
            handleService.postDelayed(new Runnable() {
                @Override
                public void run() {
                    CyclicFight(Entity2, Entity3, Entity1);
                }
            }, 1000);
        }
    }

    /**
     * 单个回合
     **/
    private void Fight(CustomEntity customEntity) {
        Log.e("Fight", "Fight----------------------");
        NowPlayer = customEntity.getName();
        if (Count == 2) {
            ArrayNowCards.clear();
            Count = 0;
        }
        Log.e("ArrayNowCards", ArrayNowCards.size() == 0 ? "null" : ArrayNowCards.get(0));
        Log.e("NowPlayer", NowPlayer);

        ArrayList<String> arrayList = customEntity.PlayCard(ArrayNowCards);
        if (arrayList.size() != 0) {
            ArrayNowCards = arrayList;
            Count = 0;
            Log.e("ChooseCards", ArrayNowCards.get(0));
        } else {
            Count++;
        }

        Intent intentNowCards = new Intent(TransmitFlag.PlayService);
        intentNowCards.putExtra(TransmitFlag.State, TransmitFlag.NowTurn);
        intentNowCards.putExtra(TransmitFlag.NowCards, ArrayNowCards);
        // intentNowCards.putExtra(TransmitFlag.NowPlayer, NowPlayer);
        sendBroadcast(intentNowCards);

        if (customEntity.getArrayCard().size() == 0) {
            TurnEnd = true;
            ShowResult();
            UpdateUserDate();
            Intent intentTurnEnd = new Intent(TransmitFlag.PlayService);
            intentTurnEnd.putExtra(TransmitFlag.State, TransmitFlag.TurnEnd);
            intentTurnEnd.putExtra(TransmitFlag.Victor, NowPlayer);
            sendBroadcast(intentTurnEnd);
        }
    }

    /**
     * 按权值排序
     **/
    private ArrayList<String> SortByWeight(ArrayList<String> arrayList) {
        ArrayList<String> ArraySort = arrayList;
        boolean hasChanged = false;
        for (int i = 0; i < arrayList.size() && !hasChanged; i++) {
            hasChanged = true;
            for (int j = arrayList.size() - 1; j > i; j--) {
                if (CardUtil.getWeight(arrayList.get(j)) < CardUtil.getWeight(arrayList.get(j - 1))) {
                    String temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j - 1));
                    arrayList.set(j - 1, temp);
                    hasChanged = false;
                }
            }
        }
        return ArraySort;
    }

    /**
     * 新游戏
     **/
    private void Play() {
        InitLandlord();
        ShuffleCard();
        DistributeCard();
        TurnEnd = false;
        switch (Landlord) {
            case TransmitFlag.Computer1:
                CyclicFight(computer1Entity, computer2Entity, playerEntity);
                break;
            case TransmitFlag.Computer2:
                CyclicFight(computer2Entity, playerEntity, computer1Entity);
                break;
            case TransmitFlag.Player:
                CyclicFight(playerEntity, computer1Entity, computer2Entity);
                break;
            default:
                break;
        }
    }

    /**
     * 继续游戏
     **/
    private void Play(String NowPlayer, ArrayList<String> ArrayCardComputer1, ArrayList<String> ArrayCardComputer2, ArrayList<String> ArrayCardPlayer) {
        DistributeCard(ArrayCardComputer1, ArrayCardComputer2, ArrayCardPlayer);
        TurnEnd = false;
        switch (NowPlayer) {
            case TransmitFlag.Computer1:
                CyclicFight(computer1Entity, computer2Entity, playerEntity);
                break;
            case TransmitFlag.Computer2:
                CyclicFight(computer2Entity, playerEntity, computer1Entity);
                break;
            case TransmitFlag.Player:
                CyclicFight(playerEntity, computer1Entity, computer2Entity);
            default:
                break;
        }
    }

    /**
     * 显示结果
     **/
    private void ShowResult() {
        Log.e("TurnEnd", "TurnEnd");
        Log.e("Victor", NowPlayer);
        Log.e("computer1 rest cards ", computer1Entity.getArrayCard().size() + "");
        Log.e("computer2 rest cards", computer2Entity.getArrayCard().size() + "");
        Log.e("player rest cards", playerEntity.getArrayCard().size() + "");
        Toast.makeText(getApplicationContext(), NowPlayer + " win!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 更新用户数据
     **/
    private void UpdateUserDate() {
        Intent intent_Update = new Intent(TransmitFlag.PlayService);
        intent_Update.putExtra(TransmitFlag.State, TransmitFlag.UpdateUserData);
        if (NowPlayer.equals(playerEntity.getName())) {
            intent_Update.putExtra("Win", true);
        } else if (NowPlayer.equals(Landlord)) {
            intent_Update.putExtra("Win", false);
        } else {
            intent_Update.putExtra("Win", true);
        }
        sendBroadcast(intent_Update);
    }

    /**
     * 保存进度
     **/
    private boolean Save(String userID, String Landlord, String NowPlayer, ArrayList<String> ArrayCardComputer1, ArrayList<String> ArrayCardComputer2, ArrayList<String> ArrayCardPlayer) {
        try {
            File UserFile = new File(getFilesDir(), userID + "_user_progress.xml");
            //获取XmlSerializer类的实例  通过xml这个工具类去获取
            XmlSerializer xmlSerializer = Xml.newSerializer();
            //设置XmlSerializer序列化参数
            FileOutputStream fos = new FileOutputStream(UserFile);
            xmlSerializer.setOutput(fos, "utf-8");
            //开始写xml文档开头
            xmlSerializer.startDocument("utf-8", true);
            //写xml的根节点
            //用户数据
            xmlSerializer.startTag(null, "landlord");
            xmlSerializer.text(Landlord);
            xmlSerializer.endTag(null, "landlord");

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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 服务绑定类
     **/
    public class ServiceBinder extends Binder {
    }

    /**
     * 接收器
     **/
    class PlayServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String strState = intent.getStringExtra(TransmitFlag.State);
            switch (strState) {
                case TransmitFlag.Save:
                    TurnEnd = true;
                    RestCardComputer1 = computer1Entity.getArrayCard();
                    RestCardComputer2 = computer2Entity.getArrayCard();
                    RestCardPlayer = playerEntity.getArrayCard();
                    if (Save(UserID, Landlord, NowPlayer, RestCardComputer1, RestCardComputer2, RestCardPlayer)) {
                        Toast.makeText(getApplicationContext(), "Save successful!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Save unsuccessful.", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    }
}