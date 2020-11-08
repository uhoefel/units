package eu.hoefel.unit.level;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;
import java.util.logging.Logger;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
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
			logger.warning(("For the given reference unit (%s) no matching reference unit type "
					+ "could be found that matched the given reference unit type (%s). "
					+ "Continuing with the given reference unit type, but the resulting symbol "
					+ "will not be uniquely resolvable (the returned unit will be correct, though). "
					+ "Please consider reporting the reference unit and its type "
					+ "to %s")
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

		symbol += String.format(Locale.ENGLISH, "(re %s %s)", refValueString, refUnit.symbols().get(0));
		List<String> symbols = List.of(symbol);

		Set<Unit> compatibleUnits = Set.of(Units.flattenUnits(LevelUnit.values(), refUnit.compatibleUnits().stream().toArray(Unit[]::new)));

		Supplier<Unit> u = () -> new Unit() {
			// Note that we do not override equals and hashcode here, as this unit is only
			// accessible via the concurrent map, so there should always be at most one
			// instance of a unit with the properties as specified here, so we are hopefully
			// fine if it uses the memory address for comparison
			@Override public List<String> symbols() { return symbols; }
			@Override public Set<UnitPrefix> prefixes() { return Set.of(); }
			@Override public boolean prefixAllowed(String symbol) { return false; }
			@Override public boolean isBasic() { return false; }
			@Override public double factor(String symbol) { return Double.NaN; }
			@Override public double convertToBaseUnits(double value) { return toBase.applyAsDouble(value); }
			@Override public double convertFromBaseUnits(double value) { return fromBase.applyAsDouble(value); }
			@Override public boolean canUseFactor() { return false; }
			@Override public Map<Unit, Integer> baseUnits() { return refUnit.baseUnits(); }
			@Override public Set<Unit> compatibleUnits() { return compatibleUnits; }
		};

		return Units.computeSpecialUnitIfAbsent(symbol, compatibleUnits, u);
	}
}
