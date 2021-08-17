package game.Controller;

import game.Main;
import game.data.saves.Load;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javafx.scene.input.MouseEvent;
import java.io.*;

public class LoadSaveController {
    @FXML
    private HBox grass;

    @FXML
    private Button loadSave;

    @FXML
    private Button deleteSave;

    @FXML
    private Button newGame;

    @FXML
    private Button saveOne;

    @FXML
    private Button saveTwo;

    @FXML
    private Button saveThree;

    @FXML
    private Text nameOne;

    @FXML
    private Text nameTwo;

    @FXML
    private Text nameThree;

    @FXML
    private Text moneyOne;

    @FXML
    private Text moneyTwo;

    @FXML
    private Text moneyThree;

    @FXML
    private Text dayOne;

    @FXML
    private Text dayTwo;

    @FXML
    private Text dayThree;

    @FXML
    private Text diffOne;

    @FXML
    private Text diffTwo;

    @FXML
    private Text diffThree;

    @FXML
    private ImageView imgOne;

    @FXML
    private ImageView imgTwo;

    @FXML
    private ImageView imgThree;

    @FXML
    private Text seasonOne;

    @FXML
    private Text seasonTwo;

    @FXML
    private Text seasonThree;

    @FXML
    private Text screenOne;

    @FXML
    private Text screenTwo;

    @FXML
    private Text screenThree;

    private File fileOne = new File(
            "src/Zombie-Farming-Game/data/saves/saveOne.txt");
    private File fileTwo = new File(
            "src/Zombie-Farming-Game/data/saves/saveTwo.txt");
    private File fileThree = new File(
            "src/Zombie-Farming-Game/data/saves/saveThree.txt");
    private File finalFile;
    private boolean[] savePresent = new boolean[3];

    private Load loadOne;
    private Load loadTwo;
    private Load loadThree;
    private Load finalLoad;
    private int loadIndex;

    private File finalImage;

    private final String[] farmPaths = {
        "src/Zombie-Farming-Game/data/saves/imgOne.png",
        "src/Zombie-Farming-Game/data/saves/imgTwo.png",
        "src/Zombie-Farming-Game/data/saves/imgThree.png"
    };

    private final String[] localFarmPaths = {
        "../data/saves/imgOne.png",
        "../data/saves/imgTwo.png",
        "../data/saves/imgThree.png"
    };

    private String modeType = null;
    private String fileSelected = null;

    @FXML
    private void initialize() throws IOException {
        BufferedReader buffReader;

        fileOne.createNewFile();
        fileTwo.createNewFile();
        fileThree.createNewFile();

        new File(farmPaths[0]).createNewFile();
        new File(farmPaths[1]).createNewFile();
        new File(farmPaths[2]).createNewFile();

        buffReader = new BufferedReader(new FileReader(fileOne));
        if (buffReader.readLine() != null) {
            buffReader.close();
            loadOne = loadFileInfo(
                    fileOne, imgOne, localFarmPaths[0]);
            setUpTextBoxes(
                    loadOne, nameOne, moneyOne,
                    dayOne, diffOne, seasonOne, screenOne);
            savePresent[0] = true;
            grass.setTranslateX(13.0);
        }
        buffReader.close();

        buffReader = new BufferedReader(new FileReader(fileTwo));
        if (buffReader.readLine() != null) {
            buffReader.close();
            loadTwo = loadFileInfo(
                    fileTwo, imgTwo, localFarmPaths[1]);
            setUpTextBoxes(
                    loadTwo, nameTwo, moneyTwo,
                    dayTwo, diffTwo, seasonTwo, screenTwo);
            savePresent[1] = true;
            grass.setTranslateX(13.0);
        }
        buffReader.close();

        buffReader = new BufferedReader(new FileReader(fileThree));
        if (buffReader.readLine() != null) {
            buffReader.close();
            loadThree = loadFileInfo(
                    fileThree, imgThree, localFarmPaths[2]);
            setUpTextBoxes(
                    loadThree, nameThree, moneyThree,
                    dayThree, diffThree, seasonThree, screenThree);
            savePresent[2] = true;
            grass.setTranslateX(13.0);
        }
        buffReader.close();
    }

    /**
     * Constructs a Load object for each save file in the game, setting the appropriate
     * general information contained within the save inside of the UI
     *
     * @param file is the save file
     * @param view is the image container to hold the picture of the current state of the
     *             farm in the save file
     * @param imagePath is the path to the image where the save file screenshot is located
     * @return the Load object associated with the specified save file
     * @throws IOException if there is a problem parsing or locating the save file
     */
    private Load loadFileInfo(
            File file,
            ImageView view,
            String imagePath) throws IOException {
        Load load = new Load(file);
        view.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        view.setFitHeight(142.0);
        view.setPreserveRatio(true);

        return load;
    }

