create table if not exists apa.type_mise_au_point_photo
(
    id  bigint auto_increment primary key,
    nom varchar(255) null,
    constraint ID_UNIQUE unique (id)
);