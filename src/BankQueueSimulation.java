
import java.util.ArrayList;
import java.util.List;

public class BankQueueSimulation {

    private final List<Customer> customers = new ArrayList<>();

    private double averageWaitingTime;
    private double averageTimeInSystem;
    private double tellerUtilization;
    private double elapsedTime;
    private int maxQueueLength;

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void runSimulation() {

        double totalWaitingTime = 0;
        double totalTimeInSystem = 0;
        double totalServiceTime = 0;
        double tellerAvailableTime = 0;
        double lastDeparture = 0;

        for (Customer customer : customers) {

            double serviceStart = Math.max(
                    customer.getArrivalTime(),
                    tellerAvailableTime
            );

            double departure = serviceStart
                    + customer.getServiceTime();

            customer.setServiceStartTime(serviceStart);
            customer.setDepartureTime(departure);

            tellerAvailableTime = departure;
            lastDeparture = departure;

            totalWaitingTime += customer.getWaitingTime();
            totalTimeInSystem += customer.getTimeInSystem();
            totalServiceTime += customer.getServiceTime();
        }

        averageWaitingTime = customers.isEmpty()
                ? 0 : totalWaitingTime / customers.size();

        averageTimeInSystem = customers.isEmpty()
                ? 0 : totalTimeInSystem / customers.size();

        elapsedTime = lastDeparture;

        tellerUtilization = elapsedTime > 0
                ? totalServiceTime / elapsedTime * 100
                : 0;

        calculateMaximumQueueLength();
    }

    private void calculateMaximumQueueLength() {

        maxQueueLength = 0;

        for (Customer current : customers) {

            int waiting = 0;
            double time = current.getArrivalTime();

            for (Customer customer : customers) {

                if (customer.getArrivalTime() <= time
                        && customer.getServiceStartTime() > time) {
                    waiting++;
                }
            }

            maxQueueLength = Math.max(maxQueueLength, waiting);
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageTimeInSystem() {
        return averageTimeInSystem;
    }

    public double getTellerUtilization() {
        return tellerUtilization;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }
}
