
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random(42);
        List<Customer> customers = new ArrayList<>();

        double arrivalTime = 0;
        int numberOfCustomers = 20;

        // Generate the customer data once.
        for (int i = 1; i <= numberOfCustomers; i++) {
            arrivalTime += 1 + random.nextInt(5);

            double serviceTime = 2 + random.nextInt(6);

            customers.add(
                    new Customer(i, arrivalTime, serviceTime)
            );
        }

        // Run experiments with one, two, and three tellers.
        int[] tellerOptions = {1, 2, 3};
        double[] averageWaits = new double[tellerOptions.length];

        for (int i = 0; i < tellerOptions.length; i++) {

            int tellerCount = tellerOptions[i];

            BankQueueSimulation simulation =
                    new BankQueueSimulation(tellerCount);

            for (Customer customer : customers) {
                simulation.addCustomer(
                        new Customer(
                                customer.getCustomerId(),
                                customer.getArrivalTime(),
                                customer.getServiceTime()
                        )
                );
            }

            simulation.runSimulation();

            averageWaits[i] =
                    simulation.getAverageWaitingTime();
        }

        // Display a comparison table.
        System.out.println("\n========== EXPERIMENT COMPARISON ==========");
        System.out.printf("%-15s %-25s%n",
                "Number of Tellers", "Average Waiting Time");
        System.out.println("------------------------------------------");

        for (int i = 0; i < tellerOptions.length; i++) {
            System.out.printf("%-15d %-25.2f%n",
                    tellerOptions[i], averageWaits[i]);
        }

        // Calculate improvement compared with one teller.
        System.out.println("\n========== WAITING TIME REDUCTION ==========");

        double baseline = averageWaits[0];

        for (int i = 1; i < tellerOptions.length; i++) {
            if (baseline > 0) {
                double reduction =
                        (baseline - averageWaits[i]) / baseline * 100;

                System.out.printf(
                        "%d teller(s): %.2f%% reduction%n",
                        tellerOptions[i], reduction
                );
            } else {
                System.out.printf(
                        "%d teller(s): baseline waiting time is zero%n",
                        tellerOptions[i]
                );
            }
        }
    }
}
