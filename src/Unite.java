import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public abstract class Unite {
    private String name;
    private int currentHp;
    private int maxHp;
    private int strength;
    private int magic;
    private int defense;
    private int resistance;
    private int speed;
    private int movement;

    private int row;
    private int col;
    private int level = 1;
    private List<Arme> inventory = new ArrayList<>(5);
    private Arme armeEquipee;
    private Random random = new Random();


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

    Unite(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col) {
        this(name, currentHp, maxHp, strength, magic, defense, resistance, speed, movement, row, col, 1);
    }

    Unite(String name, int currentHp, int maxHp, int strength, int magic, int defense, int resistance, int speed, int movement, int row, int col, int level) {
        this.name = name;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.strength = strength;
        this.magic = magic;
        this.defense = defense;
        this.resistance = resistance;
        this.speed = speed;
        this.movement = movement;
        this.row = row;
        this.col = col;
        this.level = level;
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
    
    public int getStrength() {
        return strength;
    }

    public int getMagic() {
        return magic;
    }

    public int getDefense() {
        return defense;
    }

    public int getResistance() {
        return resistance;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMovement() {
        return movement;
    }

    public int getLevel() {
        return level;
    }

    public Arme getArmeEquipee() {
        return armeEquipee;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Random getRandom() {
        return random;
    }

    public String afficherArmeEquipee() {
        if(armeEquipee == null) {
            return "Aucune";
        }
        return armeEquipee.getName() + " (" + armeEquipee.getRemainingUses() + " utilisations restantes)";
    }

    public String afficherFiche() {
        return (typeUnite() + " " + name + " (Niv. " + level + ") | PV : " + currentHp + "/" 
        + maxHp + " | Pos : [" + row + ", " + col + "] | Arme : " + afficherArmeEquipee());
    }

    public String afficherFicheComplete() {
        return (typeUnite() + " " + name + ", niveau " + level + " : " + currentHp + "/" 
        + maxHp + " PV, force : " + strength + ", magie : " + magic + ", défense : " + defense + ", résistance : " + resistance + ", vitesse : " + speed + ", mouvements : " + movement + ", position : [" + row + ", " + col + "], arme équipée : " + afficherArmeEquipee());
    }

    public int calculerDegats(Unite cible) {
        if(armeEquipee == null) {
            return 0;
        }
        if(armeEquipee.getCategorie() == CategorieArme.TOME) {
            return Math.max(0, magic + armeEquipee.getMight() - cible.getResistance());
        }
        else if(armeEquipee.getCategorie() != CategorieArme.BATON) {
            return Math.max(0, strength + armeEquipee.getMight() - cible.getDefense());
        }
        return 0;
    }

    public int calculerSoin(Unite cible) {
        int missingHp = cible.getMaxHp() - cible.getCurrentHp();
        if(armeEquipee == null || armeEquipee.getCategorie() != CategorieArme.BATON) {
            return 0;
        }
        if(armeEquipee.getEffetBaton() == EffetBaton.SOIN_COMPLET) {
            return missingHp;
        }
        if(armeEquipee.getEffetBaton() == EffetBaton.SOIN) {
            return Math.min((magic / 2 + armeEquipee.getMight()), missingHp);
        }
        return 0;
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

    public abstract String typeUnite();

    public String afficherHp() {
        return (name + " (" + currentHp + " / " + maxHp + " PV)");
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

    public boolean equiperArme(Arme arme) {
        if(peutEquiper(arme) && inventory.contains(arme)) {
            armeEquipee = arme;
            System.out.println(name + " a équipé " + arme.getName());
            return true;
        }
        else {
            System.out.println(name + " ne peut pas équiper " + arme.getName());
            return false;
        }
    }

    public int esquive() {
        return (speed * 2);
    }

    public int probabiliteReussir(Unite cible) {
        if (armeEquipee == null) {
            return 0;
        }
        int probabilite = Math.max(0, armeEquipee.getHit() - cible.esquive());
        return Math.min(100, probabilite);
    }

    public boolean tirageReussite(Unite cible) {
        int tirage = random.nextInt(100);
        return tirage < probabiliteReussir(cible);
    }

    public boolean tirageCritique() {
        if(armeEquipee == null) {
            return false;
        }
        int tirage = random.nextInt(100);
        return tirage < armeEquipee.getCrit();
    }

    public void equiperArmeSuivante() {
        for(Arme arme : inventory) {
            if(peutEquiper(arme) && arme.getRemainingUses() >0) {
                armeEquipee = arme;
                return;
            }
        }
        armeEquipee = null;
    }
}