create table if not exists apa.modele
(
    id     bigint auto_increment primary key,
    nom    varchar(255) null,
    marque bigint       null,
    constraint id_UNIQUE unique (id),
    constraint FK_MODELE_ON_MARQUE foreign key (marque) references apa.marque (id)
);