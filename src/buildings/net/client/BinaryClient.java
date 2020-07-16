package buildings.net.client;

import buildings.Buildings;
import buildings.interfaces.Building;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BinaryClient {
    private static final String BUILDINGS = "Buildings.txt";
    private static final String TYPES_OF_BUILDINGS = "Types.txt";
    private static final String COSTS = "Costs.txt";

    private static Socket clientSocket = null;
    //private static PrintWriter out = null;
   private static BufferedReader in = null;

    public static void main(String[] args) throws IOException {
        try {
            FileReader buildings = new FileReader(BUILDINGS);
            Scanner types = new Scanner(new FileReader(TYPES_OF_BUILDINGS));
            PrintWriter costs = new PrintWriter(new FileWriter(COSTS));

            try {
                clientSocket = new Socket("localhost", 7);

               // DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
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
                    Buildings.outputBuilding(building, out);
                    out.flush();

                    String answer = in.readLine();
                    costs.println(answer);
                }
                out.write(0); //0-finish job
                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

