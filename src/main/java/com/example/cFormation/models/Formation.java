package com.example.cFormation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

import lombok.EqualsAndHashCode;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude = {"participants"})
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;
    private int date;
    private int duree;
    private float budget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formateur_id")
    private Formateur formateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domaine_id")
    private Domaine domaine;

    @ManyToMany(mappedBy = "formations")
    @JsonManagedReference 
    private Set<Participant> participants;

    @Transient
    public int getIdFormateur() {
        return (this.formateur != null) ? this.formateur.getId() : 0;
    }

    public void setIdFormateur(int idFormateur) {
        if (this.formateur == null) {
            this.formateur = new Formateur();
        }
        this.formateur.setId(idFormateur);
    }

    @Transient
    public int getIdDomaine() {
        return (this.domaine != null) ? this.domaine.getId() : 0;
    }

    public void setIdDomaine(int idDomaine) {
        if (this.domaine == null) {
            this.domaine = new Domaine();
        }
        this.domaine.setId(idDomaine);
    }
}
