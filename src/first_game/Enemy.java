package first_game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * A patrolling enemy that uses a sprite image for drawing.
 */
public class Enemy {
    private BufferedImage sprite;  // the enemy image
    private int x, y, width, height;
    private int speed, dir = 1;
    private int minX, maxX;

    /**
     * @param sprite  Loaded BufferedImage for the enemy
     * @param x       Starting x-coordinate
     * @param y       Y-coordinate (ground level minus height)
     * @param speed   Pixels to move each update
     * @param minX    Left bound of patrol
     * @param maxX    Right bound of patrol
     */
    public Enemy(BufferedImage sprite, int x, int y, int speed, int minX, int maxX) {
        this.sprite = sprite;
        this.width  = sprite.getWidth();
        this.height = sprite.getHeight();
        this.x      = x;
        this.y      = y;
        this.speed  = speed;
        this.minX   = minX;
        this.maxX   = maxX;
    }

    /** Move back and forth between minX and maxX. */
    public void update() {
        x += speed * dir;
        if (x < minX) {
            x   = minX;
            dir = 1;
        } else if (x + width > maxX) {
            x   = maxX - width;
            dir = -1;
        }
    }

    /** Draw the enemy sprite at its world position minus the camera offset. */
    public void draw(Graphics g, int cameraX) {
        g.drawImage(sprite, x - cameraX, y, null);
    }

    /** Collision bounds in world coordinates. */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

