import utilities.InputGetter;

public class ShopkeepCounter {
    private final RecordKeeper recordKeeper = new RecordKeeper();
    private Order currentOrder; // After an order is completed, if another order is created, make sure to start a fresh order

    private void startRecordKeeper() {
        recordKeeper.readPricesFromRecords();
    }

    public void greetCustomer() {
        String userName = InputGetter.getString("What's your name?\n");
        int orderNumber = recordKeeper.getOrderNumber();
        currentOrder = new Order(userName, orderNumber);
        startRecordKeeper();
        openShop();
    }

    // Begin prompting user for input from selections
    public void openShop() {

        int userInput = 0;
        while (!(userInput == 99)) {
            userInput = InputGetter.getInt("""
                    
                    \t1) Sword
                    \t2) Axe
                    \t3) Mace
                    \t8) Display Current Order
                    \t88) Checkout
                    \t99) Leave
                    
                    What do you want?
                    """);

            switch (userInput) {
                case 1 -> processWeaponCreationRequest("Sword");
                case 2 -> processWeaponCreationRequest("Axe");
                case 3 -> processWeaponCreationRequest("Mace");
                case 8 -> {
                    if (!currentOrder.getAllItemsInOrder().isEmpty()) {
                        processDisplayOrderRequest();
                    } else {
                        System.out.println("\nYou've got nothing in your order currently.");
                    }
                }
                case 88 -> {
                    if (!currentOrder.getAllItemsInOrder().isEmpty()) {
                        processOrderCheckoutRequest();
                    }
                    else {
                        System.out.println("\nYou've got nothing in your order currently.");
                    }
                }
            }
        }
    }

    private void processDisplayOrderRequest() {
        System.out.println();
        currentOrder.displayItemsInOrder();
        System.out.printf("\nYour order comes to a total of: $%.2f", currentOrder.getTotalPrice());
        InputGetter.getString("\n\n\tWake me up when you're ready to continue... Zzzzz...\n");
    }

    private void processOrderCheckoutRequest() {
        System.out.println();
        currentOrder.displayItemsInOrder();
        System.out.printf("\nYour order comes to a total of: $%.2f\n", currentOrder.getTotalPrice());
        int userInput = 0;
        while (!(userInput == 2) && !(userInput == 1)) {
            userInput = InputGetter.getInt("""
                    \n
                    \t1) Yes
                    \t2) No
                    
                    Do you accept this transaction?
                    """);

            switch (userInput) {
                case 1 -> recordKeeper.writeReceipt(currentOrder);
                case 2 -> System.out.println("\nRight, lets put a hold on that.");
                default -> System.out.println("\nInvalid selection, try again.");

            }
        }
    }

