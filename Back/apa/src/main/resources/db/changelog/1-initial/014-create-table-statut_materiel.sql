create table if not exists apa.statut_materiel
(
    id  bigint auto_increment primary key,
    nom varchar(255) null,
    constraint id_UNIQUE unique (id)
);