package buildings.office;

import buildings.interfaces.Space;
import exception.InvalidRoomsCountException;
import exception.InvalidSpaceAreaException;

import java.io.Serializable;

public class Office implements Space, Serializable, Cloneable {

    private final int ROOM_COUNT = 1;
    private final double ROOM_SQUARE = 250.0;

    private int roomCount;
    private double square;


    public Office() {
        roomCount = ROOM_COUNT;
        square = ROOM_SQUARE;
    }

    public Office(double square) {
        roomCount = ROOM_COUNT;
        this.square = square;
    }

    // конструктор может принимать пл офиса и колво комнат
    public Office(double square, int roomCount) {
        if (square <= 0) {
            throw new InvalidSpaceAreaException();
        }
        if (roomCount <= 0) {
            throw new InvalidRoomsCountException();
        }
        this.square = square;
        this.roomCount = roomCount;
    }

    // получ колва комнат в офисе
    @Override
    public int getRoomCount() {
        return roomCount;
    }

    //измен колва комнат в офисе
    @Override
    public void setRoomCount(int roomCountOfficeChange) {
        if (roomCount <= 0) {
            throw new InvalidRoomsCountException();
        }
        roomCount = roomCountOfficeChange;
    }

    //получения площади офиса
    @Override
    public double getSquare() {
        return square;
    }

    //измен пл офиса
    @Override
    public void setSquare(double squareOfficeChange) {
        if (squareOfficeChange <= 0) {
            throw new InvalidSpaceAreaException();
        }
        square = squareOfficeChange;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("Office ").append("(").append(roomCount).append(",").append(square).append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Office)) {
            return false;
        }
        Office other = (Office) object;
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


