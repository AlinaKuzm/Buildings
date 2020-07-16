package buildings.office;

import buildings.interfaces.Space;
import buildings.interfaces.Floor;
import exception.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;

public class OfficeFloor implements Floor, Serializable, Cloneable, Iterable<Space> {
    private static class Node implements Serializable {
        Node next;
        Space office;
    }

    private Node head;

    private OfficeFloor() {
        head = new Node();
        head.next = head;
    }

    // private static int officeCountFloor;
    // private static Office[] arrayOfficeFloor;

//три приватных метода

    private Node getNode(int numberNode) {
        Node current = head;
        for (int i = 0; i <= numberNode; i++) {
            current = current.next;
        }
        return current;
    }

    private void addNode(Node node, int numberNode) {
        Node current = head;
        for (int i = 0; i <= numberNode; i++) {
            current = current.next;
        }
        node.next = current.next;
        current.next = node;
    }

    private void deleteNode(int numberNode) {
        Node current = head;
        for (int i = 0; i <= numberNode; i++) {
            current = current.next;
        }
        current.next = current.next.next;
    }

    // Конструктор может принимать количество офисов на этаже
    public OfficeFloor(int officeCount) {
        this();
        Node current = head;
        for (int i = 0; i < officeCount; i++) {
            Node x = new Node();
            x.office = new Office();
            current.next = x;
            current = x;
        }
        current.next = head.next;
    }

    // public OfficeFloor(int officeCountFloor) {
    //   this.officeCountFloor = officeCountFloor;
    // }

    //Конструктор может принимать массив офисов этажа.

    public OfficeFloor(Space[] arrayOfficeFloor) {
        this();
        Node current = head;
        for (int i = 0; i < arrayOfficeFloor.length; i++) {
            Node x = new Node();
            x.office = arrayOfficeFloor[i];
            current.next = x;
            current = x;
        }
        current.next = head.next;
    }

    //Создайте метод получения количества офисов на этаже.
    @Override
    public int getSpacesCount() {
        Node current = head;
        int count = 0;
        do {
            current = current.next;
            count = count + 1;
        } while (current.next != head.next);
        return count;
    }

    //Создайте метод получения общей площади помещений этажа.
    @Override
    public double getSpacesSquare() {
        double totalSquare = 0.0;
        Node current = head;
        do {
            current = current.next;
            totalSquare = totalSquare + current.office.getSquare();
        } while (current.next != head.next);
        return totalSquare;
    }


    //Создайте метод получения общего количества комнат этажа.
    @Override
    public int getRoomCount() {
        int totalCount = 0;
        Node current = head;
        do {
            current = current.next;
            totalCount = totalCount + current.office.getRoomCount();
        } while (current.next != head.next);
        return totalCount;
    }

    //Создайте метод получения массива офисов этажа.
    @Override
    public Space[] getSpaceArray() {
        Space[] offices = new Space[getSpacesCount()];
        Node current = head;
        int i = 0;
        for (Space office : offices) {
            //for (int i = 0; i < getSpacesCount(); i++) {
            current = current.next;
            offices[i] = (Office) current.office;
            i++;
        }
        return offices;
    }

    //Создайте метод получения офиса по его номеру на этаже.
    @Override
    public Space getSpace(int number) {
        if ((number < 0) || (number > getSpacesCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        return getNode(number).office;

    }

    //Создайте метод изменения офиса по его номеру на этаже и ссылке на обновленный офис.
    @Override
    public void setSpace(int number, Space changeOffice) {
        if ((number < 0) || (number >= getSpacesCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        getNode(number).office = changeOffice;
    }

    //Создайте метод добавления нового офиса на этаже по будущему номеру офиса.
    @Override
    public void addSpace(int number, Space changeOffice) {
        if ((number < 0) || (number >= getSpacesCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node newOffice = new Node();
        newOffice.office = changeOffice;
        addNode(newOffice, number);
    }
    //Создайте метод удаления офиса по его номеру на этаже.

    @Override
    public void deleteSpace(int number) {
        if ((number < 0) || (number >= getSpacesCount())) {
            throw new SpaceIndexOutOfBoundsException();
        }
        deleteNode(number);
    }
    //Создайте метод getBestSpace() получения самого большого по площади офиса
    //этажа.

    @Override
    public Space getBestSpace() {
        double bestSpace = 0.0;
        Space bestOffice = null;
        Node current = head;

        for (int i = 0; i < getSpacesCount(); i++) {
            current = current.next;
            if (current.office.getSquare() > bestSpace) {
                bestSpace = current.office.getSquare();
                bestOffice = current.office;
            }
        }
        return bestOffice;
    }

    //return "Flat (" + roomCount + ", " + square +")";
    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        Space[] offices = getSpaceArray();
        s.append("OfficeFloor (").append(getSpacesCount());
        for (int i = 0; i < offices.length; i++) {
            s.append(", ");
            s.append(offices[i].toString());
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof OfficeFloor)) {
            return false;
        }
        OfficeFloor other = (OfficeFloor) object;
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
        OfficeFloor result = null;
        try {
            result = (OfficeFloor) super.clone();

            //head
            result.head = new Node();
            result.head.next = head;
            for (int i = 0; i < result.getSpacesCount(); i++) {
                result.addSpace(i, (Space) result.getSpace(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            //throw new InternalError();
        }
        return result;
    }

    @Override
    public Iterator<Space> iterator() {
        return new SpaceIterator(this);
    }

    private class SpaceIterator implements Iterator<Space> {
        private int index, count;
        private Node node;

        public SpaceIterator(OfficeFloor officeFloor) {
            index = -1;
            count = officeFloor.getSpacesCount();
            node = officeFloor.head;
            for (int i = 0; i < count - 1; i++) {
                node = node.next;
            }
        }

        @Override
        public Space next() {
            index++;
            node = node.next;
            return node.office;
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

    @Override
    public int compareTo(Floor o) {
        if (this.getSpacesCount() > o.getSpacesCount()) {
            return 1;
        } else if (this.getSpacesCount() < o.getSpacesCount()) {
            return -1;
        } else return 0;
    }
}


