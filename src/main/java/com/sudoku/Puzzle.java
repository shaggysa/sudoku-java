package com.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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
        preInit();
    }

    private void gatherBlanks(){
        for (int i = 0; i < 81; i++){
            if (this.puzzle[i] == 0){
                this.blankPositions.add(i);
            }
        }
    }

    private void preInit(){
        ArrayList<Integer> toRemove = new ArrayList<>();
        parentLoop:
        for (int item:this.blankPositions){
            int solution = 0;
            boolean found = false; 
            for (int i = 1; i < 10; i++){
                this.puzzle[item] = i;
                if (Solver.isValid(this.puzzle, item)){
                    if (found){
                        this.puzzle[item] = 0;
                        continue parentLoop;
                    } else{
                        found = true;
                        solution = i;
                    }
                }
            }
            toRemove.add(item);
            this.puzzle[item] = solution;
        }
        if (!toRemove.isEmpty()){
            for (int item:toRemove){
                this.blankPositions.remove(Integer.valueOf(item));
            }
            preInit();
        } else if (blankPositions.size() == 0){
            this.solved = true;
        }
    }

    public void forwardInit(){
        for (int i:blankPositions){
            ArrayList<Integer> tempPossibilities = new ArrayList<>();
            for (int j = 1; j < 10; j++) {
                this.puzzle[i] = j;
                if (Solver.isValid(this.puzzle, i)){
                    tempPossibilities.add(j);
                }
            }
            this.puzzle[i] = 0;
            this.possibilities.put(i, tempPossibilities);
            this.currentPos.put(i, -1);
        }
    }


    public void reverseInit(){
        for (int i:blankPositions){
            ArrayList<Integer> tempPossibilities = new ArrayList<>();
            for (int j = 9; j > 0; j -= 1) {
                this.puzzle[i] = j;
                if (Solver.isValid(this.puzzle, i)){
                    tempPossibilities.add(j);
                }
            }
            this.puzzle[i] = 0;
            this.possibilities.put(i, tempPossibilities);
            this.currentPos.put(i, -1);
        }
    }
    

    public void randomInit(){
        for (int i:blankPositions){
            ArrayList<Integer> tempPossibilities = new ArrayList<>();
            for (int j = 1; j < 10; j++) {
                this.puzzle[i] = j;
                if (Solver.isValid(this.puzzle, i)){
                    tempPossibilities.add(j);
                }
            }
            this.puzzle[i] = 0;
            Collections.shuffle(tempPossibilities);
            this.possibilities.put(i, tempPossibilities);
            this.currentPos.put(i, -1);
        }
    }
}
