package first_game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;

public class bucky extends Canvas {

    public static void main(String[] args) {
        // Define desired screen mode: width, height, bit depth, refresh rate
        DisplayMode dm = new DisplayMode(880, 600, 32, DisplayMode.REFRESH_RATE_UNKNOWN);

        // Create game canvas (this class)
        bucky b = new bucky();

        // Create a JFrame to hold the canvas
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(b);

        // Create custom screen handler
        Screan s = new Screan();

        // Run the fullscreen setup and game logic
        b.run(dm, s, window);
    }

    public void run(DisplayMode dm, Screan s, JFrame window) {
        setBackground(Color.GREEN);
        setForeground(Color.BLACK);
        setFont(new Font("Arial", Font.PLAIN, 24));

        try {
            s.setFullScreen(dm, window); // Make window fullscreen
            Thread.sleep(50000);          // Wait for 5 seconds (simulate gameplay)
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            s.restoreScreen();           // Restore screen after done
        }
    }
// make the data smoother 
    @Override
    // this is the grpahics 2d   this is the graohics 2d object 
    public void paint(Graphics g) {
    	if (g instanceof Graphics2D) {// if it in the class  
    		 Graphics2D g2  = (Graphics2D)g; 
    		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	}
        g.drawString("I told my date I'm a Java programmer... she left. Probably compiling her feelings.", 200, 200);
    }
}
