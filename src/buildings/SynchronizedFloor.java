package buildings;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.interfaces.Floor;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;

    }

    @Override
    public synchronized int getSpacesCount() {
        return floor.getSpacesCount();
    }

    @Override
    public synchronized double getSpacesSquare() {
        return floor.getSpacesSquare();
    }

    @Override
    public synchronized int getRoomCount() {
        return floor.getRoomCount();
    }

    @Override
    public synchronized Space[] getSpaceArray() {
        return floor.getSpaceArray();
    }

    @Override
    public synchronized Space getSpace(int number) {
        return floor.getSpace(number);
    }

    @Override
    public synchronized void setSpace(int number, Space space) {
        floor.setSpace(number, space);
    }

    @Override
    public synchronized void addSpace(int number, Space space) {
        floor.addSpace(number, space);
    }

    @Override
    public synchronized void deleteSpace(int number) {
        floor.deleteSpace(number);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized Object clone() {
        return floor.clone();
    }

    @Override
    public synchronized int compareTo(Floor o) {
        return floor.compareTo(o);
    }

    @Override
    public synchronized Iterator<Space> iterator() {
        return floor.iterator();
    }

    public synchronized boolean equals(Object obj) {
        return floor.equals(obj);
    }

    @Override
    public synchronized int hashCode() {
        return floor.hashCode();
    }
}
