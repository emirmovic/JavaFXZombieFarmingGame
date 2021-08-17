package game.Controller;

import game.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;

public class MarketScreenController {
    private static final int CARROT_BASE_PRICE = 80;
    private static final int BROCCOLI_BASE_PRICE = 70;
    private static final int CORN_BASE_PRICE = 100;
    private static final int BOMB_BASE_PRICE = 200;
    private static final int POTION_BASE_PRICE = 200;
    private static final int PLOTS_BASE_PRICE = 100;
    private static int fertPrice = 0;
    private static int tractorPrice = 5;

    public static final int WEAK_BASE_PRICE = 200;
    public static final int STRONG_BASE_PRICE = 300;
    public static final double WEAK_CHANCE = 0.60;
    public static final double STRONG_CHANCE = 0.80;
    private static int tractorFactor;
    private static int harvestMax = 5;

    private double finalCarrotPrice;
    private double finalBroccoliPrice;
    private double finalCornPrice;
    private double finalBombPrice;
    private double finalPotionPrice;
    private double carrotBuyingPrice;
    private double broccoliBuyingPrice;
    private double cornBuyingPrice;
    private double bombBuyingPrice;
    private double potionBuyingPrice;
    private double irrigationBuyingPrice;
    private double currentMoney;

    private double plotsBuyingPrice;
    private double finalWeakPrice;
    private double finalStrongPrice;

    private static int weakDaysLeft = 0;
    private static int strongDaysLeft = 0;
    private static int wateringAmount = 7;

    private Text weakDaysText = new Text();
    private Text strongDaysText = new Text();
    private Text bombsPlantedText = new Text();
    private Text farmUpgradeText = new Text();
    private Text tractorText = new Text();
    private Text wateringText = new Text();

    private final Label listingLabel = new Label("Listing Info:");

    private static String[] itemSelected = new String[1];
    private int[][] inventory = Main.getInventory();

    private HBox buyPriceBox = new HBox();
    private HBox sellPriceBox = new HBox();
    private Text buyPriceText = new Text();
    private Text sellPriceText = new Text();
    private Text ownedText = new Text();

    private final String[][] listingInfo = {
        {
            "Higher health in Spring = Fall > Summer > Winter. Highest value in Winter.",
            "Higher health in Spring = Fall > Winter > Summer. Highest value in Winter.",
            "Higher health in Spring > Summer = Fall > Winter. Highest value in Winter."
        },
        {
            "Highest value in Winter.",
            "Highest value in Winter.",
            "Highest value in Winter."
        },
        {
            "Half the price of broccoli.",
            "Half the price of carrots.",
            "Half the price of corn."
        },
        {
            "Will not be attacked by zombies. Protects all plants NSEW of it "
                    + "from zombie attacks. Will have to be at a mature state to activate "
                    + "and it will have to be watered appropriately. Will die after two days.",
            "Protects plant from zombie attacks. Effect will disappear after 2 days. "
                    + "Plants harvested with this effect (B-G) will be sold at half price.",
            "Doubles the growth rate of the effected plant. Yields increase as fertilizer levels"
                    + "increase (max level is 3)."
        },
        {
            "Has a 60% chance to automatically harvest when a plant reaches maturity and "
                    + "will always auto sell. Pay rate of 100 per day.",
            "Has an 80% chance to automatically harvest when a plant reaches maturity and "
                    + "will always auto sell. Pay rate of 150 per day.",
            "Increases overall daily watering amount by 1."
        },
        {
            "Three additional plots for your farm!",
            "Five additional plots for your farm!",
            "You have bought all expansions!",
            "Increases overall daily harvesting limit by 2."
        }
    };

    @FXML
    private Button fertButton;

    @FXML
    private Button tractorBtn;

    @FXML
    private Button brocSeedButton;

    @FXML
    private Button carSeedButton;

    @FXML
    private Button corSeedButton;

    @FXML
    private Button brocButton;

    @FXML
    private Button carButton;

    @FXML
    private Button corButton;

    @FXML
    private Button bgBrocButton;

    @FXML
    private Button bgCarButton;

    @FXML
    private Button bgCorButton;

    @FXML
    private Button bombButton;

    @FXML
    private Button potionButton;

    @FXML
    private Button wWorkerButton;

    @FXML
    private Button sWorkerButton;

    @FXML
    private Button farmUpgradeButton;

    @FXML
    private Button buyButton;

    @FXML
    private Button sellButton;

    @FXML
    private Text money;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Pane listingPane;

    @FXML
    private Text listingText;

