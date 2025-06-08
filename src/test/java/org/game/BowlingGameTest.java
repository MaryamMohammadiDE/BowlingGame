package org.game;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BowlingGameTest {

    @Test
    public void pruefeObRollsNullSind() {
        // given
        List<Integer> rolls = null;

        // when

        // then
        assertThatThrownBy(() -> BowlingGame.validiereRolls(rolls))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Würfe dürfen nicht null oder leer sein");
    }

    @Test
    public void pruefeObRollsLeerSind() {
        // given
        List<Integer> rolls = List.of();

        // when

        // then
        assertThatThrownBy(() -> BowlingGame.validiereRolls(rolls))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Würfe dürfen nicht null oder leer sein");
    }

    @Test
    public void pruefeObRollwertGueltigIst() {
        // given
        List<Integer> rolls = List.of(10, 10, 15, 10, 10, 10, 10, 10, 10, 10, 10, 10);

        // when

        // then
        assertThatThrownBy(() -> BowlingGame.validiereRolls(rolls))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Ungültiger Rollwert: " + 15);
    }

    @Test
    public void pruefeObFrameSummeNichtGroeßerAlsZehnIst() {
        // given
        List<Integer> rolls = List.of(9, 0, 6, 7, 7, 2, 6, 3, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0, 0, 0);
        BowlingGame bowlingGame = new BowlingGame(rolls);

        // when

        // then
        assertThatThrownBy(() -> bowlingGame.berechneGesamtPunkte())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Ungültiger Frame: Rolls überschreiten 10 Pins");
    }

    @Test
    public void pruefeObZuVieleRollsVorhandenSind() {
        // given
        List<Integer> rolls = List.of(9, 0, 6, 4, 7, 2, 6, 3, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0, 0, 0, 10);
        BowlingGame bowlingGame = new BowlingGame(rolls);

        // when

        // then
        assertThatThrownBy(() -> bowlingGame.berechneGesamtPunkte())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Zu viele Rolls für ein einzelnes Spiel");
    }


    @Test
    public void perfektesSpiel() {
        // given
        List<Integer> rolls = List.of(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        BowlingGame bowlingGame = new BowlingGame(rolls);

        // when
        int totalScore = bowlingGame.berechneGesamtPunkte();

        // then
        assertThat(totalScore).isEqualTo(300);
    }

    @Test
    public void keineStrikesOderSpares() {
        // given
        List<Integer> rolls = List.of(9, 0, 8, 1, 7, 2, 6, 3, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0, 0, 0);
        BowlingGame bowlingGame = new BowlingGame(rolls);

        // when
        int totalScore = bowlingGame.berechneGesamtPunkte();

        // then
        assertThat(totalScore).isEqualTo(61);
    }

    @Test
    public void strikeImZehntenFrameMitBonussen() {
        // given
        List<Integer> rolls = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 10, 10);
        BowlingGame bowlingGame = new BowlingGame(rolls);

        // when
        int totalScore = bowlingGame.berechneGesamtPunkte();

        // then
        assertThat(totalScore).isEqualTo(30);
    }

    @Test
    public void spareImZehntenFrameMitBonus() {
        // given
        List<Integer> rolls = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 7);
        BowlingGame bowlingGame = new BowlingGame(rolls);

        // when
        int totalScore = bowlingGame.berechneGesamtPunkte();

        // then
        assertThat(totalScore).isEqualTo(17);
    }
}