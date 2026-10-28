package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

/**
 * Stratégie Weekend : tarif journalier × jours × majoration de 10 %.
 * (coefficient 0.90 — légère remise par rapport au tarif plein)
 */
@Component
public class TarifWeekendStrategy implements ITarificationStrategy {

    private static final BigDecimal REMISE_WEEKEND = new BigDecimal("0.90");

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
                .multiply(REMISE_WEEKEND);
    }

    @Override
    public String getCode() {
        return "WEEKEND";
    }
}
