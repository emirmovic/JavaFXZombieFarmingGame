package game;

import game.data.Plot;
import game.data.defense.Bomb;
import game.data.zombie.Zombie;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javafx.scene.input.KeyEvent;
import java.awt.image.BufferedImage;
import javafx.event.EventHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.Timer;

public class Main extends Application {
    private static int[] dayNum = {1};
    private static double cash = 0;
    private static String difficulty = "";
    private static String currSeason = "";
    private static String name = "Comrade";
    private static String startingSeed = "";
    private static String screen = "";
    private static int[][] inventory = new int[5][3];
    /*
        INVENTORY SETUP:
                        broccoli   carrot    corn
        seed              0, 0      0, 1     0, 2
        matured crops     1, 0      1, 1     1, 2
        b-goned           2, 0      2, 1     2, 2
                         (bomb)   (potion)   (TBD)
        defenses          3, 0      3, 1     3, 2
     */
    private static Plot[][] farmPlots = new Plot[3][4];
    private static HashSet<Integer> alivePlots = new HashSet<>();
    private static boolean init = true;
    private static Stage primaryStage = null;
    private static boolean marketActive = false;
    private static ArrayList<Bomb> farmBombs = new ArrayList();

    private static File saveFile = null;
    private static File saveImage = null;
    private static boolean loadFlag = false;
    private static boolean expandedColumn = false;
    private static boolean expandedRow = false;

    private static boolean[] animationToggles = {true, true, true};
    private static boolean[] soundToggles = {true, true};

    private static String backScreen = "welcome";

    public static boolean[] getAnimationToggles() {
        return animationToggles;
    }

    public static boolean[] getSoundToggles() {
        return soundToggles;
    }

    public static String getBackScreen() {
        return backScreen;
    }

