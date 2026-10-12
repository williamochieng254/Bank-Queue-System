
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random(42);
        List<Customer> customers = new ArrayList<>();

        double arrivalTime = 0;
        int numberOfCustomers = 20;

        // Generate one shared customer dataset.
        for (int i = 1; i <= numberOfCustomers; i++) {
            arrivalTime += 1 + random.nextInt(5);

            double serviceTime = 2 + random.nextInt(6);

            customers.add(
                    new Customer(i, arrivalTime, serviceTime)
            );
        }

        // Experiment 1: One teller.
        BankQueueSimulation oneTeller =
                new BankQueueSimulation(1);

        for (Customer customer : customers) {
            oneTeller.addCustomer(customer);
        }

        oneTeller.runSimulation();

        // Experiment 2: Two tellers.
        BankQueueSimulation twoTellers =
                new BankQueueSimulation(2);

        for (Customer customer : customers) {
            twoTellers.addCustomer(
                    new Customer(
                            customer.getCustomerId(),
                            customer.getArrivalTime(),
                            customer.getServiceTime()
                    )
            );
        }

        twoTellers.runSimulation();

        double oneTellerWait = oneTeller.getAverageWaitingTime();
        double twoTellerWait = twoTellers.getAverageWaitingTime();

        System.out.println("\n===== TELLER COMPARISON =====");
        System.out.printf("One teller average wait: %.2f minutes%n",
                oneTellerWait);
        System.out.printf("Two tellers average wait: %.2f minutes%n",
                twoTellerWait);

        if (oneTellerWait > 0) {
            double reduction =
                    ((oneTellerWait - twoTellerWait) / oneTellerWait) * 100;

            System.out.printf("Waiting time reduction: %.2f%%%n",
                    reduction);
        } else {
            System.out.println(
                    "Waiting time reduction cannot be calculated."
            );
        }

    }
}
