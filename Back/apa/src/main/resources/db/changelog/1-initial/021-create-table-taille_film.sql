create table if not exists apa.taille_film
(
    id             bigint auto_increment primary key,
    nom            varchar(255) null,
    taille         varchar(255) null,
    format_film_id bigint       null,
    constraint id_UNIQUE unique (id),
    constraint FK_TAILLE_FILM_ON_FORMAT foreign key (format_film_id) references apa.format_film (id)
);