    private static final String[][][] masterZombieFrames = {
        {
            {
                "./../Images/Zombies/Pixel/male/Idle (1).png",
                "./../Images/Zombies/Pixel/male/Idle (2).png",
                "./../Images/Zombies/Pixel/male/Idle (3).png",
                "./../Images/Zombies/Pixel/male/Idle (4).png",
                "./../Images/Zombies/Pixel/male/Idle (5).png",
                "./../Images/Zombies/Pixel/male/Idle (6).png",
                "./../Images/Zombies/Pixel/male/Idle (7).png",
                "./../Images/Zombies/Pixel/male/Idle (8).png",
                "./../Images/Zombies/Pixel/male/Idle (9).png",
                "./../Images/Zombies/Pixel/male/Idle (10).png",
                "./../Images/Zombies/Pixel/male/Idle (11).png",
                "./../Images/Zombies/Pixel/male/Idle (12).png",
                "./../Images/Zombies/Pixel/male/Idle (13).png",
                "./../Images/Zombies/Pixel/male/Idle (14).png",
                "./../Images/Zombies/Pixel/male/Idle (15).png"
            },
            {
                "./../Images/Zombies/Pixel/male/Walk (1).png",
                "./../Images/Zombies/Pixel/male/Walk (2).png",
                "./../Images/Zombies/Pixel/male/Walk (3).png",
                "./../Images/Zombies/Pixel/male/Walk (4).png",
                "./../Images/Zombies/Pixel/male/Walk (5).png",
                "./../Images/Zombies/Pixel/male/Walk (6).png",
                "./../Images/Zombies/Pixel/male/Walk (7).png",
                "./../Images/Zombies/Pixel/male/Walk (8).png",
                "./../Images/Zombies/Pixel/male/Walk (9).png",
                "./../Images/Zombies/Pixel/male/Walk (10).png"
            },
            {
                "./../Images/Zombies/Pixel/male/Attack (1).png",
                "./../Images/Zombies/Pixel/male/Attack (2).png",
                "./../Images/Zombies/Pixel/male/Attack (3).png",
                "./../Images/Zombies/Pixel/male/Attack (4).png",
                "./../Images/Zombies/Pixel/male/Attack (5).png",
                "./../Images/Zombies/Pixel/male/Attack (6).png",
                "./../Images/Zombies/Pixel/male/Attack (7).png",
                "./../Images/Zombies/Pixel/male/Attack (8).png"
            },
            {
                "./../Images/Zombies/Pixel/male/Dead (1).png",
                "./../Images/Zombies/Pixel/male/Dead (2).png",
                "./../Images/Zombies/Pixel/male/Dead (3).png",
                "./../Images/Zombies/Pixel/male/Dead (4).png",
                "./../Images/Zombies/Pixel/male/Dead (5).png",
                "./../Images/Zombies/Pixel/male/Dead (6).png",
                "./../Images/Zombies/Pixel/male/Dead (7).png",
                "./../Images/Zombies/Pixel/male/Dead (8).png",
                "./../Images/Zombies/Pixel/male/Dead (9).png",
                "./../Images/Zombies/Pixel/male/Dead (10).png",
                "./../Images/Zombies/Pixel/male/Dead (11).png",
                "./../Images/Zombies/Pixel/male/Dead (12).png"
            }
        },
        {
            {
                "./../Images/Zombies/Pixel/female/Idle (1).png",
                "./../Images/Zombies/Pixel/female/Idle (2).png",
                "./../Images/Zombies/Pixel/female/Idle (3).png",
                "./../Images/Zombies/Pixel/female/Idle (4).png",
                "./../Images/Zombies/Pixel/female/Idle (5).png",
                "./../Images/Zombies/Pixel/female/Idle (6).png",
                "./../Images/Zombies/Pixel/female/Idle (7).png",
                "./../Images/Zombies/Pixel/female/Idle (8).png",
                "./../Images/Zombies/Pixel/female/Idle (9).png",
                "./../Images/Zombies/Pixel/female/Idle (10).png",
                "./../Images/Zombies/Pixel/female/Idle (11).png",
                "./../Images/Zombies/Pixel/female/Idle (12).png",
                "./../Images/Zombies/Pixel/female/Idle (13).png",
                "./../Images/Zombies/Pixel/female/Idle (14).png",
                "./../Images/Zombies/Pixel/female/Idle (15).png"
            },
            {
                "./../Images/Zombies/Pixel/female/Walk (1).png",
                "./../Images/Zombies/Pixel/female/Walk (2).png",
                "./../Images/Zombies/Pixel/female/Walk (3).png",
                "./../Images/Zombies/Pixel/female/Walk (4).png",
                "./../Images/Zombies/Pixel/female/Walk (5).png",
                "./../Images/Zombies/Pixel/female/Walk (6).png",
                "./../Images/Zombies/Pixel/female/Walk (7).png",
                "./../Images/Zombies/Pixel/female/Walk (8).png",
                "./../Images/Zombies/Pixel/female/Walk (9).png",
                "./../Images/Zombies/Pixel/female/Walk (10).png"
            },
            {
                "./../Images/Zombies/Pixel/female/Attack (1).png",
                "./../Images/Zombies/Pixel/female/Attack (2).png",
                "./../Images/Zombies/Pixel/female/Attack (3).png",
                "./../Images/Zombies/Pixel/female/Attack (4).png",
                "./../Images/Zombies/Pixel/female/Attack (5).png",
                "./../Images/Zombies/Pixel/female/Attack (6).png",
                "./../Images/Zombies/Pixel/female/Attack (7).png",
                "./../Images/Zombies/Pixel/female/Attack (8).png"
            },
            {
                "./../Images/Zombies/Pixel/female/Dead (1).png",
                "./../Images/Zombies/Pixel/female/Dead (2).png",
                "./../Images/Zombies/Pixel/female/Dead (3).png",
                "./../Images/Zombies/Pixel/female/Dead (4).png",
                "./../Images/Zombies/Pixel/female/Dead (5).png",
                "./../Images/Zombies/Pixel/female/Dead (6).png",
                "./../Images/Zombies/Pixel/female/Dead (7).png",
                "./../Images/Zombies/Pixel/female/Dead (8).png",
                "./../Images/Zombies/Pixel/female/Dead (9).png",
                "./../Images/Zombies/Pixel/female/Dead (10).png",
                "./../Images/Zombies/Pixel/female/Dead (11).png",
                "./../Images/Zombies/Pixel/female/Dead (12).png"
            }
        }
    };

