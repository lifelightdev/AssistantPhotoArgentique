create table if not exists apa.objectif
(
    id                        bigint auto_increment primary key,
    materiel_id               bigint null,
    type_fixation_objectif_id bigint null,
    type_fixation_flash_id    bigint null,
    type_fixation_pied_id     bigint null,
    type_fixation_filtre_id   bigint null,
    constraint id_UNIQUE unique (id),
    constraint FK_OBJECTIF_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_OBJECTIF_ON_TYPE_FIXATION_FILTRE foreign key (type_fixation_filtre_id) references apa.type_fixation (id),
    constraint FK_OBJECTIF_ON_TYPE_FIXATION_FLASH foreign key (type_fixation_flash_id) references apa.type_fixation (id),
    constraint FK_OBJECTIF_ON_TYPE_FIXATION_OBJECTIF foreign key (type_fixation_objectif_id) references apa.type_fixation (id),
    constraint FK_OBJECTIF_ON_TYPE_FIXATION_PIED foreign key (type_fixation_pied_id) references apa.type_fixation (id)
);