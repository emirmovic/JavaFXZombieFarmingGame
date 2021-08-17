package game.Controller;

import game.Main;
import game.data.Plot;
import game.data.defense.Bomb;
import game.data.zombie.Zombie;
import game.data.saves.Save;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.util.Duration;

import java.util.*;

public class ExpandedColumnController {
    @FXML
    private BorderPane borderMain;

    @FXML
    private Text money;

    @FXML
    private Text day;

    @FXML
    private Text inventoryName;

    @FXML
    private VBox inventoryBox;

    @FXML
    private GridPane inventoryGrid;

    @FXML
    private BorderPane box00;

    @FXML
    private BorderPane box01;

    @FXML
    private BorderPane box02;

    @FXML
    private BorderPane box10;

    @FXML
    private BorderPane box11;

    @FXML
    private BorderPane box12;

    @FXML
    private BorderPane box20;

    @FXML
    private BorderPane box21;

    @FXML
    private BorderPane box22;

    @FXML
    private BorderPane box30;

    @FXML
    private BorderPane box31;

    @FXML
    private BorderPane box32;

    @FXML
    private BorderPane box40;

    @FXML
    private BorderPane box41;

    @FXML
    private BorderPane box42;

    @FXML
    private Label amount10;

    @FXML
    private Label amount11;

    @FXML
    private Label amount12;

    @FXML
    private Label amount20;

    @FXML
    private Label amount21;

    @FXML
    private Label amount22;

    @FXML
    private Label amount30;

    @FXML
    private Label amount31;

    @FXML
    private Label amount32;

    @FXML
    private Label amount40;

    @FXML
    private Label amount41;

    @FXML
    private Label amount42;

    @FXML
    private Pane plot00;

    @FXML
    private Pane plot01;

    @FXML
    private Pane plot02;

    @FXML
    private Pane plot03;

    @FXML
    private Pane plot04;

    @FXML
    private Pane plot10;

    @FXML
    private Pane plot11;

    @FXML
    private Pane plot12;

    @FXML
    private Pane plot13;

    @FXML
    private Pane plot14;

    @FXML
    private Pane plot20;

    @FXML
    private Pane plot21;

    @FXML
    private Pane plot22;

    @FXML
    private Pane plot23;

    @FXML
    private Pane plot24;

    @FXML
    private Text currentItemLabel;

    @FXML
    private Text currentItemText;

    @FXML
    private Text plotIndicator;

    @FXML
    private ImageView zombie1view;

    @FXML
    private ImageView zombie2view;

    @FXML
    private ImageView zombie3view;

    @FXML
    private ImageView strongManView;

    @FXML
    private ImageView weakManView;

    @FXML
    private Line dayBar;

    @FXML
    private Line fullDayBar;

    @FXML
    private Text timeText;

    @FXML
    private Line growthLine;

    @FXML
    private Text growthText;

    @FXML
    private GridPane wholeFarmPlots;

    @FXML
    private Label watering;

    private static int[][] inventory = Main.getInventory();
    private static Plot[][] farmPlots = Main.getFarmPlots();
    private Timer dayTimer;
    private Timer growTimer;
    private TimerTask timerTask = null;
    private int growTime = 0;
    private TimerTask frameIdleTask = null;
    private TimerTask frameWalkTask = null;
    private TimerTask spinTask = null;
    private TimerTask fillBarTask = null;
    private static String[] modes = {
        "water", "remove", "harvest", "plantBroccoli",
        "plantCarrot", "plantCorn", "plantBomb", "potion", "fertilize", "click"};
    private static String mode = "Click";
    private static Pane prevGrid = null;
    private static final BorderStroke BASIC_BORDER_TOOL = new BorderStroke(
            Paint.valueOf("#FF0000"),
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(1.0));
    private static final BorderStroke THICK_BORDER_TOOL = new BorderStroke(
            Paint.valueOf("#0000FF"),
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(5.0));
    private static final BorderStroke BASIC_BORDER_PLOT = new BorderStroke(
            Color.SADDLEBROWN,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(10.0));
    private static final BorderStroke THICK_BORDER_PLOT = new BorderStroke(
            Color.DARKCYAN,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            new BorderWidths(10));

    private int harvestMax = 5;
    private int plantsHarvested = 0;

    private String waterInfo = "";
    private String fertInfo = "";

    // Variable to hold the jumping animation of the farm worker
    private SequentialTransition strongJump;
    private SequentialTransition weakJump;

    private final String[] cornPaths = {
        "./../Images/Pixel/Drawn/babyCorn.png",
        "./../Images/Pixel/Drawn/grownCorn.png",
        "./../Images/Pixel/Drawn/ancientCorn.png"
    };

    private final String[] broccoliPaths = {
        "./../Images/Pixel/Drawn/babyBroccoli.png",
        "./../Images/Pixel/Drawn/grownBroccoli.png",
        "./../Images/Pixel/Drawn/ancientBroccoli.png"
    };

    private final String[] carrotPaths = {
        "./../Images/Pixel/Drawn/babyCarrot.png",
        "./../Images/Pixel/Drawn/grownCarrot.png",
        "./../Images/Pixel/Drawn/ancientCarrot.png"
    };

    private final String[] potionCarrotPaths = {
        "./../Images/glowing-plants-sprites/sprite_0.png",
        "./../Images/glowing-plants-sprites/sprite_1.png",
        "./../Images/glowing-plants-sprites/sprite_2.png",
    };

    private final String[] potionCornPaths = {
        "./../Images/glowing-plants-sprites/sprite_3.png",
        "./../Images/glowing-plants-sprites/sprite_4.png",
        "./../Images/glowing-plants-sprites/sprite_5.png",
    };

    private final String[] potionBroccoliPaths = {
        "./../Images/glowing-plants-sprites/sprite_6.png",
        "./../Images/glowing-plants-sprites/sprite_7.png",
        "./../Images/glowing-plants-sprites/sprite_8.png",
    };

    private final String potionPath = "./../Images/Pixel/Net Found(Edited)/potion.png";

    private boolean night = false;
    private ArrayList<Bomb> farmBombs = Main.getFarmBombs();

    // Structure to hold all valid plots that are able to be attacked once nighttime comes
    private HashSet<Integer> alivePlots = Main.getAlivePlots();

    private final String[][][] masterFrames = Main.getMasterZombieFrames();

    private File saveFile = Main.getSaveFile();

    /**
     * Sets up the initial inventory, money, current item (watering can),
     * day timer display, borders of selected item in hotbar, and either randomizes
     * the different plots of plants and their stages of growth or re-adds previous
     * display with accompanying alive status changes.
     *
     * @throws IOException if Market fxml file is not found
     */
    @FXML
    public void initialize() throws IOException {
        // Randomizes or sets previous plot displays based on game's initial boot up
        if (Main.getInit()) {
            Main.setInit(false);

            // Sets the player's selected starting seed equal to the max capacity
            String startingSeed = Main.getStartingSeed();

            if (startingSeed.equals("broccoli")) {
                inventory[0][0] = 3;
            } else if (startingSeed.equals("carrot")) {
                inventory[0][1] = 3;
            } else {
                inventory[0][2] = 3;
            }

            //Watering initialization - Default value 7
            inventory[4][2] = 7;
            watering.setText(inventory[4][2] + "");

            handleRandomPlanting();
        } else {
            if (!Main.getLoadFlag()) {
                Main.setLoadFlag(false);

                // Decrement each plant's water at the "end" of the day, keeping track
                // of bomb plants that are killed by an insufficient water level. Also
                // increments the number of days a plot's potion has been active,
                // removing the effect if the limit is reached
                ArrayList<Plot> waterBombs = new ArrayList<>();
                for (Plot[] plots : farmPlots) {
                    for (Plot plot : plots) {
                        plot.updateFert();

                        if (!plot.updateWater(false)) {
                            alivePlots.remove(plot.getRow() * 5 + plot.getCol());
                        }

                        if (plot.getPlant().contains("deadBomb")) {
                            waterBombs.add(plot);
                        }

                        if (plot.getPotion()) {
                            plot.addPotionDay();
                        }
                    }
                    watering.setText(inventory[4][2] + "");
                }

                // Increments the number of days that a bomb plant has been active (is able
                // to survive 2 days instead of 1), removing the bomb as active within the
                // farm if the limit has been reached or if it was killed due to its water
                // level
                for (Plot plot : waterBombs) {
                    for (Bomb bomb : farmBombs) {
                        if (bomb.getCentralPlot().equals(plot)) {
                            farmBombs.remove(bomb);
                            waterBombs.remove(plot);
                        }
                    }
                }

                for (Bomb bomb : farmBombs) {
                    if (bomb.addDay() > 2) {
                        farmBombs.remove(bomb);
                        bomb.getCentralPlot().setAncient();
                    }
                }
            }

            // Allows for permanence of the plot display across multiple days, sets plots
            // back to their previous display
            handleSetPlant();
        }

        // Take a picture of the current state of the farm at the beginning of the day
        SnapshotParameters snapPam = new SnapshotParameters();
        snapPam.setFill(Color.SADDLEBROWN);
        Main.setScreenshot(wholeFarmPlots.snapshot(snapPam, null));

        // Auto save the current state of the game at the beginning of the day
        new Save(saveFile);

        String currentItem = "None";
        currentItemText.setText(currentItem);
        money.setText("" + new DecimalFormat("#####.##").format(Main.getCash()));
        inventoryName.setText(Main.getName() + "'s Inventory");
        setInventory();
        setBorders(true, new Pane(), true);
        int[] dayNum = Main.getDay();
        day.setText("Day: " + dayNum[0] + "  ");
        updateHarvestMax(true);
        dayTime();

        if (Main.getAnimationToggles()[0]) {
            cycleZombieIdle();
        }

        if (MarketScreenController.getStrongDaysLeft() > 0) {
            strongManView.setVisible(true);

            if (Main.getAnimationToggles()[2]) {
                strongManJump();
            }
        }

        if (MarketScreenController.getWeakDaysLeft() > 0) {
            weakManView.setVisible(true);

            if (Main.getAnimationToggles()[2]) {
                weakManJump();
            }
        }
    }

