import utilities.InputGetter;

public class ShopkeepCounter {
    private RecordKeeper recordKeeper;
    private Order currentOrder; // After an order is completed, if another order is created, make sure to start a fresh order

    public ShopkeepCounter() {

    }

    private void startRecordKeeper() {
        recordKeeper = new RecordKeeper();
    }

    // Begin prompting user for input from selections
    public void openShop() {
        startRecordKeeper();

        String userInput = InputGetter.getString("Enter a test response: ");
        System.out.println(userInput);

    }
}
