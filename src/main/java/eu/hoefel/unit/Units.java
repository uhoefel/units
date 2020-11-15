package eu.hoefel.unit;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import eu.hoefel.unit.binary.BinaryUnit;
import eu.hoefel.unit.context.PhysicsContext;
import eu.hoefel.unit.level.LevelUnit;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiCommonUnit;
import eu.hoefel.unit.si.SiDerivedUnit;
import eu.hoefel.utils.Regexes;
import eu.hoefel.utils.Strings;

/**
 * Helper methods and fields for unit handling. Mainly of interest if you are
 * going to overwrite some of the default methods from {@link Unit} or
 * implementing new units.
 * 
 * @author Udo Hoefel
 */
public final class Units {

	/** Hiding any public constructor. */
	private Units() {
		throw new IllegalStateException("This is a pure utility class!");
	}

	/**
	 * This record holds the information of the units and the accompanying extra
	 * units to be used for parsing. This is useful for maps, in which the units
	 * should serve as the key, but the units available for parsing might change the
	 * meaning of the unit, i.e., it is used for {@link Units#specialUnits}.
	 * 
	 * @param units      the units, e.g. "kg^2 m s^-1"
	 * @param extraUnits the additional units to use for parsing the units
	 * 
	 * @author Udo Hoefel
	 */
	static record StringBasedUnit(String units, Set<Unit> extraUnits) {}

	/** The "Unit" of "no unit". */
	public static final Unit EMPTY_UNIT = new Unit() {
		@Override public List<String> symbols() { return List.of(""); }
		@Override public Set<UnitPrefix> prefixes() { return Set.of(); }
		@Override public boolean prefixAllowed(String symbol) { return false; }
		@Override public boolean isBasic() { return true; }
		@Override public Map<Unit, Integer> baseUnits() { return Map.of(); }
		@Override public double factor(String symbol) { return 1; }
		@Override public double convertToBaseUnits(double value) { return value; }
		@Override public double convertFromBaseUnits(double value) { return value; }
		@Override public boolean canUseFactor() { return true; }
		@Override public Set<Unit> compatibleUnits() { return EMPTY_UNITS; }
	};

	/** Represents a "prefix" with no symbol and a factor of 1. */
	public static final UnitPrefix IDENTITY_PREFIX = new UnitPrefix() {
		@Override public List<String> symbols() { return List.of(""); }
		@Override public double factor() { return 1; }
	};

	/** Map for holding unknown units, ensuring that each "type" of unknown unit exists only once. */
	private static final ConcurrentMap<String, Unit> unknownUnits = new ConcurrentHashMap<>();

	/** Map for holding composite units, ensuring that each "type" of composite unit exists only once. */
	static final ConcurrentMap<StringBasedUnit, Unit> specialUnits = new ConcurrentHashMap<>();

	/**
	 * Map for holding simplifications of specified string based units, ensuring that
	 * one does not need to redo the simplification too often, as
	 * {@link #searchSimplificationSpace(String, Set, Unit[]...)} is an expensive
	 * operation.
	 */
	private static final ConcurrentMap<StringBasedUnit, String> simplifiedUnits = new ConcurrentHashMap<>();

	/**
	 * The default units used within {@link Unit}. These include all of
	 * {@link SiBaseUnit}, {@link SiDerivedUnit}, {@link SiCommonUnit} and
	 * {@link BinaryUnit}. Might also be useful within (some) implementations of
	 * {@link Unit}.
	 */
	public static final Set<Unit> DEFAULT_UNITS;

	/**
	 * The default unit prefixes used within {@link Unit}. Might also be useful
	 * within (some) implementations of {@link Unit}.
	 */
	public static final Set<UnitPrefix> DEFAULT_PREFIXES;

	/** Empty units, mainly for use within (some) implementations of {@link Unit}. */
	public static final Set<Unit> EMPTY_UNITS = Set.of();

	/**
	 * Empty unit prefixes, mainly for use within (some) implementations of
	 * {@link Unit}.
	 */
	public static final Set<UnitPrefix> EMPTY_PREFIXES = Set.of();

	/** Pattern for a logarithmic quantity with respect to some reference value and unit. */
	static final Pattern LOG_UNIT_WITH_REF = Pattern.compile("(log|ln)\\^{0,1}(\\d*)\\(re[\\h, ](\\d+\\.?\\d*)[\\h, ](.*?)\\)\\^{0,1}(\\d*)");

	static {
		Set<Unit> defUnits = new LinkedHashSet<>();
		Collections.addAll(defUnits, SiBaseUnit.values());
		Collections.addAll(defUnits, SiDerivedUnit.values());
		Collections.addAll(defUnits, SiCommonUnit.values());
		Collections.addAll(defUnits, BinaryUnit.values());
		Collections.addAll(defUnits, LevelUnit.values());
		defUnits.add(EMPTY_UNIT);
		DEFAULT_UNITS = Collections.unmodifiableSet(defUnits);

		Set<UnitPrefix> defPrefixes = new LinkedHashSet<>();
		for (Unit refUnit : DEFAULT_UNITS) {
			defPrefixes.addAll(refUnit.prefixes());
		}
		DEFAULT_PREFIXES = Collections.unmodifiableSet(defPrefixes);
	}

	/**
	 * Compares two symbols, first by checking if uppercase letters get used (if so,
	 * the symbol is considered smaller), second by checking the symbol length and
	 * finally by lexicographical comparison. Note that there is a small, but
	 * important difference to {@link #comparatorForEquivalentSymbols(String)}, as the
	 * latter checks first if one of the two strings was given in the original
	 * units, which this one does not.
	 */
	public static final Comparator<String> COMPARATOR_FOR_UNIT_ORDERING = (symbol1, symbol2) -> {

		boolean refUnit1HasUppercase = symbol1.chars().anyMatch(c -> Character.isLetter(c) && Character.isUpperCase(c));
		boolean refUnit2HasUppercase = symbol2.chars().anyMatch(c -> Character.isLetter(c) && Character.isUpperCase(c));

		// pick the one with uppercase if possible
		if (refUnit1HasUppercase && !refUnit2HasUppercase) {
			return -1;
		} else if (!refUnit1HasUppercase && refUnit2HasUppercase) {
			return 1;
		}

		// pick the shorter one, if they differ in length
		if (symbol1.length() < symbol2.length()) {
			return -1;
		} else if (symbol1.length() > symbol2.length()) {
			return 1;
		}

		return symbol1.compareTo(symbol2);
	};

