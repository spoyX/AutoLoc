package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.*;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.ModePaiement;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.repository.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IclientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Autowired
    private IEquipementRepository equipementRepository;

    @Autowired
    private IPaiementRepository paiementRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testAgenceRepository() {

        System.out.println("\n========== TEST AGENCE REPOSITORY ==========");


        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis");
        agence.setVille("Tunis");
        agence.setAdresse("12 Avenue Habib Bourguiba");
        agence.setTelephone("71234567");
        System.out.println("→ Agence créée : " + agence);


        Agence saved = agenceRepository.save(agence);


        System.out.println("→ Après save() : " + saved);
        System.out.println("   → ID généré : " + saved.getIdAgence());
        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());

        List<Agence> toutes = agenceRepository.findAll();
        System.out.println("→ findAll() : " + toutes.size() + " agence(s)");
        toutes.forEach(a -> System.out.println("     " + a));
        assertFalse(toutes.isEmpty());

        Optional<Agence> trouvee = agenceRepository.findById(saved.getIdAgence());
        System.out.println("→ findById(" + saved.getIdAgence() + ") : " + trouvee);
        assertTrue(trouvee.isPresent());
        assertEquals("AutoLoc Tunis", trouvee.get().getNom());


        boolean existe = agenceRepository.existsById(saved.getIdAgence());
        System.out.println("→ existsById(" + saved.getIdAgence() + ") : " + existe);
        assertTrue(existe);

        long total = agenceRepository.count();
        System.out.println("→ count() : " + total);
        assertTrue(total > 0);


        Long id = saved.getIdAgence();
        agenceRepository.deleteById(id);
        System.out.println("→ deleteById(" + id + ") effectué");

        boolean existeApres = agenceRepository.existsById(id);
        System.out.println("→ existsById(" + id + ") après suppression : " + existeApres);
        assertFalse(existeApres);


        System.out.println("========== FIN TEST AGENCE ==========\n");
    }

    @Test
    public void testClientRepository() {
        System.out.println("\n========== TEST CLIENT REPOSITORY ==========");

        // 1. Créer deux clients
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.benali@email.com");
        c1.setTelephone("20123456");
        c1.setNumPermis("P123456");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sarra");
        c2.setEmail("sarra.trabelsi@email.com");
        c2.setTelephone("98765432");
        c2.setNumPermis("P654321");
        c2.setDateInscription(LocalDate.now());

        // 2. save() les deux
        Client saved1 = clientRepository.save(c1);
        Client saved2 = clientRepository.save(c2);
        System.out.println("→ Client 1 sauvé : " + saved1);
        System.out.println("→ Client 2 sauvé : " + saved2);
        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());

        List<Client> clients = clientRepository.findAll();
        System.out.println("→ findAll() : " + clients.size() + " client(s)");
        clients.forEach(cl -> System.out.println("     " + cl));
        assertTrue(clients.size() >= 2);


        Optional<Client> trouve = clientRepository.findById(saved1.getIdClient());
        System.out.println("→ findById(" + saved1.getIdClient() + ") : " + trouve);
        assertTrue(trouve.isPresent());
        assertEquals("Ben Ali", trouve.get().getNom());

        boolean existe = clientRepository.existsById(saved1.getIdClient());
        System.out.println("→ existsById(" + saved1.getIdClient() + ") : " + existe);
        assertTrue(existe);

        boolean existePas = clientRepository.existsById(999999L);
        System.out.println("→ existsById(999999) : " + existePas);
        assertFalse(existePas);

        long total = clientRepository.count();
        System.out.println("→ count() : " + total);
        assertTrue(total >= 2);

        Long id1 = saved1.getIdClient();
        Long id2 = saved2.getIdClient();


        clientRepository.deleteById(id1);
        System.out.println("→ deleteById(" + id1 + ") effectué (client 1)");

        clientRepository.deleteById(id2);
        System.out.println("→ deleteById(" + id2 + ") effectué (client 2)");

        
        boolean existeApres2 = clientRepository.existsById(id2);
        System.out.println("→ existsById(" + id2 + ") après suppression : " + existeApres2);
        assertFalse(existeApres2);


        long totalApres = clientRepository.count();
        System.out.println("→ count() après suppressions : " + totalApres);
        assertTrue(totalApres < total);

        System.out.println("========== FIN TEST CLIENT ==========\n");
    }
    @Test
    public void testVehiculeRepository() {
        System.out.println("\n========== TEST VEHICULE REPOSITORY ==========");

        // 1. Créer un véhicule
        Vehicule v = new Vehicule();
        v.setImmatriculation("TN-1234-AB");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("120.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);
        System.out.println("→ Véhicule créé : " + v);

        // 2. save()
        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("→ Après save() : " + saved);
        assertNotNull(saved.getIdVehicule());

        // 3. findAll()
        List<Vehicule> tous = vehiculeRepository.findAll();
        System.out.println("→ findAll() : " + tous.size() + " véhicule(s)");
        tous.forEach(ve -> System.out.println("     " + ve));

        // 4. count()
        long total = vehiculeRepository.count();
        System.out.println("→ count() : " + total);
        assertTrue(total > 0);

        // 5. Modifier une propriété puis save()
        saved.setTarifJournalier(new BigDecimal("150.00"));
        Vehicule modifie = vehiculeRepository.save(saved);
        System.out.println("→ Après modification tarif : " + modifie.getTarifJournalier());
        assertEquals(new BigDecimal("150.00"), modifie.getTarifJournalier());

        // 6. deleteById()
        Long id = saved.getIdVehicule();
        vehiculeRepository.deleteById(id);
        System.out.println("→ deleteById(" + id + ") effectué");
        assertFalse(vehiculeRepository.existsById(id));

        System.out.println("========== FIN TEST VEHICULE ==========\n");
    }
    @Test
    public void testExerciceAutonome() {
        System.out.println("\n========== EXERCICE AUTONOME ==========");

        // ─── 1. MAINTENANCE ───────────────────────────────────
        System.out.println("--- Maintenance ---");
        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.now());
        m.setDateFin(LocalDate.now().plusDays(3));
        m.setDescription("Vidange + contrôle freins");

        Maintenance savedM = maintenanceRepository.save(m);
        System.out.println("→ Maintenance sauvée : " + savedM);
        assertNotNull(savedM.getIdMaintenance());

        Optional<Maintenance> trouveM = maintenanceRepository.findById(savedM.getIdMaintenance());
        assertTrue(trouveM.isPresent());
        System.out.println("→ findById(" + savedM.getIdMaintenance() + ") : " + trouveM.get());

        assertTrue(maintenanceRepository.existsById(savedM.getIdMaintenance()));
        System.out.println("→ existsById : true");
        System.out.println("→ findAll() : " + maintenanceRepository.findAll().size() + " maintenance(s)");

        // ─── 2. EQUIPEMENT ────────────────────────────────────
        System.out.println("--- Equipement ---");
        Equipement e = new Equipement();
        e.setLibelle("GPS");

        Equipement savedE = equipementRepository.save(e);
        System.out.println("→ Equipement sauvé : " + savedE);
        assertNotNull(savedE.getIdEquipement());

        Optional<Equipement> trouveE = equipementRepository.findById(savedE.getIdEquipement());
        assertTrue(trouveE.isPresent());
        System.out.println("→ findById(" + savedE.getIdEquipement() + ") : " + trouveE.get());

        assertTrue(equipementRepository.existsById(savedE.getIdEquipement()));
        System.out.println("→ existsById : true");
        System.out.println("→ findAll() : " + equipementRepository.findAll().size() + " équipement(s)");

        // ─── 3. PAIEMENT ──────────────────────────────────────
        System.out.println("--- Paiement ---");
        Paiement p = new Paiement();
        p.setMontant(new BigDecimal("500.00"));
        p.setDatePaiement(LocalDate.now());
        p.setModePaiement(ModePaiement.CARTE);

        Paiement savedP = paiementRepository.save(p);
        System.out.println("→ Paiement sauvé : " + savedP);
        assertNotNull(savedP.getIdPaiement());

        Optional<Paiement> trouveP = paiementRepository.findById(savedP.getIdPaiement());
        assertTrue(trouveP.isPresent());
        System.out.println("→ findById(" + savedP.getIdPaiement() + ") : " + trouveP.get());

        assertTrue(paiementRepository.existsById(savedP.getIdPaiement()));
        System.out.println("→ existsById : true");
        System.out.println("→ findAll() : " + paiementRepository.findAll().size() + " paiement(s)");

        // ─── SUPPRESSIONS ─────────────────────────────────────
        System.out.println("--- Suppressions ---");
        maintenanceRepository.deleteById(savedM.getIdMaintenance());
        equipementRepository.deleteById(savedE.getIdEquipement());
        paiementRepository.deleteById(savedP.getIdPaiement());

        assertFalse(maintenanceRepository.existsById(savedM.getIdMaintenance()));
        assertFalse(equipementRepository.existsById(savedE.getIdEquipement()));
        assertFalse(paiementRepository.existsById(savedP.getIdPaiement()));

        System.out.println("→ Toutes les entités ont été supprimées avec succès");
        System.out.println("========== FIN EXERCICE AUTONOME ==========\n");
    }
}






