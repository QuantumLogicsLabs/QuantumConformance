public class P073_TicTacToe {
    static char[] board = "         ".toCharArray();

    static char winner(char[] b) {
        int[][] lines = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int[] l : lines)
            if (b[l[0]] != ' ' && b[l[0]] == b[l[1]] && b[l[1]] == b[l[2]]) return b[l[0]];
        return ' ';
    }

    static boolean full(char[] b) {
        for (char c : b) if (c == ' ') return false;
        return true;
    }

    static int minimax(char[] b, char player, int depth) {
        char w = winner(b);
        if (w == 'X') return 10 - depth;
        if (w == 'O') return depth - 10;
        if (full(b)) return 0;
        int best = player == 'X' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (b[i] != ' ') continue;
            b[i] = player;
            int score = minimax(b, player == 'X' ? 'O' : 'X', depth + 1);
            b[i] = ' ';
            best = player == 'X' ? Math.max(best, score) : Math.min(best, score);
        }
        return best;
    }

    static int bestMove(char[] b, char player) {
        int move = -1, best = player == 'X' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (b[i] != ' ') continue;
            b[i] = player;
            int s = minimax(b, player == 'X' ? 'O' : 'X', 1);
            b[i] = ' ';
            if (player == 'X' ? s > best : s < best) { best = s; move = i; }
        }
        return move;
    }

    static void print(char[] b) {
        for (int r = 0; r < 3; r++) {
            System.out.println(" " + b[3 * r] + " | " + b[3 * r + 1] + " | " + b[3 * r + 2]);
            if (r < 2) System.out.println("---+---+---");
        }
    }

    public static void main(String[] args) {
        board[0] = 'X';
        board[4] = 'O';
        char turn = 'X';
        while (winner(board) == ' ' && !full(board)) {
            int m = bestMove(board, turn);
            board[m] = turn;
            System.out.println(turn + " plays " + m);
            turn = turn == 'X' ? 'O' : 'X';
        }
        print(board);
        char w = winner(board);
        System.out.println(w == ' ' ? "draw" : w + " wins");
    }
}
