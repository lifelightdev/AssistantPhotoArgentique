create table if not exists apa.statut_chimie
(
    id  bigint auto_increment primary key,
    nom varchar(45) null,
    constraint id_UNIQUE unique (id)
);