package buildings.threads;

import buildings.interfaces.Space;

public class Semaphore {
    private int counter;
    //0 - ремонт, 1 - уборка

    public Semaphore() {
    }

    synchronized public void minus(int index, Space space) {

        while (counter != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Repairing space number %d with total area %.1f square metres", index, space.getSquare()));
        }
        counter--;
        notify();
    }
    synchronized public void plus(int index, Space space) {

        while (counter != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Cleaning space number %d with total area %.1f square metres", index, space.getSquare()));
        }
        counter++;
        notify();
    }
}
