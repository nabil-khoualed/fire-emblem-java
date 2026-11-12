import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Partie {
    private List<Unite> allies;
    private List<Unite> ennemies;
    private Map<Unite, EtatUnite> etats;
    private int rowCount;
    private int columnCount;
    private boolean[][] casesInfranchissables;

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
        this.casesInfranchissables = new boolean[rowCount][columnCount];
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

    public boolean[][] getCasesInfranchissables() {
        return casesInfranchissables;
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
        if(peutAgir(attaquant) &&
        arme != null &&
        arme.getCategorie() != CategorieArme.BATON &&
        arme.getRemainingUses() > 0 &&
        cible.getCurrentHp() > 0 &&
        aPorteeDeCoup(attaquant, cible) &&
        equipeOpposee(attaquant, cible)){
            return true;
        }
        return false;
    }

    public boolean aPorteeDeCoup(Unite attaquant, Unite cible) {
        int distance = distanceEntreUnites(attaquant, cible);
        Arme arme = attaquant.getArmeEquipee();
        return (distance >= arme.getMinRange() && distance <= arme.getMaxRange());
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

    public void frapper(Unite attaquant, Unite cible) {
        if(attaquant.tirageReussite(cible)) {
            int damageAmount = attaquant.calculerDegats(cible);
            if(attaquant.tirageCritique()) {
                damageAmount *= 3;
                System.out.println(attaquant.getName() + " réalise un COUP CRITIQUE !");
            }
            System.out.print(attaquant.getName() + " attaque " + cible.getName() + " avec " + attaquant.getArmeEquipee().getName()
            + " : " + damageAmount + " dégâts (" + cible.getCurrentHp() + " -> ");
            cible.subirDegats(damageAmount);
            System.out.println(cible.getCurrentHp() + " PV).");
            attaquant.getArmeEquipee().consommerUtilisation();
        }
        else {
            System.out.println(attaquant.getName() + " rate " + cible.afficherHp() + ".");
            if(attaquant.getArmeEquipee().getCategorie() == CategorieArme.TOME) {
                attaquant.getArmeEquipee().consommerUtilisation();
            }
        }
    }

    public boolean attaquer(Unite attaquant, Unite cible) {
        Arme armeAttaquant = attaquant.getArmeEquipee();
        Arme armeCible = cible.getArmeEquipee();
        if(peutAttaquer(attaquant, cible)) {
            frapper(attaquant, cible);
            if(peutRiposter(attaquant, cible)) {
                frapper(cible, attaquant);
            }
            if(attaquant.peutDoubler(cible) && attaquant.getCurrentHp() > 0 && cible.getCurrentHp() > 0 &&
            armeAttaquant != null && armeAttaquant.getRemainingUses() > 0) {
                frapper(attaquant, cible);
            }
            if(cible.peutDoubler(attaquant) && cible.getCurrentHp() > 0 && attaquant.getCurrentHp() > 0 &&
            armeCible != null && armeCible.getRemainingUses() > 0 && armeCible.getCategorie() != CategorieArme.BATON && aPorteeDeCoup(cible, attaquant)) {
                frapper(cible, attaquant);
            }
            if(armeAttaquant.getRemainingUses() == 0) {
                attaquant.equiperArmeSuivante();
            }
            if(armeCible != null && armeCible.getRemainingUses() == 0) {
                cible.equiperArmeSuivante();
            }
            modifierEtat(attaquant, EtatUnite.TERMINEE);
            return true;
        }
        System.out.println(attaquant.getName() + " ne peut pas attaquer " + cible.getName());
        return false;
    }

    public boolean peutRiposter(Unite attaquant, Unite defenseur) {
        return (defenseur.getCurrentHp() > 0 &&
        defenseur.getArmeEquipee() != null &&
        defenseur.getArmeEquipee().getCategorie() != CategorieArme.BATON &&
        defenseur.getArmeEquipee().getRemainingUses() > 0 &&
        equipeOpposee(attaquant, defenseur) &&
        attaquant.getCurrentHp() > 0 &&
        aPorteeDeCoup(defenseur, attaquant));
    }

    public boolean soigner(Unite soigneur, Unite cible) {
        if(peutSoigner(soigneur, cible)) {
            int healAmount = soigneur.calculerSoin(cible);
            cible.recevoirSoin(healAmount);
            System.out.println(soigneur.getName() + " soigne " + cible.getName() + " avec " + soigneur.getArmeEquipee().getName() +" de " + healAmount + " points de vie.");
            modifierEtat(soigneur, EtatUnite.TERMINEE);
            soigneur.getArmeEquipee().consommerUtilisation();
            if(soigneur.getArmeEquipee().getRemainingUses() == 0) {
                soigneur.equiperArmeSuivante();
            }
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

    public void finirPhaseEquipe(List<Unite> equipe) {
        for (Unite unite : equipe) {
            modifierEtat(unite, EtatUnite.TERMINEE);
        }
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
                else if(casesInfranchissables[row][col]) {
                    System.out.print("#");
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
    public boolean placerObstacle(int row, int column) {
        if(caseExist(row, column) && !caseOccupied(row, column)) {
            casesInfranchissables[row][column] = true;
            return true;
        }
        return false;
    }
    public boolean retirerObstacle(int row, int column) {
        if(caseExist(row, column)) {
            casesInfranchissables[row][column] = false;
            return true;
        }
        return false;
    }

    public boolean caseBloquee(int row, int column) {
        return caseExist(row, column) && casesInfranchissables[row][column];
    }
}