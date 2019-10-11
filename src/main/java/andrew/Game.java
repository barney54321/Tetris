package andrew;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L; // This is required
	public static final int WIDTH = 300, HEIGHT = 500; // Sets up window width and height

	private Thread thread;

	private boolean running = false; // boolean to check if game is running
    private Board board;

	public Game() {

		new Window(WIDTH, HEIGHT, "Tetris", this);

        this.board = new Board();
        this.board.addPiece(new Piece(Tetromino.Line, 1, this.board));
        this.addKeyListener(new KeyInput(this.board));
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 4.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			ns = 1000000000 / amountOfTicks;
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
			frames++;

			amountOfTicks = 4 + this.board.getScore() / 200.0;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
        this.board.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

        this.board.render(g);

		g.dispose();
		bs.show();
	}

}