package com.sudoku;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument for the puzzles file was provided, using preloaded puzzles.");
            SpeedTest.speedTest("puzzles.csv", true);
        } else {
        SpeedTest.speedTest(args[0], false);
    }}
}
