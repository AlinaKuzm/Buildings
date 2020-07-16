package buildings.net.server.sequental;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFactory;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFactory;
import exception.BuildingUnderArrestException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BinaryServer {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static InputStream in; // чтение из сокета
    private static BufferedWriter out; //запись в сокет

    public static void main(String[] args) throws IOException {
        try {
            try {
                serverSocket = new ServerSocket(7);
                while (true) {
                    clientSocket = serverSocket.accept();
                    //клиент подключился
                    try {
                        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream in = new DataInputStream(clientSocket.getInputStream());


                        while (!clientSocket.isClosed()) {
                            int type = in.read();
                            if (type == 1) {
                                Buildings.setBuildingFactory(new DwellingFactory());
                            } else if (type == 2) {
                                Buildings.setBuildingFactory(new OfficeFactory());
                            } else if (type == 3) {
                                Buildings.setBuildingFactory(new HotelFactory());
                            } else if (type == 0) {
                                break;
                            }

                            Building building = Buildings.inputBuilding(in);
                            System.out.println("Building: " + building);

                            String answer = "";
                            try {
                                answer = String.format("%.3f", cost(building));
                            } catch (BuildingUnderArrestException e) {
                                answer = "Arrested";
                            } finally {
                                out.writeUTF(answer + "\n");
                                out.flush();
                                System.out.println("Ответ на запрос клиента: " + answer);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    in.close();
                    out.close();
                    clientSocket.close();
                }
                //serverSocket.close();
                //System.out.println("Finish");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private static boolean isArrested() {
        Random random = new Random();
        int r = random.nextInt(100);
        if (r > 10) {
            return true;
        } else
            return false;
    }

    public static double cost(Building buildings) throws BuildingUnderArrestException {
        double result = 0.0;
        if (isArrested()) {
            throw new BuildingUnderArrestException();

        }
        if (buildings instanceof Dwelling) {
            result =  buildings.getSpacesSquare() * 1000;
        }
        if (buildings instanceof OfficeBuilding) {
            result = buildings.getSpacesSquare() * 1500;
        }
        if (buildings instanceof Hotel) {
            result =  buildings.getSpacesSquare() * 2000;
        }

        return result;
    }
}


