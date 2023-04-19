package com.programmingwithmati.tennis;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public record Game(
        PlayerScore player1,
        PlayerScore player2,
        GameStatus status) {

  enum GameStatus {
    ON_GOING,
    PLAYER_1_WON,
    PLAYER_2_WON
  }

  public static Game init() {
    return new Game(
            PlayerScore.init(1),
            PlayerScore.init(2),
            GameStatus.ON_GOING
    );
  }

  public Game pointPlayer1() {
    return point(() -> player1.point(player2),
            (newScore, status) -> new Game(newScore.player(), newScore.opponent(), status),
            GameStatus.PLAYER_1_WON);
  }

  public Game pointPlayer2() {
    return point(() -> player2.point(player1),
            (newScore, status) -> new Game(newScore.opponent(), newScore.player(), status),
            GameStatus.PLAYER_2_WON);
  }

  private Game point(
          Supplier<PlayerScore.ScorePair> gameSupplier,
          BiFunction<PlayerScore.ScorePair, GameStatus, Game> gameProvider,
          GameStatus statusIfPlayerWins
  ) {
    var newPoints = gameSupplier.get();
    return gameProvider.apply(
            newPoints,
            newPoints.player().status() == PlayerScore.ScoreStatus.COMPLETED ? statusIfPlayerWins : GameStatus.ON_GOING
            );

  }

}
