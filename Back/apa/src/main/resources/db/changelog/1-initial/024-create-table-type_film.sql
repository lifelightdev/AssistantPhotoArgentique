create table if not exists apa.type_film
(
    id  bigint auto_increment primary key,
    nom varchar(255) null,
    constraint id_UNIQUE unique (id)
);