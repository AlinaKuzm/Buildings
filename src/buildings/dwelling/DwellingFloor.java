package buildings.dwelling;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import exception.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class DwellingFloor implements Floor, Serializable, Cloneable, Iterable<Space> {
    private int flatCountFloor;
    protected Space[] flats;

    public DwellingFloor(int flatCountFloor) {
        this.flatCountFloor = flatCountFloor;
        this.flats = new Space[flatCountFloor];
        for (int i = 0; i < flatCountFloor; i++) {
            flats[i] = new Flat();
        }

    }

    public DwellingFloor(Space[] flats) {
        flatCountFloor = flats.length;
        this.flats = flats;

    }

    @Override
    public int getSpacesCount() {
        return flatCountFloor;
    }

    @Override
    public double getSpacesSquare() {
        double totalSquare = 0.0;

        for (int i = 0; i < flats.length; i++) {
            totalSquare = totalSquare + flats[i].getSquare();
        }
        return totalSquare;
    }

    @Override
    public int getRoomCount() {
        int totalRoomsCount = 0;
        for (Space flat : flats) {
            //for(int i=0; i< flats.length; i++)   {
            totalRoomsCount = totalRoomsCount + flat.getRoomCount();
        }
        return totalRoomsCount;
    }

    @Override
    public Space[] getSpaceArray() {
        return flats;
    }

    @Override
    public Space getSpace(int number) {
        if ((number < 0) || (number >= flats.length)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        return flats[number];
    }

    @Override
    public void setSpace(int number, Space flatChange) {
        if ((number < 0) || (number >= flats.length)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        flats[number] = (Flat) flatChange;
    }

    @Override
    public void addSpace(int number, Space flatChange) {
        if ((number < 0) || (number >= flats.length)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Space[] flatsNew;
        if (number > flats.length) {
            flatsNew = new Flat[number + 1]; //новый массив с длиной []
        } else {
            flatsNew = new Flat[flats.length + 1];
        }
        flatsNew[number] = (Flat) flatChange;
        int j = 0; // счетчик для нового массива
        for (int i = 0; i < flats.length; i++, j++) {

            if (i == number) {
                j++;
                flatsNew[j] = flats[i];
            }

            flatsNew[j] = flats[i];

        }
        flats = flatsNew;
    }

    @Override
    public void deleteSpace(int number) {
        if ((number < 0) || (number >= flats.length)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        flats[number] = null;
    }

    @Override
    public Space getBestSpace() {
        Space bestFlat = flats[0];
        for (Space flat : flats) {
            //  for(int i=0; i< flats.length; i++){
            if (flat.getSquare() > bestFlat.getSquare())
                bestFlat = flat;
        }
        return bestFlat;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("DwellingFloor (").append(getSpacesCount()).append(" ");
        for (int i = 0; i < flats.length; i++) {
            s.append(", ");
            s.append(flats[i].toString());
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof DwellingFloor)) {
            return false;
        }
        DwellingFloor other = (DwellingFloor) object;
        if (!Arrays.equals(flats, other.flats))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(flats);
        return result;
    }

    @Override
    public Object clone() {
        DwellingFloor result = null;
        try {
            result = (DwellingFloor) super.clone();
            result.flats = flats.clone();
            for (int i = 0; i < result.getSpacesCount(); i++) {
                result.setSpace(i, (Space) result.getSpace(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return result;
    }

    @Override
    public Iterator<Space> iterator() {
        return new SpaceIterator(this);
    }


    private class SpaceIterator implements Iterator<Space> {
        private DwellingFloor dwellingFloor;
        private int index;

        public SpaceIterator(DwellingFloor dwellingFloor) {
            this.dwellingFloor = dwellingFloor;
            index = -1;
        }

        @Override
        public Space next() {
            index++;
            return dwellingFloor.getSpace(index);
        }

        @Override
        public boolean hasNext() {
            if ((index + 1) < dwellingFloor.getSpacesCount()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int compareTo(Floor o) {
        if (this.getSpacesCount() > o.getSpacesCount()) {
            return 1;
        } else if (this.getSpacesCount() < o.getSpacesCount()) {
            return -1;
        } else return 0;
    }
}