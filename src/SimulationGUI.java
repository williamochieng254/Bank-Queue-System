
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class SimulationGUI extends JFrame {

    private final JTextField customerField = new JTextField("100", 8);

    private final String[] columnNames = {
            "Customer ID",
            "IAT",
            "Arrival Time",
            "Service Time",
            "Service Start",
            "Waiting Time",
            "Departure Time",
            "Time in System"
    };

    private final DefaultTableModel tableModel =
            new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable resultsTable = new JTable(tableModel);

    private final JLabel customersLabel = new JLabel("Customers: -");
    private final JLabel averageWaitingLabel =
            new JLabel("Average waiting time: -");
    private final JLabel averageSystemLabel =
            new JLabel("Average time in system: -");
    private final JLabel maxQueueLabel =
            new JLabel("Maximum queue length: -");
    private final JLabel utilizationLabel =
            new JLabel("Teller utilization: -");
    private final JLabel totalTimeLabel =
            new JLabel("Total simulation time: -");

    public SimulationGUI() {
        setTitle("Bank Queue Simulation");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Simulation Input")
        );

        inputPanel.add(new JLabel("Number of customers:"));
        inputPanel.add(customerField);

        JButton runButton = new JButton("Run Simulation");
        inputPanel.add(runButton);

        add(inputPanel, BorderLayout.NORTH);

        // Simulation results table
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultsTable.setRowHeight(24);
        resultsTable.setFillsViewportHeight(true);
        resultsTable.setAutoCreateRowSorter(true);

        int[] columnWidths = {90, 75, 100, 100, 105, 100, 110, 105};

        for (int i = 0; i < columnWidths.length; i++) {
            resultsTable.getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(columnWidths[i]);
        }

        JScrollPane tableScrollPane = new JScrollPane(resultsTable);
        tableScrollPane.setBorder(
                BorderFactory.createTitledBorder("Simulation Table")
        );

        add(tableScrollPane, BorderLayout.CENTER);

        // Statistics panel
        JPanel statisticsPanel = new JPanel(new GridLayout(2, 3, 15, 10));
        statisticsPanel.setBorder(
                BorderFactory.createTitledBorder("Queue Statistics")
        );
        statisticsPanel.setPreferredSize(new Dimension(1000, 110));

        statisticsPanel.add(customersLabel);
        statisticsPanel.add(averageWaitingLabel);
        statisticsPanel.add(averageSystemLabel);
        statisticsPanel.add(maxQueueLabel);
        statisticsPanel.add(utilizationLabel);
        statisticsPanel.add(totalTimeLabel);

        add(statisticsPanel, BorderLayout.SOUTH);

        runButton.addActionListener(e -> runSimulation());
    }

    private void runSimulation() {
        int numberOfCustomers;

        try {
            numberOfCustomers =
                    Integer.parseInt(customerField.getText().trim());

            if (numberOfCustomers < 1 || numberOfCustomers > 10000) {
                JOptionPane.showMessageDialog(
                        this,
                        "Enter a number between 1 and 10000.",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid whole number.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Random random = new Random();
        BankQueueSimulation simulation = new BankQueueSimulation();

        double arrivalTime = 0;

        for (int i = 1; i <= numberOfCustomers; i++) {
            double interarrivalTime;

            if (i == 1) {
                interarrivalTime = 0;
            } else {
                // Uniformly distributed between 1 and 5 minutes
                interarrivalTime = 1 + random.nextDouble() * 4;
                arrivalTime += interarrivalTime;
            }

            // Uniformly distributed between 1 and 6 minutes
            double serviceTime = 1 + random.nextDouble() * 5;

            Customer customer = new Customer(
                    i,
                    interarrivalTime,
                    arrivalTime,
                    serviceTime
            );

            simulation.addCustomer(customer);
        }

        simulation.runSimulation();

        // Clear previous results
        tableModel.setRowCount(0);

        // Populate the simulation table
        for (Customer customer : simulation.getCustomers()) {
            tableModel.addRow(new Object[]{
                    customer.getCustomerId(),
                    format(customer.getInterarrivalTime()),
                    format(customer.getArrivalTime()),
                    format(customer.getServiceTime()),
                    format(customer.getServiceStartTime()),
                    format(customer.getWaitingTime()),
                    format(customer.getDepartureTime()),
                    format(customer.getTimeInSystem())
            });
        }

        // Update queue statistics
        customersLabel.setText(
                "Customers: " + simulation.getCustomers().size()
        );

        averageWaitingLabel.setText(
                "Average waiting time: "
                        + format(simulation.getAverageWaitingTime()) + " min"
        );

        averageSystemLabel.setText(
                "Average time in system: "
                        + format(simulation.getAverageTimeInSystem()) + " min"
        );

        maxQueueLabel.setText(
                "Maximum queue length: "
                        + simulation.getMaxQueueLength()
        );

        utilizationLabel.setText(
                "Teller utilization: "
                        + format(simulation.getTellerUtilization()) + "%"
        );

        totalTimeLabel.setText(
                "Total simulation time: "
                        + format(simulation.getElapsedTime()) + " min"
        );
    }

    private String format(double value) {
        return String.format("%.2f", value);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}
