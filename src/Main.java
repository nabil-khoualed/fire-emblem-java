import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 8, 5, 1, 1, 3);
        Cavalier frederick = new Cavalier("Frederick", 4, 30, 10, 0,
        9, 4, 6, 7, 1, 3, 5);
        Mage robin = new Mage("Robin", 22, 22, 3, 9,
        3, 7, 7, 5, 0, 5);
        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 0, 1);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Archer vaike = new Archer("Vaike", 25, 25, 6, 0,
        4, 2, 5, 5, 3, 3);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        Partie game = new Partie(allies, ennemies, 5, 7);

        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 50, 1, 350);
        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 9, 80,
        0, 1, 1, 35, 5, 910);
        Arme remede = new Arme("Remède", CategorieArme.BATON, 10, 100,
        0, 1, 5, 10, 8, 1800);
        Arme epeeFer = new Arme("Epee fer", CategorieArme.EPEE, 5, 95,
        0, 1, 1, 40, 3, 520);
        Arme feu = new Arme("Feu", CategorieArme.TOME, 2, 90,
        0, 1, 2, 45, 1, 540); 
        Arme foudre = new Arme("Foudre", CategorieArme.TOME, 3, 80,
        5, 1, 2, 0, 1, 630); 
        Arme arcBronze = new Arme("Arc Bronze", CategorieArme.ARC, 3, 90, 0, 2, 2, 50, 1, 350);

        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeFer);
        frederick.ajouterObjet(lanceAcier);
        lissa.ajouterObjet(remede);
        robin.ajouterObjet(feu);
        robin.ajouterObjet(foudre);
        vaike.ajouterObjet(arcBronze);

        chrom.equiperArme(epeeFer);
        lissa.equiperArme(remede);
        robin.equiperArme(foudre);
        vaike.equiperArme(arcBronze);

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

        System.out.println("Chrom peut attaquer une cible trop loin : " + game.attaquer(chrom, vaike));
        System.out.println("Chrom : " + game.recupererEtat(chrom));
        System.out.println(vaike.afficherHp());

        game.deplacerUnite(chrom, 3, 2);
        game.attaquer(chrom, vaike);
        System.out.println(vaike.afficherHp());

        game.deplacerUnite(lissa, 1, 2);
        if(game.attendre(lissa)) {
            System.out.println("Lissa attend.");
        }

        game.deplacerUnite(frederick, 2, 3);
        System.out.println("Frederick peut attaquer sans arme equipée : " + game.attaquer(frederick, vaike));
        System.out.println("Frederick : " + game.recupererEtat(frederick));
        System.out.println(vaike.afficherHp());

        game.deplacerUnite(robin, 3, 5);
        System.out.println("Robin peut attaquer avec une arme épuisée : " + game.attaquer(robin, vaike));
        System.out.println("Robin : " + game.recupererEtat(robin));
        System.out.println(vaike.afficherHp());

        robin.equiperArme(feu);
        game.attaquer(robin, vaike);
        System.out.println(vaike.afficherHp());
        System.out.println("Robin : " + game.recupererEtat(robin));
        System.out.println("Robin peut attaquer après avoir terminé son activation : " + game.attaquer(robin, vaike));
        System.out.println(vaike.afficherHp());

        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        System.out.println("Début de la phase ennemie ! ");
        game.attaquer(vaike, frederick);
        game.deplacerUnite(vaike, 4, 3);
        game.attaquer(vaike, frederick);
        System.out.println(frederick.afficherHp());

        System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));
        game.reinitialiserEtat(allUnits);
        System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");

        for(Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
        }
        game.afficherTerrain();
    }
}