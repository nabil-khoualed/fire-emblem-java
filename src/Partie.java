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

    public boolean phaseTerminee(List<Unite> equipe) {
        for (Unite unite : equipe) {
            if(etats.get(unite) != EtatUnite.TERMINEE) {
                return false;
            }
        }
        return true;
    }
}

/* 
Travail demandé. Créer dans Partie une méthode qui vérifie si toutes les unités d’une équipe
passée en argument ont l’état TERMINEE. Faire attendre Frederick et Robin, puis vérifier la fin de
la phase alliée avant et après le soin de Lissa.

Contrainte. Ne pas supposer qu’il y a toujours quatre alliés. Les états ennemis ne doivent pas
intervenir dans la vérification de l’équipe alliée.

Validation. Avant Lissa : false ; après Lissa : true. Vaike peut être encore DISPONIBLE. */