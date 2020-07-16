import buildings.Buildings;
import buildings.interfaces.*;
import buildings.office.*;

import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {


        Office[] officeZeroFloor = new Office[4];
        officeZeroFloor[0] = new Office(70.9, 3);
        officeZeroFloor[1] = new Office(85.3, 4);
        officeZeroFloor[2] = new Office(32, 1);
        officeZeroFloor[3] = new Office(55.6, 2);

        Office[] officeFirstFloor = new Office[2];
        officeFirstFloor[0] = new Office(70.0, 3);
        officeFirstFloor[1] = new Office(33.8, 2);

        OfficeFloor[] officeFloors = new OfficeFloor[2];
        officeFloors[0] = new OfficeFloor(officeZeroFloor);
        officeFloors[1] = new OfficeFloor(officeFirstFloor);

        //OfficeBuilding theOffice = new OfficeBuilding(officeFloors.length, new int[]{officeZeroFloor.length, officeFirstFloor.length});
        OfficeBuilding officeBuilding = new OfficeBuilding(officeFloors);
        //System.out.println(officeBuilding.getBestSpace().getSquare());
        //System.out.println(officeBuilding.getSpace(2));

        ///sorted
        //  Space[] sortedSpace = officeBuilding.getSpaceArraySorted();
        // for(Space office : sortedSpace) {
        //     System.out.println(office.getSquare());
        //  }
        //
// rabota 4 punkt 3
//        FileOutputStream out = new FileOutputStream("out.bin");
//        Buildings.outputBuilding(officeBuilding, out);
//        System.out.println(officeBuilding.toString());
//
//        FileInputStream in = new FileInputStream("out.bin");
//        Building newOfficeBuilding = Buildings.inputBuilding(in);
//        System.out.println(newOfficeBuilding.toString());

//        FileWriter out = new FileWriter("out.txt");
//        Buildings.writeBuilding(officeBuilding, out);
//        System.out.println(officeBuilding.toString());
//        FileReader in = new FileReader("out.txt");
//        Building newOfficeBuilding = Buildings.readBuilding(in);
//        System.out.println(newOfficeBuilding.toString());

//rabota 4 serialize
//FileOutputStream out = new FileOutputStream ("outObject.bin");
//Buildings.serializeBuilding(officeBuilding, out);
//
//FileInputStream in = new FileInputStream ("outObject.bin");
//Building newBuilding1 = Buildings.deserializeBuilding(in);
//System.out.println(newBuilding1.toString());

//rabota 4 writeBuilding
        //Buildings.writeBuilding(officeBuilding, new OutputStreamWriter(System.out));
//        Building newBuilding2 = Buildings.readBuilding(new Scanner(System.in));
//        System.out.println(newBuilding2.toString());

//rabota 5 punkt 1
        // Space test = new Flat();
        //  System.out.println(test.toString());

        // OfficeFloor testOfficeFloor = new OfficeFloor(3);
        //System.out.println(testOfficeFloor.toString());

        // Floor testDwellingFloor = new DwellingFloor(9);
        //  System.out.println(testDwellingFloor.toString());

//        int[] officeCountFloor = {1, 2, 3};
//        OfficeBuilding testOffieBuilding = new OfficeBuilding(2,officeCountFloor);
//        System.out.println(testOffieBuilding.toString());

        //   int[] countFlatsFloor = {1, 2, 3};
        //   Dwelling testDwelling = new Dwelling(3, countFlatsFloor);
        //   System.out.println(testDwelling.toString());

        //Flat newFlat = new Flat(20.5,1);

        //System.out.println(newFlat.hashCode());

        int[] officeCountFloor = {1, 2, 3};
//      OfficeBuilding testOfficeBuilding = new OfficeBuilding(2,officeCountFloor);
//        OfficeBuilding testClone =  (OfficeBuilding) testOfficeBuilding.clone();
//        System.out.println(testOfficeBuilding.toString());
//        System.out.println(testClone.toString());

//       Iterator<Floor> floorIterator = testOfficeBuilding.iterator();
//        System.out.println(floorIterator.hasNext());
//        floorIterator.next();
//        floorIterator.next();
//        System.out.println(floorIterator.hasNext());
//        System.out.println("Проверка sort:");
//        Floor floors[] = {new DwellingFloor(5), new OfficeFloor(1), new HotelFloor(3),
//                new OfficeFloor(4), new DwellingFloor(6)};
//        Buildings.sort(spaces);
//        System.out.println(Arrays.deepToString(spaces));
//       Buildings.sort(floors);
//       System.out.println(Arrays.deepToString(floors));
//        System.out.println("Проверка THREAD:");
//        Floor floor = new OfficeFloor(5);
//
//        Thread t1 = new Repairer(floor);
//        Thread t2 = new Cleaner(floor);
//        t1.start();
//        t2.start();
//
//
//           t1.interrupt();
//rabota7

//        Floor floor = new OfficeFloor(100);
//        Semaphore semaphore = new Semaphore();
//        Thread t1 = new Thread(new SequentalCleaner(floor, semaphore));
//        Thread t2 = new Thread(new SequentalRepairer(floor, semaphore));
//        t1.start();
//        t2.start();

//        //rabota8
//
       //Dwelling dwelling = new Dwelling(1, officeCountFloor);

//        Hotel hotel = new Hotel(2,officeCountFloor);
       // OfficeBuilding officeBuilding1 = new OfficeBuilding(3,officeCountFloor);
//
//        try (Writer out1 = new FileWriter("BUILDINGS.txt");
//             Writer out2 = new FileWriter("TYPES.txt")) {
//            fillFiles(out1, out2, dwelling);
//            fillFiles(out1, out2, officeBuilding1);
//            fillFiles(out1, out2, hotel);
//            fillFiles(out1, out2, dwelling);
//            fillFiles(out1, out2, hotel);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

        //rabota10 flexing
        //System.out.println(Buildings.createSpace(10.0, Flat.class));
        // System.out.println(Buildings.createSpace(10.0,2, Flat.class));
        //System.out.println(Buildings.createFloor(spaces, DwellingFloor.class));
        Building building = Buildings.createBuilding(2, officeCountFloor);

        Buildings.outputBuilding(building, new FileOutputStream("test10.bin"));

        //rabota10();

        //rabota11

//        Space spaces[] = {new Flat(4, 200), new Office(2, 100),
//                new Flat(6, 500), new Office(7, 400)};
//        Buildings.sort(spaces, (Space o1, Space o2) -> o2.getRoomCount() - o1.getRoomCount());
//        Arrays.stream(spaces).forEach(System.out::println);

        //for Swing
        FileReader in = new FileReader("forSwing.txt");
        Building newBuilding = Buildings.readBuilding(in);

        Buildings.outputBuilding(newBuilding, new FileOutputStream("buildingForSwing.bin"));

    }
//    private static void rabota10() {
//        Class buildingClass = Hotel.class;
//        Class floorClass = OfficeFloor.class;
//        Class spaceClass = Flat.class;
//        try{FileInputStream in1 = new FileInputStream("test10.bin");
//            //FileReader in2 = new FileReader("out.txt");
//
//            Building building1 = Buildings.inputBuilding(in1, buildingClass, floorClass, spaceClass);
//           // Building building2 = Buildings.readBuilding(in2, buildingClass, floorClass, spaceClass);
//            System.out.println(building1);
//            //System.out.println(building2);
//        }
//        catch (IOException e) { System.err.println(e.getMessage()); }
//    }

    private static void fillFiles(Writer out1, Writer out2, Building building) throws IOException {
        /* метод для task8() */
        Buildings.writeBuilding(building, out1);
        out1.write("\n");
        out2.write(building.getClass().getSimpleName());
        out2.write("\n");
    }
}


