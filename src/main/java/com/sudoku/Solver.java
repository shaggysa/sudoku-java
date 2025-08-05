package com.sudoku;

import java.util.Arrays;

public class Solver {
    static void printPuzzle(short[] puzzle){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                System.out.print(puzzle[j + (i*9)] + "  ");
            }
            System.out.println();
        }
    }

    private static boolean[] getPossibilites(short[] puzzle, int position) {
        boolean[] seen = new boolean[10];
        Arrays.fill(seen, true);
        int row = position / 9;
        int col = position % 9;

        int rootRow = (row/3) * 3;
        int rootCol = (col/3) * 3;
        for (int r = rootRow; r < rootRow + 3; r++) {
            for (int c = rootCol; c < rootCol + 3; c++) {
                seen[puzzle[r*9+c]] = false;
            }
        }

        for (int i = 0; i < 9; i++) {
            seen[puzzle[i + (row * 9)]] = false;
            seen[puzzle[col + (i * 9)]] = false;
        }
        return seen;
    }

    public static short[] solve(short[] puzzle){
        Puzzle p = new Puzzle(puzzle);
        p.forwardInit();
        if (p.solved){
            return p.puzzle;
        }
        p.forwardInit();
        int position = 0;
        int max_len = p.blankPositions.size();
        boolean progressed = true;
        while (position < max_len) {
            boolean found = false;
            if (position < 0) {
                System.err.println("The puzzle is unsolvable!");
                return new short[81];
            }
            int spot = p.blankPositions.get(position);
            int max = p.possibilities.get(position).size() - 1;

            if (progressed) {
                if (position == p.cachedPossibilites.size()) {
                    p.cachedPossibilites.add(getPossibilites(p.puzzle, spot));
                } else {
                    p.cachedPossibilites.set(position, getPossibilites(p.puzzle, spot));
                }
            }

            while (p.currentPos.get(position) < max) {
                p.currentPos.set(position, (short) (p.currentPos.get(position) + 1));
                if (p.cachedPossibilites.get(position)[p.possibilities.get(position).get(p.currentPos.get(position))]) {
                    p.puzzle[spot] = p.possibilities.get(position).get(p.currentPos.get(position));
                    found = true;
                    position++;
                    break;
                }
            }

            if (!found) {
                p.puzzle[spot] = 0;
                p.currentPos.set(position, (short) -1);
                position--;
                progressed = false;
            } else {
                progressed = true;
            }

        }
        p.solved = true;
        return p.puzzle;
    }
}


