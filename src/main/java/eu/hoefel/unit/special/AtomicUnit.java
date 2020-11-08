package eu.hoefel.unit.special;

import static eu.hoefel.unit.constant.physics.PhysicsConstant.BOHR_MAGNETON;
import static eu.hoefel.unit.constant.physics.PhysicsConstant.BOHR_RADIUS;
import static eu.hoefel.unit.constant.physics.PhysicsConstant.ELECTRON_MASS;
import static eu.hoefel.unit.constant.physics.PhysicsConstant.ELEMENTARY_CHARGE;
import static eu.hoefel.unit.constant.physics.PhysicsConstant.HARTREE_ENERGY;
import static eu.hoefel.unit.constant.physics.PhysicsConstant.PLANCK_CONSTANT;
import static eu.hoefel.unit.constant.physics.PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM;
import static eu.hoefel.unit.si.SiBaseUnit.AMPERE;
import static eu.hoefel.unit.si.SiBaseUnit.KILOGRAM;
import static eu.hoefel.unit.si.SiBaseUnit.METER;
import static eu.hoefel.unit.si.SiBaseUnit.SECOND;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;

/**
 * Atomic units form a set of natural units. The four units that the atomic
 * units normalize to 1 are
 * <ul>
 * <li>the {@link #ELEMENTARY_CHARGE elementary charge}
 * <li>the {@link #ELECTRON_MASS electron mass}
 * <li>the reduced {@link #PLANCK_CONSTANT Planck constant}
 * <li>the Coulomb constant
 * </ul>
 */
public enum AtomicUnit implements Unit {

	/**
	 * The atomic unit of charge is determined by the {@link #ELEMENTARY_CHARGE
	 * elementary charge}.
	 */
	OF_CHARGE(1.602176634E-19, "qa"),

	/**
	 * The atomic unit of mass is determined by the {@link #ELECTRON_MASS electron
	 * mass}.
	 */
	OF_MASS(9.1093837015E-31, "ma"),

	/**
	 * The atomic unit of action is determined by the reduced
	 * {@link #PLANCK_CONSTANT Planck constant}.
	 */
	OF_ACTION(1.0545718176461565E-34, "Sa"),

	/**
	 * The atomic unit of length is determined by the {@link #BOHR_RADIUS Bohr
	 * radius}.
	 */
	OF_LENGTH(5.29177210903E-11, "la"),

	/**
	 * The atomic unit of energy is determined by the {@link #HARTREE_ENERGY Hartree
	 * energy}.
	 */
	OF_ENERGY(4.3597447222071E-18, "Eh"),

	/**
	 * The atomic unit of time is determined by the reduced {@link #PLANCK_CONSTANT
	 * Planck constant} divided by the {@link #HARTREE_ENERGY Hartree energy}.
	 */
	OF_TIME(2.4188843265857192E-17, "ta"),

	/**
	 * The atomic unit of force is determined by the {@link #HARTREE_ENERGY Hartree
	 * energy} divided by the {@link #BOHR_RADIUS Bohr radius}.
	 */
	OF_FORCE(8.238723498254079E-8, "Fa"),

	/**
	 * The atomic unit of velocity is determined by the {@link #BOHR_RADIUS Bohr
	 * radius} divided by the {@link AtomicUnit#OF_TIME atomic unit of time}.
	 */
	OF_VELOCITY(2187691.2636411153, "va"),

	/**
	 * The atomic unit of momentum is determined by the reduced
	 * {@link #PLANCK_CONSTANT Planck constant} divided by the {@link #BOHR_RADIUS
	 * Bohr radius}.
	 */
	OF_MOMENTUM(1.9928519141000257E-24, "pa"),

	/**
	 * The atomic unit of current is determined by the {@link #ELEMENTARY_CHARGE
	 * elementary charge} divided by the {@link AtomicUnit#OF_TIME atomic unit of
	 * time}.
	 */
	OF_CURRENT(0.00662361823750989, "Ia"),

