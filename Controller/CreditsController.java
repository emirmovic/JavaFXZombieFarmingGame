package game.Controller;

import game.Main;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javafx.scene.image.ImageView;
import java.io.IOException;

public class CreditsController {
    @FXML
    private VBox rollBox;

    @FXML
    private HBox grass;

    @FXML
    private void initialize() {
        PerspectiveTransform spec = new PerspectiveTransform();
        spec.setLlx(10.0);
        spec.setLrx(1200.0);
        spec.setUlx(250.0);
        spec.setUrx(960.0);
        spec.setLly(2800.0);
        spec.setLry(2800.0);
        spec.setUly(0.0);
        spec.setUry(0.0);

        Group g = new Group();
        g.setEffect(spec);
        g.setCache(true);

        String creditsString =
            "Thank you for playing Dead Harvest!\n\n"
                    + "Developed by the team at AgriCorp:\n\n"
            + "Supreme leader: Tahlee Jaynes\n\n"
            + "Art major: Erica Izaguirre\n\n"
            + "Vibe checker: Emir Ibrisimovic\n\n"
            + "Meme enthusiast: Nicolas Ramirez\n\n"
                    + "IRL farm hand: Khalaya Dean\n\n\n"
            + "Thank you to\n\n"
            + "Professor Musaev, "
            + "(Best) TA Sambhav, and "
            + "Stack Overflow (b/c yes)\n\n"
            + "Disclaimers:\n\n"
            + "Soviet anthem is soviet anthem, "
            + "elevator music is the work of Benjamin "
                    + "Tissot (aka Bensound), "
            + "Sans SFX is from Undertale by Toby Fox, "
            + "Nyoom SFX was uttered by Neil DeGrasse Tyson, and "
            + "art not drawn by art major is copyright free";

        Text text = new Text(creditsString);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font("Impact", FontWeight.BOLD, 40.0));
        text.setTranslateX(225.0);
        text.setTranslateY(-1500);
        text.setWrappingWidth(700);


        g.getChildren().add(text);

        rollBox.getChildren().add(g);

        TranslateTransition tt = new TranslateTransition(Duration.millis(40 * 1000), text);
        tt.setAutoReverse(false);
        tt.setCycleCount(1);
        tt.setToY(2500);

        tt.setOnFinished(e -> {
            try {
                Main.getSecondaryWelcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        ImageView sansView =
                new ImageView(
                        new Image(
                                getClass().getResourceAsStream(
                                        "./../Images/NetFound/sans.png")));
        sansView.setTranslateY(1100);
        sansView.setTranslateX(-60.0);
        sansView.setPreserveRatio(true);
        sansView.setFitHeight(200.0);


        TranslateTransition ot = new TranslateTransition(Duration.millis(2000), sansView);
        ot.setToY(-100);
        ot.setDelay(Duration.millis(22 * 1000));
        ot.setCycleCount(1);
        ot.setAutoReverse(false);

        ot.setOnFinished(e -> {
            TranslateTransition ott = new TranslateTransition(Duration.millis(2000), sansView);
            ott.setToY(1100);
            ott.setCycleCount(1);
            ott.setAutoReverse(false);

            ott.play();
        });

        ImageView headView =
                new ImageView(
                        new Image(
                                getClass().getResourceAsStream(
                                        "./../Images/NetFound/headOut.jpg")));
        headView.setPreserveRatio(true);
        headView.setFitHeight(450);
        headView.setTranslateY(1100);
        headView.setOnMouseClicked(e -> {
            try {
                Main.getSecondaryWelcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        TranslateTransition st = new TranslateTransition(Duration.millis(5000), headView);
        st.setDelay(Duration.millis(30 * 1000));
        st.setCycleCount(1);
        st.setAutoReverse(false);
        st.setToY(-800);

        rollBox.getChildren().addAll(sansView, headView);

        tt.play();
        ot.play();
        st.play();

        grass.setTranslateY(-650);
    }
}
