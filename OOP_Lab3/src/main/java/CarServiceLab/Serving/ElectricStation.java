package CarServiceLab.Serving;

import CarServiceLab.Interfaces.Refuelable;

public class ElectricStation implements Refuelable {
    private static int electricCarsServed = 0;

    @Override
    public synchronized void refuel(String carId) {
        electricCarsServed++;
        System.out.println("Refueling electric car " + carId);
    }

    public static int getElectricCarsServed() {
        return electricCarsServed;
    }
}