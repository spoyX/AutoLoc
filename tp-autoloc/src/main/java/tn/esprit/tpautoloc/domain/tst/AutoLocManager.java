package tn.esprit.tpautoloc.domain.tst;

/**
 * Partie 1 — AutoLoc v0
 *
 * Classe problématique qui concentre TOUTES les responsabilités
 * dans une seule classe (violation du SRP et de l'OCP).
 *
 * Responsabilités identifiées :
 *  1. Calcul du prix de location (logique tarifaire)
 *  2. Création / sauvegarde du contrat
 *  3. Notification du client (email)
 *  4. Notification par SMS
 *  5. Enregistrement du paiement
 */
public class AutoLocManager {

    public void louerVehicule(String typeVehicule, int nombreJours, String clientEmail) {

        double prix = 0;

        // ---- Violation OCP : chaque nouveau type de réservation impose de modifier cette méthode ----
        if (typeVehicule.equals("LongDuree")) {
            prix = nombreJours * 120;
        } else if (typeVehicule.equals("Weekend")) {
            prix = nombreJours * 180;
        } else if (typeVehicule.equals("Standard")) {
            prix = nombreJours * 150;
        }

        System.out.println("Contrat créé");
        System.out.println("Prix total : " + prix);

        envoyerEmail(clientEmail);
        enregistrerPaiement();
        sauvegarderContrat();
    }

    private void envoyerEmail(String email) {
        System.out.println("Email envoyé à " + email);
    }

    private void envoyerSMS(String sms) {
        System.out.println("SMS envoyé à " + sms);
    }

    private void sauvegarderContrat() {
        System.out.println("Contrat sauvegardé");
    }

    private void enregistrerPaiement() {
        System.out.println("Paiement enregistré");
    }
}