    /**
     * Handles the random planting of all plots upon initial loading of the game
     */
    private void handleRandomPlanting() {
        randomPlanting(plot00, "00");
        randomPlanting(plot01, "01");
        randomPlanting(plot02, "02");
        randomPlanting(plot03, "03");
        randomPlanting(plot04, "04");
        randomPlanting(plot10, "10");
        randomPlanting(plot11, "11");
        randomPlanting(plot12, "12");
        randomPlanting(plot13, "13");
        randomPlanting(plot14, "14");
        randomPlanting(plot20, "20");
        randomPlanting(plot21, "21");
        randomPlanting(plot22, "22");
        randomPlanting(plot23, "23");
        randomPlanting(plot24, "24");
    }

    /**
     * Randomizes the display of each plot upon initial loading of the game.
     *
     * @param plot is the plot being affected
     * @param plotCoor is the coordinates of the plot on the plot grid
     */
    public void randomPlanting(Pane plot, String plotCoor) {
        int row = Integer.parseInt(plotCoor.substring(0, 1));
        int col = Integer.parseInt(plotCoor.substring(1));

        // 1 = empty, 2 = carrot, 3 = corn, 4 = broccoli
        int randomFirst = (int) (Math.random() * 4) + 1;

        // 1 = baby. 2 = grown; 3 = ancient;
        int randomSecond = (int) (Math.random() * 3) + 1;

        if (randomFirst == 1 && randomSecond == 1) {
            plantCarrot(plot, plotCoor, "baby", carrotPaths[0], false);
        } else if (randomFirst == 1 && randomSecond == 2) {
            plantCarrot(plot, plotCoor, "grown", carrotPaths[1], false);
        } else if (randomFirst == 1 && randomSecond == 3) {
            plantCarrot(plot, plotCoor, "ancient", carrotPaths[2], false);
        } else if (randomFirst == 2 && randomSecond == 1) {
            plantCorn(plot, plotCoor, "baby", cornPaths[0], false);
        } else if (randomFirst == 2 && randomSecond == 2) {
            plantCorn(plot, plotCoor, "grown", cornPaths[1], false);
        } else if (randomFirst == 2 && randomSecond == 3) {
            plantCorn(plot, plotCoor, "ancient", cornPaths[2], false);
        } else if (randomFirst == 3 && randomSecond == 1) {
            plantBroccoli(plot, plotCoor, "baby", broccoliPaths[0], false);
        } else if (randomFirst == 3 && randomSecond == 2) {
            plantBroccoli(plot, plotCoor, "grown", broccoliPaths[1], false);
        } else if (randomFirst == 3 && randomSecond == 3) {
            plantBroccoli(plot, plotCoor, "ancient", broccoliPaths[2], false);
        }

        if (plot.getId().contains("emptyPlot")) {
            farmPlots[row][col] = new Plot(row, col, false, "emptyPlot:" + row + col);
        } else {
            farmPlots[row][col] = new Plot(row, col, true, plot.getId());
        }

        if (farmPlots[row][col].isAlive()) {
            alivePlots.add(row * 5 + col);
        }
    }

    @FXML
    private void goSettings() throws IOException {
        dayTimer.cancel();
        growTimer.cancel();

        Main.getSettings();
    }

    /**
     * Handles the resetting of each plot's state upon re-entering of the farm screen
     */
    private void handleSetPlant() {
        setPlant(plot00, farmPlots[0][0].getPlant(), "00", farmPlots[0][0].getPotion());
        setPlant(plot01, farmPlots[0][1].getPlant(), "01", farmPlots[0][1].getPotion());
        setPlant(plot02, farmPlots[0][2].getPlant(), "02", farmPlots[0][2].getPotion());
        setPlant(plot03, farmPlots[0][3].getPlant(), "03", farmPlots[0][3].getPotion());
        setPlant(plot04, farmPlots[0][4].getPlant(), "04", farmPlots[0][4].getPotion());
        setPlant(plot10, farmPlots[1][0].getPlant(), "10", farmPlots[1][0].getPotion());
        setPlant(plot11, farmPlots[1][1].getPlant(), "11", farmPlots[1][1].getPotion());
        setPlant(plot12, farmPlots[1][2].getPlant(), "12", farmPlots[1][2].getPotion());
        setPlant(plot13, farmPlots[1][3].getPlant(), "13", farmPlots[1][3].getPotion());
        setPlant(plot14, farmPlots[1][4].getPlant(), "14", farmPlots[1][4].getPotion());
        setPlant(plot20, farmPlots[2][0].getPlant(), "20", farmPlots[2][0].getPotion());
        setPlant(plot21, farmPlots[2][1].getPlant(), "21", farmPlots[2][1].getPotion());
        setPlant(plot22, farmPlots[2][2].getPlant(), "22", farmPlots[2][2].getPotion());
        setPlant(plot23, farmPlots[2][3].getPlant(), "23", farmPlots[2][3].getPotion());
        setPlant(plot24, farmPlots[2][4].getPlant(), "24", farmPlots[2][4].getPotion());
    }

    /**
     * Sets up initial inventory display
     */
    private void setInventory() {
        watering.setText(inventory[4][2] + "");
        setInventoryHelper(amount10, 0, 0);
        setInventoryHelper(amount11, 0, 1);
        setInventoryHelper(amount12, 0, 2);
        setInventoryHelper(amount20, 1, 0);
        setInventoryHelper(amount21, 1, 1);
        setInventoryHelper(amount22, 1, 2);
        setInventoryHelper(amount30, 2, 0);
        setInventoryHelper(amount31, 2, 1);
        setInventoryHelper(amount32, 2, 2);
        setInventoryHelper(amount40, 3, 0);
        setInventoryHelper(amount41, 3, 1);
        setInventoryHelper(amount42, 3, 2);
    }

    /**
     * Helper method for setting the state of the inventory and their corresponding
     * box in the farm UI, setting maxed out slots to red and non-maxed out slots
     * to black
     *
     * @param amount is the amount label to hold the current inventory element
     * @param row is the row of the inventory element
     * @param col is the column of the inventory element
     */
    private void setInventoryHelper(Label amount, int row, int col) {
        amount.setText(Integer.toString(inventory[row][col]));

        if (inventory[row][col] == 3) {
            amount.setTextFill(Paint.valueOf("#FF0000"));
        } else {
            amount.setTextFill(Paint.valueOf("#000000"));
        }
    }

