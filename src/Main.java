import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 8, 5, 1, 1, 3);

        Cavalier frederick = new Cavalier("Frederick", 5, 35, 10, 0,
        6, 4, 6, 7, 1, 3, 5);

        Mage robin = new Mage("Robin", 15, 15, 3, 9,
        3, 7, 7, 5, 0, 5);

        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 0, 1, 9);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Archer vaike = new Archer("Vaike", 45, 45, 9, 2,
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
        0, 1, 1, 30, 3, 520);

        Arme feu = new Arme("Feu", CategorieArme.TOME, 2, 90,
        0, 1, 2, 45, 1, 540); 

        Arme foudre = new Arme("Foudre", CategorieArme.TOME, 3, 80,
        5, 1, 2, 30, 1, 630); 

        Arme arcLetal = new Arme("Arc Létal", CategorieArme.ARC, 10, 80, 30, 2, 2, 30, 10, 1680);

        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeFer);
        frederick.ajouterObjet(lanceAcier);
        lissa.ajouterObjet(soin);
        lissa.ajouterObjet(cure);
        lissa.ajouterObjet(restitution);
        robin.ajouterObjet(feu);
        robin.ajouterObjet(foudre);
        vaike.ajouterObjet(arcLetal);

        chrom.equiperArme(epeeFer);
        frederick.equiperArme(lanceAcier);
        lissa.equiperArme(soin);
        robin.equiperArme(foudre);
        vaike.equiperArme(arcLetal);

        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);

        System.out.println("");
        System.out.println("Troupe alliés : ");
        game.afficherEquipeAllies();
        System.out.println("\nTroupe ennemis : ");
        game.afficherEquipeEnnemies();
        System.out.println("");

        game.afficherTerrain();

        System.out.println("Début de la phase alliée ! ");

        System.out.println("Chrom tente d'attaquer Vaike à distance : " + game.attaquer(chrom, vaike));
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.deplacerUnite(chrom, 3, 2);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.attaquer(chrom, vaike);
        System.out.println(vaike.afficherHp());
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println();

        game.deplacerUnite(lissa, 0, 3);
        System.out.println(lissa.afficherFiche() + ", état : " + game.recupererEtat(lissa));
        System.out.println(vaike.afficherHp());
        game.soigner(lissa, frederick);
        System.out.println(frederick.afficherHp());
        System.out.println(lissa.afficherFiche() + ", état : " + game.recupererEtat(lissa));
        System.out.println();

        if(game.attendre(frederick)) {
            System.out.println("Frederick attend.");
        }
        System.out.println(frederick.afficherFiche() + ", état : " + game.recupererEtat(frederick));
        System.out.println();

        game.deplacerUnite(robin, 3, 5);
        game.attaquer(robin, vaike);
        System.out.println(vaike.afficherHp());
        System.out.println(robin.afficherFiche() + ", état : " + game.recupererEtat(robin));
        System.out.println();

        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        System.out.println("Début de la phase ennemie ! ");
        game.attaquer(vaike, robin);
        System.out.println(robin.afficherHp());
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        System.out.println();

        System.out.println("Phase ennemie terminée : " + game.phaseTerminee(ennemies));

        for(Unite unite : allUnits) {
            System.out.println(unite.afficherFiche() + ", état : " + game.recupererEtat(unite));
        }
        System.out.println();

        game.reinitialiserEtat(allUnits);
        System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");
        System.out.println();

        for(Unite unite : allUnits) {
            System.out.println(unite.afficherFiche() + ", état : " + game.recupererEtat(unite));
        }
        System.out.println();

        game.afficherTerrain();
    }
}

/* 
Actuellement, le système ne prend pas en compte la précision des armes, ni leur taux de critique. 
La vitesse des personnages n'influe aucune méthode actuelle.
Il n'y a pas encore d'obstacles sur la carte, les unités mortes restent sur le terrain, et constitue des obstacles. Aucune condition de fin de partie n'existe.
*/