    /**
     * Initializes the Market setup.
     * First we calculate crop prices based on base price, difficulty, and season.
     * Then, we setup the player's money and inventory items that need to be displayed.
     * Finally, we clear border styling for each of the buttons (when a button is
     * selected it will have a green outline).
     */
    @FXML
    private void initialize() {
        currentMoney = Main.getCash();
        setupCropPrices();

        inventory[4][2] = wateringAmount;

        if (weakDaysLeft > 0 || strongDaysLeft > 0) {
            if (inventory[1][0] > 0) { //Broccoli
                double addBroccMoney = (finalBroccoliPrice * inventory[1][0]);
                currentMoney += addBroccMoney;
                inventory[1][0] = 0;
            }
            if (inventory[1][1] > 0) { //Carrot
                double addCarrMoney = (finalCarrotPrice * inventory[1][1]);
                currentMoney += addCarrMoney;
                inventory[1][1] = 0;
            }
            if (inventory[1][2] > 0) { //Corn
                double addCornMoney = (finalCornPrice * inventory[1][2]);
                currentMoney += addCornMoney;
                inventory[1][2] = 0;
            }
        }

        if (Main.getExpandedRow()) {
            farmUpgradeButton.setDisable(true);
        }

        money.setText("" + new DecimalFormat("#####.##").format(currentMoney));

        clearStyles();
        clearModeStyles();
        setUpPriceBoxes(buyPriceBox, "buy");
        setUpPriceBoxes(sellPriceBox, "sell");
        ownedText.setFont(new Font(24.0));
        ownedText.setLayoutX(14.0);
        ownedText.setLayoutY(118.0);

        buyPriceText.setFont(new Font(24.0));
        sellPriceText.setFont(new Font(24.0));

        bombsPlantedText.setFont(new Font(24.0));
        bombsPlantedText.setLayoutX(14.0);
        bombsPlantedText.setLayoutY(158.0);

        weakDaysText.setFont(new Font(24.0));
        weakDaysText.setLayoutX(14.0);
        weakDaysText.setLayoutY(118.0);

        strongDaysText.setFont(new Font(24.0));
        strongDaysText.setLayoutX(14.0);
        strongDaysText.setLayoutY(118.0);

        farmUpgradeText.setFont(new Font(24.0));
        farmUpgradeText.setLayoutX(14.0);
        farmUpgradeText.setLayoutY(118.0);

        wateringText.setFont(new Font(24.0));
        wateringText.setLayoutX(14.0);
        wateringText.setLayoutY(118.0);

        tractorText.setFont(new Font(24.0));
        tractorText.setLayoutX(14.0);
        tractorText.setLayoutY(118.0);

        listingLabel.setLayoutX(14.0);
        listingLabel.setLayoutY(14.0);
        listingLabel.setFont(new Font("System Bold Italic", 25.0));

        if (weakDaysLeft < 0) {
            weakDaysLeft = 0;
        }

        if (strongDaysLeft < 0) {
            strongDaysLeft = 0;
        }
    }

