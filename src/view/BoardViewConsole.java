
package view;

import control.GameActionExecutor;
import java.util.List;
import javax.swing.*;
import model.Turn;
import utils.ResourceBundleUTF8;

/**
 * Describe your class
 */
public class BoardViewConsole implements BoardView {

    private final int WIDTH;
    private final int HEIGHT;
    private final ResourceBundleUTF8 translations;

    BoardViewConsole(GameActionExecutor executor, int h, int w) {
        HEIGHT = h;
        WIDTH = w;
        this.translations = executor.getTranslations();
    }

    @Override
    public void renderBoard(int board[][]) {
        StringBuilder boardStr = new StringBuilder("===BOARD====\n");
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                boardStr.append(" ")
                        .append(Symbol.getvalue(board[i][j]))
                        .append(" ");
            }
            boardStr.append("\n");
        }
        boardStr.append("============");
        System.out.println(boardStr);
    }

    @Override
    public String renderAssistant(int[][] board, Turn currentTurn) {
        int pos = 1;
        StringBuilder sb = new StringBuilder(String.format("--> Player '%s' choose your cell to play\n", currentTurn.value));
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                sb.append(" ")
                        .append(board[i][j] == 0 ? pos++ : "_")
                        .append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public int getMoveCell(int[][] board, Turn turn) {
        String message = renderAssistant(board, turn);
        String answer = null;
        boolean exception = false;
        int ansCell = -1;
        while (null == answer || exception) {
            exception = false;
            answer = JOptionPane.showInputDialog(message);
            try {
                ansCell = Integer.parseInt(answer);
            }catch (Exception e) {
                exception = true;
            }
        }
        return ansCell;
    }

    @Override
    public void announceResult(int gameResult, List<Integer> winningPath) {
        String result = "";
        if (gameResult == 0) {
            result = translations.getString(TranslationConstants.GAME_RESULT_DRAW_LABEL);
        } else {
            result = String.format(translations.getString(TranslationConstants.GAME_RESULTS_WINNER_LABEL), gameResult > 0 ? Turn.CROSS.value : Turn.CIRCLE.value);
        }
        JOptionPane.showMessageDialog(null, result);
    }

    @Override
    public void restartGame(List<Integer> winningPath) {
        System.out.println("pending restart implementation");
    }

}
