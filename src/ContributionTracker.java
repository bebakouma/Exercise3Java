/*
 * Explanation Video: https://www.loom.com/share/5564243d8bca4317a6888bc5cc6bd51a
 * Execution Video: https://www.loom.com/share/b1505d55e8a247e5a6efc545951fda5c
 */



import java.io.*;
import java.nio.file.*;
import java.text.NumberFormat;
import java.util.*;

public class ContributionTracker {

    //  Constants 
    private static final double GOAL = 10_000_000.00;  // Fundraising goal
    private static final String INPUT_FILE = "input.in"; // must exist
    private static final String OUTPUT_FILE = "results.out";

    // Main Method 
    public static void main(String[] args) {
        try {
            List<Double> donations = readContributions(INPUT_FILE);

            int count = 0;
            double total = 0.0;
            double max = Double.NEGATIVE_INFINITY;
            double min = Double.POSITIVE_INFINITY;

            // Loop through contributions until goal is reached
            for (double amount : donations) {
                

                total += amount;
                count++;

                if (amount > max) max = amount;
                if (amount < min) min = amount;
                if (total >= GOAL) break;
            }

            if (total < GOAL) {
                System.out.printf(" Goal NOT reached (only %s collected).%n", money(total));
            } else System.out.println("Goal reached!");

            double average = (count == 0) ? 0 : total / count;

            writeResults(count, max, min, average, total);
            System.out.println("Summary written to " + OUTPUT_FILE);

        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }

    // Helper Method 1: Read Contributions from File 
    private static List<Double> readContributions(String path) throws IOException {
        List<Double> list = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(path))) {
            line = line.trim();
            if (!line.isEmpty()) {
                list.add(Double.parseDouble(line));
            }
        }
        return list;
    }

    // Helper Method 2: Write Results to Output File 
    private static void writeResults(int count, double max, double min, double avg, double total) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            out.printf("It took %d contributions to reach the goal.%n", count);
            out.printf("The maximum contribution received was %s.%n", money(max));
            out.printf("The minimum contribution received was %s.%n", money(min));
            out.printf("The average contribution amount was %s.%n", money(avg));
            out.printf("A total of %s was collected.%n", money(total));
        }
    }

    //  Helper Method 3: Format Number as US Currency 
    private static String money(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }
}
