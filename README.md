# Video Recommendation App
La Video Recommendation App est une application Spring Boot permettant de gérer des vidéos, y compris des films et des séries. Elle fournit des fonctionnalités telles que l'ajout de vidéos, la suppression, la recherche par titre, la récupération des films, des séries, etc.

## Prérequis
Java 17

Maven

Spring Boot
## Lancement de l'application
Pour lancer l'application, exécutez la commande suivante dans le répertoire racine du projet :

bash

Copy code

mvn spring-boot:run

#### L'application sera accessible à l'adresse suivante : http://localhost:8080

## Endpoints disponibles
* Ajout de vidéos : POST /videos/add

* Récupération d'une vidéo par ID : GET /videos/{videoId}

* Recherche de vidéos par titre : GET /videos/search/{keyword}

* Suppression d'une vidéo par ID : DELETE /videos/delete/{videoId}

* Récupération des vidéos supprimées : GET /videos/deleted-ids

* Récupération des films : GET /videos/movies

* Récupération des séries : GET /videos/series

* Récupération des vidéos similaires : GET /videos/similar-videos