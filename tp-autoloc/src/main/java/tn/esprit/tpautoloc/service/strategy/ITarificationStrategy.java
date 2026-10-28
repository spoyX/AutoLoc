package tn.esprit.tpautoloc.service.strategy;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;

/**
 * Patron GoF : STRATEGY
 *
 * Interface commune à toutes les stratégies de tarification.
 * Chaque implémentation encapsule un algorithme de calcul du prix
 * sans modifier le code appelant (principe OCP).
 */
public interface ITarificationStrategy {

    /**
     * Calcule le tarif total pour une location donnée.
     *
     * @param vehicule    le véhicule loué (contient le tarifJournalier)
     * @param reservation la réservation (contient dateDebut/dateFin)
     * @return le montant total en BigDecimal
     */
    BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation);

    /**
     * Code unique de la stratégie, utilisé par la Factory pour la sélection
     * dynamique (ex. "STANDARD", "WEEKEND", "LONGUE_DUREE").
     */
    String getCode();
}
