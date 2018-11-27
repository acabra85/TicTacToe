
package control;

import java.util.Map;
import model.Turn;
import utils.ResourceBundleUTF8;

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
