package com.example.administrator.fightthelandlord.tool;


import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 卡牌工具类
 **/
public class CardUtil {
    public static final String Type_Single = "Type_Single";
    public static final String Type_Pair = "Type_Pair";
    public static final String Type_Three = "Type_Three";
    public static final String Type_ThreeWithOne = "Type_ThreeWithOne";
    public static final String Type_FourWithTwo = "Type_FourWithTwo";
    public static final String Type_Straight = "Type_Straight";
    public static final String Type_ContinuousPairs = "Type_ContinuousPairs";
    public static final String Type_Airplane = "Type_Airplane";
    public static final String Type_Boom = "Type_Boom";
    public static final String Type_JokerBoom = "Type_JokerBoom";
    public static final String Type_Wrong = "Type_Wrong";

    /**
     * 获取单张牌的权值
     **/
    public static int getWeight(String str) {
        switch (str) {
            case "3":
                return 0;
            case "4":
                return 1;
            case "5":
                return 2;
            case "6":
                return 3;
            case "7":
                return 4;
            case "8":
                return 5;
            case "9":
                return 6;
            case "10":
                return 7;
            case "J":
                return 8;
            case "Q":
                return 9;
            case "K":
                return 10;
            case "A":
                return 11;
            case "2":
                return 13;
            case "joker":
                return 15;
            case "Joker":
                return 16;
            default:
                return 0;
        }
    }

    /**
     * 获取组合牌的类型
     **/
    public static String getType(ArrayList<String> arrayListRes) {
        if (arrayListRes.size() == 1) {                                //Type_Single
            return Type_Single;
        } else if (arrayListRes.size() == 2) {                         //Type_Pair
            if (arrayListRes.get(0).equals(arrayListRes.get(1))) {
                return Type_Pair;
            }
        } else if (arrayListRes.size() == 3) {                         //Type_Three
            if (arrayListRes.get(0).equals(arrayListRes.get(1)) && arrayListRes.get(2).equals(arrayListRes.get(1))) {
                return Type_Three;
            }
        } else if (arrayListRes.size() == 4) {                           //Type_Boom
            String Res = "";
            for (String res : arrayListRes) {
                Res += res;
            }
            Log.e("Res", Res);
            Pattern pattern = Pattern.compile("[.*]{4}");
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_Boom;
            }
        } else if (arrayListRes.size() == 4) {                         //Type_ThreeWithOne
            String Res = "";
            for (String res : arrayListRes) {
                Res += res;
            }
            Log.e("Res", Res);
            Pattern pattern = Pattern.compile("[.*]?[.*]{3}[.*]?");
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_ThreeWithOne;
            }
        } else if (arrayListRes.size() == 6) {                         //Type_FourWithTwo
            String Res = "";
            for (String res : arrayListRes) {
                Log.e("res", res);
                Res += res;
                Log.e("Res += res", Res);
            }
            Log.e("Res", Res);
            Pattern pattern = Pattern.compile("[.*]{0,2}[.*]{4}[.*]{0,2}");
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                return Type_FourWithTwo;
            }
        } else if (arrayListRes.size() > 4) {                           //Type_Straight
            int count_straight = 0;
            for (int i = 0; i < arrayListRes.size() - 1; i++) {
                if (NextSequenceCard(arrayListRes.get(i)).equals(arrayListRes.get(i + 1))) {
                    count_straight++;
                }
            }
            if (count_straight == arrayListRes.size() - 1) {
                return Type_Straight;
            }
        } else if (arrayListRes.size() >= 6 && arrayListRes.size() % 2 == 0) {    //Type_ContinuousPairs
            int count_pair = arrayListRes.size() / 2;
            String Res = "";
            for (String res : arrayListRes) {
                Log.e("res", res);
                Res += res;
                Log.e("Res += res", Res);
            }
            Log.e("Res", Res);
            String RegEx = "";
            for (int i = 0; i < count_pair; i++) {
                RegEx += "[.*]{2}";
            }
            Pattern pattern = Pattern.compile(RegEx);
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                int count_straight = 0;
                for (int i = 0; i < count_pair - 1; i++) {
                    if (NextSequenceCard(arrayListRes.get(i * 2)).equals(arrayListRes.get(i * 2 + 2)))
                        count_straight++;
                }
                if (count_straight == count_pair) {
                    return Type_ContinuousPairs;
                }
            }
        } else if (arrayListRes.size() >= 8 && arrayListRes.size() % 4 == 0) {    //Type_Airplane
            int count_three = arrayListRes.size() / 4;
            String Res = "";
            for (String res : arrayListRes) {
                Log.e("res", res);
                Res += res;
                Log.e("Res += res", Res);
            }
            Log.e("Res", Res);
            String RegEx = ".*";
            for (int i = 0; i < count_three; i++) {
                RegEx += "[.*]{3}";
            }
            RegEx += ".*";
            Pattern pattern = Pattern.compile(RegEx);
            Matcher matcher = pattern.matcher(Res);
            if (matcher.matches()) {
                int count_straight = 0;
                for (int i = 0; i < count_three - 1; i++) {
                    if (NextSequenceCard(arrayListRes.get(i * 4 + 1)).equals(arrayListRes.get(i * 4 + 5)))
                        count_straight++;
                }
                if (count_straight == count_three) {
                    return Type_Airplane;
                }
            }
        } else if (arrayListRes.size() == 2) {                            //Type_JokerBoom
            if (getWeight(arrayListRes.get(0)) == 15 && getWeight(arrayListRes.get(1)) == 16) {
                return Type_JokerBoom;
            }
        }
        return Type_Wrong;
    }

    /**
     * 获取组合牌的权值
     **/
    public static int getGroupWeight(ArrayList<String> str) {
        switch (getType(str)) {
            case Type_Single:
                return getWeight(str.get(0));
            case Type_Pair:
                return getWeight(str.get(0));
            case Type_Three:
                return getWeight(str.get(0));
            case Type_ThreeWithOne:
                return getWeight(str.get(1));
            case Type_FourWithTwo:
                return getWeight(str.get(2));
            case Type_Straight:
                return getWeight(str.get(0));
            case Type_ContinuousPairs:
                return getWeight(str.get(0));
            case Type_Airplane:
                return getWeight(str.get(str.size() / 4));
            case Type_Boom:
                return getWeight(str.get(0));
            case Type_JokerBoom:
                return getWeight(str.get(0));
            case Type_Wrong:
            default:
                return 0;
        }
    }

    /**
     * 获取顺序下一张牌
     **/
    public static String NextSequenceCard(String card) {
        switch (card) {
            case "3":
                return "4";
            case "4":
                return "5";
            case "5":
                return "6";
            case "6":
                return "7";
            case "7":
                return "8";
            case "8":
                return "9";
            case "9":
                return "10";
            case "10":
                return "J";
            case "J":
                return "Q";
            case "Q":
                return "K";
            case "K":
                return "A";
            case "A":
            case "2":
            case "joker":
            case "Joker":
            default:
                return "";
        }
    }

    /**
     * 按权值排序
     **/
    public static ArrayList<String> SortByWeight(ArrayList<String> arrayList) {
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
}
