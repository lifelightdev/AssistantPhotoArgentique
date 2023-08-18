create table if not exists apa.objectif_ouverture
(
    objectif_id  bigint not null,
    ouverture_id bigint not null,
    primary key (objectif_id, ouverture_id),
    constraint FK_OBJECTIF_OUVERTURE_ON_OBJECTIF foreign key (objectif_id) references apa.objectif (id),
    constraint FK_OBJECTIF_OUVERTURE_ON_OUVERTURE foreign key (ouverture_id) references apa.ouverture (id)
);