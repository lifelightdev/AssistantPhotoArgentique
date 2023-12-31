create table if not exists apa.appareil_photo
(
    id                        bigint auto_increment primary key,
    materiel_id               bigint     null,
    type_appareil_photo_id    bigint     null,
    type_fixation_objectif_id bigint     null,
    objectif_id               bigint     null,
    type_fixation_flash_id    bigint     null,
    type_fixation_pied_id     bigint     null,
    chassis_id                bigint     null,
    film_id                   bigint     null,
    type_mise_au_point_id     bigint     null,
    flash_integre             tinyint(1) null,
    nombre_pile               int        null,
    dimension_chassis_id      bigint     null,
    constraint id_UNIQUE unique (id),
    constraint FK_APPAREIL_PHOTO_ON_CHASSIS foreign key (chassis_id) references apa.chassis (id),
    constraint FK_APPAREIL_PHOTO_ON_DIMENSION_CHASSIS foreign key (dimension_chassis_id) references apa.dimension_chassis (id),
    constraint FK_APPAREIL_PHOTO_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_APPAREIL_PHOTO_ON_OBJECTIF foreign key (objectif_id) references apa.objectif (id),
    constraint FK_APPAREIL_PHOTO_ON_TYPE_APPAREIL_PHOTO foreign key (type_appareil_photo_id) references apa.type_appareil_photo (id),
    constraint FK_APPAREIL_PHOTO_ON_TYPE_FIXATION_FLASH foreign key (type_fixation_flash_id) references apa.type_fixation (id),
    constraint FK_APPAREIL_PHOTO_ON_TYPE_FIXATION_OBJECTIF foreign key (type_fixation_objectif_id) references apa.type_fixation (id),
    constraint FK_APPAREIL_PHOTO_ON_TYPE_FIXATION_PIED foreign key (type_fixation_pied_id) references apa.type_fixation (id),
    constraint FK_APPAREIL_PHOTO_ON_TYPE_MISE_AU_POINT foreign key (type_mise_au_point_id) references apa.type_mise_au_point_photo (id)
);
