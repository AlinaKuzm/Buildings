package buildings.office;

import exception.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Iterator;

public class OfficeBuilding implements Building, Serializable, Cloneable, Iterable<Floor> {


    private static class Node implements Serializable {
        Node next;
        Node previous;
        OfficeFloor officeFloor;
    }

    private Node head;

    private OfficeBuilding() {
        head = new Node();
        head.next = head;
        head.previous = head;
    }

    //Создайте три вспомогательных приватных метода:
    //- приватный метод получения узла по его номеру;
    private Node getNode(int numberNode) {
        Node current = head;
        for (int i = 0; i <= numberNode; i++) {
            current = current.next;
        }
        return current;
    }

    //- приватный метод добавления узла в список по номеру;
    private void addNode(int numberNode, Node changeNode) {
        Node current = head;
        for (int i = 0; i < numberNode; i++) {
            current = current.next;
        }
        current.next.previous = changeNode;
        changeNode.next = current.next;
        changeNode.previous = current;
    }

    //- приватный метод удаления узла из списка по его номеру.
    private void deleteNode(int numberNode) {
        Node current = head;
        for (int i = 0; i < numberNode; i++) {
            current = current.next;
        }
        current.next = current.next.next;
        current.next.previous = current;
    }

    //Конструктор может принимать количество этажей и массив количества офисов по
    //этажам.
    public OfficeBuilding(int countFloor, int[] officeCountFloor) {
        this();
        Node current = head;
        for (int i = 0; i < countFloor; i++) {
            Node x = new Node();
            x.officeFloor = new OfficeFloor(officeCountFloor[i]);
            current.next = x;
            current.next.previous = current;
            current = current.next;
        }
        current.next = head.next;
        head.next.previous = current;
    }

    //Конструктор может принимать массив этажей офисного здания.
    public OfficeBuilding(OfficeFloor[] officesFloor) {
        this();
        Node current = head;
        for (int i = 0; i < officesFloor.length; i++) {
            Node x = new Node();
            current.next = x;
            x.previous = current;
            x.officeFloor = officesFloor[i];
            current = current.next;
        }
        current.next = head.next;
        head.next.previous = current;
    }

    //Создайте метод получения общего количества этажей здания.
    @Override
    public int getFloorsCount() {
        int countFloor = 0;
        Node current = head;
        do {
            current = current.next;
            countFloor = countFloor + 1;
        } while (current.next != head.next);
        return countFloor;
    }

    //Создайте метод получения общего количества офисов здания.
    @Override
    public int getSpacesCount() {
        int countOffices = 0;
        Node current = head;
        do {
            current = current.next;
            countOffices = countOffices + current.officeFloor.getSpacesCount();
        } while (current.next != head.next);
        return countOffices;
    }

    //Создайте метод получения общей площади помещений здания.
    @Override
    public double getSpacesSquare() {
        double squareOffices = 0.0;
        Node current = head;
        do {
            current = current.next;
            squareOffices = squareOffices + current.officeFloor.getSpacesCount();
        } while (current.next != head.next);
        return squareOffices;
    }

    //Создайте метод получения общего количества комнат здания.
    @Override
    public int getRoomCount() {
        int countRoom = 0;
        Node current = head;
        do {
            current = current.next;
            countRoom += countRoom + current.officeFloor.getRoomCount();
        } while (current.next != head.next);
        return countRoom;
    }

    //Создайте метод получения массива этажей офисного здания.
    @Override
    public Floor[] getFloorsArray() {
        OfficeFloor[] officeFloors = new OfficeFloor[getFloorsCount()];
        Node current = head;
        for (int i = 0; i < getFloorsCount(); i++) {
            current = current.next;
            officeFloors[i] = current.officeFloor;
        }
        return officeFloors;
    }

