package CarServiceLab;

import CarServiceLab.AboutCars.Car;
import CarServiceLab.AboutCars.CarStats;
import CarServiceLab.QueueImplementation.CarQueue;
import CarServiceLab.Serving.*;

class CarServiceSemaphore {
    private final CarStation electricPeopleStation;
    private final CarStation electricRobotStation;
    private final CarStation gasPeopleStation;
    private final CarStation gasRobotStation;
    private final CarStats carStats;

    public CarServiceSemaphore() {
        this.carStats = new CarStats();

        electricPeopleStation = new CarStation(
                new PeopleDinner(),
                new ElectricStation(),
                new CarQueue()
        );

        electricRobotStation = new CarStation(
                new RobotDinner(),
                new ElectricStation(),
                new CarQueue()
        );

        gasPeopleStation = new CarStation(
                new PeopleDinner(),
                new GasStation(),
                new CarQueue()
        );

        gasRobotStation = new CarStation(
                new RobotDinner(),
                new GasStation(),
                new CarQueue()
        );
    }

    public void routeCar(Car car) throws InterruptedException {
        carStats.updateStats(car);

        CarStation targetStation;
        if ("ELECTRIC".equals(car.getType())) {
            if ("PEOPLE".equals(car.getPassengers())) {
                targetStation = electricPeopleStation;
            } else {
                targetStation = electricRobotStation;
            }
        } else {
            if ("PEOPLE".equals(car.getPassengers())) {
                targetStation = gasPeopleStation;
            } else {
                targetStation = gasRobotStation;
            }
        }

        targetStation.addCarStation(car);
        targetStation.serveCars();
    }

    public CarStats getStats() {
        return carStats;
    }
}