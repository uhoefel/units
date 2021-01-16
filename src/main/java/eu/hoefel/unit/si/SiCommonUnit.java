package eu.hoefel.unit.si;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.binary.BinaryPrefix;
import eu.hoefel.unit.constant.physics.PhysicsConstant;

/**
 * Commonly used units that are not standard {@link SiBaseUnit SI base units} or
 * in the set of standard {@link SiDerivedUnit derived SI units}.
 */
public enum SiCommonUnit implements Unit {

	/**
	 * The Julian year is defined as 365.25 days of 86,400 {@link SiBaseUnit#SECOND
	 * second}. This is used e.g. in astronomy, and is represented in this unit.
	 * Note that "a" is not an allowed symbol due to it conflicting with
	 * {@link SiDerivedUnit#PASCAL Pa} (vs. Pa representing 10<sup>15</sup> years),
	 * "y" is also not accepted due to it conflicting with {@link SiDerivedUnit#GRAY
	 * Gy} (vs. Gy representing 10<sup>9</sup> years).
	 */
	YEAR(31_557_600/* This corresponds to a julian year*/, "yr"),

	/**
	 * One astronomical unit corresponds to roughly the distance between the Earth
	 * and the Sun. It is mainly used to measure distances in the Solar System.
	 */
	ASTRONOMICAL_UNIT(149_597_870_700.0, "au"),

	/** One day, corresponding to 86,400 {@link SiBaseUnit#SECOND seconds}. */
	DAY(86_400, "d"),

	/** One hour, corresponding to 3,600 {@link SiBaseUnit#SECOND seconds}. */
	HOUR(3_600, "h"),

	/** One minute, corresponding to 60 {@link SiBaseUnit#SECOND seconds}. */
	MINUTE(60, "min"),

	/**
	 * The arcdegree is a measure for a plane angle, such that a full rotation
	 * corresponds to 360° or 2π {@link SiDerivedUnit#RADIAN radians}.
	 */
	ARCDEGREE(Math.PI / 180, "°"),

	/**
	 * The arcminute is a measure for a plane angle. It is 1/60th of an
	 * {@link #ARCDEGREE arcdegree}.
	 */
	ARCMINUTE(Math.PI / (60 * 180), "'"),

	/**
	 * The arcsecond is a measure for a plane angle. It is 1/60th of a
	 * {@link #ARCMINUTE arcminute}.
	 */
	ARCSECOND(Math.PI / (3_600 * 180), "''"),

	/**
	 * A hectare is corresponding to a square with 100 {@link SiBaseUnit#METER
	 * meter}. It is primarily used to measure land. Note that 'ha' is not an
	 * allowed symbol for hectare, as it would conflict with hectoannum. However,
	 * 'hectare' can be used.
	 */
	HECTARE(1e4, "hectare"),

	/** There are a thousand liters in one cubic {@link SiBaseUnit#METER meter}. */
	LITER(1e-3, "l", "L"),

	/** One tonne corresponds to one thousand {@link SiBaseUnit#KILOGRAM kilogram}. */
	TONNE(1e6, "t"),

	/**
	 * A parsec is a unit commonly used in astronomy. It is defined as the distance
	 * at which one {@link #ASTRONOMICAL_UNIT astronomical unit} subtends an angle of one
	 * {@link #ARCSECOND arcsecond}. This leads to a parsec being roughly 3.26
	 * {@link #LIGHTYEAR light-years}.
	 */
	PARSEC(648_000 * 1.495978707 * 1e11 / Math.PI, "pc"),

	/**
	 * A light-year is defined as the product of one {@link #YEAR year} and the
	 * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum} as
	 * defined by the <a href=
	 * "http://asa.usno.navy.mil/static/files/2009/Astronomical_Constants_2009.pdf">IAU</a>.
	 */
	LIGHTYEAR(9_460_730_472_580_800.0, "ly"),

	/**
	 * The angstrom, named after Anders Jonas Ångström, corresponds to 0.1
	 * {@link SiPrefix#NANO nano}{@link SiBaseUnit#METER meter}.
	 */
	ANGSTROM(1e-10, "Å", "ångström", "angstrom", "Angstrom"),

