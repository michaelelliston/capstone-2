import java.util.ArrayList;

public class Order {
    private String customerName;
    private int orderNumber;
    private ArrayList<Priceable> purchases = new ArrayList<Priceable>();

    public Order(String customerName, int orderNumber) {
        this.customerName = customerName;
        this.orderNumber = orderNumber;
    }

    public void addPurchase(Priceable purchase) {
        purchases.add(purchase);
    }

    public double getTotalPrice() {
        double totalPrice = purchases.stream()
                .mapToDouble(Priceable::getPrice)
                .sum();
        return totalPrice;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public ArrayList<Priceable> getAllItemsInOrder() {
        return new ArrayList<Priceable>(purchases);
    }

    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {

        return "";
    }
}


