
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random random = new Random();

        BankQueueSimulation simulation =
                new BankQueueSimulation();

        double arrivalTime = 0;

        for (int i = 1; i <= 100; i++) {

            double interarrivalTime;

            if (i == 1) {
                interarrivalTime = 0;
            } else {
                interarrivalTime = 1 + random.nextDouble() * 4;
                arrivalTime += interarrivalTime;
            }

            double serviceTime =
                    1 + random.nextDouble() * 5;

            Customer customer = new Customer(
                    i,
                    interarrivalTime,
                    arrivalTime,
                    serviceTime
            );

            simulation.addCustomer(customer);
        }

        simulation.runSimulation();

        System.out.println("BANK QUEUE SIMULATION");
        System.out.println("Customers served: "
                + simulation.getCustomers().size());

        System.out.printf(
                "Average waiting time: %.2f minutes%n",
                simulation.getAverageWaitingTime()
        );

        System.out.printf(
                "Average time in system: %.2f minutes%n",
                simulation.getAverageTimeInSystem()
        );

        System.out.println("Maximum queue length: "
                + simulation.getMaxQueueLength());

        System.out.printf(
                "Teller utilization: %.2f%%%n",
                simulation.getTellerUtilization()
        );
    }
}
