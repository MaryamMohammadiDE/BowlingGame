package org.example;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

    private final List<Integer> rolls;

    public BowlingGame(List<Integer> rolls) {
        validiereRolls(rolls);
        this.rolls = rolls;
    }

    public static void validiereRolls(List<Integer> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException("Würfe dürfen nicht null oder leer sein");
        }
        for (int roll : rolls) {
            if (roll < 0 || roll > 10) {
                throw new IllegalArgumentException("Ungültiger Rollwert: " + roll);
            }
        }
    }

    public int berechneGesamtPunkte() {
        List<Frame> frames = new ArrayList<>();
        int index = 0;
        Frame frame;

        for (int frameNummer = 1; frameNummer <= 9; frameNummer++) {
            int roll1 = getRoll(index);
            int roll2 = getRoll(index + 1);
            int roll3 = getRoll(index + 2);

            int punkte;

            if (istStrike(roll1)) {
                int boni = roll2 + roll3;
                punkte = 10 + boni;
                frame = new Frame(frameNummer, punkte, Status.STRIKE);
                index++;
            } else {
                if (roll1 + roll2 > 10) {
                    throw new IllegalArgumentException("Ungültiger Frame: Rolls überschreiten 10 Pins");
                }
                punkte = roll1 + roll2;
                if (isSpare(roll1, roll2)) {
                    int boni = roll3;
                    punkte += boni;
                    frame = new Frame(frameNummer, punkte, Status.SPARE);
                } else {
                    frame = new Frame(frameNummer, punkte, Status.OPEN);
                }
                index = index + 2;
            }
            frames.add(frame);
        }
        verarbeiteZehntenFrame(index, frames);
        return frames.stream().mapToInt(Frame::punkte).sum();
    }

    private int getRoll(int index) {
        if (index >= rolls.size()) {
            return 0;
        }
        return rolls.get(index);
    }

    private static boolean isSpare(
        int roll,
        int nextRoll
    ) {
        return roll + nextRoll == 10 && roll != 10;
    }

    public static boolean istStrike(int roll) {
        return roll == 10;
    }

    private void verarbeiteZehntenFrame(
        int index,
        List<Frame> frames
    ) {
        Frame frame;
        if (index < rolls.size()) {
            int roll1 = getRoll(index);
            int roll2 = getRoll(index + 1);
            int roll3 = getRoll(index + 2);

            int punkte;
            if (istStrike(roll1)) {
                punkte = roll1 + roll2 + roll3;
                frame = new Frame(10, punkte, Status.STRIKE);
                index = index + 3;
            } else if (isSpare(roll1, roll2)) {
                punkte = roll1 + roll2 + roll3;
                frame = new Frame(10, punkte, Status.SPARE);
                index = index + 3;
            }
            else {
                if (roll1 + roll2 > 10) {
                    throw new IllegalArgumentException("Ungültiger Frame: Rolls überschreiten 10 Pins");
                }
                punkte = roll1 + roll2;
                frame = new Frame(10, punkte, Status.OPEN);
                index = index + 2;
            }
            frames.add(frame);
        }
        if (index < rolls.size()) {
            throw new IllegalArgumentException("Zu viele Rolls für ein einzelnes Spiel");
        }
    }
}
