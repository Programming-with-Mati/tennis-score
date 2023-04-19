package com.programmingwithmati.tennis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  @Test
  void testInit() {
    var game = Game.init();
    assertEquals(0, game.player1().score());
    assertEquals(0, game.player2().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }

  @Test
  void testPointPlayer1When0() {
    var game15 = Game.init().pointPlayer1();
    assertEquals(15, game15.player1().score());
    assertEquals(Game.GameStatus.ON_GOING, game15.status());
  }

  @Test
  void testPointPlayer1When15() {
    var game = Game.init().pointPlayer1().pointPlayer1();
    assertEquals(30, game.player1().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }

  @Test
  void testPointPlayer1When30() {
    var game = Game.init().pointPlayer1().pointPlayer1().pointPlayer1();
    assertEquals(40, game.player1().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }

  @Test
  void testPointPlayer1When40() {
    var game = Game.init().pointPlayer1().pointPlayer1().pointPlayer1().pointPlayer1();
    assertEquals(40, game.player1().score());
    assertEquals(Game.GameStatus.PLAYER_1_WON, game.status());
  }

  @Test
  void testPointPlayer2When0() {
    var game15 = Game.init().pointPlayer2();
    assertEquals(15, game15.player2().score());
    assertEquals(Game.GameStatus.ON_GOING, game15.status());
  }

  @Test
  void testPointPlayer2When15() {
    var game = Game.init().pointPlayer2().pointPlayer2();
    assertEquals(30, game.player2().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }

  @Test
  void testPointPlayer2When30() {
    var game = Game.init().pointPlayer2().pointPlayer2().pointPlayer2();
    assertEquals(40, game.player2().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }

  @Test
  void testAdvantage() {
    var game = Game.init()
            .pointPlayer2()
            .pointPlayer2()
            .pointPlayer2()
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer1();
    assertEquals(PlayerScore.ADVANTAGE, game.player1().score());
    assertEquals(40, game.player2().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }

  @Test
  void testAdvantageBackToDeuce() {
    var game = Game.init()
            .pointPlayer2()
            .pointPlayer2()
            .pointPlayer2()
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer2();
    assertEquals(40, game.player1().score());
    assertEquals(40, game.player2().score());
    assertEquals(Game.GameStatus.ON_GOING, game.status());
  }
}
