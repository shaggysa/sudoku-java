package com.sudoku;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class Reader {
    private final ArrayList<ArrayList<String>> puzzleList;

    public Reader(String filename) {
        this.puzzleList = readCSV(filename);
    }
    
    private ArrayList<ArrayList<String>> readCSV(String filename) {
    ArrayList<String> unsolved = new ArrayList<>();
    ArrayList<String> solved = new ArrayList<>();
    ArrayList<ArrayList<String>> all = new ArrayList<>();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
         CSVReader reader = new CSVReader(inputStreamReader)) {
        
        List<String[]> allRows = reader.readAll();
        for (String[] row : allRows) {
            if (row.length == 2) {
                unsolved.add(row[0]);
                solved.add(row[1]);
            } else if (row.length == 1){
                unsolved.add(row[0]);
            }
        }
        all.add(unsolved);
        all.add(solved);

        
    } catch (IOException | CsvException e) {
        System.err.println("Error reading CSV: " + e.getMessage());
    }
    return all;
}
    public int[] getPuzzle (int lineNumber){
        String puzzleString = this.puzzleList.get(0).get(lineNumber - 1);
        int[] puzzle = new int[81];
        for (int i = 0; i < 81; i++){
            puzzle[i] = Character.getNumericValue(puzzleString.charAt(i));
        }
        return puzzle;
    }
    public int[] getAnswer (int lineNumber){
        String puzzleString = this.puzzleList.get(1).get(lineNumber - 1);
        int[] puzzle = new int[81];
        for (int i = 0; i < 81; i++){
            puzzle[i] = Character.getNumericValue(puzzleString.charAt(i));
        }
        return puzzle;
    }
}
