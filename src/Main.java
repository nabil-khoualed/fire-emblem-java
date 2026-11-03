    import java.util.ArrayList;
    import java.util.List;
    import java.util.HashMap;
    import java.util.Map;

    public class Main {
        public static void afficherNomEtPv(Unite unite) {
            System.out.println(unite.typeUnite() + " " + unite.getName() + " : " + unite.afficherHp());
        }

        public static void afficherEquipe(List<Unite> equipe) {
            for (Unite unite : equipe) {
                afficherNomEtPv(unite);
            }
        }

        public static boolean peutAgir(Unite unite, Map<Unite, EtatUnite> etats) {
            return unite.getCurrentHp() > 0
                    && etats.get(unite) == EtatUnite.DISPONIBLE;
        }

        public static void reinitialiserEtat(List<Unite> equipe, Map<Unite, EtatUnite> etats) {
            for(Unite unite : equipe) {
                if(unite.getCurrentHp() > 0) {
                    etats.put(unite, EtatUnite.DISPONIBLE);
                }
            }
        }

        public static void main(String[] args) {
            Epeiste chrom = new Epeiste("Chrom", 24, 8);
            Cavalier frederick = new Cavalier("Frederick", 30, 7);
            Mage robin = new Mage("Robin", 22, 9);
            Clerc lissa = new Clerc("Lissa", 18);

            ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

            Epeiste vaike = new Epeiste("Vaike", 25, 6);

            ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

            Partie game = new Partie(allies, ennemies);

            ArrayList<Unite> allUnits = new ArrayList<>();
            allUnits.addAll(allies);
            allUnits.addAll(ennemies);


            System.out.println("Troupe alliés : ");
            afficherEquipe(game.getListAllies());
            System.out.println("\nTroupe ennemis : ");
            afficherEquipe(game.getListEnnemies());
            System.out.println("");
            for (Unite unite : allUnits) {
                System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
            }

            for (Unite unite : allUnits) {
                System.out.println(unite.getName() + " peut agir : " + game.peutAgir(unite));
            }

            if(game.attaquer(chrom, vaike)) {
                System.out.println("Chrom attaque Vaike.");
                System.out.println("Vaike après attaque : " + vaike.afficherHp());
            }
            else {
                System.out.println("Chrom ne peut pas agir.");
            }

            if(game.attaquer(chrom, vaike)) {
                System.out.println("Chrom attaque Vaike.");
            }
            else {
                System.out.println("Chrom ne peut pas agir.");
            }

            System.out.println("Vaike : " + vaike.afficherHp());
            game.attendre(frederick);
            game.attendre(robin);

            System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));

            chrom.subirDegats(6);
            game.soigner(lissa, chrom, 5);
            game.soigner(lissa, chrom, 5);
            System.out.println("Chrom après dégâts et soin : " + chrom.afficherHp());
            System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
            
            System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));
            game.attaquer(vaike, chrom);

            System.out.println("Chrom après phase ennemie : " + chrom.afficherHp());
            System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));
            reinitialiserEtat(allUnits, game.getEtats());
            System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");
            for(Unite unite : allUnits) {
                System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
            }
            System.out.println("Première attente : " + game.attendre(frederick));
            System.out.println("Deuxième attente : " + game.attendre(frederick));
            System.out.println("Frederick : " + frederick.afficherHp());
            System.out.println("Robin : " + game.recupererEtat(robin));
            game.attaquer(robin, chrom);
            System.out.println("Chrom : " + chrom.afficherHp());
            System.out.println("Robin : " + game.recupererEtat(robin));
        }
    }