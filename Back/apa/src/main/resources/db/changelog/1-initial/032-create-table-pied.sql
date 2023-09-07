create table if not exists apa.pied
(
    id                    bigint auto_increment primary key,
    materiel_id           bigint       null,
    dimension_ouvert      varchar(255) null,
    dimension_ferme       varchar(255) null,
    type_fixation_pied_id bigint       null,
    rotule_id             bigint       null,
    constraint id_UNIQUE unique (id),
    constraint FK_PIED_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_PIED_ON_ROTULE foreign key (rotule_id) references apa.rotule (id),
    constraint FK_PIED_ON_TYPE_FIXATION_PIED foreign key (type_fixation_pied_id) references apa.type_fixation (id)
);
