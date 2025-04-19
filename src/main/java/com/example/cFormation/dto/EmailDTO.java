package com.example.cFormation.dto;
import java.util.List;


public class EmailDTO {

    private String objet;
    private String message;
    private List<String> listeMails;

    // Getters et setters
    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getListeMails() {
        return listeMails;
    }

    public void setListeMails(List<String> listeMails) {
        this.listeMails = listeMails;
    }
}
