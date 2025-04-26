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

public class Utilisateur {
            @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Login;
    private String MotdePasse;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;




    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getMotdePasse() {
        return MotdePasse;
    }

    public void setMotdePasse(String motdePasse) {
        MotdePasse = motdePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @Transient  // N'est pas persisté en base (calculé dynamiquement)
    public int getIdRole() {
        return (this.role != null) ? this.role.getId() : 0;
    }

    // Méthode pour définir la structure par son ID
    public void setIdRole(int idRole) {
        if (this.role == null) {
            this.role = new Role();
        }
        this.role.setId(idRole);
    }
}