    public static String[][][] getMasterZombieFrames() {
        return masterZombieFrames;
    }

    public static void main(String[] args) {
        launch();
    }

    public static void getSecondaryWelcome() throws IOException {
        getWelcome(primaryStage, false);
    }

    /**
     * Launches the game application, starting with the Welcome screen
     * @param primStage is the stage in which the game will take place
     * @throws IOException if Welcome screen fxml file not found
     */
    public void start(Stage primStage) throws IOException {
        primaryStage = primStage;
        getWelcome(primStage, true);
    }

    /**
     * Returns the initialization status of the game
     * @return the game's initialization status
     */
    public static boolean getInit() {
        return init;
    }

    public static void setInit(boolean in) {
        init = in;
    }

    public static String getScreen() {
        return screen;
    }

    /**
     * Returns the name of the player
     * @return the player's name
     */
    public static String getName() {
        return name;
    }

    /**
     * Sets the name of the player
     * @param enteredName is the player's entered name (or default "Comrade")
     */
    public static void setName(String enteredName) {
        name = enteredName;
    }

    /**
     * Gets the selected difficulty the player chose
     * @return the selected difficulty
     */
    public static String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty the game will be in
     * @param diff is the game's difficulty
     */
    public static void setDifficulty(String diff) {
        difficulty = diff;

        if (difficulty.equals("beginner")) {
            Zombie.setDifficultyIndex(0);
        } else if (difficulty.equals("inferno")) {
            Zombie.setDifficultyIndex(1);
        } else {
            Zombie.setDifficultyIndex(2);
        }
    }

    /**
     * Returns the player's starting seed
     * @return player's starting seed
     */
    public static String getStartingSeed() {
        return startingSeed;
    }

    /**
     * Sets the starting seed
     * @param seed is the player's starting seed
     */
    public static void setStartingSeed(String seed) {
        startingSeed = seed;
    }

    /**
     * Gets the current season in game
     * @return the current season in game
     */
    public static String getCurrSeason() {
        return currSeason;
    }

    /**
     * Sets the current season in-game
     * @param season is the new season in-game
     */
    public static void setCurrSeason(String season) {
        currSeason = season;
    }

    /**
     * Gets the player's currently possessed money
     * @return the player's money
     */
    public static double getCash() {
        return cash;
    }

    /**
     * Set the player's currently possessed money
     * @param newCash is the new money value the player has
     */
    public static void setCash(double newCash) {
        cash = newCash;
    }

    /**
     * Gets the player's inventory
     * @return the inventory
     */
    public static int[][] getInventory() {
        return inventory;
    }

    /**
     * Sets the player's inventory
     * @param inv the new inventory
     */
    public static void setInventory(int[][] inv) {
        inventory = inv;
    }

    /**
     * Returns the information for each plot within the farm
     * @return all farm plots
     */
    public static Plot[][] getFarmPlots() {
        return farmPlots;
    }

    /**
     * Sets the player's farm plots
     * @param plots the new farm plots
     */
    public static void setFarmPlots(Plot[][] plots) {
        farmPlots = plots;

        if (plots.length == 4) {
            setExpandedRow();
        } else if (plots[0].length == 5) {
            setExpandedColumn();
        }
    }

    private static void setExpandedColumn() {
        expandedColumn = true;
    }

    private static void setExpandedRow() {
        expandedRow = true;
    }

    public static boolean getExpandedColumn() {
        return expandedColumn;
    }

    public static boolean getExpandedRow() {
        return expandedRow;
    }

    private static final String[] seasons = {"spring", "summer", "fall", "winter"};
    private static int seasonIndex;

    public static void setSeasonIndex(int index) {
        seasonIndex = index;
    }

