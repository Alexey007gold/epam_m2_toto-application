package com.epam.trainning.toto.service;

import com.epam.trainning.toto.domain.Hit;
import com.epam.trainning.toto.domain.Outcome;
import com.epam.trainning.toto.domain.Round;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Oleksii_Kovetskyi on 9/8/2018.
 */
public class TotoService {

    public SortedSet<Round> parseInputFile(String filePath) throws IOException {
        String[] lines = new String(Files.readAllBytes(Paths.get(filePath))).split("\n");
        SortedSet<Round> games = new TreeSet<>(Comparator.comparing(Round::getDate));
        Arrays.stream(lines).forEach(line -> {
            String[] values = line.split(";");
            int year = Integer.parseInt(values[0]);
            int week = Integer.parseInt(values[1]);
            int round = values[2].matches("\\d+") ? Integer.parseInt(values[2]) : -1;
            String date = values[3];

            Map<Integer, Hit> prizesMap = new HashMap<>();
            int hits = 14;
            for (int i = 4; i < 14; i += 2) {
                prizesMap.put(hits, new Hit(Integer.parseInt(values[i]), values[i + 1]));
                hits--;
            }
            List<Outcome> outcomes = new ArrayList<>();
            for (int i = 14; i < 27; i++) {
                outcomes.add(getOutcomeVal(values[i].trim()));
            }
            outcomes.add(getOutcomeVal(values[27].trim().replace("+", "")));

            Round game = new Round();
            game.setYear(year);
            game.setWeek(week);
            game.setRoundNumber(round);
            game.setDate(date);
            game.setPrizesMap(prizesMap);
            game.setOutcomes(outcomes);
            games.add(game);
        });
        return games;
    }

    public void printResultsDistribution(SortedSet<Round> rounds) {
        rounds.forEach(round -> {
            Map<Outcome, Integer> distributionMap = new EnumMap<>(Outcome.class);
            round.getOutcomes().forEach(o -> distributionMap.merge(o, 1, (a, b) -> a + b));

            int games = round.getOutcomes().size();
            double team1 = getPercentage(distributionMap.get(Outcome.ONE), games);
            double team2 = getPercentage(distributionMap.get(Outcome.TWO), games);
            double draw = getPercentage(distributionMap.get(Outcome.X), games);
            DecimalFormat format = new DecimalFormat("#.##");
            format.setMinimumFractionDigits(2);
            System.out.println(String.format("Team #1 won: %s %%, team #2 won %s %%, draw %s %%",
                    format.format(team1), format.format(team2), format.format(draw)));
        });
    }

    private double getPercentage(Integer outcome, int games) {
        return outcome == null ? 0 : outcome * 100.0 / games;
    }

    public Hit getMaxPrizeValue(SortedSet<Round> rounds) {
        return rounds.stream()
                .flatMap(round -> round.getPrizesMap().values().stream())
                .max(Comparator.comparing(Hit::getPrizeValue))
                .orElseThrow(() -> new IllegalStateException("No entries"));
    }

    private Outcome getOutcomeVal(String value) {
        return Arrays.stream(Outcome.values())
                .filter(v -> v.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Incorrect outcome value: " + value));
    }
}
