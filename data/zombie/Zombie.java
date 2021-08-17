package game.data.zombie;

import game.Main;
import game.data.Plot;
import game.data.defense.Bomb;
import java.util.ArrayList;

public class Zombie {
    private static final int[] BASE_ATK = {300, 325, 350};
    private static double[] dmgMultiplier = {1.0, 1.15, 1.25};
    private static int difficultyIndex;
    private static final double TIME_MULTIPLIER = 1.25;
    private static double plotChance = .15;

    /**
     * Sets the difficulty with which to pull factors from
     *
     * @param index is the index of the difficulty the player has chosen
     */
    public static void setDifficultyIndex(int index) {
        difficultyIndex = index;
    }

    /**
     * Handles the zombie attacking a plot
     *
     * @param plot is the plot being affected
     * @param day is the current day number in game
     * @return the damage output applied to the plant:
     *          -1 if the plant is protected by a potion (no damage)
     *          -2 if the plant is within the surrounding radius of a bomb plant (no damage)
     *          -3 if the attack chance fell short (no damage)
     *          number > 0 from a successful attack
     */
    public static double attack(Plot plot, int day) {
        ArrayList<Bomb> farmBombs = Main.getFarmBombs();

        boolean isProtected = false;
        for (Bomb bomb : farmBombs) {
            if (bomb.getProtectedPlots()[plot.getRow()][plot.getCol()]) {
                isProtected = true;
                break;
            }
        }

        if (!isProtected) {
            if (!plot.getPotion()) {
                plotChance *= (day % 10 == 0) ? TIME_MULTIPLIER : 1;
                dmgMultiplier[difficultyIndex] *= (day % 10 == 0) ? TIME_MULTIPLIER : 1;

                double atk = (BASE_ATK[difficultyIndex] * dmgMultiplier[difficultyIndex]);

                if (Math.random() >= plotChance) {
                    plot.updateHealth(-1 * atk);

                    return atk;
                }

                return -1;
            }

            return -2;
        }

        return -3;
    }
}
