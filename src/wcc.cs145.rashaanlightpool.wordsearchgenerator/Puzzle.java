package WCC.CS145.RashaanLightpool.WordSearchGenerator;

import java.util.*;

// a class for creating word search puzzle objects
public class Puzzle {

    // object fields
    private List<String> words;
    private int rows;
    private int columns;
    private char[][] puzzle;
    private char[][] solution;
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

    // 0 argument constructor
    public Puzzle() {
        this.words = null;
        this.rows = 0;
        this.columns = 0;
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

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
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

    public void clearPuzzle() {
        this.words = null;
        this.puzzle = new char[this.rows][this.columns];
        this.solution = new char[this.rows][this.columns];
        this.created = false;
    }
}
