package eu.hoefel.unit.special;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.constant.physics.PhysicsConstant;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;

/**
 * The conventional electrical units were defined in the 1980s to increase the
 * precision of measurements, as they are based on the Josephson and von
 * Klitzing constants, both which could be measured with high accuracy and
 * relative ease, before the redefinition of the SI units in
 * <a href="https://doi.org/10.1088/1681-7575/ab0013">2019</a>. As the values of
 * the Josephson and von Klitzing constant as defined in the 80s came into
 * effect in 1990, their corresponding values are called
 * {@link PhysicsConstant#CONVENTIONAL_VALUE_OF_JOSEPHSON_CONSTANT conventional
 * value of the Josephson constant} and
 * {@link PhysicsConstant#CONVENTIONAL_VALUE_OF_VON_KLITZING_CONSTANT
 * conventional value of the von Klitzing constant} with their symbols having an
 * appended "90". Since the SI redefinition, the values of the
 * {@link PhysicsConstant#JOSEPHSON_CONSTANT Josephson constant} and the
 * {@link PhysicsConstant#VON_KLITZING_CONSTANT von Klitzing constant} deviate
 * slightly from their conventional counterparts, leading to fixed (and exact)
 * conversion factors.
 */
public enum ConventionalElectricalUnit implements Unit {

	/** The conventional counterpart of the  {@link SiBaseUnit#AMPERE ampere}. */
	AMPERE(1.0000000892670184, "A90"),

	/** The conventional counterpart of the  {@link SiDerivedUnit#COULOMB coulomb}. */
	COULOMB(1.0000000892670184, "C90"),

	/** The conventional counterpart of the  {@link SiDerivedUnit#FARAD farad}. */
	FARAD(0.9999999825667936, "F90"),

	/** The conventional counterpart of the  {@link SiDerivedUnit#HENRY henry}. */
	HENRY(1.0000000174332067, "H90"),

	/**
	 * The conventional ohm is based on the
	 * {@link PhysicsConstant#CONVENTIONAL_VALUE_OF_VON_KLITZING_CONSTANT
	 * conventional value of the von Klitzing constant}, making use of the quantum
	 * Hall effect.
	 */
	OHM(1.0000000174332067, "Ω90", "Ohm90", "ohm90"),

	/**
	 * The conventional volt is based on the
	 * {@link PhysicsConstant#CONVENTIONAL_VALUE_OF_JOSEPHSON_CONSTANT conventional
	 * value of the Josephson constant}, making use of the Josephson effect.
	 */
	VOLT(1.0000001067002267, "V90"),

	/** The conventional counterpart of the  {@link SiDerivedUnit#WATT watt}. */
	WATT(1.0000001959672546, "W90");

	/** The symbols representing the conventional electrical unit. */
	private final List<String> symbols;

	/** The SI base units to which the conventional electrical unit corresponds. */
	private Map<Unit, Integer> baseUnits;

	/** The factor to convert from the conventional electrical unit to the SI base units. */
	private final double factor;

	/**
	 * Constructor for conventional electrical units.
	 * 
	 * @param factor  the conversion factor to base SI units
	 * @param symbols the symbols representing the conventional electrical unit
	 */
	private ConventionalElectricalUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override
	public Map<Unit, Integer> baseUnits() {
		if (baseUnits == null) {
			baseUnits = switch (this) {
				case AMPERE -> Map.of(SiBaseUnit.SECOND, -1);
				case COULOMB -> Map.of(SiBaseUnit.SECOND, 1, SiBaseUnit.AMPERE, 1);
				case FARAD -> Map.of(SiBaseUnit.KILOGRAM, -1, SiBaseUnit.METER, -2, SiBaseUnit.SECOND, 4, SiBaseUnit.AMPERE, 2);
				case HENRY -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2, SiBaseUnit.AMPERE, -2);
				case OHM -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -3, SiBaseUnit.AMPERE, -2);
				case VOLT -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -3, SiBaseUnit.AMPERE, -1);
				case WATT -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -3);
			};
		}
		return baseUnits;
	}

	@Override public double factor(String symbol) { return factor; }
	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return false; }
	@Override public boolean isConversionLinear() { return true; }
	@Override public double convertToBaseUnits(double value) { return factor * value; }
	@Override public double convertFromBaseUnits(double value) { return value / factor; }
	@Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
	@Override public boolean isBasic() { return false; }
}
