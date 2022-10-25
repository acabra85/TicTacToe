
package com.acabra.tictactoe.model;

import com.acabra.tictactoe.view.WinnerLineNames;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BoardLogic {

    private static final int HEIGHT = 3;
    private static final int WIDTH = 3;
    private static final int IS_AVAILABLE = 0;

    private final int[][] board;
    private int availableMoves;
    private Turn activePlayer;
    private WinnerLineNames winnerLine;
    private final Map<Integer, Move> keys;

    public BoardLogic() {
        activePlayer = Turn.CIRCLE;
        availableMoves = HEIGHT * WIDTH;
        this.winnerLine = WinnerLineNames.NO_WINNER;
        board = new int[HEIGHT][WIDTH];
        int idx = 0;
        Map<Integer, Move> map = new HashMap<>();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = IS_AVAILABLE;
                map.put(++idx, new Move(i, j));
            }
        }
        this.keys = Collections.unmodifiableMap(map);
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

    public boolean move(Turn currentTurn, int moveCell) {
        Move move = this.keys.get(moveCell);
        if (null != move && isValidMove(move)) {
            board[move.i][move.j] = currentTurn.id;
            activePlayer = currentTurn;
            availableMoves--;
            return true;
        }
        System.out.println("invalid mode");
        return false;
    }

    public boolean gameActive() {
        return availableMoves > 0 && noWinner();
    }

    private boolean noWinner() {
        this.winnerLine = calculateWinnerLine();
        return winnerLine == WinnerLineNames.NO_WINNER;
    }

    private WinnerLineNames calculateWinnerLine() {
        int num = activePlayer.id;

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

    public boolean isValidMove(Move move) {
        return move != null && board[move.i][move.j] == IS_AVAILABLE;
    }

    public boolean isValidMove(int move) {
        return isValidMove(this.keys.get(move));
    }

    public int getGameResult() {
        return noWinner() ? 0 : activePlayer.id;
    }

    public Turn getCurrentTurn() {
        return activePlayer;
    }

    public Turn getNextTurn() {
        return activePlayer == Turn.CIRCLE ? Turn.CROSS : Turn.CIRCLE;
    }

    public void restartGame() {
        for (int[] row: board) {
            Arrays.fill(row, 0);
        }
        this.activePlayer = Turn.CIRCLE;
        this.availableMoves = HEIGHT * WIDTH;
        this.winnerLine = WinnerLineNames.NO_WINNER;
    }

    static class Move {
        int i;
        int j;
        Move(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
