package com.programmingwithmati.tennis.console;

import com.programmingwithmati.tennis.TennisSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TennisCommandLineTest {

  TennisCommandLine tennisCommandLine = new TennisCommandLine();

  @Test
  void testParseCommandWhenPoint1() {
    var set = TennisSet.init();
    var punto1 = tennisCommandLine.parseCommand("punto1");
    var result = punto1.operation().apply(set);

    assertEquals(15, result.currentGame().player1().score());
    assertEquals(0, result.currentGame().player2().score());
  }


  @Test
  void testParseCommandWhenPoint2() {
    var set = TennisSet.init();
    var punto2 = tennisCommandLine.parseCommand("punto2");
    var result = punto2.operation().apply(set);

    assertEquals(0, result.currentGame().player1().score());
    assertEquals(15, result.currentGame().player2().score());
  }

  @Test
  void testParseCommandWhenInvalidCommand() {
    var set = TennisSet.init();
    var command = tennisCommandLine.parseCommand("punto3");

    assertFalse(command.isValid());

    var result = command.operation().apply(set);

    assertEquals(0, result.currentGame().player1().score());
    assertEquals(0, result.currentGame().player2().score());
  }

  @Test
  void testCurrentScoreWhenEmpty() {
    var set = TennisSet.init();

    var currentScore = tennisCommandLine.currentScore(set);
    System.out.println(currentScore);

    var scores = currentScore.split("\n");

    assertEquals("Jugador\t\t| G\t| S\t|", scores[0]);
    assertEquals("jugador 1\t| 0\t| 0\t|", scores[1]);
    assertEquals("jugador 2\t| 0\t| 0\t|", scores[2]);

  }

  @Test
  void testCurrentScoreWhenPointPlayer1() {
    var set = TennisSet.init().pointPlayer1();

    var currentScore = tennisCommandLine.currentScore(set);

    var scores = currentScore.split("\n");

    assertEquals("jugador 1\t| 15\t| 0\t|", scores[1]);
    assertEquals("jugador 2\t| 0\t| 0\t|", scores[2]);
  }
}
