public abstract class Weapon implements Priceable {

    protected String type;
    protected String material;

    public Weapon(String material) {
        this.material = material;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
