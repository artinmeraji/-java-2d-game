# Java 2D Side-Scroller Game

A 2D side-scrolling game built from scratch in Java using AWT/Swing — no game engine.

## Features

- **Game loop** — fixed-timestep loop running on a dedicated thread
- **Sprite animation** — frame-by-frame character animation loaded from image sheets
- **Physics** — gravity, jumping, and vertical velocity simulation
- **Scrolling camera** — viewport follows the player through the level
- **Enemy AI** — patrolling enemy that moves back and forth
- **Collision detection** — rectangle-based collision with obstacles and enemy
- **Fullscreen rendering** — uses `GraphicsEnvironment` for fullscreen display
- **Finish line** — level complete / game over conditions
- **Assets** — custom sprite images and background loaded via `ImageIO`

## Classes

| Class | Role |
|---|---|
| `Game` | Main game class — loop, physics, input, rendering |
| `Animation` | Manages sprite frame cycling |
| `Enemy` | Patrolling enemy logic |
| `Images` | Loads and stores image assets |
| `Apple` | Collectible item |
| `bucky` | Player character |
| `Screan` | Fullscreen display helper |

## How to Run

Open in Eclipse, right-click `Game.java` → Run As → Java Application.

## Skills Demonstrated

- Java AWT/Swing rendering
- Game loop architecture
- Sprite animation system
- Basic 2D physics (gravity, velocity)
- Keyboard input handling (`KeyListener`)
- Object-oriented design with multiple interacting classes
