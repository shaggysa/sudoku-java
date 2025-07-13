package com.sudoku;
import java.util.Arrays;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class SpeedTest {
    public static void speedTest (String fileName, int firstLine, int lastLine){
	long startTime = System.nanoTime();
        Reader puzzles = new Reader(fileName);
        DescriptiveStatistics times = new DescriptiveStatistics();
        for (int i = firstLine; i <= lastLine; i++){
            System.out.println("line: " + i);
            int[] unsolved = puzzles.getPuzzle(i);

	    System.out.println("Unsolved:");
            Solver.printPuzzle(unsolved);

            long old = System.nanoTime();
            int[] ans = Solver.solve(unsolved);
            long time = System.nanoTime() - old;
            
	    System.out.println("\nSolved:");
            Solver.printPuzzle(ans);
            
	    if (Arrays.equals(ans,puzzles.getAnswer(i))) {
                System.out.println("The solved puzzle matches the answer key!");
                times.addValue(time/1000000.0);
            } else{
                System.err.println("The solved puzzle does not match the answer key! The correct answer is:");
                Solver.printPuzzle(puzzles.getAnswer(i));
                break;
            }
        }
        
        System.out.println("\nTime Statistics(ms):");
        System.out.println("Mean: " + times.getMean());
        System.out.println("Median: " + times.getPercentile(50));
        System.out.println("Min: " + times.getMin());
        System.out.println("Max: " + times.getMax());
        System.out.println("Total (solving time): " + times.getSum());
        System.out.println("Total (including all file reading and puzzle printing overhead): " + (System.nanoTime() - startTime) / 1000000.0);
    }
}
