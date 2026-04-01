package duckcorp.duck;

/**
 * Canard en plastique miniature.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class MiniDuck extends Duck {

    public static final double BASE_PRICE = 12.0;

    /**
     * Constructeur d'un mini canard.
     * @param qualityScore Le score de qualité (0 à 100)
     */
    public MiniDuck(int qualityScore) {
        // Appel au constructeur de la classe parente Duck avec le type MINI
        super(DuckType.MINI, qualityScore);
    }

    @Override
    public double getBasePrice() {
        return BASE_PRICE;
    }

    @Override
    public String describe() {
        return "Mini Canard";
    }
}