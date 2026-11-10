# Fire Emblem Java — Cahier des charges et décisions de conception

> **Dernière mise à jour : 10 novembre 2026**  
> **Statut :** document de référence évolutif — **TP1 à TP14 terminés, préparation du TP15**.  
> **Origine :** synthèse de nos échanges. Les décisions sont distinguées des propositions encore ouvertes.  
> **Jeu visé :** RPG tactique 2D inspiré de *Fire Emblem: Awakening*, avec mode solo et surtout **PvP 1 contre 1 sur deux ordinateurs**, dans l'esprit des affrontements tactiques rejouables du Kolizéum de *Dofus*.

## 1. Vision du projet

- **Priorité absolue :** obtenir à terme **un jeu amusant**, pas seulement une démonstration de programmation.
- **Gameplay :** combats stratégiques au tour par tour, diversité de compositions, armes, soins, compétences et choix tactiques.
- **Durée de vie :** **pas** de longue campagne de 50 h. Privilégier des batailles complètes, rejouables et équilibrées.
- **Modes :** solo contre une IA et PvP **1v1, un joueur par PC** ; **pas** de 2v2 à quatre joueurs.
- **Approche :** construire progressivement les mécaniques, tester régulièrement leur intérêt ludique, et conserver une architecture Java maintenable.
- **Style du projet :** s'inspirer d'Awakening plutôt que le copier à l'identique. Pas de dizaines de classes ni de cinématiques.

**Légende :** ✅ retenu · ❌ exclu · 🟡 ouvert / à définir · ⏳ reporté à plus tard.

## 2. Déroulement d'une bataille

| Fonctionnalité                               | Décision | Précisions                                                                  |
|----------------------------------------------|----------|-----------------------------------------------------------------------------|
| Carte quadrillée                             | ✅        | Grille 2D, dimensions variables.                                            |
| Alternance des camps                         | ✅        | Phases alliée et ennemie ; chacun joue ses unités.                          |
| Déplacer puis agir                           | ✅        | Déplacement suivi d'une action ; attaque directe sans déplacement possible. |
| Attaquer, soigner, objet, échanger, attendre | ✅        | Menu d'actions dans l'interface finale.                                     |
| Terminer volontairement sa phase             | ✅        | Même si toutes les unités n'ont pas agi.                                    |
| Annuler un déplacement                       | ✅        | Si aucune action définitive n'a encore été réalisée.                        |
| Prévisualisation des cases de déplacement    | ✅        | Cases atteignables mises en évidence.                                       |
| Zone de menace ennemie                       | ✅        | Affichage des cases attaquables.                                            |
| Animations simples sur la carte              | ✅        | Marche, impact, soins, dégâts, disparition / KO.                            |
| Cinématiques de combat                       | ❌        | Pas de séquence cinématique séparée.                                        |
| Dialogues scénarisés avant le combat         | ❌        | Pas d'histoire dialoguée préalable.                                         |

### Machine à états — implémentée (TP13 et TP14)

- `DISPONIBLE` : peut se déplacer et agir.
- `DEPLACEE` : peut encore agir, mais ne peut plus se déplacer.
- `TERMINEE` : ne peut plus agir durant son activation.
- Une action réussie (attaque, soin, attente) termine l'activation ; une tentative refusée n'en consomme pas une.
- Une unité à 0 PV ne peut plus agir ni se déplacer, et ne doit pas redevenir disponible à la réinitialisation.
- **Exception future :** une compétence permettant de rejouer après avoir éliminé un ennemi nécessitera une règle explicite pour réautoriser une activation.

## 3. Classes, équipements et armes

### Classes envisagées pour la première version

| Classe     | Équipement principal | Identité tactique                          |
|------------|----------------------|--------------------------------------------|
| Épéiste    | Épées                | Vitesse / esquive.                         |
| Cavalier   | Épées, lances        | Mobilité élevée.                           |
| Combattant | Haches               | Force élevée.                              |
| Archer     | Arcs                 | Attaque à distance.                        |
| Mage       | Tomes                | Dégâts magiques.                           |
| Clerc      | Bâtons               | Soins et soutien.                          |
| Chevalier  | Lances               | Défense physique.                          |
| Pégase     | Lances               | Mobilité particulière, faiblesse aux arcs. |

