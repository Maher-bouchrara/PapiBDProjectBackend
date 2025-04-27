
package com.example.cFormation.services;
import com.example.cFormation.models.Employeur;
import com.example.cFormation.repositories.EmployeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeurService {

    @Autowired
    private EmployeurRepository employeurRepository;

    public List<Employeur> getAllEmployeurs() {
        return employeurRepository.findAll();
    }

    public Optional<Employeur> getEmployeurById(int id) {
        return employeurRepository.findById(id);
    }

    public Employeur createEmployeur(Employeur employeur) {
        return employeurRepository.save(employeur);
    }

    public void deleteEmployeur(int id) {
        employeurRepository.deleteById(id);
    }
    
    public Employeur updateEmployeur(int id, Employeur employeur) {
        // Vérifier si l'employeur existe
        Optional<Employeur> existingEmployeur = employeurRepository.findById(id);

        if (existingEmployeur.isPresent()) {
            // Mettre à jour l'employeur avec les nouvelles valeurs
            Employeur updatedEmployeur = existingEmployeur.get();
            updatedEmployeur.setNomEmployeur(employeur.getNomEmployeur());
            // Ajouter ici d'autres propriétés si nécessaire
            return employeurRepository.save(updatedEmployeur);
        } else {
            // Si l'employeur n'existe pas, vous pouvez gérer cela comme une exception
            throw new RuntimeException("Employeur avec ID " + id + " non trouvé");
        }
    }

}
