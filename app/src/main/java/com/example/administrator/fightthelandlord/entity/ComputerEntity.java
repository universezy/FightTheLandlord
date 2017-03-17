package com.example.administrator.fightthelandlord.entity;

import java.util.ArrayList;

/**
 * 电脑实体
 **/
public class ComputerEntity extends CustomEntity {

    public ComputerEntity(String Name){
        super.SetName(Name);
    }

    @Override
    public ArrayList<String> PlayCard(ArrayList<String> cards) {
        ArrayList<String> chooseCards = new ArrayList<>();
        //TODO


        for (String chooseCard : chooseCards) {
            int index = ArrayCard.indexOf(chooseCard);
            chooseCards.add(chooseCard);
            ArrayCard.remove(index);
        }
        return chooseCards;
    }
}
