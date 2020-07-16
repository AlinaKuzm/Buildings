package buildings.threads;

import buildings.interfaces.Floor;

public class SequentalRepairer implements Runnable {
    private Floor floor;
    private Semaphore semaphore;

public SequentalRepairer(Floor floor, Semaphore semaphore) {
    this.floor = floor;
    this.semaphore = semaphore;
}

    @Override
    public void run() {
        int index = 0;
        int count = floor.getSpacesCount();
        while (!Thread.currentThread().isInterrupted() && index < count) {
        semaphore.plus(index, floor.getSpace(index));
        index++;
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("the thread \"Repairer\" has interrupted");
        } else if (count < floor.getSpacesCount()) {
            System.out.println("the thread \"Repairer\" has finished");
        }


    }
}
