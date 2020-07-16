package buildings.threads;

import buildings.interfaces.Floor;

public class SequentalCleaner implements Runnable {
    private Floor floor;
    private Semaphore semaphore;

    public SequentalCleaner(Floor floor, Semaphore semaphore) {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int index = 0;
        int count = floor.getSpacesCount();
        while (!Thread.currentThread().isInterrupted() && index < count) {
            semaphore.minus(index, floor.getSpace(index));
            index++;
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("the thread \"Cleaner\" has interrupted");
        } else if (count < floor.getSpacesCount()) {
            System.out.println("the thread \"Cleaner\" has finished");
        }
    }
}
