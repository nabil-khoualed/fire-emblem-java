import java.util.List;
import java.util.ArrayList;

public abstract class Unite {
    private String name;
    private int currentHp;
    private int maxHp;
    private int row;
    private int col;
    private int level = 1;
    private List<Arme> inventory = new ArrayList<>(5);

    public boolean ajouterObjet(Arme arme) {
        if(inventory.size() <5) {
            inventory.add(arme);
            System.out.println(arme.getName() + " a été ajouté à l'inventaire de " + name + ".");
            return true;
        }
        else {
            System.out.println("Inventaire de " + name + " plein, impossible d'ajouter " + arme.getName() + ".");
            return false;
        }

    }

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

    public List<Arme> getInventory() {
        return inventory;
    }

    public String afficherFiche() {
        return (typeUnite() + " " + name + " : " + currentHp + " / " 
        + maxHp + " PV, [" + row + ", " + col + "]");
    }

    public void subirDegats(int damage) {
        currentHp = Math.max(0, currentHp - damage);
    }

    public void recevoirSoin(int healAmount) {
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
        return (name + " : " + currentHp + " / " + maxHp + " PV");
    }

    public String afficherInventaire() {
        if(inventory.size() == 0) {
            return "[]";
        }
        else {
            StringBuilder inventoryList = new StringBuilder();
            inventoryList.append("[");
            for(int i = 0; i < inventory.size()-1 ; i++) {
                inventoryList.append(inventory.get(i).getName() + " | ");
            }
            inventoryList.append(inventory.get(inventory.size() -1).getName() + "]");
            return inventoryList.toString();
        }
    }

    public abstract boolean peutManier(CategorieArme categorie);

    public boolean peutEquiper(Arme arme) {
        return arme.getLevelRequired() <= level && peutManier(arme.getCategorie());
    }
}