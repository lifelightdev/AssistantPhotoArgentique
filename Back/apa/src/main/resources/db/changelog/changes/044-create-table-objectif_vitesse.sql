create table if not exists apa.objectif_vitesse
(
    objectif_id bigint not null,
    vitesse_id  bigint not null,
    primary key (objectif_id, vitesse_id),
    constraint FK_OBJECTIF_VITESSE_ON_OBJECTIF foreign key (objectif_id) references apa.objectif (id),
    constraint FK_OBJECTIF_VITESSE_ON_VITESSE foreign key (vitesse_id) references apa.vitesse (id)
);