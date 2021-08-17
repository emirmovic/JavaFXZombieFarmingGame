/* package Designs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Random;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class WelcomeScreenTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, 1500, 900);

        //Section 1: Title and Start Button

        Text title = new Text("AgriCorp");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 70));
        title.setFill(Color.GOLDENROD);
        Button startButt = new Button("Start");
        DropShadow shadow = new DropShadow();
        startButt.setEffect(shadow);
        startButt.setMinSize(70, 50);
        startButt.setStyle("-fx-font-size: 35;" + "-fx-background-radius: 5em;");

        VBox titleBox = new VBox();
        titleBox.getChildren().addAll(title, startButt);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(160, 0, 50, 0));
        titleBox.setSpacing(165);
        titleBox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                CornerRadii.EMPTY, Insets.EMPTY)));

        //Section 2: Grass generation with random height

        VBox container = new VBox();
        container.setAlignment(Pos.BOTTOM_LEFT);

        HBox grass = new HBox();
        grass.setSpacing(1);
        grass.setAlignment(Pos.BOTTOM_LEFT);
        Random random = new Random();
        for (int i = 0; i < scene.getWidth() / 11; i++) {
            int limit = random.nextInt(95) + 75;
            Line line = new Line(0,
                    0, 0, limit);
            line.setStroke(Color.GREEN);
            line.setStrokeWidth(10);
            grass.getChildren().add(line);
        }
        container.getChildren().addAll(grass);
        container.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,
                CornerRadii.EMPTY, Insets.EMPTY)));

        //Section 3: Bottom section and generation of dirt

        VBox bottom = new VBox();
        bottom.setBackground(new Background(new BackgroundFill(Color.SADDLEBROWN,
                CornerRadii.EMPTY, Insets.EMPTY)));
        Line line2 = new Line(0, 0, 0, 150);
        bottom.getChildren().add(line2);

        //Section 4: Putting it all together and compiling it into the BorderPane

        border.setTop(titleBox);
        border.setCenter(container);
        border.setBottom(bottom);

        //Section 5: Title and showing of stage + scene.

        primaryStage.setTitle("Welcome to AgriCorp!");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Switch to configuration screen after start button is clicked
        startButt.setOnAction(e -> primaryStage.close());
    }

    @Test
    public void testStartButtonExists() {
        verifyThat("#startButton", hasText("Start"));
    }

    @Test
    public void testTitleExists() {
        verifyThat("#mainTitle", hasText("AgriCorp"));
    }
}
 */
