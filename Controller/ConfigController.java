package game.Controller;

import game.Main;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class ConfigController {
    @FXML
    private TextField nameBox;

    @FXML
    private ToggleGroup difficultyRadios;

    @FXML
    private ToggleGroup seedRadios;

    @FXML
    private ToggleGroup seasonRadios;

    private String name = "";

    private RadioButton selectedDifficulty = null;
    private RadioButton selectedSeed = null;
    private RadioButton selectedSeason = null;

    private static final Paint NO_COLOR = Paint.valueOf("#000000"); // black
    private static final Paint BEGINNER_COLOR = Paint.valueOf("#0000FF"); // blue
    private static final Paint INFERNO_COLOR = Paint.valueOf("#FF0000"); // red
    private static final Paint DOOMSDAY_COLOR = Paint.valueOf("#FFFFFF"); // white
    private static final Paint CORN_COLOR = Paint.valueOf("#E0E000"); // yellow
    private static final Paint CARROT_COLOR = Paint.valueOf("#E07000"); // orange
    private static final Paint BROCCOLI_COLOR = Paint.valueOf("#008718"); // green
    private static final Paint SPRING_COLOR = Paint.valueOf("#008718"); // green
    private static final Paint SUMMER_COLOR = Paint.valueOf("#FF0000"); // red
    private static final Paint FALL_COLOR = Paint.valueOf("#E07000"); // orange
    private static final Paint WINTER_COLOR = Paint.valueOf("#FFFFFF"); // white
    private static final InnerShadow INNER_SHADOW = new InnerShadow(5.0, Color.valueOf("black"));

    @FXML
    /**
     * Sets the default selected difficulty, starting seed, and season color
     * and values to send back to Main
     */
    private void initialize() {
        nameBox.setText("Hello \"Comrade\"");
        name = "Comrade";

        selectedDifficulty = (RadioButton) difficultyRadios.getSelectedToggle();
        selectedSeed = (RadioButton) seedRadios.getSelectedToggle();
        selectedSeason = (RadioButton) seasonRadios.getSelectedToggle();

        setColor(0, BEGINNER_COLOR);
        setColor(1, CORN_COLOR);
        setColor(2, SPRING_COLOR);
    }

    @FXML
    /**
     * Evaluates the player's entered name and alters it if need be.
     * Player cannot have a name that is only " ", nor can they have an empty name
     * or a name that is too long (>20 characters).
     */
    private void changeName() {
        String enteredName = nameBox.getText();

        if (enteredName.equals("")) {
            nameBox.setText("Too empty \"Comrade\"");
            name = "Comrade";
        } else if (enteredName.length() >= 20) {
            nameBox.setText("Too long \"Comrade\"");
            name = "Comrade";
        } else {
            boolean allBlank = true;

            for (char letter : enteredName.toCharArray()) {
                if (letter != ' ') {
                    allBlank = false;
                    break;
                }
            }

            if (allBlank) {
                nameBox.setText("Too blank \"Comrade\"");
                name = "Comrade";
            } else {
                name = enteredName;
            }
        }

        System.out.println(name);
    }

    @FXML
    /**
     * Sets the selected difficulty's color and stores the selection
     */
    private void setDifficulty() {
        selectedDifficulty = (RadioButton) difficultyRadios.getSelectedToggle();
        String difficultyId = selectedDifficulty.getId();

        if (difficultyId.equals("beginner")) {
            setColor(0, BEGINNER_COLOR);
        } else if (difficultyId.equals("inferno")) {
            setColor(0, INFERNO_COLOR);
        } else {
            setColor(0, DOOMSDAY_COLOR);
        }
    }

    @FXML
    /**
     * Sets the selected seed's color and stores the selection
     */
    private void setStartingSeed() {
        selectedSeed = (RadioButton) seedRadios.getSelectedToggle();
        String seedId = selectedSeed.getId();

        if (seedId.equals("corn")) {
            setColor(1, CORN_COLOR);
        } else if (seedId.equals("carrot")) {
            setColor(1, CARROT_COLOR);
        } else {
            setColor(1, BROCCOLI_COLOR);
        }
    }

    @FXML
    /**
     * Sets the selected season's color and stores the selection
     */
    private void setSeason() {
        selectedSeason = (RadioButton) seasonRadios.getSelectedToggle();
        String seasonId = selectedSeason.getId();

        if (seasonId.equals("spring")) {
            setColor(2, SPRING_COLOR);
        } else if (seasonId.equals("summer")) {
            setColor(2, SUMMER_COLOR);
        } else if (seasonId.equals("fall")) {
            setColor(2, FALL_COLOR);
        } else {
            setColor(2, WINTER_COLOR);
        }
    }

    /**
     * Sets the color of all selection options based on what is currently selected
     * @param radioGroup is the selection group being changed (difficulty, starting seed, season)
     * @param color is the color that the selected option will be changed to
     */
    private void setColor(int radioGroup, Paint color) {
        if (radioGroup == 0) {
            for (Toggle button : difficultyRadios.getToggles()) {
                ((RadioButton) button).setTextFill(NO_COLOR);
            }

            selectedDifficulty.setTextFill(color);
            selectedDifficulty.setEffect(INNER_SHADOW);
        } else if (radioGroup == 1) {
            for (Toggle button : seedRadios.getToggles()) {
                ((RadioButton) button).setTextFill(NO_COLOR);
            }

            selectedSeed.setTextFill(color);
            selectedSeed.setEffect(INNER_SHADOW);
        } else {
            for (Toggle button : seasonRadios.getToggles()) {
                ((RadioButton) button).setTextFill(NO_COLOR);
            }

            selectedSeason.setTextFill(color);
            selectedSeason.setEffect(INNER_SHADOW);
        }
    }

    @FXML
    /**
     * Enters the farm for the very first time after choosing configurations
     * @throws IOException if fxml file not found
     */
    private void goFarm() throws IOException {
        double cash = 10000;
        if (selectedDifficulty.getId().equals("doomsday")) {
            cash = 0;
        } else if (selectedDifficulty.getId().equals("inferno")) {
            cash = 5000;
        }

        System.out.println(name);

        Main.setCash(cash);
        Main.setName(name);

        String gameDifficulty = selectedDifficulty.getId();
        Main.setDifficulty(gameDifficulty);
        Main.setStartingSeed(selectedSeed.getId());

        switch (selectedSeason.getId()) {
        case "spring" :
            Main.setSeasonIndex(0);
            break;
        case "summer" :
            Main.setSeasonIndex(1);
            break;
        case "fall" :
            Main.setSeasonIndex(2);
            break;
        case "winter" :
            Main.setSeasonIndex(3);
            break;
        default :
            break;
        }

        Main.setCurrSeason(selectedSeason.getId());

        Main.getFarm();
    }

    @FXML
    private void goSettings() throws IOException {
        Main.getSettings();
    }
}
