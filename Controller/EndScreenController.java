package game.Controller;

import game.Main;
import javafx.fxml.FXML;

import java.io.IOException;

public class EndScreenController {
@FXML
private void restart() throws IOException {
	Main.getSecondaryWelcome();
}
}
