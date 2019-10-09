package andrew;

public class Game {

    private Board board;

    public Game() {
        this.board = new Board();
        this.board.addPiece(new Piece(Tetromino.Line, 1, this.board));
    }




    public void run() {

        boolean play = true;
        while (play) {
            play = this.board.tick();
        }
    }
}