package com.example.cFormation.repositories;

import com.example.cFormation.models.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormateurRepository extends JpaRepository<Formateur, Integer> {
    @Query("SELECT f.nom, f.prenom, f.email, f.tel, f.type as specialite, e.nomEmployeur as employeur, COUNT(fm.id) as nb " +
            "FROM Formateur f " +
            "LEFT JOIN Formation fm ON fm.formateur.id = f.id " +
            "LEFT JOIN f.employeur e " +
            "GROUP BY f.id, e.nomEmployeur " +
            "ORDER BY nb DESC " +
            "LIMIT 3")
    List<Object[]> findTop3FormateursDetails();
    // Méthode 1: Récupère les participants par ID de structure
    @Query("SELECT f FROM Formateur f WHERE f.employeur.id = :employeurId")
    List<Formateur> findByEmployeurId(@Param("employeurId") int employeurId);



    // Alternative avec nommage conventionnel (sans @Query)
    List<Formateur> findByEmployeur_Id(int employeurId);
}
