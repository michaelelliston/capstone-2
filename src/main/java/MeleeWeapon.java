public abstract class MeleeWeapon extends Weapon {
    // TODO: Create fields for MeleeWeapon
    protected boolean isInlaid;

    public MeleeWeapon(String material, boolean isInlaid) {
        super(material);
        this.isInlaid = isInlaid;
    }
}
