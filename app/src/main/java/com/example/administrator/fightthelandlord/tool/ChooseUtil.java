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
                    EmptyArrayList = getSingle(ArrayPlayerCards.get(i));
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
                    EmptyArrayList = getPair(ArrayPlayerCards.get(i));
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
                    EmptyArrayList = getThreeWithOne(ArrayPlayerCards.get(i));
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
                    EmptyArrayList = getStraight(ArrayPlayerCards.get(i));
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
                    EmptyArrayList = getContinuousPairs(ArrayPlayerCards.get(i));
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
                    EmptyArrayList = getAirplane(ArrayPlayerCards.get(i));
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
                    EmptyArrayList = getBoom(ArrayPlayerCards.get(i));
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
            if ((EmptyArrayList = getJokerBoom(ArrayPlayerCards.get(0))).size() != 0) {
                EmptyArrayList.clear();
                if ((EmptyArrayList = getAirplane(ArrayPlayerCards.get(0))).size() != 0 &&
                        ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getContinuousPairs(ArrayPlayerCards.get(0))).size() != 0 &&
                        ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getStraight(ArrayPlayerCards.get(0))).size() != 0 &&
                        ArrayPlayerCards.indexOf("joker") == EmptyArrayList.size()) {
                } else if ((EmptyArrayList = getThreeWithOne(ArrayPlayerCards.get(0))).size() != 0 &&
                        ArrayPlayerCards.indexOf("joker") == 4) {
                } else if ((EmptyArrayList = getBoom(ArrayPlayerCards.get(0))).size() != 0 &&
                        ArrayPlayerCards.indexOf("joker") == 4) {
                } else if ((EmptyArrayList = getPair(ArrayPlayerCards.get(0))).size() != 0 &&
                        ArrayPlayerCards.indexOf("joker") == 2) {
                } else if (!(getSingle(ArrayPlayerCards.get(0))).equals("") &&
                        ArrayPlayerCards.indexOf("joker") == 1) {
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
            if ((EmptyArrayList = getAirplane(ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getContinuousPairs(ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getStraight(ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getPair(ArrayPlayerCards.get(i))).size() != 0) {
                break;
            } else if ((EmptyArrayList = getSingle(ArrayPlayerCards.get(i))).size() != 0) {
                break;
            }

        }
        if (EmptyArrayList.size() == 0)
            EmptyArrayList.add(ArrayPlayerCards.get(0));
        EmptyArrayList = CardUtil.SortByWeight(EmptyArrayList);
        return EmptyArrayList;
    }

    /**
     * getSingle
     **/
    public ArrayList<String> getSingle(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getSingle");
        ArrayList<String> temp;
        if (getStraight(firstCard).size() == 0 &&
                getThree(firstCard).size() == 0 &&
                getContinuousPairs(firstCard).size() == 0 &&
                getBoom(firstCard).size() == 0) {
            if ((temp = getPair(firstCard)).size() != 0) {
                EmptyArrayList.add(temp.get(0));
            }
        }
        return EmptyArrayList;
    }


    /**
     * getPair
     **/
    public ArrayList<String> getPair(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getPair");
        if (getThree(firstCard).size() == 0 &&
                getBoom(firstCard).size() == 0) {
            int indexEnd = ArrayPlayerCards.indexOf(firstCard) + 1;
            if (indexEnd < ArrayPlayerCards.size()) {
                if (ArrayPlayerCards.get(indexEnd).equals(firstCard)) {
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(firstCard);
                }
            }
        }
        return EmptyArrayList;
    }

    /**
     * getStraight
     **/
    public ArrayList<String> getStraight(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getStraight");
        if (getAirplane(firstCard).size() == 0 &&
                getContinuousPairs(firstCard).size() == 0 &&
                getBoom(firstCard).size() == 0) {
            EmptyArrayList.add(firstCard);
            int index = ArrayPlayerCards.indexOf(CardUtil.NextSequenceCard(firstCard));
            if (index > 0) {
                EmptyArrayList = getStraight(ArrayPlayerCards.get(index));
            }
        }
        if (EmptyArrayList.size() < 5) {
            EmptyArrayList.clear();
        }
        return EmptyArrayList;
    }

    /**
     * getJokerBoom
     **/
    public ArrayList<String> getThree(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getThree");
        if (getBoom(firstCard).size() == 0) {
            int indexEnd = ArrayPlayerCards.indexOf(firstCard) + 2;
            if (indexEnd < ArrayPlayerCards.size()) {
                if (ArrayPlayerCards.get(indexEnd).equals(firstCard)) {
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(firstCard);
                }
            }
        }
        return EmptyArrayList;
    }

    /**
     * getThreeWithOne
     **/
    public ArrayList<String> getThreeWithOne(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        ArrayList<String> temp;
        Log.e("ChooseUtil", "getThreeWithOne");
        if (getThree(firstCard).size() != 0) {
            for (int i = 0; i < ArrayPlayerCards.size(); i++) {
                if ((temp = getSingle(ArrayPlayerCards.get(i))).size() != 0) {
                    EmptyArrayList = getThree(firstCard);
                    EmptyArrayList.add(temp.get(0));
                }
            }
        }
        return EmptyArrayList;
    }

    /**
     * getContinuousPairs
     **/
    public ArrayList<String> getContinuousPairs(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getContinuousPairs");
        while (getPair(firstCard).size() != 0) {
            EmptyArrayList.add(firstCard);
            EmptyArrayList.add(firstCard);
            if (ArrayPlayerCards.indexOf(firstCard) + 2 < ArrayPlayerCards.size()) {
                firstCard = CardUtil.NextSequenceCard(firstCard);
            } else {
                break;
            }
        }
        if (EmptyArrayList.size() / 2 < 3) {
            EmptyArrayList.clear();
        }
        return EmptyArrayList;
    }

    /**
     * getAirplane
     **/
    public ArrayList<String> getAirplane(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        ArrayList<String> temp;
        Log.e("ChooseUtil", "getAirplane");
        int indexSingle = 0;
        while (getThree(firstCard).size() != 0) {
            for (int i = indexSingle; i < ArrayPlayerCards.size(); i++) {
                if ((temp = getSingle(ArrayPlayerCards.get(i))).size() != 0) {
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(firstCard);
                    EmptyArrayList.add(temp.get(0));
                    indexSingle = i + 1;
                    break;
                }
            }
            firstCard = CardUtil.NextSequenceCard(firstCard);
        }
        if (EmptyArrayList.size() / 4 < 2) {
            EmptyArrayList.clear();
        }
        return EmptyArrayList;
    }

    /**
     * getBoom
     **/
    public ArrayList<String> getBoom(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getBoom");
        int indexEnd = ArrayPlayerCards.indexOf(firstCard) + 3;
        if (indexEnd < ArrayPlayerCards.size()) {
            if (ArrayPlayerCards.get(indexEnd).equals(firstCard)) {
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
                EmptyArrayList.add(firstCard);
            }
        }
        return EmptyArrayList;
    }

    /**
     * getJokerBoom
     **/
    public ArrayList<String> getJokerBoom(String firstCard) {
        ArrayList<String> EmptyArrayList = new ArrayList<>();
        Log.e("ChooseUtil", "getJokerBoom");
        if (ArrayPlayerCards.indexOf("joker") > 0 && ArrayPlayerCards.indexOf("Joker") > 0) {
            EmptyArrayList.add("joker");
            EmptyArrayList.add("Joker");
        }
        return EmptyArrayList;
    }
}
