package com.example.administrator.fightthelandlord.entity;


import java.util.ArrayList;

/**
 * 玩家实体
 **/
public class PlayerEntity extends CustomEntity {

    public PlayerEntity(String Name) {
        super.setName(Name);
    }

    @Override
    public ArrayList<String> playCard(ArrayList<String> cards) {
        for (String chooseCard : cards) {
            int index = ArrayCard.indexOf(chooseCard);
            ArrayCard.remove(index);
        }
        return null;
    }
}
