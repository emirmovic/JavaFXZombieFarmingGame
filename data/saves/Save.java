package game.data.saves;

import game.Controller.MarketScreenController;
import game.Main;
import game.data.Plot;
import game.data.defense.Bomb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save {
    private FileOutputStream out;
    private String writeString;

    /**
     * Object to write the current state of the game into a save file
     *
     * @param file is the save file to write to
     * @throws IOException is the save file cannot be found
     */
    public Save(File file) throws IOException {
        out = new FileOutputStream(file);

        writeDay();
        writeMoney();
        writeName();
        writeDifficulty();
        writeScreen();
        writeCurrentSeason();
        writeExpandedRow();
        writeExpandedColumn();
        writeWorkers();
        writeWater();
        writeHarvest();
        writeInventory();
        writePlots();
        writeAlivePlots();
        writeBombs();

        out.close();
    }

    /**
     * Writes the current day in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeDay() throws IOException {
        int day = Main.getDay()[0];

        writeString = String.format("%d", day);

        write();
    }

    /**
     * Writes the current money in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeMoney() throws IOException {
        double money = Main.getCash();

        writeString = String.format("\n%f", money);

        write();
    }

    /**
     * Writes the current alive plots in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeAlivePlots() throws IOException {
        writeString = "\n";

        for (int alivePlot : Main.getAlivePlots()) {
            writeString += String.format(":%d", alivePlot);
        }

        writeString = writeString.replaceFirst(":", "");

        write();
    }

    /**
     * Writes the current farm plots in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writePlots() throws IOException {
        writeString = "";

        for (Plot[] plots : Main.getFarmPlots()) {
            for (Plot plot : plots) {
                // plantType, health, waterLevel, row, col, occupied, alive, potion,
                // potionDay, fertLevel
                writeString += String.format("\n%s:%f:%d:%d:%d:%s:%s:%s:%d:%d:%d",
                        plot.getPlant(),
                        plot.getHealth(),
                        plot.getWaterLevel(),
                        plot.getRow(),
                        plot.getCol(),
                        plot.getOccupied(),
                        plot.getAlive(),
                        plot.getPotion(),
                        plot.getPotionDay(),
                        plot.getFertilizerLevel(),
                        plot.getBoost());
            }
        }

        write();
    }

    /**
     * Writes the current bombs in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeBombs() throws IOException {
        writeString = "";

        for (Bomb bomb : Main.getFarmBombs()) {
            // row (centralPlot), col (centralPlot), days
            writeString += String.format("\n%d:%d:%d",
                    bomb.getCentralPlot().getRow(),
                    bomb.getCentralPlot().getCol(),
                    bomb.getDays());
        }

        write();
    }

    /**
     * Writes the current remaining weak and strong worker days in-game
     * to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeWorkers() throws IOException {
        writeString = String.format("\n%d\n%d",
                MarketScreenController.getWeakDaysLeft(),
                MarketScreenController.getStrongDaysLeft());
        write();
    }

    private void writeWater() throws IOException {
        writeString = String.format("\n%d",
                MarketScreenController.getWateringAmount());
        write();
    }

    private void writeHarvest() throws IOException {
        writeString = String.format("\n%d",
                MarketScreenController.getHarvestMax());
        write();
    }

    /**
     * Writes the selected difficulty in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeDifficulty() throws IOException {
        writeString = String.format("\n%s", Main.getDifficulty());
        write();
    }

    /**
     * Writes the current inventory in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeInventory() throws IOException {
        writeString = "";

        for (int[] row : Main.getInventory()) {
            writeString += String.format("\n%d:%d:%d", row[0], row[1], row[2]);
        }

        write();
    }

    /**
     * Writes the player's name in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeName() throws IOException {
        writeString = String.format("\n%s", Main.getName());
        write();
    }

    /**
     * Writes the current season in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeCurrentSeason() throws IOException {
        writeString = String.format("\n%s", Main.getCurrSeason());
        write();
    }

    private void writeExpandedRow() throws IOException {
        writeString = String.format("\n%s", Main.getExpandedRow());
        write();
    }

    private void writeExpandedColumn() throws IOException {
        writeString = String.format("\n%s", Main.getExpandedColumn());
        write();
    }
    
    /**
     * Writes the current screen in-game to the save file
     *
     * @throws IOException if the file cannot be found or there is an issue
     *                     in writing to the file
     */
    private void writeScreen() throws IOException {
        writeString = String.format("\n%s", Main.getScreen());
        write();
    }

    /**
     * Helper method to actually write to the save file
     *
     * @throws IOException if there is an issue in writing to the save file
     */
    private void write() throws IOException {
        byte[] byteArray = writeString.getBytes();
        out.write(byteArray);
    }
}