	/**
	 * The atomic unit of charge density is determined by the
	 * {@link #ELEMENTARY_CHARGE elementary charge} divided by the cubed
	 * {@link #BOHR_RADIUS Bohr radius}.
	 */
	OF_CHARGE_DENSITY(1.0812023845664917E12, "rhoa", "ρa"),

	/**
	 * The atomic unit of potential is determined by the {@link #HARTREE_ENERGY
	 * Hartree energy} divided by the {@link #ELEMENTARY_CHARGE elementary charge} .
	 */
	OF_ELECTRIC_POTENTIAL(27.211386245988034, "phia", "ϕa"),

	/**
	 * The atomic unit of the electric field is determined by the
	 * {@link AtomicUnit#OF_ELECTRIC_POTENTIAL atomic unit of electric potential}
	 * divided by the {@link #BOHR_RADIUS Bohr radius}.
	 */
	OF_ELECTRIC_FIELD(5.1422067476325946E11, "Ea"),

	/**
	 * The atomic unit of the electric field gradient is determined by the
	 * {@link AtomicUnit#OF_ELECTRIC_FIELD atomic unit of electric field} divided by
	 * the {@link #BOHR_RADIUS Bohr radius}.
	 */
	OF_ELECTRIC_FIELD_GRADIENT(9.71736242922823E21, "dEa"),

	/**
	 * The atomic unit of the electric dipole moment is determined by the
	 * {@link #ELEMENTARY_CHARGE elementary charge} times the {@link #BOHR_RADIUS
	 * Bohr radius}.
	 */
	OF_ELECTRIC_DIPOLE_MOMENT(8.478353625540766E-30, "edipolea"),

	/**
	 * The atomic unit of the electric quadrupole moment is determined by the
	 * {@link AtomicUnit#OF_ELECTRIC_DIPOLE_MOMENT atomic unit of electric dipole
	 * moment} times the {@link #BOHR_RADIUS Bohr radius}.
	 */
	OF_ELECTRIC_QUADRUPOLE_MOMENT(4.486551524613E-40, "equada"),

	/**
	 * The atomic unit of the electric polarizability is determined by the
	 * {@link #ELEMENTARY_CHARGE elementary charge} divided by the
	 * {@link AtomicUnit#OF_ELECTRIC_FIELD_GRADIENT atomic unit of electric field
	 * gradient}.
	 */
	OF_ELECTRIC_POLARIZABILITY(1.6487772743567905E-41, "alphaa", "αa"),

	/**
	 * The atomic unit of the first hyperpolarizability is determined by the
	 * {@link AtomicUnit#OF_ELECTRIC_POLARIZABILITY atomic unit of electric
	 * polarizability} divided by the {@link AtomicUnit#OF_ELECTRIC_FIELD atomic
	 * unit of electric field}.
	 */
	OF_FIRST_HYPERPOLARIZABILITY(3.2063613060985273E-53, "betaa", "βa"),

	/**
	 * The atomic unit of the second hyperpolarizability is determined by the
	 * {@link AtomicUnit#OF_FIRST_HYPERPOLARIZABILITY atomic unit of first
	 * hyperpolarizability} divided by the {@link AtomicUnit#OF_ELECTRIC_FIELD
	 * atomic unit of electric field}.
	 */
	OF_SECOND_HYPERPOLARIZABILITY(6.235379990457783E-65, "gammaa", "γa"),

	/**
	 * The atomic unit of the magnetic flux density is determined by the reduced
	 * {@link #PLANCK_CONSTANT Planck constant} divided by the
	 * {@link AtomicUnit#OF_ELECTRIC_QUADRUPOLE_MOMENT atomic unit of electric
	 * quadrupole moment}.
	 */
	OF_MAGNETIC_FLUX_DENSITY(235051.75675813094, "Ba"),

	/**
	 * The atomic unit of the magnetic dipole moment is determined by the
	 * {@link #BOHR_MAGNETON Bohr magneton} times 2.
	 */
	OF_MAGNETIC_DIPOLE_MOMENT(1.85480201566E-23, "mdipolea"),

