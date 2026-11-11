public class Arme {
    private String name;
    private CategorieArme categorie;
    private int might;
    private int hit;
    private int crit;
    private int minRange;
    private int maxRange;
    private int remainingUses;
    private int levelRequired;
    private int price;
    private EffetBaton effetBaton;


    
    public Arme(String name, CategorieArme categorie, int might, int hit, int crit, int minRange, int maxRange,
        int remainingUses, int levelRequired, int price) {
        this(name, categorie, might, hit, crit, minRange, maxRange, remainingUses, levelRequired, price, null);
    }

    Arme(String name, CategorieArme categorie, int might, int hit, int crit, int minRange, int maxRange, int remainingUses, int levelRequired, int price, EffetBaton effetBaton) {
        this.name = name;
        this.categorie = categorie;
        this.might = might;
        this.hit = hit;
        this.crit = crit;
        this.minRange = minRange;
        this.maxRange = maxRange;
        if(minRange > maxRange) {
            throw new IllegalArgumentException("La portée minimale doit être inférieure ou égale à la portée maximale.");
        }
        this.remainingUses = remainingUses;
        if(remainingUses < 0) {
            throw new IllegalArgumentException("La durabilité ne peut pas être négative.");
        }
        this.levelRequired = levelRequired;
        this.price = price;
        this.effetBaton = effetBaton;
        if (categorie == CategorieArme.BATON && effetBaton == null) {
            throw new IllegalArgumentException("Un bâton doit obligatoirement posséder un effet.");
        }
        else if (categorie != CategorieArme.BATON && effetBaton != null) {
            throw new IllegalArgumentException("Seuls les bâtons peuvent posséder cet effet.");
        }
    }

    public String getName() {
        return name;
    }

    public CategorieArme getCategorie() {
        return categorie;
    }

    public int getMight() {
        return might;
    }

    public int getHit() {
        return hit;
    }

    public int getCrit() {
        return crit;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public int getPrice() {
        return price;
    }
    
    public EffetBaton getEffetBaton() {
        return effetBaton;
    }
    
    public String afficherFiche() {
        if(categorie == CategorieArme.BATON) {
            return (name + " | " + categorie + " | Puissance : " + might + " | Précision : " + hit +
            "% | Critique : " + crit + "% | Portée : " + minRange + "-" + maxRange + " | Utilisations : " +
            remainingUses + " | Niveau requis : " + levelRequired + " | Prix : " + price + "| Effet Bâton : " + effetBaton); 
        }
        else {
            return (name + " | " + categorie + " | Puissance : " + might + " | Précision : " + hit +
            "% | Critique : " + crit + "% | Portée : " + minRange + "-" + maxRange + " | Utilisations : " +
            remainingUses + " | Niveau requis : " + levelRequired + " | Prix : " + price);
        }
    }

    public void consommerUtilisation() {
        if(remainingUses > 0) {
            remainingUses--;
        }
    }
}

/* 
• Créer une classe représentant une arme avec : nom, catégorie, puissance, portée minimale, portée
maximale, utilisations restantes et niveau minimal requis.
• Prévoir un constructeur et des moyens de consulter ses caractéristiques sans rendre ses attributs
publics.
• Créer deux armes d’une même catégorie avec des caractéristiques différentes ; afficher leur fiche.
• Interdire à la construction une portée minimale supérieure à la portée maximale, et toute durabilité
négative.
Validation attendue : Les deux armes possèdent des données indépendantes ; les cas invalides sont refusés. */