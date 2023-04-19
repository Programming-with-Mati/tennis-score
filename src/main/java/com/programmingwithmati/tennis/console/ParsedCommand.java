package com.programmingwithmati.tennis.console;

import com.programmingwithmati.tennis.TennisSet;

import java.util.function.UnaryOperator;

public record ParsedCommand(
        String key,
        UnaryOperator<TennisSet> operation,
        boolean isValid
) {
}
