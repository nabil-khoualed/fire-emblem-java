import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 1, 1, 8);
        Cavalier frederick = new Cavalier("Frederick", 4, 30, 1, 3, 7);
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
        game.afficherEquipeAllies();
        System.out.println("\nTroupe ennemis : ");
        game.afficherEquipeEnnemies();
        System.out.println("");

        game.afficherTerrain();

        System.out.println("Début de la phase alliée ! ");

        System.out.println("TEST : Déplacement vers case occupée : ");game.deplacerUnite(robin, 1, 3);
        System.out.println("TEST : Déplacement hors carte : ");game.deplacerUnite(robin, 0, 8);
        System.out.println("TEST : Déplacement vers case trop loin : ");game.deplacerUnite(robin, 4, 6);

        game.deplacerUnite(frederick, 2, 3);

        System.out.println("TEST : Deuxième déplacement : " + game.deplacerUnite(frederick, 4, 3));

        if(game.attendre(frederick)) {
            System.out.println("Frederick attend impatiemment.");
        }

        if(game.attendre(lissa)) {
            System.out.println("Lissa attend impatiemment.");
        }

        game.deplacerUnite(chrom, 3, 2);
        System.out.println("Chrom : " + game.recupererEtat(chrom));
        System.out.println("Chrom peut agir ce tour : " + game.peutAgir(chrom));
        System.out.println("Chrom peut se déplacer ce tour : " + game.peutSeDeplacer(chrom));

        game.attendre(chrom);
        System.out.println("TEST : Attaque après attente : " + game.attaquer(chrom, vaike));
        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        if(game.attendre(robin)) {
            System.out.println("Robin attend impatiemment.");
        }
        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        System.out.println("Début de la phase ennemie ! ");

        if(game.attaquer(vaike, frederick))
        {
            System.out.println("Vaike porte un coup à Frederick !");
        }
        System.out.println("TEST : Soin après attaque : " + game.soigner(vaike, frederick, 6));
        System.out.println("Frederick après phase ennemie : " + frederick.afficherFiche());
        System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));

        game.reinitialiserEtat(allUnits);
        System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");

        for(Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
        }

        game.deplacerUnite(frederick, 2, 1);
        game.afficherTerrain();
        game.deplacerUnite(lissa, 2, 2);
    }
}