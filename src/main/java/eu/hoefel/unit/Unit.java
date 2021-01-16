package eu.hoefel.unit;

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
	 * Checks whether the unit conversion to base units requires a nonlinear
	 * operation like a shift (like e.g.
	 * {@link eu.hoefel.unit.si.SiDerivedUnit#DEGREE_CELSIUS degree Celsius}) to get
	 * to the corresponding base units or whether the conversion can be done purely
	 * multiplicative, i.e. the operation is linear.
	 * 
	 * @return true if conversion to base units is multiplicative
	 */
	public boolean isConversionLinear();

	/**
	 * Gets the factor for the specified symbol to convert to base units. Note that
	 * not all conversions can be multiplicative (cf. {@link #isConversionLinear()}).
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
	 * Gets compatible units (nonexhaustive). This method serves mainly the purpose
	 * that, if e.g. {@link Units#convert(double, String, String, Unit[]...)} is
	 * used with the current unit provided within the {@code extraUnits} some
	 * default units are still available. To give a practical example:
	 * <p>
	 * {@code // Does only work because the TemperatureUnit provides}<br>
	 * {@code // SiBaseUnit.KELVIN in its compatible units}<br>
	 * {@code Units.convert(3, "K", "°F", TemperatureUnit.values());}
	 * <p>
	 * The reason for not hardcoding the {@link Units#DEFAULT_UNITS} is that it
	 * would foreclose the option for unit systems that are completely detached from
	 * the SI system.<br>
	 * However, since the vast majority of units will be within the SI system, the
	 * default implementation uses the SI default units mentioned above.
	 * 
	 * @return compatible units to be checked as well
	 */
	default Set<Unit> compatibleUnits() {
		return Units.DEFAULT_UNITS;
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
	 * from the equals and hashCode methods for practical reasons:
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
		Objects.requireNonNull(units);
		Objects.requireNonNull(extraUnits);

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

		List<UnitInfo> infos = Units.processStringRangeMatches(units, allUnitInfos);

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
			@Override public boolean isConversionLinear() { return conversionInfo.canUseFactor(); }
			@Override public Map<Unit, Integer> baseUnits() { return Map.copyOf(conversionInfo.baseUnitInfos()); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
			
			@Override
			public String toString() {
				return "DynamicUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
						+ isConversionLinear() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits + "]";
			}

			@Override
			public int hashCode() {
				return Objects.hash(baseUnits(), isConversionLinear(), compatibleUnits, isBasic(), prefixes(), symbols());
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj instanceof Unit other) {
					return Objects.equals(baseUnits(), other.baseUnits()) && isConversionLinear() == other.isConversionLinear()
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
			@Override public boolean isConversionLinear() { return conversionInfo.canUseFactor(); }
			@Override public Map<Unit, Integer> baseUnits() { return conversionInfo.baseUnitInfos(); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
			
			@Override
			public String toString() {
				return "DynamicUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
						+ isConversionLinear() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits + "]";
			}

			@Override
			public int hashCode() {
				return Objects.hash(baseUnits(), isConversionLinear(), compatibleUnits, isBasic(), prefixes(), symbols());
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj instanceof Unit other) {
					return Objects.equals(baseUnits(), other.baseUnits()) && isConversionLinear() == other.isConversionLinear()
							&& Objects.equals(compatibleUnits, other.compatibleUnits()) && isBasic() == other.isBasic()
							&& Objects.equals(prefixes(), other.prefixes()) && Objects.equals(symbols(), other.symbols());
				}
				return false;
			}
		};
	}
}