	/**
	 * An electronvolt (denoted eV) is the kinetic energy gained by an electron
	 * accelerating from rest through an electric potential difference od one
	 * {@link SiDerivedUnit#VOLT volt} in vacuum. To convert to
	 * {@link SiDerivedUnit#JOULE joule}, one needs to multiply the energy in
	 * electronvolt with {@link PhysicsConstant#ELEMENTARY_CHARGE elementary
	 * charge}.
	 */
	ELECTRONVOLT(1.602176634e-19, "eV"), // we ensure in the tests that the value matches the constant in PhysicsConstant, otherwise we get problems with the initialization

	/**
	 * The dalton, also known as unified atomic mass unit (note that only Da is
	 * allowed as symbol, as u would collide with {@link #ASTRONOMICAL_UNIT
	 * astronomical unit} with the {@link SiPrefix#ATTO atto} prefix) is a unit of
	 * mass. It is defined as 1/12th of the mass of an unbound carbon atom in its
	 * ground state and at rest.
	 */
	DALTON(1.6605390666e-27, "Da"), // we ensure in the tests that the value matches the constant in PhysicsConstant, otherwise we get problems with the initialization

	/**
     * The bar is a unit of pressure, which is not accepted by the International
     * Bureau of Weights and Measures for use with the SI. However, in practice it
     * is still widely used. One bar corresponds to one
     * {@link PhysicsConstant#STANDARD_STATE_PRESSURE}. The unit is named in honor
     * of Vilhelm Bjerknes, who worked in weather forecasting.
     */
    BAR(1e5, "bar"); 

	/** Units that should be loaded for the sake of convenience. */
	private static final Set<Unit> COMPATIBLE_UNITS;

	/** The symbols representing the commonly used unit. */
	private final List<String> symbols;

	/** The factor to convert from the commonly used unit to the SI base units. */
	private final double factor;

	/** The SI base units to which the commonly used unit corresponds. */
	private static final Map<SiCommonUnit, Map<Unit, Integer>> baseUnits = new EnumMap<>(SiCommonUnit.class);

	/** The default prefixes. */
	private static final Set<UnitPrefix> DEFAULT_PREFIXES;

	static {
		Set<Unit> compatible = new HashSet<>();
		Collections.addAll(compatible, SiBaseUnit.values());
		Collections.addAll(compatible, SiDerivedUnit.values());
		COMPATIBLE_UNITS = Set.copyOf(compatible);

		Set<UnitPrefix> defPrefixes = new HashSet<>();
		Collections.addAll(defPrefixes, SiPrefix.values());
		Collections.addAll(defPrefixes, BinaryPrefix.values());
		DEFAULT_PREFIXES = Set.copyOf(defPrefixes);

		for (SiCommonUnit unit : values()) {
			switch (unit) {
				case ANGSTROM -> baseUnits.put(unit, Map.of(SiBaseUnit.METER, 1));
				case YEAR, HOUR, DAY, MINUTE -> baseUnits.put(unit, Map.of(SiBaseUnit.SECOND, 1));
				case ARCDEGREE, ARCMINUTE, ARCSECOND -> baseUnits.put(unit, Map.of());
				case DALTON -> baseUnits.put(unit, Map.of(SiBaseUnit.KILOGRAM, 1));
				case ELECTRONVOLT -> baseUnits.put(unit, Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2));
				case HECTARE -> baseUnits.put(unit, Map.of(SiBaseUnit.METER, 2));
				case LITER -> baseUnits.put(unit, Map.of(SiBaseUnit.METER, 3));
				case PARSEC, LIGHTYEAR, ASTRONOMICAL_UNIT -> baseUnits.put(unit, Map.of(SiBaseUnit.METER, 1));
				case TONNE -> baseUnits.put(unit, Map.of(SiBaseUnit.KILOGRAM, 1));
				case BAR -> baseUnits.put(unit, Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, -1, SiBaseUnit.SECOND, -2));
			};
		}
	}

	/**
	 * Constructor for commonly used SI units.
	 * 
	 * @param factor  the conversion factor to base SI units
	 * @param symbols the symbols representing the commonly used SI unit
	 */
	private SiCommonUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override public double factor(String symbol) { return factor; }
	@Override public Map<Unit, Integer> baseUnits() { return baseUnits.get(this); }
	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return this != DAY; }
	@Override public boolean isConversionLinear() { return true; }
	@Override public double convertToBaseUnits(double value) { return factor * value; }
	@Override public double convertFromBaseUnits(double value) { return value / factor; }
	@Override public Set<UnitPrefix> prefixes() { return DEFAULT_PREFIXES; }
	@Override public boolean isBasic() { return false; }
	@Override public Set<Unit> compatibleUnits() { return COMPATIBLE_UNITS; }
}
