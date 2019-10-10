package andrew;

import java.util.ArrayList;
import java.awt.Color;

public class Piece {

    private Board board;
    private int id;
    private ArrayList<int[]> coordinates;
    private Color color;
    private Direction orientation;
    private Tetromino shape;

    public Piece(ArrayList<int[]> coordinates, int id, Board board) {
        this.coordinates = coordinates;
        this.id = id;
        this.board = board;
        this.color = Color.green;
        this.orientation = Direction.North;
        this.shape = Tetromino.None;
    }

    public Piece(Tetromino shape, int id, Board board) {
        this.id = id;
        this.board = board;
        this.coordinates = new ArrayList<int[]>();
        this.orientation = Direction.North;

        int boardMiddle = this.board.getMatrix()[0].length / 2;

        if (shape == Tetromino.Line) {

            // Horizontal line
            this.coordinates.add(new int[] {boardMiddle - 2, 3});
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            this.coordinates.add(new int[] {boardMiddle, 3});
            this.coordinates.add(new int[] {boardMiddle + 1, 3});
            this.color = Color.cyan;
            this.shape = Tetromino.Line;
            this.orientation = Direction.East; // I mucked up

        } else if (shape == Tetromino.L) {

            // Long part horizontal, nub pointing up
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            this.coordinates.add(new int[] {boardMiddle, 3});
            this.coordinates.add(new int[] {boardMiddle + 1, 3});
            this.coordinates.add(new int[] {boardMiddle + 1, 2});
            this.color = Color.orange;
            this.shape = Tetromino.L;

        } else if (shape == Tetromino.MirroredL) {

            // Long part horizontal, nub pointing up
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            this.coordinates.add(new int[] {boardMiddle, 3});
            this.coordinates.add(new int[] {boardMiddle + 1, 3});
            this.coordinates.add(new int[] {boardMiddle - 1, 2});
            this.color = Color.blue;
            this.shape = Tetromino.MirroredL;

        } else if (shape == Tetromino.Square) {

            // It's a square
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            this.coordinates.add(new int[] {boardMiddle - 1, 2});
            this.coordinates.add(new int[] {boardMiddle, 3});
            this.coordinates.add(new int[] {boardMiddle, 2});
            this.color = Color.yellow;
            this.shape = Tetromino.Square;
            
        } else if (shape == Tetromino.S) {

            // Lying down
            this.coordinates.add(new int[] {boardMiddle - 1, 3});
            this.coordinates.add(new int[] {boardMiddle, 3});
            this.coordinates.add(new int[] {boardMiddle, 2});
            this.coordinates.add(new int[] {boardMiddle + 1, 2});
            this.color = Color.green;
            this.shape = Tetromino.S;
            
        } 
    }

    public Color getColor() {
        return this.color;
    }

    public ArrayList<int[]> getCoordinates() {
        return this.coordinates;
    }

