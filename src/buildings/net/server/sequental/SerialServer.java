package buildings.net.server.sequental;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;
import exception.BuildingUnderArrestException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SerialServer {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static ObjectInputStream in; // чтение из сокета
    private static ObjectOutputStream out; //запись в сокет

    public static void main(String[] args) throws IOException {
        try {
            try {
                serverSocket = new ServerSocket(7);
                while (true) {
                    clientSocket = serverSocket.accept();
                    //клиент подключился
                    try {
                        out = new ObjectOutputStream(clientSocket.getOutputStream());
                        in = new ObjectInputStream(clientSocket.getInputStream());
                        Building building;
                        try {
                            building = Buildings.deserializeBuilding(in);
                        } catch (Exception e) {
                            break;
                        }
                        System.out.println("Building: " + building);
                        String answer;
                        try {
                            answer = String.format("%.3f", cost(building));
                            out.writeObject(answer);
                            out.flush();
                        } catch (BuildingUnderArrestException e) {
                            out.writeObject(new BuildingUnderArrestException());
                            out.flush();
                        }
                    } finally {
                        in.close();
                        out.close();
                        clientSocket.close();
                    }
                }
            } finally {
                serverSocket.close();
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

    private static double cost(Building buildings) throws BuildingUnderArrestException {
        double result = 0.0;
        if (isArrested()) {
            throw new BuildingUnderArrestException();

        }
        if (buildings instanceof Dwelling) {
            result = buildings.getSpacesSquare() * 1000;
        }
        if (buildings instanceof OfficeBuilding) {
            result = buildings.getSpacesSquare() * 1500;
        }
        if (buildings instanceof Hotel) {
            result = buildings.getSpacesSquare() * 2000;
        }

        return result;
    }
}
