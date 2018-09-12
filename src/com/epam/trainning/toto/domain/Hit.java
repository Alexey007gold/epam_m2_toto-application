package com.epam.trainning.toto.domain;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Oleksii_Kovetskyi on 9/8/2018.
 */
public class Hit {

    private static final Pattern prizePattern = Pattern.compile("[^0-9. ]");

    private int number;
    private double prizeValue;
    private String prizeCurrency;

    public Hit(int number, String prize) {
        this.number = number;

        Matcher matcher = prizePattern.matcher(prize);
        if (matcher.find()) {
            this.prizeValue = Double.parseDouble(prize.substring(0, matcher.start()).replaceAll(" ", "").trim());
            this.prizeCurrency = prize.substring(matcher.start()).trim();
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
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        format.setDecimalFormatSymbols(symbols);
        return format.format(prizeValue) + " " + prizeCurrency;
    }
}
