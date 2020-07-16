package buildings.net.server.parallel;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialServer {
    private static ExecutorService execute = Executors.newFixedThreadPool(2);
    //ExecutorService управляет очередью в памяти и планирует отправленные задачи в зависимости от доступности потока.
    private static class Server implements Runnable {
        private Socket clientSocket;

        public Server(Socket client) throws IOException {
            this.clientSocket = client;
        }

        @Override
        public void run() {

            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Building building = Buildings.deserializeBuilding(in);
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

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        while (!server.isClosed()) {
            Socket client = server.accept();
            execute.execute(new SerialServer.Server(client));
        }
        execute.shutdown(); //позволяющий останавливать все потоки исполнения, находящиеся под управлением экземпляра ExecutorService.
    }
}