	/**
	 * The atomic unit of the magnetizability is determined by the squared
	 * {@link AtomicUnit#OF_ELECTRIC_DIPOLE_MOMENT atomic unit of electric dipole
	 * moment} divided by the {@link #ELECTRON_MASS electron mass}.
	 */
	OF_MAGNETIZABILITY(7.891036600849703E-29, "magna"),

	/**
	 * The atomic unit of permittivity is determined by the 1e7 divided by the
	 * squared {@link #SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum}.
	 */
	OF_PERMITTIVITY(1.1126500560536184E-10, "epsa", "epsilona", "εa");

	/** The symbols representing the atomic unit. */
	private final List<String> symbols;

	/** The factor to convert from the atomic unit to the SI base units. */
	private final double factor;

	/** The SI base units to which the atomic unit corresponds. */
	private Map<Unit, Integer> baseUnits;

	/**
	 * Constructor for atomic units.
	 * 
	 * @param factor  the conversion factor to base SI units
	 * @param symbols the symbols representing the atomic unit
	 */
	private AtomicUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override
	public Map<Unit, Integer> baseUnits() {
		// Note that this is suboptimal at the moment, this should really use lazy final
		// initialization, which is not yet there (cf. https://openjdk.java.net/jeps/8209964).
		// TODO: Use lazy evaluation once available
		if (baseUnits == null) {
			baseUnits = switch (this) {
				case OF_ACTION -> Map.of(KILOGRAM, 1, METER, 2, SECOND, -1);
				case OF_ENERGY -> Map.of(KILOGRAM, 1, METER, 2, SECOND, -2);
				case OF_LENGTH -> Map.of(METER, 1);
				case OF_MASS -> Map.of(KILOGRAM, 1);
				case OF_MOMENTUM -> Map.of(KILOGRAM, 1, METER, 1, SECOND, -1);
				case OF_TIME -> Map.of(SECOND, 1);
				case OF_VELOCITY -> Map.of(METER, 1, SECOND, -1);
				case OF_CHARGE -> Map.of(AMPERE, 1, SECOND, 1);
				case OF_CHARGE_DENSITY -> Map.of(AMPERE, 1, SECOND, 1, METER, -3);
				case OF_CURRENT -> Map.of(AMPERE, 1);
				case OF_ELECTRIC_DIPOLE_MOMENT -> Map.of(AMPERE, 1, SECOND, 1, METER, 1);
				case OF_ELECTRIC_FIELD -> Map.of(KILOGRAM, 1, METER, 1, AMPERE, -1, SECOND, -3);
				case OF_ELECTRIC_FIELD_GRADIENT -> Map.of(KILOGRAM, 1, AMPERE, -1, SECOND, -3);
				case OF_ELECTRIC_POLARIZABILITY -> Map.of(KILOGRAM, -1, AMPERE, 2, SECOND, 4);
				case OF_ELECTRIC_POTENTIAL -> Map.of(KILOGRAM, 1, METER, 2, AMPERE, -1, SECOND, -3);
				case OF_ELECTRIC_QUADRUPOLE_MOMENT -> Map.of(AMPERE, 1, SECOND, 1, METER, 2);
				case OF_FIRST_HYPERPOLARIZABILITY -> Map.of(KILOGRAM, -2, METER, -1, AMPERE, 3, SECOND, 7);
				case OF_FORCE -> Map.of(KILOGRAM, 1, METER, 1, SECOND, -2);
				case OF_MAGNETIC_DIPOLE_MOMENT -> Map.of(AMPERE, 1, METER, 2);
				case OF_MAGNETIC_FLUX_DENSITY -> Map.of(KILOGRAM, 1, AMPERE, -1, SECOND, -2);
				case OF_MAGNETIZABILITY -> Map.of(KILOGRAM, -1, METER, 2, AMPERE, 2, SECOND, 2);
				case OF_PERMITTIVITY -> Map.of(KILOGRAM, -1, METER, -3, AMPERE, 2, SECOND, 4);
				case OF_SECOND_HYPERPOLARIZABILITY -> Map.of(KILOGRAM, -3, METER, -2, AMPERE, 4, SECOND, 12);
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
