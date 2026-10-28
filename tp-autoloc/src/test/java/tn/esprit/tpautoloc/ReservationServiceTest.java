package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.service.impl.ReservationServiceImpl;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Partie 5 — Test du pattern Observer
 *
 * Vérifications attendues dans la console :
 *  [EMAIL] Confirmation envoyée au client ...
 *  [SMS]   Confirmation envoyée au téléphone de client ...
 *  [EMAIL] Annulation notifiée au client ...
 *  [SMS]   Annulation notifiée au téléphone de client ...
 */
@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationSubject reservationSubject;

    // -----------------------------------------------------------------------
    // Helper : construit un client de test sans persistence en base
    // -----------------------------------------------------------------------
    private Client buildClient() {
        Client client = new Client();
        client.setNom("Dupont");
        client.setPrenom("Alice");
        client.setEmail("alice.dupont@autoloc.tn");
        client.setTelephone("+21698765432");
        client.setNumPermis("TN-2023-001");
        return client;
    }

    // -----------------------------------------------------------------------
    // Test 1 : Création de réservation
    // Attendu en console :
    //   [EMAIL] Confirmation envoyée au client alice.dupont@autoloc.tn ...
    //   [SMS]   Confirmation envoyée au +21698765432 ...
    // -----------------------------------------------------------------------
    @Test
    void testCreerReservation_observerNotifiesEmailAndSms() {
        Client client = buildClient();

        Reservation reservation = reservationService.creerReservation(client);

        assertNotNull(reservation, "La réservation ne doit pas être null");
        assertEquals(StatutReservation.CONFIRMEE, reservation.getStatut(),
                "Le statut doit être CONFIRMEE après création");
        assertEquals(client, reservation.getClient(),
                "Le client doit être associé à la réservation");

        // Les logs [EMAIL] et [SMS] doivent apparaître dans la console
        System.out.println(">>> Test créerReservation terminé — vérifiez les logs [EMAIL] et [SMS] ci-dessus");
    }

    // -----------------------------------------------------------------------
    // Test 2 : Annulation de réservation
    // Attendu en console :
    //   [EMAIL] Annulation notifiée au client alice.dupont@autoloc.tn ...
    //   [SMS]   Annulation notifiée au +21698765432 ...
    // -----------------------------------------------------------------------
    @Test
    void testAnnulerReservation_observerNotifiesEmailAndSms() {
        Client client = buildClient();

        // On crée d'abord la réservation
        Reservation reservation = reservationService.creerReservation(client);

        // Puis on l'annule
        Reservation annulee = reservationService.annulerReservation(reservation);

        assertEquals(StatutReservation.ANNULEE, annulee.getStatut(),
                "Le statut doit être ANNULEE après annulation");

        // Les logs [EMAIL] et [SMS] d'annulation doivent apparaître dans la console
        System.out.println(">>> Test annulerReservation terminé — vérifiez les logs [EMAIL] et [SMS] ci-dessus");
    }
}
