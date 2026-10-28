public class Mage extends Unite {
    private int magic;

    Mage(String name) {
        super(name);
    }

    Mage(String name, int maxHp) {
        this(name, maxHp, maxHp);
    }

    Mage(String name, int maxHp, int magic) {
        this(name, maxHp, maxHp, magic);
    }

    Mage(String name, int currentHp, int maxHp, int magic) {
        this(name, currentHp, maxHp, 0, 0, magic);
    }

    Mage(String name, int currentHp, int maxHp, int row, int col, int magic) {
        super(name, currentHp, maxHp, row, col);
        this.magic = magic;
    }

    public int getMagic() {
        return magic;
    }

    @Override
    public void afficherFiche() {
        super.afficherFiche();
        System.out.println("Magie : " + magic);
    }

    @Override
    public String typeUnite() {
        return "Mage";
    }

    @Override
    public void annoncerAction() {
        System.out.println(getName() + " prépare son bâton.");
    }
}