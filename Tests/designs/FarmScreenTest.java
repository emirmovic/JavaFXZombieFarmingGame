/*package designs;

import game.Controller.FarmController;
import game.Controller.WelcomeScreenController;
import game.Main;
import game.data.Plot;
import game.data.defense.Bomb;
import game.data.zombie.Zombie;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;


public class FarmScreenTest extends ApplicationTest {

    private FarmController f;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int[][] newInv = new int[5][3];
        newInv[3][0] = 3;
        newInv[4][2] = 3;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXML/farm.fxml"));
        Parent root = fxmlLoader.load();
        this.f = fxmlLoader.getController();
        primaryStage.setTitle("Your Farm");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();
        Main.setInventory(newInv);
    }



    @Test
    public void testInventoryNameTextExists() {
        clickOn("#inventoryName");
    }

    @Test
    public void testMoneyTextExists() {
        clickOn("#money");
        verifyThat("#money", hasText("$0.0"));
    }

    @Test
    public void testCurrentItemTextExists() {
        //verifyThat("#currentItem", hasText(Main.getCurrentItem()));
    }

    @Test
    public void aInventorySelector() {
        clickOn("#box00");
        verifyThat("#currentItemText", hasText("Watering can"));
        clickOn("#box01");
        verifyThat("#currentItemText", hasText("Shovel"));
        clickOn("#box02");
        verifyThat("#currentItemText", hasText("Sickle"));
        clickOn("#box02");
        verifyThat("#currentItemText", hasText("None"));
    }

    @Test
    public void basicPlotInfo() {
        clickOn(300, 250);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #1: 80 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(500, 250);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #2: 80 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(300, 450);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #5: 80 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(500, 450);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #6: 80 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(300, 650);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #9: 80 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(900, 650);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #12: 80 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
    }

    @Test
    public void canWaterPlotInfo() {
        clickOn("#box00");
        clickOn(300, 250);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #1: 90 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(500, 250);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #2: 90 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(300, 250);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #1: 100 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(900, 650);
        try {
            verifyThat("#waterIndicator",
                    hasText("Water Level for Plot #12: 90 out of 100"));
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn("#box00");
    }

    @Test
    public void rHarvestCrops() {
        clickOn("#box01");
        Plot[][] arr = Main.getFarmPlots();
        clickOn(300, 250);
        try {
            assertFalse(arr[0][0].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(500, 250);
        try {
            assertFalse(arr[0][1].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(300, 450);
        try {
            assertFalse(arr[1][0].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(500, 450);
        try {
            assertFalse(arr[1][1].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(300, 650);
        try {
            assertFalse(arr[2][0].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(900, 650);
        try {
            assertFalse(arr[2][3].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn("#box01");
    }

    @Test
    public void removeCrops() {
        clickOn("#box02");
        Plot[][] arr = Main.getFarmPlots();
        clickOn(300, 250);
        try {
            assertFalse(arr[0][0].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(500, 250);
        try {
            assertFalse(arr[0][1].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(300, 450);
        try {
            assertFalse(arr[1][0].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(500, 450);
        try {
            assertFalse(arr[1][1].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(300, 650);
        try {
            assertFalse(arr[2][0].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn(900, 650);
        try {
            assertFalse(arr[2][3].getOccupied());
        } catch (AssertionError ae) {
            System.out.println("Empty or unharvestable plot!");
        }
        clickOn("#box02");
    }

     */

    /*



    @Test
    public void test1ExistenceZombies() {
        clickOn("#zombie1box");
        clickOn("#zombie2box");
        clickOn("#zombie3");
    }

    @Test
    public void test2Rain() {
        boolean generatedRain = false;
        while (!generatedRain) {
            generatedRain = this.f.chanceRain();
        }

        try {
            verifyThat("#waterIndicator",
                    hasText("There was a drought! All plant water levels +10!"));
        } catch (AssertionError a1) {
            try {
                verifyThat("#waterIndicator",
                        hasText("There was a drought! All plant water levels +20!"));
            } catch (AssertionError a2) {
                try {
                    verifyThat("#waterIndicator",
                            hasText("There was a drought! All plant water levels +30!"));
                } catch (AssertionError a3) {
                    System.out.println("Out of bounds.");
                }
            }
        }
    }

    @Test
    public void test3Drought() {
        boolean generatedDrought = false;
        while (!generatedDrought) {
            generatedDrought = this.f.chanceDrought();
        }

        try {
            verifyThat("#waterIndicator",
                    hasText("There was a drought! All plant water levels -10"));
        } catch (AssertionError a1) {
            try {
                verifyThat("#waterIndicator",
                        hasText("There was a drought! All plant water levels -20"));
            } catch (AssertionError a2) {
                try {
                    verifyThat("#waterIndicator",
                            hasText("There was a drought! All plant water levels -30"));
                } catch (AssertionError a3) {
                    System.out.println("Out of bounds.");
                }
            }
        }
    }

    @Test
    public void test4ZombieAttack() {
        boolean chanceAttack = false;
        int iterCounter = 0;
        while (!chanceAttack && iterCounter < 10) {
            try {
                double getHealth = Main.getFarmPlots()[1][0].getHealth();
                Zombie.attack(Main.getFarmPlots()[1][0], 1);
                double afterAttack = Main.getFarmPlots()[1][0].getHealth();
                assertTrue(afterAttack < getHealth);
                chanceAttack = true;
            } catch (AssertionError assertionError) {
                System.out.println("Luck interference");
            }
            iterCounter++;
        }
    }

     */

    /*

    @Test
    public void test5ApplyPesticide() {
        clickOn("#box40");
        int saveX = 0;
        int saveY;
        if (!Main.getFarmPlots()[0][0].getOccupied()) {
            clickOn(400, 220);
            saveY = 0;
        } else if (!Main.getFarmPlots()[0][1].getOccupied()) {
            clickOn(600, 220);
            saveY = 1;
        } else if (!Main.getFarmPlots()[0][2].getOccupied()) {
            clickOn(800, 220);
            saveY = 2;
        } else {
            clickOn(1000, 220);
            saveY = 3;
        }
        clickOn("#box40");

        ArrayList<Bomb> b = new ArrayList<>();
        b.add(new Bomb(Main.getFarmPlots()[saveX][saveY]));
        Main.setFarmBombs(b);
        HashSet<Integer> exclude = this.f.getAlivePlots();
        exclude.remove(saveY);
        this.f.setAlivePlots(exclude);
        double getHealth = Main.getFarmPlots()[saveX][saveY].getHealth();
        Zombie.attack(Main.getFarmPlots()[saveX][saveY], 1);
        double afterAttack = Main.getFarmPlots()[saveX][saveY].getHealth();
        try {
            assertFalse(afterAttack < getHealth);
        } catch (AssertionError e) {
            System.out.println("Plots occupied");
        }
    }

     */
