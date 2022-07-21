package eu.hoefel.unit.special;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiCommonUnit;
import eu.hoefel.unit.si.SiPrefix;

/** These units are in common use in fusion plasma research. */
public enum FusionPlasmaUnit implements Unit {

    /**
     * The unit (1e19) for all kind of densities, so for both electron and ion densities.
     * Note that impurities should use {@link #IMPURITY_DENSITY}.
     */
    DENSITY(1e19, "n"),

    /** The unit for all kind of temperatures (keV). */
    TEMPERATURE(SiPrefix.KILO.factor() * SiCommonUnit.ELECTRONVOLT.factor(), "keV"),

    /**
     * The unit (1e14) to be used for impurity densities. Impurity densities are in
     * general much lower than electron or main species ion densities, for which
     * {@link #DENSITY} should be used.
     */
    IMPURITY_DENSITY(1e14, "nimp"),

    /** The unit for the current (kA). */
    CURRENT(1e3, "I"),

    /** The unit for the current density (MA m^-2). */
    CURRENT_DENSITY(1e6, "j"),

    /** The unit for the pressure (kPa). */
    PRESSURE(1, "p"),

    /** The unit for the plasma energy (kJ). */
    PLASMA_ENERGY(1, "E"),

    /** The unit for wavelengths (nm). */
    WAVELENGTH(1e-9, "nm"),

    /** The unit for rotation frequencies (rad ms^-1). */
    ROTATION_FREQUENCY(1e3, "frot"),

    /** The unit for rotation frequencies (km s^-1). */
    ROTATION_VELOCITY(1e3, "vrot");

    /** The symbols representing the fusion plasma unit. */
    private final List<String> symbols;

    /** The SI base units to which the fusion plasma unit corresponds. */
    private Map<Unit, Integer> baseUnits;

    /** The factor to convert from the fusion plasma unit to the SI base units. */
    private double factor = Double.NaN;

    /**
     * Constructor for fusion plasma units.
     * 
     * @param factor  the conversion factor to base SI units
     * @param symbols the symbols representing the fusion plasma unit
     */
    private FusionPlasmaUnit(double factor, String... symbols) {
        this.factor = factor;
        this.symbols = List.of(symbols);
    }

    @Override
    public Map<Unit, Integer> baseUnits() {
        if (baseUnits == null) {
            baseUnits = switch (this) {
                case CURRENT            -> Map.of(SiBaseUnit.AMPERE, 1);
                case CURRENT_DENSITY    -> Map.of(SiBaseUnit.AMPERE, 1, SiBaseUnit.METER, -2);
                case DENSITY            -> Map.of(SiBaseUnit.METER, -3);
                case IMPURITY_DENSITY   -> Map.of(SiBaseUnit.METER, -3);
                case PLASMA_ENERGY      -> Map.of(SiBaseUnit.GRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2);
                case PRESSURE           -> Map.of(SiBaseUnit.GRAM, 1, SiBaseUnit.METER, -1, SiBaseUnit.SECOND, -2);
                case ROTATION_FREQUENCY -> Map.of(SiBaseUnit.SECOND, -1);
                case ROTATION_VELOCITY  -> Map.of(SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -1);
                case TEMPERATURE        -> SiCommonUnit.ELECTRONVOLT.baseUnits();
                case WAVELENGTH         -> Map.of(SiBaseUnit.METER, 1);
            };
        }
        return baseUnits;
    }

    @Override public double factor() { return factor; }
    @Override public List<String> symbols() { return symbols; }
    @Override public boolean prefixAllowed(String symbol) { return false; }
    @Override public boolean isConversionLinear() { return true; }
    @Override public double convertToBaseUnits(double value) { return factor * value; }
    @Override public double convertFromBaseUnits(double value) { return value / factor; }
    @Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
    @Override public boolean isBasic() { return false; }
}