	/**
	 * Compares two symbols, first by checking if one of the two symbols was
	 * contained in the original units, then by checking if uppercase letters get
	 * used (if so, the symbol is considered smaller), second by checking the symbol
	 * length and finally by lexicographical comparison.
	 * 
	 * @param origin the original units
	 * @return the Comparator
	 */
	public static final Comparator<String> comparatorForEquivalentSymbols(String origin) {
		return (symbol1, symbol2) -> {
			boolean originalUnitsHaveSymbol1 = origin.contains(symbol1);
			boolean originalUnitsHaveSymbol2 = origin.contains(symbol2);

			if (originalUnitsHaveSymbol1 && !originalUnitsHaveSymbol2) {
				return -1;
			} else if (!originalUnitsHaveSymbol1 && originalUnitsHaveSymbol2) {
				return 1;
			}
			return COMPARATOR_FOR_UNIT_ORDERING.compare(symbol1, symbol2);
		};
	}

	/**
	 * Represents an unknown unit. Each unknown unit will be instantiated only once.
	 * 
	 * @param unit the symbol of the unknown unit
	 * @return the unknown unit, not intended to be exposed to users
	 */
	public static final Unit unknownUnit(String unit) {
		return unknownUnits.computeIfAbsent(unit, u -> new Unit() {
			@Override public List<String> symbols() { return List.of(u); }
			@Override public Set<UnitPrefix> prefixes() { return Set.of(); }
			@Override public boolean prefixAllowed(String symbol) { return false; }
			@Override public boolean isBasic() { return true; }
			@Override public Map<Unit, Integer> baseUnits() { return Map.of(this, 1); }
			@Override public double factor(String symbol) { return 1; }
			@Override public double convertToBaseUnits(double value) { return value; }
			@Override public double convertFromBaseUnits(double value) { return value; }
			@Override public boolean canUseFactor() { return true; }
			@Override public Set<Unit> compatibleUnits() { return EMPTY_UNITS; }
		});
	}

	/**
	 * This checks whether the given unit has already been used via
	 * {@link #unknownUnit(String)}. Consequently, one does not need to fill the map
	 * unnecessarily with strings, just to check if a {@link Unit} potentially is an
	 * unknown unit.
	 * 
	 * @param unit the unit to check
	 * @return true if {@link #unknownUnit(String)} has been called before with the
	 *         string specified here
	 */
	public static final boolean knownUnknownUnit(String unit) {
		return unknownUnits.containsKey(unit);
	}

	/**
	 * Prints the units in a sensible manner, i.e. it handles unknown units in a
	 * useful way.
	 * 
	 * @param units the units to print
	 * @return the listed units with unknown units being indicated. If only a single
	 *         unit is given, the square brackets are not printed around the unit's
	 *         value
	 */
	public static final String toString(Unit... units) {
		StringBuilder sb = new StringBuilder();
		for (Unit unit : units) {
			if (sb.length() == 0 && units.length > 1) {
				sb.append("[");
			} else if (units.length > 1) {
				sb.append(", ");
			}
			String symbol = unit.symbols().get(0);
			if (knownUnknownUnit(symbol) && unit.equals(unknownUnit(symbol))) {
				sb.append(symbol + " (unknown unit)");
			} else {
				sb.append(unit.symbols().get(0));
			}
		}
		if (units.length > 1) sb.append("]");
		return sb.toString();
	}

	/**
	 * Gets the symbol for the given map.
	 * 
	 * @param info the map with the Units and their exponents
	 * @return the corresponding symbol
	 */
	public static final String toSymbol(Map<Unit, Integer> info) {
		StringBuilder sb = new StringBuilder();
		for (var unitInfo : info.entrySet()) {
			if (!sb.isEmpty()) sb.append(Strings.NON_BREAKABLE_SPACE);
			sb.append(unitInfo.getKey().symbols().get(0));
			if (unitInfo.getValue() != 0) {
				sb.append("^");
				sb.append(unitInfo.getValue().toString());
			}
		}
		return sb.toString();
	}

	/**
	 * Calculates the symbol score for a concatenation of both given symbols,
	 * wherein smaller returned values indicate preferable symbols (i.e. e.g., they
	 * should have smaller (absolute) and positive exponents)
	 * 
	 * @param symbol1   the first symbol, e.g. "m"
	 * @param symbol2   the second symbol, e.g. "s"
	 * @param exponent1 the exponent corresponding to the first given symbol
	 * @param exponent2 the exponent corresponding to the second given symbol
	 * @return the corresponding symbol score, larger values corresponding to
	 *         symbols considered more complex
	 */
	private static final long calculateSymbolScore(String symbol1, String symbol2, int exponent1, int exponent2) {
		long score = Long.MIN_VALUE;
		if (symbol1.isEmpty() && symbol2.isEmpty()) return score; // no unit -> it doesn't get simpler
		if (!symbol1.isEmpty() && !symbol2.isEmpty()) score = 0L; // we prefer one unit over two

		if (!symbol1.isEmpty()) {
			double exponent = 2.0 * Math.abs(exponent1) + (Math.signum(exponent1) == -1 ? 1 : 0);
			score += (long) Math.pow(2, exponent);
		}

		if (!symbol2.isEmpty()) {
			double exponent = 2.0 * Math.abs(exponent2) + (Math.signum(exponent2) == -1 ? 1 : 0);
			score += (long) Math.pow(2, exponent);
		}

		return score;
	}

