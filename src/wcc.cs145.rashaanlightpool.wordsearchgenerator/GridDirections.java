/*
Rashaan Lightpool
11/08/2022
CS 145
Word Search Generator Assignment
GridDirections.java
 */

package WCC.CS145.RashaanLightpool.WordSearchGenerator;

//  This enum holds valid directions for traversing a grid
public enum GridDirections {
    NORTH(new int[]{0, -1}), NORTHEAST(new int[]{1, -1}), EAST(new int[]{1, 0}), SOUTHEAST(new int[]{1, 1}),
    SOUTH(new int[]{0, 1}), SOUTHWEST(new int[]{-1, 1}), WEST(new int[]{-1, 0}), NORTHWEST(new int[]{-1, -1});

    private int[] moves;

    GridDirections(int[] moves) {
        this.moves = moves;
    }

    public int[] getMoves() {
        return moves;
    }
}