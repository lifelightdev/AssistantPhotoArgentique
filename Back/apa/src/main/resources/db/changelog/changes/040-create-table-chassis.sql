create table if not exists apa.chassis
(
    id                   bigint auto_increment primary key,
    materiel_id          bigint null,
    statut_chassis_id    bigint null,
    dimension_chassis_id bigint null,
    film_id              bigint null,
    constraint ID_UNIQUE unique (id),
    constraint FK_CHASSIS_ON_DIMENSION_CHASSIS foreign key (dimension_chassis_id) references apa.dimension_chassis (id),
    constraint FK_CHASSIS_ON_FILM foreign key (film_id) references apa.film (type_film_id),
    constraint FK_CHASSIS_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_CHASSIS_ON_STATUT_CHASSIS foreign key (statut_chassis_id) references apa.statut_chassis (id)
);