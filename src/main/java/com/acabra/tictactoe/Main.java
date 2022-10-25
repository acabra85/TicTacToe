
package com.acabra.tictactoe;

import java.util.Locale;

import com.acabra.tictactoe.control.GameController;
import com.acabra.tictactoe.view.ViewType;

/**
 * Describe your class
 */
public class Main {

    public static void main(String... args) {

        ViewType type = args.length > 0 && ViewType.CONSOLE.id.equals(args[0].toUpperCase()) ?
                ViewType.CONSOLE :
                ViewType.INTERACTIVE;
        Locale locale = Locale.ENGLISH;
        if (args.length > 1) {
            String[] localeArgs = args[1].split("_");
            locale = new Locale(localeArgs[0], localeArgs[1]);
        }
        GameController.of(locale).play(type, 3, 3);
    }
}
