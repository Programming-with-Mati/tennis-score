package com.programmingwithmati.tennis.console;

import com.programmingwithmati.tennis.TennisSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppTest {

  @Mock
  PrintStream out;
  @Mock
  BufferedReader br;
  @InjectMocks
  App app;

  @Test
  void testMainLoopWhenExit() throws IOException {
    given(br.readLine()).willReturn(App.EXIT_COMMAND);

    app.mainLoop();

    verify(out).println(TennisCommandLine.GAME_START_MESSAGE);
    verify(out).println(App.THANKS_FOR_PLAYING);
  }

  @Test
  void testMainLoopWhenPlayer1Wins() throws IOException {
    given(br.readLine()).willReturn("punto1");

    app.mainLoop();

    var set = app.getTennisSet();

    assertTrue(set.isCompleted());
    assertEquals( TennisSet.SetStatus.PLAYER_1_WON,set.status());

    verify(out).println(TennisCommandLine.GAME_START_MESSAGE);
    verify(out).printf(App.WINNER_TEMPLATE, 1);
    verify(out).println(App.THANKS_FOR_PLAYING);
  }

}
