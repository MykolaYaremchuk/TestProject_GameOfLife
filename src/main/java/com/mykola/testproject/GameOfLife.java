package com.mykola.testproject;

public class GameOfLife {

    /**
     * The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells,
     * each of which is in one of two possible states, alive or dead.
     */
    private boolean[][] universe;

    /**
     * Constructs a new instance of GameOfLife with the given width and height.
     *
     * @param width  the width of the universe
     * @param height the height of the universe
     */
    public GameOfLife(int width, int height) {
        this.universe = new boolean[height][width];
    }

    /**
     * Initializes the universe with the given pattern.
     *
     * @param pattern the pattern to initialize the universe with
     */
    public void initialize(boolean[][] pattern) {
        int patternHeight = pattern.length;
        int patternWidth = pattern[0].length;

        // calculate the center position to place the pattern in the universe
        int centerRow = (this.universe.length - patternHeight) / 2;
        int centerCol = (this.universe[0].length - patternWidth) / 2;

        // copy the pattern to the center of the universe
        for (int i = 0; i < patternHeight; i++) {
            for (int j = 0; j < patternWidth; j++) {
                this.universe[centerRow + i][centerCol + j] = pattern[i][j];
            }
        }
    }


    /**
     * Updates the state of the universe by applying the rules of the Game of Life.
     */
    public void update() {
        int height = this.universe.length;
        int width = this.universe[0].length;

        // create a new universe to hold the next state
        boolean[][] nextUniverse = new boolean[height][width];

        // iterate over each cell in the universe
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                if (this.universe[i][j]) {
                    // rule 1: Any live cell with fewer than two live neighbors dies as if caused by underpopulation.
                    if (liveNeighbors < 2) {
                        nextUniverse[i][j] = false;
                    }
                    // rule 2: Any live cell with two or three live neighbors lives on to the next generation.
                    else if (liveNeighbors == 2 || liveNeighbors == 3) {
                        nextUniverse[i][j] = true;
                    }
                    // rule 3: Any live cell with more than three live neighbors dies, as if by overcrowding.
                    else if (liveNeighbors > 3) {
                        nextUniverse[i][j] = false;
                    }
                } else {
                    // rule 4: Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
                    if (liveNeighbors == 3) {
                        nextUniverse[i][j] = true;
                    }
                }
            }
        }

        // update the universe with the next state
        this.universe = nextUniverse;
    }

    /**
     * Counts the number of live neighbors of the cell at the given row and column.
     *
     * @param row    the row of the cell
     * @param column the column of the cell
     * @return the number of live neighbors of the cell
     */
    private int countLiveNeighbors(int row, int column) {
        int count = 0;
        int height = this.universe.length;
        int width = this.universe[0].length;

        // iterate over the neighboring cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // skip the current cell
                if (i == 0 && j == 0) {
                    continue;
                }

                // calculate the coordinates of the neighboring cell
                int neighborRow = row + i;
                int neighborColumn = column + j;

                // check if the neighboring cell is within the universe
                if (neighborRow >= 0 && neighborRow < height && neighborColumn >= 0 && neighborColumn < width) {
                    // increment the count if the neighboring cell is alive
                    if (this.universe[neighborRow][neighborColumn]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Prints the current state of the universe to the console.
     */
    public void printState() {
        for (boolean[] row : this.universe) {
            for (boolean cell : row) {
                System.out.print(cell ? "X " : ". ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // initialize the game with a 25x25 universe
        GameOfLife game = new GameOfLife(25, 25);

        // initialize the game with the Glider pattern
        boolean[][] pattern = {
                {false, false, true, false, false},
                {true, false, true, false, false},
                {false, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };
        game.initialize(pattern);

        System.out.println("Initial space with Glider pattern:");
        game.printState();
        System.out.println();

        // update and print the state of the universe for 20 generations
        for (int i = 0; i < 20; i++) {
            System.out.println("Generation " + (i + 1) + ":");
            game.update();
            game.printState();
            System.out.println();
        }
    }
}