    private void setUpTextBoxes(
            Load load,
            Text name,
            Text money,
            Text day,
            Text difficulty,
            Text season,
            Text screen) {
        name.setText("Name: " + load.getName());
        money.setText("" + load.getMoney());
        day.setText("Day: " + load.getDay());
        difficulty.setText("Difficulty: " + load.getDifficulty());
        season.setText("Season: " + load.getCurrentSeason());
        screen.setText("Screen: " + load.getScreen());
    }

    /**
     * Transitions between the modes after being clicked on (load, new game, delete),
     * changing the border of the selected mode
     *
     * @param clickedButton is the selected mode's button
     */
    @FXML
    private void selectMode(MouseEvent clickedButton) {
        Button modeButton = (Button) clickedButton.getSource();
        modeType = modeButton.getId();

        loadSave.setStyle("-fx-effect: none;");
        newGame.setStyle("-fx-effect: none;");
        modeButton.setStyle("-fx-effect: dropshadow(three-pass-box, green, 10, 0, 0, 0);");
    }

    @FXML
    private void goLoad() throws IOException {
        if (fileSelected == null) {
            new Alert(
                    Alert.AlertType.ERROR, "You must select a file to load from!")
                    .show();
        } else {
            Main.setSaveFile(finalFile);
            Main.setSaveImage(finalImage);

            if (finalLoad != null) {
                finalLoad.configureLoad();

                if (finalLoad.getScreen().equals("farm")) {
                    Main.setLoadFlag(true);
                    Main.getFarm();
                } else {
                    Main.getMarket();
                }
            } else {
                new Alert(
                        Alert.AlertType.ERROR, "There is no save in the selected file!")
                        .show();
            }
        }
    }

