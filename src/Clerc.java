public class Clerc extends Unite {
    Clerc(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col) {
        this(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, 1);
    }

    Clerc(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col, int level) {
        super(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, level);
    }

    public String typeUnite() {
        return "Clerc";
    }

    public boolean peutManier(CategorieArme categorie) {
        return categorie == CategorieArme.BATON;
    }
}