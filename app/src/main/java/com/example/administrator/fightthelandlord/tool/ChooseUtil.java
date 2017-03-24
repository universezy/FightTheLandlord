package com.example.administrator.fightthelandlord.tool;

import java.util.ArrayList;

public class ChooseUtil {
    public ArrayList<String> arrayList = new ArrayList<>();

    public ChooseUtil(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public static ArrayList<String> chooseCombine(ArrayList<String> arrayList, ArrayList<String> NowCards) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        switch (CardUtil.getType(arrayList)) {
            case CardUtil.Type_Single:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getSingle(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_Pair:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getPair(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_ThreeWithOne:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getThreeWithOne(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_Straight:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getStraight(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_ContinuousPairs:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getContinuousPairs(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_Airplane:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getAirplane(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_Boom:
                for (int i = 0; i < arrayList.size(); i++) {
                    stringArrayList = getBoom(stringArrayList, arrayList, arrayList.get(i));
                    if (stringArrayList.size() != 0) {
                        if (CardUtil.getTypeWeight(stringArrayList) > CardUtil.getTypeWeight(NowCards))
                            return stringArrayList;
                    }
                }
                break;
            case CardUtil.Type_JokerBoom:
                return stringArrayList;
            default:
                break;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            stringArrayList = getBoom(stringArrayList, arrayList, arrayList.get(i));
            if (stringArrayList.size() != 0) {
                if (NowCards.indexOf("joker") > 0 && NowCards.indexOf("Joker") > 0) {
                    return stringArrayList;
                }
            }
        }
        return stringArrayList;
    }

    public ArrayList<String> chooseCombine() {
        ArrayList<String> stringArrayList = new ArrayList<>();

        if (arrayList.size() == 2) {
            if (CardUtil.getType(arrayList).equals(CardUtil.Type_JokerBoom)) {
                stringArrayList = arrayList;
                return stringArrayList;
            }
        } else if (arrayList.size() == 3) {
            if (CardUtil.getType(arrayList).equals(CardUtil.Type_Three)) {
                stringArrayList = arrayList;
                return stringArrayList;
            }
        } else if (arrayList.size() == 4) {
            if (CardUtil.getType(arrayList).equals(CardUtil.Type_Boom)) {
                stringArrayList = arrayList;
                return stringArrayList;
            }
        } else if (arrayList.size() == 6) {
            if (CardUtil.getType(arrayList).equals(CardUtil.Type_FourWithTwo)) {
                stringArrayList = arrayList;
                return stringArrayList;
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if ((stringArrayList = getAirplane(stringArrayList, arrayList, arrayList.get(i))).size() != 0) {
            } else if ((stringArrayList = getContinuousPairs(stringArrayList, arrayList, arrayList.get(i))).size() != 0) {
                return stringArrayList;
            } else if ((stringArrayList = getStraight(stringArrayList, arrayList, arrayList.get(i))).size() != 0) {
                return stringArrayList;
            } else if ((stringArrayList = getPair(stringArrayList, arrayList, arrayList.get(i))).size() != 0) {
                return stringArrayList;
            } else if ((stringArrayList = getSingle(stringArrayList, arrayList, arrayList.get(i))).size() != 0) {
                return stringArrayList;
            }
        }
        stringArrayList.add(arrayList.get(0));
        return stringArrayList;
    }

    public static ArrayList<String> getSingle(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        if (getPair(stringArrayList, arrayList, firstCard).size() == 0 &&
                getStraight(stringArrayList, arrayList, firstCard).size() == 0 &&
                getThree(stringArrayList, arrayList, firstCard).size() == 0 &&
                getContinuousPairs(stringArrayList, arrayList, firstCard).size() == 0 &&
                getAirplane(stringArrayList, arrayList, firstCard).size() == 0 &&
                getBoom(stringArrayList, arrayList, firstCard).size() == 0) {
            stringArrayList.add(getPair(stringArrayList, arrayList, firstCard).get(0));
        }
        return stringArrayList;
    }

    public static ArrayList<String> getPair(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        if (getThree(stringArrayList, arrayList, firstCard).size() == 0 &&
                getContinuousPairs(stringArrayList, arrayList, firstCard).size() == 0 &&
                getAirplane(stringArrayList, arrayList, firstCard).size() == 0 &&
                getBoom(stringArrayList, arrayList, firstCard).size() == 0) {
            int index1 = arrayList.indexOf(firstCard) + 1;
            int index2 = index1 + 1;
            if (arrayList.get(index1).equals(firstCard) && !arrayList.get(index2).equals(firstCard)) {
                stringArrayList.add(firstCard);
                stringArrayList.add(firstCard);
            }
        }
        return stringArrayList;
    }

    public static ArrayList<String> getStraight(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        int index = arrayList.indexOf(CardUtil.NextSequenceCard(firstCard));
        if (index > 0) {
            stringArrayList.add(firstCard);
            stringArrayList = getStraight(stringArrayList, arrayList, arrayList.get(index));
        }
        if (stringArrayList.size() < 5) {
            stringArrayList.clear();
        }
        return stringArrayList;
    }

    public static ArrayList<String> getThree(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        int index1 = arrayList.indexOf(firstCard) + 1;
        int index2 = index1 + 1;
        if (arrayList.get(index1).equals(firstCard) && arrayList.get(index2).equals(firstCard)) {
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
        }
        return stringArrayList;
    }

    public static ArrayList<String> getThreeWithOne(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        stringArrayList = getThree(stringArrayList, arrayList, firstCard);
        stringArrayList.add(getSingle(stringArrayList, arrayList, firstCard).get(0));
        return stringArrayList;
    }

    public static ArrayList<String> getContinuousPairs(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        int index1 = arrayList.indexOf(firstCard) + 1;
        if (arrayList.get(index1).equals(firstCard)) {
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
            int index2 = arrayList.indexOf(CardUtil.NextSequenceCard(firstCard));
            if (index2 > 0) {
                getContinuousPairs(stringArrayList, arrayList, arrayList.get(index2));
            }
        }
        if (stringArrayList.size() / 2 < 3) {
            stringArrayList.clear();
        }
        return stringArrayList;
    }

    public static ArrayList<String> getAirplane(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        int index1 = arrayList.indexOf(firstCard) + 1;
        int index2 = index1 + 1;
        if (arrayList.get(index1).equals(firstCard) && arrayList.get(index2).equals(firstCard)) {
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
            int index3 = arrayList.indexOf(CardUtil.NextSequenceCard(firstCard));
            if (index3 > 0) {
                getContinuousPairs(stringArrayList, arrayList, arrayList.get(index2));
            }
        }
        if (stringArrayList.size() / 3 < 1) {
            stringArrayList.clear();
        }
        return stringArrayList;
    }

    public static ArrayList<String> getBoom(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        int index1 = arrayList.indexOf(firstCard) + 1;
        int index2 = index1 + 1;
        int index3 = index2 + 1;
        if (arrayList.get(index1).equals(firstCard) && arrayList.get(index2).equals(firstCard) && arrayList.get(index3).equals(firstCard)) {
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
            stringArrayList.add(firstCard);
        }
        return stringArrayList;
    }

    public static ArrayList<String> getJokerBoom(ArrayList<String> stringArrayList, ArrayList<String> arrayList, String firstCard) {
        if (arrayList.indexOf("joker") > 0 && arrayList.indexOf("Joker") > 0) {
            stringArrayList.add("joker");
            stringArrayList.add("Joker");
        }
        return stringArrayList;
    }
}
