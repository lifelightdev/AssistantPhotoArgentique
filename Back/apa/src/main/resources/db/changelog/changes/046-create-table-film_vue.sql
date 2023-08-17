create table if not exists apa.film_vue
(
    film_id bigint not null,
    vue_id  bigint not null,
    primary key (film_id, vue_id),
    constraint FK_FILM_VUES_ON_FILM foreign key (film_id) references apa.film (id),
    constraint FK_FILM_VUES_ON_VUE foreign key (vue_id) references apa.vue (id)
);