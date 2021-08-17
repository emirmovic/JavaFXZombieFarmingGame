package game.data.saves;

import game.Controller.MarketScreenController;
import game.Main;
import game.data.Plot;
import game.data.defense.Bomb;

import java.io.*;
import java.util.ArrayList;

public class Load {
    private BufferedReader buffReader;
    private String lineString;
    private File file;

    private int day;
    private double money;
    private String name;
    private String difficulty;
    private String screen;
    private String currentSeason;
    private boolean expandedRow;
    private boolean expandedColumn;
    private int weakDaysLeft;
    private int strongDaysLeft;
    private int wateringAmount;
    private int harvestMax;
    private int[][] inventory;
    private Plot[][] farmPlots;
    private int[] alivePlotsArray;
    private ArrayList<Bomb> farmBombs;

    /**
     * Object to hold the game information contained within a save file
     *
     * @param file is the save file
     * @throws IOException if the save file cannot be found
     */
    public Load(File file) throws IOException {
        this.file = file;
        buffReader = new BufferedReader(new FileReader(file));

        readDay();
        readMoney();
        readName();
        readDifficulty();
        readScreen();
        readCurrentSeason();
        readExpandedRow();
        readExpandedColumn();
        readWorkers();
        readWater();
        readHarvest();
        readInventory();
        readPlots();
        readAlivePlots();
        readBombs();

        buffReader.close();
    }

