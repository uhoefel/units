package eu.hoefel.unit.level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitInfo;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.Units.StringRange;
import eu.hoefel.unit.binary.BinaryPrefix;
import eu.hoefel.unit.si.SiPrefix;

/**
 * Level units are logarithmic measures. Note that to get the level units in
 * reference to some quantity you need to use
 * {@link #inReferenceTo(double, Unit)} or
 * {@link #inReferenceTo(double, Unit, LevelUnitReferenceType)}, as the
 * reference can vary. The enum values directly defined in here hence have the
 * {@link #NEPER Np} as their base unit, and only allow conversions between
 * them, while taking them in reference to other units allows conversion to the
 * specified reference unit.
 * 
 * @author Udo Hoefel
 * @see <a href=
 *      "https://www.iso.org/obp/ui/#iso:std:iso:80000:-1:ed-1:v1:en">ISO
 *      80000-1 § Annex C</a>
 */
public enum LevelUnit implements Unit {

	/**
	 * The bel (denoted "B") is named after Alexander Graham Bell, the inventor of
	 * the telephone. Often the {@link SiPrefix#DECI d}B is used in practice, e.g.
	 * in electronics, acoustics and for signal-to-noise quantification.
	 */
	BEL(Math.log(10) / 2, "B"),

	/**
	 * The neper (denoted "Np") is the only level unit considered coherent to the SI
	 * system. Its name stems from John Napier, who invented logarithms (which are
	 * essential to level units). It is defined in the international standard
	 * <a href= "https://www.iso.org/obp/ui/#iso:std:iso:80000:-1:ed-1:v1:en">ISO
	 * 80000-1 § Annex C</a>.
	 */
	NEPER(1, "Np");

	/** Pattern for a logarithmic quantity with respect to some reference value and unit. */
	static final Pattern LOG_UNIT_WITH_REF = Pattern.compile("(log|ln)\\^{0,1}(\\d*)\\(re[\\h, ](\\d+\\.?\\d*)[\\h,\u202F](.*?)\\)\\^{0,1}(\\d*)");

	private static final Logger logger = Logger.getLogger(LevelUnit.class.getName());

	/** The path where this project is hosted. */
	private static final String PROJECT_HOME_URL = "https://github.com/uhoefel/units";

	/** The level base units to which the level unit corresponds. */
	private static final Map<Unit, Integer> BASE_UNITS = Map.of(NEPER, 1);

	/** The symbols representing the level unit. */
	private final List<String> symbols;

	/** The factor to convert from the level unit to the base units. */
	private final double factor;

	/** The default prefixes. */
	private static final Set<UnitPrefix> DEFAULT_PREFIXES;

	static {
		Set<UnitPrefix> defPrefixes = new HashSet<>();
		Collections.addAll(defPrefixes, SiPrefix.values());
		Collections.addAll(defPrefixes, BinaryPrefix.values());
		DEFAULT_PREFIXES = Set.copyOf(defPrefixes);
	}

