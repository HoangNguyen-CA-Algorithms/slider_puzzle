/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int finalMoves;
    private Iterable<Board> finalSolution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Board twin = initial.twin();
        MinPQ<SearchNode> priorityQ = new MinPQ<>();

        priorityQ.insert(new SearchNode(initial, 0));
        priorityQ.insert(new SearchNode(twin, 0));


        while (!priorityQ.min().board.isGoal()) {
            SearchNode curr = priorityQ.delMin();
            Board currBoard = curr.board;

            Iterable<Board> neighbours = currBoard.neighbors();
            for (Board n : neighbours) {
                SearchNode nNode = new SearchNode(n, curr.moves + 1);
                if (curr.previous == null || !nNode.board.equals(curr.previous.board)) {
                    nNode.previous = curr;
                    priorityQ.insert(nNode);

                }
            }
        }

        Stack<Board> solStack = new Stack<>();
        SearchNode temp = priorityQ.min();

        while (temp.previous != null) {
            solStack.push(temp.board);
            temp = temp.previous;
        }
        // last board
        if (initial.equals(temp.board)) {
            finalMoves = solStack.size();
            solStack.push(temp.board);
            finalSolution = solStack;
        }
        else { // solution doesn't exist
            finalMoves = -1;
            finalSolution = null;
        }

    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int cachedPriority;

        SearchNode(Board board, int moves) {
            this.board = board;
            this.moves = moves;
            cachedPriority = moves + board.manhattan();
        }


        public int compareTo(SearchNode other) {
            return Integer.compare(cachedPriority, other.cachedPriority);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return finalMoves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return finalMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return finalSolution;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
