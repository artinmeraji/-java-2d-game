package first_game;

import java.util.Random;

public class Apple implements Runnable {
    String name;
    int time;
    Random r = new Random();

    // Constructor to set name and random sleep time
    public Apple(String name) {
        this.name = name;
        this.time = r.nextInt(999);  // Sleep time up to 998ms
    }

    // The run() method defines what each thread does
    public void run() {
        try {
            System.out.printf("%s is sleeping for %d ms...\n", name, time);
            Thread.sleep(time); // Sleep for the random time
            System.out.printf("%s is done!\n", name);
        } catch (Exception e) {
            System.out.println(name + " was interrupted.");
        }
    }
}

