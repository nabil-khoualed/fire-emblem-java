import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String afficherNomEtPv(Unite unite) {
        return (unite.typeUnite() + " " + unite.getName() + " : " + unite.afficherHp());
    }

    public static String afficherPosition(Unite unite) {
        return ("[" + unite.getRow() + ", " + unite.getCol() +"]");
    }

    public static void afficherEquipe(List<Unite> equipe) {
        for (Unite unite : equipe) {
            System.out.println(afficherNomEtPv(unite) + ", " + afficherPosition(unite));
        }
    }

    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 1, 1, 8);
        Cavalier frederick = new Cavalier("Frederick", 30, 30, 1, 3, 7);
        Mage robin = new Mage("Robin", 22, 22, 0, 5,9);
        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 1);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Epeiste vaike = new Epeiste("Vaike", 25, 25, 3, 3, 6);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        Partie game = new Partie(allies, ennemies, 5, 7);

        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);


        System.out.println("Troupe alliés : ");
        afficherEquipe(game.getListAllies());
        System.out.println("\nTroupe ennemis : ");
        afficherEquipe(game.getListEnnemies());
        System.out.println("");

        game.afficherTerrain();


        System.out.println("Début de la phase alliée ! ");
        game.deplacerUnite(frederick, 2, 3);
        game.deplacerUnite(robin, 0, 8);
        if(game.attendre(robin)) {
            System.out.println("Robin attend impatiemment.");
        }
        System.out.println("Robin essaie d'attendre une seconde fois : " + game.attendre(robin));
        if(game.attendre(lissa)) {
            System.out.println("Lissa attend impatiemment.");
        }

        game.deplacerUnite(chrom, 3, 2);
        System.out.println("Chrom : " + game.recupererEtat(chrom));
        System.out.println("Chrom peut agir ce tour : " + game.peutAgir(chrom));
        System.out.println("Chrom peut se déplacer ce tour : " + game.peutSeDeplacer(chrom));

        game.attaquer(chrom, vaike);
        System.out.println(afficherNomEtPv(vaike));
        game.attaquer(chrom, vaike);
        System.out.println(afficherNomEtPv(vaike));
        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        System.out.println("Début de la phase ennemie ! ");

        if(game.attaquer(vaike, frederick))
        {
            System.out.println("Vaike porte un coup à Frederick !");
        }

        System.out.println("Frederick après phase ennemie : " + frederick.afficherHp());
        System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));
        game.reinitialiserEtat(allUnits);
        System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");
        for(Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
        }
        game.afficherTerrain();
        game.deplacerUnite(lissa, 2, 2);
        game.soigner(lissa, frederick, 5);
        System.out.println("Deuxième tentative de soin : " + game.soigner(lissa, frederick, 5));
        System.out.println("Chrom essaie de soigner frederick : " + game.soigner(chrom, frederick, 5));
        System.out.println(afficherNomEtPv(frederick));
    }
}