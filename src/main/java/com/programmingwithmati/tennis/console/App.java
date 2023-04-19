package com.programmingwithmati.tennis.console;

import com.programmingwithmati.tennis.TennisSet;

import java.io.*;

public class App {

  public static final String EXIT_COMMAND = "salir";
  public static final String THANKS_FOR_PLAYING = "Gracias por jugar! Hasta luego!";
  public static final String WINNER_TEMPLATE = "El ganador es: jugador %d\n";

  private final PrintStream out;
  private final BufferedReader br;
  private TennisSet set = TennisSet.init();

  public App(PrintStream out, BufferedReader br) {
    this.out = out;
    this.br = br;
  }

  public void mainLoop() throws IOException {
    TennisCommandLine commandLine = new TennisCommandLine();

    out.println(TennisCommandLine.GAME_START_MESSAGE);

    ParsedCommand parsedCommand;

    do {
      parsedCommand = commandLine.parseCommand(br.readLine());

      if (!parsedCommand.isValid()) out.printf("%s no es un comando valido, ingresa un nuevo comando\n", parsedCommand.key());

      set = parsedCommand.operation().apply(set);

      out.println(commandLine.currentScore(set));
    } while (!parsedCommand.key().equals(EXIT_COMMAND) && !set.isCompleted());

    if(set.isCompleted()) out.printf(WINNER_TEMPLATE, set.winner());

    out.println(THANKS_FOR_PLAYING);

    br.close();
  }

  public TennisSet getTennisSet() {
    return set;
  }
}
