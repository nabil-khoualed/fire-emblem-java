import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 5, 1, 1, 3);

        Cavalier frederick = new Cavalier("Frederick", 5, 35, 7, 0,
        6, 4, 6, 7, 1, 3, 5);

        Mage robin = new Mage("Robin", 30, 30, 3, 9,
        3, 7, 4, 5, 0, 5);

        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 0, 1, 9);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Archer vaike = new Archer("Vaike", 45, 45, 9, 2,
        6, 7, 9, 5, 3, 3, 10);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        Partie game = new Partie(allies, ennemies, 5, 7);

        System.out.println("Obstacle en [2, 2] : " + game.placerObstacle(2, 2));
        System.out.println("Obstacle en [2, 3] : " + game.placerObstacle(2, 3));
        System.out.println("Obstacle en [8, 8] : " + game.placerObstacle(8, 8));
        System.out.println("Case bloquée en [2, 2] : " + game.caseBloquee(2, 2));
        System.out.println("Retirer obstacle en [2, 2] : " + game.retirerObstacle(2, 2));
        System.out.println("Case bloquée en [2, 2] : " + game.caseBloquee(2, 2)); 
        System.out.println("Placer obstacle sur une case occupée par une unité : " + game.placerObstacle(1, 1));
        game.afficherTerrain();


        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 10, 1, 350);

        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 4, 80,
        90, 1, 1, 1, 5, 910);

        Arme soin = new Arme("Soin", CategorieArme.BATON, 8, 100,
        0, 1, 1, 1, 1, 600, EffetBaton.SOIN);

        Arme cure = new Arme("Cure", CategorieArme.BATON, 15, 100,
        0, 1, 1, 20, 4, 1000, EffetBaton.SOIN);

        Arme restitution = new Arme("Restitution", CategorieArme.BATON, 0, 100,
        0, 1, 1, 15, 8, 1950, EffetBaton.SOIN_COMPLET);

        Arme epeeFer = new Arme("Epee fer", CategorieArme.EPEE, 5, 95,
        0, 1, 1, 30, 3, 520);

        Arme feu = new Arme("Feu", CategorieArme.TOME, 2, 90,
        0, 1, 2, 1, 15, 540); 

        Arme foudre = new Arme("Foudre", CategorieArme.TOME, 3, 10,
        25, 1, 2, 5, 1, 630); 

        Arme arcLetal = new Arme("Arc Létal", CategorieArme.ARC, 4, 80,
        30, 2, 2, 5, 10, 1680);

        Arme arcAcier = new Arme("Arc Acier", CategorieArme.ARC, 3, 85,
        5, 2, 2, 10, 3, 1680);

        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeFer);
        frederick.ajouterObjet(lanceAcier);
        lissa.ajouterObjet(soin);
        lissa.ajouterObjet(cure);
        lissa.ajouterObjet(restitution);
        robin.ajouterObjet(feu);
        robin.ajouterObjet(foudre);
        vaike.ajouterObjet(arcLetal);
        vaike.ajouterObjet(arcAcier);

        chrom.equiperArme(epeeBronze);
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
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.deplacerUnite(chrom, 3, 2);
        game.attaquer(chrom, vaike);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        System.out.println();

        game.deplacerUnite(lissa, 0, 3);
        game.soigner(lissa, frederick);
        System.out.println(frederick.afficherHp());
        System.out.println(lissa.afficherFiche() + ", état : " + game.recupererEtat(lissa));
        System.out.println();

        game.deplacerUnite(frederick, 4, 3);
        game.attaquer(frederick, vaike);
        System.out.println(frederick.afficherFiche() + ", état : " + game.recupererEtat(frederick));
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        System.out.println();

        game.deplacerUnite(robin, 3, 5);
        game.attaquer(robin, vaike);
        System.out.println(robin.afficherFiche() + ", état : " + game.recupererEtat(robin));
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        System.out.println();

        System.out.println("Phase alliée terminée : " + game.phaseTerminee(allies));
        System.out.println("Début de la phase ennemie ! ");

        if(game.attaquer(vaike, robin)) {
            System.out.println(robin.afficherFiche() + ", état : " + game.recupererEtat(robin));
            System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        }
        else {
            game.attendre(vaike);
        }

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
        System.out.println("Début de la phase alliée ! ");
        game.attaquer(robin, vaike);
        System.out.println(robin.afficherFiche() + ", état : " + game.recupererEtat(robin));
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        System.out.println();

        game.finirPhaseEquipe(allies);
        System.out.println("Début de la phase ennemie ! ");
    }
}