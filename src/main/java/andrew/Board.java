package andrew;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    // Normal tetris board is 10*20
    // Use a 10*24 board to allow for hidden rotations

    // If we use the ID method for storing locations in a matrix
    // then we need two extra columns and an extra row for -1

    private int[][] matrix;
    private ArrayList<Piece> pieces;
    private Piece activePiece;
    private int currentId;

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
        this.currentId = 1;
        this.activePiece = null;
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    public boolean addPiece(Piece piece) {

        boolean placed = true;
        this.pieces.add(piece);
        this.activePiece = piece;

        for (int[] coordinate : piece.getCoordinates()) {
            if (this.matrix[coordinate[1]][coordinate[0]] != 0) {
                placed = false;
            } else {
                this.matrix[coordinate[1]][coordinate[0]] = piece.getId();
            }
        }

        return placed;
    }

    public void moveActive() {
        if (this.activePiece.canMoveDown()) {

            // Clear old coordinates
            for (int[] co : this.activePiece.getCoordinates()) {
                this.matrix[co[1]][co[0]] = 0;
            }

            // Move down
            this.activePiece.moveDown();

            // Adjust matrix
            for (int[] co : this.activePiece.getCoordinates()) {
                this.matrix[co[1]][co[0]] = this.activePiece.getId();
            }

        } else {

            Tetromino[] possible = Tetromino.values();
            Random r = new Random();
            Tetromino next = possible[r.nextInt(possible.length)];
            this.addPiece(new Piece(next, this.currentId, this));
            this.currentId++;
            
        }
    }
}