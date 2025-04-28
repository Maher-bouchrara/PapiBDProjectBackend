package com.example.cFormation.repositories;

import com.example.cFormation.models.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
    @Query(value = "WITH top_domaines AS (" +
            "  SELECT d.id FROM formation f JOIN domaine d ON f.domaine_id = d.id " +
            "  GROUP BY d.id ORDER BY COUNT(f.id) DESC LIMIT 3" +
            ") " +
            "SELECT d.libelle as domaine, (f.date DIV 100) % 100 as mois, " +
            "AVG(f.budget) as budgetMoyen, COUNT(f.id) as nombreFormations " +
            "FROM formation f JOIN domaine d ON f.domaine_id = d.id " +
            "JOIN top_domaines td ON d.id = td.id " +
            "GROUP BY d.libelle, (f.date DIV 100) % 100 " +
            "ORDER BY d.libelle, mois",
            nativeQuery = true)
    List<Object[]> findTop3DomainesBudgetMensuel();

    // Your existing methods
    @Query("SELECT f FROM Formation f WHERE f.formateur.id = :formateurId")
    List<Formation> findByFormateurId(@Param("formateurId") int formateurId);

    @Query("SELECT f FROM Formation f WHERE f.domaine.id = :domaineId")
    List<Formation> findByDomaineId(@Param("domaineId") int domaineId);
}

