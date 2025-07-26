package com.sudoku;

import java.util.*;


public class Puzzle {
    public int[] puzzle = new int[81];
    public ArrayList<Integer> blankPositions = new ArrayList<>();
    public HashMap<Integer, ArrayList<Integer>> possibilities = new HashMap<>();
    public HashMap<Integer, Integer> currentPos = new HashMap<>();
    public boolean brandNew = true;
    public boolean solved = false;

    public Puzzle(int[] puzzle){
        this.puzzle = puzzle;
        gatherBlanks();
        forwardInit();
    }
    private ArrayList<Integer> getPossibilites(int position) {
        boolean[] seen = new boolean[10];
        ArrayList<Integer> possibilities = new ArrayList<>();
        int row = position / 9;
        int col = position % 9;

        int rootRow = (row / 3) * 3;
        int rootCol = (col / 3) * 3;


        for (int r = rootRow; r < rootRow + 3; r++) {
            for (int c = rootCol; c < rootCol + 3; c++) {
                seen[this.puzzle[r * 9 + c]] = true;
            }
        }

        for (int i = 0; i < 9; i++){
            seen[this.puzzle[i + (row*9)]] = true;
            seen[this.puzzle[col + (i * 9)]] = true;
        }
        for (int i = 1; i < 10; i++) {
            if (!seen[i]) {
                possibilities.add(i);
            }
        }
        return possibilities;
    }
    private void gatherBlanks(){
        for (int i = 0; i < 81; i++){
            if (this.puzzle[i] == 0){
                this.blankPositions.add(i);
            }
        }
    }


    public void forwardInit(){
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (int i:blankPositions){
            ArrayList<Integer> tempPossibilities = getPossibilites(i);
            if (tempPossibilities.size() == 1) {
                this.puzzle[i] = tempPossibilities.get(0);
                toRemove.add(i);
            } else{
                this.possibilities.put(i, tempPossibilities);
                this.currentPos.put(i, -1);
            }

        }
        for (int item:toRemove) {
            this.blankPositions.remove(Integer.valueOf(item));
        }
    }
}