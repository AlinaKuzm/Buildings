package buildings.dwelling;

import buildings.interfaces.Space;
import exception.InvalidRoomsCountException;
import exception.InvalidSpaceAreaException;

import java.io.Serializable;

public class Flat implements Space, Serializable, Cloneable {
    private final int ROOM_COUNT = 2;
    private final double ROOM_SQUARE = 50.0;

    private int roomCount;
    private double square;

    public Flat() {
        roomCount = ROOM_COUNT;
        square = ROOM_SQUARE;
    }

    public Flat(double square) {
        roomCount = ROOM_COUNT;
        this.square = square;
    }

    public Flat(double square, int roomCount) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
        if (roomCount <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.square = square;
        this.roomCount = roomCount;
    }

    @Override
    public int getRoomCount() {
        return roomCount;
    }

    @Override
    public void setRoomCount(int roomCountChange) {
        roomCount = roomCountChange;
    }

    @Override
    public double getSquare() {
        return square;
    }

    @Override
    public void setSquare(double squareChange) {
        square = squareChange;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("Flat ").append("(").append(roomCount).append(",").append(square).append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Flat)) {
            return false;
        }
        Flat other = (Flat) object;
        return (this.square == other.square) && (this.roomCount == other.roomCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(square);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + roomCount;
        return result;
    }

    @Override
    public Object clone() {
        Object result = null;
        try {
            result = super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return result;
    }

    @Override
    public int compareTo(Space o) {
        if (this.square > o.getSquare()) {
            return 1;
        } else if (this.square < o.getSquare()) {
            return -1;
        } else return 0;
    }
}