    //Создайте метод получения объекта этажа, по его номеру в здании.
    @Override
    public Floor getFloor(int number) {
        if ((number < 0) || (number > getFloorsCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        return getNode(number).officeFloor;
    }

    //Создайте метод изменения этажа по его номеру в здании и ссылке на объект нового
    //этажа.
    @Override
    public void setFloor(int number, Floor changeFloor) {
        if ((number < 0) || (number >= getFloorsCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        getNode(number).officeFloor = (OfficeFloor) changeFloor;
    }

    //Создайте метод получения объекта офиса по его номеру в офисном здании.
    @Override
    public Space getSpace(int number) {
        if ((number < 0) || (number >= getFloorsCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node current = head;
        int sum = 0;
        for (int i = 0; i < getFloorsCount(); i++) {
            current = current.next;
            sum += current.officeFloor.getSpacesCount();
            if (sum >= number) {
                int numberOnFloor = current.officeFloor.getSpacesCount() - (sum - number);
                return current.officeFloor.getSpace(numberOnFloor);
            }
        }
        return null;
    }

    //Создайте метод изменения объекта офиса по его номеру в доме и ссылке на объект
    //офиса.
    @Override
    public void setSpace(int number, Space changeOffice) {
        if ((number < 0) || (number >= getFloorsCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node current = head;
        int sum = 0;
        for (int i = 0; i < getFloorsCount(); i++) {
            current = current.next;
            sum += current.officeFloor.getSpacesCount();
            if (sum >= number) {
                int indexOnFloor = current.officeFloor.getSpacesCount() - (sum - number);
                current.officeFloor.setSpace(indexOnFloor, changeOffice);
            }
        }
    }

    //Создайте метод добавления офиса в здание по номеру офиса в здании и ссылке на объект
    //офиса.
    @Override
    public void addSpace(int number, Space newOffice) {
        if ((number < 0) || (number > getFloorsCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node current = head;
        int sum = 0;
        for (int i = 0; i < getFloorsCount(); i++) {
            current = current.next;
            sum += current.officeFloor.getSpacesCount();
            if (sum >= number) {
                int indexOnFloor = current.officeFloor.getSpacesCount() - (sum - number);
                current.officeFloor.addSpace(indexOnFloor, newOffice);
            }
        }
    }

    //Создайте метод удаления офиса по его номеру в здании.
    @Override
    public void deleteSpace(int number) {
        if ((number < 0) || (number >= getFloorsCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node current = head;
        int sum = 0;
        for (int i = 0; i < getFloorsCount(); i++) {
            current = current.next;
            sum += current.officeFloor.getSpacesCount();
            if (sum >= number) {
                int indexOnFloor = current.officeFloor.getSpacesCount() - (sum - number);
                current.officeFloor.deleteSpace(indexOnFloor);
            }
        }
    }

    //Создайте метод getBestSpace() получения самого большого по площади офиса
    //здания.
    @Override
    public Space getBestSpace() {
        if (head.next == head.previous) {
            return null;
        }
        Node current = head.next;
        Space bestOffice = current.officeFloor.getBestSpace();
        for (int i = 1; i < getFloorsCount(); i++) {
            current = current.next;
            if (bestOffice.getSquare() < current.officeFloor.getBestSpace().getSquare()) {
                bestOffice = current.officeFloor.getBestSpace();
            }
        }
        return bestOffice;
    }

    //Создайте метод получения отсортированного по убыванию площадей массива офисов.
    @Override
    public Space[] getSpaceArraySorted() {
        Space[] officesSorted = new Office[getSpacesCount()];
        Floor[] floorsOfficeBuilding = getFloorsArray();
        int k = 0;
        for (int i = 0; i < floorsOfficeBuilding.length; i++) {
            Space[] currentFloor = floorsOfficeBuilding[i].getSpaceArray();
            for (int j = 0; j < currentFloor.length; j++) {
                officesSorted[k] = currentFloor[j];
                k++;
            }

        }
        for (int n = 0; n < officesSorted.length; n++) {
            Space current = officesSorted[n];
            int m = n - 1;
            while (m >= 0 && current.getSquare() > officesSorted[m].getSquare()) {
                officesSorted[m + 1] = officesSorted[m];
                m = m - 1;
            }
            officesSorted[m + 1] = current;
        }

        return officesSorted;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        OfficeFloor[] officefloor = (OfficeFloor[]) getFloorsArray();
        s.append("OfficeBuilding (").append(getFloorsCount());
        for (int i = 0; i < officefloor.length; i++) {
            s.append(", ");
            s.append(officefloor[i].toString());
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof OfficeBuilding)) {
            return false;
        }
        OfficeBuilding other = (OfficeBuilding) object;
        if (head == null) {
            if (other.head != null)
                return false;
        } else if (!head.equals(other.head))

            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((head == null) ? 0 : head.hashCode());
        return result;
    }

    @Override
    public Object clone() {
        OfficeBuilding result = null;
        try {
            result = (OfficeBuilding) super.clone();
            result.head = new Node();
            result.head.next = head;
            result.head.previous = head;
            for (int i = 0; i < result.getFloorsCount(); i++) {
                result.setFloor(i, (Floor) result.getFloor(i).clone());
                for (int j = 0; j < result.getFloor(i).getSpacesCount(); j++) {
                    result.getFloor(i).setSpace(j, (Space) result.getSpace(j).clone());
                }
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

        private int index, count;
        private Node node;

        public FloorIterator(OfficeBuilding officeBuilding) {
            index = -1;
            count = officeBuilding.getFloorsCount();
            node = officeBuilding.head;
            for (int i = 0; i < count - 1; i++) {
                node = node.next;
            }
        }

        @Override
        public Floor next() {
            index++;
            node = node.next;
            return node.officeFloor;
        }

        @Override
        public boolean hasNext() {
            if ((index + 1) < count) {
                return true;
            } else {
                return false;
            }
        }
    }
}




