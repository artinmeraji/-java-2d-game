// GamePanel.java
package game;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

/**
 * GamePanel is our custom drawing surface.
 * It sets its preferred size, starts the game loop thread,
 * and then continuously calls update() and repaint() to drive the game.
 */
public class GamePanel extends JPanel implements Runnable {

    // ─── SCREEN SETTINGS ─────────────────────────────────────────────────────
    private final int originalTileSize = 16;           // 16x16 tile
    private final int scale            = 3;            // 3x scale
    private final int tileSize         = originalTileSize * scale; // 48x48
    private final int maxScreenCol     = 16;           // 16 tiles wide
    private final int maxScreenRow     = 12;           // 12 tiles tall
    private final int screenWidth      = tileSize * maxScreenCol;  // 768 px
    private final int screenHeight     = tileSize * maxScreenRow;  // 576 px

    // ─── GAME LOOP ────────────────────────────────────────────────────────────
    private Thread gameThread;   // the thread in which run() will execute

    public GamePanel() {
        // tell the parent JFrame how big we want to be
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        // set the background color
        this.setBackground(Color.BLACK);
        // enable double buffering for smoother rendering
        this.setDoubleBuffered(true);
        // start the game loop
        startGameThread();
    }

    /** Creates and starts the game loop thread. */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Alias matching MainClass’s call signature.
     * Simply delegates to startGameThread().
     */
    public void StartGameTHread() {
        startGameThread();
    }

    /**
     * The core game loop.
     * As long as gameThread is non-null, we:
     *   1) Update game state (e.g., character position)
     *   2) Repaint the screen with the updated state
     */
    @Override
    public void run() {
        while (gameThread != null) {
            // Update: update game information (e.g., character position)
            update();
            // Draw: redraw the screen with updated information
            repaint();
        }
        // When gameThread is set to null, the loop ends and thread exits
    }

    /** Update game logic here (called once per loop iteration). */
    public void update() {
        // TODO: implement your update logic (movement, collisions, etc.)
    }

    /**
     * paintComponent is the built-in Swing method for drawing.
     * We override it to render our game objects each frame.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // clears the previous frame

        // Example draw call:
        // set color of our “brush”
        g.setColor(Color.WHITE);
        // draw a filled rectangle at (100,100) with size tileSize x tileSize
        g.fillRect(100, 100, tileSize, tileSize);

        // TODO: replace with your actual rendering (background, sprites, UI, etc.)
    }
}


