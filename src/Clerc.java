public class Clerc extends Unite implements Soigneur {
    Clerc(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col) {
        this(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, 1);
    }

    Clerc(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col, int level) {
        super(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, level);
    }

    public String typeUnite() {
        return "Clerc";
    }

    public void soignerCible(Unite target, int healAmount) {
        target.recevoirSoin(healAmount);
    }

    public boolean peutManier(CategorieArme categorie) {
        return categorie == CategorieArme.BATON;
    }
}