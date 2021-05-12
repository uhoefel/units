package eu.hoefel.unit;

import java.util.Map;

/**
 * Helper record for passing information about units (like "Pa") and unit
 * composites (like "kg^2 m^-3 s^-1") around. Not intended to be public.
 * 
 * @param factor        the conversion factor to base units, including potential prefixes
 * @param baseUnitInfos a map of all base units with their exponents that
 *                      correspond to the unit for which the information was
 *                      collected
 * @param canUseFactor  the boolean that if true signifies that a pure
 *                      multiplicative operation is sufficient to get into SI
 *                      base units (this will yield false e.g. if the units that
 *                      produced this {@code info} contained degree Celsius,
 *                      "°C")
 * @author Udo Hoefel
 */
final record BaseConversionInfo(double factor, Map<Unit, Integer> baseUnitInfos, boolean canUseFactor) { }