    public int getId() {
        return this.id;
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

    public void moveLeft() {
        for (int[] co : this.coordinates) {
            co[0]--;
        }
    }

    public void moveRight() {
        for (int[] co : this.coordinates) {
            co[0]++;
        }
    }

    public boolean canRotateClockwise() {
        boolean canMove = true;

        int[][] matrix = this.board.getMatrix();

        for (int[] co : this.computeRotationClockwise()) {
            if (matrix[co[1]][co[0] + 1] != 0 && matrix[co[1]][co[0] + 1] != this.id) {
                canMove = false;
            }
        }

        return canMove;
    }

    public boolean canRotateAntiClockwise() {
        boolean canMove = true;

        int[][] matrix = this.board.getMatrix();

        for (int[] co : this.computeRotationAntiClockwise()) {
            if (matrix[co[1]][co[0] + 1] != 0 && matrix[co[1]][co[0] + 1] != this.id) {
                canMove = false;
            }
        }

        return canMove;
    }

    public void rotateClockwise() {

        this.coordinates = this.computeRotationClockwise();

        if (this.orientation == Direction.North) {
            this.orientation = Direction.East;
        } else if (this.orientation == Direction.East) {
            this.orientation = Direction.South;
        } else if (this.orientation == Direction.South) {
            this.orientation = Direction.West;
        } else if (this.orientation == Direction.West) {
            this.orientation = Direction.North;
        } 
        
    }

    public void rotateAntiClockwise() {
        
        this.coordinates = this.computeRotationAntiClockwise();

        if (this.orientation == Direction.North) {
            this.orientation = Direction.West;
        } else if (this.orientation == Direction.East) {
            this.orientation = Direction.North;
        } else if (this.orientation == Direction.South) {
            this.orientation = Direction.East;
        } else if (this.orientation == Direction.West) {
            this.orientation = Direction.South;
        } 

    }

    private ArrayList<int[]> computeRotationClockwise() {

        ArrayList<int[]> res = new ArrayList<int[]>();

        // Figure out the shape
        if (this.shape == Tetromino.None) {

            res = this.coordinates;

        } else if (this.shape == Tetromino.Line) {

            // Third element
            int[] third = this.coordinates.get(2);

            // Figure out the direction
            if (this.orientation == Direction.North || this.orientation == Direction.South) {

                res.add(new int[] {third[0] - 2, third[1]});
                res.add(new int[] {third[0] - 1, third[1]});
                res.add(new int[] {third[0], third[1]});
                res.add(new int[] {third[0] + 1, third[1]});

            } else {
                
                res.add(new int[] {third[0], third[1] - 2});
                res.add(new int[] {third[0], third[1] - 1});
                res.add(new int[] {third[0], third[1]});
                res.add(new int[] {third[0], third[1] + 1});

            }

        } else if (this.shape == Tetromino.L) {

            // Second element
            int[] second = this.coordinates.get(1);

            // Figure out the direction
            if (this.orientation == Direction.North) {

                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] + 1});
                res.add(new int[] {second[0] + 1, second[1] + 1});

            } else if (this.orientation == Direction.East) {

                res.add(new int[] {second[0] - 1, second[1] + 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1]});

            } else if (this.orientation == Direction.South) {

                res.add(new int[] {second[0] - 1, second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1] + 1});

            } else {

                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] + 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1] - 1});

            }

        } else if (this.shape == Tetromino.MirroredL) {

            // Second element
            int[] second = this.coordinates.get(1);

            // Figure out the direction
            if (this.orientation == Direction.North) {

                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] + 1});
                res.add(new int[] {second[0] + 1, second[1] - 1});

            } else if (this.orientation == Direction.East) {

                res.add(new int[] {second[0] + 1, second[1] + 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1]});

            } else if (this.orientation == Direction.South) {

                res.add(new int[] {second[0] - 1, second[1] + 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1] + 1});

            } else {

                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] + 1, second[1]});
                res.add(new int[] {second[0] - 1, second[1] - 1});

            }

        } else if (this.shape == Tetromino.Square) {

            res = this.coordinates;

        } else if (this.shape == Tetromino.S) {

            // Second element
            int[] second = this.coordinates.get(1);

            // Figure out the direction
            if (this.orientation == Direction.North || this.orientation == Direction.South) {

                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] + 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1] + 1});

            } else {
                
                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0] + 1, second[1] - 1});

            }

        } 

        return res;
    }

    private ArrayList<int[]> computeRotationAntiClockwise() {
        
        ArrayList<int[]> res = new ArrayList<int[]>();

        // Figure out the shape
        if (this.shape == Tetromino.None) {

            res = this.coordinates;

        } else if (this.shape == Tetromino.Line) {

            // Third element
            int[] third = this.coordinates.get(2);

            // Figure out the direction
            if (this.orientation == Direction.North || this.orientation == Direction.South) {

                res.add(new int[] {third[0] - 2, third[1]});
                res.add(new int[] {third[0] - 1, third[1]});
                res.add(new int[] {third[0], third[1]});
                res.add(new int[] {third[0] + 1, third[1]});

            } else {
                
                res.add(new int[] {third[0], third[1] - 2});
                res.add(new int[] {third[0], third[1] - 1});
                res.add(new int[] {third[0], third[1]});
                res.add(new int[] {third[0], third[1] + 1});

            }

        } else if (this.shape == Tetromino.L) {

            // Second element
            int[] second = this.coordinates.get(1);

            // Figure out the direction
            if (this.orientation == Direction.North) {

                res.add(new int[] {second[0] - 1, second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1] + 1});

            } else if (this.orientation == Direction.East) {

                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] + 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1] - 1});

            } else if (this.orientation == Direction.South) {

                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] + 1});
                res.add(new int[] {second[0] + 1, second[1] + 1});

            } else {

                res.add(new int[] {second[0] - 1, second[1] + 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1]});

            }

        } else if (this.shape == Tetromino.MirroredL) {

            // Second element
            int[] second = this.coordinates.get(1);

            // Figure out the direction
            if (this.orientation == Direction.North) {

                res.add(new int[] {second[0] - 1, second[1] + 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1] + 1});

            } else if (this.orientation == Direction.East) {

                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] + 1, second[1]});
                res.add(new int[] {second[0] - 1, second[1] - 1});

            } else if (this.orientation == Direction.South) {

                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] + 1});
                res.add(new int[] {second[0] + 1, second[1] - 1});

            } else {

                res.add(new int[] {second[0] + 1, second[1] + 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1]});

            }

        } else if (this.shape == Tetromino.Square) {

            res = this.coordinates;

        } else if (this.shape == Tetromino.S) {

            // Second element
            int[] second = this.coordinates.get(1);

            // Figure out the direction
            if (this.orientation == Direction.North || this.orientation == Direction.South) {

                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0] + 1, second[1]});
                res.add(new int[] {second[0] + 1, second[1] + 1});

            } else {
                
                res.add(new int[] {second[0] - 1, second[1]});
                res.add(new int[] {second[0], second[1]});
                res.add(new int[] {second[0], second[1] - 1});
                res.add(new int[] {second[0] + 1, second[1] - 1});

            }

        } 

        return res;
    }
}