Ces **8 classes** constituent la base envisagée. **Déjà codées :** Épéiste, Cavalier, Combattant, Archer, Mage et Clerc. **À créer :** Chevalier et Pégase. Des classes supplémentaires disposant de plusieurs catégories d’armes (ex. Sage : tomes + bâtons) restent possibles, sans prolifération inutile.

### Principes retenus

- ✅ **Tous les combattants aptes à attaquer peuvent attaquer**, sans restriction `instanceof Epeiste` : le moteur passe par `Unite` et la catégorie de l’arme équipée.
- ✅ **La classe détermine les catégories d'armes autorisées** ; l'arme équipée détermine puissance, portée et autres propriétés.
- ✅ **Le niveau de l'unité détermine la qualité des armes utilisables**, **sans rang de maîtrise d'armes E–A**.
- 🟡 **Progression de référence, encore à équilibrer :** niveau 1 = bronze ; niveau 3 = fer ; niveau 5 = acier ; niveau 8 = argent. Chaque arme déclare actuellement son propre `levelRequired`.
- ✅ Plusieurs modèles par catégorie, ainsi que la **durabilité / le nombre d'utilisations**.
- ✅ Équiper une arme et en changer ; gérer l'inventaire.
- ✅ Triangle des armes : **épée > hache > lance > épée**.
- ✅ Armes efficaces contre certaines classes / catégories d'unités (par ex. arcs contre unités volantes).
- ❌ Armes légendaires avec caractéristiques / effets spécifiques. Des armes différentes par qualité restent possibles.
- ❌ Forge.
- ❌ Pierres de transformation (Taguels, Manaketes, etc.).
- ❌ Promotion de classe et changement de classe en V1.

### Inventaire

- ✅ Inventaire individuel ; **limite envisagée de 5 objets par unité**.
- ✅ Équipement / déséquipement, utilisation de consommables.
- ✅ Échanges d'objets entre unités proches.
- ✅ Objets parfois obtenus sur les ennemis (modalités à préciser).
- ✅ Préparation des équipements avant une bataille.
- ❌ Convoi pour déposer/reprendre des objets.
- ❌ Ramassage manuel d'objets au sol.
- ❌ Boutiques *sur la carte pendant un combat*.

## 4. Statistiques et règles du combat

| Mécanique                    | Décision | Précisions                                                                                                               |
|------------------------------|----------|--------------------------------------------------------------------------------------------------------------------------|
| PV, Force, Magie             | ✅        | Distinguer attaques physiques et magiques.                                                                               |
| Défense, Résistance          | ✅        | Réduction des dégâts selon le type d'attaque.                                                                            |
| Vitesse                      | ✅        | Notamment pour les doubles attaques, si retenues.                                                                        |
| Mouvement                    | ✅        | Variable selon la classe.                                                                                                |
| Précision et esquive         | ✅        | Risque de manquer une attaque.                                                                                           |
| Coups critiques              | ✅        | Événement aléatoire infligeant plus de dégâts.                                                                           |
| Contre-attaque               | ✅        | Si le défenseur peut attaquer à cette portée.                                                                            |
| Double attaque               | ✅        | Décision retenue : seuil proposé de **5 points de vitesse**, seconde frappe après une riposte éventuelle ; **non codé**. |
| Prévision de combat          | ✅        | Dégâts, chances de toucher/critiquer, riposte avant confirmation.                                                        |
| Technique, Chance            | ❌        | Ne pas ajouter ces deux statistiques.                                                                                    |
| Formules exactes d'Awakening | ❌        | Formules simplifiées, à équilibrer.                                                                                      |

**Ordre de combat retenu :** attaquant → riposte du défenseur (si portée compatible et toujours vivant) → deuxième frappe de l’attaquant (si écart de vitesse suffisant et toujours vivant). Pour l’instant, aucun de ces enchaînements n’est codé ; un appel à `attaquer()` provoque une seule frappe. Un critique amplifie une frappe, il ne la multiplie pas en nombre.

**Formules et paramètres retenus comme base de travail (à équilibrer en PvP) :**

