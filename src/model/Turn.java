
package model;

/**
 * Reports active player
 */
public enum Turn {
    CIRCLE("O"), CROSS("X");

    public final String value;

    Turn(String x) {
        this.value = x;
    }
}
