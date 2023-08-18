create table if not exists apa.rotule
(
    id                   bigint auto_increment primary key,
    materiel_id          bigint               null,
    pas_de_vis_materiel  varchar(255)         null,
    pas_de_vis_pied      varchar(255)         null,
    avec_fixation_rapide tinyint(1) default 0 null,
    constraint avec_fixation_rapide_UNIQUE unique (avec_fixation_rapide),
    constraint id_UNIQUE unique (id),
    constraint FK_ROTULE_ON_MATERIEL foreign key (materiel_id) references materiel (id)
);