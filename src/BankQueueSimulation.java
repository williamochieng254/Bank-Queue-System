
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BankQueueSimulation {

    private final List<Customer> customers = new ArrayList<>();
    private final int numberOfTellers;

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

        // Each value represents when a teller becomes available.
        PriorityQueue<Double> tellers = new PriorityQueue<>();

        for (int i = 0; i < numberOfTellers; i++) {
            tellers.add(0.0);
        }

        double totalWaitingTime = 0;
        double totalServiceTime = 0;
        double firstArrival = customers.isEmpty()
                ? 0 : customers.get(0).getArrivalTime();

        System.out.println("\n===== " + numberOfTellers
                + " TELLER(S) =====");

        // Schedule customers in arrival order.
        for (Customer customer : customers) {
            double tellerAvailable = tellers.poll();

            double serviceStart = Math.max(
                    customer.getArrivalTime(),
                    tellerAvailable
            );

            double departure = serviceStart
                    + customer.getServiceTime();

            customer.setServiceStartTime(serviceStart);
            customer.setDepartureTime(departure);

            tellers.add(departure);

            totalWaitingTime += customer.getWaitingTime();
            totalServiceTime += customer.getServiceTime();

            System.out.printf(
                    "Customer %d | Arrival: %.1f | Start: %.1f"
                            + " | Wait: %.1f | Departure: %.1f%n",
                    customer.getCustomerId(),
                    customer.getArrivalTime(),
                    serviceStart,
                    customer.getWaitingTime(),
                    departure
            );
        }

        // Measure customers waiting at each arrival time.
        int maxQueueLength = 0;

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

        double lastDeparture = 0;

        for (Customer customer : customers) {
            lastDeparture = Math.max(
                    lastDeparture,
                    customer.getDepartureTime()
            );
        }

        int served = customers.size();

        double averageWaiting = served > 0
                ? totalWaitingTime / served : 0;

        double elapsedTime = served > 0
                ? lastDeparture - firstArrival : 0;

        double utilization = elapsedTime > 0
                ? totalServiceTime / (numberOfTellers * elapsedTime) * 100
                : 0;

        System.out.println("\n--- Performance Summary ---");
        System.out.println("Tellers: " + numberOfTellers);
        System.out.println("Customers served: " + served);
        System.out.printf("Average waiting time: %.2f minutes%n",
                averageWaiting);
        System.out.println("Maximum queue length: " + maxQueueLength);
        System.out.printf("Teller utilization: %.2f%%%n", utilization);
        System.out.printf("Elapsed simulation time: %.2f minutes%n",
                elapsedTime);
    }

    public double getAverageWaitingTime() {
        double total = 0;

        for (Customer customer : customers) {
            total += customer.getWaitingTime();
        }

        return customers.isEmpty() ? 0 : total / customers.size();
    }

}
