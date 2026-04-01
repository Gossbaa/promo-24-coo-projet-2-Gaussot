package duckcorp.duck;

/**
 * Canard en plastique de luxe.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class LuxuryDuck extends Duck {

    public static final double BASE_PRICE = 80.0;

    /**
     * Constructeur d'un canard de luxe.
     * @param qualityScore Le score de qualité (0 à 100)
     */
    public LuxuryDuck(int qualityScore) {
        // Appel au constructeur de la classe parente Duck avec le type LUXURY
        super(DuckType.LUXURY, qualityScore);
    }

    @Override
    public double getBasePrice() {
        return BASE_PRICE;
    }

    @Override
    public String describe() {
        return "Canard de Luxe";
    }
}