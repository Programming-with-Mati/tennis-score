package com.programmingwithmati.tennis;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record TennisSet (
        List<Game> gamesPlayer1,
        List<Game> gamesPlayer2,
        Game currentGame,
        SetStatus status
) {

  public enum SetStatus {
    PLAYER_1_WON(1),
    PLAYER_2_WON(2),
    ON_GOING(0);

    final int winner;

    SetStatus(int winner) {
      this.winner = winner;
    }
    public int winner() {
      return winner;
    }
  }

  static public TennisSet init() {
    return new TennisSet(
            Collections.emptyList(),
            Collections.emptyList(),
            Game.init(),
            SetStatus.ON_GOING
            );
  }

  public TennisSet pointPlayer1() {
    return point(
            currentGame::pointPlayer1,
            (game) -> new TennisSet(
                    appendGame(gamesPlayer1, game),
                    gamesPlayer2,
                    Game.init(),
                    didPlayerWinSet(gamesPlayer1.size() + 1, gamesPlayer2.size()) ? SetStatus.PLAYER_1_WON : SetStatus.ON_GOING
                   )
            );
  }

  public TennisSet pointPlayer2() {
    return point(
            currentGame::pointPlayer2,
            (game) -> new TennisSet(
                    gamesPlayer1,
                    appendGame(gamesPlayer2, game),
                    Game.init(),
                    didPlayerWinSet(gamesPlayer2.size() + 1, gamesPlayer1.size()) ? SetStatus.PLAYER_2_WON : SetStatus.ON_GOING
            ));
  }

  private TennisSet point(
          Supplier<Game> gameSupplier,
          Function<Game, TennisSet> winnerSetProvider
  ) {
    if (alreadyCompleted()) throw new IllegalStateException("Can't process a new point for an already completed set. Status was: %s".formatted(status));

    var newCurrentGame = gameSupplier.get();

    return newCurrentGame.status() == Game.GameStatus.ON_GOING ?
            new TennisSet(
                    gamesPlayer1,
                    gamesPlayer2,
                    newCurrentGame,
                    SetStatus.ON_GOING
            ) : winnerSetProvider.apply(newCurrentGame);
  }

  private boolean alreadyCompleted() {
    return status != SetStatus.ON_GOING;
  }

  private List<Game> appendGame(List<Game> games, Game newCurrentGame) {
    return Stream.concat(games.stream(), Stream.of(newCurrentGame)).toList();
  }

  private boolean didPlayerWinSet(int winnerGames, int loserGames) {
    if (winnerGames == 7 && loserGames == 6) return true;

    return winnerWonSixOrMoreGames(winnerGames) &&
            gameDifferenceIsTwoOrMore(winnerGames, loserGames);
  }

  private boolean winnerWonSixOrMoreGames(int winnerGames) {
    return winnerGames >= 6;
  }

  private boolean gameDifferenceIsTwoOrMore(int winnerGames, int loserGames) {
    return winnerGames - loserGames >= 2;
  }

  public boolean isCompleted() {
    return status != SetStatus.ON_GOING;
  }

  public int winner() {
    return status.winner();
  }

}
