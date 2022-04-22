package eu.hoefel.unit;

import java.util.List;

import eu.hoefel.unit.si.SiPrefix;

/**
 * The unit prefix, like e.g. {@link SiPrefix}, that may be used before a unit
 * symbol.
 * 
 * @author Udo Hoefel
 */
public interface UnitPrefix {

    /**
     * Provides corresponding (potentially multiple) prefix symbols, e.g. "k" or "M".
     * 
     * @return the unit symbols
     */
    public List<String> symbols();

    /**
     * Gets the factor this prefix corresponds to.
     * 
     * @return the conversion factor
     */
    public double factor();
}
