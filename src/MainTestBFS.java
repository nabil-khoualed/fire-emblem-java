import java.util.ArrayList;
import java.util.List;

public class MainTestBFS {
    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 24, 8, 2,
        5, 3, 15, 1, 2, 3, 3);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom));

        ArrayList<Unite> ennemies = new ArrayList<>();

        Partie game = new Partie(allies, ennemies, 5, 7);

        game.afficherTerrain();

        Arme epeeBronze = new Arme("Epee Bronze", CategorieArme.EPEE, 3, 100,
        0, 1, 1, 10, 1, 350);

        chrom.ajouterObjet(epeeBronze);
        chrom.equiperArme(epeeBronze);

        List<Position> casesAccessiblesChrom = game.calculerCasesAccessibles(chrom);
        System.out.println("Nombre de positions accessibles : " + casesAccessiblesChrom.size());
        for(Position position : casesAccessiblesChrom) {
            System.out.println(position);
        }

        System.out.println(chrom.afficherFiche() + ", état : " + game.recupererEtat(chrom));

        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);

        game.afficherTerrain();

    }
}