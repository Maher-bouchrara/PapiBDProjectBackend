package com.example.cFormation.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class FormateurDTO {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private String type;
    // Au lieu de l'objet complet, on ne garde que l'ID du profile
    private Integer employeurId;


}