	/**
	 * Collects the info necessary for further conversion operations for the
	 * specified units. All separate units have to be separated by a space, prefixes
	 * have to be prepended (without a separating space) to their unit, exponents
	 * have to be specified by appending (without a separating space) "^" plus the
	 * value of the exponent to the unit. Example: "km^2 s^-1". If additional units,
	 * not (yet) in the standard implementations, are needed for the conversions,
	 * they can be supplied via {@code extraUnits}.
	 * 
	 * @param units      the units, e.g. "kg^2 s^-1"
	 * @param extraUnits additional units to check against
	 * @return info of the decoded units
	 */
	static final UnitInfo[] collectInfo(String units, Unit[]... extraUnits) {
		String[] unitsRaw = Regexes.ALL_SPACE.split(units.trim());
		UnitInfo[] unitInfo = new UnitInfo[unitsRaw.length];
		for (int i = 0; i < unitsRaw.length; i++) {
			String[] unitPower = Regexes.EXPONENT.split(unitsRaw[i].trim());
			unitInfo[i] = checkUnits(unitPower, extraUnits);
		}
		return unitInfo;
	}

	/**
	 * Collects the info necessary for further conversion operations for the
	 * specified unit. Compatible output to {@link #collectInfo(String, Unit[][])}.
	 * 
	 * @param unit the unit
	 * @return info for further operations, in the format: ret[0] contains info
	 *         about the prefix, the SI unit and the corresponding symbol, ret[1]
	 *         contains the corresponding exponent
	 */
	private static final UnitInfo[] collectInfo(Unit unit) {
		// we just need that prefix in all further operations anyways to just give us a
		// factor of 1, which this does
		UnitPrefix prefix = IDENTITY_PREFIX;

		return new UnitInfo[] { new UnitInfo(prefix, unit, unit.symbols().get(0), 1) };
	}

	/**
	 * Collects the information required for conversions that require
	 * non-multiplicative operations.
	 * 
	 * @param originInfos the info about the original units
	 * @param targetInfos the info about the target units
	 * @return the info containing the units, the symbol(s), and, on the
	 *         innermost level, the exponent and factor information, accessible via
	 *         "exponent" and "factor"
	 */
	private static final Map<Unit, Map<String, Map<String, Double>>> collectNonMultiplicativeOperationInfo(UnitInfo[] originInfos,
			UnitInfo[] targetInfos) {
		Map<Unit, Map<String, Map<String, Double>>> effectiveTransformations = new LinkedHashMap<>();
		for (UnitInfo info : originInfos) {
			var unitInfos = effectiveTransformations.computeIfAbsent(info.unit(), s -> new LinkedHashMap<>());
			var symbolInfos = unitInfos.computeIfAbsent(info.symbol(), s -> new LinkedHashMap<>());
			symbolInfos.compute("exponent", (k, v) -> (v == null ? 0 : v) + info.exponent());
			symbolInfos.compute("factor", (k, v) -> (v == null ? 1.0 : v) * info.prefix().factor());
		}
		for (UnitInfo info : targetInfos) {
			var unitInfos = effectiveTransformations.computeIfAbsent(info.unit(), s -> new LinkedHashMap<>());
			var symbolInfos = unitInfos.computeIfAbsent(info.symbol(), s -> new LinkedHashMap<>());
			symbolInfos.compute("exponent", (k, v) -> (v == null ? 0 : v) - info.exponent());
			symbolInfos.compute("factor", (k, v) -> (v == null ? 1.0 : v) / info.prefix().factor());
		}
		return effectiveTransformations;
	}

	/**
	 * Checks the {@code unit} versus the {@link Units#DEFAULT_UNITS} or the
	 * given {@code extraUnits} and returns the first successful match.
	 * 
	 * @param unitParts  the first index contains the unit to check, e.g. "kg" or
	 *                   "ms". Multiple units, as well as exponents, are not allowed
	 *                   here. If unitParts is of length 2, the second part contains
	 *                   information about the exponent
	 * @param extraUnits additional units to check against
	 * @return the unit info
	 */
	private static final UnitInfo checkUnits(String[] unitParts, Unit[]... extraUnits) {
		Set<Unit> units;
		Set<UnitPrefix> prefixes;
		if (extraUnits.length == 0) {
			units = DEFAULT_UNITS;
			prefixes = DEFAULT_PREFIXES;
		} else {
			units = new LinkedHashSet<>();
			prefixes = new LinkedHashSet<>();
			Unit[] flatExtraUnits = flattenUnits(extraUnits);
			for (Unit singleUnit : flatExtraUnits) {
				units.add(singleUnit);
				for (Unit compatUnit : singleUnit.compatibleUnits()) {
					units.add(compatUnit);
				}
			}

			units.add(EMPTY_UNIT);

			for (Unit refUnit : units) {
				prefixes.addAll(refUnit.prefixes());
			}
		}

		for (Unit refUnit : units) {
			UnitInfo info = identifyUnitInfo(unitParts, refUnit, prefixes);
			if (info != null) return info;
		}

		// if we get here, the unit is unknown
		int exponent = unitParts.length == 1 ? 1 : Integer.parseInt(unitParts[1]);
		return new UnitInfo(IDENTITY_PREFIX, unknownUnit(unitParts[0]), unitParts[0], exponent);
	}

