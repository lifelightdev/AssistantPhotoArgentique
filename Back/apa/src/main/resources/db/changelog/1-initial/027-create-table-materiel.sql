create table if not exists apa.materiel
(
    id           bigint auto_increment primary key,
    nom          varchar(255) null,
    type_id      bigint       null,
    sous_type_id bigint       null,
    statut_id    bigint       null,
    modele_id    bigint       null,
    photo        mediumblob   null,
    mode_emploie mediumblob   null,
    remarque     varchar(255) null,
    constraint id_UNIQUE unique (id),
    constraint FK_MATERIEL_ON_MODELE foreign key (modele_id) references apa.modele (id),
    constraint FK_MATERIEL_ON_SOUS_TYPE foreign key (sous_type_id) references apa.sous_type_materiel (id),
    constraint FK_MATERIEL_ON_STATUS foreign key (statut_id) references apa.statut_materiel (id),
    constraint FK_MATERIEL_ON_TYPE foreign key (type_id) references apa.type_materiel (id)
);