    private void processWeaponCreationRequest(String weaponType) {
        String weaponMaterial = "";
        String weaponSubType = "";
        String gemType = "";
        boolean isInlaid = false;
        int userInput = 0;

        switch (weaponType) {
            case "Sword" -> {
                while (!(userInput == 1) && (!(userInput == 2)) && (!(userInput == 3))) {


                    System.out.printf("\n\t1) Shortsword: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Shortsword"));
                    System.out.printf("\n\t2) Longsword: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Longsword"));
                    System.out.printf("\n\t3) Greatsword: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Greatsword"));


                    userInput = InputGetter.getInt("\n\nWhat kind?\n");

                    switch (userInput) {
                        case 1 -> weaponSubType = "Shortsword";
                        case 2 -> weaponSubType = "Longsword";
                        case 3 -> weaponSubType = "Greatsword";
                        default -> System.out.println("Invalid selection, try again.");
                    }
                }

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 2)) && (!(userInput == 3)) && (!(userInput == 4))) {

                    System.out.printf("\n\t1) Iron: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Iron", weaponSubType));
                    System.out.printf("\n\t2) Steel: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Steel", weaponSubType));
                    System.out.printf("\n\t3) Mithral: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Mithral", weaponSubType));
                    System.out.printf("\n\t4) Adamantine: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Adamantine", weaponSubType));

                    userInput = InputGetter.getInt("\n\nWhat material do you want?\n");

                    switch (userInput) {
                        case 1 -> weaponMaterial = "Iron";
                        case 2 -> weaponMaterial = "Steel";
                        case 3 -> weaponMaterial = "Mithral";
                        case 4 -> weaponMaterial = "Adamantine";
                        default -> System.out.println("Invalid selection, try again.");
                    }
                }

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 2))) {

                    userInput = InputGetter.getInt("""
                            
                            \t1) Yes
                            \t2) No
                            
                            Want a gem inlaid into it?
                            """);

                    if (userInput == 1) {
                        isInlaid = true;
                        gemType = processGemInlayRequest();
                    } else if (userInput == 2) {
                        gemType = "None";
                    } else {
                        System.out.println("Invalid selection, try again.");
                    }
                }

                Sword sword = new Sword(recordKeeper.getReadPrice("Weapon", "Base",
                        weaponSubType), weaponMaterial, isInlaid, gemType, weaponType, weaponSubType, recordKeeper);

                System.out.printf("\nThis would cost you $%.2f, shall I add it to your order?", sword.getTotalPrice());

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 99))) {
                    userInput = InputGetter.getInt("""
                            \n
                            1) Yes
                            99) No
                            """);

                    if (userInput == 1) {
                        currentOrder.addPurchase(sword);
                    } else if (userInput == 99) {
                        System.out.println("\nRight, scratch that.");
                    } else {
                        System.out.println("\nInvalid selection, try again.");
                    }
                }
            }

            case "Axe" -> {
                while (!(userInput == 1) && (!(userInput == 2)) && (!(userInput == 3))) {


                    System.out.printf("\n\t1) Hatchet: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Hatchet"));
                    System.out.printf("\n\t2) Broadaxe: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Broadaxe"));
                    System.out.printf("\n\t3) Greataxe: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Greataxe"));


                    userInput = InputGetter.getInt("\n\nWhat kind?\n");

                    switch (userInput) {
                        case 1 -> weaponSubType = "Hatchet";
                        case 2 -> weaponSubType = "Broadaxe";
                        case 3 -> weaponSubType = "Greataxe";
                        default -> System.out.println("Invalid selection, try again.");
                    }
                }

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 2)) && (!(userInput == 3)) && (!(userInput == 4))) {

                    System.out.printf("\n\t1) Iron: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Iron", weaponSubType));
                    System.out.printf("\n\t2) Steel: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Steel", weaponSubType));
                    System.out.printf("\n\t3) Mithral: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Mithral", weaponSubType));
                    System.out.printf("\n\t4) Adamantine: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Adamantine", weaponSubType));

                    userInput = InputGetter.getInt("\n\nWhat material do you want?\n");

                    switch (userInput) {
                        case 1 -> weaponMaterial = "Iron";
                        case 2 -> weaponMaterial = "Steel";
                        case 3 -> weaponMaterial = "Mithral";
                        case 4 -> weaponMaterial = "Adamantine";
                        default -> System.out.println("Invalid selection, try again.");
                    }
                }

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 2))) {

                    userInput = InputGetter.getInt("""
                            
                            \t1) Yes
                            \t2) No
                            
                            Want a gem inlaid into it?
                            """);

                    if (userInput == 1) {
                        isInlaid = true;
                        gemType = processGemInlayRequest();
                    } else if (userInput == 2) {
                        gemType = "None";
                    } else {
                        System.out.println("Invalid selection, try again.");
                    }
                }

                Axe axe = new Axe(recordKeeper.getReadPrice("Weapon", "Base", weaponSubType), weaponMaterial, isInlaid, gemType, weaponType, weaponSubType, recordKeeper);
                System.out.printf("\nThis would cost you $%.2f, shall I add it to your order?", axe.getTotalPrice());

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 99))) {
                    userInput = InputGetter.getInt("""
                            \n
                            1) Yes
                            99) No
                            """);

                    if (userInput == 1) {
                        currentOrder.addPurchase(axe);
                    } else if (userInput == 99) {
                        System.out.println("\nRight, scratch that.");
                    } else {
                        System.out.println("\nInvalid selection, try again.");
                    }
                }
            }

            case "Mace" -> {

                while (!(userInput == 1) && (!(userInput == 2)) && (!(userInput == 3))) {


                    System.out.printf("\n\t1) Battle Mace: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Battle Mace"));
                    System.out.printf("\n\t2) Warhammer: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Warhammer"));
                    System.out.printf("\n\t3) Maul: Base price of $%.2f", recordKeeper.getReadPrice("Weapon", "Base", "Maul"));


                    userInput = InputGetter.getInt("\n\nWhat kind?\n");

                    switch (userInput) {
                        case 1 -> weaponSubType = "Battle Mace";
                        case 2 -> weaponSubType = "Warhammer";
                        case 3 -> weaponSubType = "Maul";
                        default -> System.out.println("Invalid selection, try again.");
                    }
                }

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 2)) && (!(userInput == 3)) && (!(userInput == 4))) {

                    System.out.printf("\n\t1) Iron: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Iron", weaponSubType));
                    System.out.printf("\n\t2) Steel: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Steel", weaponSubType));
                    System.out.printf("\n\t3) Mithral: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Mithral", weaponSubType));
                    System.out.printf("\n\t4) Adamantine: Additional fee of $%.2f", recordKeeper.getReadPrice("Material", "Adamantine", weaponSubType));

                    userInput = InputGetter.getInt("\n\nWhat material do you want?\n");

                    switch (userInput) {
                        case 1 -> weaponMaterial = "Iron";
                        case 2 -> weaponMaterial = "Steel";
                        case 3 -> weaponMaterial = "Mithral";
                        case 4 -> weaponMaterial = "Adamantine";
                        default -> System.out.println("Invalid selection, try again.");
                    }
                }

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 2))) {

                    userInput = InputGetter.getInt("""
                            
                            \t1) Yes
                            \t2) No
                            
                            Want a gem inlaid into it?
                            """);

                    if (userInput == 1) {
                        isInlaid = true;
                        gemType = processGemInlayRequest();
                    } else if (userInput == 2) {
                        gemType = "None";
                    } else {
                        System.out.println("Invalid selection, try again.");
                    }
                }

                Mace mace = new Mace(recordKeeper.getReadPrice("Weapon", "Base", weaponSubType), weaponMaterial, isInlaid, gemType, weaponType, weaponSubType, recordKeeper);
                System.out.printf("\nThis would cost you $%.2f, shall I add it to your order?", mace.getTotalPrice());

                userInput = 0;
                while (!(userInput == 1) && (!(userInput == 99))) {
                    userInput = InputGetter.getInt("""
                            \n
                            1) Yes
                            99) No
                            """);

                    if (userInput == 1) {
                        currentOrder.addPurchase(mace);
                    } else if (userInput == 99) {
                        System.out.println("\nRight, scratch that.");
                    } else {
                        System.out.println("\nInvalid selection, try again.");
                    }
                }

            }
        }
    }

    public String processGemInlayRequest() {
        String selection = "";
        int userInput = 0;

        while (!(userInput == 99) && (!(userInput == 1)) && (!(userInput == 2)) && (!(userInput == 3)) && (!(userInput == 4)) && (!(userInput == 5)) && (!(userInput == 6))) {

            System.out.printf("\n1) Amethyst: $%.2f", recordKeeper.getReadPrice("Upgrade", "Amethyst", "Inlay"));
            System.out.printf("\n2) Garnet: $%.2f", recordKeeper.getReadPrice("Upgrade", "Garnet", "Inlay"));
            System.out.printf("\n3) Emerald: $%.2f", recordKeeper.getReadPrice("Upgrade", "Emerald", "Inlay"));
            System.out.printf("\n4) Citrine: $%.2f", recordKeeper.getReadPrice("Upgrade", "Citrine", "Inlay"));
            System.out.printf("\n5) Sapphire: $%.2f", recordKeeper.getReadPrice("Upgrade", "Sapphire", "Inlay"));
            System.out.printf("\n6) Diamond: $%.2f", recordKeeper.getReadPrice("Upgrade", "Diamond", "Inlay"));
            System.out.println("\n99) Nevermind, nothing.\n");

            userInput = InputGetter.getInt("Which gem would you like?\n");


            switch (userInput) {
                case 1 -> selection = "Amethyst";
                case 2 -> selection = "Garnet";
                case 3 -> selection = "Emerald";
                case 4 -> selection = "Citrine";
                case 5 -> selection = "Sapphire";
                case 6 -> selection = "Diamond";
                case 99 -> selection = "None";
                default -> System.out.println("Invalid selection, try again.");
            }
        }
        return selection;
    }
}

