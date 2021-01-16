package eu.hoefel.unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;

import eu.hoefel.unit.Units.StringRange;
import eu.hoefel.utils.Regexes;
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
	 * {@link eu.hoefel.unit.si.SiDerivedUnit#DEGREE_CELSIUS degree Celsius}) or
	 * whether the conversion can be done purely multiplicative.
	 * 
	 * @return true if conversion to base units is multiplicative
	 */
	// TODO isConversionMultiplicative? naming!
	public boolean canUseFactor();

	/**
	 * Gets the factor for the specified symbol to convert to base units. Note that
	 * not all conversions can be multiplicative (cf. {@link #canUseFactor()}).
	 * 
	 * @param symbol the symbol of which one wants the corresponding conversion
	 *               factor. Only relevant if different symbols correspond to
	 *               different factors, like e.g. for "kg" and "g" (which both are
	 *               recognized as representations of
	 *               {@link eu.hoefel.unit.si.SiBaseUnit#KILOGRAM}).
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
	 * multiplication, like e.g. for
	 * {@link eu.hoefel.unit.si.SiDerivedUnit#DEGREE_CELSIUS degree Celsius}.
	 * 
	 * @param value the value in this unit
	 * @return the value in base SI units
	 */
	public double convertToBaseUnits(double value);

	/**
	 * Converts {@code value} from base units into this unit. This method also works
	 * for conversions that require a shift, not a multiplication, like e.g. for
	 * {@link eu.hoefel.unit.si.SiDerivedUnit#DEGREE_CELSIUS degree Celsius}.
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
	 * for most units, as they usually refer to the
	 * {@link eu.hoefel.unit.si.SiBaseUnit SI units} (this also holds true for,
	 * e.g., the {@link eu.hoefel.unit.imperial.ImperialUnit Imperial units}).
	 * 
	 * @return true if unit is a basic unit
	 */
	public boolean isBasic();

	/**
	 * Gets units that should be checked against as well when parsing this unit.
	 * Cannot be null, should be empty if there are no compatible units. Only
	 * necessary for unusual units.
	 * 
	 * @return compatible units to be checked as well
	 */
	// TODO can I remove this?
	default Set<Unit> compatibleUnits() {
		return Set.of();
	}

	/**
	 * Gets the parser to recognize this unit in a given string.
	 * <p>
	 * This method only needs to be overridden if a unit requires non-standard
	 * parsing behavior, e.g. the units in {@link eu.hoefel.unit.level.LevelUnit}
	 * provide a special parser to handle Units created via
	 * {@link eu.hoefel.unit.level.LevelUnit#inReferenceTo(double, Unit)}. Using the
	 * {@link Units#DEFAULT_PARSER} if possible offers the advantage of a much
	 * faster parsing via {@link #of(String, Unit[][])}.
	 * 
	 * @return the parser, i.e. a function that takes a string, e.g. "kg^2 m" as
	 *         well as extra Units that may be required for parsing (this array can
	 *         be of zero length if no additional units are required) and return a
	 *         navigable map that holds the string ranges in which matches have been
	 *         found, and the decomposed information about the units at the
	 *         corresponding string range. The inputs to the parser should be
	 *         non-null.
	 */
	default BiFunction<String, Unit[][], NavigableMap<StringRange, UnitInfo>> parser() {
		return Units.DEFAULT_PARSER;
	}

	/**
	 * Gets a unit representation of the given units. Can handle composite units
	 * (i.e., it can handle units like e.g. "kg m^2 s^-1") as well as non-composite
	 * units (i.e., it can handle units like "kg", "m^2", "s").
	 * <p>
	 * Note that the returned unit omits the lambdas used in
	 * {@link #prefixAllowed(String)}, {@link #factor(String)},
	 * {@link #convertToBaseUnits(double)} and {@link #convertFromBaseUnits(double)}
	 * from the {@link #equals(Object)} and {@link #hashCode()} for practical
	 * reasons:
	 * <p>
	 * {@code // false if the above mentioned lambdas would be taken into account}<br>
	 * {@code Unit.of("kg^2").equals(Unit.of("kg^2"))}<br>
	 * <p>
	 * 
	 * @param units      the units, e.g. "kg^2 s^-1"
	 * @param extraUnits the additional units to use for parsing the units
	 * @return a {@link Unit} representing the (potentially non-)composite unit
	 */
	public static Unit of(String units, Unit[]... extraUnits) {
		String trimmedUnits = Strings.trim(units);

		Unit[] unitsForParsing = Units.flattenUnits(extraUnits);
		Unit[][] allExtraUnits = extraUnits;
		if (unitsForParsing.length == 0) {
			unitsForParsing = Units.DEFAULT_UNITS.stream().toArray(Unit[]::new);
			allExtraUnits = new Unit[0][];
		}

		NavigableMap<StringRange, UnitInfo> allUnitInfos = new TreeMap<>();
		
		String[] unitsRaw = Regexes.ALL_SPACE.split(units.trim());
		String[][] unitPower = new String[unitsRaw.length][];
		for (int i = 0; i < unitsRaw.length; i++) {
			unitPower[i] = Regexes.EXPONENT.split(unitsRaw[i].trim());
		}
		
		for (Unit unitForParsing : unitsForParsing) {
			if (unitForParsing.parser() == Units.DEFAULT_PARSER) {
				// this is the standard case
				var map = Units.provideUnitInfoWithRegexesAlreadyApplied(units, unitsRaw, unitPower, Units::checkUnits, new Unit[] { unitForParsing });
				allUnitInfos.putAll(map);
			} else {
				allUnitInfos.putAll(unitForParsing.parser().apply(units, allExtraUnits));
			}
		}
		
		// just to make sure we don't change this one by accident
		var finalAllUnitInfos = Collections.unmodifiableNavigableMap(allUnitInfos);

		// get a modifiable map
		NavigableMap<StringRange,UnitInfo> relevantUnitInfos = new TreeMap<>(allUnitInfos);

		// remove all substrings whose ranges get covered by a larger substring
		finalAllUnitInfos.forEach((range, info) -> {
			if (finalAllUnitInfos.keySet().stream().anyMatch(r -> r.comprises(range))) {
				relevantUnitInfos.remove(range);
			}
		});
		
		// make sure we have no overlapping ranges
		for (var sr1 : relevantUnitInfos.entrySet()) {
			for (var sr2 : relevantUnitInfos.entrySet()) {
				if (sr1.getKey().equals(sr2.getKey())) continue;
				if (sr1.getKey().intersects(sr2.getKey())) {
					throw new IllegalStateException(
							"The resolved units correspond to overlapping ranges in the given String. "
									+ "Unable to determine which unit to use. The units in question were "
									+ "%s (index %d to %d) and %s (index %d to %d) in %s".formatted(
											sr1.getValue(), sr1.getKey().from(), sr1.getKey().to(),
											sr2.getValue(), sr1.getKey().from(), sr2.getKey().to(), units));
				}
			}
		}
		
		var infos = new ArrayList<>(relevantUnitInfos.values());
		
		if (infos.isEmpty()) {
			return Units.unknownUnit(trimmedUnits);
		} else if (infos.size() == 1) {
			return noncompositeUnitOf(trimmedUnits, infos, allExtraUnits);
		}
		return compositeUnitOf(trimmedUnits, infos, allExtraUnits);
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
	private static Unit noncompositeUnitOf(String units, List<UnitInfo> infos, Unit[]... extraUnits) {
		BaseConversionInfo conversionInfo = Units.cleanup(infos);

		// identity prefixes will generally have an empty string as a symbol
		boolean isIdentityPrefix = infos.get(0).prefix().symbols().get(0).isEmpty();

		if (isIdentityPrefix && infos.get(0).exponent() == 1) {
			// this should be a normal non-composite unit with an exponent of 1, i.e. a unit
			// matching its original definition
			return infos.get(0).unit();
		}

		Unit unit = infos.get(0).unit();

		List<String> symbols = List.of(units);
		Set<UnitPrefix> prefixes = Set.copyOf(unit.prefixes());
		
		// if the exponent is not 1, it is not the base unit, i.e., "m^2" is not a base unit
		boolean isBasic = unit.isBasic() && infos.get(0).exponent() == 1;
		
		DoubleUnaryOperator toBase;
		DoubleUnaryOperator fromBase;
		if (conversionInfo.canUseFactor()) {
			toBase = v -> v * conversionInfo.factor();
			fromBase = v -> v / conversionInfo.factor();
		} else {
			double prefix = infos.get(0).prefix().factor();
			toBase = v -> unit.convertToBaseUnits(prefix * v);
			fromBase = v -> unit.convertFromBaseUnits(prefix * v);
		}

		Set<Unit> compatibleUnits;
		if (extraUnits.length == 0) {
			compatibleUnits = Units.DEFAULT_UNITS;
		} else {
			compatibleUnits = Set.of(Units.flattenUnits(extraUnits));
		}

		return new Unit() {
			@Override public List<String> symbols() { return symbols; }
			@Override public Set<UnitPrefix> prefixes() { return prefixes; }
			@Override public boolean prefixAllowed(String symbol) { return isIdentityPrefix && unit.prefixAllowed(units); }
			@Override public boolean isBasic() { return isBasic; }
			@Override public double factor(String symbol) { return conversionInfo.factor(); }
			@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
			@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
			@Override public boolean canUseFactor() { return conversionInfo.canUseFactor(); }
			@Override public Map<Unit, Integer> baseUnits() { return Map.copyOf(conversionInfo.baseUnitInfos()); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
			
			@Override
			public String toString() {
				return "DynamicUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
						+ canUseFactor() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits + "]";
			}

			@Override
			public int hashCode() {
				return Objects.hash(baseUnits(), canUseFactor(), compatibleUnits, isBasic(), prefixes(), symbols());
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj instanceof Unit other) {
					return Objects.equals(baseUnits(), other.baseUnits()) && canUseFactor() == other.canUseFactor()
							&& Objects.equals(compatibleUnits, other.compatibleUnits()) && isBasic() == other.isBasic()
							&& Objects.equals(prefixes(), other.prefixes()) && Objects.equals(symbols(), other.symbols());
				}
				return false;
			}
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
	private static Unit compositeUnitOf(String units, List<UnitInfo> infos, Unit[]... extraUnits) {
		BaseConversionInfo conversionInfo = Units.cleanup(infos);
		var baseUnitInfos = conversionInfo.baseUnitInfos();
		var baseUnitInfo = Units.collectInfo(Units.toSymbol(baseUnitInfos), baseUnitInfos.keySet().toArray(Unit[]::new)).values();

		List<String> symbols = List.of(units);
		DoubleUnaryOperator toBase = Units.internalConversionOperation(infos, baseUnitInfo);
		DoubleUnaryOperator fromBase = Units.internalConversionOperation(baseUnitInfo, infos);

		Set<Unit> compatibleUnits;
		if (extraUnits.length == 0) {
			compatibleUnits = Units.DEFAULT_UNITS;
		} else {
			compatibleUnits = Set.of(Units.flattenUnits(extraUnits));
		}

		return new Unit() {
			@Override public List<String> symbols() { return symbols; }
			@Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
			@Override public boolean prefixAllowed(String symbol) { return false; }
			@Override public boolean isBasic() { return false; }
			@Override public double factor(String symbol) { return conversionInfo.factor(); }
			@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
			@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
			@Override public boolean canUseFactor() { return conversionInfo.canUseFactor(); }
			@Override public Map<Unit, Integer> baseUnits() { return conversionInfo.baseUnitInfos(); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
			
			@Override
			public String toString() {
				return "DynamicUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
						+ canUseFactor() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits + "]";
			}

			@Override
			public int hashCode() {
				return Objects.hash(baseUnits(), canUseFactor(), compatibleUnits, isBasic(), prefixes(), symbols());
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj instanceof Unit other) {
					return Objects.equals(baseUnits(), other.baseUnits()) && canUseFactor() == other.canUseFactor()
							&& Objects.equals(compatibleUnits, other.compatibleUnits()) && isBasic() == other.isBasic()
							&& Objects.equals(prefixes(), other.prefixes()) && Objects.equals(symbols(), other.symbols());
				}
				return false;
			}
		};
	}
}