    /**
     * Calculates buying and selling crop prices based on base price, difficulty,
     * and season. Then, it sets each in their respective text item.
     */
    private void setupCropPrices() {
        String difficulty = Main.getDifficulty();
        String currentSeason = Main.getCurrSeason();
        double diffMultiplier;
        double seasonMultiplier;
        double farmerMultiplier;

        if (difficulty.equals("beginner")) {
            diffMultiplier = 0.1;
            farmerMultiplier = 0.5;

            fertPrice = 100;
            irrigationBuyingPrice = 200;
        } else if (difficulty.equals("inferno")) {
            diffMultiplier = 0.5;
            farmerMultiplier = 0.75;
            fertPrice = 150;
            irrigationBuyingPrice = 350;
        } else {
            diffMultiplier = 0.2;
            farmerMultiplier = 1.00;
            fertPrice = 200;
            irrigationBuyingPrice = 500;
        }

        if (currentSeason.equals("summer")) {
            seasonMultiplier = 0;
        } else if (currentSeason.equals("fall")) {
            seasonMultiplier = 0.5;
        } else if (currentSeason.equals("winter")) {
            seasonMultiplier = 0.75;
        } else {
            seasonMultiplier = 0.25;
        }

        finalWeakPrice = (WEAK_BASE_PRICE * farmerMultiplier);
        finalStrongPrice = (STRONG_BASE_PRICE * farmerMultiplier);
        plotsBuyingPrice =
                PLOTS_BASE_PRICE * Main.getFarmPlots().length * Main.getFarmPlots()[0].length;

        finalCarrotPrice = CARROT_BASE_PRICE + ((CARROT_BASE_PRICE * diffMultiplier)
                + (CARROT_BASE_PRICE * seasonMultiplier));
        finalCarrotPrice = Math.round(finalCarrotPrice * 100.0) / 100.0;
        finalBroccoliPrice = BROCCOLI_BASE_PRICE + ((BROCCOLI_BASE_PRICE * diffMultiplier)
                + (BROCCOLI_BASE_PRICE * seasonMultiplier));
        finalBroccoliPrice = Math.round(finalBroccoliPrice * 100.0) / 100.0;
        finalCornPrice = CORN_BASE_PRICE + ((CORN_BASE_PRICE * diffMultiplier)
                + (CORN_BASE_PRICE * seasonMultiplier));
        finalCornPrice = Math.round(finalCornPrice * 100.0) / 100.0;
        finalBombPrice = BOMB_BASE_PRICE + ((BOMB_BASE_PRICE * diffMultiplier)
                + (BOMB_BASE_PRICE * seasonMultiplier));
        finalBombPrice = Math.round(finalBombPrice * 100.0) / 100.0;
        finalPotionPrice = POTION_BASE_PRICE + ((POTION_BASE_PRICE * diffMultiplier)
                + (POTION_BASE_PRICE * seasonMultiplier));
        finalPotionPrice = Math.round(finalPotionPrice * 100.0) / 100.0;

        carrotBuyingPrice = Math.round((finalCarrotPrice * 1.25) * 100.0) / 100.0;
        broccoliBuyingPrice = Math.round((finalBroccoliPrice * 1.25) * 100.0) / 100.0;
        cornBuyingPrice = Math.round((finalCornPrice * 1.25) * 100.0) / 100.0;
        bombBuyingPrice = Math.round((finalBombPrice * 1.25) * 100.0) / 100.0;
        potionBuyingPrice = Math.round((finalPotionPrice * 1.25) * 100.0) / 100.0;
        tractorPrice = 200;
    }

    /**
     * Sets up the information boxes to be used throughout the player's interactions
     * with the market (selling/buying/owned, etc.)
     *
     * @param priceBox is node of the price box (for buying/selling price)
     * @param type     is the type of price being dealt with
     */
    private void setUpPriceBoxes(HBox priceBox, String type) {
        priceBox.setPrefHeight(36.0);
        priceBox.setPrefWidth(200.0);
        priceBox.setLayoutX(14.0);
        priceBox.setLayoutY(50.0);

        Text typePrice = new Text();
        typePrice.setText((type.equals("buy")) ? "Buy Price:" : "Sell Price:");
        typePrice.setWrappingWidth((type.equals("buy")) ? 103.0474853515625 : 103.491943359375);
        typePrice.setFont(new Font(24.0));

        priceBox.getChildren().add(typePrice);

        ImageView ccView =
                new ImageView(
                        new Image(
                                getClass().getResourceAsStream(
                                        "./../Images/Pixel/Drawn/drawnCC.png")));

        ccView.setFitHeight(38.0);
        ccView.setFitWidth(38.0);
        ccView.setPreserveRatio(true);
        ccView.setPickOnBounds(true);

        priceBox.getChildren().add(ccView);

        if (type.equals("buy")) {
            priceBox.getChildren().add(buyPriceText);
        } else {
            priceBox.getChildren().add(sellPriceText);
        }
    }