	/**
	 * Checks whether the given unit is used in {@code unit} and, if successful,
	 * identifies the corresponding prefix.
	 * 
	 * @param unitParts  the first index contains the unit to check, e.g. "kg" or
	 *                   "ms". Multiple units, as well as exponents, are not allowed
	 *                   here. If unitParts is of length 2, the second part contains
	 *                   information about the exponent
	 * @param refUnit  the unit to check against
	 * @param prefixes the prefixes to check
	 * @return the unit info
	 */
	private static final UnitInfo identifyUnitInfo(String[] unitParts, Unit refUnit, Set<UnitPrefix> prefixes) {
		List<String> symbols = refUnit.symbols();
		for (String symbol : symbols) {
			if (unitParts[0].endsWith(symbol)) {
				String prefixString = unitParts[0].substring(0, unitParts[0].lastIndexOf(symbol));

				if (prefixString.isEmpty()) {
					int exponent = unitParts.length == 1 ? 1 : Integer.parseInt(unitParts[1]);
					return new UnitInfo(IDENTITY_PREFIX, refUnit, symbol, exponent);
				}

				for (UnitPrefix prefix : prefixes) {
					for (String prefixSymbol : prefix.symbols()) {
						if (refUnit.prefixAllowed(symbol) && prefixString.equals(prefixSymbol)) {
							int exponent = unitParts.length == 1 ? 1 : Integer.parseInt(unitParts[1]);
							return new UnitInfo(prefix, refUnit, symbol, exponent);
						}
					}
				}
			}
		}

		// at the one place where we use this method an Optional<UnitInfo> does not
		// really help with the code, so we leave null here
		return null;
	}

	/**
	 * Flattens the given units.
	 * 
	 * @param units the units
	 * @return the flattened units
	 */
	public static final Unit[] flattenUnits(Unit[]... units) {
		int size = 0;
		for (int i = 0; i < units.length; i++) {
			size += units[i].length;
		}

		Unit[] flat = new Unit[size];
		int index = 0;
		for (int i = 0; i < units.length; i++) {
			int numNew = units[i].length;
			System.arraycopy(units[i], 0, flat, index, numNew);
			index += numNew;
		}
		return flat;
	}

	/**
	 * Gets the conversion factor, if possible, from the origin units to the target
	 * units. All separate units have to be separated by a space, prefixes have to
	 * be prepended (without a separating space) to their unit, exponents have to be
	 * specified by appending (without a separating space) "^" plus the value of the
	 * exponent to the unit. Example: "km^2 s^-1". If additional units, not (yet) in
	 * the standard implementations, are needed for the conversions, they can be
	 * supplied via {@code extraUnits}.
	 * 
	 * @param origin     the original units
	 * @param target     the target units
	 * @param extraUnits the additional units to use for the conversion
	 * @return the conversion factor
	 */
	public static final double factor(String origin, String target, Unit[]... extraUnits) {
		if (origin == null || target == null || origin.equals(target)) {
			return 1;
		}
		UnitInfo[] originInfos = collectInfo(origin, extraUnits);
		UnitInfo[] targetInfos = collectInfo(target, extraUnits);

		return internalFactor(origin, target, originInfos, targetInfos);
	}

	/**
	 * Gets the conversion factor, if possible, from the origin unit to the target
	 * unit.
	 * 
	 * @param origin the original unit
	 * @param target the target unit
	 * @return the conversion factor
	 * @throws IllegalArgumentException if the conversion is not possible for the
	 *                                  given units
	 */
	public static final double factor(Unit origin, Unit target) {
		if (origin == target) {
			return 1;
		} else {
			if (origin.baseUnits().equals(target.baseUnits())) {
				if (origin.canUseFactor() && target.canUseFactor()) {
					return origin.factor(origin.symbols().get(0)) / target.factor(target.symbols().get(0));
				} else {
					throw new IllegalArgumentException(
							("Conversion from \"%s\" to \"%s\" contains non-multiplicative operations, "
									+ "hence a conversion factor cannot be used here.").formatted(toString(origin),
											toString(target)));			
				}
			} else {
				throw new IllegalArgumentException(
						"Cannot convert from %s (units: %s) to %s (units: %s)".formatted(toString(origin),
								toSymbol(origin.baseUnits()), toString(target), toSymbol(target.baseUnits())));
			}
		}
	}

	/**
	 * Converts a value, if possible, from the origin units to the target units. All
	 * separate units have to be separated by a space, prefixes have to be prepended
	 * (without a separating space) to their unit, exponents have to be specified by
	 * appending (without a separating space) "^" plus the value of the exponent to
	 * the unit. Example: "km^2 s^-1". If additional units, not (yet) in the
	 * standard implementations, are needed for the conversions, they can be
	 * supplied via {@code extraUnits}.
	 * 
	 * @param value      the value in the original units
	 * @param origin     the original units
	 * @param target     the target units
	 * @param extraUnits the additional units to use for the conversion
	 * @return the converted value
	 */
	public static final double convert(double value, String origin, String target, Unit[]... extraUnits) {
		if (origin == null || target == null || origin.equals(target)) {
			return value;
		}
		UnitInfo[] originInfos = collectInfo(origin, extraUnits);
		UnitInfo[] targetInfos = collectInfo(target, extraUnits);
		return internalConversionOperation(originInfos, targetInfos).applyAsDouble(value);
	}

	/**
	 * Converts a value, if possible, from the origin unit to the target unit.
	 * 
	 * @param value  the value in the original unit
	 * @param origin the original unit
	 * @param target the target unit
	 * @return the converted value
	 * @throws IllegalArgumentException if the conversion cannot be performed for
	 *                                  the given units
	 */
	public static final double convert(double value, Unit origin, Unit target) {
		if (origin == target) {
			return value;
		} else {
			if (origin.baseUnits().equals(target.baseUnits())) {
				if (origin.canUseFactor()) {
					value *= origin.factor(origin.symbols().get(0));
				} else {
					value = origin.convertToBaseUnits(value);
				}
				if (target.canUseFactor()) {
					value /= target.factor(target.symbols().get(0));
				} else {
					value = target.convertFromBaseUnits(value);
				}
			} else {
				throw new IllegalArgumentException(
						"Cannot convert from %s (units: %s) to %s (units: %s)".formatted(toString(origin),
								toSymbol(origin.baseUnits()), toString(target), toSymbol(target.baseUnits())));
			}
		}
		return value;
	}

