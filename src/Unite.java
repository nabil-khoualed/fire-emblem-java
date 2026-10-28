public abstract class Unite {
    private String name;
    private int currentHp;
    private int maxHp;
    private int row;
    private int col;

    Unite(String name, int currentHp, int maxHp, int row, int col) {
        this.name = name;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.row = row;
        this.col = col;
    }

    Unite(String name) {
        this.name = name;
    }

    Unite(String name, int currentHp, int maxHp) {
        this.name = name;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
    }

    Unite(String name, int maxHp) {
        this.name = name;
        this.currentHp = maxHp;
        this.maxHp = maxHp;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void afficherFiche() {
        System.out.println("Nom : " + name);
        System.out.println("PV : " + currentHp + " / " + maxHp);
    }

    public void subirDegats(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public void soigner(int healAmount) {
        currentHp = Math.min(maxHp, currentHp + healAmount);
    }

    public void deplacerVers(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void annoncerAction() {
        System.out.println(name + " attend.");
    }

    public abstract String typeUnite();

    public String afficherHp() {
        return (currentHp + " / " + maxHp + " PV");
    }
}