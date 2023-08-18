create table if not exists apa.prise_de_vue
(
    id          bigint auto_increment primary key,
    nom         varchar(255) null,
    date        datetime(6)  null,
    remarque    varchar(255) null,
    statut_id   bigint       null,
    position_id bigint       null,
    constraint id_UNIQUE unique (id),
    constraint FK_PRISE_DE_VUE_ON_POSITION foreign key (position_id) references apa.position (id),
    constraint FK_PRISE_DE_VUE_ON_STATUS foreign key (statut_id) references apa.statut_prise_de_vue (id)
);