import java.util.ArrayList;
import java.util.List;

public class MainTestCamp {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 3, 2, 3, 3);

 /*        Archer vaike = new Archer("Vaike", 45, 45, 9, 2,
        6, 7, 9, 5, 3, 3, 10); */

        Cavalier frederick = new Cavalier("Frederick", 0, 35, 7, 0,
        6, 4, 6, 7, 2, 4, 5);

        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 10, 1, 350);

        Arme arcAcier = new Arme("Arc Acier", CategorieArme.ARC, 3, 85,
        5, 2, 2, 10, 3, 1680);

        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 4, 80,
        90, 1, 1, 20, 5, 910);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick));

        ArrayList<Unite> ennemies = new ArrayList<>();

        Partie game = new Partie(allies, ennemies, 5, 7);

/*         chrom.ajouterObjet(epeeBronze);
        chrom.equiperArme(epeeBronze);

        frederick.ajouterObjet(lanceAcier);
        frederick.equiperArme(lanceAcier);

        vaike.ajouterObjet(arcAcier);
        vaike.equiperArme(arcAcier); */

        game.afficherTerrain();

        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
        List<Position> casesAccessiblesChrom = game.calculerCasesAccessibles(chrom);
        List<Position> casesAccessiblesFrederick = game.calculerCasesAccessibles(frederick);
        System.out.println("Positions accesibles par Chrom : ");
        for(Position position : casesAccessiblesChrom) {
            System.out.println(position);
        }
        game.afficherCasesAccessibles(chrom);
        System.out.println("Positions accesibles par Frederick : ");
        for(Position position : casesAccessiblesFrederick) {
            System.out.println(position);
        }
        game.afficherCasesAccessibles(frederick);
        System.out.println("Case [2,4] occupée : " + game.caseOccupied(2, 4));
        System.out.println("Chrom se déplace en [2,4] : " + game.deplacerUnite(chrom, 2, 4));
        System.out.println("J'appelle terminerPhaseCourante : " + game.terminerPhaseCourante());
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println(frederick.afficherFiche() + ", état : " + game.recupererEtat(frederick));
        System.out.println("Tour " + game.getNumeroTour() + " - " + game.getCampActif());
    }
}