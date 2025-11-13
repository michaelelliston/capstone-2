import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordKeeper {

    private final static String PRICES_FILE_PATH = "src/main/resources/prices.csv";
    private final static String RECEIPTS_FILE_PATH = "src/main/resources/receipts/";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-d-MM_HHmmss");
    private HashMap<String, Double> itemPrices;

    public void readPricesFromRecords() {
        itemPrices = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRICES_FILE_PATH))) {

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

        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DATE_TIME_FORMATTER);

        try (PrintWriter writer = new PrintWriter(new FileWriter(RECEIPTS_FILE_PATH + formattedDateTime + ".csv"))) {

            ArrayList<Priceable> currentOrder = order.getAllItemsInOrder();

            String customerName = order.getCustomerName();
            int orderNumber = order.getOrderNumber();

            writer.write("\n\t" + customerName + "\n");
            writer.printf("\tOrder ID: %d\n\n", orderNumber);

            for (Priceable item : currentOrder) {

                writer.write("\t\t" + item.toString() + "\n");
                if (item instanceof Sword) {
                    writer.printf("\t\t\t %s: $%.2f\n", ((Sword) item).getSubType(), getReadPrice("Weapon", "Base", ((Sword) item).getSubType()));
                    writer.printf("\t\t\t %s: $%.2f\n", ((Sword) item).getMaterial(), getReadPrice("Material", ((Sword) item).getMaterial(), ((Sword) item).getSubType()));
                    if (((Sword) item).isInlaid) {
                        writer.printf("\t\t\t %s inlay: $%.2f\n\n", ((Sword) item).getGemType(), getReadPrice("Upgrade", ((Sword) item).getGemType(), "Inlay"));
                    } else {
                        writer.printf("\n\n");
                    }
                }
                if (item instanceof Axe) {
                    writer.printf("\t\t\t %s: $%.2f\n", ((Axe) item).getSubType(), getReadPrice("Weapon", "Base", ((Axe) item).getSubType()));
                    writer.printf("\t\t\t %s: $%.2f\n", ((Axe) item).getMaterial(), getReadPrice("Material", ((Axe) item).getMaterial(), ((Axe) item).getSubType()));
                    if (((Axe) item).isInlaid) {
                        writer.printf("\t\t\t %s inlay: $%.2f\n\n", ((Axe) item).getGemType(), getReadPrice("Upgrade", ((Axe) item).getGemType(), "Inlay"));
                    } else {
                        writer.printf("\n\n");
                    }
                }
                if (item instanceof Mace) {
                    writer.printf("\t\t\t %s: $%.2f\n", ((Mace) item).getSubType(), getReadPrice("Weapon", "Base", ((Mace) item).getSubType()));
                    writer.printf("\t\t\t %s: $%.2f\n", ((Mace) item).getMaterial(), getReadPrice("Material", ((Mace) item).getMaterial(), ((Mace) item).getSubType()));
                    if (((Mace) item).isInlaid) {
                        writer.printf("\t\t\t %s inlay: $%.2f\n\n", ((Mace) item).getGemType(), getReadPrice("Upgrade", ((Mace) item).getGemType(), "Inlay"));
                    } else {
                        writer.printf("\n\n");
                    }
                }
            }

            writer.printf("\t\tTotal: $%.2f", order.getTotalPrice());


        } catch (IOException e) {
            System.err.println("An error occurred while trying to write your receipt.");
        }
    }

    public int getOrderNumber() {

        File folder = new File(RECEIPTS_FILE_PATH);
        File[] receipts = folder.listFiles(((dir, name) -> name.toLowerCase().endsWith(".csv")));
        int orderNumber = (receipts == null) ? 1 : receipts.length + 1;
        return orderNumber;
    }
}
