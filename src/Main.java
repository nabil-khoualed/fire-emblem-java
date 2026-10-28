import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void afficherNomEtPv(Unite unite) {
        System.out.println(unite.typeUnite() + " " + unite.getName() + " : " + unite.afficherHp());
    }

    public static void afficherEquipe(List<Unite> equipe) {
        for (Unite unite : equipe) {
            afficherNomEtPv(unite);
        }
    }

    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 8);
        Cavalier frederick = new Cavalier("Frederick", 30, 7);
        Mage robin = new Mage("Robin", 22, 9);
        Clerc lissa = new Clerc("Lissa", 18);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Epeiste vaike = new Epeiste("Vaike", 25, 6);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        System.out.println("Troupe alliés : ");
        afficherEquipe(allies);
        System.out.println("\nTroupe ennemis : ");
        afficherEquipe(ennemies);
    }
}