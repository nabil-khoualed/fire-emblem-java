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

    public static boolean peutAgir(Unite unite, Map<Unite, EtatUnite> etats) {
        return unite.getCurrentHp() > 0
                && etats.get(unite) == EtatUnite.DISPONIBLE;
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
            if(etats.get(unite) != EtatUnite.TERMINEE) {
                return false;
            }
        }
        return true;
    }

    public static void reinitialiserEtat(List<Unite> equipe, Map<Unite, EtatUnite> etats) {
        for(Unite unite : equipe) {
            if(unite.getCurrentHp() > 0) {
                etats.put(unite, EtatUnite.DISPONIBLE);
            }
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

        Partie game = new Partie(allies, ennemies);

        ArrayList<Unite> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(ennemies);


        System.out.println("Troupe alliés : ");
        afficherEquipe(game.getListAllies());
        System.out.println("\nTroupe ennemis : ");
        afficherEquipe(game.getListEnnemies());
        System.out.println("");

        chrom.subirDegats(24);
        game.modifierEtat(frederick, EtatUnite.TERMINEE);
        for (Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
        }

        for (Unite unite : allUnits) {
            System.out.println(unite.getName() + " peut agir : " + peutAgir(unite, game.getEtats()));
        }

        lissa.soignerCible(chrom, 24);
        game.modifierEtat(frederick, EtatUnite.DISPONIBLE);

        if(peutAgir(chrom, game.getEtats())) {
            chrom.attaqueRapide(vaike);
            System.out.println("Chrom attaque Vaike.");
            System.out.println("Vaike après attaque : " + vaike.afficherHp());
            game.modifierEtat(chrom, EtatUnite.TERMINEE);
        }
        else {
            System.out.println("Chrom ne peut pas agir.");
        }

        if(peutAgir(chrom, game.getEtats())) {
            chrom.attaqueRapide(vaike);
            System.out.println("Chrom attaque Vaike.");
            game.modifierEtat(chrom, EtatUnite.TERMINEE);

        }
        else {
            System.out.println("Chrom ne peut pas agir.");
        }
        System.out.println("Vaike : " + vaike.afficherHp());
        attendre(frederick, game.getEtats());
        attendre(robin, game.getEtats());

        chrom.subirDegats(6);
        if(peutAgir(lissa, game.getEtats())) {
            lissa.soignerCible(chrom, 5);
            game.modifierEtat(lissa, EtatUnite.TERMINEE);

        }
        System.out.println("Chrom après dégâts et soin : " + chrom.afficherHp());
        System.out.println("Phase alliée terminée : " + phaseTerminee(allies, game.getEtats()));

        if(peutAgir(vaike, game.getEtats())) {
            vaike.attaqueRapide(chrom);
            game.modifierEtat(vaike, EtatUnite.TERMINEE);

        }

        System.out.println("Frederick tombe dans un piège mortel !");
        frederick.subirDegats(99);
        System.out.println("Frederick après le drame : " + frederick.afficherHp());
        System.out.println("Chrom après phase ennemie : " + chrom.afficherHp());
        System.out.println("Phase ennemie terminée : " + phaseTerminee(ennemies, game.getEtats()));
        reinitialiserEtat(allUnits, game.getEtats());
        System.out.println("Début du tour suivant : unités vivantes DISPONIBLE");
        for(Unite unite : allUnits) {
            System.out.println(unite.getName() + " : " + game.recupererEtat(unite));
        }
    }
}

/* 
Travail demandé. Créer une méthode réinitialisant à DISPONIBLE l’état des unités vivantes de l’équipe
reçue. Après les deux phases terminées, réinitialiser les deux équipes et afficher leurs états. Organiser
les appels pour que le déroulement complet reste lisible dans main (ou dans une classe Partie
facultative).
Contrainte. Ne pas réanimer d’unité à 0 PV ; ne pas effacer les PV ni recréer les personnages. Le deuxième
tour n’a pas besoin d’être simulé.
Validation. Chrom conserve 17/24 PV, Vaike 17/25 PV ; tous les personnages vivants sont de nouveau
DISPONIBLE. */