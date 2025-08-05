package com.sudoku;

import java.util.*;


public class Puzzle {
    public short[] puzzle = new short[81];
    public ArrayList<Short> blankPositions = new ArrayList<>();
    public ArrayList<ArrayList<Short>> possibilities = new ArrayList<>();
    public ArrayList<boolean[]> cachedPossibilites = new ArrayList<>();
    public ArrayList<Short> currentPos = new ArrayList<>();
    public boolean solved = false;

    public Puzzle(short[] puzzle){
        this.puzzle = puzzle;
        gatherBlanks();
    }

    ArrayList<Short> getPossibilites(int position) {
        boolean[] seen = new boolean[10];
        ArrayList<Short> possibilities = new ArrayList<>();
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
        for (short i = 1; i < 10; i++) {
            if (!seen[i]) {
                possibilities.add(i);
            }
        }
        return possibilities;
    }
    private void gatherBlanks(){
        for (short i = 0; i < 81; i++){
            if (this.puzzle[i] == 0){
                this.blankPositions.add(i);
            }
        }
    }

    public void forwardInit(){
        while (true) {
        ArrayList<Short> toRemove = new ArrayList<>();
        for (short i:this.blankPositions){
            ArrayList<Short> possibilities = getPossibilites(i);
            if (possibilities.size() == 1) {
                this.puzzle[i] = possibilities.get(0);
                toRemove.add(i);
            }
        }
        if (toRemove.size() == this.blankPositions.size()) {
            this.solved = true;
            return;
            } else if (toRemove.isEmpty()) {
            for (short item : this.blankPositions) {
                this.possibilities.add(getPossibilites(item));
                this.currentPos.add((short) -1);
            }
            return;
        }
            for (short item:toRemove) {
                this.blankPositions.remove(Short.valueOf(item));
            }
        }
    }
}