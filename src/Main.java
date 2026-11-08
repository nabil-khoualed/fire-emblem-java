import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 8, 5, 1, 1, 3);
        Cavalier frederick = new Cavalier("Frederick", 4, 30, 10, 0,
        9, 4, 6, 7, 1, 3);
        Mage robin = new Mage("Robin", 22, 22, 3, 9,
        3, 7, 7, 5, 0, 5);
        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 0, 1);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Epeiste vaike = new Epeiste("Vaike", 25, 25, 6, 0,
        4, 2, 5, 5, 3, 3);

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

        for(CategorieArme arme : CategorieArme.values()) {
            System.out.println(arme);
        }
        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100, 0, 1, 1, 50, 1, 350);
        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 9, 80, 0, 1, 1, 35, 5, 910);
        Arme remede = new Arme("Remède", CategorieArme.BATON, 10, 100, 0, 1, 5, 10, 8, 1800);
        Arme epeeFer = new Arme("Epee fer", CategorieArme.EPEE, 5, 95, 0, 1, 1, 40, 3, 520);
        Arme feu = new Arme("Feu", CategorieArme.TOME, 2, 90, 0, 1, 2, 45, 1, 540); 
        System.out.println(epeeFer.afficherFiche());
        System.out.println(epeeBronze.afficherFiche());
        System.out.println(feu.afficherFiche());

        try {
            Arme testEpee1 = new Arme("Test Epee 1", CategorieArme.EPEE, 3, 100, 0, 2, 1, 50, 1, 350);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            Arme testEpee2 = new Arme("Test Epee 2", CategorieArme.EPEE, 3, 100, 0, 1, 1, -4, 1, 350);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeFer);
        System.out.println("Inventaire de Chrom : " + chrom.afficherInventaire());

        System.out.println("Lissa peut équiper remède : " + lissa.peutEquiper(remede));
        System.out.println("Chrom peut équiper Epee Bronze : " + chrom.peutEquiper(epeeBronze));
        System.out.println("Chrom peut equiper Epee Fer : " + chrom.peutEquiper(epeeFer));
        System.out.println("Frederick peut equiper Lance Acier : " + frederick.peutEquiper(lanceAcier));
        System.out.println("Robin peut equiper Feu : " + robin.peutEquiper(feu));
        System.out.println("Chrom peut equiper Feu : " + chrom.peutEquiper(feu));

        chrom.equiperArme(feu);
        chrom.equiperArme(epeeBronze);
        chrom.equiperArme(epeeFer);
        chrom.equiperArme(epeeBronze);
    }
}