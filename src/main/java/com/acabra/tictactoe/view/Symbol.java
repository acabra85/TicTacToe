
package com.acabra.tictactoe.view;

/**
 * Describe your class
 */
public enum Symbol {
    EMPTY("_"), CIRCLE("O"), CROSS("X")
    ;

    public final String value;

    Symbol(String c) {
        this.value = c;
    }

    public static String getValue(int i) {
        return i == 0 ? EMPTY.value : (i < 0 ? CIRCLE.value : CROSS.value);
    }
}
