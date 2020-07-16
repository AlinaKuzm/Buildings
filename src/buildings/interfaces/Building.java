package buildings.interfaces;

public interface Building extends Cloneable, Iterable<Floor> {
    public int getFloorsCount();

    public int getSpacesCount();

    public double getSpacesSquare();

    public int getRoomCount();

    public Floor[] getFloorsArray();

    public Floor getFloor(int number);

    public void setFloor(int number, Floor changeFloor);

    public Space getSpace(int number);

    public void setSpace(int number, Space changeSpace);

    public void addSpace(int number, Space changeSpace);

    public void deleteSpace(int number);

    public Space getBestSpace();

    public Space[] getSpaceArraySorted();

    public Object clone();

}
