import java.util.ArrayList;

public class Order {
    private final String customerName;
    private final int orderNumber;
    private final ArrayList<Priceable> purchases = new ArrayList<>();

    public Order(String customerName, int orderNumber) {
        this.customerName = customerName;
        this.orderNumber = orderNumber;
    }

    public void addPurchase(Priceable purchase) {
        purchases.add(purchase);
    }

    public double getTotalPrice() {
        return purchases.stream()
                .mapToDouble(Priceable::getTotalPrice)
                .sum();
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public ArrayList<Priceable> getAllItemsInOrder() {
        return new ArrayList<>(purchases);
    }

    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void displayItemsInOrder() {
        purchases.forEach(System.out::println);
    }
}


