package com.epam.trainning.toto.domain;

/**
 * Created by Oleksii_Kovetskyi on 9/8/2018.
 */
public enum Outcome {
    ONE("1"),
    TWO("2"),
    X("X");

    private String value;

    Outcome(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
