package eu.hoefel.unit.si;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.binary.BinaryPrefix;
import eu.hoefel.unit.constant.physics.PhysicsConstant;

/**
 * The Bureau International des Poids et Mesures provides a list of coherently
 * derived SI units (see
 * <a href="https://www.bipm.org/utils/common/pdf/si_brochure_8_en.pdf">here</a>
 * on page 118). This enum provides all of the 22 coherently derived units.
 */
public enum SiDerivedUnit implements Unit {

    /**
     * The becquerel (denoted Bq) is the unit of activity referred to a radionuclide
     * (which is not radioactivity, see
     * <a href="https://www.bipm.org/utils/common/pdf/si_brochure_8_en.pdf">page
     * 118</a>), i.e. one becquerel is defined as the activity of a quantity of
     * radioactive material in which one nucleus decays per {@link SiBaseUnit#SECOND
     * second}. Thus, it is usually used in combination with one of the large
     * {@link SiPrefix SI prefixes}. It is named after Antoine Henri Becquerel.
     */
    BECQUEREL("Bq"),

    /**
     * The coulomb (denoted C) is the unit of electric charge. One coulomb is the
     * charge transported by a current of one {@link SiBaseUnit#AMPERE ampere} in
     * one {@link SiBaseUnit#SECOND second}.
     */
    COULOMB("C"),

    /**
     * The degree Celsius, named after Anders Celsius, is a temperature scale. One
     * degree Celsius corresponds to exactly one degree {@link SiBaseUnit#KELVIN
     * kelvin}, and absolute zero (0K) corresponds to -273.15°C.
     */
    DEGREE_CELSIUS("°C"),

    /**
     * The farad (denoted F), named after Michael Faraday, is the unit of electrical
     * capacitance, i.e. the ability of a body to store electrical charge.
     */
    FARAD("F"),

    /**
     * The gray (denoted Gy), named after Louis Harold Gray, is the unit of ionizing
     * radiation dose, i.e. the absorption of one {@link #JOULE joule} of radiation
     * energy per {@link SiBaseUnit#KILOGRAM kilogram} of matter.
     */
    GRAY("Gy"),

    /**
     * The henry (denoted H), named after Joseph Henry, is the unit of electrical
     * inductance. A coil has an inductivity of one henry, if, while changing the
     * current by one {@link SiBaseUnit#AMPERE ampere} per one
     * {@link SiBaseUnit#SECOND second}, a self induction voltage of one
     * {@link #VOLT volt} is created.
     */
    HENRY("H"),

    /**
     * The hertz (denoted Hz), named after Heinrich Rudolf Hertz, is the unit of
     * frequency, i.e. one hertz is defined as one cycle per one
     * {@link SiBaseUnit#SECOND second}.
     */
    HERTZ("Hz"),

    /**
     * The joule (denoted J), named after James Prescott Joule, is the unit of
     * energy, i.e. one joule is the work done on an object when a force of one
     * {@link #NEWTON newton} (applied in the direction of the objects movement) is
     * applied over the distance of one {@link SiBaseUnit#METER meter}.
     */
    JOULE("J"),

    /**
     * The katal (denoted kat) is the unit of catalytic activity, i.e. one katal
     * corresponds to one {@link SiBaseUnit#MOLE mole} per one
     * {@link SiBaseUnit#SECOND second} (not the reaction rate, that is expressed as
     * mol m^-3 s^-1), an intrinsic property of the chosen catalyst.
     */
    KATAL("kat"),

    /**
     * The lumen (denoted lm) is the unit of luminous flux, i.e. a measure of the
     * total amount of visible light emitted by a source per time. Note that it is
     * not a power, as the luminous flux is weighted with respect to the human's eye
     * sensitivity to different wavelengths. One lumen per square
     * {@link SiBaseUnit#METER meter} is one {@link #LUX lux}.
     */
    LUMEN("lm"),

