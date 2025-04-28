package com.example.cFormation.dto;

import com.example.cFormation.models.Formateur;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class FormateurStatsDto {
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String specialite;
    private String employeur;
    private long nb;

    public FormateurStatsDto(String nom, String prenom, String email, String tel,
                           String specialite, String employeur, long nb) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.specialite = specialite;
        this.employeur = employeur;
        this.nb = nb;
    }
}
