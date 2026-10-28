public class Clerc extends Unite implements Soigneur {

    Clerc(String name) {
        super(name);
    }

    Clerc(String name, int maxHp) {
        super(name, maxHp, maxHp);
    }

    Clerc(String name, int currentHp, int maxHp, int row, int col) {
        super(name, currentHp, maxHp, row, col);
    }

    public String typeUnite() {
        return "Clerc";
    }

    public void soignerCible(Unite target, int healAmount) {
        target.soigner(healAmount);
    }
}