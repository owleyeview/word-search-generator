package WCC.CS145.RashaanLightpool.WordSearchGenerator;

import java.io.*;
import java.util.*;

public class WordSearchGenerator {
    
    private final static int GRID_ROWS = 10;
    private final static int GRID_COLS = 10;
    private final static int MAX_WORD_SIZE = 10;
    private final static Random RAND = new Random();

    private static boolean puzzleCreated = false;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        char[][] puzzle = new char[GRID_COLS][GRID_ROWS];
        char[][] solution = new char[GRID_COLS][GRID_ROWS];
        // boolean puzzleCreated = false;
        String choice; // input variable
        boolean done = false; // boolean flag

        printIntro();
        do{
            printMainMenu();
            choice = scan.nextLine().toUpperCase();
            switch (choice) {
                case "G":
                    generate(scan, puzzle, solution);
                    break;
                case "V":
                    print(puzzle);
                    break;
                case "P":
                    print(solution);
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

    public static void printIntro() {
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
        System.out.println("(G)enerate a new word search puzzle \n" +
                "(V)iew your puzzle \n(P)eek at the solution \n" +
                "(Q)uit the program");
    }

    public static void generate(Scanner scan, char[][] puzzle, char[][] solution) {
        List<String> words = new ArrayList<String>(getWords(scan));
        Collections.shuffle(words);
        for (String word : words) {
            tryToPlaceWord(word, puzzle);
        }
        // copy words to the solution array
        for (int i = 0; i < puzzle.length; i++) {
            solution[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);
        }
        fillWithLetters(puzzle);
        fillWithPlaceholder(solution);
        puzzleCreated = true;
        System.out.println("Puzzle generated successfully");
    }

    public static void tryToPlaceWord(String word, char[][] puzzle) {
        int firstCell = RAND.nextInt(100);
        int firstDir = RAND.nextInt(8) + 1;
        int tries = 0;
        int cell = firstCell;
        int dir = firstDir;
        do {
            for(int dirsTried = 0; dirsTried < 8 ; dirsTried++) {
                if (checkGridBounds(word, cell, dir)) {
                    if (checkCells(puzzle, word, cell, dir)) { // check all the cells
                        placeWord (puzzle, word, cell, dir);
                        return;
                    }
                    tries++;
                } else {
                    dir = ((dir + 1) % 8) + 1;
                    tries++;
                }
            }
            cell = (cell + 1) % (GRID_ROWS * GRID_COLS);
        } while (tries < 500); // 500 tries per word
    }

    public static boolean checkGridBounds(String word, int cell, int dir) {
        int cellRow = cell / GRID_COLS; // -1?
        int cellColumn = cell % GRID_ROWS; // -1?
        int checkRow = cellRow + (getGridDirection(dir)).getMoves()[0] * word.length();
        int checkCol = cellColumn + (getGridDirection(dir)).getMoves()[1] * word.length();
        return (checkRow >= 0 && checkRow < GRID_ROWS) && (checkCol >= 0 && checkCol < GRID_COLS);
    }

    public static boolean checkCells(char[][] puzzle, String word, int cell, int dir) {
       int startRow = cell / GRID_COLS;
       int startColumn = cell % GRID_ROWS;
       int length = word.length();
       for (int i = 0, r = startRow, c = startColumn; i < length; i++) {
           if (puzzle[r][c] == 0 || puzzle[r][c] == word.charAt(i)) {
               r = r + getGridDirection(dir).getMoves()[0];
               c = c + getGridDirection(dir).getMoves()[1];
           } else return false;
       }
       return true;
    }

    public static void placeWord(char[][] puzzle, String word, int cell, int dir) {
       int startRow = cell / GRID_COLS;
       int startColumn = cell % GRID_ROWS;
       int length = word.length();
       for (int i = 0, r = startRow, c = startColumn; i < length; i++) {
           puzzle[r][c] = word.charAt(i);
           r = r + getGridDirection(dir).getMoves()[0];
           c = c + getGridDirection(dir).getMoves()[1];
       }
    }

    public static void fillWithLetters(char[][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == 0) {
                    puzzle[i][j] = (char)(RAND.nextInt(26) + 65);
                }
            }
        }
    }

    public static void fillWithPlaceholder(char[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] == 0) {
                    solution[i][j] = (char)(126); // placeholder character
                }
            }
        }
    }
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
    public static void print(char[][] arr) {
        if (!puzzleCreated) {
            System.out.println("!You must generate a puzzle before viewing!");
            sleep(1);
        } else {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

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
        }
        return words;
    }

    public static void sleep(double seconds) {
        seconds = seconds * 1000;
        try {
            Thread.sleep((long) seconds);
        } catch (InterruptedException e) {}
    }
}
