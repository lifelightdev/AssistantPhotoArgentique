# AssistantPhotoArgentique

Ce Projet est en cours de développement avec IntelliJ IDE et Android Studio.
* Il ne fonctionne que sur un ordinateur local avec une webCam.
* Il faut avoir un serveur MySql. 
* Il faut créer un schémas `apa` : 

`CREATE DATABASE IF NOT EXISTS "apa";`
* 
* Il faut créer l'utilistateur `AppareilPhoto` avec la mot de passe `AppareilPhoto` : 

`create user if not exists AppareilPhoto@localhost;`

`grant alter, create, delete, drop, event,  index, insert, lock tables,  select, update on apa.* to AppareilPhoto@localhost;`

* Pour l'application Android, il faut créer un device de type Pixel Pro avec un API Level 34 et ajouter la webCam à la camera du device.

Adresse GitHub : https://github.com/lifelightdev/AssistantPhotoArgentique