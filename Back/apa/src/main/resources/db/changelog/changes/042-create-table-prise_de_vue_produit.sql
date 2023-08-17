create table if not exists apa.prise_de_vue_produit
(
    prise_de_vue_id bigint not null,
    produit_id      bigint not null,
    primary key (prise_de_vue_id, produit_id),
    constraint prise_de_vue_id_UNIQUE unique (prise_de_vue_id),
    constraint produit_id_UNIQUE unique (produit_id),
    constraint FK_PRISE_DE_VUE_PRODUIT_ON_PRISE_DE_VUE foreign key (prise_de_vue_id) references apa.prise_de_vue (id),
    constraint FK_PRISE_DE_VUE_PRODUIT_ON_PRODUIT foreign key (produit_id) references apa.produit (id)
);