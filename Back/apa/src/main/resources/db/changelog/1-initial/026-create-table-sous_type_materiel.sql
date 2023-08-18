create table if not exists apa.sous_type_materiel
(
    id      bigint auto_increment primary key,
    type_id bigint       null,
    nom     varchar(255) null,
    constraint id_UNIQUE unique (id),
    constraint FK_SOUS_TYPE_MATERIEL_ON_TYPE foreign key (type_id) references apa.type_materiel (id)
);