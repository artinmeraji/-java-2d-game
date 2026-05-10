package first_game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Side-Scroller Demo
 * • Fullscreen background
 * • Scaled & animated hero sprite
 * • Rectangular obstacles
 * • Patrolling enemy loaded from character-o.png
 * • Finish line with “Level Complete” or “Game Over”
 */
public class Game extends Canvas implements KeyListener {
    // ─── Resources & Animations ─────────────────────────────────────────────
    private BufferedImage background;    // level backdrop
    private Animation   runAnim;         // hero’s run cycle

    // ─── Hero Scaling & Physics ─────────────────────────────────────────────
    private static final int SCALE = 3;  // enlarge hero frames 3×
    private int x = 200, y;              // hero world coords
    private int groundY;                 // y of ground line
    private int velY = 0;                // vertical velocity
    private final int GRAVITY = 1;       // gravity accel
    private boolean jumping = false;     // true while in air

    // ─── Input Flags ─────────────────────────────────────────────────────────
    private boolean leftPressed, rightPressed, jumpPressed;

    // ─── Obstacles & Enemy ───────────────────────────────────────────────────
    private Rectangle[] obstacles;       // simple hurdles
    private Enemy     enemy;             // patrolling foe

    // ─── Finish Line ─────────────────────────────────────────────────────────
    private int finishX = 2500;          // world-x to finish

    // ─── Camera & Control ────────────────────────────────────────────────────
    private int    cameraX = 0;          // viewport left edge
    private Screan s;                    // fullscreen helper
    private boolean running = true;      // loop control

    public static void main(String[] args) throws Exception {
        // 1) get safe fullscreen resolution
        DisplayMode dm = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDisplayMode();

        // 2) setup canvas & window
        Game game = new Game();
        System.out.println("Working dir: " + new File(".").getAbsolutePath());
        JFrame window = new JFrame("Side-Scroller Demo");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(game);

        // 3) fullscreen helper
        game.s = new Screan();

        // 4) start game
        game.start(dm, window);
    }

    /** Loads assets, goes fullscreen, hooks input, then loops. */
    public void start(DisplayMode dm, JFrame window) {
        try {
            loadResources();            // may throw if files missing
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        addKeyListener(this);
        setBackground(Color.BLACK);

        // enter fullscreen (before showing window)
        s.setFullScreen(dm, window);
        window.setVisible(true);

        // compute ground line once canvas is sized
        groundY = getHeight() - runAnim.getFrame().getHeight();
        y       = groundY;

        // setup static obstacles on ground
        obstacles = new Rectangle[] {
            new Rectangle(600,  groundY - 50, 50, 50),
            new Rectangle(1200, groundY - 75, 75, 75),
            new Rectangle(1800, groundY - 60, 60, 60)
        };

        gameLoop();                   // begin update→repaint loop
    }

    /**
     * Loads:
     * 1) background image from src/first_game/throne_room.png
     * 2) hero sprite‐sheet from src/first_game/character-a.png
     * 3) enemy sprite from src/first_game/character-o.png
     */
    private void loadResources() throws Exception {
        // — background
        File bgFile = new File("src/first_game/throne_room.png");
        System.out.println("BG exists? " + bgFile.exists());
        background = ImageIO.read(bgFile);

        // — hero sheet
        File heroFile = new File("src/first_game/character-a.png");
        System.out.println("Hero exists? " + heroFile.exists());
        BufferedImage sheet = ImageIO.read(heroFile);
        int total = 4, fw = sheet.getWidth() / total, fh = sheet.getHeight();
        BufferedImage[] frames = new BufferedImage[total];
        for (int i = 0; i < total; i++) {
            BufferedImage f = sheet.getSubimage(i * fw, 0, fw, fh);
            BufferedImage big = new BufferedImage(fw * SCALE, fh * SCALE,
                                                  BufferedImage.TYPE_INT_ARGB);
            big.getGraphics().drawImage(f, 0, 0, fw * SCALE, fh * SCALE, null);
            frames[i] = big;
        }
        runAnim = new Animation(100_000_000L, frames);

        // — enemy sprite
        File eFile = new File("src/first_game/character-o.png");
        System.out.println("Enemy exists? " + eFile.exists());
        BufferedImage eSprite = ImageIO.read(eFile);
        int ex = 1000, ey = groundY - eSprite.getHeight();
        enemy = new Enemy(eSprite, ex, ey, 2, 1000, 1400);
    }

    /** Main loop: update state, repaint, delay ~16ms (≈60FPS). */
    private void gameLoop() {
        new Thread(() -> {
            while (running) {
                updateState();
                repaint();
                try { Thread.sleep(16); } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    /** Updates hero/jump, collisions, animation, finish, and camera. */
    private void updateState() {
        // horizontal
        if (leftPressed)  x -= 4;
        if (rightPressed) x += 4;

        // jump
        if (jumpPressed && !jumping) {
            jumping = true; velY = -15;
        }

        // gravity
        if (jumping) {
            y += velY; velY += GRAVITY;
            if (y >= groundY) { y = groundY; jumping = false; velY = 0; }
        }

        // obstacle collisions
        if (!jumping) {
            Rectangle hero = new Rectangle(x, y,
                runAnim.getFrame().getWidth(),
                runAnim.getFrame().getHeight());
            for (Rectangle o : obstacles) {
                if (hero.intersects(o)) {
                    x = o.x - hero.width;  // push left
                }
            }
        }

        // enemy update & collision
        enemy.update();
        Rectangle heroRect = new Rectangle(x, y,
            runAnim.getFrame().getWidth(),
            runAnim.getFrame().getHeight());
        if (heroRect.intersects(enemy.getBounds())) {
            running = false;
            System.out.println("💥 Slain by enemy! Game Over.");
        }

        // animate
        if (leftPressed || rightPressed) runAnim.update();

        // finish line
        if (x >= finishX) {
            running = false;
            System.out.println("✅ Level Complete! Well done.");
        }

        // camera clamp
        int sw = getWidth(), bgw = background.getWidth();
        cameraX = x - sw / 2;
        if (cameraX < 0)        cameraX = 0;
        if (cameraX > bgw - sw) cameraX = bgw - sw;
    }

    /** Draws background slice, obstacles, enemy, hero, and end text. */
    @Override
    public void paint(Graphics g) {
        // background
        g.drawImage(background,
            0, 0, getWidth(), getHeight(),
            cameraX, 0, cameraX + getWidth(), getHeight(), this);

        // obstacles
        g.setColor(Color.DARK_GRAY);
        for (Rectangle o : obstacles) {
            g.fillRect(o.x - cameraX, o.y, o.width, o.height);
        }

        // enemy
        enemy.draw(g, cameraX);

        // hero
        g.drawImage(runAnim.getFrame(), x - cameraX, y, this);

        // end message
        if (!running) {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(48f));
            String msg = (x >= finishX)
                ? "✅ Level Complete! Well done."
                : "💀 Game Over";
            g.drawString(msg, getWidth() / 3, getHeight() / 2);
        }
    }

    // ─── KeyListener ───────────────────────────────────────────

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  leftPressed  = true;  break;
            case KeyEvent.VK_RIGHT: rightPressed = true;  break;
            case KeyEvent.VK_SPACE: jumpPressed  = true;  break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  leftPressed  = false; break;
            case KeyEvent.VK_RIGHT: rightPressed = false; break;
            case KeyEvent.VK_SPACE: jumpPressed  = false; break;
        }
    }
    @Override public void keyTyped(KeyEvent e) { /* unused */ }
}
