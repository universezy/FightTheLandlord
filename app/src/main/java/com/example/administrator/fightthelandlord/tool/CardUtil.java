package com.example.administrator.fightthelandlord.tool;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardUtil {
    public static final String Type_Single = "Type_Single";
    public static final String Type_Pair = "Type_Pair";
    public static final String Type_Three = "Type_Three";
    public static final String Type_ThreeWithOne = "Type_ThreeWithOne";
    public static final String Type_FourWithTwo = "Type_FourWithTwo";
    public static final String Type_Straight = "Type_Straight";
    public static final String Type_ContinuousPairs = "Type_ContinuousPairs";
    public static final String Type_Airplane = "Type_Airplane";
    public static final String Type_Bomb = "Type_Bomb";
    public static final String Type_JokerBomb = "Type_JokerBomb";
    public static final String Type_Wrong = "Type_Wrong";

    CardUtil() {

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
                weight = 13;
                break;
            case "joker":
                weight = 15;
                break;
            case "Joker":
                weight = 16;
                break;
            default:
                break;
        }
        return weight;
    }

    public static String getType(ArrayList<String> arrayList) {
        if (arrayList.size() == 1) {                                //Type_Single
            return Type_Single;
        } else if (arrayList.size() == 2) {                         //Type_Pair
            if (arrayList.get(0).equals(arrayList.get(1))) {
                return Type_Pair;
            }
        } else if (arrayList.size() == 3) {                         //Type_Three
            if (arrayList.get(0).equals(arrayList.get(1)) && arrayList.get(2).equals(arrayList.get(1))) {
                return Type_Three;
            }
        } else if (arrayList.size() == 4) {                         //Type_ThreeWithOne
            String Res = "";
            for (String res : arrayList) {
                Res += res;
            }
            Pattern pattern = Pattern.compile("[.*]?[.*]{3}[.*]?");
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_ThreeWithOne;
            }
        } else if (arrayList.size() == 6) {                         //Type_FourWithTwo
            String Res = "";
            for (String res : arrayList) {
                Res += res;
            }
            Pattern pattern = Pattern.compile("[.*]{0,2}[.*]{4}[.*]{0,2}");
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_FourWithTwo;
            }
        } else if (arrayList.size() > 4) {                           //Type_Straight
            int count_straight = 0;
            for (int i = 0; i < arrayList.size() - 1; i++) {
                if (CardUtil.getWeight(arrayList.get(i + 1)) - CardUtil.getWeight(arrayList.get(i)) == 1)
                    count_straight++;
            }
            if (count_straight == arrayList.size() - 1) {
                return Type_Straight;
            }
        } else if (arrayList.size() > 4 && arrayList.size() % 2 == 0) {    //Type_ContinuousPairs
            int count_pair = arrayList.size() / 2;
            String Res = "";
            for (String res : arrayList) {
                Res += res;
            }
            String RegEx = "";
            for (int i = 0; i < count_pair; i++) {
                RegEx += "[.*]{2}";
            }
            Pattern pattern = Pattern.compile(RegEx);
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                int count_straight = 0;
                for (int i = 0; i < count_straight - 1; i++) {
                    if (CardUtil.getWeight(arrayList.get(i * 2 + 2)) - CardUtil.getWeight(arrayList.get(i * 2)) == 1)
                        count_straight++;
                }
                if (count_straight == arrayList.size() - 1) {
                    return Type_ContinuousPairs;
                }
            }
        } else if (arrayList.size() > 7 && arrayList.size() % 4 == 0) {    //Type_Airplane
            int count_pair = arrayList.size() / 4;
            String Res = "";
            for (String res : arrayList) {
                Res += res;
            }
            String RegEx = ".*";
            for (int i = 0; i < count_pair; i++) {
                RegEx += "[.*]{3}";
            }
            RegEx += ".*";
            Pattern pattern = Pattern.compile(RegEx);
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_Airplane;
            }
        } else if (arrayList.size() == 4) {                           //Type_Bomb
            String Res = "";
            for (String res : arrayList) {
                Res += res;
            }
            String RegEx = "";
            for (int i = 0; i < 4; i++) {
                RegEx += "[.*]{4}";
            }
            Pattern pattern = Pattern.compile(RegEx);
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_Bomb;
            }
        } else if (arrayList.size() == 2) {                            //Type_JokerBomb
            if (getWeight(arrayList.get(0)) == 15 && getWeight(arrayList.get(1)) == 16) {
                return Type_JokerBomb;
            }
        }
        return Type_Wrong;
    }

    public static int getTypeWeight(ArrayList<String> str) {
        int weight = 0;
        switch (getType(str)) {
            case Type_Single:
                weight = getWeight(str.get(0));
                break;
            case Type_Pair:
                weight = getWeight(str.get(0));
                break;
            case Type_Three:
                weight = getWeight(str.get(0));
                break;
            case Type_ThreeWithOne:
                weight = getWeight(str.get(1));
                break;
            case Type_FourWithTwo:
                weight = getWeight(str.get(2));
                break;
            case Type_Straight:
                weight = getWeight(str.get(0));
                break;
            case Type_ContinuousPairs:
                weight = getWeight(str.get(0));
                break;
            case Type_Airplane:
                weight = getWeight(str.get(str.size() / 4));
                break;
            case Type_Bomb:
                weight = getWeight(str.get(0));
                break;
            case Type_JokerBomb:
                weight = getWeight(str.get(0));
                break;
            default:
                break;
        }
        return weight;
    }
}
