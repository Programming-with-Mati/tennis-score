package com.programmingwithmati.tennis;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TennisSetTest {

  @Test
  void init() {
    var set = TennisSet.init();

    assertEquals(0, set.gamesPlayer1().size());
    assertEquals(0, set.gamesPlayer2().size());
    assertEquals(0, set.currentGame().player1().score());
    assertEquals(0, set.currentGame().player2().score());
  }

  @Test
  void pointPlayer1(){
    var set = TennisSet.init().pointPlayer1();

    assertEquals(15, set.currentGame().player1().score());
    assertEquals(0, set.currentGame().player2().score());
  }

  @Test
  void pointPlayer1WinsGame1(){
    var set = gamePlayer1(TennisSet.init());


    assertEquals(1, set.gamesPlayer1().size());
    assertEquals(0, set.gamesPlayer2().size());
    assertEquals(0, set.currentGame().player1().score());
    assertEquals(0, set.currentGame().player2().score());
  }

  @Test
  void pointPlayer2(){
    var set = TennisSet.init().pointPlayer2();

    assertEquals(15, set.currentGame().player2().score());
    assertEquals(0, set.currentGame().player1().score());
  }

  @Test
  void pointPlayer1Wins6Games(){
    var set = Stream.iterate(TennisSet.init(), this::gamePlayer1)
            .limit(7).skip(6).findFirst()
            .orElseThrow();

    assertEquals(6, set.gamesPlayer1().size());
    assertEquals(0, set.gamesPlayer2().size());
    assertEquals(TennisSet.SetStatus.PLAYER_1_WON, set.status());
  }

  @Test
  void pointTryWhenPlayer1AlreadyWon(){
    var set = Stream.iterate(TennisSet.init(), this::gamePlayer1)
            .limit(7).skip(6).findFirst()
            .orElseThrow();

    assertEquals(TennisSet.SetStatus.PLAYER_1_WON, set.status());

    assertThrows(IllegalStateException.class, set::pointPlayer2);
  }

  @Test
  void pointPlayer1Wins6GamesPlayer2Wins4(){
    var set = Stream.iterate(TennisSet.init(), this::gamePlayer2)
            .limit(5).skip(4).findFirst()
            .orElseThrow();

    set = Stream.iterate(set, this::gamePlayer1)
            .limit(7).skip(6).findFirst()
            .orElseThrow();

    assertEquals(6, set.gamesPlayer1().size());
    assertEquals(4, set.gamesPlayer2().size());
    assertEquals(TennisSet.SetStatus.PLAYER_1_WON, set.status());
  }

  @Test
  void pointPlayer1Wins6GamesPlayer2Wins5(){
    var set = Stream.iterate(TennisSet.init(), this::gamePlayer2)
            .limit(6).skip(5).findFirst()
            .orElseThrow();

    set = Stream.iterate(set, this::gamePlayer1)
            .limit(7).skip(6).findFirst()
            .orElseThrow();

    assertEquals(6, set.gamesPlayer1().size());
    assertEquals(5, set.gamesPlayer2().size());
    assertEquals(TennisSet.SetStatus.ON_GOING, set.status());
  }

  @Test
  void pointPlayer1WinsInTieBreak(){
    var set = Stream.iterate(TennisSet.init(), this::gamePlayer2)
            .limit(6).skip(5).findFirst()
            .orElseThrow();

    set = Stream.iterate(set, this::gamePlayer1)
            .limit(8).skip(7).findFirst()
            .orElseThrow();

    assertEquals(7, set.gamesPlayer1().size());
    assertEquals(5, set.gamesPlayer2().size());
    assertEquals(TennisSet.SetStatus.PLAYER_1_WON, set.status());
  }

  @Test
  void pointPlayer1WinsInTieBreak7to6(){
    var set = Stream.iterate(TennisSet.init(), this::gamePlayer2)
            .limit(6).skip(5).findFirst()
            .orElseThrow();

    set = Stream.iterate(set, this::gamePlayer1)
            .limit(7).skip(6).findFirst()
            .orElseThrow();

    set = gamePlayer2(set);
    set = gamePlayer1(set);

    assertEquals(7, set.gamesPlayer1().size());
    assertEquals(6, set.gamesPlayer2().size());
    assertEquals(TennisSet.SetStatus.PLAYER_1_WON, set.status());
  }

  private TennisSet gamePlayer1(TennisSet set) {
    return set
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer1()
            .pointPlayer1();
  }

  private TennisSet gamePlayer2(TennisSet set) {
    return set
            .pointPlayer2()
            .pointPlayer2()
            .pointPlayer2()
            .pointPlayer2();
  }
}
