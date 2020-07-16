package buildings;

import buildings.dwelling.DwellingFactory;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Building;
import buildings.interfaces.BuildingFactory;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class Buildings {
    // записи данных о здании в байтовый поток
    //public static void outputBuilding (Building building,
    //OutputStream out);
    public static void outputBuilding(Building building, OutputStream out) {
        try {
            DataOutputStream dos = new DataOutputStream(out);

            Floor floor = null;
            Space space = null;
            int floorsCount = building.getFloorsCount();
            dos.writeInt(building.getFloorsCount());
            for (int i = 0; i < floorsCount; i++) {
                floor = building.getFloor(i);
                dos.writeInt(floor.getSpacesCount());
                for (int j = 0; j < floor.getSpacesCount(); j++) {
                    space = floor.getSpace(j);
                    dos.writeDouble(space.getSquare());
                    dos.writeInt(space.getRoomCount());

                }
            }
//        dos.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // чтения данных о здании из байтового потока
    //public static Building inputBuilding (InputStream in);
    public static Building inputBuilding(InputStream in) {
        try {
            DataInputStream dis = new DataInputStream(in);


            Floor[] floors = new Floor[dis.readInt()];
            for (int i = 0; i < floors.length; i++) {
                Space[] spaces = new Space[dis.readInt()];
                for (int j = 0; j < spaces.length; j++) {
                    //spaces[j] = new Flat(dis.readDouble(), dis.readInt());
                    spaces[j] = buildingFactory.createSpace(dis.readDouble(), dis.readInt());
                }
                //floors[i] = new DwellingFloor((Flat[])spaces);
                floors[i] = buildingFactory.createFloor(spaces);
            }
//              dis.close();
            //return new Dwelling((DwellingFloor[]) floors);
            return buildingFactory.createBuilding(floors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static Building inputBuilding(InputStream in, Class<Building> buildingClass, Class<Floor> floorClass, Class<Space> spaceClass) throws IOException {
            DataInputStream dis = new DataInputStream(in);
            Floor[] floors = new Floor[dis.readInt()];
            for (int i = 0; i < floors.length; i++) {
                Space[] spaces = new Space[dis.readInt()];
                for (int j = 0; j < spaces.length; j++) {
                    spaces[j] = createSpace(dis.readDouble(), dis.readInt(), spaceClass);
                }
                floors[i] = createFloor(floorClass, spaces);
            }
            return createBuilding(buildingClass, floors);
    }

    // записи здания в символьный поток
    //public static void writeBuilding (Building building,
    //Writer out);
    public static void writeBuilding(Building building, Writer out) {
        try {
            PrintWriter pw = new PrintWriter(out);
            Floor floor = null;
            Space space = null;
            pw.print(building.getFloorsCount() + " ");
            for (int i = 0; i < building.getFloorsCount(); i++) {
                floor = building.getFloor(i);
                pw.print(floor.getSpacesCount() + " ");
                for (int j = 0; j < floor.getSpacesCount(); j++) {
                    space = floor.getSpace(j);
                    pw.print(space.getSquare() + " ");
                    pw.print(space.getRoomCount() + " ");

                }
            }
//            pw.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // чтения здания из символьного потока
    //public static Building readBuilding (Reader in).
    public static Building readBuilding(Reader in) {
        try {
            StreamTokenizer st = new StreamTokenizer(in);
            st.nextToken();
            Floor[] floors = new DwellingFloor[(int) st.nval];
            for (int i = 0; i < floors.length; i++) {
                st.nextToken();
                Space[] spaces = new Flat[(int) st.nval];
                for (int j = 0; j < spaces.length; j++) {
                    st.nextToken();
                    double square = st.nval;
                    st.nextToken();
                    int roomCount = (int) st.nval;
                    //spaces[j] = new Flat(square, roomCount);
                    spaces[j] = buildingFactory.createSpace(square, roomCount);
                }
                //floors[i] = new DwellingFloor((Flat[]) spaces);
                floors[i] = buildingFactory.createFloor(spaces);
            }
            // return new Dwelling((DwellingFloor[]) floors);
            return buildingFactory.createBuilding(floors);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    // с рефлексией
    public static Building readBuilding(DataInputStream in, Class<Building> buildingClass, Class<Floor>floorClass, Class<Space>spaceClass) throws IOException {
            StreamTokenizer st = new StreamTokenizer(in);
            st.nextToken();
            Floor[] floors = new DwellingFloor[(int) st.nval];
            for (int i = 0; i < floors.length; i++) {
                st.nextToken();
                Space[] spaces = new Flat[(int) st.nval];
                for (int j = 0; j < spaces.length; j++) {
                    st.nextToken();
                    double square = st.nval;
                    st.nextToken();
                    int roomCount = (int) st.nval;

                    spaces[j] = createSpace(square, roomCount, spaceClass);
                }

                floors[i] = createFloor(floorClass, spaces);
            }
            return createBuilding(buildingClass, floors);

    }

    public static void serializeBuilding(Building building, OutputStream out) {
        try {
            ObjectOutputStream outObject = new ObjectOutputStream(out);
            outObject.writeObject(building);
            outObject.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static Building deserializeBuilding(InputStream in) {
        try {
            ObjectInputStream inObject = new ObjectInputStream(in);
            Building building = (Building) inObject.readObject();
            inObject.close();
            return building;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static void writeBuildingFormat(Building building, Writer out) {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.printf("%d ", building.getFloorsCount());
        for (Floor floor : building.getFloorsArray()) {
            printWriter.printf("%d ", floor.getSpacesCount());
            for (Space space : floor.getSpaceArray()) {
                printWriter.printf("%.3f ", space.getSquare());
                printWriter.printf("%d ", space.getRoomCount());
            }
            printWriter.print(" ");
        }
        printWriter.println();
        printWriter.flush();
    }

    public static Building readBuilding(ObjectInputStream scanner) throws IOException {
        System.out.println("Введите кол-во этажей");
        Floor[] floors = new DwellingFloor[scanner.readInt()];
        for (int i = 0; i < floors.length; i++) {
            System.out.println("Введите кол-во помещений на этаже № " + i);
            Space[] spaces = new Flat[scanner.readInt()];
            for (int j = 0; j < spaces.length; j++) {
                System.out.println("Введите площадь помещения № " + j + " на этаже № " + i);
                double square = scanner.readDouble();
                System.out.println("Введите количество комнат помещения № " + j + " на этаже № " + i);
                int roomCount = scanner.readInt();
                // spaces[j] = new Flat(square,roomCount)  ;
                spaces[j] = buildingFactory.createSpace(square, roomCount);
            }
            //floors[i] = new DwellingFloor((Flat[])spaces);
            floors[i] = buildingFactory.createFloor(spaces);
        }
        //return new Dwelling((DwellingFloor[]) floors );
        return buildingFactory.createBuilding(floors);
    }


    public static <T extends Comparable<T>> void sort(T[] objects) {
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (objects[j].compareTo(objects[minIndex]) < 0)
                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }

    public static <T> void sort(T[] objects, Comparator<T> comparator) {
        for (int i = 0; i < objects.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < objects.length; j++) {
                if (comparator.compare(objects[j], objects[minIndex]) < 0)
                    minIndex = j;
            }
            T swapBuf = objects[i];
            objects[i] = objects[minIndex];
            objects[minIndex] = swapBuf;
        }
    }

    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory buildFact) {
        buildingFactory = buildFact;
    }

    public static Space createSpace(double square) {
        return buildingFactory.createSpace(square);
    }

    public static Space createSpace(double square, Class<? extends Space> spaceClass) {
        try {
            Constructor<? extends Space> constructor = spaceClass.getConstructor(double.class);
            return  constructor.newInstance(square);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }


    public static Space createSpace(double square, int roomCount) {
        return buildingFactory.createSpace(square, roomCount);
    }

    public static Space createSpace(double square, int roomCount, Class<Space> spaceClass) {
        Space space = null;
        try {
            Constructor<Space> constructor = spaceClass.getConstructor(double.class, int.class);
            space = constructor.newInstance(square, roomCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return space;
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(int spacesCount, Class<Floor> floorClass) {
        Floor floor = null;
        try {
            Constructor<Floor> constructor = floorClass.getConstructor(int.class);
            floor = constructor.newInstance(spacesCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
        return floor;
    }


    public static Floor createFloor(Space... spaces) {
        return buildingFactory.createFloor(spaces);
    }


    public static Floor createFloor(Class<Floor> floorClass, Space... spaces) {
        try {
            Constructor<Floor> constructor = floorClass.getConstructor(Space[].class);
            return constructor.newInstance((Object) spaces);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }

    }


    public static Building createBuilding(int floorsCount, int... spacesCount) {
        return buildingFactory.createBuilding(floorsCount, spacesCount);
    }

    public static Building createBuilding(int floorsCount, Class<Building> buildingClass, int... spacesCount) {
        try {
            Constructor<Building> constructor = buildingClass.getConstructor(int.class, int[].class);
            return constructor.newInstance(floorsCount, spacesCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }

    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }


    public static Building createBuilding(Class buildingClass, Floor... floors) {

        try {
            Constructor<Building> constructor = buildingClass.getConstructor(Floor[].class);
            return constructor.newInstance((Object) floors);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }
}




