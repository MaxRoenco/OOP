package CarServiceLab.QueueImplementation;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.Interfaces.Queue;

public class CarQueue {
    private final Queue<Car> queue;

    public CarQueue(Queue<Car> queueImplementation) {
        this.queue = queueImplementation;
    }

    public CarQueue() {
        this.queue = new ArrayQueue<Car>();
    }

    public void addCar(Car car) {
        if (car != null) {
            queue.enqueue(car);
        }
    }

    public Car takeCar() throws InterruptedException {
        return queue.dequeue();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void setComplete() {
        queue.setComplete();
    }

    public boolean isComplete() {
        return queue.isComplete();
    }
}