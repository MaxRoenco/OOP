package CarServiceLab.Serving;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.Interfaces.Dineable;
import CarServiceLab.Interfaces.Refuelable;

public class ServiceProcessor {
    private final Dineable diningService;
    private final Refuelable refuelingService;

    public ServiceProcessor(Dineable diningService, Refuelable refuelingService) {
        this.diningService = diningService;
        this.refuelingService = refuelingService;
    }

    public void processCar(Car car) {
        if ("ELECTRIC".equals(car.getType())) {
            refuelingService.refuel(String.valueOf(car.getId()));
        } else if ("GAS".equals(car.getType())) {
            refuelingService.refuel(String.valueOf(car.getId()));
        }

        if (car.isDining()) {
            if ("PEOPLE".equals(car.getPassengers())) {
                diningService.serveDinner(String.valueOf(car.getId()));
            } else if ("ROBOTS".equals(car.getPassengers())) {
                diningService.serveDinner(String.valueOf(car.getId()));
            }
        }
    }
}