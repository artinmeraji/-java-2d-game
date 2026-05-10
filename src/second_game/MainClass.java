// MainClass.java
package game;

import javax.swing.JFrame;

public class MainClass {
    public static void main(String[] args) {
        // 1) Create the main application window
        JFrame window = new JFrame();

        // 2) Exit the program when the window’s close button is clicked
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 3) Prevent the user from resizing the window
        window.setResizable(false);

        // 4) Set the title that appears in the title bar
        window.setTitle("2D Adventure");

        // 5) Create and configure our custom drawing panel
        GamePanel gamePanel = new GamePanel();
        //    GamePanel sets its own preferred size, used by pack()

        // 6) Add the game panel into the window’s content area
        window.add(gamePanel);

        // 7) Size the window to fit the panel’s preferred size
        window.pack();

        // 8) Center the window on the user’s screen
        window.setLocationRelativeTo(null);

        // 9) Make the window visible, starting the game loop inside GamePanel
        window.setVisible(true);

        // 10) Kick off the game loop thread (matches MainClass’s call)
        gamePanel.StartGameTHread();
    }
}

