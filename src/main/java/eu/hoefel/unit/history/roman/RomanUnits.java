package eu.hoefel.unit.history.roman;

/**
 * Helper class for {@link RomanUnit}.
 * 
 * @author Udo Hoefel
 */
class RomanUnits {

    /** Hiding any public constructor. */
    private RomanUnits() {
        throw new IllegalStateException("This is a pure utility class!");
    }

    /** One roman foot corresponds to roughly 0.296m. */
    static final double ROMAN_FOOT = 0.296;
    static final double ROMAN_SQUARE_FOOT = Math.pow(ROMAN_FOOT, 2);
    static final double ROMAN_CUBE_FOOT = Math.pow(ROMAN_FOOT, 3);
    static final double ROMAN_JUGERUM = 28_800 * ROMAN_SQUARE_FOOT;
    
    /** One roman libra corresponds to roughly 0.325kg. */
    static final double ROMAN_LIBRA = 0.325;
}
