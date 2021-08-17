package game.data;

import game.Main;

public class Plot {
    private double health;
    private String plant;
    private int waterLevel;
    private int col;
    private int row;
    private boolean occupied;
    private boolean alive;
    private int fertLevel = 0;
    private int fertBoost;
    private static String difficulty = Main.getDifficulty();
    private static final double[][] SEASON_MULTIPLIERS = {
            {1.25, 1.0, 1.0, .75},
            {1.25, .75, 1.25, 1.0},
            {1.25, 1.0, 1.25, .75}
    };
    private static final int BASE_HEALTH = 400;
    private boolean potion;
    private int potionDay;

    /**
     * Object to hold all information needed for each individual farm plot
     * @param col the column of the plot
     * @param row the row of the plot
     * @param occupied whether the plot is empty upon randomization
     * @param plant the type of plant in this plot, "empty" if none
     */
    public Plot(int row, int col, boolean occupied, String plant) {
        this.col = col;
        this.row = row;
        this.plant = plant;
        this.occupied = occupied;
        this.potion = false;
        this.potionDay = 0;
        this.health = 0.0;
        this.waterLevel = 0;

        alive = !plant.contains("ancient") && occupied;

        if (alive) {
            resetWaterLevel();
            resetHealth();
        }
    }

    /**
     * Constructor for use when recreating plots from a save file
     *
     * @param health is the plant's health
     * @param waterLevel is the plant's water level
     * @param row is the plant's row in the farm
     * @param col is the plant's column in the farm
     * @param potion is whether this plant has a potion applied to it
     * @param potionDay is the number of days this plant's potion has been active
     * @param fertLevel is the fertilizer level of the plant
     */
    public Plot(
            double health,
            int waterLevel,
            int row,
            int col,
            boolean potion,
            int potionDay,
            int fertLevel) {
        this.health = health;
        this.waterLevel = waterLevel;
        this.row = row;
        this.col = col;
        this.potion = potion;
        this.potionDay = potionDay;
        this.fertLevel = fertLevel;
    }

    /**
     * Returns the plant contained within this plot
     * @return the plant type
     */
    public String getPlant() {
        return plant;
    }

    /**
     * Sets the plant type of this plot
     * @param plant is the new plant of this plot
     */
    public void setPlant(String plant) {
        this.plant = plant;
    }

    /**
     * Returns the water level of the plant within this plot
     * @return this plot's plant's water level
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * Updates the water level for the plot.
     * No negative water levels and the plot must always have a sufficient
     * water level or else the plant will die. Ranges of being alive is based on difficulty
     * level: beginner -> [10, 90], inferno -> [50, 90], doomsday -> [70, 90]
     *
     * @param day is whether the water level should be incremented or decremented
     * @return whether this plant survived getting watered
     */
    public boolean updateWater(boolean day) {
        if (occupied) {
            if (day) {
                if (waterLevel + 10 >= 100) {
                    waterLevel = 100;
                } else {
                    waterLevel += 10;
                }
            } else {
                boolean alreadySet = false;
                if (waterLevel == 100) {
                    setAncient();

                    alive = false;
                    alreadySet = true;
                }

                if (difficulty.equals("beginner") && !alreadySet) {
                    if (waterLevel - 10 <= 0) {
                        waterLevel = 0;
                        alive = false;

                        setAncient();
                    } else {
                        waterLevel -= 10;
                    }
                } else if (difficulty.equals("inferno") && !alreadySet) {
                    if (waterLevel - 10 <= 40) {
                        waterLevel = 40;
                        alive = false;

                        setAncient();
                    } else {
                        waterLevel -= 10;
                    }
                } else if (!alreadySet) {
                    if (waterLevel - 10 <= 60) {
                        waterLevel = 60;
                        alive = false;

                        setAncient();
                    } else {
                        waterLevel -= 10;
                    }
                }
            }

            if (waterLevel == 0 || waterLevel == 100) {
                health = 0;
            } else {
                updateHealth(-1 * (.2 * waterLevel - 10));
                updateHealth(.2 * waterLevel);
            }
        }

        return alive;
    }

    /**
     * Resets the water level of the plot back to its default level based on difficulty.
     */
    public void resetWaterLevel() {
        waterLevel = 0;
        if (occupied) {
            if (difficulty.equals("beginner")) {
                waterLevel = 50;
            } else if (difficulty.equals("inferno")) {
                waterLevel = 70;
            } else {
                waterLevel = 80;
            }
        }
    }

    /**
     * Returns the living status of the plant within this plot
     * @return this plant's living status
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the living status of the plant within this plot
     * @param living is this plot's plant's new living status
     */
    public void setAlive(boolean living) {
        this.alive = living;
    }

