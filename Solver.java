/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int totalMoves;
    private Iterable<Board> finalSolution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Board twin = initial.twin();
        MinPQ<SearchNode> priorityQ = new MinPQ<>();

        priorityQ.insert(new SearchNode(initial, 0));
        priorityQ.insert(new SearchNode(twin, 0));
        Board prevBoard = null;
        while (!priorityQ.isEmpty()) {
            SearchNode curr = priorityQ.delMin();
            Board currBoard = curr.board;
            if (curr.previous != null) {
                prevBoard = curr.previous.board;
            }


            if (currBoard.isGoal()) {
                Stack<Board> sol = new Stack<Board>();
                SearchNode temp = curr;

                while (temp.previous != null) {
                    sol.push(temp.board);
                    temp = temp.previous;
                }
                // last board
                if (initial.equals(temp.board)) {
                    sol.push(temp.board);
                    totalMoves = curr.moves;
                    finalSolution = sol;
                }
                else {
                    totalMoves = -1;
                    finalSolution = null;
                }
                break;
            }

            Iterable<Board> neighbours = currBoard.neighbors();
            for (Board n : neighbours) {
                SearchNode nNode = new SearchNode(n, curr.moves + 1);
                nNode.previous = curr;
                if (!nNode.board.equals(prevBoard)) {
                    priorityQ.insert(nNode);
                }
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode previous;
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
        return totalMoves != -1;
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
