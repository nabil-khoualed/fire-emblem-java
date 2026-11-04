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
        game.deplacerUnite(chrom, 1 , 3);
        game.deplacerUnite(frederick, 2, 3);
        game.deplacerUnite(robin, 0, 8);
        if(game.attendre(robin)) {
            System.out.println("Robin attend impatiemment.");
        }

        game.deplacerUnite(chrom, 0, 2);
        System.out.println("Chrom marche sur un piège et prends 6 dégâts !");
        chrom.subirDegats(6);
        if(game.soigner(lissa, chrom, 5)) {
            System.out.println("Lissa soigne Chrom de 5 points de vie.");
        }
        System.out.println("Chrom après dégâts et soin : " + chrom.afficherHp());
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
    }
}