    /**
     * The lux (denoted lx) is the unit of illuminance, i.e. one lux corresponds to
     * one {@link #LUMEN lumen} per square {@link SiBaseUnit#METER meter}. Note that
     * in contrast to {@link #WATT watt} per square {@link SiBaseUnit#METER meter},
     * the lux is weighted with respect to the human eye's sensitivity to different
     * wavelengths.
     */
    LUX("lx"),

    /**
     * The newton (denoted N), named after Isaac Newton, is the unit of force. One
     * newton is the force needed to accelerate one {@link SiBaseUnit#KILOGRAM
     * kilogram} of matter at the rate of one {@link SiBaseUnit#METER meter} per
     * square {@link SiBaseUnit#SECOND second} in the direction of the force.
     */
    NEWTON("N"),

    /**
     * The ohm (denoted Ω), named after Georg Simon Ohm, is the unit of electrical
     * resistance. One ohm corresponds to the electrical resistance between two
     * points, if the constant potential difference of one {@link #VOLT volt}
     * between these two points produces a current of one {@link SiBaseUnit#AMPERE
     * ampere}.
     */
    OHM("Ω", "Ohm", "ohm"),

    /**
     * The pascal (denoted Pa), named after Blaise Pascal, is the unit of pressure.
     * One pascal corresponds to one {@link #NEWTON newton} per square
     * {@link SiBaseUnit#METER meter}. Both the
     * {@link PhysicsConstant#STANDARD_ATMOSPHERE standard atmosphere} and the
     * {@link PhysicsConstant#STANDARD_STATE_PRESSURE standard state pressure} are
     * given in pascal.
     */
    PASCAL("Pa"),

    /**
     * The radian (denoted rad) is the unit for measuring angles. The unit circle
     * arc length is identical to the measurement in radians of the angle that it
     * subtends.
     */
    RADIAN("rad"),

    /**
     * The siemens (denoted S), named after Ernst Werner von Siemens, is the unit of
     * electric conductance, susceptance, admittance. One siemens corresponds to the
     * reciprocal of one {@link #OHM}.
     */
    SIEMENS("℧", "S", "mho"),

    /**
     * The sievert (denoted Sv), named after Rolf Maximilian Sievert, is the unit of
     * ionizing radiation dose. It is a measure of the health effect of low levels
     * of ionizing radiation on the human body.
     */
    SIEVERT("Sv"),

    /**
     * The steradian (denoted sr), also called square {@link #RADIAN radian}, is the
     * unit of solid angles. In contrast to the {@link #RADIAN radian}, which is
     * used for plane angles, the steradian is used for 3D operations, i.e. a solid
     * angle in steradians, projected onto a sphere corresponds to an area on the
     * surface of the sphere.
     */
    STERADIAN("sr"),

    /**
     * The tesla (denoted T), named after Nikola Tesla, is the unit of the magnetic
     * induction. One tesla corresponds to one {@link #WEBER weber} per square
     * {@link SiBaseUnit#METER meter}.
     */
    TESLA("T"),

    /**
     * The volt (denoted V), named after Alessandro Volta, is the unit of the
     * electric potential, potential difference and electromotive force. One volt
     * corresponds to the difference in electric potential between two points if an
     * electric current of one {@link SiBaseUnit#AMPERE ampere} dissipates one
     * {@link #WATT watt} of power between the points.
     */
    VOLT("V"),

    /**
     * The watt (denoted W), named after James Watt, is the unit to quantify the
     * rate of energy transfer. One watt corresponds to one {@link #JOULE joule} per
     * {@link SiBaseUnit#SECOND second}.
     */
    WATT("W"),

    /**
     * The weber (denoted Wb), named after Wilhelm Eduard Weber, is the unit of
     * magnetic flux. One weber is one {@link #TESLA tesla} times one
     * {@link SiBaseUnit#METER meter} squared.
     */
    WEBER("Wb");

    /** Units that should be loaded for the sake of convenience. */
    private static final Set<Unit> COMPATIBLE_UNITS = Set.of(SiBaseUnit.values());

    /** The symbols representing the coherently derived SI unit. */
    private final List<String> symbols;