    /**
     * Handles the switching of modes when an item is clicked, displayed
     * appropriate, respective information about the current status of the
     * crops in relation to the player
     *
     * @param clickedButton is the item button selected
     */
    @FXML
    private void switchItem(MouseEvent clickedButton) {
        Button itemButton = (Button) clickedButton.getSource();
        clearStyles();
        itemButton.setStyle("-fx-border-color: green");
        listingPane.getChildren().removeAll(listingPane.getChildren());
        listingPane.getChildren().add(listingLabel);
        buyButton.setDisable(false);
        sellButton.setDisable(false);
        quantityTextField.setDisable(false);
        quantityTextField.setText("");

        double tempPrice;
        String tempText;

        switch (itemButton.getId()) {
        case "brocSeedButton":
            switchHelper(false, broccoliBuyingPrice, true, ownedText, "",
                    new int[]{0, 0}, "broccoliSeed");
            break;
        case "carSeedButton":
            switchHelper(false, carrotBuyingPrice, true, ownedText, "",
                    new int[]{0, 1}, "carrotSeed");
            break;
        case "corSeedButton":
            switchHelper(false, cornBuyingPrice, true, ownedText, "",
                    new int[]{0, 2}, "cornSeed");
            break;
        case "brocButton":
            switchHelper(true, finalBroccoliPrice, true, ownedText, "",
                    new int[]{1, 0}, "broccoli");
            break;
        case "carButton":
            switchHelper(true, finalCarrotPrice, true, ownedText, "",
                    new int[]{1, 1}, "carrot");
            break;
        case "corButton":
            switchHelper(true, finalCornPrice, true, ownedText, "",
                    new int[]{1, 2}, "corn");
            break;
        case "bgBrocButton":
            tempPrice = Math.round((finalBroccoliPrice * .5) * 100) / 100.0;
            switchHelper(true, tempPrice, true, ownedText, "",
                    new int[]{2, 0}, "bgBroccoli");
            break;
        case "bgCarButton":
            tempPrice = Math.round((finalCarrotPrice * .5) * 100) / 100.0;
            switchHelper(true, tempPrice, true, ownedText, "",
                    new int[]{2, 1}, "bgCorn");
            break;
        case "bgCorButton":
            tempPrice = Math.round((finalCornPrice * .5) * 100) / 100.0;
            switchHelper(true, tempPrice, true, ownedText, "",
                    new int[]{2, 2}, "bgCorn");
            break;
        case "bombButton":
            tempText = "Bombs Planted: " + Main.getFarmBombs().size();
            switchHelper(false, bombBuyingPrice, true, bombsPlantedText, tempText,
                    new int[]{3, 0}, "bombSeed");
            break;
        case "potionButton":
            switchHelper(false, potionBuyingPrice, true, ownedText, "",
                    new int[]{3, 1}, "potion");
            break;
        case "wWorkerButton":
            tempText = "Days left: " + weakDaysLeft;
            switchHelper(false, finalWeakPrice, false, weakDaysText, tempText,
                    new int[]{4, 0}, "weakWorker");
            break;
        case "sWorkerButton":
            tempText = "Days left: " + strongDaysLeft;
            switchHelper(false, finalStrongPrice, false, strongDaysText, tempText,
                    new int[]{4, 1}, "strongWorker");
            break;
        case "fertButton":
            switchHelper(false, fertPrice, true, ownedText, "",
                    new int[]{3, 2}, "Fertilizer");
            break;
        case "btIrrigation":
            tempText = "Watering limit: " + inventory[4][2];
            switchHelper(false, irrigationBuyingPrice, false, wateringText, tempText,
                    new int[]{4, 2}, "Irrigation");
            break;
        case "FarmUpgradeButton":
            tempText = "Number of plots: "
                    + Main.getFarmPlots().length * Main.getFarmPlots()[0].length;
            quantityTextField.setText("1");
            quantityTextField.setDisable(true);
            if (Main.getExpandedColumn()) {
                switchHelper(false, plotsBuyingPrice, false, farmUpgradeText, tempText,
                        new int[]{5, 1}, "farmUpgrade");
            } else {
                switchHelper(false, plotsBuyingPrice, false, farmUpgradeText, tempText,
                        new int[]{5, 0}, "farmUpgrade");
            }
            break;
        case "tractorBtn":
            tempText = "Harvesting limit: " + harvestMax;

            switchHelper(false, tractorPrice, false, tractorText, tempText,
                    new int[]{5, 3}, "Tractor");
            break;
        default:
            break;
        }
    }

    /**
     * Helper method to handle the disabling of buy/sell buttons and the display of
     * selected item information
     *
     * @param disableBuy   is whether to disable the buy button (true) or sell button (false)
     * @param price        is the price of the item in buy/sell mode
     * @param setOwnedText is whether to set/display the owned amount of this item using the
     *                     ownedText element
     * @param textBox      is the specialized text box to display information in
     * @param text         is the text to display in the specialized text box
     * @param coordinates  is the coordinates of the item in the inventory
     * @param item         is the current item
     */
    private void switchHelper(
            boolean disableBuy,
            double price,
            boolean setOwnedText,
            Text textBox,
            String text,
            int[] coordinates,
            String item) {
        if (disableBuy) {
            buyButton.setDisable(true);
            sellPriceText.setText("" + price);
            listingPane.getChildren().add(sellPriceBox);
        } else {
            sellButton.setDisable(true);
            buyPriceText.setText("" + price);
            listingPane.getChildren().add(buyPriceBox);
        }

        if (setOwnedText && text.equals("")) {
            ownedText.setText("Owned: " + inventory[coordinates[0]][coordinates[1]]);
            listingPane.getChildren().add(ownedText);
        } else {
            if (setOwnedText) {
                ownedText.setText("Owned: " + inventory[coordinates[0]][coordinates[1]]);
                listingPane.getChildren().add(ownedText);
            }

            textBox.setText(text);
            listingPane.getChildren().add(textBox);
        }

        listingText.setText(listingInfo[coordinates[0]][coordinates[1]]);

        itemSelected[0] = item;
    }

