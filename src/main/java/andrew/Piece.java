package andrew;

import java.util.ArrayList;

public class Piece {

    private Board board;
    private int id;
    private ArrayList<int[]> coordinates;

    public Piece(ArrayList<int[]> coordinates, int id, Board board) {
        this.coordinates = coordinates;
        this.id = id;
        this.board = board;
    }

    public Piece(Tetromino shape, int id, Board board) {
        this.id = id;
        this.board = board;
        this.coordinates = new ArrayList<int[]>();

        int boardMiddle = this.board.getMatrix()[0].length / 2;

        if (shape == Tetromino.Line) {

            // Horizontal line
            this.coordinates.add(new int[] {boardMiddle - 2, 4});
            this.coordinates.add(new int[] {boardMiddle - 1, 4});
            this.coordinates.add(new int[] {boardMiddle, 4});
            this.coordinates.add(new int[] {boardMiddle + 1, 4});

        } else if (shape == Tetromino.L) {

            // Long part horizontal, nub pointing up
            this.coordinates.add(new int[] {boardMiddle - 1, 4});
            this.coordinates.add(new int[] {boardMiddle, 4});
            this.coordinates.add(new int[] {boardMiddle + 1, 4});
            this.coordinates.add(new int[] {boardMiddle + 1, 3});

        } else if (shape == Tetromino.MirroredL) {

            // Long part horizontal, nub pointing up
            this.coordinates.add(new int[] {boardMiddle - 1, 4});
            this.coordinates.add(new int[] {boardMiddle, 4});
            this.coordinates.add(new int[] {boardMiddle + 1, 4});
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            
        } else if (shape == Tetromino.Square) {

            // It's a square
            this.coordinates.add(new int[] {boardMiddle - 1, 4});
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            this.coordinates.add(new int[] {boardMiddle, 4});
            this.coordinates.add(new int[] {boardMiddle, 3});
            
        } else if (shape == Tetromino.S) {

            // Lying down
            this.coordinates.add(new int[] {boardMiddle - 1, 4});
            this.coordinates.add(new int[] {boardMiddle, 4});
            this.coordinates.add(new int[] {boardMiddle, 3});
            this.coordinates.add(new int[] {boardMiddle + 1, 3});
            
        } 
    }

    public ArrayList<int[]> getCoordinates() {
        return this.coordinates;
    }

    public int getId() {
        return this.id;
    }

    public boolean rotate() {
        return false;
    }

    public boolean canMoveDown() {

        boolean canMove = true;

        int[][] matrix = this.board.getMatrix();

        for (int[] co : this.coordinates) {
            if (matrix[co[1] + 1][co[0]] != 0 && matrix[co[1] + 1][co[0]] != this.id) {
                canMove = false;
            }
        }

        return canMove;
    }

    public boolean canMoveLeft() {
        
        boolean canMove = true;

        int[][] matrix = this.board.getMatrix();

        for (int[] co : this.coordinates) {
            if (matrix[co[1]][co[0] - 1] != 0 && matrix[co[1]][co[0] - 1] != this.id) {
                canMove = false;
            }
        }

        return canMove;
    }

    public boolean canMoveRight() {
        
        boolean canMove = true;

        int[][] matrix = this.board.getMatrix();

        for (int[] co : this.coordinates) {
            if (matrix[co[1]][co[0] + 1] != 0 && matrix[co[1]][co[0] + 1] != this.id) {
                canMove = false;
            }
        }

        return canMove;
    }

    public void moveDown() {
        for (int[] co : this.coordinates) {
            co[1]++;
        }
    }
}