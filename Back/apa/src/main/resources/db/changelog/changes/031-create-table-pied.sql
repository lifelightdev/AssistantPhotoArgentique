create table if not exists apa.pied
(
    id               bigint auto_increment primary key,
    materiel_id      bigint       null,
    dimension_ouvert varchar(255) null,
    dimension_ferme  varchar(255) null,
    pas_de_vis       varchar(255) null,
    rotule_id        bigint       null,
    constraint id_UNIQUE unique (id),
    constraint FK_PIED_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_ROTULE_ON_ROTULE foreign key (rotule_id) references apa.rotule (id)
);