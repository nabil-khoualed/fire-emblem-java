import java.util.ArrayList;
import java.util.List;

public class MainTestEX9 {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 3, 1, 1, 3);

        Cavalier frederick = new Cavalier("Frederick", 35, 35, 7, 0,
        7, 4, 4, 6, 1, 3, 5);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick));

        Archer vaike = new Archer("Vaike", 45, 45, 9, 2,
        6, 7, 10, 7, 3, 3, 10);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        Partie game = new Partie(allies, ennemies, 5, 7);

        System.out.println("Obstacle en [2, 2] : " + game.placerObstacle(2, 2));
        System.out.println("Obstacle en [2, 3] : " + game.placerObstacle(2, 3));
        System.out.println("Obstacle en [2, 4] : " + game.placerObstacle(2, 4));

        game.afficherTerrain();


        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 4, 1, 350);

        Arme epeeDestinee = new Arme("Epee de la Destinée", CategorieArme.EPEE,
        30, 50, 0, 1, 1, 1, 1, 350);

        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 4, 80,
        10, 1, 1, 20, 5, 910);

        Arme epeeFer = new Arme("Epee fer", CategorieArme.EPEE, 5, 95,
        0, 1, 1, 30, 3, 520);

        Arme arcLetal = new Arme("Arc Létal", CategorieArme.ARC, 8, 100,
        99, 2, 2, 1, 10, 1680);

        Arme arcAcier = new Arme("Arc Acier", CategorieArme.ARC, 3, 85,
        5, 2, 2, 10, 3, 1680);

        chrom.ajouterObjet(epeeBronze);
        chrom.ajouterObjet(epeeFer);
        chrom.ajouterObjet(epeeDestinee);
        frederick.ajouterObjet(lanceAcier);

        vaike.ajouterObjet(arcLetal);
        vaike.ajouterObjet(arcAcier);

        chrom.equiperArme(epeeBronze);
        frederick.equiperArme(lanceAcier);
        vaike.equiperArme(arcLetal);

        System.out.println("");
        System.out.println("Troupe alliés : ");
        game.afficherEquipeAllies();
        System.out.println("\nTroupe ennemis : ");
        game.afficherEquipeEnnemies();
        System.out.println("");

        game.afficherTerrain();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        System.out.println("Cases accessibles par Chrom : ");
        game.afficherCasesAccessibles(chrom);
        game.deplacerUnite(chrom, 3, 2);
        game.attaquer(chrom, vaike);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println();
        
        game.deplacerUnite(frederick, 3, 4);
        game.attaquer(frederick, vaike);
        System.out.println(frederick.afficherFiche() + ", état : " + game.recupererEtat(frederick));
        game.terminerPhaseCourante();
        System.out.println();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(vaike, 1, 4);
        game.attaquer(vaike, frederick);
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        game.terminerPhaseCourante();
        game.afficherTerrain();
        System.out.println();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(chrom, 3, 5);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.finirPhaseEquipe();
        System.out.println(frederick.afficherFiche() + ", état : " + game.recupererEtat(frederick));
        System.out.println();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.attaquer(vaike, frederick);
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        game.terminerPhaseCourante();
        game.afficherTerrain();
        System.out.println();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(chrom, 1, 5);
        chrom.equiperArme(epeeDestinee);
        game.attaquer(chrom, vaike);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.terminerPhaseCourante();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(vaike, 1, 3);
        game.attaquer(vaike, chrom);
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        game.terminerPhaseCourante();
        game.afficherTerrain();
        System.out.println();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(chrom, 1, 4);
        game.attaquer(chrom, vaike);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.terminerPhaseCourante();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(vaike, 1, 2);
        game.attaquer(vaike, chrom);
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        game.terminerPhaseCourante();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(chrom, 1, 3);
        game.attaquer(chrom, vaike);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.terminerPhaseCourante();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        game.deplacerUnite(vaike, 1, 1);
        game.attaquer(vaike, chrom);
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));
        game.terminerPhaseCourante();
        
        System.out.println("Résultat de la partie : " + game.determinerResultat());
    }
}