import utilities.InputGetter;

public class ShopkeepCounter {
    private RecordKeeper recordKeeper = new RecordKeeper();
    private Order currentOrder; // After an order is completed, if another order is created, make sure to start a fresh order

    public ShopkeepCounter() {

    }

    private void startRecordKeeper() {
        recordKeeper.readPricesFromRecords();
    }

    // Begin prompting user for input from selections
    public void openShop() {
        startRecordKeeper();

        int userInput = InputGetter.getInt("""
                \n
                \t1) Sword
                \t2) Axe
                
                
                What are you buying?""");

        switch (userInput) {
            case 1 -> processSwordCreationRequest();
        }


    }

    private void processSwordCreationRequest() {

        String swordMaterial = "";
        String swordType = "";
        boolean isInlaid = false;
        int userInput = 0;

        while (!(userInput == 1) || (!(userInput == 2)) || (!(userInput == 3))) {

            userInput = InputGetter.getInt("""
                    \n
                    \t1) Shortsword
                    \t2) Longsword
                    \t3) Greatsword
                    
                    
                    What kind?""");

            switch (userInput) {
                case 1 -> swordType = "Shortsword";
                case 2 -> swordType = "Longsword";
                case 3 -> swordType = "Greatsword";
            }
        }
        userInput = InputGetter.getInt("""
                \n
                \t1) Iron
                \t2) Steel
                \t3) Mithral
                \t4) Adamantine
                
                What material?""");

        switch (userInput) {
            case 1 -> swordMaterial = "Iron";
            case 2 -> swordMaterial = "Steel";
            case 3 -> swordMaterial = "Mithral";
            case 4 -> swordMaterial = "Adamantine";
        }

        userInput = InputGetter.getInt("""
                \n
                \t1) Yes
                \t2) No
                
                Want a gem inlaid into it?""");

        if (userInput == 1) {
            isInlaid = true;
            String gem = processGemInlayRequest();
        }

        Sword sword = new Sword(swordMaterial, isInlaid, swordType);


    }

    public String processGemInlayRequest() {
        String selection = "";
        int userInput = 0;

        while (!(userInput == 99)) {

            System.out.printf("\n1) Amethyst: $%.2f", recordKeeper.getReadPrice("Upgrade", "Inlay", "Amethyst"));
            System.out.printf("\n2) Garnet: $%.2f", recordKeeper.getReadPrice("Upgrade", "Inlay", "Garnet"));
            System.out.printf("\n3) Emerald: $%.2f", recordKeeper.getReadPrice("Upgrade", "Inlay", "Emerald"));
            System.out.printf("\n4) Citrine: $%.2f", recordKeeper.getReadPrice("Upgrade", "Inlay", "Citrine"));
            System.out.printf("\n5) Sapphire: $%.2f", recordKeeper.getReadPrice("Upgrade", "Inlay", "Sapphire"));
            System.out.printf("\n6) Diamond: $%.2f", recordKeeper.getReadPrice("Upgrade", "Inlay", "Diamond"));
            System.out.println("99) Nevermind, nothing.");

            userInput = InputGetter.getInt("Which gem would you like?");


            switch (userInput) {
                case 1 -> selection = "Amethyst";
                case 2 -> selection = "Garnet";
                case 3 -> selection = "Emerald";
                case 4 -> selection = "Citrine";
                case 5 -> selection = "Sapphire";
                case 6 -> selection = "Diamond";
            }

        }
        return selection;
    }
}

