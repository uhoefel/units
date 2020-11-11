package eu.hoefel.unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.DoubleUnaryOperator;
import java.util.regex.Matcher;

import eu.hoefel.unit.Units.StringBasedUnit;
import eu.hoefel.unit.imperial.ImperialUnit;
import eu.hoefel.unit.level.LevelUnit;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;
import eu.hoefel.utils.Strings;

/**
 * Interface for all units of measurement. In practice, most of the implemented
 * units will be compatible to the SI system. For reference consider:
 * <ul>
 * <li><a href="https://doi.org/10.1088/1681-7575/ab0013">2019 redefinition</a>
 * <li><a href=
 * "https://www.bipm.org/cc/TGFC/Allowed/Minutes/CODATA_Minutes_14-BIPM-public.pdf">minutes
 * of CODATA task group</a>
 * <li><a href=
 * "https://en.wikipedia.org/wiki/International_System_of_Units">Wikipedia</a>
 * </ul>
 * 
 * @apiNote Implementing classes must make sure to have
 *          {@link Object#equals(Object) equals} and {@link Object#hashCode()
 *          hashCode} properly implemented, such that a unit in a key of a
 *          {@link Map map} or in a {@link Set set} may only occur once. It is
 *          recommended to use enums.
 * 
 * @author Udo Hoefel
 */
public interface Unit {

	/**
	 * Provides corresponding (potentially multiple) unit symbols, e.g. "Angstrom"
	 * and "Å".
	 * 
	 * @return the unit symbols
	 */
	public List<String> symbols();

	/**
	 * Checks whether prefixes are allowed for the specified symbol of the unit.
	 * 
	 * @param symbol the symbol to check
	 * @return true if this unit allows prefixes
	 */
	public boolean prefixAllowed(String symbol);

	/**
	 * Checks whether the unit conversion to base units requires a shift (like e.g.
	 * {@link SiDerivedUnit#DEGREE_CELSIUS degree Celsius}) or whether the
	 * conversion can be done purely multiplicative.
	 * 
	 * @return true if conversion to base units is multiplicative
	 */
	public boolean canUseFactor();

	/**
	 * Gets the factor for the specified symbol to convert to base units. Note that
	 * not all conversions can be multiplicative (cf. {@link #canUseFactor()}).
	 * 
	 * @param symbol the symbol of which one wants the corresponding conversion
	 *               factor. Only relevant if different symbols correspond to
	 *               different factors, like e.g. for "kg" and "g" (which both are
	 *               recognized as representations of {@link SiBaseUnit#KILOGRAM}).
	 * @return the conversion factor to base SI units
	 */
	public double factor(String symbol);

	/**
	 * Gets the base {@link Unit units} and their exponents corresponding to this
	 * unit.
	 * 
	 * @return the base SI units and their exponents
	 */
	public Map<Unit, Integer> baseUnits();

	/**
	 * Converts {@code value} for this unit into the proper value in base units.
	 * This method also works for conversions that require a shift, not a
	 * multiplication, like e.g. for {@link SiDerivedUnit#DEGREE_CELSIUS degree
	 * Celsius}.
	 * 
	 * @param value the value in this unit
	 * @return the value in base SI units
	 */
	public double convertToBaseUnits(double value);

	/**
	 * Converts {@code value} from base units into this unit. This method also works
	 * for conversions that require a shift, not a multiplication, like e.g. for
	 * {@link SiDerivedUnit#DEGREE_CELSIUS degree Celsius}.
	 * 
	 * @param value the value in base SI units
	 * @return the value in this unit
	 */
	public double convertFromBaseUnits(double value);

	/**
	 * Gets the prefixes that are allowed to be used. Cannot be null, should be
	 * empty if there are no allowed prefixes.
	 * 
	 * @return allowed prefixes
	 */
	public Set<UnitPrefix> prefixes();

	/**
	 * Checks whether the unit is a base unit in its unit system. This will be false
	 * for most units, as they usually refer to the {@link SiBaseUnit SI units} 
	 * (this also holds true for, e.g., the {@link ImperialUnit Imperial units}).
	 * 
	 * @return true if unit is a basic unit
	 */
	public boolean isBasic();

	/**
	 * This is a convenience method for unusual units. It can provide units that
	 * should be checked against as well. Cannot be null, should be empty if there
	 * are no compatible units.
	 * 
	 * @return compatible units to be checked as well
	 */
	default Set<Unit> compatibleUnits() {
		return Set.of();
	}

