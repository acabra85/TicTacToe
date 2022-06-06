
package view;

import java.util.List;
import model.Turn;

/**
 * Describe your class
 */
public interface BoardView {
    void renderBoard(int board[][]);

    String renderAssistant(int[][] board, Turn currentTurn, String bannerMessage);

    int getMoveCell(int[][] board, Turn turn, boolean invalidMove, Integer invalidPos);

    void announceResult(int gameResult, List<Integer> winningPath);

    void restartGame(List<Integer> winningPath);
}
