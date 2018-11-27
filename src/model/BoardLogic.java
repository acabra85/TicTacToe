
package model;

import view.WinnerLineNames;

/**
 * Describe your class
 */
public class BoardLogic {

    private static final int HEIGHT = 3;
    private static final int WIDTH = 3;

    private final int[][] board;
    private int availableMoves;
    private Turn activePlayer;
    private WinnerLineNames winnerLine;

    public BoardLogic() {
        activePlayer = Turn.CIRCLE;
        availableMoves = HEIGHT * WIDTH;
        this.winnerLine = WinnerLineNames.NO_WINNER;
        board = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int[][] get() {
        return copy(board);
    }

    private int[][] copy(int[][] board) {
        int[][] copy = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, WIDTH);
        }
        return copy;
    }

    public void move(Turn currentTurn, int moveCell) {
        if (isValidMove(moveCell)) {
            int pos = 1;
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (board[i][j] == 0 && pos++ == moveCell) {
                        board[i][j] = getNum(currentTurn);
                        activePlayer = currentTurn;
                        availableMoves--;
                        return;
                    }
                }
            }
        } else {
            System.out.println("invalid mode");
        }
    }

    private int getNum(Turn currentTurn) {
        return currentTurn == Turn.CIRCLE ? -1 : 1;
    }

    public boolean gameActive() {
        return availableMoves > 0 && noWinner();
    }

    private boolean noWinner() {
        this.winnerLine = calculateWinnerLine();
        return winnerLine == WinnerLineNames.NO_WINNER;
    }

    private WinnerLineNames calculateWinnerLine() {
        int num = getNum(activePlayer);

        if(board[0][0] == num && board[0][1] == num && board[0][2] == num) return WinnerLineNames.H1;
        if(board[1][0] == num && board[1][1] == num && board[1][2] == num) return WinnerLineNames.H2;
        if(board[2][0] == num && board[2][1] == num && board[2][2] == num) return WinnerLineNames.H3;

        if(board[0][0] == num && board[1][0] == num && board[2][0] == num) return WinnerLineNames.V1;
        if(board[0][1] == num && board[1][1] == num && board[2][1] == num) return WinnerLineNames.V2;
        if(board[0][2] == num && board[1][2] == num && board[2][2] == num) return WinnerLineNames.V3;

        if(board[0][0] == num && board[1][1] == num && board[2][2] == num) return WinnerLineNames.D1;
        if(board[0][2] == num && board[1][1] == num && board[2][0] == num) return WinnerLineNames.D2;

        return WinnerLineNames.NO_WINNER;
    }

    public WinnerLineNames getWinnerLine() {
        return this.winnerLine;
    }

    public boolean isValidMove(int desiredMove) {
        if (desiredMove > availableMoves || desiredMove <= 0) {
            return false;
        }
        int pos = 1;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j]==0 && pos++ == desiredMove) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getGameResult() {
        return noWinner() ? 0 : getNum(activePlayer);
    }

    public Turn getCurrentTurn() {
        return activePlayer;
    }

    public Turn getNextTurn() {
        return activePlayer == Turn.CIRCLE ? Turn.CROSS : Turn.CIRCLE;
    }

    public void restartGame() {
        for (int row[]: board) {
            for (int i = 0; i < board.length; i++) {
                row[i] = 0;
            }
        }
        this.activePlayer = Turn.CIRCLE;
        this.availableMoves = HEIGHT * WIDTH;
        this.winnerLine = WinnerLineNames.NO_WINNER;
    }
}
