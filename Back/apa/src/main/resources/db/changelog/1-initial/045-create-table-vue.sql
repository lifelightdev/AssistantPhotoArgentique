create table if not exists apa.vue
(
    id                bigint auto_increment primary key,
    prise_de_vue_id   bigint       not null,
    statut_vue_id     bigint       not null,
    nom               varchar(255) null,
    appareil_photo_id bigint       null,
    position_id       bigint       null,
    film_id           bigint       null,
    ouverture_id      bigint       null,
    vitesse_id        bigint       null,
    photo             mediumblob   null,
    date              datetime(6)  null,
    constraint id_UNIQUE unique (id),
    constraint FK_VUE_ON_APPARIEL_PHOTO foreign key (appareil_photo_id) references apa.appareil_photo (id),
    constraint FK_VUE_ON_FILM foreign key (film_id) references apa.film (id),
    constraint FK_VUE_ON_OUVERTURE foreign key (ouverture_id) references apa.ouverture (id),
    constraint FK_VUE_ON_POSITION foreign key (position_id) references apa.position (id),
    constraint FK_VUE_ON_PRISE_DE_VUE foreign key (prise_de_vue_id) references apa.prise_de_vue (id),
    constraint FK_VUE_ON_STATUT_VUE foreign key (statut_vue_id) references apa.statut_vue (id),
    constraint FK_VUE_ON_VITESSE foreign key (vitesse_id) references apa.vitesse (id)
);