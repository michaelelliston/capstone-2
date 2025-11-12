public class Sword extends MeleeWeapon {

    protected String weaponType;
    protected String swordType;
    protected RecordKeeper records;

    public Sword(double basePrice, String material, boolean isInlaid, String gemType, String weaponType, String swordType, RecordKeeper records) {
        super(basePrice, material, isInlaid, gemType);
        this.weaponType = weaponType;
        this.swordType = swordType;
        this.records = records;
    }

    public String getMaterial() {
        return this.material;
    }

    public String getType() {
        return this.weaponType;
    }

    public String getSubType() {
        return this.swordType;
    }


    @Override
    public double getBasePrice() {
        return this.basePrice;
    }

    @Override
    public double getTotalPrice() {
        return records.getReadPrice("Material", this.getMaterial(), this.swordType) + this.getBasePrice() + (isInlaid ? records.getReadPrice("Upgrade", this.gemType, "Inlay") : 0);
    }

    @Override
    public String toString() {
        return String.format("%s made of %s with an inlay of %s, priced at: $%.2f", this.getSubType(), this.getMaterial(), this.getGemType(), this.getTotalPrice());
    }


}
