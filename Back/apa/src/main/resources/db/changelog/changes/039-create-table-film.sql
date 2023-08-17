create table if not exists apa.film
(
    id              bigint auto_increment primary key,
    produit_id      bigint null,
    statut_film_id  bigint null,
    taille_film_id  bigint null,
    type_film_id    bigint null,
    nb_vue_possible int    null,
    taille_vue_id   bigint null,
    nb_vue_expose   int    null,
    sensibilite_id  bigint null,
    constraint id_UNIQUE unique (id),
    constraint FK_FILM_ON_PRODUIT foreign key (produit_id) references apa.produit (id),
    constraint FK_FILM_ON_SENSIBITLITE foreign key (sensibilite_id) references apa.sensibilite (id),
    constraint FK_FILM_ON_STATUT_FILM foreign key (statut_film_id) references apa.statut_film (id),
    constraint FK_FILM_ON_TAILLE_FILM foreign key (taille_film_id) references apa.taille_film (id),
    constraint FK_FILM_ON_TAILLE_VUE foreign key (taille_vue_id) references apa.taille_vue (id),
    constraint FK_FILM_ON_TYPE_FILM foreign key (type_film_id) references apa.type_film (id)
);