    /**
     * Cycles the the zombies' initial idle animation before night time
     */
    private void cycleZombieIdle() {
        int[] frame = {0, 3, 6};

        frameIdleTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    zombie1view.setImage(
                            new Image(
                                    getClass().getResourceAsStream(masterFrames[0][0][frame[0]])));
                    zombie2view.setImage(
                            new Image(
                                    getClass().getResourceAsStream(masterFrames[1][0][frame[1]])));
                    zombie3view.setImage(
                            new Image(
                                    getClass().getResourceAsStream(masterFrames[0][0][frame[2]])));

                    frame[0] = (frame[0] + 1 == masterFrames[0][0].length) ? 0 : frame[0] + 1;
                    frame[1] = (frame[1] + 1 == masterFrames[1][0].length) ? 0 : frame[1] + 1;
                    frame[2] = (frame[2] + 1 == masterFrames[0][0].length) ? 0 : frame[2] + 1;
                });
            }
        };

        Timer frameTimer = new Timer();

        frameTimer.scheduleAtFixedRate(frameIdleTask, 50, 50);
    }

    /**
     * Gives the strong farm worker a jump animation
     */
    private void strongManJump() {
        TranslateTransition totop = new TranslateTransition(Duration.millis(5), strongManView);
        totop.setToY(strongManView.getTranslateY() - 10.0);

        TranslateTransition tobottom = new TranslateTransition(Duration.millis(5), strongManView);
        tobottom.setToY(strongManView.getTranslateY() + 10.0);

        strongJump = new SequentialTransition(
                new PauseTransition(Duration.millis(1250)),
                totop,
                new SequentialTransition(new PauseTransition(Duration.millis(1250)),
                        tobottom));

        strongJump.setCycleCount(Animation.INDEFINITE);
        strongJump.play();
    }

    /**
     * Gives the strong farm worker a flying animation across the screen
     */
    @FXML
    private void strongManFly() {
        if (Main.getAnimationToggles()[2]) {
            strongJump.stop();

            ScaleTransition scaleBig = new ScaleTransition(Duration.millis(1250), strongManView);
            scaleBig.setByX(3.5);
            scaleBig.setByY(3.5);
            scaleBig.setCycleCount(2);
            scaleBig.setAutoReverse(true);

            RotateTransition rt = new RotateTransition(Duration.millis(50), strongManView);
            rt.setByAngle(-45);
            rt.setCycleCount(1);

            TimerTask schmooveTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        strongManView.setTranslateX(strongManView.getTranslateX() - 25.0);
                        strongManView.setTranslateY(strongManView.getTranslateY() - 20.0);
                    });
                }
            };

            Timer schmooveTimer = new Timer();

            rt.play();
            scaleBig.play();
            schmooveTimer.scheduleAtFixedRate(schmooveTask, 0, 50);

            scaleBig.setOnFinished(e -> {
                schmooveTask.cancel();

                strongManView.setTranslateX(30.0);
                strongManView.setTranslateY(-20.0);
                strongManView.setRotate(0.0);

                strongManJump();
            });
        }
    }

    /**
     * Gives the weak farm worker a jump animation
     */
    private void weakManJump() {
        TranslateTransition totop = new TranslateTransition(Duration.millis(5), weakManView);
        totop.setToY(weakManView.getTranslateY() - 10.0);

        TranslateTransition tobottom = new TranslateTransition(Duration.millis(5), weakManView);
        tobottom.setToY(weakManView.getTranslateY() + 10.0);

        weakJump = new SequentialTransition(
                new PauseTransition(Duration.millis(1250)),
                totop,
                new SequentialTransition(new PauseTransition(Duration.millis(1250)),
                        tobottom));

        weakJump.setCycleCount(Animation.INDEFINITE);
        weakJump.play();
    }

    /**
     * Gives the weak farm worker a flying animation across the screen
     */
    @FXML
    private void weakManFly() {
        if (Main.getAnimationToggles()[0]) {
            weakJump.stop();

            ScaleTransition scaleBig = new ScaleTransition(Duration.millis(1250), weakManView);
            scaleBig.setByX(3.5);
            scaleBig.setByY(3.5);
            scaleBig.setCycleCount(2);
            scaleBig.setAutoReverse(true);

            RotateTransition rt = new RotateTransition(Duration.millis(50), weakManView);
            rt.setByAngle(-45);
            rt.setCycleCount(1);

            TimerTask schmooveTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        weakManView.setTranslateX(weakManView.getTranslateX() - 25.0);
                        weakManView.setTranslateY(weakManView.getTranslateY() - 20.0);
                    });
                }
            };

            Timer schmooveTimer = new Timer();

            rt.play();
            scaleBig.play();
            schmooveTimer.scheduleAtFixedRate(schmooveTask, 0, 50);

            scaleBig.setOnFinished(e -> {
                schmooveTask.cancel();

                weakManView.setTranslateX(30.0);
                weakManView.setTranslateY(-20.0);
                weakManView.setRotate(0.0);

                weakManJump();
            });
        }
    }

    /**
     * Determines length of day depending on difficulty level and switches to the
     * market after each day's respective time has passes
     */
    public void dayTime() {
        dayTimer = new Timer();
        //  Timer timeChecker = new Timer();
        //  Updating update = new Updating();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        dayTimer.cancel();
                        timerTask.cancel();

                        if (Main.getAnimationToggles()[0]) {
                            frameWalkTask.cancel();
                        }

                        SnapshotParameters snapPam = new SnapshotParameters();
                        snapPam.setFill(Color.SADDLEBROWN);
                        Main.setScreenshot(wholeFarmPlots.snapshot(snapPam, null));

                        MarketScreenController.setStrongDaysLeft(
                                MarketScreenController.getStrongDaysLeft() - 1);
                        MarketScreenController.setWeakDaysLeft(
                                MarketScreenController.getWeakDaysLeft() - 1);
                        Main.getMarket();
                        new Save(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        };

        TimerTask growTask = new TimerTask() {
            @Override
            public void run() {
                growTimer.cancel();

                Platform.runLater(() -> {
                    for (Plot[] plots : farmPlots) {
                        for (Plot plot : plots) {
                            if (plot.isAlive()) {
                                String coordinates = "" + plot.getRow() + plot.getCol();
                                switch (coordinates) {
                                case "00":
                                    updateGrowth(plot, coordinates, plot00);
                                    break;
                                case "01":
                                    updateGrowth(plot, coordinates, plot01);
                                    break;
                                case "02":
                                    updateGrowth(plot, coordinates, plot02);
                                    break;
                                case "03":
                                    updateGrowth(plot, coordinates, plot03);
                                    break;
                                case "04":
                                    updateGrowth(plot, coordinates, plot04);
                                    break;
                                case "10":
                                    updateGrowth(plot, coordinates, plot10);
                                    break;
                                case "11":
                                    updateGrowth(plot, coordinates, plot11);
                                    break;
                                case "12":
                                    updateGrowth(plot, coordinates, plot12);
                                    break;
                                case "13":
                                    updateGrowth(plot, coordinates, plot13);
                                    break;
                                case "14":
                                    updateGrowth(plot, coordinates, plot14);
                                    break;
                                case "20":
                                    updateGrowth(plot, coordinates, plot20);
                                    break;
                                case "21":
                                    updateGrowth(plot, coordinates, plot21);
                                    break;
                                case "22":
                                    updateGrowth(plot, coordinates, plot22);
                                    break;
                                case "23":
                                    updateGrowth(plot, coordinates, plot23);
                                    break;
                                case "24":
                                    updateGrowth(plot, coordinates, plot24);
                                    break;
                                default:
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        };

        growTimer = new Timer();

        int dayDuration;

        if (Main.getDifficulty().equals("doomsday")) {
            dayTimer.schedule(timerTask, 35 * 1000);
            growTimer.schedule(growTask, 10 * 1000);
            dayDuration = 20;
        } else if (Main.getDifficulty().equals("inferno")) {
            dayTimer.schedule(timerTask, 45 * 1000);
            growTimer.schedule(growTask, 20 * 1000);

            growthLine.setStartX(406.0);
            growthLine.setEndX(406.0);
            growthText.setLayoutX(379.0);
            fullDayBar.setEndX(567.0);
            dayDuration = 30;
        } else {
            dayTimer.schedule(timerTask, 55 * 1000);
            growTimer.schedule(growTask, 30 * 1000);

            growthLine.setStartX(451.0);
            growthLine.setEndX(451.0);
            growthText.setLayoutX(425.0);
            fullDayBar.setEndX(564.0);
            dayDuration = 40;
        }

        setUpNightAnimations(dayDuration);
    }

    private void setUpNightAnimations(int dayDuration) {
        FillTransition fakeToNight = new FillTransition();

        Rectangle fakeRec = new Rectangle();
        fakeRec.setFill(Color.LIGHTBLUE);

        fakeToNight.setShape(fakeRec);
        fakeToNight.setFromValue(Color.LIGHTBLUE);
        fakeToNight.setToValue(Color.DARKBLUE);
        fakeToNight.setDuration(Duration.millis(dayDuration * 1000));
        fakeToNight.setCycleCount(1);

        Animation fadeToNight = new Transition() {
            {
                setCycleDuration(Duration.millis(dayDuration * 1000));
                setCycleCount(1);
                setInterpolator(Interpolator.EASE_IN);
            }

            @Override
            protected void interpolate(double num) {
                borderMain.setBackground(
                        new Background(
                                new BackgroundFill(
                                        fakeRec.getFill(), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };

        fakeToNight.play();
        fadeToNight.play();

        fillDayBar(dayDuration);
    }

    /**
     * Handles the filling and coloring of the bar timer on top of the farm.
     * Fills according to the duration of the day and changes from green ->
     * yellow - orange -> red as the player gets in danger of running out of
     * time
     *
     * @param dayDuration is the duration of the day
     */
    private void fillDayBar(int dayDuration) {
        int durationBase = dayDuration * 1000 / 3;

        StrokeTransition fillOne = new StrokeTransition(
                Duration.millis(durationBase), dayBar, Color.LIME, Color.YELLOW);
        fillOne.setCycleCount(1);
        fillOne.setAutoReverse(false);

        StrokeTransition fillTwo = new StrokeTransition(
                Duration.millis(durationBase), dayBar, Color.YELLOW, Color.DARKORANGE);
        fillTwo.setCycleCount(1);
        fillTwo.setAutoReverse(false);
        fillTwo.setDelay(Duration.millis(durationBase));

        StrokeTransition fillThree = new StrokeTransition(
                Duration.millis(durationBase), dayBar, Color.DARKORANGE, Color.RED);
        fillThree.setCycleCount(1);
        fillThree.setAutoReverse(false);
        fillThree.setDelay(Duration.millis(durationBase * 2));

        Timer fillTimer = new Timer();

        fillBarTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    dayBar.setEndX(dayBar.getEndX() + 1.0);
                });
            }
        };

        int rate = dayDuration * 1000 / 560;

        fillTimer.scheduleAtFixedRate(fillBarTask, 0, rate);
        fillOne.play();
        fillTwo.play();
        fillThree.play();

        TimerTask prepTask;

        prepTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(Main::setIsNight);
            }
        };

        Timer prepTimer = new Timer();
        prepTimer.schedule(prepTask, (dayDuration / 2) * 1000);

        fillTwo.setOnFinished(e -> {
            ((Label) box00.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box01.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box02.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box10.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box11.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box12.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box20.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box21.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box22.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box30.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box31.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box32.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box40.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box41.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));
            ((Label) box42.getTop()).setTextFill(Paint.valueOf("#FFFFFF"));

            inventoryName.setFill(Color.LIME);
            currentItemLabel.setFill(Color.LIME);
            currentItemText.setFill(Color.LIME);
            money.setFill(Color.LIME);
            day.setFill(Color.LIME);
            timeText.setFill(Color.LIME);
            growthText.setVisible(false);

            plotIndicator.setFill(Paint.valueOf("#FFFFFF"));
        });

        fillThree.setOnFinished(e -> {
            fillBarTask.cancel();
            setNight();
        });
    }

    /**
     * Advances the growth level of a plant by 1 level
     *
     * @param plot        is the plot being affected
     * @param coordinates is the coordinates of the plot on the farm
     * @param pane        is the plot's pane on the farm
     */
    private void updateGrowth(Plot plot, String coordinates, Pane pane) {
        String plant = plot.getPlant().toLowerCase();

        if (!plant.contains("grown") && !plant.contains("ancient")) {
            if (plant.contains("seed")) {
                if (plant.contains("carrot")) {
                    plantCarrot(pane, coordinates, "baby", carrotPaths[0], plot.getPotion());
                    plot.setPlant("babyCarrot:" + coordinates);
                } else if (plant.contains("broccoli")) {
                    plantBroccoli(pane, coordinates, "baby", broccoliPaths[0], plot.getPotion());
                    plot.setPlant("babyBroccoli:" + coordinates);
                } else if (plant.contains("corn")) {
                    plantCorn(pane, coordinates, "baby", cornPaths[0], plot.getPotion());
                    plot.setPlant("babyCorn:" + coordinates);
                } else {
                    plantBabyBomb(pane, coordinates);
                    plot.setPlant("babyBomb:" + coordinates);
                }

                plot.updateGrowthHealth(30);
            } else if (plant.contains("baby")) {
                double random = new Random().nextDouble();
                boolean weakWorkerHarvested = false;
                boolean strongWorkerHarvested = false;

                if (plant.contains("bomb")) {
                    // BOMB SHOULD NEVER BE HARVESTED
                    plantGrownBomb(pane, coordinates);
                    plot.setPlant("grownBomb:" + coordinates);
                    farmBombs.add(new Bomb(plot));
                } else if ((MarketScreenController.getStrongDaysLeft() > 0)
                        && (random < MarketScreenController.STRONG_CHANCE)) {
                    pane.setId(pane.getId().replace("baby", "grown"));
                    harvestPlot(pane, pane.getId().split(":"));
                    strongWorkerHarvested = true;
                } else if ((MarketScreenController.getWeakDaysLeft() > 0)
                        && (random < MarketScreenController.WEAK_CHANCE)) {
                    pane.setId(pane.getId().replace("baby", "grown"));
                    harvestPlot(pane, pane.getId().split(":"));
                    weakWorkerHarvested = true;
                } else if (plant.contains("carrot")) {
                    plantCarrot(pane, coordinates, "grown", carrotPaths[1], plot.getPotion());
                    plot.setPlant("grownCarrot:" + coordinates);
                } else if (plant.contains("broccoli")) {
                    plantBroccoli(pane, coordinates, "grown", broccoliPaths[1], plot.getPotion());
                    plot.setPlant("grownBroccoli:" + coordinates);
                } else if (plant.contains("corn")) {
                    plantCorn(pane, coordinates, "grown", cornPaths[1], plot.getPotion());
                    plot.setPlant("grownCorn:" + coordinates);
                }

                plot.updateGrowthHealth(40);

                if (strongWorkerHarvested) {
                    strongManFly();
                }

                if (weakWorkerHarvested) {
                    weakManFly();
                }
            }
        } else if (plant.contains("grown")) {
            if (plant.contains("carrot")) {
                plantCarrot(pane, coordinates, "ancient", carrotPaths[2], false);
                plot.setAlive(false);
                plot.setPlant("ancientCarrot:" + coordinates);
                plot.setAncient();
            } else if (plant.contains("broccoli")) {
                plantBroccoli(pane, coordinates, "ancient", broccoliPaths[2], false);
                plot.setAlive(false);
                plot.setPlant("ancientBroccoli:" + coordinates);
                plot.setAncient();
            } else if (plant.contains("corn")) {
                plantCorn(pane, coordinates, "ancient", cornPaths[2], false);
                plot.setAlive(false);
                plot.setPlant("ancientCorn:" + coordinates);
                plot.setAncient();
            }

            // Removes this plot as a valid possible attacked plot if it is already dead
            alivePlots.remove((Integer.parseInt(coordinates.substring(0, 1)) * 5
                    + Integer.parseInt(coordinates.substring(1))));
        }
    }

    /**
     * Sets the plots back to their previous display. Only occurs after the first day,
     * first day has randomization with no permanence.
     *
     * @param plot is the specific plot being set
     * @param plantType is the type of plant previous held by the plot
     * @param plotCoor is the grid coordinate of the plant
     * @param addPotion is whether to add a potion graphic to the plant
     */
    public void setPlant(
            Pane plot,
            String plantType,
            String plotCoor,
            boolean addPotion) {
        plot.getChildren().clear();

        switch (plantType.split(":")[0]) {
        case "seedCarrot":
            inventory[0][1]++;
            plantSeedCarrot(plot, plotCoor);
            break;
        case "seedCorn":
            inventory[0][2]++;
            plantSeedCorn(plot, plotCoor);
            break;
        case "seedBroccoli":
            inventory[0][0]++;
            plantSeedBroccoli(plot, plotCoor);
            break;
        case "seedBomb":
            inventory[3][0]++;
            plantSeedBomb(plot, plotCoor);
            break;
        case "babyCarrot":
            plantCarrot(plot, plotCoor, "baby", carrotPaths[0], addPotion);
            break;
        case "babyCorn":
            plantCorn(plot, plotCoor, "baby", cornPaths[0], addPotion);
            break;
        case "babyBroccoli":
            plantBroccoli(plot, plotCoor, "baby", broccoliPaths[0], addPotion);
            break;
        case "babyBomb":
            plantBabyBomb(plot, plotCoor);
            break;
        case "grownCarrot":
            plantCarrot(plot, plotCoor, "grown", carrotPaths[1], addPotion);
            break;
        case "grownCorn":
            plantCorn(plot, plotCoor, "grown", cornPaths[1], addPotion);
            break;
        case "grownBroccoli":
            plantBroccoli(plot, plotCoor, "grown", broccoliPaths[1], addPotion);
            break;
        case "grownBomb":
            plantGrownBomb(plot, plotCoor);
            break;
        case "ancientCarrot":
            plantCarrot(plot, plotCoor, "ancient", carrotPaths[2], addPotion);
            break;
        case "ancientCorn":
            plantCorn(plot, plotCoor, "ancient", cornPaths[2], addPotion);
            break;
        case "ancientBroccoli":
            plantBroccoli(plot, plotCoor, "ancient", broccoliPaths[2], addPotion);
            break;
        case "deadBomb":
            plantDeadBomb(plot, plotCoor);
            break;
        default:
            break;
        }
    }

    /**
     * Changes the mode that the player is in upon clicking a "tool" in the
     * hotbar/top inventory level
     *
     * @param mouseEvent is the information of the click
     */
    @FXML
    public void changeMode(MouseEvent mouseEvent) {
        BorderPane pane = (BorderPane) mouseEvent.getSource();
        String paneId = pane.getId();
        boolean alreadySelected = false;
        plotIndicator.setVisible(false);

        switch (paneId) {
        case "box00":
            alreadySelected = changeHelper(0, "Watering can");
            break;
        case "box01":
            alreadySelected = changeHelper(1, "Shovel");
            break;
        case "box02":
            alreadySelected = changeHelper(2, "Sickle");
            break;
        case "box10":
            alreadySelected = changeHelper(3, "Broccoli Seed");
            break;
        case "box11":
            alreadySelected = changeHelper(4, "Carrot Seed");
            break;
        case "box12":
            alreadySelected = changeHelper(5, "Corn Seed");
            break;
        case "box40":
            alreadySelected = changeHelper(6, "Bomb Seed");
            break;
        case "box41":
            alreadySelected = changeHelper(7, "Zom-B-Gone");
            break;
        case "box42":
            alreadySelected = changeHelper(8, "Fertilizer");
            break;
        default:
            break;
        }

        // Handles the thickening/resetting of the selected mode's border
        setBorders(true, pane, alreadySelected);
    }

    /**
     * Handles the resizing/coloring of borders based on the selected item
     *
     * @param modeIndex is the index of the mode that was clicked on
     * @param modeText is the text representing the clicked mode
     * @return whether this mode was already selected when clicked
     */
    private boolean changeHelper(int modeIndex, String modeText) {
        boolean alreadySelected = false;

        if (mode.equals(modes[modeIndex])) {
            mode = modes[9];
            alreadySelected = true;
            currentItemText.setText("None");
        } else {
            mode = modes[modeIndex];
            currentItemText.setText(modeText);
        }

        return alreadySelected;
    }

    /**
     * Sets the borders of each hotbar box based on what is selected
     *
     * @param tools is whether a tool or plot is selected
     * @param thickPane is which pane should have a thickened border
     * @param alreadySelected is whether this tool/plot was already selected
     */
    @FXML
    private void setBorders(
            boolean tools,
            Pane thickPane,
            boolean alreadySelected) {
        if (tools) {
            box00.setBorder(new Border(BASIC_BORDER_TOOL));
            box01.setBorder(new Border(BASIC_BORDER_TOOL));
            box02.setBorder(new Border(BASIC_BORDER_TOOL));
            box10.setBorder(new Border(BASIC_BORDER_TOOL));
            box11.setBorder(new Border(BASIC_BORDER_TOOL));
            box12.setBorder(new Border(BASIC_BORDER_TOOL));
            box40.setBorder(new Border(BASIC_BORDER_TOOL));
            box41.setBorder(new Border(BASIC_BORDER_TOOL));
            box42.setBorder(new Border(BASIC_BORDER_TOOL));

            if (!alreadySelected) {
                thickPane.setBorder(new Border(THICK_BORDER_TOOL));
            }
        } else {
            thickPane.setBorder(new Border(BASIC_BORDER_PLOT));

            if (prevGrid != null) {
                prevGrid.setBorder(new Border(BASIC_BORDER_PLOT));
            }

            if (!alreadySelected) {
                thickPane.setBorder(new Border(THICK_BORDER_PLOT));
                prevGrid = thickPane;
            }
        }
    }

    /**
     * General interaction method of the player with a plot, branching off to more
     * specific interactions based on the selected mode
     *
     * @param mouseEvent is the info of the click
     */
    @FXML
    public void interactPlot(MouseEvent mouseEvent) {
        Pane clickedPane = (Pane) mouseEvent.getSource();
        String plotId = clickedPane.getId();
        String[] plotInfo = plotId.split(":");

        if (!night) {
            if (mode.equals(modes[0])) {
                waterPlot(plotInfo);
            } else if (mode.equals(modes[1])) {
                remove(clickedPane, plotInfo);
            } else if (mode.equals(modes[2])) {
                harvestPlot(clickedPane, plotInfo);
            } else if (mode.equals(modes[3])) {
                String plotCoor = plotInfo[1];
                plantSeedBroccoli(clickedPane, plotCoor);
            } else if (mode.equals(modes[4])) {
                String plotCoor = plotInfo[1];
                plantSeedCarrot(clickedPane, plotCoor);
            } else if (mode.equals(modes[5])) {
                String plotCoor = plotInfo[1];
                plantSeedCorn(clickedPane, plotCoor);
            } else if (mode.equals(modes[6])) {
                plantSeedBomb(clickedPane, plotInfo[1]);
            } else if (mode.equals(modes[7])) {
                applyPotion(clickedPane, plotInfo);
            } else if (mode.equals(modes[8])) {
                fertilizePlot(plotInfo, clickedPane);
            } else {
                if (prevGrid != null && clickedPane.getId().split(":")[1].equals(
                        prevGrid.getId().split(":")[1])) {
                    setBorders(false, clickedPane, true);
                    plotIndicator.setVisible(false);
                } else {
                    setBorders(false, clickedPane, false);
                    displayPlotInfo(plotInfo);
                }
            }
        }
    }

    /**
     * Interaction with plots when in "fertilizing" mode
     *
     * @param plotInfo is the coordinates of the clicked pane
     * @param clickedPane is the pane that was clicked
     */
    private void fertilizePlot(String[] plotInfo, Pane clickedPane) {
        if (inventory[3][2] > 0) {
            String plotCoor = plotInfo[1];
            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));

            if (farmPlots[row][col].getOccupied()) {
                farmPlots[row][col].increaseFert();

                amount42.setText("" + --inventory[3][2]);
                int plotNumber;
                if (row == 0) {
                    plotNumber = col + 1;
                } else if (row == 1) {
                    plotNumber = row + col + 5;
                } else {
                    plotNumber = row + col + 9;
                }

                //fertilize functionality
                String coordinates = "" + row + col;

                Timer growTimeBooster = new Timer();
                TimerTask fertTask = new TimerTask() {
                    @Override
                    public void run() {
                        growTimeBooster.cancel();

                        Platform.runLater(() -> {
                            if (farmPlots[row][col].isAlive()
                                    && farmPlots[row][col].getFertilizerLevel() > 0) {
                                updateGrowth(farmPlots[row][col], coordinates, clickedPane);
                            }
                        });
                    }
                };

                growTimeBooster.schedule(fertTask, 5 * 1000);

                fertInfo = "Fertilizer Level for Plot #" + plotNumber + ": "
                        + farmPlots[row][col].getFertilizerLevel() + " out of 3";

                plotIndicator.setText(fertInfo);
                plotIndicator.setVisible(true);
            } else {
                plotIndicator.setVisible(false);
            }
        }
    }

    /**
     * Interaction with plots when in "watering" mode
     *
     * @param plotInfo is the coordinates of the clicked pane
     */
    private void waterPlot(String[] plotInfo) {
        String plotCoor = plotInfo[1];
        int row = Integer.parseInt(plotCoor.substring(0, 1));
        int col = Integer.parseInt(plotCoor.substring(1));

        if (farmPlots[row][col].getOccupied()) {
            if (inventory[4][2] > 0 && farmPlots[row][col].getWaterLevel() < 100) {
                int plotNumber;
                if (row == 0) {
                    plotNumber = col + 1;
                } else if (row == 1) {
                    plotNumber = row + col + 5;
                } else {
                    plotNumber = row + col + 13;
                }

                if (!farmPlots[row][col].updateWater(true)) {
                    alivePlots.remove((row * 5 + col));
                }

                waterInfo = "Water Level for Plot #" + plotNumber + ": "
                        + farmPlots[row][col].getWaterLevel() + " out of 100";
                inventory[4][2]--;
                watering.setText(inventory[4][2] + "");
                plotIndicator.setText(waterInfo);
                plotIndicator.setVisible(true);
            } else if (inventory[4][2] <= 0 && farmPlots[row][col].getWaterLevel() < 100) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You don't have enough water "
                                + "to continue watering for today.");
                alert.show();
            } else if (inventory[4][2] > 0 && farmPlots[row][col].getWaterLevel() >= 100) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Maximum water capacity has been "
                                + "reached for this plot.");
                alert.show();
            } else if (inventory[4][2] <= 0 && farmPlots[row][col].getWaterLevel() >= 100) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You don't have enough water to continue watering for today. "
                                + "Also, maximum water capacity has been reached for this plot.");
                alert.show();
            }
        } else {
            plotIndicator.setVisible(false);
        }
    }

    /**
     * Interaction with plots when in "remove" mode
     *
     * @param clickedPane is the pane that was clicked
     * @param plotInfo    is the coordinates of the clicked pane
     */
    private void remove(Pane clickedPane, String[] plotInfo) {
        // Reset the plot's backend to have no crop
        String plotCoor = plotInfo[1];
        int row = Integer.parseInt(plotCoor.substring(0, 1));
        int col = Integer.parseInt(plotCoor.substring(1));

        if (farmPlots[row][col].getOccupied()) {
            farmPlots[row][col].setPlant("emptyplot:" + plotCoor);
            farmPlots[row][col].setOccupied(false);
            farmPlots[row][col].setAlive(false);
            farmPlots[row][col].resetWaterLevel();
            alivePlots.remove((row * 5 + col));

            GridPane emptyGrid = new GridPane();
            emptyGrid.setLayoutX(10.0);
            emptyGrid.setLayoutY(10.0);
            emptyGrid.setPrefWidth(140);
            emptyGrid.setPrefHeight(180.0);
            emptyGrid.setStyle("-fx-background-color: sienna;");
            emptyGrid.setBorder(new Border(BASIC_BORDER_PLOT));
            clickedPane.getChildren().add(emptyGrid);
            clickedPane.setId("emptyPlot:" + plotCoor);
        }
    }

    @FXML
    private Label sickle;

    private boolean updateHarvestMax(boolean initMax) {
        if (initMax) {
            harvestMax = MarketScreenController.getHarvestMax();
            sickle.setText("" + harvestMax);

            return false;
        } else {
            boolean canHarvest;

            if (harvestMax - plantsHarvested >= 0) {
                sickle.setText("" + (harvestMax - plantsHarvested));
                canHarvest = true;
            } else {
                //error message
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You are out of daily harvest.");
                alert.show();
                canHarvest = false;
                sickle.setText("" + 0);
            }

            return canHarvest;
        }
    }

    /**
     * Harvests plants if grown and updates inventory accordingly
     *
     * @param clickedPane farm plot pane clicked
     * @param plotInfo    is the coordinates of the clicked pane
     */
    private void harvestPlot(Pane clickedPane, String[] plotInfo) {
        String plotId = clickedPane.getId().toLowerCase();

        if (plotId.contains("grown") && updateHarvestMax(false)) {
            // Reset the plot's backend to have no crop
            String plotCoor = plotInfo[1];
            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));
            farmPlots[row][col].setPlant("emptyplot:" + plotCoor);
            farmPlots[row][col].setOccupied(false);
            farmPlots[row][col].setAlive(false);
            farmPlots[row][col].resetWaterLevel();
            alivePlots.remove((row * 5 + col));

            GridPane emptyGrid = new GridPane();
            emptyGrid.setLayoutX(10.0);
            emptyGrid.setLayoutY(10.0);
            emptyGrid.setPrefWidth(140);
            emptyGrid.setPrefHeight(180.0);
            emptyGrid.setStyle("-fx-background-color: sienna;");
            emptyGrid.setBorder(new Border(BASIC_BORDER_PLOT));
            clickedPane.getChildren().add(emptyGrid);
            clickedPane.setId("emptyPlot:" + plotCoor);

            if (plotId.contains("carrot")) {
                harvestHelper(plotInfo, 1, row, col, amount21, amount31);
            } else if (plotId.contains("broccoli")) {
                harvestHelper(plotInfo, 0, row, col, amount20, amount30);
            } else if (plotId.contains("corn")) {
                harvestHelper(plotInfo, 2, row, col, amount22, amount32);
            }
        }
    }

    /**
     * Helper method for harvesting
     *
     * @param plotInfo is the plant name and coordinates
     * @param invCol is the inventory column of the plant's crop
     * @param farmRow is the row of the plant on the farm
     * @param farmCol is the column of the plant on the farm
     * @param amountReg is the label for the non-potioned version of this crop
     * @param amountBg is the label for the potioned version of this crop
     */
    private void harvestHelper(
            String[] plotInfo,
            int invCol,
            int farmRow,
            int farmCol,
            Label amountReg,
            Label amountBg
    ) {
        if (!farmPlots[farmRow][farmCol].getPotion()) {
            if (fertilizerBoost(plotInfo)) {
                inventory[1][invCol] += ((inventory[1][invCol] + 1) > 3) ? 0
                        : (1 + farmPlots[farmRow][farmCol].getBoost());
                farmPlots[farmRow][farmCol].setFertBoost(0);
            } else {
                inventory[1][invCol] += ((inventory[1][invCol] + 1) > 3) ? 0 : 1;
            }
            amountReg.setText(Integer.toString(inventory[1][invCol]));

            if (inventory[1][invCol] == 3) {
                amountReg.setTextFill(Paint.valueOf("#FF0000"));
            }
        } else {
            if (fertilizerBoost(plotInfo)) {
                inventory[2][invCol] += ((inventory[2][invCol] + 1) > 3) ? 0
                        : (1 + farmPlots[farmRow][farmCol].getBoost());
                farmPlots[farmRow][farmCol].setFertBoost(0);
            } else {
                inventory[2][invCol] += ((inventory[2][invCol] + 1) > 3) ? 0 : 1;
            }
            amountBg.setText(Integer.toString(inventory[2][invCol]));

            if (inventory[2][invCol] == 3) {
                amountBg.setTextFill(Paint.valueOf("#FF0000"));
            }
        }
    }

    /**
     * Handles the yield increase of a fertilized plot based on its fertilizer level
     *
     * @param plotInfo is the name and coordinate information of the plot
     * @return whether this plot will get a yield increase upon harvest
     */
    private boolean fertilizerBoost(String[] plotInfo) {
        boolean boost = false;
        String plotCoor = plotInfo[1];
        int row = Integer.parseInt(plotCoor.substring(0, 1));
        int col = Integer.parseInt(plotCoor.substring(1));

        int a = 1;
        int b = 2;
        int c = new Random().nextBoolean() ? a : b;

        if (farmPlots[row][col].getFertilizerLevel() == 1 && c == a) {
            boost = true;
            farmPlots[row][col].setFertBoost(1);
        } else if (farmPlots[row][col].getFertilizerLevel() == 2 && c == a) {
            boost = true;
            farmPlots[row][col].setFertBoost(2);
        } else if (farmPlots[row][col].getFertilizerLevel() == 3 && c == a) {
            boost = true;
            farmPlots[row][col].setFertBoost(3);
        } else {
            farmPlots[row][col].setFertBoost(0);
        }
        return boost;
    }

    /**
     * Displays the info about a specific plot that has been clicked on
     *
     * @param plotInfo is the clicked plot's type/coordinates
     */
    private void displayPlotInfo(String[] plotInfo) {
        String plotCoor = plotInfo[1];
        int row = Integer.parseInt(plotCoor.substring(0, 1));
        int col = Integer.parseInt(plotCoor.substring(1));

        if (farmPlots[row][col].getOccupied()) {
            int plotNumber;
            if (row == 0) {
                plotNumber = col + 1;
            } else if (row == 1) {
                plotNumber = row + col + 5;
            } else {
                plotNumber = row + col + 9;
            }

            waterInfo = "Water Level for Plot #" + plotNumber + ": "
                    + farmPlots[row][col].getWaterLevel() + " out of 100";

            fertInfo = "Fertilizer Level fot Plot #" + plotNumber + ": "
                    + farmPlots[row][col].getFertilizerLevel() + " out of 3";

            plotIndicator.setText(waterInfo + "\n" + fertInfo);
            plotIndicator.setVisible(true);
        } else {

            plotIndicator.setVisible(false);
        }
    }

    /**
     * Adds a potion to a specified plot
     *
     * @param clickedPane is the plot to add a potion to
     * @param plotInfo is the information of the clicked plot
     */
    private void applyPotion(Pane clickedPane, String[] plotInfo) {
        if (inventory[3][1] > 0) {
            String plotCoor = plotInfo[1];
            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));

            if (farmPlots[row][col].isAlive() && !farmPlots[row][col].getPotion()
                    && !farmPlots[row][col].getPlant().contains("Bomb")) {
                farmPlots[row][col].givePotion();

                amount41.setText(String.valueOf(--inventory[3][1]));
                amount41.setTextFill(Paint.valueOf("#000000"));

                if (!plotInfo[0].contains("seed")) {
                    String actualGrowth = plotInfo[0];
                    remove(clickedPane, plotInfo);
                    int potionIndex = (actualGrowth.contains("grown")) ? 1 : 0;
                    alivePlots.add(row * 5 + col);

                    if (actualGrowth.contains("Corn")) {
                        plantCorn(clickedPane, plotInfo[1], actualGrowth.split("C")[0],
                                cornPaths[potionIndex], true);
                    } else if (actualGrowth.contains("Broccoli")) {
                        plantBroccoli(clickedPane, plotInfo[1], actualGrowth.split("B")[0],
                                broccoliPaths[potionIndex], true);
                    } else {
                        plantCarrot(clickedPane, plotInfo[1], actualGrowth.split("C")[0],
                                carrotPaths[potionIndex], true);
                    }
                }
            }
        }
    }

    /**
     * Planting method for first stage of corn
     * Updates view to a darker pane
     * Updates backend for plot
     *
     * @param clickedPane the pane being acted upon
     * @param plotCoor    data for clickedPane
     */
    private void plantSeedCorn(Pane clickedPane, String plotCoor) {
        int cornSeeds = inventory[0][2];

        if (clickedPane.getId().contains("emptyPlot") && cornSeeds != 0) {
            GridPane plotGrid = new GridPane();
            plotGrid.setMinHeight(180.0);
            plotGrid.setMinWidth(140);
            plotGrid.setTranslateX(10);
            plotGrid.setTranslateY(10);
            plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
            plotGrid.getRowConstraints().add(new RowConstraints(90));
            plotGrid.setStyle("-fx-background-color: Saddlebrown;");

            clickedPane.setId("seedCorn:" + plotCoor);

            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));
            farmPlots[row][col].setPlant("seedCorn:" + plotCoor);
            farmPlots[row][col].setOccupied(true);
            farmPlots[row][col].setAlive(true);
            farmPlots[row][col].resetWaterLevel();
            farmPlots[row][col].resetHealth();
            farmPlots[row][col].updateGrowthHealth(20);
            alivePlots.add((row * 5 + col));

            cornSeeds--;
            amount12.setText(Integer.toString(cornSeeds));
            amount12.setTextFill(Paint.valueOf("#000000"));
            inventory[0][2]--;

            clickedPane.getChildren().add(plotGrid);
        }
    }

    /**
     * Updates the display of planting corn
     *
     * @param plot is the clicked pane
     * @param plotCoor is the coordinates of the plot
     * @param growth is the level of growth to apply to the corn
     * @param imagePath is the image path to the graphic of the corn at the specified growth level
     * @param addPotion is whether to add a potion graphic to the plot
     */
    private void plantCorn(
            Pane plot,
            String plotCoor,
            String growth,
            String imagePath,
            boolean addPotion
    ) {
        GridPane plotGrid = new GridPane();
        plotGrid.setLayoutX(10.0);
        plotGrid.setLayoutY(10.0);
        plotGrid.setPrefHeight(180.0);
        plotGrid.setPrefWidth(140);
        plotGrid.getColumnConstraints().add(new ColumnConstraints(70.0));
        plotGrid.getRowConstraints().add(new RowConstraints(90.0));
        plotGrid.setStyle("-fx-background-color: Saddlebrown;");

        plot.setId(String.format("%sCorn:%s", growth, plotCoor));
        plotGrid.setId("grid:" + plotCoor);

        if (addPotion) {
            if (imagePath.contains("baby")) {
                imagePath = potionCornPaths[0];
            } else if (imagePath.contains("grown")) {
                imagePath = potionCornPaths[1];
            } else {
                imagePath = potionCornPaths[2];
            }
        }

        ImageView cornView;
        cornView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        cornView.setFitWidth(50.0);
        cornView.setTranslateX(15.0);
        cornView.setPreserveRatio(true);
        plotGrid.add(cornView, 0, 0);

        cornView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        cornView.setFitWidth(50.0);
        cornView.setTranslateX(15.0);
        cornView.setPreserveRatio(true);
        plotGrid.add(cornView, 1, 1);

        plot.getChildren().add(plotGrid);
    }

    /**
     * Planting method for first stage of broccoli
     * Updates view to a darker pane
     * Updates backend for plot
     *
     * @param clickedPane the pane being acted upon
     * @param plotCoor    data for clickedPane
     */
    private void plantSeedBroccoli(Pane clickedPane, String plotCoor) {
        int broccoliSeeds = inventory[0][0];

        if (clickedPane.getId().contains("emptyPlot") && broccoliSeeds != 0) {
            GridPane plotGrid = new GridPane();
            plotGrid.setMinHeight(180.0);
            plotGrid.setMinWidth(140);
            plotGrid.setTranslateX(10);
            plotGrid.setTranslateY(10);
            plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
            plotGrid.getRowConstraints().add(new RowConstraints(90));
            plotGrid.setStyle("-fx-background-color: Saddlebrown;");

            clickedPane.setId("seedBroccoli:" + plotCoor);
            broccoliSeeds--;
            amount10.setText(Integer.toString(broccoliSeeds));
            amount10.setTextFill(Paint.valueOf("#000000"));
            inventory[0][0]--;

            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));
            farmPlots[row][col].setPlant("seedBroccoli:" + plotCoor);
            farmPlots[row][col].setOccupied(true);
            farmPlots[row][col].setAlive(true);
            farmPlots[row][col].resetWaterLevel();
            farmPlots[row][col].resetHealth();
            farmPlots[row][col].updateGrowthHealth(20);
            alivePlots.add((row * 5 + col));

            clickedPane.getChildren().add(plotGrid);
        }
    }

    /**
     * Updates the display of planting broccoli
     *
     * @param plot is the clicked pane
     * @param plotCoor is the coordinates of the plot
     * @param growth is the level of growth to apply to the broccoli
     * @param imagePath is the image path to the graphic of the broccoli at the
     *                  specified growth level
     * @param addPotion is whether to add a potion graphic to the plot
     */
    private void plantBroccoli(
            Pane plot,
            String plotCoor,
            String growth,
            String imagePath,
            boolean addPotion
    ) {
        GridPane plotGrid = new GridPane();
        plotGrid.setLayoutX(10.0);
        plotGrid.setLayoutY(10.0);
        plotGrid.setPrefHeight(180.0);
        plotGrid.setPrefWidth(140);
        plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
        plotGrid.getRowConstraints().add(new RowConstraints(90.0));
        plotGrid.setStyle("-fx-background-color: Saddlebrown;");

        plot.setId(String.format("%sBroccoli:%s", growth, plotCoor));
        plotGrid.setId("grid:" + plotCoor);

        if (addPotion) {
            if (imagePath.contains("baby")) {
                imagePath = potionBroccoliPaths[0];
            } else if (imagePath.contains("grown")) {
                imagePath = potionBroccoliPaths[1];
            } else {
                imagePath = potionBroccoliPaths[2];
            }
        }

        ImageView broccoliView;
        broccoliView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        broccoliView.setFitWidth(50.0);
        broccoliView.setTranslateX(15.0);
        broccoliView.setPreserveRatio(true);

        plotGrid.add(broccoliView, 0, 0);
        broccoliView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        broccoliView.setFitWidth(50.0);
        broccoliView.setTranslateX(15.0);
        broccoliView.setPreserveRatio(true);
        plotGrid.add(broccoliView, 1, 1);

        plot.getChildren().add(plotGrid);
    }

    /**
     * Planting method for first stage of carrot
     * Updates view to a darker pane
     * Updates backend for plot
     *
     * @param clickedPane the pane being acted upon
     * @param plotCoor    data for clickedPane
     */
    private void plantSeedCarrot(Pane clickedPane, String plotCoor) {
        int carrotSeeds = inventory[0][1];

        if (clickedPane.getId().contains("emptyPlot") && carrotSeeds != 0) {
            GridPane plotGrid = new GridPane();
            plotGrid.setMinHeight(180.0);
            plotGrid.setMinWidth(140);
            plotGrid.setTranslateX(10);
            plotGrid.setTranslateY(10);
            plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
            plotGrid.getRowConstraints().add(new RowConstraints(100));
            plotGrid.setStyle("-fx-background-color: Saddlebrown;");

            clickedPane.setId("seedCarrot:" + plotCoor);

            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));
            farmPlots[row][col].setPlant("seedCarrot:" + plotCoor);
            farmPlots[row][col].setOccupied(true);
            farmPlots[row][col].setAlive(true);
            farmPlots[row][col].resetWaterLevel();
            farmPlots[row][col].resetHealth();
            farmPlots[row][col].updateGrowthHealth(20);
            alivePlots.add((row * 5 + col));

            carrotSeeds--;
            amount11.setText(Integer.toString(carrotSeeds));
            amount11.setTextFill(Paint.valueOf("#000000"));
            inventory[0][1]--;

            clickedPane.getChildren().add(plotGrid);
        }
    }

    /**
     * Updates the display of planting carrots
     *
     * @param plot is the clicked pane
     * @param plotCoor is the coordinates of the plot
     * @param growth is the level of growth to apply to the carrots
     * @param imagePath is the image path to the graphic of the carrots at the
     *                  specified growth level
     * @param addPotion is whether to add a potion graphic to the plot
     */
    private void plantCarrot(
            Pane plot,
            String plotCoor,
            String growth,
            String imagePath,
            boolean addPotion
    ) {
        GridPane plotGrid = new GridPane();
        plotGrid.setLayoutX(10.0);
        plotGrid.setLayoutY(10.0);
        plotGrid.setPrefHeight(180.0);
        plotGrid.setPrefWidth(140);
        plotGrid.getColumnConstraints().add(new ColumnConstraints(70.0));
        plotGrid.getRowConstraints().add(new RowConstraints(90.0));
        plotGrid.setStyle("-fx-background-color: Saddlebrown;");

        plot.setId(String.format("%sCarrot:%s", growth, plotCoor));
        plotGrid.setId("grid:" + plotCoor);

        if (addPotion) {
            if (imagePath.contains("baby")) {
                imagePath = potionCarrotPaths[0];
            } else if (imagePath.contains("grown")) {
                imagePath = potionCarrotPaths[1];
            } else {
                imagePath = potionCarrotPaths[2];
            }
        }

        ImageView carrotView;

        carrotView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        carrotView.setFitWidth(35.0);
        carrotView.setPreserveRatio(true);
        plotGrid.add(carrotView, 0, 0);

        carrotView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        carrotView.setFitWidth(35.0);
        carrotView.setPreserveRatio(true);
        plotGrid.add(carrotView, 0, 2);

        carrotView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        carrotView.setFitWidth(35.0);
        carrotView.setPreserveRatio(true);
        plotGrid.add(carrotView, 1, 1);

        carrotView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        carrotView.setFitWidth(35.0);
        carrotView.setPreserveRatio(true);
        plotGrid.add(carrotView, 2, 0);

        carrotView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        carrotView.setFitWidth(35.0);
        carrotView.setPreserveRatio(true);
        plotGrid.add(carrotView, 2, 2);

        plot.getChildren().add(plotGrid);
    }

    /**
     * Updates the plot display with the bomb plant seed
     *
     * @param plot is the plot being affected
     * @param plotCoor is the coordinates of the plot
     */
    private void plantSeedBomb(Pane plot, String plotCoor) {
        int bombSeeds = inventory[3][0];

        if (plot.getId().contains("emptyPlot") && bombSeeds != 0) {
            GridPane plotGrid = new GridPane();
            plotGrid.setMinHeight(180.0);
            plotGrid.setMinWidth(140);
            plotGrid.setTranslateX(10);
            plotGrid.setTranslateY(10);
            plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
            plotGrid.getRowConstraints().add(new RowConstraints(100));
            plotGrid.setStyle("-fx-background-color: Saddlebrown;");

            plot.setId("seedBomb:" + plotCoor);

            int row = Integer.parseInt(plotCoor.substring(0, 1));
            int col = Integer.parseInt(plotCoor.substring(1));
            farmPlots[row][col].setPlant("seedBomb:" + plotCoor);
            farmPlots[row][col].setOccupied(true);
            farmPlots[row][col].setAlive(true);
            farmPlots[row][col].resetWaterLevel();
            farmPlots[row][col].resetHealth();

            bombSeeds--;
            amount40.setText(Integer.toString(bombSeeds));
            amount40.setTextFill(Paint.valueOf("#000000"));
            inventory[3][0]--;

            plot.getChildren().add(plotGrid);
        }
    }

    /**
     * Updates the plot display with the baby bomb plant
     *
     * @param plot is the plot being affected
     * @param plotCoor is the coordinates of the plot
     */
    private void plantBabyBomb(Pane plot, String plotCoor) {
        GridPane plotGrid = new GridPane();
        plotGrid.setMinHeight(180.0);
        plotGrid.setMinWidth(140.0);
        plotGrid.setTranslateX(10);
        plotGrid.setTranslateY(10);
        plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
        plotGrid.getRowConstraints().add(new RowConstraints(90));
        plotGrid.setStyle("-fx-background-color: Saddlebrown;");

        plot.setId("grownBomb:" + plotCoor);
        plotGrid.setId("grid:" + plotCoor);

        ImageView view =
                new ImageView(
                        new Image(
                                getClass().getResourceAsStream(
                                        "./../Images/Pixel/Net Found(Edited)/babyBomb.png")));

        view.setFitHeight(100.0);
        view.setTranslateX(29.0);
        view.setTranslateY(53.0);
        view.setPreserveRatio(true);

        plotGrid.add(view, 0, 0);
        plot.getChildren().add(plotGrid);
    }

    /**
     * Updates the plot display with the mature bomb plant
     *
     * @param plot is the plot being affected
     * @param plotCoor is the coordinates of the plot
     */
    private void plantGrownBomb(Pane plot, String plotCoor) {
        GridPane plotGrid = new GridPane();
        plotGrid.setMinHeight(180.0);
        plotGrid.setMinWidth(140);
        plotGrid.setTranslateX(10);
        plotGrid.setTranslateY(10);
        plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
        plotGrid.getRowConstraints().add(new RowConstraints(90));
        plotGrid.setStyle("-fx-background-color: Saddlebrown;");

        plot.setId("grownBomb:" + plotCoor);
        plotGrid.setId("grid:" + plotCoor);

        ImageView view = new ImageView(
                new Image(
                        getClass().getResourceAsStream(
                                "./../Images/Pixel/Net Found(Edited)/realBombPlant.png")));

        view.setFitHeight(122.0);
        view.setFitWidth(140.0);
        view.setTranslateX(6.0);
        view.setTranslateY(53.0);
        view.setPreserveRatio(true);

        plotGrid.add(view, 0, 0);
        plot.getChildren().add(plotGrid);
    }

    /**
     * Updates the plot display with the ancient bomb plant
     *
     * @param plot is the plot being affected
     * @param plotCoor is the coordinates of the plot
     */
    private void plantDeadBomb(Pane plot, String plotCoor) {
        GridPane plotGrid = new GridPane();
        plotGrid.setMinHeight(180.0);
        plotGrid.setMinWidth(140);
        plotGrid.setTranslateX(10);
        plotGrid.setTranslateY(10);
        plotGrid.getColumnConstraints().add(new ColumnConstraints(70));
        plotGrid.getRowConstraints().add(new RowConstraints(90));
        plotGrid.setStyle("-fx-background-color: Saddlebrown;");

        plot.setId("grownBomb:" + plotCoor);
        plotGrid.setId("grid:" + plotCoor);

        ImageView view = new ImageView(
                new Image(
                        getClass().getResourceAsStream(
                                "./../Images/Pixel/Net Found(Edited)/deadBomb.png")));

        view.setFitHeight(122.0);
        view.setFitWidth(140.0);
        view.setTranslateX(30.0);
        view.setTranslateY(53.0);
        view.setPreserveRatio(true);

        plotGrid.add(view, 0, 0);
        plot.getChildren().add(plotGrid);
    }

    /**
     * Activates and executes the nighttime activities
     */
    private void setNight() {
        if (Main.getAnimationToggles()[0]) {
            frameIdleTask.cancel();
            cycleZombieWalk();
        }

        night = true;

        inventoryBox.getChildren().remove(inventoryGrid);
        inventoryName.setText(Main.getName() + "'s Damage Report");

        VBox reportBox = new VBox();
        reportBox.setPrefHeight(400.0);
        reportBox.setPrefWidth(433.0);

        inventoryBox.getChildren().add(reportBox);

        int row;
        int col;
        double damage;
        Text damageReport;
        String damageString = "";
        String plantStatus;
        ArrayList<Text> damageTexts = new ArrayList<>();

        Integer[] plots = new Integer[alivePlots.size()];
        alivePlots.toArray(plots);

        for (Integer plotNum : plots) {
            plantStatus = "";

            row = plotNum / 5;
            col = plotNum % 5;

            damage = Zombie.attack(farmPlots[row][col], Main.getDay()[0]);

            if (damage >= 0) {
                if (farmPlots[row][col].getHealth() <= 0) {
                    plantStatus += "YEETED";
                    alivePlots.remove(plotNum);
                    setAncient(farmPlots[row][col], farmPlots[row][col].getPlant().split(":"));
                } else {
                    plantStatus += "SURVIVED";
                }

                damageString = String.format(
                        "PLOT %d %s! \n\tDamage dealt: %f, Plant health: %f\n",
                        plotNum + 1, plantStatus, damage, farmPlots[row][col].getHealth());
            } else if (damage == -1) {
                damageString = String.format(
                        "PLOT %d: ZOMBIE SWERVED THE PLANT\n", plotNum + 1);
            } else if (damage == -2) {
                damageString = String.format(
                        "PLOT %d: ZOMBIE GOT POISONED\n", plotNum + 1);
            } else if (damage == -3) {
                damageString = String.format(
                        "PLOT %d: ZOMBIE GOT SPLODED\n", plotNum + 1);
            }

            damageReport = new Text(damageString);
            damageReport.setFill(Color.WHITE);
            damageReport.setFont(new Font(16.0));
            damageTexts.add(damageReport);
        }

        if (damageTexts.size() > 0) {
            // Animation for each attack information
            for (int i = 0; i < damageTexts.size(); i++) {
                Text text = damageTexts.get(i);

                spinAttack(text, reportBox, i);
            }

            if (damageTexts.size() > 1) {
                spinAttack(
                        damageTexts.get(damageTexts.size() - 1), reportBox, damageTexts.size() - 1);
            }
        } else {
            Text text = new Text("The zombies were gravely disappointed...");
            text.setFill(Color.WHITE);

            spinAttack(text, reportBox, 0);
        }

        if (!chanceRain()) {
            chanceDrought();
        }

        farmBombs.removeIf(bomb -> bomb.addDay() == 3);
    }

    private void spinAttack(Text text, VBox reportBox, int i) {
        RotateTransition rt;
        FadeTransition ft;

        rt = new RotateTransition(Duration.millis(1000), text);
        rt.setByAngle(360);
        rt.setCycleCount(1);

        ft = new FadeTransition(Duration.millis(1000), text);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.setCycleCount(1);

        spinTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    text.setFont(new Font(16.0));

                    reportBox.getChildren().add(text);
                    RotateTransition rt = new RotateTransition(Duration.millis(1000), text);
                    rt.setByAngle(360);
                    rt.setCycleCount(1);

                    FadeTransition ft = new FadeTransition(Duration.millis(1000), text);
                    ft.setFromValue(0.0);
                    ft.setToValue(1);
                    ft.setCycleCount(1);

                    rt.play();
                    ft.play();
                    spinTask.cancel();
                });
            }
        };

        new Timer().schedule(spinTask, i * 1000);
    }

    /**
     * Handles the probability of rain occurring at the end of the day
     *
     * @return whether it rained or not
     */
    public boolean chanceRain() {
        double standard;

        if (Main.getDifficulty().equals("doomsday")) {
            standard = .3; // probability is 70%
        } else if (Main.getDifficulty().equals("inferno")) {
            standard = .5; // probability is 50%
        } else {
            standard = .7; // probability is 30%
        }

        boolean raining = Math.random() > standard;
        int rainMultiplier = (int) (Math.random() * 2) + 1;

        if (raining) {
            for (Plot[] plotRow : farmPlots) {
                for (Plot plot : plotRow) {
                    for (int i = 0; i < rainMultiplier; i++) {
                        plot.updateWater(true);
                    }
                }
            }

            plotIndicator.setText(
                    "It rained! All plant water levels +" + (10 * rainMultiplier) + "!");
            plotIndicator.setVisible(true);
            return true;
        }
        return false;
    }

    /**
     * Handles the probability of a drought occurring at the end of the day
     *
     * @return whether a drought happened or not
     */
    public boolean chanceDrought() {
        double standard = 1.0;

        if (Main.getDifficulty().equals("doomsday")) {
            standard = 0.3; //Probability is 70%
        } else if (Main.getDifficulty().equals("inferno")) {
            standard = 0.5; //Probability is 50%
        } else {
            standard = 0.7; //Probability is 30%
        }

        boolean drought = Math.random() > standard;

        if (drought) {
            int randAmount = (int) ((Math.random() * 2) + 1);
            for (Plot[] plotRow : farmPlots) {
                for (Plot p : plotRow) {
                    for (int i = 0; i < randAmount; i++) {
                        if (!p.updateWater(false)) {
                            alivePlots.remove((p.getRow() * 5 + p.getCol()));
                        }
                    }
                }
            }

            plotIndicator.setText(
                    "There was a drought! All plant water levels -" + randAmount * 10 + "!");
            plotIndicator.setVisible(true);
            return true;
        }
        return false;
    }

    /**
     * Sets the display of a specified plot's crop to its ancient form
     *
     * @param plot is the plot being affected
     * @param plantInfo is the plant and plot information about the plot
     */
    private void setAncient(Plot plot, String[] plantInfo) {
        Pane pane;

        switch (plantInfo[1]) {
        case "00":
            pane = plot00;
            break;
        case "01":
            pane = plot01;
            break;
        case "02":
            pane = plot02;
            break;
        case "03":
            pane = plot03;
            break;
        case "04":
            pane = plot04;
            break;
        case "10":
            pane = plot10;
            break;
        case "11":
            pane = plot11;
            break;
        case "12":
            pane = plot12;
            break;
        case "13":
            pane = plot13;
            break;
        case "14":
            pane = plot14;
            break;
        case "20":
            pane = plot20;
            break;
        case "21":
            pane = plot21;
            break;
        case "22":
            pane = plot22;
            break;
        case "23":
            pane = plot23;
            break;
        case "24":
            pane = plot24;
            break;
        default:
            return;
        }

        if (plantInfo[0].contains("Carrot")) {
            plantCarrot(pane, plantInfo[1], "ancient", carrotPaths[2], false);
            plot.setAlive(false);
            plot.setPlant("ancientCarrot:" + plantInfo[1]);
        } else if (plantInfo[0].contains("Corn")) {
            plantCorn(pane, plantInfo[1], "ancient", cornPaths[2], false);
            plot.setAlive(false);
            plot.setPlant("ancientCorn:" + plantInfo[1]);
        } else if (plantInfo[0].contains("Broccoli")) {
            plantBroccoli(pane, plantInfo[1], "ancient", broccoliPaths[2], false);
            plot.setAlive(false);
            plot.setPlant("ancientBroccoli:" + plantInfo[1]);
        } else if (plantInfo[0].contains("Bomb")) {
            plantDeadBomb(pane, plantInfo[1]);
            plot.setAlive(false);
            plot.setPlant("deadBomb:" + plantInfo[1]);
        }
    }

    /**
     * Performs the zombie animations as they walk across the farm
     */
    private void cycleZombieWalk() {
        frameWalkTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    advanceZombieFrame(zombie1view, 0, 0);
                    advanceZombieFrame(zombie2view, 1, 1);
                    advanceZombieFrame(zombie3view, 2, 0);
                });
            }
        };

        Timer frameTimer = new Timer();

        frameTimer.scheduleAtFixedRate(frameWalkTask, 50, 50);
    }

    private int[] frame = {0, 3, 6};
    private boolean[] attackFrames = {false, false, false};
    private int[] attackCycles = {0, 0, 0};
    private int[] walkCycles = {0, 0, 0};
    private boolean[] pauseWalk = {false, false, false};
    private boolean[] deathFrames = {false, false, false};
    private boolean[] stopFrames = {false, false, false};

    /**
     * Handles the execution of the animation cycles and frames (idle, walk, attack, and death)
     *
     * @param zombieView is the ImageView object containing the zombie
     * @param zombieIndex is which zombie is being affected
     * @param frameSet is the frame set to pull the frames from (boy/girl)
     */
    private void advanceZombieFrame(ImageView zombieView, int zombieIndex, int frameSet) {
        if (zombieView.getTranslateX() >= 860.0 && !stopFrames[zombieIndex]) {
            if (!pauseWalk[zombieIndex]) {
                pauseWalk[zombieIndex] = true;
                frame[zombieIndex] = 0;

                deathFrames[zombieIndex] = Math.random() >= .05;
            }

            if (deathFrames[zombieIndex]) {
                zombieView.setImage(
                        new Image(
                                getClass().getResourceAsStream(
                                        masterFrames[frameSet][3][frame[zombieIndex]])));

                if (++frame[zombieIndex] == masterFrames[frameSet][3].length) {
                    stopFrames[zombieIndex] = true;

                    FadeTransition ft = new FadeTransition(Duration.millis(1000), zombieView);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setCycleCount(1);

                    ft.play();
                }
            } else {
                zombieView.setImage(
                        new Image(getClass().getResourceAsStream(
                                masterFrames[frameSet][0][frame[zombieIndex]])));

                frame[zombieIndex] =
                        (frame[zombieIndex] + 1 == masterFrames[frameSet][0].length) ? 0
                                : frame[zombieIndex] + 1;
            }
        } else if (!stopFrames[zombieIndex]) {
            if (!attackFrames[zombieIndex]) {
                zombieView.setImage(
                        new Image(
                                getClass().getResourceAsStream(
                                        masterFrames[frameSet][1][frame[zombieIndex]])));

                zombieView.setTranslateX(zombieView.getTranslateX() + 5.0);

                frame[zombieIndex] =
                        (frame[zombieIndex] + 1 == masterFrames[frameSet][1].length) ? 0
                                : frame[zombieIndex] + 1;

                if (frame[zombieIndex] == 0 && ++walkCycles[zombieIndex] == 2
                        && Math.random() >= .4) {

                    attackFrames[zombieIndex] = true;
                    attackCycles[zombieIndex] = 0;
                    walkCycles[zombieIndex] = 0;
                }
            } else {
                zombieView.setImage(
                        new Image(
                                getClass().getResourceAsStream(
                                        masterFrames[frameSet][2][frame[zombieIndex]])));
                frame[zombieIndex] =
                        (frame[zombieIndex] + 1 == masterFrames[frameSet][2].length) ? 0
                                : frame[zombieIndex] + 1;

                if (frame[zombieIndex] == 0 && ++attackCycles[zombieIndex] == 2) {
                    attackFrames[zombieIndex] = false;
                    attackCycles[zombieIndex] = 0;
                    walkCycles[zombieIndex] = 0;
                }
            }
        }
    }

    public HashSet<Integer> getAlivePlots() {
        return alivePlots;
    }

    public void setAlivePlots(HashSet<Integer> alivePlots) {
        this.alivePlots = alivePlots;
    }
}