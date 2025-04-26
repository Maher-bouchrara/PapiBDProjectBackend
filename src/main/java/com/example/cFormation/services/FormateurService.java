package com.example.cFormation.services;

import com.example.cFormation.dto.FormateurDTO;
import com.example.cFormation.exception.ResourceNotFoundException;
import com.example.cFormation.mapper.FormateurMapper;
import com.example.cFormation.models.Formateur;
import com.example.cFormation.models.Employeur;
import com.example.cFormation.repositories.FormateurRepository;
import com.example.cFormation.repositories.EmployeurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormateurService {

    private final FormateurRepository formateurRepository;
    private final EmployeurRepository employeurRepository;
    private final FormateurMapper participantMapper;
    private final FormateurMapper formateurMapper;

    public FormateurService(FormateurRepository formateurRepository,
                            EmployeurRepository employeurRepository,
                            FormateurMapper participantMapper,
                            FormateurMapper formateurMapper) {
        this.formateurRepository = formateurRepository;
        this.employeurRepository = employeurRepository;
        this.participantMapper = participantMapper;
        this.formateurMapper = formateurMapper;
    }

    public List<Formateur> getAllFormateurs() {
        return formateurRepository.findAll();
    }

    public Optional<Formateur> getFormateurById(int id) {
        return formateurRepository.findById(id);
    }

    public FormateurDTO getFormateurDTOById(int id) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formateur non trouvé"));
        return formateurMapper.toDto(formateur);
    }

    public Formateur createFormateur(Formateur formateur, int employeurId) {
        Employeur employeur = employeurRepository.findById(employeurId)
                .orElseThrow(() -> new RuntimeException("Profile non trouvé"));
        formateur.setEmployeur(employeur);
        return formateurRepository.save(formateur);
    }

    public Formateur updateFormateur(int id, Formateur formateurDetails) {
        Formateur formateur = formateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé avec l'ID : " + id));

        formateur.setNom(formateurDetails.getNom());
        formateur.setPrenom(formateurDetails.getPrenom());
        formateur.setEmail(formateurDetails.getEmail());
        formateur.setTel(formateurDetails.getTel());
        formateur.setType(formateurDetails.getType()); // Ajout de la mise à jour du type
        formateur.setIdEmployeur(formateurDetails.getIdEmployeur());

        return formateurRepository.save(formateur);
    }

    public void deleteFormateur(int id) {
        formateurRepository.deleteById(id);
    }

    public List<Formateur> getFormateursByEmployeurId(int employeurId) {
        employeurRepository.findById(employeurId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employeur non trouvé avec l'ID: " + employeurId));
        return formateurRepository.findByEmployeur_Id(employeurId);
    }

    // Dans FormateurService.java
    public List<Employeur> getAllEmployeurs() {
        return employeurRepository.findAll();
    }

}