- **Dégâts physiques (déjà codés) :** `max(0, force + might - défense)` pour épées, lances, haches et arcs.
- **Dégâts magiques (déjà codés) :** `max(0, magie + might - résistance)` pour les tomes.
- **Esquive (à coder) :** `vitesse × 2`.
- **Probabilité de toucher (à coder) :** `précision de l’arme - esquive de la cible`, bornée à 0–100 %.
- **Critique (à coder) :** chance de critique fondée sur `crit` de l’arme ; un critique **triple les dégâts**.
- **Double frappe (à coder) :** écart de vitesse ≥ 5 ; vérifier le cas d’une riposte possible ou impossible.
- **Triangle des armes (à coder) :** épée > hache > lance > épée ; bonus/malus proposés de **±1 dégât et ±10 points de précision**. Tomes et arcs neutres.
- **Efficacité (à coder) :** certaines armes ont une puissance triplée (`might × 3`) contre les catégories vulnérables, **pas nécessairement les dégâts finaux**.

Ces valeurs simplifient volontairement Awakening en l’absence des statistiques Technique et Chance. Elles sont **des règles prévues, pas encore toutes implémentées**.

## 5. Soins, bâtons et consommables

Les soins ne seront **ni innés et illimités**, ni indépendants de la distance. Ils dépendront d'**objets**, de la classe autorisée, de leur portée et de leurs utilisations restantes.

| Fonctionnalité                                    | Décision | Exemple                                                                          |
|---------------------------------------------------|----------|----------------------------------------------------------------------------------|
| Bâton de soin à une case                          | ✅        | Soin standard au contact.                                                        |
| Bâton de soin plus puissant                       | ✅        | Rend davantage de PV.                                                            |
| Bâton de soin à distance                          | ✅        | Soigne une cible éloignée.                                                       |
| Bâton de soin complet                             | ✅        | Restaure tous les PV d'une cible.                                                |
| Halo / soin de zone                               | ✅        | Soigne plusieurs alliés dans une zone.                                           |
| Durabilité limitée des bâtons                     | ✅        | Une utilisation consommée **uniquement si l'action réussit**.                    |
| Potions / consommables personnels                 | ✅        | Restauration de PV, élixir.                                                      |
| Bâton de téléportation / sauvetage                | ✅        | Ramener une unité trop avancée vers la sécurité.                                 |
| Bâton de protection                               | ✅        | Bonus temporaire de résistance envisagé ; effet déclaré mais non codé.           |
| Bâton de réparation                               | ✅        | Effet retenu en réflexion d’architecture ; devra respecter `maxUses` (non codé). |
| Objets augmentant définitivement les statistiques | ❌        | Supprimés.                                                                       |

**État réel à l’issue du TP14 :**

- `EffetBaton` existe avec **six valeurs** : `SOIN`, `SOIN_COMPLET`, `SOIN_ZONE`, `TELEPORTATION`, `BONUS_RESISTANCE`, `REPARATION`. L’effet de soin complet de zone n’a volontairement pas été retenu (contenu annexe / DLC).
- `Arme` possède `effetBaton`. Un bâton **doit** avoir un effet ; une arme non-bâton **ne peut pas** en recevoir. La catégorie, l’effet, la portée, la durabilité et le niveau requis sont contrôlés lors des actions.
- **Implémentés :** soin simple (`magie / 2 + might`, division entière), soin complet (tous les PV manquants), refus des soins illégaux, durabilité consommée lors d’un soin valide et fin d’activation. Trois exemples dans `Main` : Soin, Cure et Restitution.
- **Préparés, non implémentés :** soin de zone, téléportation/sauvetage, bonus temporaire de résistance et réparation. Les futurs effets ne doivent pas être acceptés par `peutSoigner()` avant leur implémentation.
- **Téléportation — règle de travail envisagée :** portée de sélection de la cible `magie / 2` (ex. 12 magie → 6 cases) ; destination choisie parmi les cases libres autour du lanceur, contrôlées via `caseExist()` et `caseOccupied()`. Ne pas confondre portée de sélection et case d’arrivée.
- **Réparation — modification nécessaire :** ajouter à terme `maxUses` à `Arme` pour plafonner toute réparation à la durabilité maximale propre à l’arme.
- **Architecture :** `Unite.calculerSoin(cible)` calcule sans effet de bord ; `cible.recevoirSoin(int)` applique ; `Partie.peutSoigner()` valide ; `Partie.soigner()` exécute, consomme une utilisation et termine l’activation. `peutManier()` reste défini par chaque classe de personnage, sans interface `Soigneur` obligatoire.

## 6. Compétences spéciales

