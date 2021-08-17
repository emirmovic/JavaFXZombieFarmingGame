package game.Controller;

import game.Main;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class WelcomeScreenController {
    @FXML
    private Text deadText;

    @FXML
    private void initialize() {
        RotateTransition rt = new RotateTransition(Duration.millis(3000), deadText);
        rt.setFromAngle(-10.0);
        rt.setByAngle(20);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setAutoReverse(true);

        rt.play();
    }

    /**
     * Transitions the game to the load/save screen
     *
     * @throws IOException if the load/save screen fxml cannot be found
     */
    @FXML
    private void goLoadSave() throws IOException {
        Main.getLoadSave();
    }

    @FXML
    private void goSettings() throws IOException {
        Main.getSettings();
    }

    @FXML
    private void goCredits() throws IOException {
        Main.getCredits();
    }
}
