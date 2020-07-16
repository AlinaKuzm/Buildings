package buildings.net.client;

import buildings.Buildings;
import buildings.interfaces.Building;
import exception.BuildingUnderArrestException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SerialClient {
    private static final String BUILDINGS = "Buildings.txt";
    private static final String TYPES_OF_BUILDINGS = "Types.txt";
    private static final String COSTS = "Costs.txt";
    private static Socket clientSocket;
    private static ObjectInputStream in; // чтение из сокета
    private static ObjectOutputStream out; //запись в сокет


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            FileReader buildings = new FileReader(BUILDINGS);
            Scanner types = new Scanner(new FileReader(TYPES_OF_BUILDINGS));
            PrintWriter costs = new PrintWriter(new FileWriter(COSTS));

            try {
                clientSocket = new Socket("localhost", 7);
                //datainputstream, dataoutputstream
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                while (types.hasNextLine()) {
                    String type = types.nextLine();
                    ///фабрики не настраивать а просто сказать серверу что 1 это двеллинг и тд
                    if (type.equals("Dwelling")) {
                        //Buildings.setBuildingFactory(new DwellingFactory());
                        out.write(1); //1-dwelling
                    } else if (type.equals("OfficeBuilding")) {
                        // Buildings.setBuildingFactory(new OfficeFactory());
                        out.write(2); //2-officebuilding
                    } else if (type.equals("Hotel")) {
                        // Buildings.setBuildingFactory(new HotelFactory());
                        out.write(3); //3-hotel
                    }

                    Building building = Buildings.readBuilding(in);
                    Buildings.serializeBuilding(building, out);
                    out.flush();

                    Object answer = in.readObject();
                    if (answer instanceof BuildingUnderArrestException) {
                        costs.println("Building under arrest");
                    } else {
                        costs.println(answer);
                    }
                }

                out.write(0); //0-finish job
            } finally {
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


