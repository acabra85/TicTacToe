
package com.acabra.tictactoe.view;

import java.util.List;
import com.acabra.tictactoe.model.Turn;
import com.acabra.tictactoe.utils.ResourceBundleUTF8;

import javax.swing.*;

/**
 * Describe your class
 */
public interface BoardView {
    void renderBoard(int[][] board);

    String renderAssistant(int[][] board, Turn currentTurn, String bannerMessage);

    int getMoveCell(int[][] board, Turn turn, boolean invalidMove, Integer invalidPos);

    void announceResult(int gameResult, List<Integer> winningPath);

    void restartGame(List<Integer> winningPath);

    default boolean playAgain() {
        return JOptionPane.showConfirmDialog(null, getTranslations().getString(TranslationConstants.PLAY_AGAIN_LABEL), "",
                JOptionPane.YES_NO_OPTION) == 0;
    }

    ResourceBundleUTF8 getTranslations();
}
