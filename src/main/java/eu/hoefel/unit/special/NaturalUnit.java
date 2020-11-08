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
 * Natural units are physical units of measurement based exclusively on
 * universal physical constants. As an example, the
 * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum} is
 * a natural unit of speed. In contrast to convention, this implementation does
 * not allow dimensionless units, so a symbol for each unit is defined. This is
 * necessary, besides avoiding potential ambiguities, to use the methods from
 * {@link Unit}, which won't work if multiple units have the same symbol. This
 * implementation uses the CODATA set of natural units.
 * 
 * @see <a href="http://doi.org/10.5281/zenodo.22826">CODATA reference</a>
 */
public enum NaturalUnit implements Unit {

	/**
	 * The natural unit of the velocity is determined by the
	 * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum}.
	 */
	OF_VELOCITY(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.value(), "c", "c0"),

	/**
	 * The natural unit of the action is determined by the reduced
	 * {@link PhysicsConstant#PLANCK_CONSTANT Planck constant}.
	 */
	OF_ACTION(PhysicsConstant.PLANCK_CONSTANT.value() / (2 * Math.PI), "hbar", "ℏ"),

	/**
	 * The natural unit of the mass is determined by the
	 * {@link PhysicsConstant#ELECTRON_MASS electron mass}.
	 */
	OF_MASS(PhysicsConstant.ELECTRON_MASS.value(), "me", "m_e"),

	/** The natural unit of the energy is determined by the electron rest energy. */
	OF_ENERGY(PhysicsConstant.ELECTRON_MASS.mul(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM.pow(2)).value(), "mec^2",
			"m_ec^2", "mec0^2", "m_ec0^2"),

	/**
	 * The natural unit of the momentum is determined by the
	 * {@link PhysicsConstant#ELECTRON_MASS electron mass} multiplied by the
	 * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum}.
	 */
	OF_MOMENTUM(PhysicsConstant.ELECTRON_MASS.mul(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM).value(), "mec", "m_ec",
			"mec0", "m_ec0"),

	/**
	 * The natural unit of the length is determined by the
	 * {@link PhysicsConstant#COMPTON_WAVELENGTH Compton wavelength}.
	 */
	OF_LENGTH(PhysicsConstant.COMPTON_WAVELENGTH.value() / (2 * Math.PI), "lambdabarC", "hbar/mec", "ℏ/mec",
			"hbar/m_ec", "ℏ/m_ec", "hbar/mec0", "ℏ/mec0", "hbar/m_ec0", "ℏ/m_ec0"),

	/**
	 * The natural unit of the length is determined by the
	 * {@link PhysicsConstant#COMPTON_WAVELENGTH Compton wavelength} divided by the
	 * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum}.
	 */
	OF_TIME(PhysicsConstant.COMPTON_WAVELENGTH.div(PhysicsConstant.SPEED_OF_LIGHT_IN_VACUUM).value(), "hbar/mec^2",
			"ℏ/mec^2", "hbar/m_ec^2", "ℏ/m_ec^2", "hbar/mec0^2", "ℏ/mec0^2", "hbar/m_ec0^2", "ℏ/m_ec0^2"),;

	/** The symbols representing the natural unit. */
	private final List<String> symbols;

	/** The factor to convert from the natural unit to the SI base units. */
	private final double factor;

	/** The SI base units to which the natural unit corresponds. */
	private Map<Unit, Integer> baseUnits;

	/**
	 * Constructor for natural units.
	 * 
	 * @param factor  the conversion factor to base SI units
	 * @param symbols the symbols representing the natural unit
	 */
	private NaturalUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override
	public Map<Unit, Integer> baseUnits() {
		if (baseUnits == null) {
			baseUnits = switch (this) {
				case OF_ACTION -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -1);
				case OF_ENERGY -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2);
				case OF_LENGTH -> Map.of(SiBaseUnit.METER, 1);
				case OF_MASS -> Map.of(SiBaseUnit.KILOGRAM, 1);
				case OF_MOMENTUM -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -1);
				case OF_TIME -> Map.of(SiBaseUnit.SECOND, 1);
				case OF_VELOCITY -> Map.of(SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -1);
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
