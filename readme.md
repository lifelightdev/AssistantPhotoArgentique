Création du certificat pour le front :
- dans Git Bash :
openssl req -new -x509 -newkey rsa:2048 -sha256 -nodes -keyout localhost.key -days 3560 -out localhost.crt -config certificate.cnf
Lancer l'installation du certificat localhost.crt
