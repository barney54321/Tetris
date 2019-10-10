package andrew;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

public class Board {

    // Normal tetris board is 10*20
    // Use a 10*24 board to allow for hidden rotations

    // If we use the ID method for storing locations in a matrix
    // then we need two extra columns and an extra row for -1

    private int[][] matrix;
    private ArrayList<Piece> pieces;
    private Piece activePiece;
    private int currentId;
    private HashMap<Integer, Piece> pieceMap;

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
        this.currentId = 2;
        this.activePiece = null;
        this.pieceMap = new HashMap<Integer, Piece>();
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    public boolean addPiece(Piece piece) {

        boolean placed = true;
        this.pieces.add(piece);
        this.activePiece = piece;
        this.pieceMap.put(piece.getId(), piece);

        try {
            for (int[] coordinate : piece.getCoordinates()) {
                if (this.matrix[coordinate[1]][coordinate[0]] != 0) {
                    placed = false;
                    break;
                } else {
                    this.matrix[coordinate[1]][coordinate[0]] = piece.getId();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            placed = false;
        }

        return placed;
    }

    public boolean moveActive() {

        if (this.activePiece.canMoveDown()) {

            // Save copy of old coordinates
            ArrayList<int[]> old = new ArrayList<int[]>();
            for (int[] co : this.activePiece.getCoordinates()) {
                old.add(new int[] {co[0], co[1]});
            }

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

            // Check to see if coordinates have changed
            boolean changed = false;
            for (int i = 0; i < old.size(); i++) {
                if (old.get(i)[0] != this.activePiece.getCoordinates().get(i)[0]) {
                    return true;
                } else if (old.get(i)[1] != this.activePiece.getCoordinates().get(i)[1]) {
                    return true;
                }
            }

        }

        Tetromino[] possible = Tetromino.values();
        Random r = new Random();
        Tetromino next = possible[r.nextInt(possible.length)];
        boolean res = this.addPiece(new Piece(next, this.currentId, this));
        this.currentId++;

        return res;

    }

    public void moveLeft() {

        // Clear old coordinates
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = 0;
        }

        // Move left
        this.activePiece.moveLeft();

        // Adjust matrix
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = this.activePiece.getId();
        }

    }

    public void moveRight() {

         // Clear old coordinates
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = 0;
        }

        // Move right
        this.activePiece.moveRight();

        // Adjust matrix
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = this.activePiece.getId();
        }

    }

    public void rotateClockwise() {

        // Clear old coordinates
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = 0;
        }

        // Rotate clockwise
        this.activePiece.rotateClockwise();

        // Adjust matrix
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = this.activePiece.getId();
        }

    }

    public void rotateAntiClockwise() {

         // Clear old coordinates
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = 0;
        }

        // Rotate anti-clockwise
        this.activePiece.rotateAntiClockwise();

        // Adjust matrix
        for (int[] co : this.activePiece.getCoordinates()) {
            this.matrix[co[1]][co[0]] = this.activePiece.getId();
        }

    }

    public boolean checkRow(int[] row) {
        // Returns true if row is not zero and
        for (int i : row) {
            if (i == 0 || i == this.activePiece.getId()) {
                return false;
            }
        }
        return true;
    }

    public void clearRows() {

        int removed = 0;

        for (int i = 0; i < this.matrix.length - 1; i++) {
            if (checkRow(this.matrix[i])) {

                removed++;

                // Remove the row
                for (int j = i; j > 0; j--) {
                    this.matrix[j] = this.matrix[j - 1]; 
                }

            }
        }

    }

    public void tick() {

        if (!this.moveActive()) {

            this.reset();

        } else {

            // Clear completed rows
            this.clearRows();

        }
    }

    public void render(Graphics g) {

        g.setColor(Color.white);
        Font fnt = new Font("arial",1,60);
        g.setFont(fnt);
        g.drawString("Tetris", 55, 50);

        // Draw board
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                g.setColor(Color.white);
                g.drawRect(40 + 20 * j, 60 + 20 * i, 20, 20);
                if (this.matrix[i + 3][j + 1] != 0) {
                    g.setColor(this.pieceMap.get(this.matrix[i + 3][j + 1]).getColor());
                    g.fillRect(41 + 20 * j, 61 + 20 * i, 17, 17);
                }
            }
        }

    }

    public void reset() {

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
        this.currentId = 2;
        this.activePiece = null;
        this.pieceMap = new HashMap<Integer, Piece>();
        this.addPiece(new Piece(Tetromino.Line, 1, this));

    }

    public void input(InputType key) {

        if (this.activePiece != null) {

            if (key == InputType.Left) {
                if (this.activePiece.canMoveLeft()) {
                    this.moveLeft();
                }
            } else if (key == InputType.Right) {
                if (this.activePiece.canMoveRight()) {
                    this.moveRight();
                }
            } else if (key == InputType.RotateLeft) {
                if (this.activePiece.canRotateAntiClockwise()) {
                    this.rotateAntiClockwise();
                }
            } else if (key == InputType.RotateRight) {
                if (this.activePiece.canRotateClockwise()) {
                    this.rotateClockwise();
                }
            }

        }

    }

}