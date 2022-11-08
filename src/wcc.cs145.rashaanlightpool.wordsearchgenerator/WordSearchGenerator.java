/*
Rashaan Lightpool
11/08/2022
CS 145
Word Search Generator Assignment
WordSearchGenerator.java
 */

/*
This program generates word search puzzles containing user provided words.
The words can be given via command prompt or text file.
 */
package WCC.CS145.RashaanLightpool.WordSearchGenerator;

import java.io.*;
import java.util.*;

public class WordSearchGenerator {
    
    private final static int GRID_ROWS = 10;
    private final static int GRID_COLS = 10;
    private final static int MAX_WORD_SIZE = 10; // max letters per word
    private final static Random RAND = new Random();


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        // create a new puzzle object
        Puzzle grid = new Puzzle(GRID_ROWS, GRID_COLS);

        String choice; // input variable
        boolean done = false; // boolean flag

        printIntro();
        do{
            printMainMenu();
            choice = scan.nextLine().toUpperCase();  // accept user input
            switch (choice) {
                case "G":
                    List<String> words = new ArrayList<>(getWords(scan));
                    grid.clearPuzzle();
                    grid.setWords(words);
                    generate(grid);
                    break;
                case "V":
                    boolean p = false;
                    print(grid, p);
                    break;
                case "P":
                    boolean peek = true;
                    print(grid, peek);
                    break;
                case "S":
                    savePuzzle(grid);
                    break;
                case "Q":
                    done = true;
                    break;
                default:
                    System.out.println("!Please select a valid option!");
                    sleep(1);
                    break;
            }
        } while (!done);
    }

    public static void printIntro() {  // intro message
        System.out.println("================================================");
        System.out.println("  Welcome to the Word Search Puzzle Generator! ");
        System.out.println("================================================");
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println(" This program can be used to create word search");
        System.out.println(" puzzles from words typed by the user or words ");
        System.out.println(" input from a user provided text file. Have fun!");
        System.out.println("------------------------------------------------");
        sleep(3);
    }

    public static void printMainMenu() {
        System.out.println();
        System.out.println("Please select an option:");
        System.out.println("(G)enerate a new word search puzzle\n" +
                "(V)iew your puzzle\n(P)eek at the solution\n" +
                "(S)ave this puzzle to a file\n(Q)uit the program");
    }

    // given a puzzle object, create a new word search puzzle
    public static void generate(Puzzle grid) {
        List<String> words = grid.getWords();
        Collections.shuffle(words);
        for (String word : words) {
            tryToPlaceWord(grid, word);
        }
        // copy words to the solution array
        char [][] s = new char[grid.getRows()][grid.getColumns()];
        for (int i = 0; i < grid.getPuzzle().length; i++) {
            s[i] = Arrays.copyOf(grid.getPuzzle()[i], grid.getPuzzle()[i].length);
        }
        grid.setSolution(s);
        fillWithLetters(grid);
        fillWithPlaceholder(grid);
        grid.setCreated(true);
        System.out.println("Puzzle generated successfully");
        sleep(1);
    }


    // Given a puzzle object and a word String,
    // attempts to place the word in the puzzle array
    public static void tryToPlaceWord(Puzzle grid, String word) {
        // random cell for the first letter of the word
        int firstCell = RAND.nextInt(100);
        // random direction to try first
        int firstDir = RAND.nextInt(8) + 1;
        int tries = 0;
        int cell = firstCell;
        int dir = firstDir;
        do {
            for(int dirsTried = 0; dirsTried < 8 ; dirsTried++) {
                // would the word stay within the grid?
                if (checkGridBounds(grid, word, cell, dir)) {
                    if (checkCells(grid, word, cell, dir)) { // check all the cells
                        placeWord (grid, word, cell, dir);
                        return;
                    }
                } else {
                    dir = ((dir + 1) % 8) + 1; // check all 8 directions
                }
                tries++;
            }
            // try the next cell through the rows
            cell = (cell + 1) % (GRID_ROWS * GRID_COLS);
        } while (tries < 5000); // 5000 tries per word
    }

    // Given a puzzle object, word String, cell, and direction
    // returns if the word will stay within the grid in that direction
    public static boolean checkGridBounds(Puzzle puzzle, String word, int cell, int dir) {
        int cellRow = cell / puzzle.getColumns();
        int cellColumn = cell % puzzle.getRows();
        int checkRow = cellRow + (getGridDirection(dir)).getMoves()[0] * word.length();
        int checkCol = cellColumn + (getGridDirection(dir)).getMoves()[1] * word.length();
        return (checkRow >= 0 && checkRow < puzzle.getRows())
                && (checkCol >= 0 && checkCol < puzzle.getColumns());
    }

    // Given a puzzle object, word String, cell, and direction
    // returns if each cell for this word placement
    // is empty or matches the letter of the word
    public static boolean checkCells(Puzzle grid, String word, int cell, int dir) {
       char[][] puzzle = grid.getPuzzle();
       int startRow = cell / grid.getColumns();
       int startColumn = cell % grid.getRows();
       int length = word.length();
       for (int i = 0, r = startRow, c = startColumn; i < length; i++) {
           // check the cell
           if (puzzle[r][c] == 0 || puzzle[r][c] == word.charAt(i)) {
               // move to the next cell in the given direction
               r = r + getGridDirection(dir).getMoves()[0];
               c = c + getGridDirection(dir).getMoves()[1];
           } else return false;
       }
       return true;
    }

    // Given a puzzle object, word String, cell, and direction
    // places the word in the puzzle grid
    public static void placeWord(Puzzle grid, String word, int cell, int dir) {
       char[][] puzzle = grid.getPuzzle();
       int startRow = cell / grid.getColumns();
       int startColumn = cell % grid.getRows();
       int length = word.length();
       for (int i = 0, r = startRow, c = startColumn; i < length; i++) {
           puzzle[r][c] = word.charAt(i);
           r = r + getGridDirection(dir).getMoves()[0];
           c = c + getGridDirection(dir).getMoves()[1];
       }
       grid.setPuzzle(puzzle);
    }

    // Given a puzzle object, fills the empty cells
    // of the puzzle grid with random letters
    public static void fillWithLetters(Puzzle grid) {
        char[][] puzzle = grid.getPuzzle();
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == 0) {
                    puzzle[i][j] = (char)(RAND.nextInt(26) + 65);
                }
            }
        }
        grid.setPuzzle(puzzle);
    }

    // Given a puzzle object, fills the empty cells
    // of the solution grid with a placeholder character
    public static void fillWithPlaceholder(Puzzle grid) {
        char[][]solution = grid.getSolution();
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] == 0) {
                    solution[i][j] = (char)(126); // placeholder character
                }
            }
        }
        grid.setSolution(solution);
    }

    // Given an integer, returns a GridDirection enum value
    public static GridDirections getGridDirection (int dir) {
        switch (dir) {
            case 1:
                return GridDirections.NORTH;
            case 2:
                return GridDirections.NORTHEAST;
            case 3:
                return GridDirections.EAST;
            case 4:
                return GridDirections.SOUTHEAST;
            case 5:
                return GridDirections.SOUTH;
            case 6:
                return GridDirections.SOUTHWEST;
            case 7:
                return GridDirections.WEST;
            case 8:
                return GridDirections.NORTHWEST;
            default:
                throw new RuntimeException("not a valid direction");
        }
    }

    // Given a puzzle object, and boolean value
    // displays either a puzzle or solution to the screen
    public static void print(Puzzle puzzle, boolean peek) {
        if (!puzzle.isCreated()) {
            System.out.println("!You must generate a puzzle before viewing!");
            sleep(1);
        } else {
            char[][] arr;
            if (peek) {
                arr = puzzle.getSolution();
            } else {
                arr = puzzle.getPuzzle();
            }
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    System.out.print(arr[i][j] + "  ");
                }
                System.out.println();
            }
        }
    }

    // Given a puzzle object, saves the puzzle and solution to a file
    public static void savePuzzle(Puzzle grid) throws FileNotFoundException {
        if (!grid.isCreated()) {
            System.out.println("!You must generate a puzzle before saving!");
            sleep(1);
        } else {
            PrintStream out = new PrintStream(new File("saved-puzzle.txt"));
            out.println(grid.getWords()); // prints the list of words
            out.println();
            // print the puzzle
            for (int i = 0; i < grid.getPuzzle().length; i++) {
                for (int j = 0; j < grid.getPuzzle()[i].length; j++) {
                    out.print(grid.getPuzzle()[i][j] + "  ");
                }
                out.println();
            }
            out.println();
            out.println();
            out.println();
            //print the solution
            for (int i = 0; i < grid.getSolution().length; i++) {
                for (int j = 0; j < grid.getSolution()[i].length; j++) {
                    out.print(grid.getSolution()[i][j] + "  ");
                }
                out.println();
            }
            System.out.println("Puzzle saved to saved-puzzle.txt");
            sleep(1);
        }
    }

    // Given a Scanner object, prompts the user for words or a word file
    // returns an ArrayList of words
    public static List<String> getWords(Scanner scan) {
        System.out.println("How would you like to provide the words?\n(K)eyboard input or .txt (F)ile?");
        List<String> words = new ArrayList<String>();
        String input = scan.nextLine().toUpperCase();
        switch (input) {
            case "K":
                System.out.println("Give me some words to play with!");
                System.out.println("Please enter 10 words between 3 and " + MAX_WORD_SIZE + " characters long: ");
                int wordCount = 0;
                while (words.size() < 10) {
                    String nextWord = scan.nextLine().toUpperCase();
                    if (nextWord.matches("^[A-Z]{3," + MAX_WORD_SIZE + "}$")) {
                        words.add(nextWord);
                    }
                }
                break;
            case "F":
                System.out.print("Enter the name of the puzzle word file: ");
                try (Scanner fileRead = new Scanner(new FileReader(scan.nextLine()))) {
                    while (fileRead.hasNext()) {
                        String nextWord = fileRead.next().toUpperCase();
                        if (nextWord.matches("^[A-Z]{3," + MAX_WORD_SIZE + "}$")) {
                            words.add(nextWord);
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("Invalid input!");
                System.out.println("No words added!");
                sleep(1);
                System.out.print("Empty ");
        }
        return words;
    }

    // Given a double number, sleeps the program
    public static void sleep(double seconds) {
        seconds = seconds * 1000;
        try {
            Thread.sleep((long) seconds);
        } catch (InterruptedException e) {}
    }
}
