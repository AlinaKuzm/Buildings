package buildings.threads;

import buildings.interfaces.Floor;

public class Cleaner extends Thread {
    private Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        super.run();
        int count = floor.getSpacesCount();
        for (int i = 0; i < count; i++) {
            System.out.println(String.format("Cleaning room number %d with total area %.1f square metres", i, floor.getSpace(i).getSquare()));
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("the thread \"Cleaner\" has interrupted");
        } else if (count < floor.getSpacesCount()) {
            System.out.println("the thread \"Cleaner\" has finished");
        }
    }
}
