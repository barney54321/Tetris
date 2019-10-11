/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package andrew;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BoardTest {

    public static boolean checkLists(ArrayList<int[]> a, ArrayList<int[]> b) {
        boolean res = true;

        if (a.size() != b.size()) {
            return false;
        }

        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.get(i).length; j++) {
                if (a.get(i)[j] != b.get(i)[j]) {
                    res = false;
                }
            }
        }

        return res;
    }

    @Test
    public void addPieceSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);

        assertTrue(b.addPiece(p));

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 2, 3});
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle + 1, 3});

        assertTrue(checkLists(coordinates, p.getCoordinates()));

    }

    @Test
    public void addPieceOutOfBounds() {
        Board b = new Board();
        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        coordinates.add(new int[] {10, 29});
        coordinates.add(new int[] {2, 29});
        coordinates.add(new int[] {194, 29});
        Piece p = new Piece(coordinates, 1, b);

        assertFalse(b.addPiece(p));
    }

    @Test
    public void addPieceCollision() {
        Board b = new Board();
        Piece p1 = new Piece(Tetromino.Line, 1, b);
        Piece p2 = new Piece(Tetromino.Line, 2, b);

        assertTrue(b.addPiece(p1));
        assertFalse(b.addPiece(p2));
    }

    @Test
    public void moveActiveSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.L, 1, b);
        b.addPiece(p);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        for (int[] arr : p.getCoordinates()) {
            coordinates.add(new int[] {arr[0], arr[1] + 1});
        }

        assertTrue(b.moveActive());
        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void moveActiveFail() {
        Board b = new Board();
        Piece p1 = new Piece(Tetromino.Line, 1, b);
        b.addPiece(p1);
        b.moveActive();

        Piece p2 = new Piece(Tetromino.Line, 2, b);
        b.addPiece(p2);

        assertFalse(b.moveActive());
    }

    @Test
    public void tickSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.L, 1, b);
        b.addPiece(p);

        b.tick();
    }

    @Test
    public void resetSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.L, 1, b);
        b.addPiece(p);

        b.reset();
        int[][] matrix = new int[24][12];

        // Set bottom row as -1
        for (int i = 0; i < matrix[matrix.length - 1].length; i++) {
            matrix[matrix.length - 1][i] = -1;
        }

        // Set left and right columns as -1
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = -1;
            matrix[i][matrix[i].length - 1] = -1;
        }

        for (int i = 0; i < matrix.length; i++) {
            assertArrayEquals(matrix[i], b.getMatrix()[i]);
        }
    }

    @Test
    public void dropSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);
        b.addPiece(p);
        b.drop();

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        int boardHeight = b.getMatrix().length;
        coordinates.add(new int[] {boardMiddle - 2, boardHeight - 2});
        coordinates.add(new int[] {boardMiddle - 1, boardHeight - 2});
        coordinates.add(new int[] {boardMiddle, boardHeight - 2});
        coordinates.add(new int[] {boardMiddle + 1, boardHeight - 2});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void dropNull() {
        Board b = new Board();
        b.drop();
    }

    @Test
    public void inputDrop() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);
        b.addPiece(p);
        b.input(InputType.Pause);
        b.input(InputType.Down);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        int boardHeight = b.getMatrix().length;
        coordinates.add(new int[] {boardMiddle - 2, boardHeight - 2});
        coordinates.add(new int[] {boardMiddle - 1, boardHeight - 2});
        coordinates.add(new int[] {boardMiddle, boardHeight - 2});
        coordinates.add(new int[] {boardMiddle + 1, boardHeight - 2});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void inputPause() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);
        b.addPiece(p);
        b.input(InputType.Pause);
        b.input(InputType.Pause);
        b.input(InputType.Down);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        int boardHeight = b.getMatrix().length;
        coordinates.add(new int[] {boardMiddle - 2, 3});
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle + 1, 3});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void inputNull() {
        Board b = new Board();
        b.input(InputType.Pause);
        b.input(InputType.Pause);
        b.input(InputType.Down);
    }
}
