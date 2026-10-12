
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random(42);
        List<Customer> customers = new ArrayList<>();

        double arrivalTime = 0;
        int numberOfCustomers = 20;

        for (int i = 1; i <= numberOfCustomers; i++) {
            arrivalTime += 1 + random.nextInt(5);
            double serviceTime = 2 + random.nextInt(6);

            customers.add(
                    new Customer(i, arrivalTime, serviceTime)
            );
        }

        int[] tellerOptions = {1, 2, 3};

        System.out.println("\n========== EXPERIMENT COMPARISON ==========");
        System.out.printf(
                "%-10s %-15s %-20s %-20s%n",
                "Tellers", "Customers", "Average Wait (min)",
                "Teller Utilization"
        );

        System.out.println(
                "---------------------------------------------------------------"
        );

        double baselineWait = 0;

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

            double averageWait =
                    simulation.getAverageWaitingTime();

            double utilization =
                    simulation.getTellerUtilization();

            if (i == 0) {
                baselineWait = averageWait;
            }

            System.out.printf(
                    "%-10d %-15d %-20.2f %-19.2f%%%n",
                    tellerCount,
                    customers.size(),
                    averageWait,
                    utilization
            );

            if (i > 0 && baselineWait > 0) {
                double reduction =
                        (baselineWait - averageWait) / baselineWait * 100;

                System.out.printf(
                        "Waiting-time reduction versus one teller: %.2f%%%n",
                        reduction
                );
            }
        }
    }
}