    @FXML
    private void newGame() throws IOException {
        if (fileSelected == null) {
            if (loadOne != null && loadTwo != null && loadThree != null) {
                new Alert(
                        Alert.AlertType.ERROR,
                        "There are no more empty save files! Choose one to overwrite!")
                        .show();
            } else {
                Alert successAlert;

                if (loadOne == null) {
                    finalFile = fileOne;
                    finalImage = new File(farmPaths[0]);

                    successAlert = new Alert(Alert.AlertType.INFORMATION, "Saving to file 1!");
                } else if (loadTwo == null) {
                    finalFile = fileTwo;
                    finalImage = new File(farmPaths[1]);

                    successAlert = new Alert(Alert.AlertType.INFORMATION, "Saving to file 2!");
                } else {
                    finalFile = fileThree;
                    finalImage = new File(farmPaths[2]);

                    successAlert = new Alert(Alert.AlertType.INFORMATION, "Saving to file 3!");
                }

                Main.setSaveFile(finalFile);
                Main.setSaveImage(finalImage);

                Main.setInit(true);

                successAlert.show();

                successAlert.setOnCloseRequest(e -> {
                    try {
                        Main.getConfig();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        } else {
            if (loadIndex == 0 && loadOne != null) {
                loadOne.clear();
            } else if (loadIndex == 1 && loadTwo != null) {
                loadTwo.clear();
            } else if (loadThree != null) {
                loadThree.clear();
            }

            Main.setSaveFile(finalFile);
            Main.setSaveImage(finalImage);
            Main.setInit(true);

            Main.getConfig();
        }
    }

    /**
     * Handles the selecting of a file, darkening its border and setting the global selected file
     * information.
     *
     * @param clickedButton is the button of the selected file
     */
    @FXML
    private void selectFile(MouseEvent clickedButton) {
        Button fileButton = (Button) clickedButton.getSource();
        fileSelected = fileButton.getId();
        String boldStyle = "";
        int fileIndex;

        if (fileSelected.equals("saveOne")) {
            fileIndex = 0;
            boldStyle = selectFileHelper(
                    loadOne, fileOne, new File(farmPaths[0]), 0, "yellow", "gold");
        } else if (fileSelected.equals("saveTwo")) {
            fileIndex = 1;
            boldStyle = selectFileHelper(
                    loadTwo, fileTwo, new File(farmPaths[1]), 1, "orange", "darkorange");
        } else {
            fileIndex = 2;
            boldStyle = selectFileHelper(
                    loadThree, fileThree, new File(farmPaths[2]), 2, "lime", "limegreen");
        }

        saveOne.setStyle(
                "-fx-border-width: 2; -fx-background-color: yellow; -fx-border-color: gold;");
        saveTwo.setStyle(
                "-fx-border-width: 2; -fx-background-color: orange; -fx-border-color: darkorange;");
        saveThree.setStyle(
                "-fx-border-width: 2; -fx-background-color: lime; -fx-border-color: limegreen;");
        fileButton.setStyle(boldStyle);

        if (savePresent[fileIndex]) {
            deleteSave.setText("Delete Save " + (fileIndex + 1));
            deleteSave.setVisible(true);
        } else {
            deleteSave.setVisible(false);
        }
    }

    /**
     * Helper method to set the current file information after its button has been clicked
     *
     * @param load is the load associated with the file
     * @param file is the physical file of this selected file
     * @param image is the physical screenshot associated with this file
     * @param index is the file index of this file (save 1 -> 0, save 2 -> 1, save 3 -> 2)
     * @param fill is the fill color of the selected file
     * @param border is the border color of the selected file
     * @return the thickened border style for the selected file
     */
    private String selectFileHelper(
            Load load,
            File file,
            File image,
            int index,
            String fill,
            String border) {
        finalLoad = load;
        finalFile = file;
        finalImage = image;
        loadIndex = index;

        return String.format(
                "-fx-border-width: 10; -fx-background-color: %s; -fx-border-color: %s;",
                fill, border);
    }

    /**
     * Handles the deletion of a selected save file
     *
     * @throws IOException if the selected file cannot be found
     */
    @FXML
    private void deleteSave() throws IOException {
        if (finalLoad == null) {
            new Alert(Alert.AlertType.INFORMATION, "DELETE ** 2 is illegal!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "You have chosen to delete a save!").show();
            finalLoad.clear();
            finalLoad = null;

            if (loadIndex == 0) {
                loadOne = null;
                deleteHelper(
                        imgOne, nameOne, moneyOne, dayOne,
                        diffOne, seasonOne, screenOne);
            } else if (loadIndex == 1) {
                loadTwo = null;
                deleteHelper(
                        imgTwo, nameTwo, moneyTwo, dayTwo,
                        diffTwo, seasonTwo, screenTwo);
            } else {
                loadThree = null;
                deleteHelper(
                        imgThree, nameThree, moneyThree, dayThree,
                        diffThree, seasonThree, screenThree);
            }
        }
    }

    /**
     * Helper method for the deletion of a file. Resets all UI displays containing save
     * file information to their default values
     *
     * @param view is the image container for the deleted file
     * @param name is the box holding the name of the deleted file
     * @param money is the box holding the money of the deleted file
     * @param day is the box holding the day of the deleted file
     * @param difficulty is the box holding the difficulty of the deleted file
     * @param season is the box holding the season of the deleted file
     * @param screen is the box holding the screen of the deleted file
     */
    private void deleteHelper(
            ImageView view,
            Text name,
            Text money,
            Text day,
            Text difficulty,
            Text season,
            Text screen) {
        view.setImage(
                new Image(
                        getClass()
                                .getResourceAsStream("./../Images/NetFound/emptyFarm.png")));
        name.setText("Name:");
        money.setText("");
        day.setText("Day: 0");
        difficulty.setText("Difficulty: recruit");
        season.setText("Season:");
        screen.setText("Screen: dis one");
    }

    /**
     * Transitions to the next screen based on the final clicked mode: config screen
     * if the player wants to begin a new game (after selecting a file to save to) or
     * the farm/market screen based on the last saved screen contained within the save file
     *
     * @throws IOException if the selected file cannot be found
     */
    @FXML
    private void nextScreen() throws IOException {
        if (modeType == null) {
            new Alert(
                    Alert.AlertType.ERROR,
                    "You must choose to load a saved game or start a new one!")
                    .show();
        } else if (fileSelected == null) {
            if (modeType.equals("loadSave")) {
                new Alert(
                        Alert.AlertType.ERROR, "You must select a file to load from!")
                        .show();
            } else {
                new Alert(
                        Alert.AlertType.ERROR, "You must a select a file to save to!")
                        .show();
            }
        } else {
            Main.setSaveFile(finalFile);
            Main.setSaveImage(finalImage);

            if (modeType.equals("loadSave")) {
                if (finalLoad != null) {
                    finalLoad.configureLoad();

                    if (finalLoad.getScreen().equals("farm")) {
                        Main.setLoadFlag(true);
                        Main.getFarm();
                    } else {
                        Main.getMarket();
                    }
                } else {
                    new Alert(
                            Alert.AlertType.ERROR, "There is no save in the selected file!")
                            .show();
                }
            } else if (modeType.equals("newGame")) {
                Main.getConfig();
            } else {
                new Alert(
                        Alert.AlertType.ERROR, "You can't delete the game itself!")
                        .show();
            }
        }
    }

    @FXML
    private void goSettings() throws IOException {
        Main.getSettings();
    }
}
