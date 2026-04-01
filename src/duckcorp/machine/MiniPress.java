package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.duck.MiniDuck;

/**
 * Presse produisant des Mini Canards.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class MiniPress extends Machine {

    public static final int PURCHASE_COST    = 300;
    public static final int CAPACITY         = 8;
    public static final int MAINTENANCE_COST = 30;

    /**
     * Constructeur d'une mini-presse.
     */
    public MiniPress() {
        // Appel au constructeur de Machine avec le type de canard, la capacité et le coût d'entretien
        super(DuckType.MINI, CAPACITY, MAINTENANCE_COST);
    }

    @Override
    public Duck produceDuck() {
        // Crée un nouveau MiniDuck en utilisant la qualité calculée par la machine
        return new MiniDuck(computeQuality());
    }

    @Override
    public int getPurchaseCost() {
        return PURCHASE_COST;
    }

    @Override
    public String getName() {
        return "Mini-Presse";
    }
}