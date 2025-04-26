package com.example.cFormation.services;

import com.example.cFormation.dto.UtilisateurDTO;
import com.example.cFormation.exception.ResourceNotFoundException;
import com.example.cFormation.mapper.UtilisateurMapper;
import com.example.cFormation.models.Utilisateur;
import com.example.cFormation.models.Role;
import com.example.cFormation.repositories.UtilisateurRepository;
import com.example.cFormation.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final UtilisateurMapper utilisateurMapper; // Injection du Mapper

    public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, UtilisateurMapper utilisateurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.utilisateurMapper = utilisateurMapper;
    }


    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(int id) {
        return utilisateurRepository.findById(id);
    }

    public UtilisateurDTO getUtilisateurDTOById(int id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        return utilisateurMapper.toDto(utilisateur); // Utilisation correcte

    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur, int roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role non trouvé"));

        utilisateur.setRole(role);

        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtilisateur(int id, Utilisateur utilisateurDetails , int roleId) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role non trouvé"));

        utilisateur.setLogin(utilisateurDetails .getLogin());
        utilisateur.setMotdePasse(utilisateurDetails.getMotdePasse());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setRole(role);

        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        utilisateurRepository.deleteById(id);
    }

    // Méthode 1: Par structure ID
    public List<Utilisateur> getUtilisateursByRoleId(int roleId) {
        // Vérifie d'abord si la structure existe
        roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Role non trouvé avec l'ID: " + roleId));

        return utilisateurRepository.findByRole_Id(roleId);
    }


}
