public class Cavalier extends Unite {
    private int defense;

    Cavalier(String name) {
        super(name);
    }

    Cavalier(String name, int maxHp) {
        this(name, maxHp, maxHp);
    }

    Cavalier(String name, int maxHp, int defense) {
        this(name, maxHp, maxHp, defense);
    }

    Cavalier(String name, int currentHp, int maxHp, int defense) {
        this(name, currentHp, maxHp, 0, 0, defense);
    }

    Cavalier(String name, int currentHp, int maxHp, int row, int col, int defense) {
        super(name, currentHp, maxHp, row, col);
        this.defense = defense;

    }

    public int getDefense() {
        return defense;
    }

    @Override
    public void afficherFiche() {
        super.afficherFiche();
        System.out.println("Défense : " + defense);
    }

    @Override
    public void annoncerAction() {
        System.out.println(getName() + " avance à cheval.");
    }

    @Override
    public String typeUnite() {
        return "Cavalier";
    }
}