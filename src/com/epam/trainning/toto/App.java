package com.epam.trainning.toto;

import com.epam.trainning.toto.domain.Hit;
import com.epam.trainning.toto.domain.Outcome;
import com.epam.trainning.toto.domain.Round;
import com.epam.trainning.toto.service.TotoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        TotoService service = new TotoService();
        SortedSet<Round> rounds = service.parseInputFile("toto.csv");

        System.out.println(String.format("The largest prize ever recorded is: %s%n",
                service.getMaxPrizeValue(rounds).getPrize()));
        service.printResultsDistribution(rounds);
        System.out.println();

        while (true) {
            interactWithUser(rounds);
        }
    }

    private static void interactWithUser(SortedSet<Round> rounds) throws IOException {
        LocalDate date = getDateFromUser();
        List<Round> roundsForDate = findRoundsForDate(rounds, date);

        String outcomesInput = getOutcomesFromUser();

        roundsForDate.forEach(r -> {
            int hits = countHits(r.getOutcomes(), outcomesInput);
            Hit hit = r.getPrizesMap().get(hits);
            String prize = (hit == null) ? "0" : hit.getPrize();
            System.out.println(String.format("Result: hits: %d, amount: %s%n", hits, prize));
        });
    }

    private static int countHits(List<Outcome> outcomes, String guess) {
        int hits = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (outcomes.get(i).getValue().equalsIgnoreCase(String.valueOf(guess.charAt(i)))) {
                hits++;
            }
        }
        return hits;
    }

    private static List<Round> findRoundsForDate(SortedSet<Round> rounds, LocalDate date) {
        return rounds.stream()
                .filter(r -> Objects.equals(r.getDate(), date))
                .collect(Collectors.toList());
    }

    private static String getOutcomesFromUser() {
        Pattern pattern = Pattern.compile("([12xX]{14})|([qQ])");
        return getInput(pattern, "Enter outcomes (1/2/X) or 'q' to quit: ",
                "14 characters are needed, ('1', '2' 'X' only)");
    }

    private static LocalDate getDateFromUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

        Pattern pattern = Pattern.compile("(\\d{4}\\.\\d{2}\\.\\d{2}\\.)|([qQ])");
        return LocalDate.parse(getInput(pattern, "Enter date or 'q' to quit: ", "1970.01.21."), formatter);
    }

    private static String getInput(Pattern pattern, String promptMessage, String formatMessage) {
        System.out.print(promptMessage);
        while (true) {
            try {
                String input = scanner.next(pattern);
                if (input.equalsIgnoreCase("q")) {
                    finish();
                }
                return input;
            } catch (Exception ignored) {
                System.out.print(String.format("Input format '%s'; Try again: ", formatMessage));
                scanner.nextLine();
            }
        }
    }

    private static void finish() {
        System.out.println("Bye!");
        System.exit(0);
    }
}
