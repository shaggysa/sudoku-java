package com.sudoku;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {
    public ArrayList<short[]> unsolvedPuzzles = new ArrayList<>();
    public ArrayList<short[]> solvedPuzzles = new ArrayList<>();
    public int len;

    public Reader(String filename, boolean preloaded) {
        readCSV(filename, preloaded);
        this.len = unsolvedPuzzles.size();
    }
    
    private void readCSV(String filename, boolean preloaded) {
        if (preloaded) {
            boolean firstLine = true;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("puzzles.csv")))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    short[] unsolved = new short[81];
                    short[] solved = new short[81];
                    for (int i = 0; i < 81; i++) {
                        unsolved[i] = (short) (line.charAt(i) - '0');
                        solved[i] = (short) (line.charAt(i + 82) - '0');
                    }
                    this.unsolvedPuzzles.add(unsolved);
                    this.solvedPuzzles.add(solved);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            boolean firstLine = true;
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    short[] unsolved = new short[81];
                    short[] solved = new short[81];
                    for (int i = 0; i < 81; i++) {
                        unsolved[i] = (short) (line.charAt(i) - '0');
                        solved[i] = (short) (line.charAt(i + 82) - '0');
                    }
                    this.unsolvedPuzzles.add(unsolved);
                    this.solvedPuzzles.add(solved);
                }
            } catch (IOException e) {
                System.err.println("Could not read " + filename);
                e.printStackTrace();
            }
        }
    }
    short[] getPuzzle (int lineNumber){
        return this.unsolvedPuzzles.get(lineNumber - 2);
    }
    short[] getAnswer (int lineNumber){
        return this.solvedPuzzles.get(lineNumber - 2);
    }
}
