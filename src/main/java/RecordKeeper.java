import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordKeeper {

    private final String pricesFileName = "src/main/resources/prices.csv";
    private final String receiptsFilePath = "src/main/resources/receipts";
    private HashMap<String, Double> itemPrices;

    public void readPricesFromRecords() {
        itemPrices = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pricesFileName))) {

            bufferedReader.readLine(); // Skips over the header of the prices file
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.startsWith("#")) { // Skips to the next line if a line starts with "#", allowing non-price information to be skipped.
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

        return itemPrices.get(key); // Returns the key's value within the hashmap
    }

    public void writeReceipt(Order order) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(receiptsFilePath + "/testreceipt.csv"))) {

            ArrayList<Priceable> currentOrder = order.getAllItemsInOrder();

            for (Priceable item : currentOrder) {

                writer.write(item.toString() + "\n");
                if (item instanceof Sword) {
                    writer.printf("\t %s: $%.2f\n", ((Sword) item).getSubType(), getReadPrice("Weapon", "Base", ((Sword) item).getSubType()));
                    writer.printf("\t %s: $%.2f\n", ((Sword) item).getMaterial(), getReadPrice("Material", ((Sword) item).getMaterial(), ((Sword) item).getSubType()));
                    if (((Sword) item).isInlaid) {
                        writer.printf("\t %s inlay: $%.2f\n", ((Sword) item).getGemType(), getReadPrice("Upgrade", ((Sword) item).getGemType(), "Inlay"));
                    }
                }
            }


        } catch (IOException e) {
            System.err.println("An error occurred while trying to write your receipt.");
        }
    }
}
