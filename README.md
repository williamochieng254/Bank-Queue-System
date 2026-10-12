
# Bank Queue Simulation System

## 1. Project Overview

The Bank Queue Simulation System is a Java application
developed for the Simulations and Modelling unit.

It simulates customers arriving at a bank, waiting for
service, and being served by available bank tellers.

The system uses a First-In, First-Out (FIFO) service
discipline.

## 2. Objectives

- Simulate customer arrivals and service durations.
- Calculate customer waiting times.
- Determine average waiting time.
- Estimate maximum queue length.
- Calculate teller utilization.
- Compare one-teller and two-teller configurations.

## 3. Technologies Used

- Java
- IntelliJ IDEA
- Git and GitHub

## 4. Simulation Assumptions

- Customers arrive at different times.
- Interarrival times range from 1 to 5 minutes.
- Service durations range from 2 to 7 minutes.
- The model initially assumes customers are served
  in arrival order.
- The experiments use the same customer dataset.
- The random seed is fixed to make experiments reproducible.

## 5. Main Classes

- Main: Generates customers and runs the experiments.
- Customer: Stores customer arrival, service, and
  departure information.
- BankQueueSimulation: Processes customers and
  calculates performance statistics.

## 6. Performance Measures

- Total customers served
- Average customer waiting time
- Maximum queue length
- Teller utilization
- Elapsed simulation time
- Waiting-time reduction between configurations

## 7. How to Run

1. Open the project in IntelliJ IDEA.
2. Ensure a compatible JDK is configured.
3. Open Main.java.
4. Run the main() method.
5. Review the one-teller and two-teller results.

## 8. Future Improvements

- Graphical user interface
- Queue-length visualization
- Experiments with more tellers
- Additional arrival and service-time distributions
- Exporting simulation results
