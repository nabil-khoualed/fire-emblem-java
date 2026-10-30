import java.util.List;

public class Partie {
    private List<Unite> allies;
    private List<Unite> ennemies;

    Partie(List<Unite> allies, List<Unite> ennemies) {
        this.allies = allies;
        this.ennemies = ennemies;
    }

    public List<Unite> getListAllies() {
        return allies;
    }

    public List<Unite> getListEnnemies() {
        return ennemies;
    }
    
}

/* 
Travail demandé. Créer une classe Partie chargée de conserver les équipes alliée et ennemie. Son
constructeur reçoit les deux collections d’unités. Ajouter des méthodes permettant de récupérer
chacune des équipes. Depuis Main, créer une partie avec les cinq personnages existants.
Contrainte. Ne pas recréer les personnages dans Partie. Ne pas supprimer les sous-classes de
Unite. Choisir des types de paramètres adaptés aux collections.
Validation. Une partie contient quatre alliés et un ennemi ; leur ordre d’affichage est conservé. */