	/**
	 * Constructor for level units.
	 * 
	 * @param factor  the conversion factor to the base level unit
	 * @param symbols the symbols representing the level unit
	 */
	private LevelUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return true; }
	@Override public boolean canUseFactor() { return true; }
	@Override public double factor(String symbol) { return factor; }
	@Override public Map<Unit, Integer> baseUnits() { return BASE_UNITS; }
	@Override public double convertToBaseUnits(double value) { return factor * value; }
	@Override public double convertFromBaseUnits(double value) { return value / factor; }
	@Override public Set<UnitPrefix> prefixes() { return DEFAULT_PREFIXES; }
	@Override public boolean isBasic() { return this == NEPER; }
	
	/**
	 * Creates a level unit with respect to the given references. Note that its base
	 * units are the base units of the given reference unit, i.e., conversions to
	 * base unit transform a, e.g., quantity of <code>0.3×log(re 1V)</code> into
	 * <code>1.41V</code> (corresponding to a gain of 0.3B, hence 3dB). Tries to
	 * automatically determine the correct {@link LevelUnitReferenceType} by
	 * checking convertability (and subsequently proportionality if no convertible
	 * unit was found) to known {@link LevelUnitReferenceType#POWER power} and
	 * {@link LevelUnitReferenceType#ROOT_POWER root power} units.
	 * 
	 * @param refValue the reference value in refUnits
	 * @param refUnit  the reference unit, e.g. "mV"
	 * @return the level unit with respect to the given reference
	 * @throws IllegalArgumentException if no power or root power unit could be
	 *                                  found
	 */
	public final Unit inReferenceTo(double refValue, Unit refUnit) {
		LevelUnitReferenceType t = LevelUnitReferenceType.findMatchingType(refUnit)
				.orElseThrow(() -> new IllegalArgumentException(("Unable to determine whether the given reference unit "
						+ "(%s) belongs to any of the known reference unit types (%s). "
						+ "Please specify the type explicitly via inReferenceTo(double,Unit,ReferenceUnitType).")
								.formatted(Units.toString(refUnit), Set.of(LevelUnitReferenceType.values()))));
		return inReferenceTo(refValue, refUnit, t);
	}

	/**
	 * Creates a level unit with respect to the given references. Note that its base
	 * units are the base units of the given reference unit, i.e., conversions to
	 * base unit transform a, e.g., quantity of <code>0.3×log(re 1V)</code> into
	 * <code>1.41V</code> (corresponding to a gain of 0.3B, hence 3dB).
	 * 
	 * @param refValue the reference value in refUnits
	 * @param refUnit  the reference unit, e.g. "mV"
	 * @param type     the reference unit type
	 * @return the level unit with respect to the given reference
	 */
	public final Unit inReferenceTo(double refValue, Unit refUnit, LevelUnitReferenceType type) {
		if (LevelUnitReferenceType.findMatchingType(refUnit).stream().filter(r -> r == type).count() == 0) {
			logger.warning(() -> ("For the given reference unit (%s) no matching reference unit type "
					+ "could be found that matched the given reference unit type (%s). "
					+ "Continuing with the given reference unit type, but the resulting symbol "
					+ "will not be uniquely resolvable (the returned unit will be correct, though). "
					+ "Please consider reporting the reference unit and its type " + "to %s")
							.formatted(Units.toString(refUnit), type, PROJECT_HOME_URL));
		}
		
		DoubleUnaryOperator fromBase = switch (this) {
			case BEL -> switch (type) {
				case POWER -> (value -> Math.log10(value / refValue));
				case ROOT_POWER -> (value -> 2 * Math.log10(value / refValue));
			};
			case NEPER -> switch (type) {
				case POWER -> (value -> 0.5 * Math.log(value / refValue));
				case ROOT_POWER -> (value -> Math.log(value / refValue));
			};
		};

		DoubleUnaryOperator toBase = switch (this) {
			case BEL -> switch (type) {
				case POWER -> (value -> refValue * Math.pow(10, value));
				case ROOT_POWER -> (value -> refValue * Math.pow(10, value / 2));
			};
			case NEPER -> switch (type) {
				case POWER -> (value -> refValue * Math.exp(2 * value));
				case ROOT_POWER -> (value -> refValue * Math.exp(value));
			};
		};

		String symbol = switch (this) {
			case BEL -> "log";
			case NEPER -> "ln";
		};

		String refValueString = String.format(Locale.ENGLISH, "%.16f", refValue);
		while (refValueString.endsWith("0") || refValueString.endsWith(".")) {
			refValueString = refValueString.substring(0, refValueString.length() - 1);
		}

		symbol += String.format(Locale.ENGLISH, "(re %s\u202F%s)", refValueString, refUnit.symbols().get(0));
		var dynamicSymbol = List.of(symbol);

		// we can not directly use Set.of as this throws an error if an element occurs
		// multiple times
		Set<Unit> set = new HashSet<>();
		Collections.addAll(set, Units.flattenUnits(LevelUnit.values(), refUnit.compatibleUnits().stream().toArray(Unit[]::new)));
		Set<Unit> compatibleUnits = Set.copyOf(set);
		
		return new Unit() {
			@Override public List<String> symbols() { return dynamicSymbol; }
			@Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
			@Override public boolean prefixAllowed(String symbol) { return false; }
			@Override public boolean isBasic() { return false; }
			@Override public double factor(String symbol) { return Double.NaN; }
			@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
			@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
			@Override public boolean canUseFactor() { return false; }
			@Override public Map<Unit, Integer> baseUnits() { return refUnit.baseUnits(); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
			
			@Override
			public String toString() {
				return "LevelUnitWithReference[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
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

	@Override
	public BiFunction<String, Unit[][], NavigableMap<StringRange, UnitInfo>> parser() {
		return (s, u) -> {
			Objects.requireNonNull(s);
			Objects.requireNonNull(u);

			var map = parseLogarithmicUnits(s.trim(), u);
			map.putAll(Units.collectInfo(s, new Unit[] { this }));
			return map;
		};
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
	 * @throws IllegalArgumentException if the logarithmic unit is malformed
	 */
	private static NavigableMap<StringRange, UnitInfo> parseLogarithmicUnits(String units, Unit[]... extraUnits) {
		Matcher m = LOG_UNIT_WITH_REF.matcher(units);
		NavigableMap<StringRange, UnitInfo> infos = new TreeMap<>();

		while (m.find()) {
			if (m.groupCount() != 5) {
				throw new IllegalArgumentException("Logarithmic unit with wrong number of captured groups found. "
						+ "Expected 5 groups, but found " + m.groupCount());
			}

			int exponent = m.group(2).isEmpty() ? 0 : Integer.parseInt(m.group(2));
			exponent += m.group(5).isEmpty() ? 0 : Integer.parseInt(m.group(5));

			Unit u = switch (m.group(1)) {
			case "log" -> BEL.inReferenceTo(Double.parseDouble(m.group(3)), Unit.of(m.group(4), extraUnits));
			case "ln" -> NEPER.inReferenceTo(Double.parseDouble(m.group(3)), Unit.of(m.group(4), extraUnits));
			default -> throw new IllegalArgumentException("Cannot identify corresponding LevelUnit. "
					+ "Known are log->BEL and ln->NEPER, but you asked for " + m.group(1));
			};
			infos.put(new StringRange(m.start(), m.end()), new UnitInfo(Units.IDENTITY_PREFIX, u, m.group(), exponent));
		}

		return infos;
	}
}
