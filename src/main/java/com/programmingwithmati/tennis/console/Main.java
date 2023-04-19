package com.programmingwithmati.tennis.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  public static void main(String[] args) throws IOException {
    var app = new App(System.out, new BufferedReader(new InputStreamReader(System.in)));

    app.mainLoop();
  }
}