    /**
     * Advances the day by 1
     */
    public static void advanceDay() {
        dayNum[0]++;

        if (dayNum[0] % 10 == 0) {
            seasonIndex = (seasonIndex + 1 == seasons.length) ? 0 : seasonIndex + 1;
            currSeason = seasons[seasonIndex];
        }
    }

    public static void setDay(int day) {
        dayNum[0] = day;
    }

    /**
     * Gets the current day in game
     * @return the current day in game
     */
    public static int[] getDay() {
        return dayNum;
    }

    /**
     * Returns whether the market is active or not
     * @return the state of the market
     */
    public static boolean getMarketStatus() {
        return marketActive;
    }

    /**
     * Returns the list of currently active bombs on the farm
     * @return current farm bombs
     */
    public static ArrayList<Bomb> getFarmBombs() {
        return farmBombs;
    }

    /**
     * Returns the list of eligible able to be attacked plots
     *
     * @return the list of eligible plots
     */
    public static HashSet<Integer> getAlivePlots() {
        return alivePlots;
    }

    /**
     * Recreates the alive plots stored in a save file
     *
     * @param aliveArray is the collection of alive plots in the save file
     */
    public static void setAlivePlots(int[] aliveArray) {
        for (int alivePlot : aliveArray) {
            alivePlots.add(alivePlot);
        }
    }

    /**
     * Sets currently active bombs in the farm (testing purposes)
     *
     * @param newArr is the new set of active bombs
     */
    public static void setFarmBombs(ArrayList<Bomb> newArr) {
        farmBombs = newArr;
    }

    /**
     * Sets the current screen in-game as specified by a save file
     *
     * @param scr is the current screen in the save file
     */
    public static void setScreen(String scr) {
        screen = scr;
    }

    /**
     * Sets the image file to be used in this instance of the game
     *
     * @param file is the image file to use
     */
    public static void setSaveImage(File file) {
        saveImage = file;
    }

    /**
     * Captures and saves the current state of the farm to the current save image file
     *
     * @param screenshot is the screenshot capture of the farm
     * @throws IOException if there is an issue in writing to the file
     */
    public static void setScreenshot(WritableImage screenshot) throws IOException {
        FileOutputStream out = new FileOutputStream(saveImage);
        out.write(new byte[0]);
        out.close();

        BufferedImage buffImage = SwingFXUtils.fromFXImage(screenshot, null);
        ImageIO.write(buffImage, "png", saveImage);
    }

    /**
     * Retrieves the save file to be used in this instance of the game
     *
     * @return this game's save file
     */
    public static File getSaveFile() {
        return saveFile;
    }

    /**
     * Sets the save file to be used in this instance of the game
     *
     * @param file is the save file to be used
     */
    public static void setSaveFile(File file) {
        saveFile = file;
    }

    /**
     * Retrieves the load flag
     *
     * @return the current load flag
     */
    public static boolean getLoadFlag() {
        return loadFlag;
    }

    /**
     * Sets the load flag (disables the daily alterations of the state of the farm
     * in order to accommodate the loading of a save)
     *
     * @param flag is the new value of the load flag
     */
    public static void setLoadFlag(boolean flag) {
        loadFlag = flag;
    }

    /**
     * Performs the random generation of grass at the bottom of the screen
     *
     * @param scene is the scene of the screen to apply the grass to
     * @param boost is the amount by which to alter the maximum height by
     */
    public static void generateGrass(Scene scene, int boost) {
        HBox grass = (HBox) scene.lookup("#grass");
        Random random = new Random();
        for (int i = 0; i < scene.getWidth() / 11; i++) {
            int limit = random.nextInt(45 + boost) + 75;
            Line line = new Line(0,
                    0, 0, limit);
            line.setStroke(Color.GREEN);
            line.setStrokeWidth(10);
            grass.getChildren().add(line);
        }
    }

    private static ImageView[] clouds = new ImageView[16];
    private static TranslateTransition[] cloudMove = new TranslateTransition[16];
    private static TranslateTransition[] cloudSchmoove = new TranslateTransition[16];
    private static HashSet<Integer> inactiveClouds = new HashSet();

