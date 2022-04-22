package eu.hoefel.unit.special;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.si.SiBaseUnit;

/**
 * Swabian units that are not standard SI base units or in the set of standard
 * derived SI units.
 * 
 * @author Udo Hoefel
 */
public enum SwabianUnit implements Unit {

    /** A viertele isch a Maßeinheit fir Woi. A viertele wird gschlotzt, net trunke. */
    VIERTELE(0.00025, "viertele"),

    /**
     * A muggaseggele isch furchtbar wenich. Es isch von em Naturkundler em Museum
     * en Schdugerd gmesse worde, guggsch au
     * <a href="https://de.wikipedia.org/wiki/Muggeseggele">do</a>.
     */
    MUGGASEGGELE(0.00022, "muggaseggele"),

    /**
     * A fitsele isch bei ons definiert els a Volume mit'rer Kanteläng von fuffzich
     * {@link #MUGGASEGGELE muggaseggele}.
     */
    FITSELE(Math.pow(50 * 0.00022, 3), "fitsele"),

    /**
     * A breggele isch net so viel wie a {@link #BROGGA brogga}, abr scho meh als a
     * {@link #FITSELE fitsele}.
     */
    BREGGELE(Math.pow(100 * 0.00022, 3), "breggele"),

    /**
     * A wengele isch etwa a Prise ond des isch etwa des, was zwische de Zeigefenger
     * ond de Daume neigoht.
     */
    WENGELE(5 * Math.pow(100 * 0.00022, 3), "wengele"),

    /** Mr braucht scho mindeschtens zeah {@link #BREGGELE breggele} fir en brogga. */
    BROGGA(10 * Math.pow(100 * 0.00022, 3), "brogga");

    /** The symbols representing the swabian unit. */
    private final List<String> symbols;

    /** The factor to convert from the swabian unit to the SI base units. */
    private final double factor;

    /** The SI base units to which the swabian unit corresponds. */
    private Map<Unit, Integer> baseUnits;

    /**
     * Constructor for swabian SI units.
     * 
     * @param factor  the conversion factor to base SI units
     * @param symbols the symbols representing the swabian unit
     */
    private SwabianUnit(double factor, String... symbols) {
        this.factor = factor;
        this.symbols = List.of(symbols);
    }

    @Override
    public Map<Unit, Integer> baseUnits() {
        if (baseUnits == null) {
            baseUnits = switch (this) {
                case MUGGASEGGELE -> Map.of(SiBaseUnit.METER, 1);
                case FITSELE, BREGGELE, WENGELE, BROGGA, VIERTELE -> Map.of(SiBaseUnit.METER, 3);
            };
        }
        return baseUnits;
    }

    @Override public double factor(String symbol) { return factor; }
    @Override public List<String> symbols() { return symbols; }
    @Override public boolean prefixAllowed(String symbol) { return false; }
    @Override public boolean isConversionLinear() { return true; }
    @Override public double convertToBaseUnits(double value) { return factor * value; }
    @Override public double convertFromBaseUnits(double value) { return value / factor; }
    @Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
    @Override public boolean isBasic() { return false; }
}
