/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package andrew;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.awt.Color;

public class PieceTest {

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
    public void createCustom() {
        Board b = new Board();
        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        coordinates.add(new int[] {10, 29});
        coordinates.add(new int[] {2, 29});
        coordinates.add(new int[] {194, 29});
        Piece p = new Piece(coordinates, 1, b);

        assertEquals(coordinates, p.getCoordinates());
    }

    @Test
    public void createLine() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 2, 3});
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle + 1, 3});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void createL() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.L, 1, b);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle + 1, 3});
        coordinates.add(new int[] {boardMiddle + 1, 2});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void createMirroredL() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.MirroredL, 1, b);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle + 1, 3});
        coordinates.add(new int[] {boardMiddle - 1, 2});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void createSquare() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Square, 1, b);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle - 1, 2});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle, 2});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void createS() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.S, 1, b);

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 1, 3});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle, 2});
        coordinates.add(new int[] {boardMiddle + 1, 2});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }
    
    @Test
    public void canMoveDownSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);

        b.addPiece(p);

        assertTrue(p.canMoveDown());
    }

    @Test
    public void canMoveDownFail() {
        Board b = new Board();
        
        Piece p1 = new Piece(Tetromino.Line, 1, b);
        b.addPiece(p1);
        assertTrue(p1.canMoveDown());
        assertTrue(b.moveActive());

        Piece p2 = new Piece(Tetromino.Line, 2, b);
        assertTrue(b.addPiece(p2));
        assertFalse(p2.canMoveDown());

    }

    @Test
    public void canMoveLeftSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);

        b.addPiece(p);

        assertTrue(p.canMoveLeft());
    }

    @Test
    public void canMoveRightSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);

        b.addPiece(p);

        assertTrue(p.canMoveRight());
    }

    @Test
    public void moveDownSimple() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.S, 1, b);
        p.moveDown();

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 1, 4});
        coordinates.add(new int[] {boardMiddle, 4});
        coordinates.add(new int[] {boardMiddle, 3});
        coordinates.add(new int[] {boardMiddle + 1, 3});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void moveDownSimpleLine() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);
        p.moveDown();

        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int boardMiddle = b.getMatrix()[0].length / 2;
        coordinates.add(new int[] {boardMiddle - 2, 4});
        coordinates.add(new int[] {boardMiddle - 1, 4});
        coordinates.add(new int[] {boardMiddle, 4});
        coordinates.add(new int[] {boardMiddle + 1, 4});

        assertTrue(checkLists(coordinates, p.getCoordinates()));
    }

    @Test
    public void colourLine() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Line, 1, b);
        assertEquals(Color.cyan, p.getColor());
    }

    @Test
    public void colourL() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.L, 1, b);
        assertEquals(Color.orange, p.getColor());
    }

    @Test
    public void colourMirroredL() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.MirroredL, 1, b);
        assertEquals(Color.blue, p.getColor());
    }

    @Test
    public void colourSquare() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.Square, 1, b);
        assertEquals(Color.yellow, p.getColor());
    }

    @Test
    public void colourS() {
        Board b = new Board();
        Piece p = new Piece(Tetromino.S, 1, b);
        assertEquals(Color.green, p.getColor());
    }
}
