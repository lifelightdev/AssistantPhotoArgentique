# AssistantPhotoArgentique

Ce Projet est en cours de développement avec IntelliJ IDE et Android Studio.
* Il ne fonctionne que sur un ordinateur local avec une webCam.
* Il faut avoir un serveur MySql. 
* Il faut créer un schéma `apa` : 

`CREATE DATABASE IF NOT EXISTS "apa";`

* Il faut créer l'utilistateur `AppareilPhoto` avec le mot de passe `AppareilPhoto` 

`create user if not exists 'AppareilPhoto'@'localhost' IDENTIFIED BY 'AppareilPhoto';`

`grant alter, create, delete, drop, event,  index, insert, lock tables,  select, update, references on apa.* to 'AppareilPhoto'@'localhost';`

* Pour l'application Android, il faut créer un device : 
   * de type Pixel Pro 7 pro avec un écran de 6,71"
   * avec une API Level 34 
   * et une caméra 

Adresse GitHub : https://github.com/lifelightdev/AssistantPhotoArgentique
