package buildings.interfaces;
public interface Floor extends Cloneable, Iterable<Space>, Comparable<Floor> {
    public int getSpacesCount();

    public double getSpacesSquare();

    public int getRoomCount();

    public Space[] getSpaceArray();

    public Space getSpace(int number);

    public void setSpace(int number, Space space);

    public void addSpace(int number, Space space);

    public void deleteSpace(int number);

    public Space getBestSpace();

    public Object clone();

}