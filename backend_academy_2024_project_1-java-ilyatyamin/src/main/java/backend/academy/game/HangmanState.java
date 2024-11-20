package backend.academy.game;

import lombok.Getter;

@Getter
public enum HangmanState {
    @SuppressWarnings({"it is idea", "checkstyle:RegexpSingleline"})
    FIRST("""

             |
             |
             |
            ___
        """),
    SECOND("""
        -------
             |
             |
             |
            ___
        """),
    THIRD("""
        -------
        O    |
             |
             |
            ___
        """),
    FOURTH("""
        -------
        O    |
        |    |
             |
            ___
        """),
    FIFTH("""
         -------
         O    |
         |    |
        /     |
             ___
        \s"""),
    SIXTH("""
         -------
         O    |
         |    |
        / \\   |
             ___
        \s"""),
    SEVENTH("""
         -------
         O    |
         |    |
        /|\\   |
             ___
        \s"""),
    EIGHTH("""
         -------
         O    |
         |    |
        /|\\   |
        /    ___
        \s"""),
    NINTH("""
         -------
        ⬭    |
         \\    |
        /|\\   |
        / \\  ___
        \s"""),
    END("""
         -------
         ⊗    |
         |    |
        /|\\   |
        / \\  ___
        \s""");

    private final String state;

    HangmanState(String state) {
        this.state = state;
    }

}

