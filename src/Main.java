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

        game.deplacerUnite(chrom, 3, 2);
        if(game.attaquer(chrom, vaike)) {
            System.out.println("Chrom attaque Vaike.");
        }
        System.out.println(vaike.afficherHp());

        game.deplacerUnite(lissa, 1, 2);
        if(game.soigner(lissa, frederick, 2)) {
            System.out.println("Lissa soigne Frederick de 2 pdv.");
            System.out.println(frederick.afficherHp());
        }

        if(game.attendre(frederick)) {
            System.out.println("Frederick attend.");
        }

        if(game.attendre(robin)) {
            System.out.println("Robin attend.");
        }
        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        System.out.println("Début de la phase ennemie ! ");
        game.deplacerUnite(vaike, 2, 3);
        if(game.attaquer(vaike, frederick))
        {
            System.out.println("Vaike porte un coup à Frederick !");
            System.out.println(frederick.afficherHp());
        }
        System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));
        game.reinitialiserEtat(allUnits);
        System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");

        for(Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
        }
        game.afficherTerrain();
    }
}