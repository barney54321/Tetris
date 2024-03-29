package andrew;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Board board;

	public KeyInput(Board board) {
        this.board = board;
	}

	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            this.board.input(InputType.Left);
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            this.board.input(InputType.Right);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            this.board.input(InputType.RotateRight);
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            this.board.input(InputType.RotateLeft);
        } else if (key == KeyEvent.VK_SPACE) {
            this.board.input(InputType.Down);
        } else if (key == KeyEvent.VK_P) {
            this.board.input(InputType.Pause);
        } else if (key == KeyEvent.VK_CONTROL) {
            this.board.input(InputType.Store);
        } 

	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

	}
}
