
package com.acabra.tictactoe.control;

import com.acabra.tictactoe.model.Turn;
import com.acabra.tictactoe.utils.ResourceBundleUTF8;

/**
 * Describe your class
 */
public interface GameActionExecutor {
    boolean reportMove(int desiredMove);

    Turn getCurrentTurn();

    void finishMove();

    void restartGame();

    ResourceBundleUTF8 getTranslations();

}
