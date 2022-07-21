package eu.hoefel.unit.si;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.binary.BinaryPrefix;
import eu.hoefel.unit.constant.physics.PhysicsConstant;

/**
 * The SI base units form the irreducible set of units (i.e., a base unit cannot
 * be expressed as a combination of other base units) in the International
 * System of Units that allow to derive all other SI units. The SI system
 * <a href="https://doi.org/10.1088/1681-7575/ab0013">redefined the base units
 * in 2019</a> (see also <a href=
 * "https://www.bipm.org/utils/common/pdf/si-brochure/SI-Brochure-9-EN.pdf">here</a>).
 */
public enum SiBaseUnit implements Unit {

    /**
     * The second, symbol s, is the SI unit of time. It is defined by the
     * {@link PhysicsConstant#HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine
     * transition frequency of caesium 133}. See also
     * <a href="https://doi.org/10.1088/1681-7575/ab0013">here</a>.
     */
    SECOND("s"),

    /**
     * The meter, symbol m, is the SI unit of length. It is defined by the
     * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum}
     * when expressed in m s^-1, where the {@link #SECOND second} is defined by the
     * {@link PhysicsConstant#HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine
     * transition frequency of caesium 133}.
     */
    METER("m"),

    /**
     * The kilogram, symbol kg, is the SI unit of mass. It is defined by the
     * {@link PhysicsConstant#PLANCK_CONSTANT Planck constant} when expressed in the
     * unit J s, which is equal to kg m^2 s^−1, where the {@link #METER meter} and
     * the {@link #SECOND second} are defined in terms of the
     * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum} and
     * the {@link PhysicsConstant#HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine
     * transition frequency of caesium 133}. Due to historic reasons, this unit (kg)
     * has a prefix (k for {@link SiPrefix#KILO kilo}) attached to it, in which case
     * no further prefixes are allowed. It is however allowed to use the
     * {@link #GRAM gram} in combination with other prefixes if the prepended
     * {@link SiPrefix#KILO k} of the kg is removed, so e.g. mg is fine.
     * <p>
     * Note that other units that would intuitively refer to the kilogram as one of
     * their base units should usually refer to the gram instead, with an
     * appropriately adjusted conversion.
     * 
     * @see #GRAM
     */
    KILOGRAM("kg"),

    /**
     * The gram is <em>not</em> a base SI unit; the corresponding SI base unit is
     * the {@link #KILOGRAM kilogram}. However, this is only the case due to
     * historic reasons, and it would complicate the internal code dramatically, so
     * for practical purposes the gram is offered as a
     * <q>pseudo</q> base unit here.
     * <p>
     * Note that other units that would intuitively refer to the kilogram as one of
     * their base units should usually refer to the gram instead, with an
     * appropriately adjusted conversion.
     * 
     * @see #KILOGRAM
     */
    GRAM("g"),

    /**
     * The ampere, symbol A, is the SI unit of electric current. It is defined by
     * the {@link PhysicsConstant#ELEMENTARY_CHARGE elementary charge} when
     * expressed in the unit {@link SiDerivedUnit#COULOMB C}, which is equal to A s,
     * where the {@link #SECOND second} is defined in terms of the
     * {@link PhysicsConstant#HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine
     * transition frequency of caesium 133}.
     */
    AMPERE("A"),

    /**
     * The kelvin, symbol K, is the SI unit of thermodynamic temperature. It is
     * defined by the {@link PhysicsConstant#BOLTZMANN_CONSTANT} when expressed in
     * the unit J K^−1, which is equal to kg m^2 s^−2 K^−1, where the
     * {@link #KILOGRAM kilogram}, {@link #METER meter} and {@link #SECOND second}
     * are defined in terms of the {@link PhysicsConstant#PLANCK_CONSTANT Planck
     * constant}, the {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light
     * in vacuum} and the
     * {@link PhysicsConstant#HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine
     * transition frequency of caesium 133}.
     */
    KELVIN("K"),

    /**
     * The mole, symbol mol, is the SI unit of amount of substance. It is defined by
     * the {@link PhysicsConstant#AVOGADRO_CONSTANT Avogadro constant} when
     * expressed in the unit mol^-1. The amount of substance, symbol n, of a system
     * is a measure of the number of specified elementary entities. An elementary
     * entity may be an atom, a molecule, an ion, an electron, any other particle or
     * specified group of particles.
     */
    MOLE("mol"),

    /**
     * The candela, symbol cd, is the SI unit of luminous intensity in a given
     * direction. It is defined by the {@link PhysicsConstant#LUMINOUS_EFFICACY
     * luminous efficacy} when expressed in the unit lm W^−1, which is equal to cd
     * sr W^−1, or cd sr kg^−1 m^−2 s^3, where the {@link #KILOGRAM kilogram},
     * {@link #METER meter} and {@link #SECOND second} are defined in terms of the
     * {@link PhysicsConstant#PLANCK_CONSTANT Planck constant}, the
     * {@link PhysicsConstant#SPEED_OF_LIGHT_IN_VACUUM speed of light in vacuum} and
     * the {@link PhysicsConstant#HYPERFINE_TRANSITION_FREQUENCY_OF_CS_133 hyperfine
     * transition frequency of caesium 133}.
     */
    CANDELA("cd");

    /** The symbols representing the base unit. */
    private final List<String> symbols;

    /** The default prefixes. */
    private static final Set<UnitPrefix> DEFAULT_PREFIXES;

    static {
        Set<UnitPrefix> defPrefixes = new HashSet<>();
        Collections.addAll(defPrefixes, SiPrefix.values());
        Collections.addAll(defPrefixes, BinaryPrefix.values());
        DEFAULT_PREFIXES = Set.copyOf(defPrefixes);
    }

    /**
     * Constructor for SI base units.
     * 
     * @param symbol the symbols representing the base unit
     */
    private SiBaseUnit(String symbol) {
        this.symbols = List.of(symbol);
    }

    @Override public double factor() { return this == KILOGRAM ? 1e3 : 1; }
    @Override public Map<Unit, Integer> baseUnits() { return this == KILOGRAM ? Map.of(GRAM, 1) : Map.of(this, 1); }
    @Override public List<String> symbols() { return symbols; }
    @Override public boolean prefixAllowed(String symbol) { return this != KILOGRAM; }
    @Override public boolean isConversionLinear() { return true; }
    @Override public double convertToBaseUnits(double value) { return this == KILOGRAM ? 1e3*value : value; }
    @Override public double convertFromBaseUnits(double value) { return this == KILOGRAM ? 1e-3*value : value; }
    @Override public Set<UnitPrefix> prefixes() { return this == KILOGRAM ? Units.EMPTY_PREFIXES : DEFAULT_PREFIXES; }
    @Override public boolean isBasic() { return this != KILOGRAM; }
    @Override public Set<Unit> compatibleUnits() { return Set.of(values()); }
}