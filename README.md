# Fire Emblem — Tactical RPG (Java)

Projet personnel de RPG tactique au tour par tour, inspiré
des mécaniques de combat de Fire Emblem Awakening.

## Objectifs

- Développer un système de combat au tour par tour.
- Gérer plusieurs unités alliées et ennemies.
- Implémenter les déplacements sur une grille.
- Ajouter les attaques, les soins et les objets.
- Développer une IA adverse.
- Créer une interface graphique.
- Ajouter un mode multijoueur 1 contre 1.

## Technologies

- Java
- Git / GitHub

## État du projet

**TP1 à TP14 terminés et validés** — moteur de bataille en console opérationnel pour les mécanismes de base.

Fonctionnalités déjà implémentées :

- Plusieurs classes de personnages : Épéiste, Cavalier, Combattant, Archer, Mage et Clerc.
- Grille, positions, distance de Manhattan, déplacement limité par la statistique `movement`.
- Deux camps, états `DISPONIBLE` / `DEPLACEE` / `TERMINEE` et alternance des phases.
- Inventaire (5 objets), restrictions d'équipement par classe et niveau, armes à durabilité limitée.
- Attaques physiques et magiques utilisant les caractéristiques des unités et la puissance des armes.
- Conditions d'attaque (équipe opposée, portée, cible vivante, arme disponible).
- Bâtons à effets déclarés ; soins simples et complets fonctionnels, avec portée, durabilité et fin d'activation.
- Scénario de démonstration dans `Main.java`, incluant des attaques, un soin et une mise KO.

**Non implémenté à ce stade :** précision et esquive, critiques, ripostes, doubles frappes, triangle et efficacité des armes, effets spéciaux des bâtons, obstacles/pathfinding, disparition des unités KO, victoire, IA, JavaFX, multijoueur.

**Prochaine étape :** préparation du TP15 — combat avancé (précision, esquive, critiques, contre-attaques et doubles frappes).

## Compiler et lancer

Depuis la racine du projet :

```bash
javac -d out src/*.java
java -cp out Main
```

## Auteur

Nabil Khoualed