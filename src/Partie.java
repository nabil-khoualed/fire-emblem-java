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
    }

    /* 
    Travail demandé. Créer dans Partie une opération qui réinitialise à DISPONIBLE les unités vivantes
    d’une équipe. Après les deux phases, réinitialiser les deux équipes et afficher les états. Pour tester
    la condition, mettre temporairement Frederick à 0 PV après son action, puis observer son état.

    Contrainte. Ne pas restaurer les PV, ne pas recréer les objets et ne pas remettre DISPONIBLE une
    unité à 0 PV. Le second tour ne doit pas être simulé.

    Validation. Les unités vivantes redeviennent DISPONIBLE ; Frederick, à 0 PV, conserve son dernier
    état. Chrom reste à 17/24 PV et Vaike à 17/25 PV. */