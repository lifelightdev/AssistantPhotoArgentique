create table if not exists apa.ouverture
(
    id    bigint auto_increment primary key,
    nom   varchar(255) null,
    ordre int          null,
    constraint id_UNIQUE unique (id)
);