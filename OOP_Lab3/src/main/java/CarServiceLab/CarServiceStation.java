package CarServiceLab;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CarServiceStation implements Runnable {
    private final CarQueue carQueue;
    private final CarStats carStats;
    private final ObjectMapper objectMapper;
    private volatile boolean running = true;

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
                    // Simulate processing time
                    Thread.sleep((long) (Math.random() * 2500 + 500));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

        // Sort files to ensure consistent processing order
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