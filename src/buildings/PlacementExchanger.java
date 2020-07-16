package buildings;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exception.FloorIndexOutOfBoundsException;
import exception.InexchangeableFloorsException;
import exception.InexchangeableSpacesException;
import exception.SpaceIndexOutOfBoundsException;


public class PlacementExchanger {

    // Метод проверки возможности обмена помещениями. Передаются две ссылки на
//объекты типа Space. Метод возвращает true, если общая площадь и количество
//комнат в помещениях равны, и false в других случаях.
    public static boolean Exchange(Space obj1, Space obj2) {
        return obj1.getSquare() == obj2.getSquare() && obj1.getRoomCount() == obj2.getRoomCount();
    }

    // Метод проверки возможности обмена этажами. Методу передаются две ссылки на
//объекты типа Floor. Метод возвращает true, если общая площадь этажей и
//количество помещений равны, и false в других случаях.
    public static boolean Exchange(Floor obj1, Floor obj2) {
        return obj1.getSpacesSquare() == obj2.getSpacesSquare() && obj1.getSpacesCount() == obj2.getSpacesCount();
    }

    // Метод обмена помещениями двух этажей public static void
//exchangeFloorRooms(Floor floor1, int index1, Floor floor2,
//int index2). Метод должен проверять возможность обмена помещениями и
//допустимость номеров помещений, выбрасывать при необходимости
//соответствующие исключения.
    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if (!Exchange(floor1.getSpace(index1), floor2.getSpace(index2))) {
            throw new InexchangeableSpacesException();
        } else if ((index1 >= floor1.getSpacesCount() || index1 < 0) || (index2 >= floor2.getSpacesCount() || index2 < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        } else {
            Space current = floor1.getSpace(index1);
            floor1.setSpace(index1, floor2.getSpace(index2));
            floor2.setSpace(index2, current);

        }

    }

    // Метод обмена этажами двух зданий public static void
//exchangeBuildingFloors(Building building1, int index1,
//Building building2, int index2). Методу передаются две ссылки типа
//Building и номера соответствующих этажей. Метод должен проверять
//возможность обмена этажами и допустимость номеров этажей, выбрасывать при
//необходимости соответствующие исключения.
    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        if (!Exchange(building1.getFloor(index1), building2.getFloor(index2))) {
            throw new InexchangeableFloorsException();
        } else if ((index1 >= building1.getFloorsCount() || index1 < 0) || (index2 >= building2.getFloorsCount() || index2 < 0)) {
            throw new FloorIndexOutOfBoundsException();
        } else {
            Floor current = building1.getFloor(index1);
            building1.setFloor(index1, building2.getFloor(index2));
            building2.setFloor(index2, current);
        }
    }
}

