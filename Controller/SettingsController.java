package game.Controller;

import game.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class SettingsController {
    @FXML
    private ToggleButton zAnim;

    @FXML
    private ToggleButton cAnim;

    @FXML
    private ToggleButton fAnim;

    @FXML
    private ToggleButton mSound;

    @FXML
    private ToggleButton sSound;

    private boolean[] animationToggles = Main.getAnimationToggles();
    private boolean[] soundToggles = Main.getSoundToggles();

    @FXML
    private void initialize() {
        zAnim.setSelected(animationToggles[0]);
        cAnim.setSelected(animationToggles[1]);
        fAnim.setSelected(animationToggles[2]);

        mSound.setSelected(soundToggles[0]);
        sSound.setSelected(soundToggles[1]);
    }

    @FXML
    private void triggerToggle(MouseEvent mouseEvent) {
        ToggleButton toggle = (ToggleButton) mouseEvent.getSource();

        switch (toggle.getId()) {
        case "zAnim":
            if (zAnim.isSelected()) {
                zAnim.setText("On");
                animationToggles[0] = true;
            } else {
                zAnim.setText("Off");
                animationToggles[0] = false;
            }
            break;
        case "cAnim" :
            if (cAnim.isSelected()) {
                cAnim.setText("On");

                if (!animationToggles[1]) {
                    Main.cycleClouds();
                }

                animationToggles[1] = true;
            } else {
                cAnim.setText("Off");
                animationToggles[1] = false;

                Main.stopClouds();
            }
            break;
        case "fAnim" :
            if (fAnim.isSelected()) {
                fAnim.setText("On");
                animationToggles[2] = true;
            } else {
                fAnim.setText("Off");
                animationToggles[2] = false;
            }
            break;
        case "mSound" :
            MediaPlayer player;
            if (Main.getBackScreen().equals("market")) {
                player = Main.getElevatorPlayer();
            } else {
                player = Main.getAnthemPlayer();
            }

            if (mSound.isSelected()) {
                mSound.setText("On");
                soundToggles[0] = true;

                if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                    player.play();
                }
            } else {
                mSound.setText("Off");
                soundToggles[0] = false;

                player.pause();
            }
            break;
        case "sSound" :
            if (sSound.isSelected()) {
                sSound.setText("On");
                soundToggles[1] = true;
            } else {
                sSound.setText("Off");
                soundToggles[1] = false;
            }
            break;
        default :
            break;
        }
    }

    @FXML
    private void goBack() throws IOException {
        String backScreen = Main.getBackScreen();

        switch (backScreen) {
        case "load" :
            Main.getLoadSave();
            break;
        case "config" :
            Main.getConfig();
            break;
        case "farm" :
            Main.setLoadFlag(true);
            Main.getFarm();
            break;
        case "market" :
            Main.getMarket();
            break;
        case "welcome" :
            Main.getSecondaryWelcome();
            break;
        default :
            break;
        }
    }
}
