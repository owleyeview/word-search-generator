/*
Rashaan Lightpool
11/08/2022
CS 145
Word Search Generator Assignment
Puzzle.java
 */

package WCC.CS145.RashaanLightpool.WordSearchGenerator;

import java.util.*;

// a class for creating word search puzzle objects
public class Puzzle {

    // object fields
    private List<String> words;  // words this puzzle will be built from
    private int rows;
    private int columns;
    private char[][] puzzle;  // grid of the word search puzzle
    private char[][] solution;  // grid showing the solution
    private boolean created;

    // 3 argument constructor
    public Puzzle(List<String> words, int rows, int columns) {
        this.words = words;
        this.rows = rows;
        this.columns = columns;
        this.puzzle = new char[rows][columns];
        this.solution = new char[rows][columns];
        this.created = false;
    }

    // 2 argument constructor
    public Puzzle(int rows, int columns) {
        this.words = null;
        this.rows = rows;
        this.columns = columns;
        this.puzzle = new char[rows][columns];
        this.solution = new char[rows][columns];
        this.created = false;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public char[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(char[][] puzzle) {
        this.puzzle = puzzle;
    }

    public char[][] getSolution() {
        return solution;
    }

    public void setSolution(char[][] solution) {
        this.solution = solution;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    // clear the contents of the puzzle object
    public void clearPuzzle() {
        this.words = null;
        this.puzzle = new char[this.rows][this.columns];
        this.solution = new char[this.rows][this.columns];
        this.created = false;
    }
}
