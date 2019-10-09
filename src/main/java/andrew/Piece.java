package andrew;

import java.util.ArrayList;

public class Piece {

    private Board board;
    private int id;
    private ArrayList<int[]> coordinates;

    public Piece(ArrayList<int[]> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<int[]> getCoordinates() {
        return this.coordinates;
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
}