    /**
     * Handles the buying operation for checkout
     */
    @FXML
    private void handleBuy() {
        if (!handleCheckoutErrors()) {
            int quantity = Integer.parseInt(quantityTextField.getText());

            if (itemSelected[0].contains("Seed")) {
                handleCropBuy(itemSelected[0].split("S")[0], quantity);
            } else {
                handleUtilityBuy(itemSelected[0], quantity);
            }
        }
    }

    /**
     * Handles the buying operation of crop seeds for checkout
     *
     * @param selectedItem is the selected item for purchase
     * @param quantity     is the amount for purchase
     */
    private void handleCropBuy(String selectedItem, int quantity) {
        boolean success = false;

        if (selectedItem.equals("carrot")) {
            if (currentMoney >= carrotBuyingPrice * quantity
                    && inventory[0][1] + quantity <= 3) {
                currentMoney -= carrotBuyingPrice * quantity;
                displayAlert(false, 4);
                inventory[0][1] += quantity;
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                ownedText.setText("Owned: " + inventory[0][1]);
                success = true;
            } else if (currentMoney < carrotBuyingPrice * quantity
                    && inventory[0][1] + quantity <= 3) {
                displayAlert(true, 0);
            } else if (currentMoney >= carrotBuyingPrice * quantity
                    && inventory[0][1] + quantity > 3) {
                displayAlert(true, 1);
            } else {
                displayAlert(true, 2);
            }
        } else if (selectedItem.equals("broccoli")) {
            if (currentMoney >= broccoliBuyingPrice * quantity
                    && inventory[0][0] + quantity <= 3) {
                currentMoney -= broccoliBuyingPrice * quantity;
                displayAlert(false, 4);
                inventory[0][0] += quantity;
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                ownedText.setText("Owned: " + inventory[0][1]);
                success = true;
            } else if (currentMoney < broccoliBuyingPrice * quantity
                    && inventory[0][0] + quantity <= 3) {
                displayAlert(true, 0);
            } else if (currentMoney >= broccoliBuyingPrice * quantity
                    && inventory[0][0] + quantity > 3) {
                displayAlert(true, 1);
            } else {
                displayAlert(true, 2);
            }
        } else if (selectedItem.equals("corn")) {
            if (currentMoney >= cornBuyingPrice * quantity
                    && inventory[0][2] + quantity <= 3) {
                currentMoney -= cornBuyingPrice * quantity;
                displayAlert(false, 4);
                inventory[0][2] += quantity;
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                ownedText.setText("Owned: " + inventory[0][1]);
                success = true;
            } else if (currentMoney < cornBuyingPrice * quantity
                    && inventory[0][2] + quantity <= 3) {
                displayAlert(true, 0);
            } else if (currentMoney >= cornBuyingPrice * quantity
                    && inventory[0][2] + quantity > 3) {
                displayAlert(true, 1);
            } else {
                displayAlert(true, 2);
            }
        } else if (selectedItem.equals("bomb")) {
            if (currentMoney >= bombBuyingPrice * quantity
                    && inventory[3][0] + quantity <= 1) {
                currentMoney -= bombBuyingPrice * quantity;
                displayAlert(false, 4);
                inventory[3][0] += quantity;
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                ownedText.setText("Owned: " + inventory[0][1]);
                success = true;
            } else if (currentMoney < bombBuyingPrice * quantity
                    && inventory[3][0] + quantity <= 31) {
                displayAlert(true, 0);
            } else if (currentMoney >= bombBuyingPrice * quantity
                    && inventory[3][0] + quantity > 1) {
                displayAlert(true, 1);
            } else {
                displayAlert(true, 2);
            }
        }

        if (success && Main.getSoundToggles()[1]) {
            MediaPlayer ching =
                    new MediaPlayer(
                            new Media(
                                    Paths.get(
                                            "src/Zombie-Farming-Game/Media/ka-ching.mp3")
                                            .toUri().toString()));
            ching.play();
        }
    }

