# sudoku-java
A java version of my sudoku solver program. It can solve 1000 puzzles in 35 ms. 

Currently in early alpha.
The current setup will simply solve 999 preloaded puzzles and report back with the speed.

# usage
Download the .jar file from the lastest release and run it with "java -jar sudoku-2.0.jar" to solve the pre-loaded puzzles. If you want to have it solve your own puzzles, you can run it with "java -jar sudoku-2.0.jar {filename}".

Note that the jar and the csv file with your puzzles must be in the same folder.

If you are a more advanced user, you can clone the repo and run mvn package to build the jar yourself.
