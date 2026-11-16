import java.util.ArrayList;
import java.util.List;

public class MainTestBFS {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 3, 2, 3, 3);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom));

        ArrayList<Unite> ennemies = new ArrayList<>();

        Partie game = new Partie(allies, ennemies, 5, 7);
        System.out.println("Obstacle en [2, 4] : " + game.placerObstacle(2, 4));
        System.out.println("Obstacle en [1, 3] : " + game.placerObstacle(1, 3));
        System.out.println("Obstacle en [3, 3] : " + game.placerObstacle(3, 3));

        game.afficherTerrain();

        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 10, 1, 350);
        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 4, 80,
        90, 1, 1, 1, 5, 910);
        Arme soin = new Arme("Soin", CategorieArme.BATON, 8, 100,
        0, 1, 1, 1, 1, 600, EffetBaton.SOIN);

        game.afficherCasesAccessibles(chrom);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println("Chrom se déplace en [2,2] : " + game.deplacerUnite(chrom, 2, 2));
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.afficherCasesAccessibles(chrom);
        System.out.println();
        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);

        game.afficherTerrain();

    }
}