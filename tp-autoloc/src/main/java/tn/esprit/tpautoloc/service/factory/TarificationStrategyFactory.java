package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.ITarificationStrategy;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Patron GoF : FACTORY METHOD appliqué aux stratégies de tarification.
 *
 * Sélectionne dynamiquement l'ITarificationStrategy adaptée à une réservation
 * (durée du séjour, type de jour...).
 * Toutes les implémentations Spring de ITarificationStrategy
 * sont injectées automatiquement (Map&lt;code, strategy&gt;).
 */
@Component
public class TarificationStrategyFactory {

    private final Map<String, ITarificationStrategy> strategies;

    /**
     * Spring injecte la liste de tous les beans ITarificationStrategy disponibles.
     * On les indexe par leur code pour une sélection O(1).
     */
    public TarificationStrategyFactory(List<ITarificationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        ITarificationStrategy::getCode,
                        Function.identity()));
    }

    /**
     * Résout la stratégie à appliquer en fonction de la réservation :
     * <ul>
     *   <li>≥ 7 jours → LONGUE_DUREE</li>
     *   <li>week-end (sam./dim. uniquement) → WEEKEND</li>
     *   <li>sinon → STANDARD</li>
     * </ul>
     */
    public ITarificationStrategy resoudre(Reservation reservation) {
        long nombreJours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin());

        if (nombreJours >= 7) {
            return strategies.get("LONGUE_DUREE");
        }
        if (estWeekend(reservation)) {
            return strategies.get("WEEKEND");
        }
        return strategies.get("STANDARD");
    }

    /**
     * Retourne true si la réservation couvre uniquement le week-end
     * (dateDebut = samedi ou dimanche ET durée ≤ 2 jours).
     */
    private boolean estWeekend(Reservation reservation) {
        DayOfWeek jour = reservation.getDateDebut().getDayOfWeek();
        long duree = ChronoUnit.DAYS.between(
                reservation.getDateDebut(),
                reservation.getDateFin());
        return duree <= 2
                && (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY);
    }
}
