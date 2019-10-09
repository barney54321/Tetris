package andrew;

import java.util.ArrayList;

public class Board {

    // Normal tetris board is 10*20
    // Use a 10*24 board to allow for hidden rotations

    // If we use the ID method for storing locations in a matrix
    // then we need two extra columns and an extra row for -1

    private int[][] matrix;
    private ArrayList<Piece> pieces;

    public Board() {
        this.matrix = new int[24][12];

        // Set bottom row as -1
        for (int i = 0; i < this.matrix[this.matrix.length - 1].length; i++) {
            this.matrix[this.matrix.length - 1][i] = -1;
        }

        // Set left and right columns as -1
        for (int i = 0; i < this.matrix.length; i++) {
            this.matrix[i][0] = -1;
            this.matrix[i][this.matrix[i].length - 1] = -1;
        }

        this.pieces = new ArrayList<Piece>();
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    public boolean addPiece(Piece piece) {

        boolean placed = true;
        this.pieces.add(piece);

        for (int[] coordinate : piece.getCoordinates()) {
            if (this.matrix[coordinate[1]][coordinate[0]] != 0) {
                placed = false;
            } else {
                this.matrix[coordinate[1]][coordinate[0]] = piece.getId();
            }
        }

        return placed;
    }
}