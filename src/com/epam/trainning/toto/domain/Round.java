package com.epam.trainning.toto.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Oleksii_Kovetskyi on 9/8/2018.
 */
public class Round {

    private Integer year;
    private Integer week;
    private Integer roundNumber;
    private LocalDate date;

    private Map<Integer, Hit> prizesMap;
    private List<Outcome> outcomes;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(String date) {
        if (date != null && !date.isEmpty()) {
            this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        } else if (year != null && week != null){
            this.date = LocalDate.of(this.year, 1, 1)
                    .with(WeekFields.of(Locale.getDefault()).weekOfYear(), week);
        }
    }

    public Map<Integer, Hit> getPrizesMap() {
        return prizesMap;
    }

    public void setPrizesMap(Map<Integer, Hit> prizesMap) {
        this.prizesMap = prizesMap;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = outcomes;
    }
}
