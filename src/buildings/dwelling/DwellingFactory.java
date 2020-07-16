package buildings.dwelling;

import buildings.interfaces.*;

public class DwellingFactory implements BuildingFactory {

    @Override
    public Space createSpace(double square) {
        return new Flat(square);
    }

    @Override
    public Space createSpace(double square, int roomCount) {
        return new Flat(square, roomCount);
    }

    @Override
    public Floor createFloor(int spacesCount) {
        return new DwellingFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new DwellingFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCount) {
        return new Dwelling(floorsCount, spacesCount);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Dwelling((DwellingFloor[]) floors);
    }
}