    /**
     * Handles the buying operation of non-crop items for checkout
     *
     * @param selectedItem is the selected item for purchase
     * @param quantity     is the amount for purchase
     */
    private void handleUtilityBuy(String selectedItem, int quantity) {
        boolean success = false;

        if (selectedItem.contains("weak")) {
            if (currentMoney >= finalWeakPrice * quantity) {
                currentMoney -= finalWeakPrice * quantity;
                weakDaysLeft += (quantity);
                displayAlert(false, 4);
                //weakDaysLeft += 1;
                weakDaysText.setText("Days left: " + weakDaysLeft);
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                //newWeakWorker = true;
                success = true;
            } else {
                displayAlert(true, 0);
            }
        } else if (selectedItem.contains("strong")) {
            if (currentMoney >= finalStrongPrice * quantity) {
                currentMoney -= finalStrongPrice * quantity;
                strongDaysLeft += quantity;
                displayAlert(false, 4);
                //strongDaysLeft += 1;
                strongDaysText.setText("Days left: " + strongDaysLeft);
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                //newStrongWorker = true;
                success = true;
            } else {
                displayAlert(true, 0);
            }
        } else if (selectedItem.equals("potion")) {
            if (currentMoney >= potionBuyingPrice * quantity
                    && inventory[3][1] + quantity <= 3) {
                currentMoney -= potionBuyingPrice * quantity;
                displayAlert(false, 4);
                inventory[3][1] += quantity;
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                ownedText.setText("Owned: " + inventory[0][1]);
                success = true;
            } else if (currentMoney < potionBuyingPrice * quantity
                    && inventory[3][1] + quantity <= 3) {
                displayAlert(true, 0);
            } else if (currentMoney >= potionBuyingPrice * quantity
                    && inventory[3][1] + quantity > 3) {
                displayAlert(true, 1);
            } else {
                displayAlert(true, 2);
            }
        } else if (selectedItem.equals("Fertilizer")) {
            if (currentMoney >= fertPrice * quantity
                    && inventory[3][2] + quantity <= 3) {
                currentMoney -= fertPrice * quantity;
                displayAlert(false, 4);
                inventory[3][2] += quantity;
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                ownedText.setText("Owned: " + inventory[3][2]);
                success = true;
            } else if (currentMoney < fertPrice * quantity
                    && inventory[3][2] + quantity <= 31) {
                displayAlert(true, 0);
            } else if (currentMoney >= fertPrice * quantity
                    && inventory[3][2] + quantity > 1) {
                displayAlert(true, 1);
            } else {
                displayAlert(true, 2);
            }
        } else if (selectedItem.equals("Irrigation")) {
            if (currentMoney >= irrigationBuyingPrice * quantity) {
                currentMoney -= irrigationBuyingPrice * quantity;
                wateringAmount += quantity;
                inventory[4][2] = wateringAmount;
                displayAlert(false, 4);
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                success = true;
                wateringText.setText("Watering limit: " + inventory[4][2]);
            }
        } else if (selectedItem.contains("Upgrade")) {
            if (currentMoney >= plotsBuyingPrice * quantity) {
                currentMoney -= plotsBuyingPrice * quantity;
                displayAlert(false, 4);
                money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                recreatePlots();

                if (Main.getExpandedColumn()) {
                    plotsBuyingPrice = PLOTS_BASE_PRICE * Main.getFarmPlots().length
                            * Main.getFarmPlots()[0].length;
                    buyPriceText.setText("" + plotsBuyingPrice);
                    listingText.setText(listingInfo[5][1]);
                }

                if (Main.getExpandedRow()) {
                    buyButton.setDisable(true);
                    farmUpgradeButton.setDisable(true);
                    listingText.setText(listingInfo[5][2]);
                }
                success = true;
                farmUpgradeText.setText(
                        "Number of plots: "
                                + Main.getFarmPlots().length * Main.getFarmPlots()[0].length);
            } else {
                displayAlert(true, 0);
            }
        } else if (selectedItem.equals("Tractor")) {
            if (currentMoney >= tractorPrice * quantity) {
                currentMoney -= tractorPrice * quantity;
                tractorFactor += quantity;
                displayAlert(false, 4);
                money.setText(" " + new DecimalFormat("#####.##").format(currentMoney));

                harvestMax = 5 + tractorFactor * 2;
                tractorText.setText("Harvesting limit: " + harvestMax);
                success = true;
            } else if (currentMoney < tractorPrice * quantity) {
                displayAlert(true, 0);
            } else {
                displayAlert(true, 2);
            }
        }

        if (success && Main.getSoundToggles()[1]) {
            MediaPlayer ching =
                    new MediaPlayer(
                            new Media(
                                    Paths.get(
                                            "src/Zombie-Farming-Game/Media/ka-ching.mp3")
                                            .toUri().toString()));
            ching.play();
        }
    }

