package com.example.cFormation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Formateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeur_id")
    private Employeur employeur;


    @Transient  // N'est pas persisté en base (calculé dynamiquement)
    public int getIdEmployeur() {
        return (this.employeur != null) ? this.employeur.getId() : 0;
    }

    // Méthode pour définir la structure par son ID
    public void setIdEmployeur(int idEmployeur) {
        if (this.employeur == null) {
            this.employeur = new Employeur();
        }
        this.employeur.setId(idEmployeur);
    }

}