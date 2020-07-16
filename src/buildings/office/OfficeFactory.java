package buildings.office;

import buildings.interfaces.*;

public class OfficeFactory implements BuildingFactory {
    @Override
    public Space createSpace(double square) {
        return new Office(square);
    }

    @Override
    public Space createSpace(double square, int roomCount) {
        return new Office(square, roomCount);
    }

    @Override
    public Floor createFloor(int spacesCount) {
        return new OfficeFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new OfficeFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCount) {
        return new OfficeBuilding(floorsCount, spacesCount);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new OfficeBuilding((OfficeFloor[]) floors);
    }
}
