package duckcorp.duck;

/**
 * Canard en plastique standard.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class StandardDuck extends Duck {

    public static final double BASE_PRICE = 25.0;

    /**
     * Constructeur d'un canard standard.
     * * @param qualityScore Le score de qualité (0 à 100)
     */
    public StandardDuck(int qualityScore) {
        // Appel au constructeur de la classe parente Duck
        super(DuckType.STANDARD, qualityScore);
    }

    @Override
    public double getBasePrice() {
        return BASE_PRICE;
    }

    @Override
    public String describe() {
        return "Canard Standard";
    }
}