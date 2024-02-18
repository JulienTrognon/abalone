2022-VP1-G2 - Javalon
---------------------------

*Projet de programmation VP1 G2. Par Monrousseau Matthieu, Montre Maxime, Pariat Simon, Trognon Julien, Wong Cedrick*

## Présentation
Le projet représente une modélisation en Java du jeu de plateau **Abalon**.

Il s'agit d'un jeu en **tour par tour** 1 contre 1 où le but est d'**éjecter les billes de son adversaires**. 
Au bout d'un certain nombre de billes éjectées d'un joueur(nombre choisi au préalable), la partie se termine et le joueur qui a ajectées ces billes gagne.

Sa particularité est la forme du plateau : Il s'agit d'un **plateau hexagonal**. On peut alors déplacer ses billes dans **6 directions différentes**, à la manière d'un Roi aux échecs (d'une seule case adjacente par tour).

On peut choisir **plusieurs billes à déplacer à la fois**, seulement elles doivent être **alignées** et il y a un **maximum** de billes sélectionnables à la fois.

Pour éjecter les billes de son adversaire, il faut les **pousser**. Pour cela, il faut effectuer un déplacement de ses billes **en ligne droite**, devant une rangée de billes adverses, mais cette rangée doit aligner **strictement moins de billes que notre sélection** pour les pousser.

## Contrôles
Le jeu se joue à la **souris**.
`ECHAP` met pause pendant une partie.

On interagit avec le plateau de jeu en cliquant sur ses différentes **intersections**.


## Installation / Exécution
Pour l'instant Javalon n'a pas son exécutable, il faut le compiler soi-même avec `javac`. On peut aussi, dans un IDE, lancer le fichier `Javalon.java` qui contient le main principal du jeu. Ce fichier se trouve dans le chemin `src/Javalon.java`.


-----------------------
Bon jeu à vous !