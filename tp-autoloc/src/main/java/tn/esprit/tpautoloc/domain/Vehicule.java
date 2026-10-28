package tn.esprit.tpautoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String immatriculation;
    private String marque;
    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;


    @ManyToOne
    @JoinColumn(name = "agence_id")
    private Agence agence;


    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations = new ArrayList<>();


    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    @JsonManagedReference("vehicule-maintenances")
    private List<Maintenance> maintenances = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "vehicule_equipements",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    @JsonIgnore
    private List<Equipement> equipements = new ArrayList<>();
}