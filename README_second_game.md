# Java 2D Game — Engine Foundation

A second Java game project focused on building a clean, tile-based game engine architecture from scratch using Java Swing.

## Features

- **Tile-based rendering** — 16x16 tiles scaled 3x (48x48px) on a 768x576 screen
- **Game loop on a thread** — `Runnable`-based game loop with `Thread` management
- **Double buffering** — smooth rendering via Swing's built-in double buffer
- **Scalable screen system** — screen size driven by tile count, easy to resize

## Classes

| Class | Role |
|---|---|
| `GamePanel` | Core rendering panel, game loop, screen settings |
| `MainClass` | Entry point, creates the JFrame and starts the panel |

## How to Run

Open in Eclipse, right-click `MainClass.java` → Run As → Java Application.

## Skills Demonstrated

- Tile-based game engine design
- Thread-based game loop (`Runnable`)
- Java Swing rendering with double buffering
- Separation of concerns (entry point vs. game panel)
