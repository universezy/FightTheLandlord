package com.example.administrator.fightthelandlord.entity;

import java.util.ArrayList;

/**
 * 自定义实体
 **/
public class CustomEntity {
    protected ArrayList<String> ArrayCard = new ArrayList<>();
    protected String Name = "";

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName(){
        return this.Name;
    }

    public void addCard(String card) {
        this.ArrayCard.add(card);
    }

    public ArrayList<String> playCard(ArrayList<String> cards) {
        return null;
    }

    public void clearCard() {
        this.ArrayCard.clear();
    }

    public void setArrayCard(ArrayList<String> arrayCard){
        this.ArrayCard = arrayCard;
    }

    public ArrayList<String> getArrayCard (){
        return this.ArrayCard;
    }

}
