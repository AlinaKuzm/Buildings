package buildings.dwelling;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exception.FloorIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class Dwelling implements Building, Serializable, Cloneable, Iterable<Floor> {

    private int countFloor;
    private int[] countFlatsFloor;
    protected Floor[] floors;

    public Dwelling(int countFloor, int[] countFlatsFloor) {
        this.countFloor = countFloor;
        this.countFlatsFloor = countFlatsFloor;
        this.floors = new DwellingFloor[countFloor];
        for (int i = 0; i < countFloor; i++) {
            this.floors[i] = new DwellingFloor(countFlatsFloor[i]);
        }
    }

    public Dwelling(Floor[] floors) {
        countFloor = floors.length;
        countFlatsFloor = new int[countFloor];
        for (int i = 0; i < floors.length; i++) {
            countFlatsFloor[i] = floors[i].getSpacesCount();
        }
        this.floors = floors;
    }

    @Override
    public int getFloorsCount() {
        return countFloor;
    } //общее колво этажей дома

    @Override
    public int getSpacesCount() {//колво квартир дома
        int countFlatsDwelling = 0;

        for (int i = 0; i < countFloor; i++) {
            countFlatsDwelling = countFlatsDwelling + countFlatsFloor[i];
        }
        return countFlatsDwelling;
    }

    @Override
    public double getSpacesSquare() {
        double squareFlatsDwelling = 0;
        for (int i = 0; i < floors.length; i++) {
            squareFlatsDwelling = squareFlatsDwelling + floors[i].getSpacesSquare(); //получение площади всех квартир дома
        }
        return squareFlatsDwelling;
    }

    @Override
    public int getRoomCount() {
        int countRoomsDwelling = 0;

        for (int i = 0; i < floors.length; i++) {

            countRoomsDwelling = countRoomsDwelling + floors[i].getRoomCount(); //получение колва квартир дома

        }
        return countRoomsDwelling;
    }

    @Override
    public Floor[] getFloorsArray() //получение массива этажей дома
    {
        return floors;
    }

    @Override
    public Floor getFloor(int number) {

        if ((number >= floors.length) || (number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        return floors[number];
    }

    @Override
    public void setFloor(int number, Floor changeFloor) {
        if ((number >= floors.length) || (number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        floors[number] = (DwellingFloor) changeFloor;
    }

    //метод получения объекта квартиры по ее номеру в доме
    @Override
    public Space getSpace(int number) {
        if ((number >= floors.length) || (number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        int sum = 0;

        for (int i = 0; i < floors.length; i++) {

            int flatsCount = floors[i].getSpacesCount();
            sum = sum + flatsCount;
            if (sum >= number) {
                return floors[i].getSpace(flatsCount - (sum - number));

            }
        }
        return null;
    }

    //метод изменения объекта квартиры по ее номеру в доме и ссылке на объект квартиры
    @Override
    public void setSpace(int number, Space changeSpace) {
        if ((number >= floors.length) || (number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        int sum = 0;

        for (int i = 0; i < floors.length; i++) {

            int flatsCount = floors[i].getSpacesCount();
            sum = sum + flatsCount;
            if (sum >= number) {
                floors[i].setSpace(flatsCount - (sum - number), changeSpace);
            }
        }

    }

    //метод добавления квартиры в дом по будущему номеру квартиры в доме (т.е. в
//параметрах указывается номер, который должны иметь квартира после вставки) и ссылке на
//объект квартиры (количество этажей в доме при этом не увеличивается).
    @Override
    public void addSpace(int number, Space changeSpace) {
        if ((number >= floors.length) || (number < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        int sum = 0;
        for (int i = 0; i < floors.length; i++) {
            int flatsCount = floors[i].getSpacesCount();
            sum = sum + flatsCount;
            if (sum >= number) {
                int numberOnFloor = flatsCount - (sum - number);
                floors[i].addSpace(numberOnFloor, changeSpace);
            }
        }
    }

    //метод удаления квартиры по ее номеру в доме.
    @Override
    public void deleteSpace(int number) {
        int sum = 0;
        for (int i = 0; i < floors.length; i++) {
            int flatsCount = floors[i].getSpacesCount();
            sum = sum + flatsCount;
            if (sum >= number) {
                int numberOnFloor = flatsCount - (sum - number);
                floors[i].deleteSpace(numberOnFloor);
            }
        }
    }

    //метод getBestSpace() получения самой большой по площади квартиры
    //дома.
    @Override
    public Space getBestSpace() {
        Space bestSpace = floors[0].getBestSpace();

        for (int i = 0; i < floors.length; i++) {
            Space temporaryBestFlat = floors[i].getBestSpace();
            if (temporaryBestFlat.getSquare() > bestSpace.getSquare()) {
                bestSpace = temporaryBestFlat;
            }

        }
        return bestSpace;
    }

    public Space[] getSpaceArraySorted() {
        Space[] totalFlats = new Space[this.getSpacesCount()];
        int k = 0;
        for (int i = 0; i < floors.length; i++) {
            Space[] currentFlatsFloor = floors[i].getSpaceArray();

            for (int j = 0; j < currentFlatsFloor.length; j++) {
                totalFlats[k] = currentFlatsFloor[j];
                k = k + 1;
            }

        }

        for (int n = 0; n < totalFlats.length; n++) {
            Space current = totalFlats[n];
            int m = n - 1;
            while (m >= 0 && current.getSquare() > totalFlats[m].getSquare()) {
                totalFlats[m + 1] = totalFlats[m];
                m = m - 1;
            }
            totalFlats[m + 1] = current;
        }
        return totalFlats;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Dwelling (").append(getFloorsCount());
        for (int i = 0; i < getFloorsCount(); i++) {
            s.append(", ");
            s.append(floors[i].toString());
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Dwelling)) {
            return false;
        }
        Dwelling other = (Dwelling) object;
        if (!Arrays.equals(floors, other.floors))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(floors);
        return result;
    }

    @Override
    public Object clone() {
        Building result = null;
        try {
            result = (Building) super.clone();

            // array has to be cloned
            for (int i = 0; i < result.getFloorsCount(); i++) {
                result.setFloor(i, (Floor) result.getFloor(i).clone());
//                for (int j = 0; j < result.getFloor(i).getSpacesCount(); i++) {
//                    result.getFloor(i).setSpace(j, (Space) result.getSpace(j).clone());
//                }
            }
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return result;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new FloorIterator(this);
    }

    private class FloorIterator implements Iterator<Floor> {
        private Dwelling dwelling;
        private int index;

        public FloorIterator(Dwelling dwelling) {
            this.dwelling = dwelling;
            index = -1;
        }

        @Override
        public Floor next() {
            index++;
            return dwelling.getFloor(index);
        }

        @Override
        public boolean hasNext() {
            if ((index + 1) < dwelling.getFloorsCount()) {
                return true;
            } else {
                return false;
            }
        }
    }

}



