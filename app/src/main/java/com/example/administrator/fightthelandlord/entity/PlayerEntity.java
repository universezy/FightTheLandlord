package com.example.administrator.fightthelandlord.entity;

import com.example.administrator.fightthelandlord.activity.PlayActivity;
import com.example.administrator.fightthelandlord.tool.CardUtil;

import java.util.ArrayList;

/**
 * 玩家实体
 **/
public class PlayerEntity extends CustomEntity {
    private PlayActivity playActivity = PlayActivity.playActivity;

    public PlayerEntity(String Name) {
        super.SetName(Name);
    }

    @Override
    public ArrayList<String> PlayCard(ArrayList<String> cards) {
        ArrayList<String> chooseCards = new ArrayList<>();
        //选牌逻辑


        //选牌逻辑
        if (cards.size() == 0) {
            chooseCards.add(ArrayCard.get(0));
        } else {
            for (String str : ArrayCard) {
                if (CardUtil.getWeight(str) > CardUtil.getWeight(cards.get(0))) {
                    chooseCards.add(str);
                    break;
                }
            }
        }

        for (String chooseCard : chooseCards) {
            int index = ArrayCard.indexOf(chooseCard);
            ArrayCard.remove(index);
        }
        return chooseCards;
    }
}
