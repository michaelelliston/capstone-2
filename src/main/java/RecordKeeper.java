import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class RecordKeeper {

    private final String pricesFileName = "src/main/resources/records.json";
    private HashMap<String, Double> itemPrices;

    public void readPricesFromRecords() {
        itemPrices = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pricesFileName))) {

            String line = bufferedReader.readLine(); // Skips over the header of the prices file, while also attempting to make sure the line variable won't be null.
            while (line != null) {

                line = bufferedReader.readLine().trim();

                if (line == null || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    String category = parts[0].trim();
                    String material = parts[1].trim();
                    String type = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());

                    String key = category + "|" + material + "|" + type;
                    itemPrices.put(key, price);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Sorry, I could not find the records you requested.");
        } catch (IOException e) {
            System.err.println("I apologize, something went horribly wrong while searching for those records.");
        }
    }

    public double getReadPrice(String category, String material, String type) {

        String key = category + "|" + material + "|" + type;

        return itemPrices.getOrDefault(key, 0.0); // Returns the key's value within the hashmap, but if it doesn't exist, returns 0.0 instead.
    }
}
