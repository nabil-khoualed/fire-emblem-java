import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Partie {
    private List<Unite> allies;
    private List<Unite> ennemies;
    private Map<Unite, EtatUnite> etats;

    Partie(List<Unite> allies, List<Unite> ennemies) {
        this.allies = allies;
        this.ennemies = ennemies;
        this.etats = new HashMap<>();
        for (Unite unite : allies) {
            etats.put(unite, EtatUnite.DISPONIBLE);
        }
        for (Unite unite : ennemies) {
            etats.put(unite, EtatUnite.DISPONIBLE);
        }
    }

    public List<Unite> getListAllies() {
        return allies;
    }

    public List<Unite> getListEnnemies() {
        return ennemies;
    }

    public EtatUnite recupererEtat(Unite unite) {
        return etats.get(unite);
    }

    public void modifierEtat(Unite unite, EtatUnite nouvelEtat) {
        etats.put(unite, nouvelEtat);
    }

    public Map<Unite, EtatUnite> getEtats() {
        return etats;
    }

    public boolean peutAgir(Unite unite) {
        return unite.getCurrentHp() > 0 && etats.get(unite) == EtatUnite.DISPONIBLE;

    }
}

/* 
Travail demandé. Déplacer dans Partie la gestion de la collection associant chaque unité à son
EtatUnite. À la création d’une partie, initialiser à DISPONIBLE les unités des deux équipes. Prévoir
une opération permettant de consulter l’état d’une unité.
Contrainte. Associer les états aux objets Unite, jamais aux indices. Ne pas maintenir deux copies
indépendantes des états dans Main et Partie.
Validation. Dans Main, les cinq unités ont l’état DISPONIBLE au début de la partie. */