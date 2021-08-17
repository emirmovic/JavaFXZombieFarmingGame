package game.data.defense;

import game.Main;
import game.data.Plot;

public class Bomb {
    private int days;
    private boolean[][] protectedPlots = new boolean[4][5];
    private Plot centralPlot;

    public Bomb(Plot centralPlot) {
        this(centralPlot, 0);
    }

    public Bomb(Plot centralPlot, int day) {
        days = day;
        this.centralPlot = centralPlot;

        /*
            0, 1, 2, 3, 4
            5, 6, 7, 8, 9
            10, 11, 12, 13, 14
            15, 16, 17, 18, 19
        */

        setProtectedPlots();
    }

    /**
     * Retrieves the set of plots protected by this bomb
     *
     * @return this bomb's protected plots
     */
    public boolean[][] getProtectedPlots() {
        return protectedPlots;
    }

    public void setProtectedPlots() {
        int plot = centralPlot.getRow() * (
                (Main.getExpandedColumn()) ? 5 : 4) + centralPlot.getCol();

        switch ((!Main.getExpandedColumn() ? plot + centralPlot.getRow() : plot)) {
        case 0: // 0, 0
            setProtectedEdgeHelper(0, 0, true);
            break;
        case 1: // 0, 1
            setProtectedEdgeHelper(0, 1, false);
            break;
        case 2: // 0, 2
            setProtectedEdgeHelper(0, 2, false);
            break;
        case 3: // 0, 3
            setProtectedEdgeHelper(0, 3, false);
            break;
        case 4: // 0, 4
            setProtectedEdgeHelper(0, 4, true);
            break;
        case 5:  // 1, 0
            setProtectedEdgeHelper(1, 0, true);
            break;
        case 6: // 1, 1
            setProtectedMidHelper(1, 1);
            break;
        case 7: // 1, 2
            setProtectedMidHelper(1, 2);
            break;
        case 8: // 1, 3
            setProtectedMidHelper(1, 3);
            break;
        case 9: // 1, 4
            setProtectedEdgeHelper(1, 4, true);
            break;
        case 10: // 2, 0
            setProtectedEdgeHelper(2, 0, true);
            break;
        case 11: // 2, 1
            setProtectedMidHelper(2, 1);
            break;
        case 12: // 2, 2
            setProtectedMidHelper(2, 2);
            break;
        case 13: // 2, 3
            setProtectedMidHelper(2, 3);
            break;
        case 14: // 2, 4
            setProtectedEdgeHelper(2, 4, true);
            break;
        case 15: // 3, 0
            setProtectedEdgeHelper(3, 0, true);
            break;
        case 16: // 3, 1
            setProtectedEdgeHelper(3, 1, false);
            break;
        case 17: // 3, 2
            setProtectedEdgeHelper(3, 2, false);
            break;
        case 18: // 3, 3
            setProtectedEdgeHelper(3, 3, false);
            break;
        case 19: // 3, 4
            setProtectedEdgeHelper(3, 4, true);
            break;
        default:
            break;
        }
    }

    private void setProtectedMidHelper(int row, int col) {
        protectedPlots[row - 1][col] = true;
        protectedPlots[row][col - 1] = true;
        protectedPlots[row][col + 1] = true;
        protectedPlots[row + 1][col] = true;
    }

    private void setProtectedEdgeHelper(int row, int col, boolean sides) {
        if (!sides) {
            protectedPlots[row][col - 1] = true;
            protectedPlots[row][col + 1] = true;

            if (row == 0) {
                protectedPlots[row + 1][col] = true;
            } else {
                protectedPlots[row - 1][col] = true;
            }
        } else {
            if (col == 0) {
                protectedPlots[row][col + 1] = true;
            } else {
                protectedPlots[row][col - 1] = true;
            }

            if (row == 0) {
                protectedPlots[row + 1][col] = true;
            } else if (row == 3) {
                protectedPlots[row - 1][col] = true;
            } else {
                protectedPlots[row - 1][col] = true;
                protectedPlots[row + 1][col] = true;
            }
        }
    }

    /**
     * Increments the number of days this bomb has been active by 1
     *
     * @return the bomb's current active days +1
     */
    public int addDay() {
        return ++days;
    }

    /**
     * Retrieves the number of days this bomb has been active
     *
     * @return the number of days this bomb has been active
     */
    public int getDays() {
        return days;
    }

    /**
     * Retrieves the plot in the farm that this bomb is placed on
     *
     * @return this bomb's central plot
     */
    public Plot getCentralPlot() {
        return centralPlot;
    }
}