- ✅ Compétences offensives, défensives, passives et liées au soin, en nombre restreint.
- ✅ **Une seule compétence équipée par unité**, choisie parmi plusieurs (orientation exprimée par l'utilisateur ; détail de sélection à préciser).
- ✅ Exemple marquant : **rejouer après avoir tué un ennemi** pendant son tour, inspiré d'une compétence rencontrée dans un DLC d'Awakening.
- ✅ Compétences de classe et possibilités d'en débloquer avec les niveaux.
- ❌ Compétence **Rallier**.
- ❌ Compétences spécifiques aux différents types de terrain / mobilité liée aux terrains.
- ❌ Cinq compétences équipées simultanément.
- ❌ Système Duo, changement de meneur, séparation de Duo, rangs et dialogues de soutien.
- ✅ **Bonus de proximité**, attaques combinées et défenses combinées (principes retenus, détails à concevoir), **sans** système Duo.

**Point d'équilibrage à traiter plus tard :** fréquence et limite d'utilisation de la compétence « rejouer après élimination », surtout en PvP.

## 7. Cartes, obstacles et pathfinding

- ✅ Plusieurs cartes quadrillées ; obstacles, murs, zones bloquées, ponts éventuels.
- ✅ Déplacement tenant compte des **obstacles** et recherche d'itinéraire, et pas seulement de la distance de Manhattan.
- ✅ Possibilité d'unités volantes, avec règles de franchissement et vulnérabilité adaptées.
- ✅ Prévisualisation des mouvements et des menaces.
- ❌ **Pas de système complexe de types de terrain avec coûts différents ou bonus particuliers** : l'utilisateur a choisi de simplifier cet aspect.
- ❌ Pas de forêt à bonus, montagne à coût spécial, fort soignant, lave/terrain volcanique, cases dangereuses à effet spécial, etc. en V1.
- ❌ Brouillard de guerre.
- ❌ Portes verrouillées, clés, coffres et classe dédiée à leur ouverture.

**À préciser au moment du TP déplacement avancé :** comportements sur l'eau et les ponts, distinctions exactes entre obstacles franchissables ou non selon les classes, sans réintroduire une mécanique de terrain non souhaitée.

## 8. Progression, modes de jeu et économie

### Progression

- ✅ Expérience, niveaux et montée en puissance des unités.
- ✅ Expérience accordée aussi aux soigneurs.
- ✅ **Progression aléatoire des statistiques** à chaque niveau ; ❌ progression entièrement fixe.
- ✅ Déblocage possible de compétences avec les niveaux.
- ❌ Promotion, reclassement et recrutement de personnages en pleine bataille.
- ❌ Système de recrutement de nouveaux personnages au fil d'une campagne (pas de campagne longue prévue).
- ✅ Choix d'une équipe parmi des personnages disponibles avant la bataille.

### Mort

- ✅ Une unité à **0 PV** est hors combat et ne peut plus agir.
- ✅ Une unité vaincue disparaît du champ de bataille / devient indisponible pour cette bataille.
- ❌ Mort permanente entre les batailles : retour possible lors de la partie suivante, esprit *Casual*.
- ❌ Défaite spéciale si Chrom meurt.

### Argent et boutique — **partiellement ouvert**

L'utilisateur souhaite **acheter de nouvelles armes / objets**, mais n'a pas encore arrêté l'économie, puisque chaque bataille se termine et qu'il ne prévoit pas un RPG interminable.

- ✅ **Achat d'armes et d'objets** souhaité, plutôt **en préparation de combat** que sur la carte.
- 🟡 Système de monnaie, acquisition de ressources et persistance d'un match à l'autre : **à décider**.
- 🟡 **Proposition de design, non validée :** en PvP, chacun dispose du **même budget de préparation**, pour acheter équipements et consommables, plutôt que d'arriver avec un avantage de progression accumulé.
- 🟡 Proposition non définitivement validée : une progression persistante en solo, mais des statistiques / niveaux normalisés en PvP.

**Principe prioritaire :** le PvP doit récompenser la stratégie et la préparation, pas uniquement le nombre d'heures de jeu.

## 9. Modes, objectifs et déroulement des parties

### Solo

- ✅ Batailles contre l'IA, utiles pour s'amuser et tester les mécaniques.
- ✅ IA mobile, qui attaque, soigne, choisit ses armes, protège ses unités fragiles et joue l'objectif.
- ✅ Difficultés proposées : facile, normal, difficile.
- ❌ Pilotage automatique de l'équipe du joueur.

### Multijoueur

- ✅ **1 joueur contre 1 joueur** sur deux ordinateurs via réseau.
- ✅ Chaque joueur contrôle plusieurs unités.
- ✅ **Taille des équipes paramétrable entre 4 et 10 unités** (plutôt qu'une limite fixe de 5).
- ✅ Composition et placement de l'équipe avant de jouer ; préparation des équipements et compétences.
- ❌ 2v2 à quatre joueurs / coop multijoueur.

### Conditions de victoire possibles

- ✅ Éliminer toutes les unités adverses.
- ✅ Battre une unité désignée / un boss.
- ✅ Capturer ou occuper une position.
- ✅ Défendre une zone ou tenir plusieurs tours.
- ✅ Protéger une unité déterminée.
- ✅ Objectifs à réaliser en un nombre limité de tours.
- ✅ Renforts ennemis pour certains scénarios (surtout solo).
- ✅ Possibilité de déclarer forfait.

**À équilibrer ultérieurement :** durée cible d'une partie, formats d'équipe 4–10, cartes, équipement de départ et conditions de victoire spécifiques au PvP.

## 10. Présentation et ergonomie

- ✅ Interface graphique **2D** (JavaFX envisagé dans la feuille de route).
- ✅ Sélection et déplacement à la souris.
- ✅ Cases de déplacement et portée en surbrillance.
- ✅ Menus d'action, inventaire, équipement, informations d'unité.
- ✅ Barres de vie et prévisions de combat.
- ✅ Animations **simples sur la carte** et effets sonores.
- ✅ **Musique**, que l'utilisateur choisira.
- ✅ Sauvegarder / reprendre une partie (forme exacte à préciser).
- ❌ Cinématiques 3D, combats animés dans une scène séparée et dialogues scénarisés.

## 11. Architecture Java et apprentissage

Le projet est aussi un support d'apprentissage : les TP doivent être découpés en **petites consignes de niveau L1**, sans donner directement le code. L'élève recherche la documentation Java lorsque nécessaire ; l'assistant corrige les exercices précisément selon leurs critères.

**Règles de travail :**

1. Un TP comporte généralement **10 exercices progressifs** et introduit autant que possible une **notion réellement nouvelle** de Java ou de conception.
2. **Pas de solution complète ni de code prémâché**, sauf demande explicite.
3. Vérification exercice par exercice, puis **commit et push GitHub après chaque exercice validé**, pas seulement à la fin du TP.
4. Garder `Main` pour l'orchestration / les scénarios, `Partie` pour les règles et la coordination, `Unite` pour les informations et comportements communs, classes spécialisées pour les aptitudes propres.
5. Préserver la lisibilité, les tests, la séparation des responsabilités et la possibilité d'ajouter de nouvelles armes / classes sans multiplier les `instanceof`.

**Dépôt de travail :** `fire-emblem-java` (branche et organisation concrètes à suivre selon le dépôt actuel).

### État d’avancement au 10 novembre 2026

- **TP1–TP13 : terminés et validés.** Le TP13 a établi les états `DISPONIBLE`, `DEPLACEE`, `TERMINEE`, le déplacement suivi d’une action, l’attente, la fin de phase et la réinitialisation des unités vivantes.
- **TP14 : terminé et validé (exercices 1 à 10).** Catégories d’armes, classe `Arme` avec précision/critique/puissance/portées/durabilité/niveau/prix ; inventaire de 5 emplacements ; compatibilité par classe ; équipement ; validation de portée ; dégâts physiques et magiques ; consommation d’utilisations ; bâtons et soins simples/complets ; test d’une bataille complète.
- **Classes effectivement présentes :** `Unite` (abstraite), `Epeiste`, `Cavalier`, `Combattant`, `Archer`, `Mage`, `Clerc` ; `Partie`, `Arme`, `CategorieArme`, `EffetBaton`, `EtatUnite`, `Action`, `Main`.
- **Règles codées :** phases des deux camps ; action refusée sans consommation de tour ni de durabilité ; impossibilité d’attaquer une cible à 0 PV ; impossibilité de soigner un ennemi, une unité morte ou déjà au maximum ; `memeEquipe()` / `equipeOpposee()` ; déplacement limité par `unite.getMovement()` ; affichage des PV, des positions, des états et des utilisations restantes.
- **Vérification par scénario :** compilation et exécution en console réussies ; Chrom frappe Vaike à l’épée, Robin au tome, Lissa soigne Frederick et Vaike met Robin KO. Au tour suivant, les vivants redeviennent disponibles, contrairement à Robin à 0 PV.
- **Limites du moteur actuel :** précision (`hit`), critiques (`crit`) et vitesse (`speed`) stockés mais pas exploités dans le combat ; pas de riposte, double frappe, triangle des armes ni efficacité ; aucun obstacle/pathfinding ; unités mortes encore affichées et considérées comme occupant une case ; pas de détection de victoire ; pas d’IA ni de réseau.
- **Prochaine étape : TP15**, à rédiger ensuite selon le format de petites consignes niveau L1, sans solution fournie, pour introduire les mécaniques de combat avancées progressivement.

### Trajectoire indicative des prochains TP

| Étape                 | Orientation prévue                                                                                         |
|-----------------------|------------------------------------------------------------------------------------------------------------|
| **TP14 — terminé**    | Armes, équipement, portée, calcul des dégâts, durabilité, bâtons, soins et simulation complète.            |
| **TP15 — à préparer** | Combat avancé : précision, esquive, critiques, ripostes, doubles frappes ; progression par petites tâches. |
| **TP suivants**       | Triangle des armes, efficacité, compétences, IA, obstacles, cheminement, effets spéciaux des bâtons.       |
| **Puis**              | Maven / JavaFX, affichage 2D, interactions souris, menus, sauvegarde et tests.                             |
| **Enfin**             | Réseau client-serveur, synchronisation et **PvP 1v1 sur deux PC**.                                         |

Cette trajectoire est **indicative** : ne pas figer la numérotation exacte des fonctionnalités avant les prochains sujets.

## 12. Décisions qui restent à prendre

1. **Double attaque :** décision prise de la conserver (écart ≥ 5 proposé) ; reste à calibrer les cas limites et l’équilibrage.
2. **PvP équitable :** statistiques normalisées, niveaux choisis, plafond de coût d'équipe, ou autre système ?
3. **Économie :** monnaie entre les parties ou budget commun alloué avant chaque match ? Comment acheter / renouveler les armes ?
4. **Une compétence par personnage :** catalogue initial, conditions de déblocage, fréquence et limites de « rejouer après élimination ».
5. **Combat :** les formules et paramètres initiaux figurent en section 4 ; leurs valeurs devront être testées et équilibrées en PvP.
6. **Durabilité en PvP :** consommation au cours d'un match uniquement, récupération entre les matchs, remplacement possible au moment de la préparation ?
7. **Cartes / unités volantes :** règles simples d'obstacles, sans coûts de terrain différenciés.
8. **Format des parties :** taille de carte et d'équipe, durée approximative souhaitée, timer de phase éventuel.
9. **Catalogue de classes, armes et bâtons :** liste définitive, progression par niveaux et effets spéciaux encore à implémenter.
10. **Sauvegarde et progression solo :** portée et persistance des données sans campagne longue.

## 13. Fonctionnalités explicitement exclues — liste rapide

Cinématiques de combat ; dialogues scénarisés ; convoi ; Rallier ; forge ; pierres de transformation ; bonus permanents par objet ; rangs d'armes E–A ; armes légendaires spéciales ; Technique et Chance ; système Duo et ses variantes ; soutiens / mariages / enfants ; promotions et changement de classe en V1 ; recrutement ; mort permanente ; défaite spéciale de Chrom ; ramassage manuel au sol ; boutiques sur la carte ; coûts de terrain et bonus complexes ; lave ; portes / clés / coffres ; brouillard de guerre ; coop / 2v2 à quatre joueurs ; pilotage automatique du groupe ; longue campagne scénarisée.

---

## 14. Règle de mise à jour de ce document

À chaque nouvelle décision de conception :

1. Corriger la ligne correspondante en distinguant **décision**, **proposition** et **report**.
2. Si une règle change, noter ce qui est remplacé (ex. soin inné → soin via bâton).
3. Ajouter les critères d'équilibrage du PvP lorsqu'ils seront connus.
4. Mettre à jour l'avancement des TP après leur validation.

> **Fil directeur :** *un Fire Emblem tactique simplifié mais fun, avec des combats suffisamment riches pour donner envie de refaire un 1v1*, sans sacrifier l'apprentissage progressif de Java.