    public static void initClouds(Scene scene) {
        String cloudPath = "./Images/Pixel/Net Found(Edited)/cloud";

        for (int i = 0; i < clouds.length; i++) {
            clouds[i] =
                    new ImageView(
                            new Image(
                                    Main.class.getResourceAsStream(
                                            String.format("%s%d.png", cloudPath, i + 1))));
            clouds[i].setTranslateX(0);
            ((Pane) scene.lookup("#cloudPane")).getChildren().add(clouds[i]);
            inactiveClouds.add(i);

            nyoomPlayers[i] =
                    new MediaPlayer(
                            new Media(
                                    Paths.get(
                                            "src/Zombie-Farming-Game/Media/nyoom.mp3")
                                            .toUri().toString()));
        }

        cycleClouds();
    }

    private static MediaPlayer[] nyoomPlayers = new MediaPlayer[clouds.length];

    private static boolean isNight = false;

    public static void setIsNight() {
        isNight = true;
    }

    private static Timer cloudTimer;

    public static void cycleClouds() {
        cloudTimer = new Timer();
        TimerTask cloudTask;

        cloudTask = new TimerTask() {
            @Override
            public void run() {
                if (inactiveClouds.size() > 0 && Math.random() > .25) {
                    Random rand = new Random();

                    Integer[] inactiveArray = new Integer[inactiveClouds.size()];
                    inactiveClouds.toArray(inactiveArray);

                    int cloudIndex = inactiveArray[rand.nextInt(inactiveClouds.size())];

                    inactiveClouds.remove(cloudIndex);
                    cloudMove[cloudIndex] =
                            new TranslateTransition(
                                    Duration.millis(
                                            (rand.nextInt(4) + 13) * 1000),
                                    clouds[cloudIndex]);
                    cloudMove[cloudIndex].setToX(1800.0);
                    cloudMove[cloudIndex].setAutoReverse(false);
                    cloudMove[cloudIndex].setCycleCount(1);

                    clouds[cloudIndex].setOnMouseClicked(e -> {
                        cloudSchmoove[cloudIndex] =
                                new TranslateTransition(
                                        Duration.millis(
                                                cloudMove[cloudIndex]
                                                        .getDuration().toMillis() / 15),
                                        clouds[cloudIndex]);
                        cloudSchmoove[cloudIndex].setToX(1800.0);
                        cloudSchmoove[cloudIndex].setAutoReverse(false);
                        cloudSchmoove[cloudIndex].setCycleCount(1);

                        cloudMove[cloudIndex].stop();
                        cloudSchmoove[cloudIndex].play();

                        if (soundToggles[1]) {
                            nyoomPlayers[cloudIndex].seek(Duration.millis(500));
                            nyoomPlayers[cloudIndex].play();
                        }

                        cloudSchmoove[cloudIndex].setOnFinished(f -> {
                            clouds[cloudIndex].setTranslateX(0);
                            inactiveClouds.add(cloudIndex);
                        });
                    });

                    if (isNight) {
                        clouds[cloudIndex].setOpacity(.5);
                    } else {
                        clouds[cloudIndex].setOpacity(1);
                    }

                    cloudMove[cloudIndex].setOnFinished(e -> {
                        clouds[cloudIndex].setTranslateX(0);
                        inactiveClouds.add(cloudIndex);
                    });

                    cloudMove[cloudIndex].play();
                }
            }
        };

        cloudTimer.scheduleAtFixedRate(cloudTask, 0, 500);
    }

    public static void stopClouds() {
        cloudTimer.cancel();
    }

    public static void resetClouds(Scene scene) {
        Pane cloudPane = (Pane) scene.lookup("#cloudPane");

        for (ImageView cloud : clouds) {
            cloudPane.getChildren().add(cloud);
        }
    }

