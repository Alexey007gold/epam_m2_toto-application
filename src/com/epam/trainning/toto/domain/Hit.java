package com.epam.trainning.toto.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleksii_Kovetskyi on 9/8/2018.
 */
public class Hit implements Comparable<Hit> {

    private int number;
    private String prize;

    public Hit(int number, String prize) {
        this.number = number;
        this.prize = prize;
    }

    public int getNumber() {
        return number;
    }

    public String getPrize() {
        return prize;
    }

    @Override
    public int compareTo(Hit o) {
        return this.getPrizeValue() - o.getPrizeValue();
    }

    public int getPrizeValue() {
        Pattern p = Pattern.compile("[^0-9 ]");
        Matcher m = p.matcher(prize);
        if (m.find()) {
            String valueStr = prize.substring(0, m.start()).replaceAll(" ", "");
            return Integer.parseInt(valueStr);
        }
        return Integer.parseInt(prize.replaceAll(" ", ""));
    }
}
