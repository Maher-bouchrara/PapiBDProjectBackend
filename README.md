# Projet de Formation en Ligne - Back-End

## Description

Ce projet est une partie back-end d'un site web de formation en ligne. Il inclut des APIs permettant la gestion des utilisateurs et des cours, l'authentification via JSON Web Token (JWT), et d'autres fonctionnalités nécessaires pour une plateforme de formation. Le système gère les rôles des étudiants, formateurs, et participants, ainsi que l'accès aux cours.

## Fonctionnalités

- **Authentification des utilisateurs :**
  - API de login avec JWT pour la gestion sécurisée des sessions utilisateurs.
  - Vérification de l'authentification à chaque requête via JWT.

- **Gestion des cours :**
  - Création, modification et suppression des cours.
  - Accès aux détails des cours pour les étudiants et formateurs.

- **Gestion des utilisateurs :**
  - Gestion des formateurs : ajout, mise à jour et suppression des profils formateurs.
  - Gestion des participants : ajout des participants à un cours et suivi de leur progression.
  - Gestion des formations : ajout, mise à jour et suppression des formations.
  - Génération des certificas.
  - Envoie des mails automatiques pour les participants afin de recevoir leurs credentials.
  - Génération des statistiques

## Prérequis

- Java 11 ou version supérieure.
- Spring Boot Security.
- Base de données relationnelle (MySQL).
- Bibliothèque JWT pour la gestion des tokens.

## Installation

1. Clonez ce repository sur votre machine locale :

   ```bash
   git clone https://github.com/Maher-bouchrara/PapiBDProjectBackend.git