    private static final ArrayList<KeyCode> realKonami =
            new ArrayList<>(
                    Arrays.asList(
                            KeyCode.UP,
                            KeyCode.UP,
                            KeyCode.DOWN,
                            KeyCode.DOWN,
                            KeyCode.LEFT,
                            KeyCode.RIGHT,
                            KeyCode.LEFT,
                            KeyCode.RIGHT,
                            KeyCode.A,
                            KeyCode.B
                    ));

    private static ArrayList<KeyCode> konamiCheck = new ArrayList<>();

    private static Scene keyScene;

    private static EventHandler<KeyEvent> konamiEvent = new EventHandler<>() {
        @Override
        public void handle(KeyEvent e) {
            konamiCheck.add(e.getCode());

            if (konamiCheck.size() == realKonami.size()) {
                if (Arrays.equals(realKonami.toArray(), konamiCheck.toArray())) {
                    Popup pop = new Popup();
                    pop.getContent().add(
                            new ImageView(
                                    new Image(
                                            Main.class.getResourceAsStream(
                                                    "Media/kermitgif.gif"))));

                    keyScene.setOnMouseMoved(f -> pop.hide());

                    pop.show(primaryStage);
                }

                konamiCheck.remove(konamiCheck.size() - 1);
            }
        }
    };

    private static MediaPlayer sansPlayer =
            new MediaPlayer(
                    new Media(
                            Paths.get("src/Zombie-Farming-Game/Media/just-sans-talking.mp3")
                    .toUri().toString()));;

    private static final ArrayList<KeyCode> realSans =
            new ArrayList<>(
                    Arrays.asList(
                            KeyCode.S,
                            KeyCode.A,
                            KeyCode.N,
                            KeyCode.S
                    ));

    private static ArrayList<KeyCode> sansCheck = new ArrayList<>();

    private static EventHandler<KeyEvent> sansEvent = new EventHandler<>() {
        @Override
        public void handle(KeyEvent e) {
            sansCheck.add(e.getCode());

            if (sansCheck.size() == realSans.size()) {
                if (Arrays.equals(realSans.toArray(), sansCheck.toArray())) {
                    Popup pop = new Popup();
                    ImageView sansView =
                            new ImageView(
                                    new Image(
                                            Main.class.getResourceAsStream(
                                                    "Images/NetFound/sans.png")));
                    sansView.setScaleX(.75);
                    sansView.setScaleY(.75);

                    pop.getContent().add(sansView);
                    pop.show(primaryStage);

                    anthemPlayer.setMute(true);
                    elevatorPlayer.setMute(true);

                    if (!soundToggles[1]) {
                        sansPlayer.setMute(true);
                    }
                    sansPlayer.seek(Duration.millis(0));
                    sansPlayer.play();

                    sansPlayer.setOnEndOfMedia(() -> {
                        pop.hide();
                        anthemPlayer.setMute(false);
                        elevatorPlayer.setMute(false);
                    });
                }

                sansCheck.remove(0);
            }
        }
    };

    /**
     * Creates layout for WelcomeScreen.
     * Final outcome is BorderPane with the top set to title and
     * start button, middle set to grass graphics, and bottom set to dirt graphics.
     * @param primaryStage the stage upon which we are setting our design
     * @param initClouds is whether to begin or continue the clouds animation
     * @throws IOException if Welcome screen fxml file not found
     */
    public static void getWelcome(Stage primaryStage, boolean initClouds) throws IOException {
        anthemPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        elevatorPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // anthemPlayer.setMute(true);
        // elevatorPlayer.setMute(true);

        backScreen = "welcome";

        if (!anthemPlayer.getStatus().equals(MediaPlayer.Status.PLAYING) && soundToggles[0]) {
            elevatorPlayer.pause();
            anthemPlayer.play();
        }

        Parent root = FXMLLoader.load(Main.class.getResource("FXML/WelcomeScreen.fxml"));
        primaryStage.setTitle("Welcome to AgriCorp!");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        primaryStage.setScene(scene);
        primaryStage.show();
        generateGrass(scene, 50);

        if (initClouds) {
            initClouds(scene);
        } else {
            resetClouds(scene);
        }
    }

