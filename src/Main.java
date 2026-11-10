import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 8, 5, 1, 1, 3);

        Cavalier frederick = new Cavalier("Frederick", 10, 80, 10, 0,
        30   , 4, 6, 7, 1, 3, 5);

        Mage robin = new Mage("Robin", 22, 22, 3, 9,
        3, 7, 7, 5, 0, 5);

        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 0, 1, 9);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Archer vaike = new Archer("Vaike", 45, 45, 7, 2,
        4, 5, 7, 5, 3, 3, 10);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        Partie game = new Partie(allies, ennemies, 5, 7);

        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 50, 1, 350);

        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 9, 80,
        0, 1, 1, 35, 5, 910);

        Arme soin = new Arme("Soin", CategorieArme.BATON, 8, 100,
        0, 1, 1, 1, 1, 600, EffetBaton.SOIN);

        Arme cure = new Arme("Cure", CategorieArme.BATON, 15, 100,
        0, 1, 1, 20, 4, 1000, EffetBaton.SOIN);

        Arme restitution = new Arme("Restitution", CategorieArme.BATON, 0, 100,
        0, 1, 1, 15, 8, 1950, EffetBaton.SOIN_COMPLET);

        Arme epeeFer = new Arme("Epee fer", CategorieArme.EPEE, 5, 95,
        0, 1, 1, 1, 3, 520);

        Arme feu = new Arme("Feu", CategorieArme.TOME, 2, 90,
        0, 1, 2, 45, 1, 540); 

        Arme foudre = new Arme("Foudre", CategorieArme.TOME, 3, 80,
        5, 1, 2, 0, 1, 630); 

        Arme arcBronze = new Arme("Arc Bronze", CategorieArme.ARC, 6, 90, 0, 2, 2, 50, 1, 350);

        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeFer);
        frederick.ajouterObjet(lanceAcier);
        lissa.ajouterObjet(soin);
        lissa.ajouterObjet(cure);
        lissa.ajouterObjet(restitution);
        robin.ajouterObjet(feu);
        robin.ajouterObjet(foudre);
        vaike.ajouterObjet(arcBronze);

        chrom.equiperArme(epeeFer);
        lissa.equiperArme(soin);
        System.out.println(lissa.afficherFiche());
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
        System.out.println(chrom.getArmeEquipee().afficherFiche());
        game.attaquer(chrom, vaike);
        System.out.println(vaike.afficherHp());
        System.out.println(chrom.getArmeEquipee().afficherFiche());

        game.deplacerUnite(lissa, 1, 2);
        System.out.println("Lissa peut attaquer Vaike avec un baton : " + game.attaquer(lissa, vaike));
        System.out.println("Lissa : " + game.recupererEtat(lissa));
        System.out.println(vaike.afficherHp());

        if(game.attendre(lissa)) {
            System.out.println("Lissa attend.");
        }

        game.deplacerUnite(frederick, 2, 3);
        System.out.println("Frederick peut attaquer sans arme equipée : " + game.attaquer(frederick, vaike));
        System.out.println("Frederick : " + game.recupererEtat(frederick));
        System.out.println(vaike.afficherHp());

        if(game.attendre(frederick)) {
            System.out.println("Frederick attend.");
        }

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
        game.deplacerUnite(chrom, 4, 2);
        game.attaquer(chrom, vaike);
        System.out.println(vaike.afficherHp());
        System.out.println(chrom.getArmeEquipee().afficherFiche());
        System.out.println("");


        System.out.println(lissa.afficherFiche());
        System.out.println(frederick.afficherFiche());

        System.out.println("--- Série de tests méthode SOIGNER ---");
        System.out.println("Lissa : " + game.recupererEtat(lissa));
        System.out.println("TEST 6 - Allié trop éloigné : " + game.soigner(lissa, frederick));
        game.deplacerUnite(lissa, 1, 3);
        System.out.println("TEST 1 - Soin : " + game.soigner(lissa, frederick));
        System.out.println(frederick.afficherFiche());
        System.out.println("TEST 8 - Soin après activation : " + game.soigner(lissa, frederick));

        game.reinitialiserEtat(allUnits);
        System.out.println("TEST 7 - Bâton épuisé : " + game.soigner(lissa, frederick));
        System.out.println(frederick.afficherFiche());
        lissa.equiperArme(cure);
        System.out.println(lissa.afficherFiche());
        System.out.println("TEST 2 - Cure : " + game.soigner(lissa, frederick));
        System.out.println(frederick.afficherFiche());

        game.reinitialiserEtat(allUnits);
        lissa.equiperArme(restitution);
        System.out.println(lissa.afficherFiche());
        System.out.println("TEST 3 - Restitution : " + game.soigner(lissa, frederick));
        System.out.println(frederick.afficherFiche());

        game.reinitialiserEtat(allUnits);
        System.out.println("TEST 4 - PV max : " + game.soigner(lissa, frederick));
        System.out.println(frederick.afficherFiche());

        game.deplacerUnite(lissa, 4, 4);
        System.out.println(lissa.afficherFiche());
        System.out.println(vaike.afficherFiche());
        System.out.println("TEST 6 - Soin ennemi : " + game.soigner(lissa, vaike));
        System.out.println(vaike.afficherFiche());
    }
}