	/**
	 * Checks whether the original units can be converted to the target units. All
	 * separate units have to be separated by a space, prefixes have to be prepended
	 * (without a separating space) to their unit, exponents have to be specified by
	 * appending (without a separating space) "^" plus the value of the exponent to
	 * the unit. Example: "km^2 s^-1". If additional units, not (yet) in the
	 * standard implementations, are needed for the conversions, they can be
	 * supplied via {@code extraUnits}.
	 * 
	 * @param origin     the original units
	 * @param target     the target units
	 * @param extraUnits the additional units to use for the conversion
	 * @return true if the original units can be converted to the target units
	 */
	public static final boolean convertible(String origin, String target, Unit[]... extraUnits) {
		if (origin == null || target == null) {
			return false;
		} else if (origin.equals(target)) {
			return true;
		}
		UnitInfo[] originInfos = collectInfo(origin, extraUnits);
		UnitInfo[] targetInfos = collectInfo(target, extraUnits);
		return internalConversionCheck(originInfos, targetInfos);
	}

	/**
	 * Checks whether the original unit can be converted to the target units. All
	 * separate units (in the String format) have to be separated by a space,
	 * prefixes have to be prepended (without a separating space) to their unit,
	 * exponents have to be specified by appending (without a separating space) "^"
	 * plus the value of the exponent to the unit. Example: "km^2 s^-1".
	 * 
	 * @param origin     the original units
	 * @param target     the target unit
	 * @return true if the original units can be converted to the target units
	 */
	public static final boolean convertible(Unit origin, Unit target) {
		if (origin == null || target == null) {
			return false;
		}
		UnitInfo[] originInfos = collectInfo(origin);
		UnitInfo[] targetInfos = collectInfo(target);
		return internalConversionCheck(originInfos, targetInfos);
	}

