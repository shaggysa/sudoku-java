package com.sudoku;

import java.util.HashSet;

public class Solver {
    public static void printPuzzle(int[] puzzle){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                System.out.print(puzzle[j + (i*9)] + "  ");
            }
            System.out.println();
        }
    }

    private static boolean noRepeats(int[] list){
        HashSet<Integer> nums = new HashSet<>();
        for (int item:list){
            if (item == 0){
                continue;
            }
            if (!nums.add(item)){
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(int[] puzzle, int position){
        int row = position / 9;
        int col = position % 9;

        int rootRow = (row / 3) * 3;
        int rootCol = (col / 3) * 3;
        int rootPos = (rootRow * 9) + rootCol; 

        int[] colToCheck = new int[9];
        int[] rowToCheck = new int[9];
        int[] squareToCheck = {puzzle[rootPos], puzzle[rootPos + 1], puzzle[rootPos + 2] , puzzle[rootPos + 9], puzzle[rootPos + 10], puzzle[rootPos + 11], puzzle[rootPos + 18], puzzle[rootPos + 19], puzzle[rootPos + 20]};
        for (int i = 0; i < 9; i++){
            rowToCheck[i] = puzzle[i + (row*9)]; 
            colToCheck[i] = puzzle[col + (i * 9)];
        }

        if (noRepeats(colToCheck)){
            if (noRepeats(rowToCheck)){
                return noRepeats(squareToCheck);
            } else{
                return false;
            }
        } else{
            return false;
        }
    }

    public static int[] solve(int[] puzzle){
        Puzzle p = new Puzzle(puzzle);
        if (p.solved){
            return p.puzzle;
        }
        p.forwardInit();
        return unwrappedSolve(p, 0);

    }

    public static int[] unwrappedSolve(Puzzle puzz, int position){
        try{
            if (position < 0){
                System.err.println("The puzzle is unsolvable!");
                int[] blankPuzz = new int[81];
                return blankPuzz;
            } else if (position == (puzz.blankPositions.size())){
                puzz.solved = true;
                return puzz.puzzle;
            }
            
            int spot = puzz.blankPositions.get(position);
            int max = puzz.possibilities.get(spot).size() - 1;
            while (puzz.currentPos.get(spot) < max){
                puzz.currentPos.replace(spot, puzz.currentPos.get(spot) + 1);
                puzz.puzzle[spot] = puzz.possibilities.get(spot).get(puzz.currentPos.get(spot));
                if (isValid(puzz.puzzle, spot)){
                    return unwrappedSolve(puzz, position + 1);
                }
            }

            puzz.puzzle[spot] = 0;
            puzz.currentPos.replace(spot, -1);
            return unwrappedSolve(puzz, position - 1);
    } catch (StackOverflowError e){
        System.err.println("A StackOverFlowError has occured! This is due to the heap size not being set high enough in your JVM args. You can set it with -Xss{size}m. Extremely difficult puzzles may need 28 or higher.");
        int[] blankPuzz = new int[81];
        return blankPuzz;
    }

}}


