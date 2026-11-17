import java.util.ArrayList;
import java.util.List;

public class MainTestCamp {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 3, 2, 3, 3);

        Archer vaike = new Archer("Vaike", 45, 45, 9, 2,
        6, 7, 9, 5, 3, 3, 10);

        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 0, 1, 9);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom));

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        Partie game = new Partie(allies, ennemies, 5, 7);
        game.placerObstacle(2, 4);
        game.placerObstacle(1, 3);
        game.placerObstacle(3, 3);

        game.afficherTerrain();

        System.out.println("Camp actif : " + game.getCampActif());
        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 10, 1, 350);
        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 4, 80,
        90, 1, 1, 1, 5, 910);
        Arme soin = new Arme("Soin", CategorieArme.BATON, 8, 100,
        0, 1, 1, 1, 1, 600, EffetBaton.SOIN);

        System.out.println("Chrom peut agir : " + game.peutAgir(chrom));
        System.out.println("Chrom peut se déplacer : " + game.peutSeDeplacer(chrom));

        System.out.println("Vaike peut agir : " + game.peutAgir(vaike));
        System.out.println("Vaike peut se déplacer : " + game.peutSeDeplacer(vaike));
        System.out.println(vaike.afficherFiche() + ", état : " + game.recupererEtat(vaike));

        System.out.println("Chrom attend. " + game.attendre(chrom));
        System.out.println("Chrom peut agir : " + game.peutAgir(chrom));
        System.out.println("Chrom peut se déplacer : " + game.peutSeDeplacer(chrom));

        System.out.println("Lissa peut agir : " + game.peutAgir(lissa));
        System.out.println("Lissa peut se déplacer : " + game.peutSeDeplacer(lissa));

        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println("Chrom se déplace en [2,2] : " + game.deplacerUnite(chrom, 2, 2));
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println();
        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);

        game.afficherTerrain();

    }
}