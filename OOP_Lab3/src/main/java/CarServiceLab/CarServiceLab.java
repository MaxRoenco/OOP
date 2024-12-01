package CarServiceLab;

import java.io.File;

public class CarServiceLab {
    public static void main(String[] args) {
        try {
            File dir = new File("D:/HomeWork/SecondYear/OOP/OOP/OOP_Lab3/src/main/resources/queue");
            System.out.println("Directory exists: " + dir.exists());
            System.out.println("Directory path: " + dir.getAbsolutePath());
            CarQueue carQueue = new CarQueue();
            CarStats carStats = new CarStats();
            CarServiceStation station = new CarServiceStation(carQueue, carStats);

            // Load cars from the directory
            String queueDir = "D:/HomeWork/SecondYear/OOP/OOP/OOP_Lab3/src/main/resources/queue";  // Updated path
            station.loadCarsFromDirectory(queueDir);

            // Start processing
            Thread serviceThread = new Thread(station);
            serviceThread.start();

            // Wait for processing to complete
            serviceThread.join();

            // Print final statistics
            System.out.println("\nFinal Statistics:");
            carStats.printStats();

        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}