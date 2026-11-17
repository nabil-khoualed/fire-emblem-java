# Idées pour le RPG tactique Java — Fire Emblem / Dofus

> **Statut : carnet d'idées, pas un cahier des charges définitif.**  
> Ces mécaniques sont envisagées pour les versions futures. Priorité actuelle : terminer le BFS du **TP16**, puis obtenir un jeu **JavaFX jouable**.

## 1. Décisions déjà prises

- **Défaite d'une unité pendant la bataille :** lorsqu'elle tombe à **0 PV**, elle est considérée comme vaincue et **ne bloque plus les cases** du terrain.
- **Après le combat :** les unités vaincues du joueur **reviennent** (inspiration du mode débutant de *Fire Emblem Awakening*). Pas de mort permanente dans les règles retenues pour le moment.
- **Résurrection pendant le combat :** pas de sort de résurrection ordinaire prévu pour l'instant, mais possibilité d'introduire une mécanique spéciale de **nécromancie**.
- **Déplacements tactiques (TP16) :** une unité traverse les cases occupées par ses alliés, sans pouvoir s'y arrêter ; les ennemis et obstacles infranchissables bloquent le passage.

## 2. Idées de mécaniques

### A. Renforts et apparitions d'ennemis

- Faire apparaître de **nouveaux ennemis en cours de bataille**, selon le numéro du tour ou un événement.
- Définir des **points d'apparition** sur la carte (portes, forts, bords du terrain, portails, etc.).
- Prévoir un message ou un indice avertissant le joueur de l'arrivée de renforts.
- Question à trancher : les unités nouvellement apparues peuvent-elles agir immédiatement, ou seulement au tour suivant ?

### B. Troisième équipe / factions supplémentaires

- Permettre à une **troisième faction** d'entrer sur la carte en cours de partie.
- Cette faction peut être **alliée, neutre ou hostile** envers le joueur et les autres groupes.
- Une faction peut changer de relation avec une autre durant le scénario.
- Enjeu technique : ne pas supposer qu'une bataille contient toujours uniquement les listes `allies` et `ennemies`.

### C. Recrutement et changement de camp

- Un ennemi ou une faction neutre peut **rejoindre l'équipe du joueur** en remplissant certaines conditions.
- Inversement, une unité alliée peut **passer dans le camp adverse**.
- Les conditions possibles : dialogue, objectif de mission, événement scénaristique ou capacité spéciale.
- Il faut distinguer **l'équipe d'origine** de la **faction actuellement contrôlante**.

### D. Corruption / contrôle mental

- Un personnage ennemi peut **corrompre des unités une par une** pour les faire combattre contre leurs anciens alliés.
- Variantes à envisager : corruption **temporaire**, **réversible** ou **définitive jusqu'à la fin du combat**.
- Possibilité de libérer les unités corrompues en battant le responsable ou en accomplissant un objectif.
- À définir : est-ce que la corruption fonctionne sur toutes les classes, nécessite une portée précise ou peut être résistée ?

### E. Nécromancie — inspiration « Roi de la Nuit » (*Game of Thrones*)

**Idée centrale :** un boss ou un ennemi spécial peut **ressusciter les unités tombées au combat** et les faire **changer de camp**.

Exemple de scénario :

1. Robin tombe à **0 PV** et quitte le plateau.
2. Un nécromancien ennemi lance **« Armée des morts »**.
3. Robin revient sur une case valide, éventuellement proche de son lieu de défaite.
4. Robin conserve une partie de son identité/statistiques, mais combat **pour l'ennemi**.
5. Chrom doit affronter son ancien allié ou chercher à le libérer.

Variantes possibles :

- Le boss réanime **jusqu'à deux unités vaincues au début de chaque tour**.
- Il peut relever les morts des **deux camps**.
- Vaincre le nécromancien **libère** les unités sous son contrôle ou met fin à leurs réanimations.
- Les unités réanimées peuvent recevoir une **apparence visuelle distincte**, des malus ou des capacités nouvelles.
- Les morts deviennent une **ressource tactique** pour l'ennemi : plus le combat dure, plus son armée peut grossir.

**Questions ouvertes :** faut-il que le personnage réanimé retrouve sa faction d'origine à la fin du combat ? Revient-il à la vie immédiatement après la défaite du boss ? Où apparaît-il si sa dernière case est occupée ?

## 3. Piste d'architecture Java

**Ne rien refactoriser tout de suite :** ce sont des pistes pour faciliter les mécaniques futures.

### Distinguer trois notions

| Notion | Exemple | Évolutive ? |
|---|---|---|
| Équipe d'origine | Chrom appartient initialement au joueur | Généralement non |
| Faction actuelle | Chrom est temporairement contrôlé par l'ennemi | Oui |
| État de l'unité | Vivante, vaincue, réanimée | Oui |

Énumérations envisageables :

```java
// Proposition d'architecture future — ne pas ajouter pendant le TP16.
enum Faction {
    JOUEUR,
    ENNEMI,
    NEUTRE
}

enum StatutUnite {
    VIVANT,
    VAINCU,
    REANIME
}
```

### Gestion des unités vaincues

- **À court terme :** conserver les unités dans leurs listes actuelles et considérer `getCurrentHp() == 0` comme une absence du terrain. `getUniteAt()` et `caseOccupied()` doivent alors ignorer les unités vaincues.
- **À terme :** distinguer les unités appartenant à une faction et celles **actuellement présentes sur le plateau**.
- Envisager un registre des unités vaincues, avec leur **faction d'origine** et éventuellement leur **dernière position**.
- **Éviter** de déplacer artificiellement les morts en `[-1,-1]` ou vers un « cimetière » hors carte : les coordonnées représentent une position, pas un état de vie.
- Éviter les listes redondantes qui risquent de se désynchroniser ; centraliser les opérations de défaite, réanimation et changement de faction.
- Une référence Java vers une unité supprimée d'une liste continue d'exister : les actions devront vérifier sa présence ou son statut.

### Conséquences pour le BFS

- Les relations « allié / adversaire » doivent être évaluées **du point de vue de l'unité qui se déplace**, et à terme selon sa **faction actuelle**.
- **Allié vivant :** traversable, destination interdite.
- **Adversaire vivant :** ni traversable ni destination autorisée.
- **Unité vaincue :** ne bloque pas une case.
- **Obstacle infranchissable :** bloque le passage.

## 4. Ordre de développement recommandé

| Étape | Priorité |
|---|---|
| **TP16 (en cours)** — BFS, obstacles, traversée des alliés | Maintenant |
| **TP17** — tours, phases, conditions de victoire et première réflexion sur les factions | Prochaine étape |
| **TP18** — plateau JavaFX, sélection et déplacement à la souris | Priorité forte |
| Renforts et points de spawn | Après le premier prototype jouable |
| Factions multiples, recrutement et changement de camp | Ensuite |
| Corruption, contrôle mental et nécromancie | Fonctionnalités avancées |

## 5. Principes à conserver

- **Progressivité pédagogique :** petits exercices façon TP de L1 Java, sans fournir le code complet d'emblée.
- **Éviter les développements prématurés :** privilégier un jeu graphique fonctionnel avant les systèmes complexes.
- **Réutiliser le moteur de combat existant** (précision, critiques, ripostes, doubles frappes, durabilité).
- **Faire un commit et un push après chaque exercice terminé et validé.**

---

*Document évolutif : ajouter ici les nouvelles mécaniques imaginées au fil des TP.*
