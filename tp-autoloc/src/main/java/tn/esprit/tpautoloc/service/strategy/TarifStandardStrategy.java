package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

/**
 * Stratégie Standard : tarifJournalier × nombre de jours, sans remise.
 */
@Component
public class TarifStandardStrategy implements ITarificationStrategy {

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        long nombreJours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin());
        if (nombreJours <= 0) {
            nombreJours = 1; // minimum 1 jour
        }
        return vehicule.getTarifJournalier()
                .multiply(BigDecimal.valueOf(nombreJours));
    }

    @Override
    public String getCode() {
        return "STANDARD";
    }
}
