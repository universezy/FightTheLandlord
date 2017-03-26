package com.example.administrator.fightthelandlord.entity;

import android.util.Log;

import com.example.administrator.fightthelandlord.tool.ChooseUtil;

import java.util.ArrayList;

/**
 * 电脑实体
 **/
public class ComputerEntity extends CustomEntity {

    public ComputerEntity(String Name) {
        super.SetName(Name);
    }

    @Override
    public ArrayList<String> PlayCard(ArrayList<String> NowCards) {
        ArrayList<String> chooseCards ;
        ChooseUtil chooseUtil = new ChooseUtil(ArrayCard);
        //选牌逻辑
        if (NowCards.size() == 0) {
            chooseCards = chooseUtil.chooseGroup();
        } else {
            chooseCards = chooseUtil.chooseGroup( NowCards);
        }
        for (String chooseCard : chooseCards) {
            Log.e("chooseCards",chooseCard);
            int index = ArrayCard.indexOf(chooseCard);
            if (index >= 0)
                ArrayCard.remove(index);
        }
        return chooseCards;
    }
}
