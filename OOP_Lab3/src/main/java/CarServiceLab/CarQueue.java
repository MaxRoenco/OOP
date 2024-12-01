package CarServiceLab;

import java.util.concurrent.LinkedBlockingQueue;

public class CarQueue {
    private final LinkedBlockingQueue<Car> queue;
    private volatile boolean isComplete = false;

    public CarQueue() {
        this.queue = new LinkedBlockingQueue<>();
    }

    public void addCar(Car car) {
        if (car != null) {
            queue.offer(car);
        }
    }

    public Car takeCar() throws InterruptedException {
        // Use take() instead of poll() to wait for available elements
        while (!isComplete || !queue.isEmpty()) {
            Car car = queue.poll();
            if (car != null) {
                return car;
            }
            // Add a small sleep to prevent busy waiting
            Thread.sleep(100);
        }
        return null;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void setComplete() {
        isComplete = true;
    }

    public boolean isComplete() {
        return isComplete && isEmpty();
    }
}