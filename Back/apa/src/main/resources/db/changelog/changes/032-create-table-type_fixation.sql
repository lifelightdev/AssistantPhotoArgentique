create table if not exists apa.type_fixation
(
    id                 bigint auto_increment primary key,
    nom                varchar(255) null,
    sous_type_materiel bigint       null,
    constraint id_UNIQUE unique (id),
    constraint FK_TYPE_FIXATION_ON_SOUS_TYPE_MATERIEL foreign key (sous_type_materiel) references apa.sous_type_materiel (id)
);