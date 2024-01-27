package practice;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Base class for vehicles
class Vehicle {
    private String vehicleID;
    private String model;
    private String color;
    private double rentalRate;
    private boolean available;

    public Vehicle(String vehicleID, String model, String color, double rentalRate) {
        this.vehicleID = vehicleID;
        this.model = model;
        this.color = color;
        this.rentalRate = rentalRate;
        this.available = true;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        if (available) {
            available = false;
        }
    }

    public void returnVehicle() {
        if (!available) {
            available = true;
        }
    }

    public void displayInfo() {
        System.out.println("Vehicle ID: " + vehicleID);
        System.out.println("Model: " + model);
        System.out.println("Color: " + color);
        System.out.println("Rental Rate: $" + rentalRate + " per day");
        System.out.println("Availability: " + (available ? "Available" : "Not Available"));
    }
}

// Car class extending Vehicle
class Car extends Vehicle {
    private int numSeats;

    public Car(String vehicleID, String model, String color, double rentalRate, int numSeats) {
        super(vehicleID, model, color, rentalRate);
        this.numSeats = numSeats;
    }

    public int getNumSeats() {
        return numSeats;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of Seats: " + numSeats);
    }
}

// Customer class
class Customer {
    private String customerID;
    private String name;
    private String phoneNumber;

    public Customer(String customerID, String name, String phoneNumber) {
        this.customerID = customerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void displayInfo() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + name);
        System.out.println("Phone Number: " + phoneNumber);
    }
}

// Rental class for handling rental operations
// Rental class for handling rental operations
class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int rentalDays;
    private double totalCost;

    public Rental(Customer customer, Vehicle vehicle, int rentalDays) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
        calculateTotalCost();
        vehicle.rent();
    }

    private void calculateTotalCost() {
        totalCost = rentalDays * vehicle.getRentalRate();
    }

    public void returnVehicle() {
        vehicle.returnVehicle();
        System.out.println("Vehicle returned by customer: " + customer.getName());
        System.out.println("Total Cost: $" + totalCost);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void displayRentalInfo() {
        System.out.println("Rental Information:");
        customer.displayInfo();
        vehicle.displayInfo();
        System.out.println("Rental Days: " + rentalDays);
        System.out.println("Total Cost: $" + totalCost);
    }
}

// CarRentalSystem class for managing the overall system
class CarRentalSystem {
    private List<Vehicle> availableVehicles;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        availableVehicles = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        availableVehicles.add(vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void displayAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : availableVehicles) {
            vehicle.displayInfo();
            System.out.println("----------------------");
        }
    }

    public void displayCustomers() {
        System.out.println("Customers:");
        for (Customer customer : customers) {
            customer.displayInfo();
            System.out.println("----------------------");
        }
    }

    public void rentVehicle(Customer customer, Vehicle vehicle, int rentalDays) {
        if (availableVehicles.contains(vehicle) && customers.contains(customer)) {
            Rental rental = new Rental(customer, vehicle, rentalDays);
            rentals.add(rental);
            availableVehicles.remove(vehicle);
            System.out.println("Vehicle rented successfully!");
        } else {
            System.out.println("Unable to rent the vehicle. Invalid customer or vehicle.");
        }
    }

    public void returnVehicle(Customer customer, Vehicle vehicle) {
        Rental rental = findRental(customer, vehicle);
        if (rental != null) {
            rental.returnVehicle();
            rentals.remove(rental);
            availableVehicles.add(vehicle);
        } else {
            System.out.println("No matching rental found for the customer and vehicle.");
        }
    }

    private Rental findRental(Customer customer, Vehicle vehicle) {
        for (Rental rental : rentals) {
            if (rental.getCustomer().equals(customer) && rental.getVehicle().equals(vehicle)) {
                return rental;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Create vehicles
        Car car1 = new Car("V001", "Toyota Camry", "Blue", 50.0, 5);
        Car car2 = new Car("V002", "Honda Accord", "Red", 45.0, 4);

        // Create customers
        Customer customer1 = new Customer("C001", "John Doe", "123-456-7890");
        Customer customer2 = new Customer("C002", "Jane Smith", "987-654-3210");

        // Create car rental system
        CarRentalSystem carRentalSystem = new CarRentalSystem();

        // Add vehicles and customers to the system
        carRentalSystem.addVehicle(car1);
        carRentalSystem.addVehicle(car2);
        carRentalSystem.addCustomer(customer1);
        carRentalSystem.addCustomer(customer2);

        // Display available vehicles and customers
        System.out.println("Available Vehicles:");
        carRentalSystem.displayAvailableVehicles();
        System.out.println("Customers:");
        carRentalSystem.displayCustomers();

        // Rent a vehicle
        System.out.println("Enter the customer ID:");
        Scanner scanner = new Scanner(System.in);
        String customerID = scanner.nextLine();
        Customer renter = null;
        for (Customer customer : carRentalSystem.getCustomers()) {
            if (customer.getCustomerID().equalsIgnoreCase(customerID)) {
                renter = customer;
                break;
            }
        }

        if (renter != null) {
            System.out.println("Enter the vehicle ID to rent:");
            String vehicleID = scanner.nextLine();
            Vehicle rentedVehicle = null;
            for (Vehicle vehicle : carRentalSystem.getAvailableVehicles()) {
                if (vehicle.getVehicleID().equalsIgnoreCase(vehicleID)) {
                    rentedVehicle = vehicle;
                    break;
                }
            }

            if (rentedVehicle != null) {
                System.out.println("Enter the number of rental days:");
                int rentalDays = scanner.nextInt();
                carRentalSystem.rentVehicle(renter, rentedVehicle, rentalDays);
                System.out.println("Rental successful!");
            } else {
                System.out.println("Invalid vehicle ID. Rental failed.");
            }
        } else {
            System.out.println("Invalid customer ID. Rental failed.");
        }

        // Display available vehicles after renting
        System.out.println("Available Vehicles after renting:");
        carRentalSystem.displayAvailableVehicles();

        // Return a vehicle
        System.out.println("Enter the customer ID for returning the vehicle:");
        String returnCustomerID = scanner.next();
        Customer returnCustomer = null;
        for (Customer customer : carRentalSystem.getCustomers()) {
            if (customer.getCustomerID().equalsIgnoreCase(returnCustomerID)) {
                returnCustomer = customer;
                break;
            }
        }

        if (returnCustomer != null) {
            System.out.println("Enter the vehicle ID to return:");
            String returnVehicleID = scanner.next();
            Vehicle returnedVehicle = null;
            for (Vehicle vehicle : carRentalSystem.getAvailableVehicles()) {
                if (vehicle.getVehicleID().equalsIgnoreCase(returnVehicleID)) {
                    returnedVehicle = vehicle;
                    break;
                }
            }

            if (returnedVehicle != null) {
                carRentalSystem.returnVehicle(returnCustomer, returnedVehicle);
                System.out.println("Vehicle returned successfully!");
            } else {
                System.out.println("Invalid vehicle ID. Return failed.");
            }
        } else {
            System.out.println("Invalid customer ID. Return failed.");
        }

        // Display available vehicles after returning
        System.out.println("Available Vehicles after returning:");
        carRentalSystem.displayAvailableVehicles();

        scanner.close();
    }

    public List<Vehicle> getAvailableVehicles() {
        return availableVehicles;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
