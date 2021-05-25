/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[][] tiles;
    private int N;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.N = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(N + "\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                builder.append(tiles[i][j] + " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int goal = i * N + j + 1;
                if (tiles[i][j] != 0 &&tiles[i][j] != goal) {
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue;
                int goalI = (tiles[i][j] - 1) / N;
                int goalJ = (tiles[i][j] - 1) % N;
                count += Math.abs(i - goalI) + Math.abs(j - goalJ);
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j == N - 1 && i == N - 1) return true;

                int goal = i * N + j + 1;
                if (tiles[i][j] != goal) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        Board that = (Board) other;
        if (other.getClass() != this.getClass()) return false;
        return (this.toString().equals(that.toString()));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    //check 4 neighbours
                    if (i + 1 < N) {
                        int[][] temp = copyTiles();
                        swap(temp, i, j, i + 1, j);
                        list.add(new Board(temp));
                    }
                    if (i - 1 >= 0) {
                        int[][] temp = copyTiles();
                        swap(temp, i, j, i - 1, j);
                        list.add(new Board(temp));
                    }
                    if (j + 1 < N) {
                        int[][] temp = copyTiles();
                        swap(temp, i, j, i, j + 1);
                        list.add(new Board(temp));
                    }
                    if (j - 1 >= 0) {
                        int[][] temp = copyTiles();
                        swap(temp, i, j, i, j - 1);
                        list.add(new Board(temp));
                    }
                }

            }
        }

        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] == 0) { // use 2nd square
            int[][] temp = copyTiles();
            swap(temp, 0, 1, 1, 1);
            return new Board(temp);
        }
        else {
            if (tiles[0][1] != 0) {
                int[][] temp = copyTiles();
                swap(temp, 0, 0, 0, 1);
                return new Board(temp);
            }
            else {
                int[][] temp = copyTiles();
                swap(temp, 0, 0, 1, 0);
                return new Board(temp);
            }
        }

    }


    private int[][] copyTiles() {
        int[][] temp = new int[N][N];
        for (int k = 0; k < temp.length; k++) {
            temp[k] = tiles[k].clone();
        }
        return temp;
    }

    private void swap(int[][] arr, int i, int j, int i2, int j2) {
        int temp = arr[i][j];
        arr[i][j] = arr[i2][j2];
        arr[i2][j2] = temp;
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.println(initial);
            System.out.println(initial.manhattan());
        }

    }

}