	/**
	 * Gets a unit representation of the given units. Can handle composite (i.e., it
	 * can handle units like e.g. "kg m^2 s^-1") as well as non-composite (i.e., it
	 * can handle units like "kg", "m^2", "s") units. Will always be a singleton.
	 * Can be used in the
	 * 
	 * @param units      the units, e.g. "kg^2 s^-1"
	 * @param extraUnits the additional units to use for parsing the units
	 * @return a {@link Unit} representing the (potentially non-)composite unit
	 */
	public static Unit of(String units, Unit[]... extraUnits) {
		String trimmedUnits = Strings.trim(units);

		Unit[] flatExtraUnits = Units.flattenUnits(extraUnits);
		Unit[][] allExtraUnits = extraUnits;
		if (flatExtraUnits.length == 0) {
			flatExtraUnits = Units.DEFAULT_UNITS.stream().toArray(Unit[]::new);
			allExtraUnits = new Unit[0][];
		}

		// TODO only there to have an effective final version of the variable...
		Unit[][] finalAllExtraUnits = allExtraUnits;

		String[] unitParts = Units.LOG_UNIT_WITH_REF.split(trimmedUnits);
		UnitInfo[] logUnits = parseLogarithmicUnits(trimmedUnits, allExtraUnits);

		// add the logarithmic unit infos at the right position
		List<UnitInfo> infos = new ArrayList<>();
		int logUnitCounter = 0;
		for (int i = 0; i < unitParts.length; i++) {
			String trimmedUnitPart = Strings.trim(unitParts[i]);
			if (!trimmedUnitPart.isEmpty()) {
				Collections.addAll(infos, Units.collectInfo(trimmedUnitPart, allExtraUnits));
			}
			if (logUnitCounter < logUnits.length) infos.add(logUnits[logUnitCounter++]);
		}

		// remaining logarithmic units
		while (logUnitCounter < logUnits.length) {
			infos.add(logUnits[logUnitCounter++]);
		}

		UnitInfo[] infoArray = infos.stream().toArray(UnitInfo[]::new);

		StringBasedUnit stringBasedUnitInfo = new StringBasedUnit(trimmedUnits, Set.of(flatExtraUnits));
		if (infoArray.length == 1) {
			return Units.specialUnits.computeIfAbsent(stringBasedUnitInfo, s -> noncompositeUnitOf(trimmedUnits, infoArray, finalAllExtraUnits));
		}
		return Units.specialUnits.computeIfAbsent(stringBasedUnitInfo, s -> compositeUnitOf(trimmedUnits, infoArray, finalAllExtraUnits));
	}

	/**
	 * Finds the logarithmic units in the given string. Note that this is separate
	 * from the standard units as the representation of logarithmic units with
	 * respect to some reference value and unit contains spaces that otherwise one
	 * would split at to obtain individual units.
	 * 
	 * @param units      the string potentially containing the logarithmic units
	 *                   with respect to some reference, e.g. "log(re 1.03 mV)"
	 * @param extraUnits the additional units to use for parsing the units given in
	 *                   the string
	 * @return the unit infos of the found logarithmic units, or empty if no
	 *         logarithmic unit is found
	 */
	private static UnitInfo[] parseLogarithmicUnits(String units, Unit[]... extraUnits) {
		Matcher m = Units.LOG_UNIT_WITH_REF.matcher(units);
		List<UnitInfo> infos = new ArrayList<>();

		while (m.find()) {
			if (m.groupCount() != 5) {
				throw new IllegalArgumentException("Logarithmic unit with wrong number of captured groups found. "
						+ "Expected 5 groups, but found " + m.groupCount());
			}

			int exponent = m.group(2).isEmpty() ? 0 : Integer.parseInt(m.group(2));
			exponent += m.group(5).isEmpty() ? 0 : Integer.parseInt(m.group(5));

			// I don't like that BEL and NEPER are explicitly used here, but I cannot think
			// of a better way to handle the whitespace-including symbols of the LevelUnits
			// with respect to some other unit at the moment
			Unit u = switch (m.group(1)) {
			case "log" -> LevelUnit.BEL.inReferenceTo(Double.parseDouble(m.group(3)), Unit.of(m.group(4), extraUnits));
			case "ln" -> LevelUnit.NEPER.inReferenceTo(Double.parseDouble(m.group(3)), Unit.of(m.group(4), extraUnits));
			default -> throw new IllegalArgumentException("Cannot identify corresponding LevelUnit. "
					+ "Known are log->BEL and ln->NEPER, but you asked for " + m.group(1));
			};
			infos.add(new UnitInfo(Units.IDENTITY_PREFIX, u, m.group(), exponent));
		}

		return infos.stream().toArray(UnitInfo[]::new);
	}

