package CarServiceLab;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.AboutCars.CarStats;
import CarServiceLab.Interfaces.Dineable;
import CarServiceLab.Interfaces.Refuelable;
import CarServiceLab.QueueImplementation.CarQueue;
import CarServiceLab.Serving.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CarServiceStation implements Runnable {
    private final CarQueue carQueue;
    private final CarStats carStats;
    private final ObjectMapper objectMapper;
    private volatile boolean running = true;
    private int useTask = 2;

    public CarServiceStation(CarQueue carQueue, CarStats carStats) {
        this.carQueue = carQueue;
        this.carStats = carStats;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            while (running && !Thread.currentThread().isInterrupted()) {
                Car car = carQueue.takeCar();
                if (car == null && carQueue.isComplete()) {
                    break;
                }
                if (car != null) {
                    System.out.println("Processing: " + car);
                    carStats.updateStats(car);
                    Thread.sleep((long) (Math.random() * 2500 + 500));
                }
                if( car != null) {
                    switch(useTask){
                        case 2:
                            processTask2(car);
                            break;
                        case 3:
                            processCarStationTask3(car);
                            break;
                        case 4:
                            processTask4(car);
                            break;
                    }
                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void processTask4(Car car) {
        CarServiceSemaphore semaphore = new CarServiceSemaphore();
        try {
            semaphore.routeCar(car);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void processTask2(Car car) {
        ServiceProcessor processor = new ServiceProcessor(
                "PEOPLE".equals(car.getPassengers()) ? new PeopleDinner() : new RobotDinner(),
                "ELECTRIC".equals(car.getType()) ? new ElectricStation() : new GasStation()
        );
        processor.processCar(car);
    }

    private void processCarStationTask3(Car car) throws InterruptedException {

        Dineable diningService = "PEOPLE".equals(car.getPassengers()) ?
                new PeopleDinner() : new RobotDinner();
        Refuelable refuelingService = "ELECTRIC".equals(car.getType()) ?
                new ElectricStation() : new GasStation();

        CarStation station = new CarStation(diningService, refuelingService, carQueue);
        station.addCarStation(car);
        station.serveCars();

        Thread.sleep((long) (Math.random() * 2500 + 500));
    }

    public void stop() {
        running = false;
    }

    public void loadCarsFromDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Directory not found: " + directoryPath);
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".json"));
        if (files == null || files.length == 0) {
            System.out.println("No JSON files found in directory: " + directoryPath);
            carQueue.setComplete();
            return;
        }

        Arrays.sort(files);

        for (File file : files) {
            try {
                Car car = objectMapper.readValue(file, Car.class);
                carQueue.addCar(car);
                System.out.println("Loaded car: " + car);
            } catch (IOException e) {
                System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
            }
        }
        carQueue.setComplete();
    }
}