    /**
     * Deletes the save file
     *
     * @throws IOException if there is an issue in writing to the file
     */
    public void clear() throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.write(new byte[0]);
        out.close();
    }

    /**
     * Parses the current day in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readDay() throws IOException {
        day = Integer.parseInt(buffReader.readLine());
    }

    /**
     * Parses the current money in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readMoney() throws IOException {
        money = Double.parseDouble(buffReader.readLine());
    }

    /**
     * Parses the player's name in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readName() throws IOException {
        name = buffReader.readLine();
    }

    /**
     * Parses the selected difficulty in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readDifficulty() throws IOException {
        difficulty = buffReader.readLine();
    }

    /**
     * Parses the current screen in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readScreen() throws IOException {
        screen = buffReader.readLine();
    }

    /**
     * Parses the current season in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readCurrentSeason() throws IOException {
        currentSeason = buffReader.readLine();
    }

    private void readExpandedRow() throws IOException {
        expandedRow = Boolean.parseBoolean(buffReader.readLine());
    }

    private void readExpandedColumn() throws IOException {
        expandedColumn = Boolean.parseBoolean(buffReader.readLine());
    }

    /**
     * Parses the current days remaining for both weak and strong workers
     * in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readWorkers() throws IOException {
        weakDaysLeft = Integer.parseInt(buffReader.readLine());
        strongDaysLeft = Integer.parseInt(buffReader.readLine());
    }

    /**
     * Parses the current water amount the person has available per day
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readWater() throws IOException {
        wateringAmount = Integer.parseInt(buffReader.readLine());
    }

    private void readHarvest() throws IOException {
        harvestMax = Integer.parseInt(buffReader.readLine());
    }

    /**
     * Parses the current inventory in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readInventory() throws IOException {
        inventory = new int[5][3];
        String[][] rows = new String[5][3];

        for (int i = 0; i < 5; i++) {
            lineString = buffReader.readLine();
            rows[i] = lineString.split(":");
        }

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                inventory[row][col] = Integer.parseInt(rows[row][col]);
            }
        }
    }

    /**
     * Parses the current farm plots in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readPlots() throws IOException {
        String[][] rows;

        if (expandedRow) {
            farmPlots = new Plot[4][5];
            rows = new String[20][12];
        } else if (expandedColumn) {
            farmPlots = new Plot[3][5];
            rows = new String[15][12];
        } else {
            farmPlots = new Plot[3][4];
            rows = new String[12][12];
        }

        // ancientCarrot:00:0.000000:0:0:0:true:false:false:0:0:0
        // plantType, health, waterLevel, row, col, occupied, alive, potion, potionDay,
        // fertLevel, fertilizer boost

        for (int i = 0; i < rows.length; i++) {
            lineString = buffReader.readLine();
            rows[i] = lineString.split(":");
        }

        int rowIndex;
        for (int row = 0; row < farmPlots.length; row++) {
            for (int col = 0; col < farmPlots[0].length; col++) {
                rowIndex = row * farmPlots[0].length + col;
                farmPlots[row][col] =
                        new Plot(
                                Double.parseDouble(rows[rowIndex][2]),
                                Integer.parseInt(rows[rowIndex][3]),
                                Integer.parseInt(rows[rowIndex][4]),
                                Integer.parseInt(rows[rowIndex][5]),
                                Boolean.parseBoolean(rows[rowIndex][8]),
                                Integer.parseInt(rows[rowIndex][9]),
                                Integer.parseInt(rows[rowIndex][10])
                        );

                farmPlots[row][col].setPlant(String.format("%s:%s",
                        rows[rowIndex][0],
                        rows[rowIndex][1]));
                farmPlots[row][col].setOccupied(Boolean.parseBoolean(rows[rowIndex][6]));
                farmPlots[row][col].setAlive(Boolean.parseBoolean(rows[rowIndex][7]));
                farmPlots[row][col].setFertBoost(Integer.parseInt(rows[rowIndex][11]));
            }
        }
    }

    /**
     * Parses the current alive plots in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readAlivePlots() throws IOException {
        lineString = buffReader.readLine();
        alivePlotsArray = new int[0];

        if (lineString != null && !lineString.equals("")) {
            String[] aliveS = lineString.split(":");
            alivePlotsArray = new int[aliveS.length];

            for (int i = 0; i < alivePlotsArray.length; i++) {
                alivePlotsArray[i] = Integer.parseInt(aliveS[i]);
            }
        }
    }

    /**
     * Parses the current bombs in the save file
     *
     * @throws IOException if the file cannot be found or there is an issue in
     *                     parsing the file
     */
    private void readBombs() throws IOException {
        farmBombs = new ArrayList<>();
        String[] tempBomb;

        while ((lineString = buffReader.readLine()) != null) {
            tempBomb = lineString.split(":");
            farmBombs.add(
                    new Bomb(
                            farmPlots
                                    [Integer.parseInt(tempBomb[0])]
                                    [Integer.parseInt(tempBomb[1])],
                            Integer.parseInt(tempBomb[2])));
        }
    }

    /**
     * Retrieves the name within the save file
     *
     * @return the player's name in the save file
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the money within the save file
     *
     * @return the player's money in the save file
     */
    public double getMoney() {
        return money;
    }

    /**
     * Retrieves the day within the save file
     *
     * @return the current day in the save file
     */
    public int getDay() {
        return day;
    }

    /**
     * Retrieves the difficulty within the save file
     *
     * @return the selected difficulty in the save file
     */
    public String getDifficulty() {
        return difficulty;
    }

    public String getCurrentSeason() {
        return currentSeason;
    }

    /**
     * Retrieves the current screen within the save file
     *
     * @return the current screen in the save file
     */
    public String getScreen() {
        return screen;
    }

    /**
     * Configures Main and Market to hold all of the information possessed within
     * the save file
     */
    public void configureLoad() {
        Main.setDay(day);
        Main.setCash(money);
        Main.setName(name);
        Main.setDifficulty(difficulty);
        Main.setScreen(screen);
        Main.setInit(false);
        Main.setCurrSeason(currentSeason);

        switch (currentSeason) {
        case "spring" :
            Main.setSeasonIndex(0);
            break;
        case "summer" :
            Main.setSeasonIndex(1);
            break;
        case "fall" :
            Main.setSeasonIndex(2);
            break;
        case "winter" :
            Main.setSeasonIndex(3);
            break;
        default :
            break;
        }

        MarketScreenController.setWeakDaysLeft(weakDaysLeft);
        MarketScreenController.setStrongDaysLeft(strongDaysLeft);
        MarketScreenController.setWateringAmount(wateringAmount);
        MarketScreenController.setHarvestMax(harvestMax);
        Main.setInventory(inventory);
        Main.setFarmPlots(farmPlots);
        Main.setAlivePlots(alivePlotsArray);
        Main.setFarmBombs(farmBombs);
    }
}
