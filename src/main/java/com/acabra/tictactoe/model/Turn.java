
package com.acabra.tictactoe.model;

/**
 * Reports active player
 */
public enum Turn {
    CIRCLE("O", -1), CROSS("X", 1);

    public final String value;
    public final int id;

    Turn(String x, int id) {
        this.value = x;
        this.id = id;
    }

    public static String getSymbol(int id) {
        return CIRCLE.id == id ? CIRCLE.value : CROSS.value;
    }
}
