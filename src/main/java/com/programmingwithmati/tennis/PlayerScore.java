package com.programmingwithmati.tennis;

public record PlayerScore(int player,
                          int score,
                          ScoreStatus status) {


  public static final int ADVANTAGE = 41;

  enum ScoreStatus {
    ON_GOING,
    COMPLETED
  }

  record ScorePair(
          PlayerScore player,
          PlayerScore opponent
  ) {}

  public static PlayerScore init(int playerNo) {
    return new PlayerScore(playerNo, 0, ScoreStatus.ON_GOING);
  }

  public ScorePair point(PlayerScore opponent) {
    return switch (score) {
      case 0 -> new ScorePair(new PlayerScore(player, 15, ScoreStatus.ON_GOING), opponent);
      case 15 -> new ScorePair(new PlayerScore(player, 30, ScoreStatus.ON_GOING), opponent);
      case 30 -> new ScorePair(new PlayerScore(player, 40, ScoreStatus.ON_GOING), opponent);
      case 40 -> calculateNewScoreFor40(opponent);
      case ADVANTAGE -> new ScorePair(new PlayerScore(player, ADVANTAGE, ScoreStatus.COMPLETED), opponent);
      default -> throw new IllegalStateException("Unexpected value for score: " + score);
    };
  }

  private ScorePair calculateNewScoreFor40(PlayerScore opponent) {
    return switch (opponent.score) {
      case 40 -> new ScorePair(new PlayerScore(player, ADVANTAGE, ScoreStatus.ON_GOING), opponent);
      case ADVANTAGE -> new ScorePair(new PlayerScore(player, 40, ScoreStatus.ON_GOING), new PlayerScore(opponent.player, 40, ScoreStatus.ON_GOING));
      default ->  new ScorePair(new PlayerScore(player, 40, ScoreStatus.COMPLETED), opponent);
    };
  }
}
