import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Partie {
    private List<Unite> allies;
    private List<Unite> ennemies;
    private Map<Unite, EtatUnite> etats;
    private int rowCount;
    private int columnCount;

    Partie(List<Unite> allies, List<Unite> ennemies) {
        this(allies, ennemies, 5, 7);
    }

    Partie(List<Unite> allies, List<Unite> ennemies, int rowCount, int columnCount) {
        this.allies = allies;
        this.ennemies = ennemies;
        this.etats = new HashMap<>();
        for (Unite unite : allies) {
            etats.put(unite, EtatUnite.DISPONIBLE);
        }
        for (Unite unite : ennemies) {
            etats.put(unite, EtatUnite.DISPONIBLE);
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
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

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void modifierEtat(Unite unite, EtatUnite nouvelEtat) {
        etats.put(unite, nouvelEtat);
    }

    public Map<Unite, EtatUnite> getEtats() {
        return etats;
    }

    public boolean peutAgir(Unite unite) {
        return unite.getCurrentHp() > 0 && etats.get(unite) != EtatUnite.TERMINEE;
    }

    public boolean peutSeDeplacer(Unite unite) {
        return peutAgir(unite) && etats.get(unite) != EtatUnite.DEPLACEE;
    }

    public boolean memeEquipe(Unite unite1, Unite unite2) {
        return (allies.contains(unite1) && allies.contains(unite2)) || (ennemies.contains(unite1) && ennemies.contains(unite2));
    }

    public boolean equipeOpposee(Unite unite1, Unite unite2) {
        return (allies.contains(unite1) && ennemies.contains(unite2)) || (allies.contains(unite2) && ennemies.contains(unite1)); 
    }

    public boolean attendre(Unite unite) {
        if(peutAgir(unite)) {
            modifierEtat(unite, EtatUnite.TERMINEE);
            return true;
        }
        return false;
    }

    public boolean peutAttaquer(Unite attaquant, Unite cible) {
        Arme arme = attaquant.getArmeEquipee();
        int distance = distanceEntreUnites(attaquant, cible);
        if(peutAgir(attaquant) &&
        arme != null &&
        arme.getCategorie() != CategorieArme.BATON &&
        arme.getRemainingUses() > 0 &&
        cible.getCurrentHp() > 0 &&
        distance >= arme.getMinRange() &&
        distance <= arme.getMaxRange() &&
        equipeOpposee(attaquant, cible)){
            return true;
        }
        return false;
    }

    public boolean peutSoigner(Unite soigneur, Unite cible) {
        Arme arme = soigneur.getArmeEquipee();
        int distance = distanceEntreUnites(soigneur, cible);
        if(peutAgir(soigneur) &&
        arme != null &&
        arme.getCategorie() == CategorieArme.BATON &&
        (arme.getEffetBaton() == EffetBaton.SOIN || arme.getEffetBaton() == EffetBaton.SOIN_COMPLET) &&
        arme.getRemainingUses() > 0 &&
        distance >= arme.getMinRange() &&
        distance <= arme.getMaxRange() &&
        cible.getCurrentHp() > 0 &&
        cible.getCurrentHp() < cible.getMaxHp() &&
        memeEquipe(soigneur, cible)) {
            return true;
        }
        return false;
    }

    public boolean attaquer(Unite attaquant, Unite cible) {
        if(peutAttaquer(attaquant, cible)) {
            cible.subirDegats(attaquant.calculerDegats(cible));
            System.out.println(attaquant.getName() + " attaque " + cible.getName());
            modifierEtat(attaquant, EtatUnite.TERMINEE);
            attaquant.getArmeEquipee().consommerUtilisation();
            return true;
        }
        System.out.println(attaquant.getName() + " ne peut pas attaquer " + cible.getName());
        return false;
    }

    public boolean soigner(Unite soigneur, Unite cible) {
        if(peutSoigner(soigneur, cible)) {
            int healAmount = soigneur.calculerSoin(cible);
            cible.recevoirSoin(healAmount);
            System.out.println(soigneur.getName() + " soigne " + cible.getName() + " de " + healAmount + " points de vie.");
            modifierEtat(soigneur, EtatUnite.TERMINEE);
            soigneur.getArmeEquipee().consommerUtilisation();
            return true;
        }
        System.out.println(soigneur.getName() + " ne peut pas soigner " + cible.getName());
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

    public void reinitialiserEtat(List<Unite> equipe) {
        for(Unite unite : equipe) {
            if(unite.getCurrentHp() > 0) {
                etats.put(unite, EtatUnite.DISPONIBLE);
            }
        }
    }

    public boolean caseExist(int row, int column) {
        return row >= 0 && row < rowCount
            && column >= 0 && column < columnCount;
    }

    public boolean caseOccupied(int row, int col) {
        for(Unite unite : allies) {
            if(row == unite.getRow() && col == unite.getCol()) {
                return true;
            }
        }
        for(Unite unite : ennemies) {
            if(row == unite.getRow() && col == unite.getCol()) {
                return true;
            }
        }
        return false;
    }

    public boolean caseOccupiedByAlly(int row, int col) {
        for(Unite unite : allies) {
            if(row == unite.getRow() && col == unite.getCol()) {
                return true;
            }
        }
        return false;
    }

        public boolean caseOccupiedByEnemy(int row, int col) {
        for(Unite unite : ennemies) {
            if(row == unite.getRow() && col == unite.getCol()) {
                return true;
            }
        }
        return false;
    }

    public int caseDistance(int row1, int col1, int row2, int col2) {
        int rowDistance = Math.abs(row1 - row2);
        int colDistance = Math.abs(col1 - col2);
        return (rowDistance + colDistance);
    }

    public int distanceEntreUnites(Unite unite1, Unite unite2) {
        return caseDistance(unite1.getRow(), unite1.getCol(), unite2.getRow(), unite2.getCol());
    }

    public boolean deplacerUnite(Unite unite, int row, int col) {
        if(!peutSeDeplacer(unite)) {
            System.out.println(unite.getName() + " ne peut pas se déplacer.");
            return false;
        }
        else if(!caseExist(row, col) || caseOccupied(row, col)) {
            System.out.println(unite.getName() + " ne peut pas se déplacer en [" + row + ", " + col + "].");
            return false;
        }
        else if(caseDistance(unite.getRow(), unite.getCol(), row, col) >unite.getMovement()) {
            System.out.println(unite.getName() + " ne peut pas se déplacer aussi loin jusqu'en [" + row + ", " + col + "].");
            return false;
        }
        unite.deplacerVers(row, col);
        System.out.println(unite.getName() + " s'est déplacé en [" + row + ", " + col + "].");
        modifierEtat(unite, EtatUnite.DEPLACEE);
        return true;
    }

    public void afficherTerrain() {
        for(int row = 0 ; row < rowCount ; row ++) {
            for(int col = 0 ; col < columnCount ; col++) {
                if(caseOccupiedByAlly(row, col)) {
                    System.out.print("A");
                }
                else if(caseOccupiedByEnemy(row, col)) {
                    System.out.print("E");
                }
                else{
                    System.out.print(".");
                }
            }            
            System.out.println();
        }
    }

    public void afficherUnite(Unite unite) {
        System.out.println(unite.afficherFiche() + ", peut agir : " + peutAgir(unite) + " - " + recupererEtat(unite));
    }

    public void afficherEquipeAllies() {
        for (Unite unite : allies) {
            System.out.println(unite.afficherFiche());
        }
    }
    public void afficherEquipeEnnemies() {
        for (Unite unite : ennemies) {
            System.out.println(unite.afficherFiche());
        }
    }

}