    /**
     * Resets the health of the plant to its most base version with no defenses and
     * only water level to boost
     */
    public void resetHealth() {
        health = 0;
        updateHealth(.2 * waterLevel);

        if (plant.contains("seed")) {
            updateHealth(20.0);
        } else if (plant.contains("baby"))  {
            updateHealth(30.0);
        } else if (plant.contains("grown")) {
            updateHealth(40.0);
        }

        int cropIndex = 0;
        if (plant.toLowerCase().contains("carrot")) {
            cropIndex = 1;
        } else if (plant.toLowerCase().contains("broccoli")) {
            cropIndex = 2;
        }

        int seasonIndex = 0;
        if (Main.getCurrSeason().equals("summer")) {
            seasonIndex = 1;
        } else if (Main.getCurrSeason().equals("fall")) {
            seasonIndex = 2;
        } else {
            seasonIndex = 3;
        }

        updateHealth(BASE_HEALTH * SEASON_MULTIPLIERS[cropIndex][seasonIndex]);
    }

    /**
     * Updates the overall health of this plot
     *
     * @param up is the amount to alter the health by
     * @return the health of this plot
     */
    public double updateHealth(double up) {
        if (health + up <= 0) {
            this.health = 0;
            alive = false;
            setAncient();
        } else {
            this.health += up;
        }

        return health;
    }

    /**
     * Updates this plant's health based on its new growth level
     *
     * @param level is the flat stat associated with a growth level
     *              to give to this plant's health (20 -> seed, 30 ->
     *              baby, 40 -> grown)
     */
    public void updateGrowthHealth(int level) {
        if (level > 20) {
            updateHealth(-1 * (level - 10));
        }

        updateHealth(level);
    }

    /**
     * Retrieves the current health of this plant
     *
     * @return this plant's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the plant in this plot to its ancient level, killing it.
     */
    public void setAncient() {
        alive = false;
        health = 0;
        fertLevel = 0;
        String hold = plant.toLowerCase();

        if (hold.contains("broccoli")) {
            plant = "ancientBroccoli:" + row + col;
        } else if (hold.contains("carrot")) {
            plant = "ancientCarrot:" + row + col;
        } else if (hold.contains("corn")) {
            plant = "ancientCorn:" + row + col;
        } else if (hold.contains("bomb")) {
            plant = "deadBomb:" + row + col;
        } else {
            plant = "emptyPlot:" + row + col;
        }
    }

    /**
     * Returns the x coordinate of this plot within the farm
     * @return this plot's column
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the y coordinate of this plot within the farm
     * @return this plot's row
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the occupied status of this plot
     * @param occupied is the new occupied status of this plot
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Returns the occupied status of this plot
     * @return this plot's occupied status
     */
    public boolean getOccupied() {
        return this.occupied;
    }

    /**
     * Retrieves whether this plant is alive or not
     *
     * @return this plant's alive status
     */
    public boolean getAlive() {
        return this.alive;
    }

    /**
     * Returns whether this plot has a potion applied to it
     *
     * @return whether this plot has a potion applied to it
     */
    public boolean getPotion() {
        return this.potion;
    }

    /**
     * Retrieves the number of days left of the potion on this plant
     *
     * @return this potion's remaining days
     */
    public int getPotionDay() {
        return this.potionDay;
    }

    /**
     * Applies a potion to this plot
     */
    public void givePotion() {
        this.potion = true;
    }

    /**
     * Increments the number of days a potion has been applied to this plot,
     * removing the potion if it has been applied for 2 days
     */
    public void addPotionDay() {
        if (++potionDay == 2) {
            this.potion = false;
            this.potionDay = 0;
        }
    }

    /**
     * Increases the fertilizer level on this plant by 1, setting a
     * maximum of 3
     */
    public void increaseFert() {
        fertLevel = (fertLevel == 3) ? 3 : fertLevel + 1;
    }

    /**
     * Updates the fertilizer level for the plot.
     */
    public void updateFert() {
        if (alive) {
            //decreases fertilizer level per day
            if (fertLevel - 1 <= 0) {
                fertLevel = 0;
            } else {
                fertLevel -= 1;
            }
        }
    }

    /**
     * Returns the fertilizer level of the plant within this plot
     * @return this plot's plant's fertilizer level
     */
    public int getFertilizerLevel() {
        return fertLevel;
    }

    /**
     * Retrieves the fertilizer boost level on this plant
     *
     * @return this plant's yield boost
     */
    public int getBoost() {
        return fertBoost;
    }

    /**
     * Sets the fertilizer boost of this plant
     *
     * @param boost is the new yield boost for this plant
     */
    public void setFertBoost(int boost) {
        fertBoost = boost;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Plot)) {
            return false;
        }

        Plot plot = (Plot) o;

        return ((this.row == plot.getRow()) && (this.col == plot.getCol()));
    }
}
