package CarServiceLab.Serving;

import CarServiceLab.Interfaces.Dineable;

public class PeopleDinner implements Dineable {
    private static int peopleServed = 0;

    @Override
    public synchronized void serveDinner(String carId) {
        peopleServed++;
        System.out.println("Serving dinner to people in car " + carId);
    }

    public static int getPeopleServed() {
        return peopleServed;
    }
}