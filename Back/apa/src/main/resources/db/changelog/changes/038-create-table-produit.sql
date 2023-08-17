create table if not exists apa.produit
(
    id           bigint auto_increment primary key,
    nom          varchar(255) null,
    type_id      bigint       null,
    statut_id    bigint       null,
    modele_id    bigint       null,
    photo        blob         null,
    mode_emploie blob         null,
    remarque     varchar(255) null,
    constraint id_UNIQUE unique (id),
    constraint FK_PRODUIT_ON_MODELE foreign key (modele_id) references apa.modele (id),
    constraint FK_PRODUIT_ON_STATUT_PRODUIT foreign key (statut_id) references apa.statut_produit (id),
    constraint FK_PRODUIT_ON_TYPE_PRODUIT foreign key (type_id) references apa.type_produit (id)
);