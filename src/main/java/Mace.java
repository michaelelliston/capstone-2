public class Mace extends MeleeWeapon {

    protected String weaponType;
    protected String maceType;
    protected RecordKeeper records;

    public Mace(double basePrice, String material, boolean isInlaid, String gemType, String weaponType, String maceType, RecordKeeper records) {
        super(basePrice, material, isInlaid, gemType);
        this.weaponType = weaponType;
        this.maceType = maceType;
        this.records = records;
    }

    public String getMaterial() {
        return this.material;
    }

    public String getSubType() {
        return this.maceType;
    }

    @Override
    public double getBasePrice() {
        return this.basePrice;
    }

    @Override
    public double getTotalPrice() {
        return records.getReadPrice("Material", this.getMaterial(), this.maceType) + this.getBasePrice() + (isInlaid ? records.getReadPrice("Upgrade", this.gemType, "Inlay") : 0);
    }

    @Override
    public String toString() {
        return String.format("%s made of %s with an inlay of %s, priced at: $%.2f", this.getSubType(), this.getMaterial(), this.getGemType(), this.getTotalPrice());
    }
}