    private void recreatePlots() {
        int rowMax = (Main.getExpandedColumn()) ? 4 : 3;
        game.data.Plot[][] newPlots = new game.data.Plot[rowMax][5];

        for (int i = 0; i < rowMax; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 3) {
                    newPlots[i][j] = new game.data.Plot(i, j, false, "emptyPlot:" + i + j);
                } else if (j == 4 && !Main.getExpandedColumn()) {
                    newPlots[i][j] = new game.data.Plot(i, j, false, "emptyPlot:" + i + j);
                } else {
                    newPlots[i][j] = Main.getFarmPlots()[i][j];
                }
            }
        }

        Main.setFarmPlots(newPlots);

        HashSet<Integer> tempAliveSet = new HashSet<>();

        Integer[] tempAlive = new Integer[Main.getAlivePlots().size()];
        Main.getAlivePlots().toArray(tempAlive);

        for (Integer plot : tempAlive) {
            Main.getAlivePlots().remove(plot);

            int row = plot / 4;
            int col = plot % 5;

            tempAliveSet.add(row * 5 + col);
        }

        tempAliveSet.toArray(tempAlive);
        Main.getAlivePlots().addAll(Arrays.asList(tempAlive));
    }

    /**
     * Handles the selling operation of crops for checkout
     */
    @FXML
    private void handleSell() {
        if (!handleCheckoutErrors()) {
            boolean success = false;
            String selectedItem = itemSelected[0];
            int quantity = Integer.parseInt(quantityTextField.getText());

            if (selectedItem.equals("carrot")) {
                if (quantity <= inventory[1][1]) {
                    currentMoney += finalCarrotPrice * quantity;
                    displaySellAlert(false);
                    inventory[1][1] -= quantity;
                    money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                    ownedText.setText("Owned: " + inventory[1][1]);
                    success = true;
                } else {
                    displaySellAlert(true);
                }
            } else if (selectedItem.equals("broccoli")) {
                if (quantity <= inventory[1][0]) {
                    currentMoney += finalBroccoliPrice * quantity;
                    displaySellAlert(false);
                    inventory[1][0] -= quantity;
                    money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                    ownedText.setText("Owned: " + inventory[1][0]);
                    success = true;
                } else {
                    displaySellAlert(true);
                }
            } else if (selectedItem.equals("corn")) {
                if (quantity <= inventory[1][2]) {
                    currentMoney += finalCornPrice * quantity;
                    displaySellAlert(false);
                    inventory[1][2] -= quantity;
                    money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                    ownedText.setText("Owned: " + inventory[1][2]);
                    success = true;
                } else {
                    displaySellAlert(true);
                }
            } else if (selectedItem.equals("bgCarrot")) {
                if (quantity <= inventory[2][1]) {
                    currentMoney += finalCarrotPrice * quantity * .5;
                    displaySellAlert(false);
                    inventory[2][1] -= quantity;
                    money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                    ownedText.setText("Owned: " + inventory[2][1]);
                    success = true;
                } else {
                    displaySellAlert(true);
                }
            } else if (selectedItem.equals("bgBroccoli")) {
                if (quantity <= inventory[2][0]) {
                    currentMoney += finalBroccoliPrice * quantity * .5;
                    displaySellAlert(false);
                    inventory[2][0] -= quantity;
                    money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                    ownedText.setText("Owned: " + inventory[2][0]);
                    success = true;
                } else {
                    displaySellAlert(true);
                }
            } else if (selectedItem.equals("bgCorn")) {
                if (quantity <= inventory[2][2]) {
                    currentMoney += finalCornPrice * quantity * .5;
                    displaySellAlert(false);
                    inventory[2][2] -= quantity;
                    money.setText("" + new DecimalFormat("#####.##").format(currentMoney));
                    ownedText.setText("Owned: " + inventory[2][2]);
                    success = true;
                } else {
                    displaySellAlert(true);
                }
            }

            if (success && Main.getSoundToggles()[1]) {
                MediaPlayer ching =
                        new MediaPlayer(
                                new Media(
                                        Paths.get(
                                                "src/Zombie-Farming-Game/Media/ka-ching.mp3")
                                                .toUri().toString()));
                ching.play();
            }
        }
    }

    /**
     * Clears all border styles from the crop buttons (removes all green outlines).
     */
    private void clearStyles() {
        brocSeedButton.setStyle("-fx-border-color: transparent");
        carSeedButton.setStyle("-fx-border-color: transparent");
        corSeedButton.setStyle("-fx-border-color: transparent");
        brocButton.setStyle("-fx-border-color: transparent");
        carButton.setStyle("-fx-border-color: transparent");
        corButton.setStyle("-fx-border-color: transparent");
        bgBrocButton.setStyle("-fx-border-color: transparent");
        bgCarButton.setStyle("-fx-border-color: transparent");
        bgCorButton.setStyle("-fx-border-color: transparent");
        bombButton.setStyle("-fx-border-color: transparent");
        potionButton.setStyle("-fx-border-color: transparent");
        wWorkerButton.setStyle("-fx-border-color: transparent");
        sWorkerButton.setStyle("-fx-border-color: transparent");
        fertButton.setStyle("-fx-border-color: transparent");
        tractorBtn.setStyle("-fx-border-color: transparent");
    }

    /**
     * Clears all border styles from the shop mode buttons (removes all green outlines).
     */
    private void clearModeStyles() {
        buyButton.setStyle("-fx-border-color: transparent");
        sellButton.setStyle("-fx-border-color: transparent");
    }

    /**
     * Handles general checkout errors related to user input (user not selecting a crop
     * to buy or sell, user inputting a non-integer value for the quantity, etc). Will
     * display an alert error message.
     * @return a boolean representing whether there is user input error
     */
    private boolean handleCheckoutErrors() {
        if (itemSelected[0] == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select an item to buy or sell!");
            alert.show();
            return true;
        } else if (!quantityTextField.getText().matches("[0-9]+")
                || quantityTextField.getText().charAt(0) == '0'
                || quantityTextField.getText() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Make sure your quantity input is a valid integer "
                            + "greater than zero!");
            alert.show();
            return true;
        }

        return false;
    }

    /**
     * Helper method that displays alert error or confirmation messages when a user
     * is performing a buying transaction.
     * @param isError whether the user was able to perform the transaction successfully or not
     * @param errCode the error code if applicable, no error would be 4.
     */
    private void displayAlert(boolean isError, int errCode) {
        if (isError && errCode == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "You don't have enough money to "
                            + "perform this transaction.");
            alert.show();
        } else if (isError && errCode == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "The amount of items you are attempting to purchase "
                            + "exceeds inventory capacity. Please try again.");
            alert.show();
        } else if (isError && errCode == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "You don't have enough money to perform this "
                            + "transaction. Also, the amount of items you are attempting "
                            + "to purchase exceeds inventory capacity. Please try again.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Purchase successful");
            alert.show();
        }
    }

    /**
     * Helper method that displays confirmation or error alert messages when a user is
     * performing a selling transaction.
     * @param isError whether the user was able to perform the transaction successfully or not
     */
    private void displaySellAlert(boolean isError) {
        if (isError) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "You don't have enough items to "
                            + "perform this transaction.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Selling successful");
            alert.show();
        }
    }

    /**
     * Retrieves the number of days left a player has a weak worker
     *
     * @return remaining weak worker days
     */
    public static int getWeakDaysLeft() {
        return weakDaysLeft;
    }

    /**
     * Retrieves the number of days left a player has a strong worker
     *
     * @return remaining strong worker days
     */
    public static int getStrongDaysLeft() {
        return strongDaysLeft;
    }

    /**
     * Retrieves the watering amount the player has per day
     *
     * @return the watering amount the player has everyday
     */
    public static int getWateringAmount() {
        return wateringAmount;
    }

    public static void setWeakDaysLeft(int daysLeft) {
        weakDaysLeft = daysLeft;
    }

    public static void setStrongDaysLeft(int daysLeft) {
        strongDaysLeft = daysLeft;
    }

    public static void setWateringAmount(int waterAmount) {
        wateringAmount = waterAmount;
    }

    public static int getHarvestMax() {
        return harvestMax;
    }

    public static void setHarvestMax(int max) {
        harvestMax = max;
    }

    @FXML
    private void goSettings() throws IOException {
        Main.getSettings();
    }

    /**
     * Next Day Button Handler.
     * @throws IOException if Farm fxml file not found
     */
    @FXML
    private void nextDay() throws IOException {
        if (currentMoney < broccoliBuyingPrice && inventory[0][0] == 0
                && inventory[0][1] == 0 && inventory[0][2] == 0
                && Main.getAlivePlots().size() == 0) {
            Main.getEndScreen();
        } else {
            Main.advanceDay();
            Main.setCash(currentMoney);
            Main.getFarm();
        }
    }

    public static String[] getItemSelected() {
        return itemSelected;
    }
}