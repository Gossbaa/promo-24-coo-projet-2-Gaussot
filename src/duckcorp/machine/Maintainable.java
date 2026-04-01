package duckcorp.machine;

/**
 * Interface représentant une machine pouvant être entretenue.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public interface Maintainable {

    /** Retourne l'état de la machine (entre 0 et 100). */
    int getCondition();

    /** Effectue une opération de maintenance sur la machine. */
    void maintain();

    /**
     * Retourne true si la machine nécessite une maintenance (condition < 30).
     */
    default boolean needsMaintenance() {
        return getCondition() < 30;
    }

    /**
     * Retourne un libellé décrivant l'état de la machine :
     * condition >= 80 -> "Parfait" / >= 50 -> "Correct" / >= 30 -> "Usé" / < 30 -> "Critique"
     */
    default String getConditionLabel() {
        int condition = getCondition();
        if (condition >= 80) {
            return "Parfait";
        } else if (condition >= 50) {
            return "Correct";
        } else if (condition >= 30) {
            return "Usé";
        } else {
            return "Critique";
        }
    }
}