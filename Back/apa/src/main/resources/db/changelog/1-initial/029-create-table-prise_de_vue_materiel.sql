create table if not exists apa.prise_de_vue_materiel
(
    prise_de_vue_id bigint not null,
    materiel_id     bigint not null,
    primary key (prise_de_vue_id, materiel_id),
    constraint FK_PRISE_DE_VUE_MATERIEL_ON_MATERIEL foreign key (materiel_id) references apa.materiel (id),
    constraint FK_PRISE_DE_VUE_MATERIEL_ON_PRISE_DE_VUE foreign key (prise_de_vue_id) references apa.prise_de_vue (id)
);