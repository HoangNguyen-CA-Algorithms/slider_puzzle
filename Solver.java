/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

public class Solver {
    private Board board;
    private int totalMoves;
    private MinPQ<SearchNode> PQ;
    private Iterable finalSolution;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        private int distance;
        ArrayList<Board> solution = new ArrayList<>();

        SearchNode(Board board, int moves) {
            this.board = board;
            this.moves = moves;
            distance = board.manhattan();
        }

        public int priority() {
            return moves + distance;
        }

        public int compareTo(SearchNode o) {
            if (priority() < o.priority()) return -1;
            else if (priority() > o.priority()) return 1;
            else return 0;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        board = initial;
        PQ = new MinPQ<>();

        PQ.insert(new SearchNode(initial, 0));
        int steps = 0;

        Board prevBoard = null;
        while (!PQ.isEmpty()) {
            SearchNode curr = PQ.delMin();
            Board currBoard = curr.board;
            ArrayList<Board> currSolution = curr.solution;

            System.out.println(currBoard + "|" + curr.priority() );

            if (currBoard.isGoal()) {
                totalMoves = curr.moves;
                finalSolution = curr.solution;
                break;
            }

            Iterable<Board> neighbours = currBoard.neighbors();
            for (Board n : neighbours) {
                SearchNode nNode = new SearchNode(n, curr.moves + 1);
                nNode.solution = (ArrayList<Board>) currSolution.clone();
                nNode.solution.add(n);
                if (prevBoard == null || !nNode.board.equals(prevBoard)) {
                    PQ.insert(nNode);
                }
            }

            prevBoard = currBoard;

            //TODO DEBUG
            steps++;
            if (steps == 100) break;

        }


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return totalMoves == -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return totalMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return finalSolution;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
