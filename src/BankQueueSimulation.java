
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BankQueueSimulation {

    private final List<Customer> customers = new ArrayList<>();
    private final int numberOfTellers;

    private double averageWaitingTime;
    private double tellerUtilization;
    private double elapsedTime;
    private int maxQueueLength;

    public BankQueueSimulation(int numberOfTellers) {
        if (numberOfTellers < 1) {
            throw new IllegalArgumentException(
                    "At least one teller is required."
            );
        }

        this.numberOfTellers = numberOfTellers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void runSimulation() {
        customers.sort(
                Comparator.comparingDouble(Customer::getArrivalTime)
        );

        PriorityQueue<Double> tellers = new PriorityQueue<>();

        for (int i = 0; i < numberOfTellers; i++) {
            tellers.add(0.0);
        }

        double totalWaitingTime = 0;
        double totalServiceTime = 0;
        double lastDeparture = 0;

        System.out.println("\n===== " + numberOfTellers
                + " TELLER(S) =====");

        for (Customer customer : customers) {
            double availableTime = tellers.poll();

            double startTime = Math.max(
                    customer.getArrivalTime(),
                    availableTime
            );

            double departureTime =
                    startTime + customer.getServiceTime();

            customer.setServiceStartTime(startTime);
            customer.setDepartureTime(departureTime);

            tellers.add(departureTime);

            totalWaitingTime += customer.getWaitingTime();
            totalServiceTime += customer.getServiceTime();

            lastDeparture = Math.max(
                    lastDeparture, departureTime
            );

            System.out.printf(
                    "Customer %d | Arrival: %.1f | Start: %.1f"
                            + " | Wait: %.1f | Departure: %.1f%n",
                    customer.getCustomerId(),
                    customer.getArrivalTime(),
                    startTime,
                    customer.getWaitingTime(),
                    departureTime
            );
        }

        averageWaitingTime = customers.isEmpty()
                ? 0 : totalWaitingTime / customers.size();

        elapsedTime = lastDeparture;

        tellerUtilization = elapsedTime > 0
                ? totalServiceTime
                / (numberOfTellers * elapsedTime) * 100
                : 0;

        maxQueueLength = 0;

        for (Customer arriving : customers) {
            int waiting = 0;
            double time = arriving.getArrivalTime();

            for (Customer customer : customers) {
                if (customer.getArrivalTime() <= time
                        && customer.getServiceStartTime() > time) {
                    waiting++;
                }
            }

            maxQueueLength = Math.max(maxQueueLength, waiting);
        }

        System.out.println("\n--- Performance Summary ---");
        System.out.println("Customers served: " + customers.size());
        System.out.printf("Average waiting time: %.2f minutes%n",
                averageWaitingTime);
        System.out.println("Maximum queue length: " + maxQueueLength);
        System.out.printf("Teller utilization: %.2f%%%n",
                tellerUtilization);
        System.out.printf("Elapsed simulation time: %.2f minutes%n",
                elapsedTime);
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public double getTellerUtilization() {
        return tellerUtilization;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }
}
