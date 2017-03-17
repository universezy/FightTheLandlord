package com.example.administrator.fightthelandlord.tool;


public class CardUtil {
    CardUtil(){

    }

    public static int getWeight(String str) {
        int weight = 0;
        switch (str) {
            case "3":
                weight = 0;
                break;
            case "4":
                weight = 1;
                break;
            case "5":
                weight = 2;
                break;
            case "6":
                weight = 3;
                break;
            case "7":
                weight = 4;
                break;
            case "8":
                weight = 5;
                break;
            case "9":
                weight = 6;
                break;
            case "10":
                weight = 7;
                break;
            case "J":
                weight = 8;
                break;
            case "Q":
                weight = 9;
                break;
            case "K":
                weight = 10;
                break;
            case "A":
                weight = 11;
                break;
            case "2":
                weight = 12;
                break;
            case "joker":
                weight = 13;
                break;
            case "Joker":
                weight = 14;
                break;
            default:
                break;
        }
        return weight;
    }


}
