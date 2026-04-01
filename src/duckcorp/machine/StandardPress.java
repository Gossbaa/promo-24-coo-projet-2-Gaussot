package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.StandardDuck;

/**
 * Presse produisant des canards Standard.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class StandardPress extends Machine {

    public static final int PURCHASE_COST    = 500;
    public static final int CAPACITY         = 5;
    public static final int MAINTENANCE_COST = 50;

    /**
     * Constructeur d'une presse standard.
     */
    public StandardPress() {
        // Appel au constructeur de Machine avec le type de canard, la capacité et le coût d'entretien
        super(DuckType.STANDARD, CAPACITY, MAINTENANCE_COST);
    }

    @Override
    public Duck produceDuck() {
        // Crée un nouveau StandardDuck en utilisant la qualité calculée par la machine
        return new StandardDuck(computeQuality());
    }

    @Override
    public int getPurchaseCost() {
        return PURCHASE_COST;
    }

    @Override
    public String getName() {
        return "Presse Standard";
    }
}