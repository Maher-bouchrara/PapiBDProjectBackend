package com.example.cFormation.mapper;

import com.example.cFormation.dto.FormateurDTO;
import com.example.cFormation.models.Formateur;
import org.springframework.stereotype.Component;

@Component
public class FormateurMapper {

    public FormateurDTO toDto(Formateur formateur) {
        if (formateur == null) {
            return null;
        }

        FormateurDTO dto = new FormateurDTO();
        dto.setId(formateur.getId());
        dto.setNom(formateur.getNom());
        dto.setPrenom(formateur.getPrenom());
        dto.setEmail(formateur.getEmail());
        dto.setTel(formateur.getTel());
        dto.setType(formateur.getType()); // Ajout du mapping pour le type

        // Gestion des relations
        if (formateur.getEmployeur() != null) {
            dto.setEmployeurId(formateur.getEmployeur().getId());
        }

        return dto;
    }

    // Méthode inverse si nécessaire (DTO vers Entity)
    public Formateur toEntity(FormateurDTO dto) {
        if (dto == null) {
            return null;
        }

        Formateur formateur = new Formateur();
        formateur.setId(dto.getId());
        formateur.setNom(dto.getNom());
        formateur.setPrenom(dto.getPrenom());
        formateur.setEmail(dto.getEmail());
        formateur.setTel(dto.getTel());
        formateur.setType(dto.getType()); // Ajout du mapping pour le type

        // Note: La gestion de l'employeur se fait généralement dans le service
        // car elle nécessite souvent une recherche en base de données

        return formateur;
    }
}