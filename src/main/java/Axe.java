public class Axe extends MeleeWeapon {

    protected String axeType;
    protected String weaponType;
    protected RecordKeeper records;

    public Axe(double basePrice, String material, boolean isInlaid, String gemType, String weaponType, String axeType, RecordKeeper records) {
        super(basePrice, material, isInlaid, gemType);
        this.weaponType = weaponType;
        this.axeType = axeType;
        this.records = records;
    }

    public String getMaterial() {
        return this.material;
    }

    public String getSubType() {
        return this.axeType;
    }

    @Override
    public double getBasePrice() {
        return this.basePrice;
    }

    @Override
    public double getTotalPrice() {
        return records.getReadPrice("Material", this.getMaterial(), this.axeType) + this.getBasePrice() + (isInlaid ? records.getReadPrice("Upgrade", this.gemType, "Inlay") : 0);
    }

    @Override
    public String toString() {
        return String.format("%s made of %s with an inlay of %s, priced at: $%.2f", this.getSubType(), this.getMaterial(), this.getGemType(), this.getTotalPrice());
    }
}
