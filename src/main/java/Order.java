import java.util.ArrayList;

public class Order {
    private final String customerName;
    private int orderNumber;
    private final ArrayList<Priceable> purchases = new ArrayList<>();

    public Order(String customerName, int orderNumber) {
        this.customerName = customerName;
        this.orderNumber = orderNumber;
    }

    public void addPurchase(Priceable purchase) {
        purchases.add(purchase);
    }

    public double getOrderPrice() {
        return purchases.stream()
                .mapToDouble(Priceable::getTotalPrice)
                .sum();
    }

    public void removePurchase(int index) {
        purchases.remove(index);
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void startNewOrder() {
        purchases.clear();
        this.orderNumber = this.orderNumber + 1;
    }

    public ArrayList<Priceable> getAllItemsInOrder() {
        return new ArrayList<>(purchases);
    }

    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void displayItemsInOrder() {
        int listedNumber = 1;
        for (Priceable item : purchases) {
            System.out.println(listedNumber + ") " + item);
            listedNumber++;
        }
    }
}


