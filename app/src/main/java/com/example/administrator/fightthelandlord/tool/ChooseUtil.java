package com.example.administrator.fightthelandlord.tool;

import android.util.Log;

import java.util.ArrayList;

public class ChooseUtil {
    public ArrayList<String> ArrayPlayerCards = new ArrayList<>();

    public ChooseUtil(ArrayList<String> arrayList) {
        this.ArrayPlayerCards = arrayList;
    }

    public ArrayList<String> chooseGroup(ArrayList<String> NowCards) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("getType chooseGroup()", CardUtil.getType(NowCards));
        switch (CardUtil.getType(NowCards)) {
            case CardUtil.Type_Single:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getSingle(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_Pair:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getPair(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_ThreeWithOne:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getThreeWithOne(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_Straight:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getStraight(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_ContinuousPairs:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getContinuousPairs(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_Airplane:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getAirplane(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_Boom:
                for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                    EmptyArrayList = getBoom(EmptyArrayList, ArrayPlayerCards.get(i));
                    if (EmptyArrayList.size() != 0) {
                        if (CardUtil.getGroupWeight(EmptyArrayList) > CardUtil.getGroupWeight(NowCards)) {
                            break;
                        } else
                            EmptyArrayList.clear();
                    }
                }
                break;
            case CardUtil.Type_JokerBoom:
            case CardUtil.Type_Wrong:
            default:
                break;
        }
        if (EmptyArrayList.size() == 0) {
            if ((EmptyArrayList = getJokerBoom(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0) {
                EmptyArrayList.clear();
                if ((EmptyArrayList = getAirplane(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getContinuousPairs(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getStraight(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getPair(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getSingle(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getThreeWithOne(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getBoom(EmptyArrayList, ArrayPlayerCards.get(0))).size() != 0 && ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else
                    EmptyArrayList.clear();
            }
        }
        EmptyArrayList = CardUtil.SortByWeight(EmptyArrayList);
        return EmptyArrayList;
    }

    public ArrayList<String> chooseGroup() {
        Log.e("getType chooseGroup", "null");
        ArrayList<String> EmptyArrayList = new ArrayList<>();

        if (ArrayPlayerCards.size() == 2) {
            if (CardUtil.getType(ArrayPlayerCards).equals(CardUtil.Type_JokerBoom)) {
                EmptyArrayList = ArrayPlayerCards;
            }
        } else if (ArrayPlayerCards.size() == 3) {
            if (CardUtil.getType(ArrayPlayerCards).equals(CardUtil.Type_Three)) {
                EmptyArrayList = ArrayPlayerCards;
            }
        } else if (ArrayPlayerCards.size() == 4) {
            if (CardUtil.getType(ArrayPlayerCards).equals(CardUtil.Type_Boom)) {
                EmptyArrayList = ArrayPlayerCards;
            }
        } else if (ArrayPlayerCards.size() == 6) {
            if (CardUtil.getType(ArrayPlayerCards).equals(CardUtil.Type_FourWithTwo)) {
                EmptyArrayList = ArrayPlayerCards;
            }
        }
        for (int i = 0; i < ArrayPlayerCards.size(); i++) {
            if ((EmptyArrayList = getAirplane(EmptyArrayList, ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getContinuousPairs(EmptyArrayList, ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getStraight(EmptyArrayList, ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getPair(EmptyArrayList, ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getSingle(EmptyArrayList, ArrayPlayerCards.get(i))).size() != 0) {
                break;
            }
        }
        if (EmptyArrayList.size() == 0)
            EmptyArrayList.add(ArrayPlayerCards.get(0));
        EmptyArrayList = CardUtil.SortByWeight(EmptyArrayList);
        return EmptyArrayList;
    }

    public ArrayList<String> getSingle(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getSingle");
        ArrayList<String> temp;
        if (getPair(EmptyArrayList, firstCard).size() == 0 &&
                getStraight(EmptyArrayList, firstCard).size() == 0 &&
                getThree(EmptyArrayList, firstCard).size() == 0 &&
                getContinuousPairs(EmptyArrayList, firstCard).size() == 0 &&
                getAirplane(EmptyArrayList, firstCard).size() == 0 &&
                getBoom(EmptyArrayList, firstCard).size() == 0) {
            if ((temp = getPair(EmptyArrayList, firstCard)).size() != 0) {
                EmptyArrayList.add(temp.get(0));
            }
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getPair(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getPair");
        if (getThree(EmptyArrayList, firstCard).size() == 0 &&
                getContinuousPairs(EmptyArrayList, firstCard).size() == 0 &&
                getAirplane(EmptyArrayList, firstCard).size() == 0 &&
                getBoom(EmptyArrayList, firstCard).size() == 0) {
            int index1 = ArrayPlayerCards.indexOf(firstCard) + 1;
            int index2 = index1 + 1;
            if (index2 < ArrayPlayerCards.size()) {
                if (ArrayPlayerCards.get(index1).equals(firstCard) && !ArrayPlayerCards.get(index2).equals(firstCard)) {
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(firstCard);
                }
                Log.e("pair.size",EmptyArrayList.size()+"");
            }
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getStraight(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getStraight");
        int index = ArrayPlayerCards.indexOf(CardUtil.NextSequenceCard(firstCard));
        if (index > 0) {
            EmptyArrayList.add(firstCard);
            EmptyArrayList = getStraight(EmptyArrayList, ArrayPlayerCards.get(index));
        }
        if (EmptyArrayList.size() < 5) {
            EmptyArrayList.clear();
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getThree(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getThree");
        int index1 = ArrayPlayerCards.indexOf(firstCard) + 1;
        int index2 = index1 + 1;
        if (index2 < ArrayPlayerCards.size()) {
            if (ArrayPlayerCards.get(index1).equals(firstCard) && ArrayPlayerCards.get(index2).equals(firstCard)) {
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
            }
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getThreeWithOne(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getThreeWithOne");
        EmptyArrayList = getThree(EmptyArrayList, firstCard);
        EmptyArrayList.add(getSingle(EmptyArrayList, firstCard).get(0));
        return EmptyArrayList;
    }

    public ArrayList<String> getContinuousPairs(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getContinuousPairs");
        int index1 = ArrayPlayerCards.indexOf(firstCard) + 1;
        if (index1 < ArrayPlayerCards.size()) {
            if (ArrayPlayerCards.get(index1).equals(firstCard)) {
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                int index2 = ArrayPlayerCards.indexOf(CardUtil.NextSequenceCard(firstCard));
                if (index2 > 0) {
                    getContinuousPairs(EmptyArrayList, ArrayPlayerCards.get(index2));
                }
            }
        }
        if (EmptyArrayList.size() / 2 < 3) {
            EmptyArrayList.clear();
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getAirplane(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getAirplane");
        int index1 = ArrayPlayerCards.indexOf(firstCard) + 1;
        int index2 = index1 + 1;
        if (index2 < ArrayPlayerCards.size()) {
            if (ArrayPlayerCards.get(index1).equals(firstCard) && ArrayPlayerCards.get(index2).equals(firstCard)) {
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                int index3 = ArrayPlayerCards.indexOf(CardUtil.NextSequenceCard(firstCard));
                if (index3 > 0) {
                    getContinuousPairs(EmptyArrayList, ArrayPlayerCards.get(index2));
                }
            }
        }
        if (EmptyArrayList.size() / 3 < 1) {
            EmptyArrayList.clear();
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getBoom(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getBoom");
        int index1 = ArrayPlayerCards.indexOf(firstCard) + 1;
        int index2 = index1 + 1;
        int index3 = index2 + 1;
        if (index3 < ArrayPlayerCards.size()) {
            if (ArrayPlayerCards.get(index1).equals(firstCard) && ArrayPlayerCards.get(index2).equals(firstCard) && ArrayPlayerCards.get(index3).equals(firstCard)) {
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
            }
        }
        return EmptyArrayList;
    }

    public ArrayList<String> getJokerBoom(ArrayList<String> EmptyArrayList, String firstCard) {
        Log.e("ChooseUtil", "getJokerBoom");
        if (ArrayPlayerCards.indexOf("joker") > 0 && ArrayPlayerCards.indexOf("Joker") > 0) {
            EmptyArrayList.add("joker");
            EmptyArrayList.add("Joker");
        }
        return EmptyArrayList;
    }
}
