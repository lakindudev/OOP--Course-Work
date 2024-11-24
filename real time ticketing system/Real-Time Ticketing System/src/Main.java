import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // List to store all configurations
        List<TicketConfig> configurations = new ArrayList<>();

        String command;

        System.out.println("\nCommands: start | stop");
        do {
            System.out.print("Enter command: ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start":
                    TicketConfig ticketConfig = new TicketConfig();
                    ticketConfig.configureSystemParameters();
                    configurations.add(ticketConfig); // Save the configuration to the list
                    break;
                case "stop":
                    System.out.println("Stopping the program...");
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        } while (!command.equals("stop"));

        // Save all configurations to a single JSON file
        TicketConfig.saveAllToJson("configurations.json", configurations);

        // Load configurations from the JSON file
        List<TicketConfig> loadedConfigurations = TicketConfig.loadAllFromJson("configurations.json");
        if (loadedConfigurations != null) {
            System.out.println("\nLoaded Configurations:");
            for (int i = 0; i < loadedConfigurations.size(); i++) {
                TicketConfig config = loadedConfigurations.get(i);
                System.out.println("\nConfiguration " + (i + 1) + ":");
                System.out.println("Total Tickets: " + config.getTotalTickets());
                System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
                System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
                System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
            }
        }
    }
}
