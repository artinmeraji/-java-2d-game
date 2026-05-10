package first_game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Images extends Canvas {

    // BufferedImage stores the actual pixel data of the image.
    private BufferedImage throneRoom;

    // ‘loaded’ indicates whether the image was successfully loaded.
    private boolean loaded;

    // Screan helper to toggle fullscreen mode and restore screen settings.
    private Screan s;

    public static void main(String[] args) {
        // Retrieve the current display mode so we don't request an unsupported resolution
        DisplayMode dm = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDisplayMode();

        Images game = new Images();

        // Print working directory to verify where Java is looking for files
        System.out.println("Working dir: " + new File(".").getAbsolutePath());

        // Set up the window (JFrame) and add our custom Canvas to it
        JFrame window = new JFrame("Throne Room Display");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(game);

        // Create the fullscreen controller
        Screan s = new Screan();

        // Start the game logic: load image, go fullscreen, draw, then restore
        game.run(dm, s, window);
    }

    public void run(DisplayMode dm, Screan s, JFrame window) {
        // Set default background/foreground and font for any text we might draw
        setBackground(Color.GREEN);
        setForeground(Color.BLACK);
        setFont(new Font("Arial", Font.PLAIN, 24));
        this.s = s;

        try {
            // Load the image from disk before entering fullscreen
            loadImage();

            // Enter fullscreen mode (must do this before making the window visible)
            s.setFullScreen(dm, window);
            window.setVisible(true);

            // Request a repaint so paint() gets called immediately
            repaint();

            // Keep the image on screen for 5 seconds
            Thread.sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // Always restore the screen back to normal, even if an error occurs
            s.restoreScreen();
        }
    }

    public void loadImage() {
        try {
            // Construct a File for the image in the project root directory
            File imgFile = new File("src/first_game/throne_room.png");
            System.out.println("Loading image from: " + imgFile.getAbsolutePath());

            // Read the image synchronously; throws if the file is missing or unreadable
            throneRoom = ImageIO.read(imgFile);

            // Mark as loaded so paint() will draw it
            loaded = true;
            System.out.println("✅ Throne room image loaded.");
        } catch (Exception e) {
            System.out.println("❌ Failed to load throne room image:");
            e.printStackTrace();
            // Keep loaded = false so paint() shows a fallback message
            loaded = false;
        }
    }

    @Override
    public void paint(Graphics g) {
        // If we get a Graphics2D, enable text anti-aliasing for smoother fonts
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
        }

        if (loaded) {
            // Draw the image once, scaled to the full size of the canvas
            g.drawImage(throneRoom, 0, 0, getWidth(), getHeight(), this);
        } else {
            // If loading failed, display a simple loading/fallback message
            g.setColor(Color.BLACK);
            g.drawString("Loading image...", 100, 100);
        }
    }
}


