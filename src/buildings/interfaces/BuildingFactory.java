package buildings.interfaces;

public interface BuildingFactory {
    public Space createSpace(double square);
    public Space createSpace(double square, int roomCount);
    public Floor createFloor(int spacesCount);
    public Floor createFloor(Space[] spaces);
    public Building createBuilding(int floorsCount, int[] spacesCount);
    public Building createBuilding(Floor[] floors);
}