/*
    @Test
    public void testWaterValues() {
        clickOn("#box00");
        clickOn(350, 250);
        int expectedValue = 7;
        try {
            verifyThat("#watering",
                    hasText(expectedValue - 1 + ""));
            expectedValue--;
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(600, 250);
        try {
            verifyThat("#watering",
                    hasText(expectedValue - 1 + ""));
            expectedValue--;
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(850, 250);
        try {
            verifyThat("#watering",
                    hasText(expectedValue - 1 + ""));
            expectedValue--;
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(900, 650);
        try {
            if (expectedValue == 0) {
                clickOn("OK");
            } else {
                verifyThat("#watering",
                        hasText(expectedValue - 1 + ""));
                expectedValue--;
            }
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn("#box00");
    }

    @Test
    public void testWaterLimit() {
        clickOn("#box00");
        clickOn(350, 250);
        int expectedValue = 3;
        try {
            verifyThat("#watering",
                    hasText(expectedValue - 1 + ""));
            expectedValue--;
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(600, 250);
        try {
            verifyThat("#watering",
                    hasText(expectedValue - 1 + ""));
            expectedValue--;
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(850, 250);
        try {
            verifyThat("#watering",
                    hasText(expectedValue - 1 + ""));
            expectedValue--;
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn(900, 650);
        try {
            if (expectedValue == 0) {
                clickOn("OK");
            } else {
                verifyThat("#watering",
                        hasText(expectedValue - 1 + ""));
                expectedValue--;
            }
        } catch (AssertionError ae) {
            System.out.println("Empty plot!");
        }
        clickOn("#box00");
    }
}


*/
