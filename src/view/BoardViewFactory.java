
package view;

import control.GameActionExecutor;

/**
 * Describe your class
 */
public class BoardViewFactory {

    private static BoardView buildViewInteractive(GameActionExecutor executor, int h, int w) {
        return new BoardViewInteractive(executor, h, w);
    }

    private static BoardView buildViewConsole(GameActionExecutor executor, int h, int w) {
        return new BoardViewConsole(executor, h, w);
    }

    public static BoardView buildView(GameActionExecutor executor, final ViewType type, final int h, final int w) {
        switch (type) {
            case CONSOLE:
                return buildViewConsole(executor, h, w);
            case INTERACTIVE:
                return buildViewInteractive(executor, h, w);
            default:
                throw new IllegalArgumentException("Unable to render type of view " + type);
        }
    }
}
