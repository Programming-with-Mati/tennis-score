package com.programmingwithmati.tennis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreTest {

  @Test
  void testInit() {
    var score = PlayerScore.init(1);
    assertEquals(1, score.player());
    assertEquals(0, score.score());
  }

  @Test
  void testPoint() {
    var scorePair = PlayerScore.init(1)
            .point(PlayerScore.init(2));
    assertEquals(1, scorePair.player().player());
    assertEquals(15, scorePair.player().score());
    assertEquals(0, scorePair.opponent().score());
  }

  @Test
  void testPointWhen15() {
    var player2 = PlayerScore.init(2);
    var scorePair = PlayerScore.init(1)
            .point(player2)
            .player().point(player2);
    assertEquals(1, scorePair.player().player());
    assertEquals(30, scorePair.player().score());
    assertEquals(0, scorePair.opponent().score());
  }

  @Test
  void testPointWhen30() {
    var player2 = PlayerScore.init(2);
    var scorePair = PlayerScore.init(1)
            .point(player2)
            .player().point(player2)
            .player().point(player2);
    assertEquals(1, scorePair.player().player());
    assertEquals(40, scorePair.player().score());
    assertEquals(0, scorePair.opponent().score());
  }

  @Test
  void testPointWhen40() {
    var player2 = PlayerScore.init(2);
    var scorePair = PlayerScore.init(1)
            .point(player2)
            .player().point(player2)
            .player().point(player2)
            .player().point(player2);
    assertEquals(1, scorePair.player().player());
    assertEquals(40, scorePair.player().score());
    assertEquals(0, scorePair.opponent().score());
    assertEquals(PlayerScore.ScoreStatus.COMPLETED, scorePair.player().status());
  }

  @Test
  void testPointWhenAdvantageTaken() {
    var player2 = new PlayerScore(2, 40, PlayerScore.ScoreStatus.ON_GOING);
    var scorePair = PlayerScore.init(1)
            .point(player2)
            .player().point(player2)
            .player().point(player2)
            .player().point(player2);

    assertEquals(1, scorePair.player().player());
    assertEquals(PlayerScore.ADVANTAGE, scorePair.player().score());
    assertEquals(40, scorePair.opponent().score());
    assertEquals(PlayerScore.ScoreStatus.ON_GOING, scorePair.player().status());

    var deuce = scorePair.opponent().point(scorePair.player());

    assertEquals(2, deuce.player().player());
    assertEquals(40, deuce.player().score());
    assertEquals(40, deuce.opponent().score());
    assertEquals(PlayerScore.ScoreStatus.ON_GOING, scorePair.player().status());

    var advPlayer2 = deuce.player().point(deuce.opponent());

    assertEquals(2, advPlayer2.player().player());
    assertEquals(PlayerScore.ADVANTAGE, advPlayer2.player().score());
    assertEquals(40, advPlayer2.opponent().score());
    assertEquals(PlayerScore.ScoreStatus.ON_GOING, scorePair.player().status());

  }
}
