public class Cavalier extends Unite {
    Cavalier(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col) {
        this(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, 1);
    }

    Cavalier(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col, int level) {
        super(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, level);
    }

    @Override
    public String typeUnite() {
        return "Cavalier";
    }

    public boolean peutManier(CategorieArme categorie) {
        return categorie == CategorieArme.EPEE || categorie == CategorieArme.LANCE;
    }
}