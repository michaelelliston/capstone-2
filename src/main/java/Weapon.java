public abstract class Weapon implements Priceable {

    protected double basePrice;
    protected String material;

    public Weapon(double basePrice, String material) {
        this.material = material;
        this.basePrice = basePrice;
    }
}
