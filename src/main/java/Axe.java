public class Axe extends MeleeWeapon {

    protected String axeType;

    public Axe(String material, boolean isInlaid, String axeType) {
        super(material, isInlaid);
        this.axeType = axeType;
    }
}
