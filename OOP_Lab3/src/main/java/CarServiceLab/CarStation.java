package CarServiceLab;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.Interfaces.Dineable;
import CarServiceLab.Interfaces.Refuelable;
import CarServiceLab.QueueImplementation.CarQueue;

public class CarStation {
    private Dineable diningService;
    private Refuelable refuelingService;
    private CarQueue carQueue;

    public CarStation(Dineable diningService, Refuelable refuelingService, CarQueue carQueue) {
        this.diningService = diningService;
        this.refuelingService = refuelingService;
        this.carQueue = carQueue;
    }

    public void serveCars() throws InterruptedException {
        while (!carQueue.isEmpty()) {
            Car currentCar = carQueue.takeCar();

            if (currentCar.isDining()) {
                diningService.serveDinner(String.valueOf(currentCar.getId()));
            }

            refuelingService.refuel(String.valueOf(currentCar.getId()));
        }
    }
    public void addCarStation(Car car) {
        carQueue.addCar(car);
    }
    public boolean isQueueEmpty() {
        return carQueue.isEmpty();
    }
}
