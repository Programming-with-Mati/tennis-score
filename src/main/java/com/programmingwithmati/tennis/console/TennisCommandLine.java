package com.programmingwithmati.tennis.console;

import com.programmingwithmati.tennis.Game;
import com.programmingwithmati.tennis.TennisSet;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TennisCommandLine {

  private static final Map<String, ParsedCommand> COMMANDS = Stream.of(
          new ParsedCommand("punto1", TennisSet::pointPlayer1, true),
          new ParsedCommand("punto2", TennisSet::pointPlayer2, true),
          new ParsedCommand("salir", UnaryOperator.identity(), true)
  ).collect(Collectors.toMap(ParsedCommand::key, Function.identity()));

  public static final String GAME_START_MESSAGE = """
          Comienza el juego!
          Los comandos para incrementar el contador son:
          punto1 - punto para el jugador 1
          punto2 - punto para el jugador 2
          salir - salir del juego
          """;

  public ParsedCommand parseCommand(String line) {
    return COMMANDS.getOrDefault(line, new ParsedCommand(line, UnaryOperator.identity(), false));
  }

  public String currentScore(TennisSet set) {
    return "Jugador\t\t| G\t| S\t|\n" +
            currentPlayerScore(1,set.gamesPlayer1(), set.currentGame().player1().score()) + "\n" +
            currentPlayerScore(2,set.gamesPlayer2(), set.currentGame().player2().score());
  }

  private String currentPlayerScore(int player, List<Game> playerGames, int currentGameScore) {
    return "jugador %d\t| %d\t| %d\t|".formatted(player, currentGameScore, playerGames.size());
  }
}
