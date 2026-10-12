
public class Customer {
    private int customerId;
    private double arrivalTime;
    private double serviceTime;
    private double serviceStartTime;
    private double departureTime;

    public Customer(int customerId, double arrivalTime, double serviceTime) {
        this.customerId = customerId;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(double serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public double getWaitingTime() {
        return serviceStartTime - arrivalTime;
    }
}