    /** The SI base units to which the coherently derived SI unit corresponds. */
    private Map<Unit, Integer> baseUnits;

    /** The default prefixes. */
    private static final Set<UnitPrefix> DEFAULT_PREFIXES;

    static {
        Set<UnitPrefix> defPrefixes = new HashSet<>();
        Collections.addAll(defPrefixes, SiPrefix.values());
        Collections.addAll(defPrefixes, BinaryPrefix.values());
        DEFAULT_PREFIXES = Set.copyOf(defPrefixes);
    }

    /**
     * Constructor for coherently derived SI units.
     * 
     * @param symbols the symbols representing the coherently derived SI unit
     */
    private SiDerivedUnit(String... symbols) {
        this.symbols = List.of(symbols);
    }

    @Override
    public Map<Unit, Integer> baseUnits() {
        if (baseUnits == null) {
            baseUnits = switch (this) {
                case BECQUEREL -> Map.of(SiBaseUnit.SECOND, -1);
                case COULOMB -> Map.of(SiBaseUnit.SECOND, 1, SiBaseUnit.AMPERE, 1);
                case DEGREE_CELSIUS -> Map.of(SiBaseUnit.KELVIN, 1);
                case FARAD -> Map.of(SiBaseUnit.KILOGRAM, -1, SiBaseUnit.METER, -2, SiBaseUnit.SECOND, 4, SiBaseUnit.AMPERE, 2);
                case GRAY -> Map.of(SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2);
                case HENRY -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2, SiBaseUnit.AMPERE, -2);
                case HERTZ -> Map.of(SiBaseUnit.SECOND, -1);
                case JOULE -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2);
                case KATAL -> Map.of(SiBaseUnit.MOLE, 1, SiBaseUnit.SECOND, -1);
                case LUMEN -> Map.of(SiBaseUnit.CANDELA, 1);
                case LUX -> Map.of(SiBaseUnit.METER, -2, SiBaseUnit.CANDELA, 1);
                case NEWTON -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 1, SiBaseUnit.SECOND, -2);
                case OHM -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -3, SiBaseUnit.AMPERE, -2);
                case PASCAL -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, -1, SiBaseUnit.SECOND, -2);
                case RADIAN -> Map.of();
                case SIEMENS -> Map.of(SiBaseUnit.KILOGRAM, -1, SiBaseUnit.METER, -2, SiBaseUnit.SECOND, 3, SiBaseUnit.AMPERE, 2);
                case SIEVERT -> Map.of(SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2);
                case STERADIAN -> Map.of();
                case TESLA -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.SECOND, -2, SiBaseUnit.AMPERE, -1);
                case VOLT -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -3, SiBaseUnit.AMPERE, -1);
                case WATT -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -3);
                case WEBER -> Map.of(SiBaseUnit.KILOGRAM, 1, SiBaseUnit.METER, 2, SiBaseUnit.SECOND, -2, SiBaseUnit.AMPERE, -1);
            };
        }
        return baseUnits;
    }

    @Override
    public double convertToBaseUnits(double value) {
        return switch (this) {
            case DEGREE_CELSIUS -> value + 273.15;
            default -> value; // the factor for all units except degree celsius is 1
        };
    }

    @Override
    public double convertFromBaseUnits(double value) {
        return switch (this) {
            case DEGREE_CELSIUS -> value - 273.15;
            default -> value; // the factor for all units except degree celsius is 1
        };
    }

    @Override public List<String> symbols() { return symbols; }
    @Override public boolean prefixAllowed(String symbol) { return true; }
    @Override public boolean isConversionLinear() { return this != DEGREE_CELSIUS; }
    @Override public double factor(String symbol) { return this == DEGREE_CELSIUS ? Double.NaN : 1; }
    @Override public Set<UnitPrefix> prefixes() { return DEFAULT_PREFIXES; }
    @Override public boolean isBasic() { return false; }
    @Override public Set<Unit> compatibleUnits() { return COMPATIBLE_UNITS; }
}
