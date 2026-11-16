import java.util.ArrayList;
import java.util.List;

public class MainTestBFS {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 3, 2, 3, 3);

        Cavalier vaike = new Cavalier("Vaike", 35, 35, 7, 0,
        6, 4, 6, 7, 2, 5, 5);

        Clerc lissa = new Clerc("Lissa", 18, 18, 0, 7,
        2, 8, 7, 5, 3, 4, 9);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom));

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike, lissa));

        Partie game = new Partie(allies, ennemies, 5, 7);

        game.afficherTerrain();

        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 10, 1, 350);
        Arme lanceAcier = new Arme("Lance Acier", CategorieArme.LANCE, 4, 80,
        90, 1, 1, 1, 5, 910);
        Arme soin = new Arme("Soin", CategorieArme.BATON, 8, 100,
        0, 1, 1, 1, 1, 600, EffetBaton.SOIN);

        vaike.ajouterObjet(lanceAcier);
        vaike.equiperArme(lanceAcier);
        chrom.ajouterObjet(epeeBronze);
        chrom.equiperArme(epeeBronze);
        lissa.ajouterObjet(soin);
        lissa.equiperArme(soin);

        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println("Chrom se déplace en [2,4] : " + game.deplacerUnite(chrom, 2, 4));
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        game.attaquer(chrom, vaike);
        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));
        System.out.println(lissa.afficherFiche() + ", état : " + game.recupererEtat(lissa));
        game.deplacerUnite(lissa, 3, 5);
        System.out.println(lissa.afficherFiche() + ", état : " + game.recupererEtat(lissa));
        game.soigner(lissa, vaike);
        System.out.println(lissa.afficherFiche() + ", état : " + game.recupererEtat(lissa));


        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);

        game.afficherTerrain();

    }
}