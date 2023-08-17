create table if not exists apa.position
(
    id          bigint auto_increment primary key,
    nom         varchar(255) null,
    ville       varchar(255) null,
    code_postal varchar(255) null,
    latitude    double       null,
    longitude   double       null,
    constraint id_UNIQUE unique (id)
);