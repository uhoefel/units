package eu.hoefel.unit;

/**
 * Helper record for passing info about units around.
 * 
 * @param prefix   the unit prefix
 * @param unit     the unit
 * @param symbol   the symbol
 * @param exponent the exponent of this unit part
 * 
 * @author Udo Hoefel
 */
public final record UnitInfo(UnitPrefix prefix, Unit unit, String symbol, int exponent) {}
