import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void afficherNomEtPv(Unite unite) {
        System.out.println(unite.typeUnite() + " " + unite.getName() + " : " + unite.afficherHp());
    }

    public static void afficherEquipe(List<Unite> equipe) {
        for (Unite unite : equipe) {
            afficherNomEtPv(unite);
        }
    }

    public static EtatUnite recupererEtat(Unite unite, Map<Unite, EtatUnite> etats) {
        return etats.get(unite);
    }

    public static boolean peutAgir(Unite unite, Map<Unite, EtatUnite> etats) {
        return ((unite.getCurrentHp() > 0) && (recupererEtat(unite, etats) == EtatUnite.DISPONIBLE));
    }

    public static void main(String[] args) {
        Epeiste chrom = new Epeiste("Chrom", 24, 8);
        Cavalier frederick = new Cavalier("Frederick", 30, 7);
        Mage robin = new Mage("Robin", 22, 9);
        Clerc lissa = new Clerc("Lissa", 18);

        ArrayList<Unite> allies = new ArrayList<>(List.of(chrom, frederick, robin, lissa));

        Epeiste vaike = new Epeiste("Vaike", 25, 6);

        ArrayList<Unite> ennemies = new ArrayList<>(List.of(vaike));

        HashMap<Unite, EtatUnite> etats = new HashMap<>();
        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);

        for (Unite unite : allUnits) {
            etats.put(unite, EtatUnite.DISPONIBLE);
        }

        System.out.println("Troupe alliés : ");
        afficherEquipe(allies);
        System.out.println("\nTroupe ennemis : ");
        afficherEquipe(ennemies);

        chrom.subirDegats(24);
        etats.put(frederick, EtatUnite.TERMINEE);
        for (Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + recupererEtat(unite, etats));
        }

        for (Unite unite : allUnits) {
            System.out.println(unite.getName() + " peut agir : " + peutAgir(unite, etats));
        }

        lissa.soignerCible(chrom, 24);
        etats.put(frederick, EtatUnite.DISPONIBLE);

        if(peutAgir(chrom, etats)) {
            chrom.attaqueRapide(vaike);
            System.out.println("Chrom attaque Vaike.");
            etats.put(chrom, EtatUnite.TERMINEE);
        }
        else {
            System.out.println("Chrom ne peut pas agir.");
        }

        if(peutAgir(chrom, etats)) {
            chrom.attaqueRapide(vaike);
            System.out.println("Chrom attaque Vaike.");
            etats.put(chrom, EtatUnite.TERMINEE);
        }
        else {
            System.out.println("Chrom ne peut pas agir.");
        }
        System.out.println("Vaike : " + vaike.afficherHp());
    }
}

/* 
Travail demandé. Pendant la phase alliée, faire attaquer Chrom sur Vaike avec attaqueRapide(),
uniquement si Chrom peut agir. Une fois l’attaque réalisée, passer Chrom à TERMINEE. Afficher les PV
de Vaike.
Contrainte. Ne pas modifier directement les PV ni ignorer la validation de l’exercice 4. Une tentative de
seconde action de Chrom ne doit rien appliquer.
Validation. Vaike après attaque : 17 / 25 PV ; Chrom est TERMINEE. */