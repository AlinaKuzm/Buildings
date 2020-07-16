package buildings.threads;

import buildings.interfaces.*;

public class Repairer extends Thread {
    private Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        super.run();
        int count = floor.getSpacesCount();
        for (int i = 0; i < count; i++) {
            System.out.println(String.format("Repairing space number %d with total area %.1f square metres", i, floor.getSpace(i).getSquare()));
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("the thread \"Repairer\" has interrupted");
        } else if (count < floor.getSpacesCount()) {
            System.out.println("the thread \"Repairer\" has finished");
        }


    }
}
