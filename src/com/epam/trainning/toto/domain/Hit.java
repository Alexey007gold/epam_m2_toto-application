package com.epam.trainning.toto.domain;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleksii_Kovetskyi on 9/8/2018.
 */
public class Hit implements Comparable<Hit> {

    private int number;
    private double prizeValue;
    private String prizeCurrency;

    public Hit(int number, String prize) {
        this.number = number;

        Pattern p = Pattern.compile("[^0-9.]");
        Matcher m = p.matcher(prize);
        if (m.find()) {
            this.prizeValue = Double.parseDouble(prize.substring(0, m.start()).replaceAll(" ", "").trim());
            this.prizeCurrency = prize.substring(m.start()).trim();
        } else {
            this.prizeValue = Double.parseDouble(prize.replaceAll(" ", "").trim());
        }
    }

    public int getNumber() {
        return number;
    }

    public String getPrizeCurrency() {
        return prizeCurrency;
    }

    public double getPrizeValue() {
        return prizeValue;
    }

    public String getPrize() {
        DecimalFormat format = new DecimalFormat("###,###.##");
        return format.format(prizeValue) + " " + prizeCurrency;
    }
}
