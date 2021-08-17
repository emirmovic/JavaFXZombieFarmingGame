/*package Designs;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class ConfigScreenTest extends ApplicationTest {

    private static String[] difficulty = {""};
    private static String[] currSeason = {""};
    private static String[] name = {""};
    private static String[] startingSeed = {""};

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DEAD HARVEST");

        // Scene is set with BorderPane
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, 1500, 900);

        // Name:
        Text nameText = new Text("Name: ");
        nameText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 40));

        // Text field after "Name:"
        TextField textField = new TextField("Hello \"Comrade\"");
        textField.setMinSize(250, 70);

        // Sets the user's name to "Comrade" if they enter a blank name,
        // a name >=20 characters, or a name with only spaces
        textField.setOnMouseExited(e -> {
            String enteredName = textField.getText();

            if (enteredName.equals("")) {
                textField.setText("Too empty \"Comrade\"");
                name[0] = "Comrade";
            } else if (enteredName.length() >= 20) {
                textField.setText("Too long \"Comrade\"");
                name[0] = "Comrade";
            } else {
                boolean allBlank = true;

                for (char letter : enteredName.toCharArray()) {
                    if (letter != ' ') {
                        allBlank = false;
                        break;
                    }
                }

                if (allBlank) {
                    textField.setText("Too blank \"Comrade\"");
                    name[0] = "Comrade";
                } else {
                    name[0] = textField.getText();
                }
            }
        });

        // HBox with name on left and text field on right
        HBox textHB = new HBox();
        textHB.getChildren().addAll(nameText, textField);
        textHB.setSpacing(10);
        textHB.setAlignment(Pos.CENTER);

        // Difficulty:
        Text diff = new Text("Difficulty: ");
        diff.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 40));

        // List of different difficulties to choose from
        ListView<Label> listViewDiff = new ListView<>();
        listViewDiff.setOrientation(Orientation.HORIZONTAL);
        Label beginner = new Label("Beginner");
        Label inferno = new Label("Inferno");
        Label doomsday = new Label("Doomsday");
        beginner.setTextFill(Color.BLUE);
        beginner.setStyle("-fx-font-size: 15;");
        inferno.setStyle("-fx-font-size: 15;");
        doomsday.setStyle("-fx-font-size: 15;");
        listViewDiff.getItems().add(beginner);
        listViewDiff.getItems().add(inferno);
        listViewDiff.getItems().add(doomsday);
        listViewDiff.setMaxSize(219, 85);

        // Assert selected difficulty's color and variable setting
        beginner.setOnMouseClicked(e -> {
            inferno.setTextFill(Color.BLACK);
            doomsday.setTextFill(Color.BLACK);

            difficulty[0] = "beginner";
            beginner.setTextFill(Color.BLUE);
        });

        inferno.setOnMouseClicked(e -> {
            beginner.setTextFill(Color.BLACK);
            doomsday.setTextFill(Color.BLACK);

            difficulty[0] = "inferno";
            inferno.setTextFill(Color.BLUE);
        });

        doomsday.setOnMouseClicked(e -> {
            beginner.setTextFill(Color.BLACK);
            inferno.setTextFill(Color.BLACK);

            difficulty[0] = "doomsday";
            doomsday.setTextFill(Color.BLUE);
        });

        // HBox with "Difficulty:" on left and list of difficulties on right
        HBox diffList = new HBox();
        diffList.setAlignment(Pos.CENTER);
        diffList.getChildren().addAll(diff, listViewDiff);

        // Starting Seed:
        Text seed = new Text("Starting Seed: ");
        seed.setFont(Font.font("verdana", FontWeight.BOLD,
                FontPosture.ITALIC, 40));

        // List of different starting seeds to choose from
        ListView<Label> listViewSeed = new ListView<>();
        listViewSeed.setOrientation(Orientation.HORIZONTAL);
        Label corn = new Label("Corn");
        Label carrot = new Label("Carrot");
        Label broccoli = new Label("Broccoli");
        corn.setTextFill(Color.ORANGE);
        corn.setStyle("-fx-font-size: 15;");
        carrot.setStyle("-fx-font-size: 15;");
        broccoli.setStyle("-fx-font-size: 15;");
        listViewSeed.getItems().add(corn);
        listViewSeed.getItems().add(carrot);
        listViewSeed.getItems().add(broccoli);
        listViewSeed.setMaxSize(172, 85);

        // Assert selected starting seed's color and variable setting
        corn.setOnMouseClicked(e -> {
            carrot.setTextFill(Color.BLACK);
            broccoli.setTextFill(Color.BLACK);

            startingSeed[0] = "corn";
            corn.setTextFill(Color.ORANGE);
        });

        carrot.setOnMouseClicked(e -> {
            corn.setTextFill(Color.BLACK);
            broccoli.setTextFill(Color.BLACK);

            startingSeed[0] = "carrot";
            carrot.setTextFill(Color.ORANGE);
        });

        broccoli.setOnMouseClicked(e -> {
            corn.setTextFill(Color.BLACK);
            carrot.setTextFill(Color.BLACK);

            startingSeed[0] = "broccoli";
            broccoli.setTextFill(Color.ORANGE);
        });

        // HBox with "Starting Seed:" on left and list of starting seeds on right
        HBox seedList = new HBox();
        seedList.setAlignment(Pos.CENTER);
        seedList.getChildren().addAll(seed, listViewSeed);

        //Starting Season:
        Text season = new Text("Starting Season: ");
        season.setFont(Font.font("verdana", FontWeight.BOLD,
                FontPosture.ITALIC, 40));

        // List of different seasons to choose from
        ListView<Label> listViewSeason = new ListView<>();
        listViewSeason.setOrientation(Orientation.HORIZONTAL);
        Label spring = new Label("Spring");
        Label summer = new Label("Summer");
        Label fall = new Label("Fall");
        Label winter = new Label("Winter");
        spring.setTextFill(Color.GREEN);
        spring.setStyle("-fx-font-size: 15;");
        summer.setStyle("-fx-font-size: 15;");
        fall.setStyle("-fx-font-size: 15;");
        winter.setStyle("-fx-font-size: 15;");
        listViewSeason.getItems().add(spring);
        listViewSeason.getItems().add(summer);
        listViewSeason.getItems().add(fall);
        listViewSeason.getItems().add(winter);
        listViewSeason.setMaxSize(224, 85);

        // Assert selected difficulty's color and variable setting
        spring.setOnMouseClicked(e -> {
            summer.setTextFill(Color.BLACK);
            fall.setTextFill(Color.BLACK);
            winter.setTextFill(Color.BLACK);

            currSeason[0] = "spring";
            spring.setTextFill(Color.GREEN);
        });

        summer.setOnMouseClicked(e -> {
            spring.setTextFill(Color.BLACK);
            fall.setTextFill(Color.BLACK);
            winter.setTextFill(Color.BLACK);

            currSeason[0] = "summer";
            summer.setTextFill(Color.GREEN);
        });

        fall.setOnMouseClicked(e -> {
            spring.setTextFill(Color.BLACK);
            summer.setTextFill(Color.BLACK);
            winter.setTextFill(Color.BLACK);

            currSeason[0] = "fall";
            fall.setTextFill(Color.GREEN);
        });

        winter.setOnMouseClicked(e -> {
            spring.setTextFill(Color.BLACK);
            summer.setTextFill(Color.BLACK);
            winter.setTextFill(Color.BLACK);

            currSeason[0] = "winter";
            winter.setTextFill(Color.GREEN);
        });

        // HBox with "Starting Season:" on left and list of seasons on right
        HBox seasonList = new HBox();
        seasonList.setAlignment(Pos.CENTER);
        seasonList.getChildren().addAll(season, listViewSeason);

        // Start button
        Button startButt = new Button("Start");
        DropShadow shadow = new DropShadow();
        startButt.setEffect(shadow);
        startButt.setMinSize(70, 50);
        startButt.setStyle("-fx-font-size: 35;" + "-fx-background-radius: 5em;");

        // VBox with each element above stacked above another
        VBox vbox = new VBox();
        vbox.setSpacing(50);

        // VBox placed into center of BorderPane
        border.setCenter(vbox);

        // Start button placed into bottom of BorderPane
        border.setBottom(startButt);
        vbox.getChildren().add(textHB);
        vbox.getChildren().add(diffList);
        vbox.getChildren().add(seedList);
        vbox.getChildren().add(seasonList);
        vbox.getChildren().add(startButt);
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                CornerRadii.EMPTY, Insets.EMPTY)));

        primaryStage.setScene(scene);
        primaryStage.show();

        //RESULT: VBox in the center of a BorderPane where each item in VBox is in a HBox

        // Switch from config screen to in-game screen after start button is clicked
    }

    @Test
    public void testNameTextExists() {
        verifyThat("#nameText", hasText("Name: "));
    }

    @Test
    public void testNameTextFieldExists() {
        verifyThat("#nameTextField", hasText("Hello \"Comrade\""));
    }

    @Test
    public void testNameTextFieldEdit() {
        verifyThat("#nameTextField", hasText("Hello \"Comrade\""));
        write("Nicolas");
        verifyThat("#nameTextField", hasText("Nicolas"));
    }

    @Test
    public void testDifficultyLabelsExist() {
        clickOn("#beginnerDiff");
        verifyThat("#beginnerDiff", hasText("Beginner"));
        clickOn("#infernoDiff");
        verifyThat("#infernoDiff", hasText("Inferno"));
        clickOn("#doomsdayDiff");
        verifyThat("#doomsdayDiff", hasText("Doomsday"));
    }

    @Test
    public void testCropsLabelsExist() {
        clickOn("#cornLabel");
        verifyThat("#cornLabel", hasText("Corn"));
        clickOn("#carrotLabel");
        verifyThat("#carrotLabel", hasText("Carrot"));
        clickOn("#broccoliLabel");
        verifyThat("#broccoliLabel", hasText("Broccoli"));
    }

    @Test
    public void testSeasonLabelsExist() {
        clickOn("#seasonSpring");
        verifyThat("#seasonSpring", hasText("Spring"));
        clickOn("#seasonSummer");
        verifyThat("#seasonSummer", hasText("Summer"));
        clickOn("#seasonFall");
        verifyThat("#seasonFall", hasText("Fall"));
        clickOn("#seasonWinter");
        verifyThat("#seasonWinter", hasText("Winter"));
    }

    @Test
    public void testStartButtonExists() {
        verifyThat("#startButton", hasText("Start"));
    }
}
 */