
package view;

import java.util.List;
import model.Turn;

/**
 * Describe your class
 */
public interface BoardView {
    void renderBoard(int board[][]);

    String renderAssistant(int[][] board, Turn currentTurn);

    int getMoveCell(int[][] board, Turn turn);

    void announceResult(int gameResult, List<Integer> winningPath);

    void restartGame(List<Integer> winningPath);
}
