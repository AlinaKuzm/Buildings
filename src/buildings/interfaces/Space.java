package buildings.interfaces;

public interface Space extends Cloneable, Comparable<Space> {
    public int getRoomCount();

    public double getSquare();

    public void setRoomCount(int roomCount);

    public void setSquare(double square);

    public Object clone();

}
