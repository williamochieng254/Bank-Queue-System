
public class SimulationValidation {

    public static void main(String[] args) {

        BankQueueSimulation bank = new BankQueueSimulation();

        Customer c1 = new Customer(1, 0, 0, 4);
        Customer c2 = new Customer(2, 2, 2, 3);
        Customer c3 = new Customer(3, 1, 3, 5);

        bank.addCustomer(c1);
        bank.addCustomer(c2);
        bank.addCustomer(c3);

        bank.runSimulation();

        check("Customer 1 waiting time", c1.getWaitingTime(), 0);
        check("Customer 2 waiting time", c2.getWaitingTime(), 2);
        check("Customer 3 waiting time", c3.getWaitingTime(), 4);

        check("Average waiting time", bank.getAverageWaitingTime(), 2);

        check("Customer 1 departure", c1.getDepartureTime(), 4);
        check("Customer 2 departure", c2.getDepartureTime(), 7);
        check("Customer 3 departure", c3.getDepartureTime(), 12);

        check("Customer 1 time in system", c1.getTimeInSystem(), 4);
        check("Customer 2 time in system", c2.getTimeInSystem(), 5);
        check("Customer 3 time in system", c3.getTimeInSystem(), 9);

        System.out.println("\nALL VALIDATION TESTS PASSED!");
    }

    private static void check(
            String testName,
            double actual,
            double expected) {

        if (Math.abs(actual - expected) > 0.000001) {
            throw new IllegalStateException(
                    testName + " failed. Expected "
                            + expected + ", but got " + actual
            );
        }

        System.out.println(testName + ": PASSED");
    }
}
