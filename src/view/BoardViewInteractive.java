
package view;

import control.GameActionExecutor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Turn;
import utils.ResourceBundleUTF8;

/**
 * This class represents a board that responds to user clicks
 * is the tictactoe visible board.
 */
public class BoardViewInteractive extends JFrame implements BoardView {

    private static final Color WINNER_COLOR = Color.green;
    private final int boardHeight;
    private final int boardWidth;
    private final GameActionExecutor executor;
    private final JButton restartButton;
    private final ResourceBundleUTF8 translations;
    private List<JButton> actionButtons;
    private final JLabel resultsLabel = new JLabel("");

    BoardViewInteractive(GameActionExecutor executor, int height, int width) {
        this.translations = executor.getTranslations();
        this.boardHeight = height;
        this.boardWidth = width;
        this.executor = executor;
        this.restartButton = new JButton(translations.getString(TranslationConstants.RESTART_GAME_LABEL));
        build();
    }

    private void build() {
        setVisible(false);
        setResizable(false);
        setDefaultLookAndFeelDecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setSize(500, 500);
        getContentPane().setLayout(new BorderLayout());
        add(buildTitlePanel(), BorderLayout.PAGE_START);
        add(buildMainPanel(), BorderLayout.CENTER);
        add(buildResultsPanel(), BorderLayout.PAGE_END);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel buildTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(new JLabel(translations.getString(TranslationConstants.GAME_TITLE_LABEL)));
        return titlePanel;
    }

    private JPanel buildResultsPanel() {
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(resultsLabel, BorderLayout.CENTER);
        this.restartButton.addActionListener(e -> {
            executor.restartGame();
        });
        resultsPanel.add(this.restartButton, BorderLayout.PAGE_END);
        return resultsPanel;
    }

    private JPanel buildMainPanel() {
        JPanel gamePanel = new JPanel(new GridLayout(boardHeight, boardWidth, 3, 3));
        //game
        ActionListener actionListener = getActionListener();
        actionButtons = buildActionButtons(actionListener);

        actionButtons.forEach(gamePanel::add);

        return gamePanel;
    }

    private ActionListener getActionListener() {
        return (event) -> {
            String actionCommand = event.getActionCommand();
            int desiredMove = Integer.parseInt(actionCommand);
            reportMove(desiredMove);
        };
    }

    private void reportMove(int desiredMove) {
        String currentTurnLabel = executor.getCurrentTurn().value;
        if (executor.reportMove(desiredMove)) {
            JButton source = actionButtons.get(desiredMove);
            source.setEnabled(false);
            source.setText(currentTurnLabel);
        }
        executor.finishMove();
    }

    private List<JButton> buildActionButtons(ActionListener actionListener) {
        List<JButton> buttons = new ArrayList<>(boardHeight * boardWidth);
        for (int i = 0; i < boardHeight * boardWidth; i++) {
            JButton but = new JButton(translations.getString(TranslationConstants.EMPTY_LABEL));
            but.setActionCommand(""+i);
            but.addActionListener(actionListener);
            buttons.add(but);
        }
        return buttons;
    }

    @Override
    public void renderBoard(int[][] board) {

    }

    @Override
    public String renderAssistant(int[][] board, Turn currentTurn, String messageBanner) {
        return null;
    }

    @Override
    public int getMoveCell(int[][] board, Turn turn, boolean invalidMove, Integer invalidPos) {
        return 0;
    }

    @Override
    public void announceResult(int gameResult, List<Integer> winningPath) {
        disableGameActions();
        String result = "";
        if (gameResult == 0) {
            result = translations.getString(TranslationConstants.GAME_RESULT_DRAW_LABEL);
        } else {
            winningPath.forEach(e -> {
                JButton jButton = actionButtons.get(e);
                jButton.setBackground(WINNER_COLOR);
                jButton.setOpaque(true);
            });
            result = String.format(translations.getString(TranslationConstants.GAME_RESULTS_WINNER_LABEL), gameResult > 0 ? Turn.CROSS.value : Turn.CIRCLE.value);
        }
        resultsLabel.setText(result);
    }

    @Override
    public void restartGame(List<Integer> winningPath) {
        actionButtons.forEach(e -> {
            e.setText(translations.getString(TranslationConstants.EMPTY_LABEL));
            e.setEnabled(true);
        });
        resultsLabel.setText("");
        winningPath.forEach(e -> {
            JButton jButton = actionButtons.get(e);
            jButton.setBackground(restartButton.getBackground());
            jButton.setOpaque(false);
        });
    }

    private void disableGameActions() {
        actionButtons.forEach(e-> e.setEnabled(false));
    }

    @Override
    public ResourceBundleUTF8 getTranslations() {
        return translations;
    }
}
