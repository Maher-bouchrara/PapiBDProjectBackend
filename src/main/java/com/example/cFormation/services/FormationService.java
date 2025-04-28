package com.example.cFormation.services;

import com.example.cFormation.dto.DomaineBudgetMensuelDto;
import com.example.cFormation.dto.FormationDTO;
import com.example.cFormation.dto.DomainePourcentageDTO;
import com.example.cFormation.exception.ResourceNotFoundException;
import com.example.cFormation.mapper.FormationMapper;
import com.example.cFormation.models.Domaine;
import com.example.cFormation.models.Formateur;
import com.example.cFormation.models.Formation;
import com.example.cFormation.models.Participant;
import com.example.cFormation.repositories.DomaineRepository;
import com.example.cFormation.repositories.FormateurRepository;
import com.example.cFormation.repositories.FormationRepository;
import com.example.cFormation.repositories.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormationService {

    private final FormationRepository formationRepository;
    private final FormateurRepository formateurRepository;
    private final DomaineRepository domaineRepository;
    private final FormationMapper formationMapper;
    private final ParticipantRepository participantRepository;
    public FormationService(FormationRepository formationRepository,
                            FormateurRepository formateurRepository,
                            DomaineRepository domaineRepository,
                            FormationMapper formationMapper, ParticipantRepository participantRepository) {
        this.formationRepository = formationRepository;
        this.formateurRepository = formateurRepository;
        this.domaineRepository = domaineRepository;
        this.formationMapper = formationMapper;
        this.participantRepository = participantRepository;
    }

    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }

    public Optional<Formation> getFormationById(int id) {
        return formationRepository.findById(id);
    }

    public FormationDTO getFormationDTOById(int id) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Formation non trouvée"));
        return formationMapper.toDto(formation);
    }

    public Formation createFormation(Formation formation, int formateurId, int domaineId) {
        Formateur formateur = formateurRepository.findById(formateurId)
                .orElseThrow(() -> new RuntimeException("Formateur non trouvé"));

        Domaine domaine = domaineRepository.findById(domaineId)
                .orElseThrow(() -> new RuntimeException("Domaine non trouvé"));

        formation.setFormateur(formateur);
        formation.setDomaine(domaine);

        return formationRepository.save(formation);
    }

    public Formation updateFormation(int id, Formation formationDetails) {
        Formation formation = formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'ID : " + id));

        formation.setTitre(formationDetails.getTitre());
        formation.setDate(formationDetails.getDate());
        formation.setDuree(formationDetails.getDuree());
        formation.setBudget(formationDetails.getBudget());

        // Mise à jour des relations si nécessaire
        if (formationDetails.getFormateur() != null) {
            formation.setFormateur(formationDetails.getFormateur());
        }
        if (formationDetails.getDomaine() != null) {
            formation.setDomaine(formationDetails.getDomaine());
        }

        return formationRepository.save(formation);
    }

    public void deleteFormation(int id) {
        formationRepository.deleteById(id);
    }

    // Méthodes pour les relations

    public List<Formation> getFormationsByFormateurId(int formateurId) {
        formateurRepository.findById(formateurId)
                .orElseThrow(() -> new EntityNotFoundException("Formateur non trouvé avec l'ID: " + formateurId));
        return formationRepository.findByFormateurId(formateurId);
    }

    public List<Formation> getFormationsByDomaineId(int domaineId) {
        domaineRepository.findById(domaineId)
                .orElseThrow(() -> new EntityNotFoundException("Domaine non trouvé avec l'ID: " + domaineId));
        return formationRepository.findByDomaineId(domaineId);
    }
    public void addParticipantToFormation(int formationId, int participantId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        formation.getParticipants().add(participant);
        participant.getFormations().add(formation);

        formationRepository.save(formation);
        participantRepository.save(participant);
    }

    public void removeParticipantFromFormation(int formationId, int participantId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant non trouvé"));

        formation.getParticipants().remove(participant);
        participant.getFormations().remove(formation);

        formationRepository.save(formation);
        participantRepository.save(participant);
    }

    public Set<Participant> getFormationParticipants(int formationId) {
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        return formation.getParticipants();
    }
    public long countFormations() {
        return formationRepository.count();
    }

    public List<DomainePourcentageDTO> getFormationsPourcentageByDomaine() {
        List<Formation> formations = formationRepository.findAll();

        if (formations.isEmpty()) {
            return Collections.emptyList();
        }

        // Filtre + regroupement en une seule opération
        Map<String, Long> countByDomaine = formations.stream()
                .filter(f -> f.getDomaine() != null && f.getDomaine().getLibelle() != null)
                .collect(Collectors.groupingBy(
                        f -> f.getDomaine().getLibelle(),
                        Collectors.counting()
                ));

        long total = countByDomaine.values().stream().mapToLong(Long::longValue).sum();

        return countByDomaine.entrySet().stream()
                .map(e -> new DomainePourcentageDTO(
                        e.getKey(),
                        Math.round((e.getValue() * 100.0 / total) * 100) / 100.0
                ))
                .sorted(Comparator.comparingDouble(DomainePourcentageDTO::getPercentage).reversed())
                .collect(Collectors.toList());
    }
    public List<DomaineBudgetMensuelDto> getBudgetsMoyensParMoisTop3Domaines() {
        // Get the results using the native query
        List<Object[]> results = formationRepository.findTop3DomainesBudgetMensuel();

        // Map to DTO with proper type handling
        return results.stream()
                .map(result -> new DomaineBudgetMensuelDto(
                        (String) result[0], // domaine
                        ((Number) result[1]).intValue(), // convert month safely from any Number type to int
                        ((Number) result[2]).doubleValue(), // convert budget safely from any Number type to double
                        ((Number) result[3]).intValue() // convert count safely from any Number type to int
                ))
                .collect(Collectors.toList());
    }

}