	/**
	 * Builds a non-composite unit. Units considered non-composite include e.g.
	 * "kg", "m", but also "m^2".
	 * 
	 * @param units      the trimmed units, i.e., surrounding spaces should have
	 *                   been removed already
	 * @param infos      the unit info for the given unit
	 * @param extraUnits the additional units to use for parsing the units given in
	 *                   the string
	 * @return the unit representing e.g. "kg" or "s^-1"
	 */
	private static Unit noncompositeUnitOf(String units, UnitInfo[] infos, Unit[]... extraUnits) {
		BaseConversionInfo conversionInfo = Units.cleanup(infos);

		// identity prefixes will generally have an empty string as a symbol
		boolean isIdentityPrefix = infos[0].prefix().symbols().get(0).isEmpty();

		if (isIdentityPrefix && infos[0].exponent() == 1) {
			// this should be a normal non-composite unit with an exponent of 1, i.e. a unit
			// matching its original definition
			return infos[0].unit();
		}

		Unit unit = infos[0].unit();
		boolean prefixAllowed = isIdentityPrefix && unit.prefixAllowed(units);

		// if the exponent is not 1, it is not the base unit, i.e., "m^2" is not a base unit
		boolean isBasic = unit.isBasic() && infos[0].exponent() == 1;

		Set<UnitPrefix> prefixes = Set.copyOf(unit.prefixes());
		List<String> symbols = List.of(units);
		DoubleUnaryOperator toBase;
		DoubleUnaryOperator fromBase;
		if (conversionInfo.canUseFactor()) {
			toBase = v -> v * conversionInfo.factor();
			fromBase = v -> v / conversionInfo.factor();
		} else {
			double prefix = infos[0].prefix().factor();
			toBase = v -> unit.convertToBaseUnits(prefix * v);
			fromBase = v -> unit.convertFromBaseUnits(prefix * v);
		}
		Set<Unit> compatibleUnits = Set.of(Units.flattenUnits(extraUnits));

		return new Unit() {
			// Note that we do not override equals and hashcode here, as this unit is only
			// accessible via the concurrent map, so there should always be at most one
			// instance of a unit with the properties as specified here, so we are hopefully
			// fine if it uses the memory address for comparison
			@Override public List<String> symbols() { return symbols; }
			@Override public Set<UnitPrefix> prefixes() { return prefixes; }
			@Override public boolean prefixAllowed(String symbol) { return prefixAllowed; }
			@Override public boolean isBasic() { return isBasic; }
			@Override public double factor(String symbol) { return conversionInfo.factor(); }
			@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
			@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
			@Override public boolean canUseFactor() { return conversionInfo.canUseFactor(); }
			@Override public Map<Unit, Integer> baseUnits() { return Map.copyOf(conversionInfo.baseUnitInfos()); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
		};
	}

	/**
	 * Builds a composite unit. Units are considered composite if include at least
	 * two different units, i.e. e.g. "kg s", "m mol", but also "m^2 s^-1".
	 * 
	 * @param units      the trimmed units, i.e., surrounding spaces should have
	 *                   been removed already
	 * @param infos      the unit infos for the given unit
	 * @param extraUnits the additional units to use for parsing the units given in
	 *                   the string
	 * @return the unit representing e.g. "kg s" or "m^-2 s^-1"
	 */
	private static Unit compositeUnitOf(String units, UnitInfo[] infos, Unit[]... extraUnits) {
		BaseConversionInfo conversionInfo = Units.cleanup(infos);
		List<String> symbols = List.of(units);
		var baseUnitInfos = conversionInfo.baseUnitInfos();
		UnitInfo[] baseUnitInfo = Units.collectInfo(Units.toSymbol(baseUnitInfos), baseUnitInfos.keySet().toArray(Unit[]::new));
		DoubleUnaryOperator toBase = Units.internalConversionOperation(infos, baseUnitInfo);
		DoubleUnaryOperator fromBase = Units.internalConversionOperation(baseUnitInfo, infos);
		Set<Unit> compatibleUnits = Set.of(Units.flattenUnits(extraUnits));

		return new Unit() {
			// Note that we do not override equals and hashcode here, as this unit is only
			// accessible via the concurrent map, so there should always be at most one
			// instance of a unit with the properties as specified here, so we are hopefully
			// fine if it uses the memory address for comparison
			@Override public List<String> symbols() { return symbols; }
			@Override public Set<UnitPrefix> prefixes() { return Set.of(); }
			@Override public boolean prefixAllowed(String symbol) { return false; }
			@Override public boolean isBasic() { return false; }
			@Override public double factor(String symbol) { return conversionInfo.factor(); }
			@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
			@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
			@Override public boolean canUseFactor() { return conversionInfo.canUseFactor(); }
			@Override public Map<Unit, Integer> baseUnits() { return conversionInfo.baseUnitInfos(); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
		};
	}
}