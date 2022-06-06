
package control;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import model.BoardLogic;
import model.Turn;
import utils.ResourceBundleUTF8;
import view.BoardView;
import view.BoardViewFactory;
import view.ViewType;
import view.WinnerLineNames;

import javax.swing.*;

/**
 * Describe your class
 */
class GameController implements GameActionExecutor {

    private final BoardLogic board;
    private final ResourceBundleUTF8 bundle;
    private Turn currentTurn;
    private BoardView view;
    private final Map<WinnerLineNames, List<Integer>> winnersButtonsMap;

    GameController(Locale locale) {
        this.board = new BoardLogic();
        this.currentTurn = board.getCurrentTurn();
        this.bundle = new ResourceBundleUTF8("tictactoe_translations", locale);
        winnersButtonsMap = Collections.unmodifiableMap(new HashMap<WinnerLineNames, List<Integer>>(){{
            put(WinnerLineNames.H1, Arrays.asList(0, 1, 2));
            put(WinnerLineNames.H2, Arrays.asList(3, 4, 5));
            put(WinnerLineNames.H3, Arrays.asList(6, 7, 8));

            put(WinnerLineNames.V1, Arrays.asList(0, 3, 6));
            put(WinnerLineNames.V2, Arrays.asList(1, 4, 7));
            put(WinnerLineNames.V3, Arrays.asList(2, 5, 8));

            put(WinnerLineNames.D1, Arrays.asList(0, 4, 8));
            put(WinnerLineNames.D2, Arrays.asList(2, 4, 6));
            put(WinnerLineNames.NO_WINNER, Collections.emptyList());
        }});
    }

    void play(ViewType type, int h, int w) {
        switch (type) {
            case CONSOLE:
                playConsole(type, h, w);
                break;
            case INTERACTIVE:
                playInteractive(type, h, w);
                break;
            default:
                throw new IllegalArgumentException("Unknown view type: " + type);
        }
    }

    private void playInteractive(ViewType type, int h, int w) {
        this.view = BoardViewFactory.buildView(this, type, h, w);
    }

    public boolean reportMove(final int viewMove) {
        int desiredMove = getDesiredMoveFromView(viewMove);
        if (board.isValidMove(desiredMove)) {
            board.move(currentTurn, desiredMove);
            currentTurn = board.getNextTurn();
            return true;
        }
        return false;
    }

    private int getDesiredMoveFromView(final int viewMove) {
        int available = 0;
        int indexOnGrid = 0;
        for (int row[] : board.get()) {
            for (int i = 0; i < row.length; i++, indexOnGrid++) {
                if (row[i] == 0) {
                    available++;
                }
                if (viewMove == indexOnGrid) {
                    return available;
                }
            }
        }
        return -1;
    }

    private void playConsole(ViewType type, int h, int w) {
        do {
            view = BoardViewFactory.buildView(this, type, h, w);
            while(board.gameActive()) {
                int[][] cBoard = board.get();
                view.renderBoard(cBoard);
                int desiredMove = view.getMoveCell(cBoard, currentTurn, false, -1);
                while (!board.isValidMove(desiredMove)) {
                    System.out.println("invalid move " + desiredMove);
                    desiredMove = view.getMoveCell(cBoard, currentTurn, true, desiredMove);
                }
                board.move(currentTurn, desiredMove);
                currentTurn = board.getNextTurn();
            }
            view.renderBoard(board.get());
            view.announceResult(board.getGameResult(), winnersButtonsMap.get(board.getWinnerLine()));
            restartGame();
        } while(view.playAgain());
    }

    @Override
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public void finishMove() {
        if(!board.gameActive()) {
            view.announceResult(board.getGameResult(), winnersButtonsMap.get(board.getWinnerLine()));
        }
    }

    @Override
    public void restartGame() {
        WinnerLineNames winnerLine = board.getWinnerLine();
        view.restartGame(winnersButtonsMap.get(winnerLine));
        board.restartGame();
        currentTurn = board.getCurrentTurn();
    }

    @Override
    public ResourceBundleUTF8 getTranslations() {
        return bundle;
    }
}
