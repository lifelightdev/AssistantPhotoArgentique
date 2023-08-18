
create table if not exists apa.objectif_compatible
(
    id          bigint auto_increment primary key,
    materiel_id bigint null,
    objectif_id bigint null,
    constraint id_UNIQUE unique (id),
    constraint FK_OBJECTIF_COMPATIBLE_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_OBJECTIF_COMPATIBLE_ON_OBJECTIF foreign key (objectif_id) references apa.materiel (id)
);