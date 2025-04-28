package com.example.cFormation.dto;

import lombok.Data;

@Data
public class DomaineBudgetMensuelDto {
    private String domaine;
    private int mois;
    private double budgetMoyen;
    private int nombreFormations;

    public DomaineBudgetMensuelDto(String domaine, int mois, double budgetMoyen, int nombreFormations) {
        this.domaine = domaine;
        this.mois = mois;
        this.budgetMoyen = budgetMoyen;
        this.nombreFormations = nombreFormations;
    }
}
