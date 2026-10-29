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

    public static boolean attendre(Unite unite, Map<Unite, EtatUnite> etats) {
        if(peutAgir(unite, etats)) {
            etats.put(unite, EtatUnite.TERMINEE);
            return true;
        }
        return false;
    }

    public static boolean phaseTerminee(List<Unite> equipe, Map<Unite, EtatUnite> etats) {
        for (Unite unite : equipe) {
            if(recupererEtat(unite, etats) != EtatUnite.TERMINEE) {
                return false;
            }
        }
        return true;
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
        attendre(frederick, etats);
        attendre(robin, etats);
        System.out.println(frederick.getName() + " : " + recupererEtat(frederick, etats));
        System.out.println(robin.getName() + " : " + recupererEtat(robin, etats));


        System.out.println("Phase alliée terminée : " + phaseTerminee(allies, etats));
        chrom.subirDegats(6);
        if(peutAgir(lissa, etats)) {
            lissa.soignerCible(chrom, 5);
            etats.put(lissa, EtatUnite.TERMINEE);
        }
        System.out.println("Chrom après dégâts et soin : " + chrom.afficherHp());
        System.out.println("Phase alliée terminée : " + phaseTerminee(allies, etats));
    }
}

/* 
Travail demandé. Créer une vérification globale qui reçoit une équipe et les états, et renvoie vrai
seulement si toutes les unités de cette équipe sont TERMINEE. L’utiliser à la fin des quatre actions
alliées.
Contrainte. Ne pas supposer qu’une équipe contient toujours quatre unités ; ne pas tenir compte des états
ennemis pour décider si la phase alliée est terminée.
Validation. Phase alliée terminée : true. Avant l’action de Lissa, la même vérification doit renvoyer false. */