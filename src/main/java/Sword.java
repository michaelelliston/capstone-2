public class Sword extends MeleeWeapon {

    protected String swordType;

    public Sword(String material, boolean isInlaid, String swordType) {
        super(material, isInlaid);
        this.swordType = swordType;
    }

    @Override
    public double getPrice() {

        return 0;
    }


}
