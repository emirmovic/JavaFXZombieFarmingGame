/*package designs;

import game.Main;
import game.Controller.MarketScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class MarketScreenTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/MarketScreen.fxml"));
        primaryStage.setTitle("Your Farm");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
        Main.setCash(10000);
    }

    @Test
    public void testCropButtons() {
        clickOn("#brocButton");
        clickOn("#corButton");
        assertEquals("corn", MarketScreenController.getItemSelected()[0]);
        clickOn("#brocButton");
        assertEquals("broccoli", MarketScreenController.getItemSelected()[0]);
        clickOn("#carButton");
        assertEquals("carrot", MarketScreenController.getItemSelected()[0]);
    }

    @Test
    public void testFailedCheckout() {
        clickOn("#corSeedButton");
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        write("2");
        clickOn("#buyButton");
        clickOn("OK");
    }

    @Test
    public void testLetterInvalidQuantityInput() {
        clickOn("#corSeedButton");
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        write("abc");
        clickOn("#buyButton");
        clickOn("OK");
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        push(KeyCode.BACK_SPACE);
        push(KeyCode.BACK_SPACE);
        clickOn("#buyButton");
        clickOn("OK");
    }

    @Test
    public void testNumericInvalidQuantityInput() {
        clickOn("#corSeedButton");
        clickOn("#quantityTextField");
        clickOn("#buyButton");
        clickOn("OK");
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        write("1000");
        clickOn("#buyButton");
        clickOn("OK");
    }

    @Test
    public void testValidButtonText() {
        verifyThat("#buyButton", hasText("Buy"));
        verifyThat("#sellButton", hasText("Sell"));
    }

    @Test
    public void testWaterBuying() {
        clickOn("#btIrrigation");
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        write("10");
        clickOn("#buyButton");
        clickOn("OK");
        verifyThat("#money", hasText("5000.0"));
    }

    @Test
    public void testWeakWorkerBuying() {
        clickOn("#wWorkerButton");
        verifyThat("#wWorkerButton", hasText("Weak Worker"));
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        write("5");
        clickOn("#buyButton");
        clickOn("OK");
        verifyThat("#money", hasText("9000.0"));
    }

    @Test
    public void testStrongWorkerBuying() {
        clickOn("#sWorkerButton");
        verifyThat("#sWorkerButton", hasText("Strong Worker"));
        clickOn("#quantityTextField");
        push(KeyCode.BACK_SPACE);
        write("5");
        clickOn("#buyButton");
        clickOn("OK");
        verifyThat("#money", hasText("8500.0"));
    }
}

 */
