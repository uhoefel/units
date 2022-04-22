package eu.hoefel.unit.binary;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;
import eu.hoefel.unit.si.SiPrefix;

/**
 * Binary units, not compatible with standard {@link SiBaseUnit SI base units}.
 * They are commonly used e.g. in computing. Also, many measurement devices
 * measure {@link #BIT bit}, which have to be converted to, e.g.,
 * {@link SiDerivedUnit#VOLT volt}.
 */
public enum BinaryUnit implements Unit {

    /**
     * The bit is a basic unit of information in wide use in e.g. computing. It is a
     * portmanteau of binary digit. It can be used as a unit in information theory
     * or as a binary digit, representing a logical state. It is defined in the
     * <a href="https://www.iso.org/standard/31898.html">IEC 80000-13:2008</a>
     * standard.
     */
    BIT(1, "bit", "b"),

    /**
     * The byte is defined as 8 {@link #BIT bit}. Note that to avoid potential
     * conflicts of the unit symbol with Bel "B" is not an accepted symbol for byte.
     * However, "byte", "octet" and "o" are all allowed.
     */
    BYTE(8, "byte", "octet", "o"); // B conflicts with Bel :-/

    /** Units that should be loaded for the sake of convenience. */
    private static final Set<Unit> COMPATIBLE_UNITS = Set.of();

    /** The binary base units to which the binary unit corresponds. */
    private static final Map<Unit, Integer> BASE_UNITS = Map.of(BIT, 1);

    /** Allowed prefixes. */
    private final Set<UnitPrefix> prefixes;

    /** The symbols representing the binary unit. */
    private final List<String> symbols;

    /** The factor to convert from the binary unit to the SI base units. */
    private final double factor;

    /**
     * Constructor for binary units.
     * 
     * @param factor the factor
     * @param symbols the symbols representing the binary unit
     */
    BinaryUnit(double factor, String... symbols) {
        this.factor = factor;
        this.symbols = List.of(symbols);

        Set<UnitPrefix> prefixSet = new HashSet<>();
        Collections.addAll(prefixSet, SiPrefix.values());
        Collections.addAll(prefixSet, BinaryPrefix.values());
        this.prefixes = Set.copyOf(prefixSet);
    }

    @Override public double factor(String symbol) { return factor; }
    @Override public Map<Unit, Integer> baseUnits() { return BASE_UNITS; }
    @Override public List<String> symbols() { return symbols; }
    @Override public boolean prefixAllowed(String symbol) { return true; }
    @Override public boolean isConversionLinear() { return true; }
    @Override public double convertToBaseUnits(double value) { return factor * value; }
    @Override public double convertFromBaseUnits(double value) { return value / factor; }
    @Override public Set<UnitPrefix> prefixes() { return prefixes; }
    @Override public boolean isBasic() { return this == BIT; }
    @Override public Set<Unit> compatibleUnits() { return COMPATIBLE_UNITS; }
}
