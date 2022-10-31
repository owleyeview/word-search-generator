package WCC.CS145.RashaanLightpool.WordSearchGenerator;

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
// System.out.println(GridDirections.EAST.getMoves()[1]);