	/**
	 * Checks whether {@code units} are valid units.
	 * 
	 * @param units      the units to check
	 * @param extraUnits the additional units to use for the conversion
	 * @return true if {@code units} is valid
	 */
	public static final boolean isValid(String units, Unit[]... extraUnits) {
		String[] unitsRaw = Regexes.ALL_SPACE.split(units.trim());
		for (String unitsRawPart : unitsRaw) {
			String[] unitPower = Regexes.EXPONENT.split(unitsRawPart.trim());
			UnitInfo unitInfo = checkUnits(unitPower, extraUnits);

			// we check if i) the unit is in the map of unknown units and ii) if it is,
			// whether the resolved unit has the same memory address as the unknown unit.
			// This is necessary, as we might have a request to, e.g. "bla", which cannot be
			// parsed as a unit, so is added to the list of unknown units, but is
			// subsequently used with extra units that do allow to parse "bla", which will
			// lead to the resolved "bla" now pointing to the proper unit and hence fail the
			// memory address check.
			if (unknownUnits.containsKey(unitPower[0]) && unknownUnits.get(unitPower[0]) == unitInfo.unit()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Calculates the conversion factor.
	 * 
	 * @param origin      the original units
	 * @param target      the target units
	 * @param originInfos the infos about the origin units, in the format: infos[0]
	 *                    contains info about the SIprefix, the SI unit and the
	 *                    corresponding symbol, info[1] contains the corresponding
	 *                    exponent
	 * @param targetInfos the infos about the target units in the same format as the
	 *                    {@code originInfos}
	 * @return the conversion factor
	 * @throws IllegalArgumentException if the conversion would require
	 *                                  non-multiplicative operations
	 */
	private static final double internalFactor(Object origin, Object target, UnitInfo[] originInfos, UnitInfo[] targetInfos) {
		BaseConversionInfo cleanOriginInfos = cleanup(originInfos);
		BaseConversionInfo cleanTargetInfos = cleanup(targetInfos);

		double factor = calculateFactor(cleanOriginInfos, cleanTargetInfos);

		// check if we can use a factor
		if (cleanOriginInfos.canUseFactor() && cleanTargetInfos.canUseFactor()) {
			return factor;
		}

		throw new IllegalArgumentException(("Conversion from \"%s\" to \"%s\" contains non-multiplicative "
				+ "operations, hence a conversion factor cannot be used here.").formatted(origin, target));
	}

	/**
	 * Calculates the conversion operation.
	 * 
	 * @param originInfos the infos about the origin units
	 * @param targetInfos the infos about the target units
	 * @return the conversion operation
	 */
	static final DoubleUnaryOperator internalConversionOperation(UnitInfo[] originInfos, UnitInfo[] targetInfos) {
		BaseConversionInfo cleanOriginInfos = cleanup(originInfos);
		BaseConversionInfo cleanTargetInfos = cleanup(targetInfos);

		double factor = calculateFactor(cleanOriginInfos, cleanTargetInfos);

		// check if we can use a factor
		if (cleanOriginInfos.canUseFactor() && cleanTargetInfos.canUseFactor()) {
			return v -> factor * v;
		}

		// so we have celsius or similar shift conversions in here
		var nonMultiplicativeOperationInfo = collectNonMultiplicativeOperationInfo(originInfos, targetInfos);

		return v -> {
			for (var effectiveTransformation : nonMultiplicativeOperationInfo.entrySet()) {
				for (var symbolInfo : effectiveTransformation.getValue().entrySet()) {
					v *= symbolInfo.getValue().get("factor");
					double exponent = symbolInfo.getValue().get("exponent");
					if (exponent > 0) {
						Unit u = effectiveTransformation.getKey();
						if (u.isBasic() && u.canUseFactor()) {
							v *= u.factor(symbolInfo.getKey());
						} else if (!u.isBasic()) {
							v = Math.pow(u.convertToBaseUnits(v), exponent);
						}
					} else if (exponent < 0) {
						Unit u = effectiveTransformation.getKey();
						if (u.isBasic() && u.canUseFactor()) {
							v /= u.factor(symbolInfo.getKey());
						} else if (!u.isBasic()) {
							v = Math.pow(u.convertFromBaseUnits(v), Math.abs(exponent));
						}
					}
				}
			}
			return v;
		};
	}

	/**
	 * Checks whether the units are compatible.
	 * 
	 * @param originInfos the info with respect to the original units
	 * @param targetInfos the info with respect to the target units
	 * @return true if convertible
	 */
	private static final boolean internalConversionCheck(UnitInfo[] originInfos, UnitInfo[] targetInfos) {
		Map<Unit, Integer> cleanOriginInfos = cleanup(originInfos).baseUnitInfos();
		Map<Unit, Integer> cleanTargetInfos = cleanup(targetInfos).baseUnitInfos();
		return cleanOriginInfos.equals(cleanTargetInfos);
	}

	/**
	 * Gets the conversion factor from original units to the target units. Only
	 * usable if all conversions contain multiplicative operations only.
	 * 
	 * @param cleanOriginInfos the info required for conversion into the base units
	 *                         for the original units
	 * @param cleanTargetInfos the info required for conversion into the base units
	 *                         for the target units
	 * @return the conversion factor
	 */
	private static final double calculateFactor(BaseConversionInfo cleanOriginInfos, BaseConversionInfo cleanTargetInfos) {
		sanityCheck(cleanOriginInfos.baseUnitInfos(), cleanTargetInfos.baseUnitInfos());
		return cleanOriginInfos.factor() / cleanTargetInfos.factor();
	}

	/**
	 * Checks whether the original units (or rather their representation in base
	 * units) is compatible to the target units (again, via their representation in
	 * base units).
	 * 
	 * @param origin the original units represented in base units
	 * @param target the target units represented in base units
	 * @throws IllegalArgumentException if the conversion is not possible
	 */
	private static final void sanityCheck(Map<Unit, Integer> origin, Map<Unit, Integer> target) {
		for (var originBaseUnit : origin.entrySet()) {
			for (var targetBaseUnit : target.entrySet()) {
				if (!target.containsKey(originBaseUnit.getKey())) {
					throw new IllegalArgumentException(
							("Target units do not include %s, although the original units contain it. "
									+ "Explicit comparison: %s (original) vs. %s (target).").formatted(
											toString(originBaseUnit.getKey()),
											toString(origin.keySet().toArray(Unit[]::new)),
											toString(target.keySet().toArray(Unit[]::new))));			
				} else if (!origin.containsKey(targetBaseUnit.getKey())) {
					throw new IllegalArgumentException(
							("Original units do not include %s, although the target units contain it. "
									+ "Explicit comparison: %s (original) vs. %s (target).").formatted(
											toString(targetBaseUnit.getKey()),
											toString(origin.keySet().toArray(Unit[]::new)),
											toString(target.keySet().toArray(Unit[]::new))));
				} else if (originBaseUnit.getValue().intValue() != target.get(originBaseUnit.getKey()).intValue()) {
					throw new IllegalArgumentException(("Target units do not include the correct exponent for %s. "
							+ "In the original units, the exponent is %s, while it is %s in the target units. "
							+ "Explicit comparison: %s (original) vs. %s (target)").formatted(
									toString(originBaseUnit.getKey()), originBaseUnit.getValue(),
									target.get(originBaseUnit.getKey()), compositeUnitToString(origin),
									compositeUnitToString(target)));
				}
			}
		}
	}

	/**
	 * Helper method to print units with their exponents in a sensible manner, i.e.
	 * it handles unknown units in a useful manner.
	 * 
	 * @param info the map with the units and their exponents
	 * @return the string with sensible unit and exponent info
	 */
	private static final String compositeUnitToString(Map<Unit, Integer> info) {
		StringBuilder sb = new StringBuilder("[");
		for (var unitInfo : info.entrySet()) {
			sb.append("{%s,%s}".formatted(toString(unitInfo.getKey()), unitInfo.getValue().toString()));
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Cleans the infos and merges them.
	 * 
	 * @param infos the unit infos
	 * @return the information required for conversions to the base units
	 */
	static final BaseConversionInfo cleanup(UnitInfo[] infos) {
		double factor = 1;
		Map<Unit, Integer> baseUnitInfos = new LinkedHashMap<>();
		boolean canUseFactor = true;
		for (UnitInfo info : infos) {
			Map<Unit, Integer> map = info.unit().baseUnits();
			boolean factorCalculated = false;
			for (Entry<Unit, Integer> correspondingBase : map.entrySet()) {
				// the exponent contributed by previous units
				int exponent = baseUnitInfos.computeIfAbsent(correspondingBase.getKey(), s -> 0);
				baseUnitInfos.put(correspondingBase.getKey(), exponent + correspondingBase.getValue() * info.exponent());
				if (!factorCalculated && info.unit().prefixAllowed(info.symbol())) {
					factor *= Math.pow(info.prefix().factor(), info.exponent());
					factor *= Math.pow(info.unit().factor(info.symbol()), info.exponent());
					factorCalculated = true;
				} else if (!factorCalculated) {
					factor *= Math.pow(info.unit().factor(info.symbol()), info.exponent());
					factorCalculated = true;
				}

			}
			if (canUseFactor) canUseFactor = info.unit().canUseFactor();
		}

		// remove ^0
		Map<Unit, Integer> trimmedBaseUnitInfos = new LinkedHashMap<>(baseUnitInfos);
		for (Entry<Unit, Integer> baseUnitInfo : baseUnitInfos.entrySet()) {
			if (baseUnitInfo.getValue() == 0) {
				trimmedBaseUnitInfos.remove(baseUnitInfo.getKey());
			}
		}

		if (!canUseFactor) factor = Double.NaN;

		return new BaseConversionInfo(factor, Collections.unmodifiableMap(trimmedBaseUnitInfos), canUseFactor);
	}

	/**
	 * Checks whether the origin and target units are equivalent (that is, the
	 * origin units are convertible to the target units, and that the specified
	 * amount of the origin units corresponds to the same amount of the target
	 * units).
	 * 
	 * @param value      the value which will be used to test for equivalence
	 * @param origin     the original units
	 * @param target     the target units
	 * @param extraUnits the additional units to use for the conversion
	 * @return true if the units are equivalent
	 */
	public static final boolean equivalent(double value, String origin, String target, Unit[]... extraUnits) {
		Unit o = Unit.of(origin, extraUnits);
		Unit t = Unit.of(target, extraUnits);
		return equivalent(value, o, t) && value == convert(value, o, t);
	}

	/**
	 * Checks whether the origin and target unit are equivalent (that is, the origin
	 * unit is convertible to the target unit, and that 1 amount of the origin unit
	 * corresponds to 1 amount of the target unit).
	 * 
	 * @param value  the value which will be used to test for equivalence
	 * @param origin the original unit
	 * @param target the target unit
	 * @return true if the units are equivalent
	 */
	public static final boolean equivalent(double value, Unit origin, Unit target) {
		return convertible(origin, target) && value == convert(value, origin, target);
	}

	/**
	 * Simplifies the given units. Note that this is a costly operation (especially
	 * if specific extra units are given), even though some optimizations have been
	 * done. Note further that multiple unit combinations may be legit
	 * simplifications.
	 * <p>
	 * For example:<br>
	 * {@code "kg^3 m^4 s^-6 A^-1"} is equivalent to both {@code "N^2 Wb"} and
	 * {@code "J^2 T"}.
	 * <p>
	 * In this case, the simplification that is picked follows these rules:
	 * <ol>
	 * <li>Pick only one unit, if possible.
	 * <li>Pick the units with lower (absolute) exponents and prefer positive over
	 * negative exponents if their magnitude is the same.
	 * <li>Pick the units contained in the original units, if possible.
	 * <li>Pick the units with uppercases, if possible.
	 * <li>Pick the shorter units, if possible.
	 * <li>Pick the lexicographically smaller units.
	 * </ol>
	 * Tries to combine at most 2 units, with exponents ranging from -3 to 3.
	 * 
	 * @param units      the units to simplify
	 * @param extraUnits the units that may be used for simplification. If none are
	 *                   given the units given by {@link Units#DEFAULT_UNITS} are
	 *                   used.
	 * @return the simplified symbol string, or the input string, if no
	 *         simplification was found
	 */
	public static final String simplify(String units, Unit[]... extraUnits) {
		Set<Unit> refUnits = new HashSet<>();
		if (extraUnits.length == 0) {
			refUnits = DEFAULT_UNITS;
		} else {
			Unit[] flatExtraUnits = Stream.of(extraUnits).flatMap(Stream::of).toArray(Unit[]::new);

			for (Unit singleUnit : flatExtraUnits) {
				refUnits.add(singleUnit);
				for (Unit compatUnit : singleUnit.compatibleUnits()) {
					refUnits.add(compatUnit);
				}
			}

			// for code simplification purposes we treat the single reference unit checks as
			// checks against a reference unit plus an empty unit
			refUnits.add(EMPTY_UNIT);
		}

		// TODO I think in Java 16 one can remove this, just to make refUnits effectively final
		var finalRefUnits = Set.copyOf(refUnits);

		return simplifiedUnits.computeIfAbsent(new StringBasedUnit(units, refUnits), k -> {
			var compatibleSymbols = searchSimplificationSpace(units, finalRefUnits, extraUnits);

			if (!compatibleSymbols.isEmpty()) {
				// so we might have bunch of equivalent symbols, like e.g. "N^2 Wb" and "J^2 T".
				// The special comparator used in the set we get takes care of this and
				// guarantees reproducible output
				NavigableSet<String> equivalentSymbols = compatibleSymbols.get(compatibleSymbols.firstKey());
				return equivalentSymbols.first();
			}

			// nothing found -> return input
			return units;
		});
	}

	/**
	 * Searches though the given reference units and tries to find simplifications.
	 * 
	 * @param units      the units to simplify
	 * @param refUnits   the reference units to use for potential simplifications
	 * @param extraUnits the additional units needed for checking the equivalence
	 * @return the found, simplified symbols
	 */
	private static final NavigableMap<Long, NavigableSet<String>> searchSimplificationSpace(String units, Set<Unit> refUnits, Unit[]... extraUnits) {
		Comparator<String> comparator = comparatorForEquivalentSymbols(units);
		NavigableMap<Long, NavigableSet<String>> compatibleSymbols = new TreeMap<>();
		Set<Unit> alreadyProcessedUnits = new HashSet<>();
		Unit[][] allUnitReferences = new Unit[0][];
		if (extraUnits.length > 0) {
			allUnitReferences = new Unit[extraUnits.length + 1][];
			System.arraycopy(extraUnits, 0, allUnitReferences, 0, extraUnits.length);
			allUnitReferences[allUnitReferences.length - 1] = refUnits.stream().toArray(Unit[]::new);
		}

		// try a combination of two reference units, each with powers of -3,...,3
		int[] exponents = { 1, 2, -1, -2, 3, -3 }; // try to sort according to typical occurrences
		for (int exponent1 : exponents) {
			for (int exponent2 : exponents) {
				alreadyProcessedUnits.clear();
				for (Unit refUnit1 : refUnits) {
					for (Unit refUnit2 : refUnits) {

						// already processed (albeit in the other order, but that is irrelevant
						if (alreadyProcessedUnits.contains(refUnit2)) continue;

						// both ref units are the same -> skip (except for empty unit)
						if (refUnit1.equals(refUnit2) && !refUnit1.equals(EMPTY_UNIT)) continue;

						// if we have mixed all lowercase and partially uppercase symbols we put the partial uppercase unit first
						String refUnit1Symbol = refUnit1.symbols().get(0);
						String refUnit2Symbol = refUnit2.symbols().get(0);

						String symbol = "";
						if (COMPARATOR_FOR_UNIT_ORDERING.compare(refUnit1Symbol, refUnit2Symbol) > 0) {
							if (!refUnit2Symbol.isEmpty()) symbol += refUnit2Symbol + (exponent2 == 1 ? "" : "^" + exponent2);
							symbol += Strings.NON_BREAKABLE_SPACE;
							if (!refUnit1Symbol.isEmpty()) symbol += refUnit1Symbol + (exponent1 == 1 ? "" : "^" + exponent1);
						} else {
							if (!refUnit1Symbol.isEmpty()) symbol += refUnit1Symbol + (exponent1 == 1 ? "" : "^" + exponent1);
							symbol += Strings.NON_BREAKABLE_SPACE;
							if (!refUnit2Symbol.isEmpty()) symbol += refUnit2Symbol + (exponent2 == 1 ? "" : "^" + exponent2);
						}
						symbol = symbol.trim();

						if (equivalent(1, units, symbol, allUnitReferences)) {
							Long score = calculateSymbolScore(refUnit1Symbol, refUnit2Symbol, exponent1, exponent2);
							compatibleSymbols.computeIfAbsent(score, s -> new TreeSet<>(comparator)).add(symbol);
						}
					}
					alreadyProcessedUnits.add(refUnit1);
				}
			}
		}
		return compatibleSymbols;
	}

	/**
	 * Gets the names of the units, within the specified context, whose symbols
	 * match (for the specific meaning of "match" here see {@link UnitContextMatch})
	 * the given units. Note that in contrast to
	 * {@link #inContext(Unit, UnitContextMatch, UnitContext...)} only one context
	 * can be given. If you want to check for multiple contexts use
	 * {@link Unit#of(String, Unit[]...)} on your units and use
	 * {@link #inContext(Unit, UnitContextMatch, UnitContext...)}.
	 * <p>
	 * Example usage:<br>
	 * <code>Units.inContext("kg", UnitContextMatch.COMPATIBLE, PhysicsContext.GENERAL);</code>
	 * 
	 * 
	 * @param units      the units whose interpretations in the given context is
	 *                   wanted
	 * @param match      the value that determines how strict the match between the
	 *                   given units and the available named symbols should be
	 * @param context    the context in which to view the given units
	 * @param extraUnits the additional units needed for checking the equivalence
	 * @return the (lexicographically) ordered names of units that match the given
	 *         unit within the specified context
	 */
	public static final NavigableSet<String> inContext(String units, UnitContextMatch match, UnitContext context, Unit[]... extraUnits) {
		NavigableSet<String> ret = new TreeSet<>();
		for (var type : context.namedSymbols().entrySet()) {
			for (String unit : type.getValue()) {
				if (match.matches(units, unit, extraUnits)) {
					ret.add(type.getKey());
				}
			}
		}
		return ret;
	}

	/**
	 * Gets the names of the units, within the specified context, whose symbols
	 * match (for the specific meaning of "match" here see {@link UnitContextMatch})
	 * the given unit.
	 * <p>
	 * Example usage:<br>
	 * <code>Units.inContext("kg", UnitContextMatch.COMPATIBLE, PhysicsContext.values());</code>
	 * 
	 * @param unit     the unit whose interpretations in the given context is wanted
	 * @param match    the value that determines how strict the match between the
	 *                 given units and the available named symbols should be
	 * @param contexts the contexts in which to view the given units
	 * @return the (lexicographically) ordered names of units that match the given
	 *         unit within the specified context
	 */
	public static final NavigableSet<String> inContext(Unit unit, UnitContextMatch match, UnitContext... contexts) {
		NavigableSet<String> ret = new TreeSet<>();
		// One could have a more performant implementation, but that seems like code
		// duplication not worth the effort, at least at the moment
		UnitContext[] contextsToUse = contexts == null || contexts.length == 0 ? PhysicsContext.values() : contexts;
		for (String symbol : unit.symbols()) {
			for (UnitContext context : contextsToUse) {
				ret.addAll(inContext(symbol, match, context));
			}
		}
		return ret;
	}

	/**
	 * Checks whether the first unit is proportional to the second unit. The check
	 * is done via checking whether all base units of the first unit are present,
	 * with the same exponents, in the base units of the second unit.
	 * 
	 * @param first      the first unit
	 * @param second     the second unit
	 * @param extraUnits the additional units needed for checking the
	 *                   proportionality
	 * @return true if the first unit is proportional to the second unit
	 */
	public static final boolean proportional(String first, String second, Unit[]... extraUnits) {
		return proportional(Unit.of(first, extraUnits), Unit.of(second, extraUnits));
	}

	/**
	 * Checks whether the first unit is proportional to the second unit. The check
	 * is done via checking whether all base units of the first unit are present,
	 * with the same exponents, in the base units of the second unit.
	 * 
	 * @param first  the first unit
	 * @param second the second unit
	 * @return true if the first unit is proportional to the second unit
	 */
	public static final boolean proportional(Unit first, Unit second) {
		if (!first.canUseFactor() || !second.canUseFactor()) return false;
		for (var o : first.baseUnits().entrySet()) {
			if (o.getValue() != second.baseUnits().getOrDefault(o.getKey(), 0)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds the specified unit to the internal caching based on the first symbol and
	 * the compatible units of the given unit if not already cached. This alleviates
	 * the need to implement equals and hashCode.
	 * 
	 * @param symbol          the symbol. Should match the first symbol in the list
	 *                        of symbols of the unit that may be created by the
	 *                        unitSupplier, if necessary.
	 * @param compatibleUnits the compatible units. Should match the compatible
	 *                        units of the unit that may be created by the
	 *                        unitSupplier, if necessary.
	 * @param unitSupplier    the supplier to create the unit, if necessary
	 * @return the unit as in the internal caching map
	 */
	public static final Unit computeSpecialUnitIfAbsent(String symbol, Set<Unit> compatibleUnits, Supplier<Unit> unitSupplier) {
		return specialUnits.computeIfAbsent(new StringBasedUnit(symbol, compatibleUnits), s -> unitSupplier.get());
	}
}
