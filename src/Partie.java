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

    public boolean attendre(Unite unite) {
        if(peutAgir(unite)) {
            modifierEtat(unite, EtatUnite.TERMINEE);
            return true;
        }
        return false;
    }

    public boolean attaquer(Unite attaquant, Unite cible) {
        if (attaquant instanceof Epeiste epeiste && peutAgir(attaquant)) {
            epeiste.attaqueRapide(cible);
            modifierEtat(attaquant, EtatUnite.TERMINEE);
            return true;
        }
        return false;
    }

    public boolean soigner(Unite soigneur, Unite cible, int healAmount) {
        if (soigneur instanceof Soigneur healer && peutAgir(soigneur)) {
            healer.soignerCible(cible,healAmount);
            modifierEtat(soigneur, EtatUnite.TERMINEE);
            return true;
        }
        return false;
    }
}

/* 
Travail demandé. Créer dans Partie une opération permettant à un Epeiste d’attaquer une Unite
avec attaqueRapide(). La méthode vérifie l’autorisation d’agir et termine l’activation uniquement
en cas de réussite. Depuis Main, faire attaquer Chrom contre Vaike deux fois de suite.

Contrainte. Réutiliser attaqueRapide et peutAgir. Ne pas refaire le calcul des dégâts ni modifier les
PV directement. Ne pas appliquer une attaque si elle a été refusée.

Validation. Première attaque acceptée : Vaike 17 / 25 PV ; deuxième tentative refusée : Vaike reste
à 17 / 25 PV. */