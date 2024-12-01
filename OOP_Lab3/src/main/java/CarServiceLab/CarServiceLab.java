package CarServiceLab;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.AboutCars.CarStats;
import CarServiceLab.Interfaces.Queue;
import CarServiceLab.QueueImplementation.ArrayQueue;
import CarServiceLab.QueueImplementation.CarQueue;
import CarServiceLab.QueueImplementation.CircularQueue;
import CarServiceLab.QueueImplementation.LinkedBlockingQueueAdapter;
import CarServiceLab.Serving.GasStation;
import CarServiceLab.Serving.PeopleDinner;

import java.io.IOException;

public class CarServiceLab {
    private static int useTask = 1;
    public static void main(String[] args) {
        try {
            testWithQueue("Array Queue", new ArrayQueue<Car>());

//            testWithQueue("Circular Queue", new CircularQueue<Car>());
//
//            testWithQueue("Linked Queue", new LinkedBlockingQueueAdapter<Car>());

        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testWithQueue(String queueType, Queue<Car> queueImpl) throws Exception {
        System.out.println("\nTesting with " + queueType);
        CarQueue carQueue = new CarQueue(queueImpl);
        CarStats carStats = new CarStats();

        if(useTask == 5){
            task5(queueType, carQueue, carStats);
        } else {
            anotherTask(queueType, carQueue, carStats);
        }
    }

    private static void anotherTask(String queueType, CarQueue carQueue, CarStats carStats) throws IOException, InterruptedException {
        CarServiceStation station = new CarServiceStation(carQueue, carStats);

        String queueDir = "D:/HomeWork/SecondYear/OOP/OOP/OOP_Lab3/src/main/resources/queue";
        station.loadCarsFromDirectory(queueDir);

        Thread serviceThread = new Thread(station);
        serviceThread.start();

        serviceThread.join();

        System.out.println("\nFinal Statistics for " + queueType + ":");
        carStats.printStats();
    }

    private static void task5(String queueType, CarQueue carQueue, CarStats carStats) throws InterruptedException {
        CarStation carStation = new CarStation(
                new PeopleDinner(),
                new GasStation(),
                carQueue
        );

        String queueDir = "D:/HomeWork/SecondYear/OOP/OOP/OOP_Lab3/src/main/resources/queue";
        CarServiceScheduler scheduler = new CarServiceScheduler(queueDir, carStation);
        scheduler.startScheduling();
        Thread.sleep(5 * 60 * 1000);
        scheduler.stop();

        System.out.println("\nFinal Statistics for " + queueType + ":");
        carStats.printStats();
    }
}