    public static void getCredits() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/creditsScreen.fxml"));
        primaryStage.setTitle("Welcome to AgriCorp!");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        primaryStage.setScene(scene);
        primaryStage.show();
        generateGrass(scene, 50);
    }

    private static MediaPlayer anthemPlayer =
            new MediaPlayer(
                    new Media(
                            Paths.get("src/Zombie-Farming-Game/Media/Anthem.mp3")
                    .toUri().toString()));

    private static MediaPlayer elevatorPlayer =
            new MediaPlayer(
                    new Media(
                            Paths.get("src/Zombie-Farming-Game/Media/elevator.mp3")
                    .toUri().toString()));;

    public static MediaPlayer getAnthemPlayer() {
        return anthemPlayer;
    }

    public static MediaPlayer getElevatorPlayer() {
        return elevatorPlayer;
    }

    /**
     * Loads the load/save screen immediately upon clicking start in the
     * Welcome screen
     * NOTE: Will only be called once
     * @throws IOException if fxml file not found
     */
    public static void getLoadSave() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/LoadSaveScreen.fxml"));
        primaryStage.setTitle("Dead Harvest");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        backScreen = "load";

        primaryStage.setScene(scene);
        primaryStage.show();
        generateGrass(scene, -10);

        if (animationToggles[1]) {
            resetClouds(scene);
        }
    }

    /**
     * Loads the configuration screen immediately upon choosing to begin a new game
     * NOTE: Will only be called once
     * @throws IOException if fxml file not found
     */
    public static void getConfig() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/configScreen.fxml"));
        primaryStage.setTitle("Dead Harvest");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        backScreen = "config";

        primaryStage.setScene(scene);
        primaryStage.show();
        generateGrass(scene, 0);

        if (animationToggles[1]) {
            resetClouds(scene);
        }
    }

    /**
     * Loads the farm screen
     * NOTE: Will be called multiple times
     * @throws IOException if fxml file cannot be found
     */
    public static void getFarm() throws IOException {
        if (!init && soundToggles[0]) {
            elevatorPlayer.pause();
            anthemPlayer.play();
        }

        isNight = false;
        screen = "farm";
        Parent root;

        if (expandedRow) {
            root = FXMLLoader.load(Main.class.getResource("FXML/expandedRowFarm.fxml"));
        } else if (expandedColumn) {
            root = FXMLLoader.load(Main.class.getResource("FXML/expandedColumnFarm.fxml"));
        } else {
            root = FXMLLoader.load(Main.class.getResource("FXML/farm.fxml"));
        }

        primaryStage.setTitle("Your Farm");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        backScreen = "farm";

        primaryStage.setScene(scene);
        primaryStage.show();

        if (animationToggles[1]) {
            resetClouds(scene);
        }
    }

    /**
     * Loads the market screen
     * NOTE: Will be called multiple times
     * @throws IOException if fxml file cannot be found
     */
    public static void getMarket() throws IOException {
        if (soundToggles[0]) {
            anthemPlayer.pause();
            elevatorPlayer.play();
        }

        marketActive = true;
        screen = "market";
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/MarketScreen.fxml"));
        primaryStage.setTitle("Your Market");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        backScreen = "market";

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void getSettings() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/settingsScreen.fxml"));
        primaryStage.setTitle("Your Settings");
        Scene scene = new Scene(root, 1500, 900);
        keyScene = scene;

        scene.addEventHandler(KeyEvent.KEY_RELEASED, konamiEvent);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, sansEvent);

        primaryStage.setScene(scene);
        primaryStage.show();

        generateGrass(scene, 50);

        if (animationToggles[1]) {
            resetClouds(scene);
        }
    }

    /**
     * Loads the market screen
     * NOTE: Will be called multiple times
     * @throws IOException if fxml file cannot be found
     */
    public static void getEndScreen() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("FXML/EndScreen.fxml"));
        primaryStage.setTitle("YOU DEAD");
        primaryStage.setScene(new Scene(root, 1500, 900));
        primaryStage.show();
    }
}
