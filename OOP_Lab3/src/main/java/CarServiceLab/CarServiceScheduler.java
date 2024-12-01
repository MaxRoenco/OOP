package CarServiceLab;

import CarServiceLab.AboutCars.Car;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.*;
import java.util.concurrent.*;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class CarServiceScheduler {
    private final String queueDirectory;
    private final CarStation carStation;
    private final ScheduledExecutorService scheduler;
    private final Semaphore stationSemaphore;
    private final int READ_INTERVAL = 5;
    private final int SERVE_INTERVAL = 7;
    private volatile boolean isRunning = true;

    public CarServiceScheduler(String queueDirectory, CarStation carStation) {
        this.queueDirectory = queueDirectory;
        this.carStation = carStation;
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.stationSemaphore = new Semaphore(1);
    }

    public void startScheduling() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (stationSemaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                    try {
                        checkQueueDirectory();
                    } finally {
                        stationSemaphore.release();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, READ_INTERVAL, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (stationSemaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                    try {
                        if (!carStation.isQueueEmpty()) {
                            carStation.serveCars();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        stationSemaphore.release();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, SERVE_INTERVAL, TimeUnit.SECONDS);
    }

    private void checkQueueDirectory() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(queueDirectory), "Car*.json")) {
            for (Path file : stream) {
                try {
                    Car car = new ObjectMapper().readValue(file.toFile(), Car.class);
                    carStation.addCarStation(car);
                    Files.delete(file);
                } catch (IOException e) {
                    System.err.println("Error processing file " + file + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }
    }

    public void stop() {
        isRunning = false;
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}