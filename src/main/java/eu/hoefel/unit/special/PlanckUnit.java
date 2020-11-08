package eu.hoefel.unit.special;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.constant.physics.PhysicsConstant;
import eu.hoefel.unit.si.SiBaseUnit;

/**
 * Planck units form a set of natural units. The five units that the Planck
 * units normalize to 1 are
 * <ul>
 * <li>the {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in
 * vacuum}
 * <li>the {@link PhysicsConstant#NEWTONIAN_CONSTANT_OF_GRAVITATION Newtonian
 * constant of gravitation}
 * <li>the reduced {@link PhysicsConstant#PLANCK_CONSTANT Planck constant}
 * <li>the Coulomb constant
 * <li>the {@link PhysicsConstant#BOLTZMANN_CONSTANT Boltzmann constant}
 * </ul>
 */
public enum PlanckUnit implements Unit {

	/** The Planck length. See {@link PhysicsConstant#PLANCK_LENGTH Planck length}. */
	PLANCK_LENGTH(PhysicsConstant.PLANCK_LENGTH.value(), "lp"),

	/** The Planck mass. See {@link PhysicsConstant#PLANCK_MASS Planck mass}. */
	PLANCK_MASS(PhysicsConstant.PLANCK_MASS.value(), "mp"),

	/** The Planck time. See {@link PhysicsConstant#PLANCK_TIME Planck time}. */
	PLANCK_TIME(PhysicsConstant.PLANCK_TIME.value(), "tp"),

	/**
	 * The Planck charge. An intuitive picture of what a Planck charge is: Consider
	 * a sphere of one Planck length in diameter. Adding one Planck charge to that
	 * sphere increases the spheres mass by one Planck mass.
	 */
	PLANCK_CHARGE(PhysicsConstant.ELEMENTARY_CHARGE.div(PhysicsConstant.FINE_STRUCTURE_CONSTANT.root(2)).value(), "qp"),

	/**
	 * The Planck temperature. See {@link PhysicsConstant#PLANCK_TEMPERATURE Planck
	 * temperature}.
	 */
	PLANCK_TEMPERATURE(PhysicsConstant.PLANCK_TEMPERATURE.value(), "Tp"),

	/** The Planck momentum. */
	PLANCK_MOMENTUM(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.mul(PhysicsConstant.PLANCK_MASS).value(), "pp"),

	/** The Planck energy. This corresponds to the highest energy a photon could carry. */
	PLANCK_ENERGY(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.pow(2).mul(PhysicsConstant.PLANCK_MASS).value(), "Ep"),

	/**
	 * The Planck force. Two bodies, separated by one {@link #PLANCK_LENGTH Planck
	 * length} and each with a weight of one {@link #PLANCK_MASS Planck mass} exert
	 * a gravitational force of one Planck force.
	 */
	PLANCK_FORCE(PLANCK_ENERGY.factor(PLANCK_ENERGY.symbols().get(0)) / PhysicsConstant.PLANCK_LENGTH.value(), "Fp"),

	/** The Planck density. */
	PLANCK_DENSITY(PhysicsConstant.PLANCK_MASS.div(PhysicsConstant.PLANCK_LENGTH.pow(3)).value(), "rhop", "ρp"),

	/**
	 * The Planck acceleration. It is the acceleration required to get from zero
	 * speed to the {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light
	 * in vacuum} within one {@link PhysicsConstant#PLANCK_TIME Planck time}.
	 */
	PLANCK_ACCELERATION(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.div(PhysicsConstant.PLANCK_TIME).value(), "ap");

	/** The symbols representing the Planck unit. */
	private final List<String> symbols;

	/** The factor to convert from the Planck unit to the SI base units. */
	private final double factor;

	/** The SI base units to which the Planck unit corresponds. */
	private Map<Unit, Integer> baseUnits;

	/**
	 * Constructor for Planck units.
	 * 
	 * @param factor  the conversion factor to base SI units
	 * @param symbols the symbols representing the Planck unit
	 */
	private PlanckUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override
	public Map<Unit, Integer> baseUnits() {
		if (baseUnits == null) {
			baseUnits = switch (this) {
				case PLANCK_ACCELERATION -> Map.of(SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -2);
				case PLANCK_CHARGE -> Map.of(SiBaseUnit.AMPERE, 1, SiBaseUnit.SECOND, 1);
				case PLANCK_DENSITY -> Map.of(SiBaseUnit.METER, -3);
				case PLANCK_ENERGY -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2);
				case PLANCK_FORCE -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -2);
				case PLANCK_LENGTH -> Map.of(SiBaseUnit.METER, 1);
				case PLANCK_MASS -> Map.of(SiBaseUnit.KILOGRAM, 1);
				case PLANCK_MOMENTUM -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -1);
				case PLANCK_TEMPERATURE -> Map.of(SiBaseUnit.KELVIN, 1);
				case PLANCK_TIME -> Map.of(SiBaseUnit.SECOND, 1);
			};
		}
		return baseUnits;
	}

	@Override public double factor(String symbol) { return factor; }
	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return false; }
	@Override public boolean canUseFactor() { return true; }
	@Override public double convertToBaseUnits(double value) { return factor * value; }
	@Override public double convertFromBaseUnits(double value) { return value / factor; }
	@Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
	@Override public boolean isBasic() { return false; }
	@Override public Set<Unit> compatibleUnits() { return Units.DEFAULT_UNITS; }
}
