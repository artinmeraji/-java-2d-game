// Animation.java
package first_game;

import java.awt.image.BufferedImage;

/**
 * Encapsulates a looping sprite‐sheet animation.
 */
public class Animation {
    private BufferedImage[] frames;   // all frames in the cycle
    private int currentFrame;         // which frame we’re on
    private long lastTime;            // when we last switched frames
    private long delay;               // nanoseconds between frames

    /**
     * @param delayNano how long to wait between frames (in ns)
     * @param frames    your sliced‐out BufferedImage frames
     */
    public Animation(long delayNano, BufferedImage... frames) {
        this.frames       = frames;
        this.delay        = delayNano;
        this.currentFrame = 0;
        this.lastTime     = System.nanoTime();
    }

    /** Advance to the next frame if enough time has passed */
    public void update() {
        long now = System.nanoTime();
        if (now - lastTime > delay) {
            currentFrame = (currentFrame + 1) % frames.length;
            lastTime     = now;
        }
    }

    /** @return the image for the current frame */
    public BufferedImage getFrame() {
        return frames[currentFrame];
    }
}

