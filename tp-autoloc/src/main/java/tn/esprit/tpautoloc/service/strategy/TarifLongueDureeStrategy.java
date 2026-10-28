package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

/**
 * Stratégie Longue Durée : tarif journalier × jours × remise de 20 %.
 * (coefficient 0.80 — le client paie 80 % du tarif normal)
 */
@Component
public class TarifLongueDureeStrategy implements ITarificationStrategy {

    private static final BigDecimal REMISE_LONGUE_DUREE = new BigDecimal("0.80");

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long nombreJours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin());
        if (nombreJours <= 0) {
            nombreJours = 1;
        }
        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(nombreJours))
                .multiply(REMISE_LONGUE_DUREE);
    }

    @Override
    public String getCode() {
        return "LONGUE_DUREE";
    }
}
