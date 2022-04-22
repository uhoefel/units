package eu.hoefel.unit;

import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;

/**
 * An interface for providing context for units. This allows to find the names
 * for given symbols.
 * 
 * @author Udo Hoefel
 */
@FunctionalInterface
public interface UnitContext {

    /**
     * Gets the map (which is sorted to guarantee reproducibility) that contains the
     * known names within the current context, and the symbols that correspond to a
     * known name.
     * 
     * @return the names and the symbols they correspond to
     */
    public NavigableMap<String, List<String>> namedSymbols();

    /**
     * Finds the (potentially multiple!) names within the given context for the
     * given units.
     * 
     * @param units      the units, e.g. "m s^-1"
     * @param match      the value that determines how strict the match between the
     *                   given units and the available named symbols should be
     * @param extraUnits additional units to check against
     * @return the names that match the given units within the current context
     */
    default NavigableSet<String> forUnits(String units, UnitContextMatch match, Unit[]... extraUnits) {
        return Units.inContext(units, match, this, extraUnits);
    }
}
