public class Epeiste extends Unite {
    private int strength;

    Epeiste(String name, int maxHp, int strength) {
        this(name, maxHp, maxHp, strength);
    }

    Epeiste(String name) {
        super(name);
    }

    Epeiste(String name, int maxHp) {
        super(name, maxHp, maxHp);
    }

    Epeiste(String name, int currentHp, int maxHp, int strength) {
        this(name, currentHp, maxHp, 0, 0, strength);
    }

    Epeiste(String name, int currentHp, int maxHp, int row, int col, int strength) {
        super(name, currentHp, maxHp, row, col);
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public void afficherFiche() {
        super.afficherFiche();
        System.out.println("Force : " + strength);
    }

    public void attaqueRapide(Unite adversaire) {
        adversaire.subirDegats(strength);
    }

    @Override
    public void annoncerAction() {
        System.out.println(getName() + " prépare son épée.");
    }

    @Override
    public String typeUnite() {
        return "Épéiste";
    }
}