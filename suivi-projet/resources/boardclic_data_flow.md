
VUE :
- *input* clic sur le plateau.
- *send* un (x,y) en pixels de la zone du plateau cliquée.

CONTROLEUR :
- *receive* (x,y).
- *convert* le (x,y) en (i,j) coordonnées de plateau.
- *send* (i,j).

MODEL : 
- *receive* (i,j).
- vérifie s'il peut changer le model.
    - **false** → renvoie que le point séléctionné ne peut pas changer le model.
    - **true**  → vérifie si le point est déjà stocké dans le model.
        - **false** → on enregistre le point dans le model.
        - **true**  → on supprime le point du model correspondant à l'input.
        on renvoie que le model a changé.

CONTROLER :
- *receive* réponse du model.
- vérifie si le model a changé.
    **false** → n'update pas la vue.
    **true**  → demande à la vue de s'update en lui passant ce dont elle a besoin pour s'update.

-------------------------------

La vue a besoin de recevoir les coordonnées des points qui ont changé leur état + leur nouvel état.
