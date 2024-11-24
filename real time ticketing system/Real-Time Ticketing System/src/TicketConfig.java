import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class TicketConfig {

    // Parameters
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Transient Scanner to avoid serialization
    private transient Scanner scanner = new Scanner(System.in);

    // Getters and Setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Configure system parameters
    public void configureSystemParameters() {
        System.out.println("=============================================");
        System.out.println("----------Ticket Configuration Setup---------");
        System.out.println("=============================================");

        // Input and set total tickets
        setTotalTickets(validatePositiveInteger("Enter the total number of tickets: "));

        // Input and set ticket release rate
        setTicketReleaseRate(validatePositiveInteger("Enter the ticket release rate (tickets per second): "));

        // Input and set customer retrieval rate
        setCustomerRetrievalRate(validatePositiveInteger("Enter the customer retrieval rate (tickets per second): "));

        // Input and set maximum ticket capacity
        setMaxTicketCapacity(validatePositiveInteger("Enter the maximum ticket capacity: "));

        // Logical constraint check
        if (getMaxTicketCapacity() < getTotalTickets()) {
            System.out.println("Warning: Maximum ticket capacity is less than total tickets. Consider adjusting.");
        }

        // Summary of configuration
        System.out.println("=============================================");
        System.out.println("-----------Configuration Summary-------------");
        System.out.println("=============================================");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Maximum Ticket Capacity: " + maxTicketCapacity);
    }

    // Helper method for input validation
    private int validatePositiveInteger(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Error: Value must be a positive integer. Please try again.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a positive integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Save all configurations to a JSON file
    public static void saveAllToJson(String filename, List<TicketConfig> configurations) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(configurations, writer);
            System.out.println("All configurations saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving configurations: " + e.getMessage());
        }
    }

    // Load all configurations from a JSON file
    public static List<TicketConfig> loadAllFromJson(String filename) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TicketConfig>>() {}.getType();
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Error loading configurations: " + e.getMessage());
        }
        return null;
    }
}
