package com.epam.trainning.toto;

import com.epam.trainning.toto.domain.Hit;
import com.epam.trainning.toto.domain.Outcome;
import com.epam.trainning.toto.domain.Round;
import com.epam.trainning.toto.service.TotoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        TotoService service = new TotoService();
        List<Round> rounds = service.parseInputFile("toto.csv");

        System.out.println(String.format("The largest prize ever recorded is: %s%n",
                service.getMaxPrizeValue(rounds).getPrize()));
        service.printResultsDistribution(rounds);
        System.out.println();

        interactWithUser(rounds);
    }

    private static void interactWithUser(List<Round> rounds) throws IOException {
        LocalDate date = getDateFromUser();
        List<Round> roundsForDate = findRoundsForDate(rounds, date);

        String outcomesInput = getOutcomesFromUser();

        roundsForDate.forEach(r -> {
            int hits = countHits(r.getOutcomes(), outcomesInput);
            Hit hit = r.getPrizesMap().get(hits);
            String prize;
            if (hit != null) {
                prize = hit.getPrize();
            } else {
                prize = "0";
            }
            System.out.println(String.format("Result: hits: %d, amount: %s", hits, prize));
        });
    }

    private static int countHits(List<Outcome> outcomes, String guess) {
        int hits = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (outcomes.get(i).getValue().equals(String.valueOf(guess.charAt(i)))) {
                hits++;
            }
        }
        return hits;
    }

    private static List<Round> findRoundsForDate(List<Round> rounds, LocalDate date) {
        return rounds.stream()
                .filter(r -> Objects.equals(r.getDate(), date))
                .collect(Collectors.toList());
    }

    private static String getOutcomesFromUser() throws IOException {
        System.out.print("Enter outcomes (1/2/X): ");
        String input = reader.readLine();

        Pattern p = Pattern.compile("[12xX]{14}");
        while (true) {
            if (p.matcher(input).matches()) {
                return input;
            } else {
                System.out.print("14 characters are needed, ('1', '2' 'X' only) Try again: ");
                input = reader.readLine();
            }
        }
    }

    private static LocalDate getDateFromUser() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        System.out.print("Enter date: ");
        String input = reader.readLine();

        while (true) {
            try {
                return LocalDate.parse(input, formatter);
            } catch (RuntimeException e) {
                System.out.print("Date format '1970.01.21.'; Try again: ");
                input = reader.readLine();
            }
        }
    }
}
