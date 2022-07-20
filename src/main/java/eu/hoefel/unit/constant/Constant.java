package eu.hoefel.unit.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import eu.hoefel.jatex.LatexPackage;
import eu.hoefel.jatex.LatexPreambleEntry;
import eu.hoefel.jatex.Texable;
import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;
import eu.hoefel.utils.Maths;
import eu.hoefel.utils.Regexes;
import eu.hoefel.utils.Strings;

/**
 * Class for generic (scientific) constants. These can be, but not necessarily,
 * constants expressible in SI units.
 * 
 * @author Udo Hoefel
 */
public interface Constant extends Texable {

    /**
     * Gets the numeric value of the constant.
     * 
     * @return the numeric value
     */
    public double value();

    /**
     * Gets the uncertainty of the constant. Usually, this will be one standard
     * deviation. If multiple constants are chained, a Gaussian error distribution
     * for each of the uncertainties is assumed.
     * 
     * @return the uncertainty of the constant
     */
    public double uncertainty();

    /**
     * Gets the units of the constant. The format of the units is of the following
     * format: All separate units are separated by a space, prefixes have to be
     * prepended (without a separating space) to their unit, exponents have to be
     * specified by appending (without a separating space) "^" plus the value of the
     * exponent to the unit. Example: "km^2 s^-1".
     * 
     * @return the units of the constant
     */
    public Unit unit();

    /**
     * Creates a new generic constant with {@code value}, no unit and no
     * uncertainty.
     * 
     * @param value the value
     * @return a new generic constant
     */
    public static Constant of(double value) {
        return of(value, 0, Units.EMPTY_UNIT);
    }

    /**
     * Creates a new generic constant with {@code value}, no uncertainty and
     * {@code units}.
     * 
     * @param value       the value
     * @param uncertainty the uncertainty (one standard deviation) of the constant
     * @param units       the units, e.g. "N m^-2". The units needs to be separated
     *                    by spaces, potential exponents need to be appended to the
     *                    unit following a "^". May not be null.
     * @param extraUnits  the additional units to use for the conversion, not null
     * @return a new generic constant
     */
    public static Constant of(double value, double uncertainty, String units, Unit[]... extraUnits) {
        Objects.requireNonNull(units);
        Objects.requireNonNull(extraUnits);

        return of(value, uncertainty, Unit.of(units, Units.flattenUnits(extraUnits)));
    }

    /**
     * Creates a new generic constant with {@code value}, no uncertainty and
     * {@code unit}.
     * 
     * @param value       the value
     * @param uncertainty the uncertainty (one standard deviation)
     * @param unit        the unit, not null
     * @return a new generic constant
     */
    public static Constant of(double value, double uncertainty, Unit unit) {
        Objects.requireNonNull(unit);

        double absUncertainty = Math.abs(uncertainty);
        return new Constant() {
            @Override public double value() { return value; }
            @Override public double uncertainty() { return absUncertainty; }
            @Override public Unit unit() { return unit; }
            @Override public String toString() { return Constant.toString(this); }
            @Override public boolean equals(Object other) {
                if (this == other) return true;
                if (!(other instanceof Constant oc)) return false;
                return Double.doubleToLongBits(value()) == Double.doubleToLongBits(oc.value())
                        && Double.doubleToLongBits(uncertainty()) == Double.doubleToLongBits(oc.uncertainty())
                        && Objects.equals(unit(), oc.unit());
            }
            @Override public int hashCode() { return Objects.hash(value(), uncertainty(), unit()); }
        };
    }

    /**
     * Creates a new generic constant with a value, no uncertainty and potentially a
     * unit.
     * <p>
     * For example:<br>
     * {@code Constant.of("3m^2");} or <br>
     * {@code Constant.of("3.435789 s");} or <br>
     * 
     * @param str        the string containing a number and potentially a unit, not
     *                   null
     * @param extraUnits the additional units to use for the conversion, not null
     * @return a new generic constant
     */
    public static Constant of(String str, Unit[]... extraUnits) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(extraUnits);

        String trimmed = str.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Found no valid number for the constant!");
        }

        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (!Character.isDigit(c) && c != '.') {
                String number = trimmed.substring(0, i);
                if (!Maths.isDouble(number)) {
                    throw new IllegalArgumentException("Found no valid number for the constant!");
                }
                String unit = trimmed.substring(i);

                if (!Units.isValid(unit, extraUnits)) {
                    throw new IllegalArgumentException("Found no valid unit!");
                }
                return Constant.of(Double.valueOf(number), 0, Unit.of(unit, extraUnits));
            }
        }

        // we know by here that we have only a number
        return Constant.of(Double.valueOf(trimmed));
    }

    /**
     * Checks whether the given string can be successfully converted into a
     * {@link Constant}. Note that this does not check for uncertainties in the
     * string.
     * <p>
     * Examples:<br>
     * {@code Constant.isConstant("3m^2")} will yield {@code true}<br>
     * {@code Constant.isConstant("3±4s^2")} will yield {@code false}<br>
     * 
     * @param str        the string to check. Needs to have a number and optionally
     *                   a unit. May not be null.
     * @param extraUnits the additional units to use for the conversion, not null
     * @return true if the given string can be converted to a {@link Constant}
     */
    public static boolean isConstant(String str, Unit[]... extraUnits) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(extraUnits);

        String trimmed = str.trim();
        if (trimmed.isEmpty()) return false;

        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (!Character.isDigit(c) && c != '.') {
                String number = trimmed.substring(0, i);
                if (!Maths.isDouble(number)) {
                    return false;
                }
                String unit = trimmed.substring(i);
                return Units.isValid(unit, extraUnits);
            }
        }

        return true;
    }

    /**
     * Helper method for toString() for implementing classes.
     * 
     * @param constant the constant, not null
     * @return the constant in a human readable format
     */
    public static String toString(Constant constant) {
        Objects.requireNonNull(constant);

        String ret = (Double.isNaN(constant.value()) ? ""
                : constant.value() + Strings.SMALL_NON_BREAKABLE_SPACE + "±" + Strings.SMALL_NON_BREAKABLE_SPACE
                        + constant.uncertainty()).toLowerCase(Locale.ENGLISH);
        if (!constant.unit().symbols().get(0).isEmpty()) {
            ret = "(" + ret + ")" + Strings.SMALL_NON_BREAKABLE_SPACE + constant.unit().symbols().get(0);
        }

        return ret;
    }

    /**
     * Checks whether the current constant is equivalent (that is, after converting
     * the value and the uncertainty to the unit of the other constant their numeric
     * vales match their counterparts from the other constant) to the specified
     * other constant
     * 
     * @param constant the constant to check, not null
     * @return true if constants are equal
     */
    default boolean equivalent(Constant constant) {
        Objects.requireNonNull(constant);

        return Units.convertible(unit(), constant.unit()) 
                && Double.compare(value(), Units.convert(constant.value(), constant.unit(), unit())) == 0
                && Double.compare(uncertainty(), Units.convert(constant.uncertainty(), constant.unit(), unit())) == 0;

    }

    /**
     * Converts the constant to the specified units.
     * 
     * @param units      the target units, e.g. "N m^-2". The units needs to be
     *                   separated by spaces, potential exponents need to be
     *                   appended to the unit following a "^". May not be null.
     * @param extraUnits the additional units to use for the conversion, not null
     * @return the constant in {@code value} {@code units}, in the sense of, e.g.,
     *         "km in units of Angstrom"
     */
    default Constant inUnitsOf(String units, Unit[]... extraUnits) {
        Objects.requireNonNull(units);
        Objects.requireNonNull(extraUnits);

        return inUnitsOf(Unit.of(units, Units.flattenUnits(extraUnits)));
    }

    /**
     * Converts the constant to the specified unit.
     * 
     * @implSpec The default implementation converts the current constant to the
     *           target units, assuming both the units of the current constant and
     *           the target units are compatible units.
     * @param unit the target unit, not null
     * @return the constant in {@code units}, in the sense of, e.g., "km in units of
     *         Angstrom"
     */
    default Constant inUnitsOf(Unit unit) {
        Objects.requireNonNull(unit);
        return of(Units.convert(value(), unit(), unit),  Units.convert(uncertainty(), unit(), unit), unit);
    }

    /**
     * Checks whether a conversion of the constant into the target units is
     * feasible.
     * 
     * @param target the target units, e.g. "N m^-2". The units needs to be
     *               separated by spaces, potential exponents need to be appended to
     *               the unit following a "^". May not be null.
     * @param extraUnits the additional units to use for the conversion, not null
     * @return true if the conversion can be done
     */
    default boolean convertible(String target, Unit[]... extraUnits) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(extraUnits);

        return convertible(Unit.of(target, extraUnits));
    }

    /**
     * Checks whether a conversion of the constant into the target unit is feasible.
     * 
     * @implSpec The default implementation performs the check assuming both the
     *           current constant and the target units are compatible SI units.
     * @param target the target unit, not null
     * @return true if the conversion can be done
     */
    default boolean convertible(Unit target) {
        Objects.requireNonNull(target);
        return Units.convertible(unit(), target);
    }

    /**
     * Adds {@code value to the constant}.
     * 
     * @param value the value to add
     * @return the constant resulting from the sum
     */
    default Constant add(double value) {
        return of(value() + value, uncertainty(), unit());
    }

    /**
     * Subtracts {@code value from the constant}.
     * 
     * @param value the value to subtract
     * @return the constant resulting from the sum
     */
    default Constant sub(double value) {
        return of(value() - value, uncertainty(), unit());
    }

    /**
     * Multiplies {@code constant} to the current constant, respecting units and
     * uncertainties.
     * 
     * @implSpec The default implementation assumes both the uncertainty of the
     *           current constant, and the specified {@code constant} are each one
     *           standard deviation of a Gaussian distribution.
     * @param constant the constant to multiply with, not null
     * @return the constant resulting from the product
     */
    default Constant mul(Constant constant) {
        Objects.requireNonNull(constant);
        return mul(constant.value(), constant.uncertainty(), constant.unit());
    }

    /**
     * Multiplies the current constant with the {@code value} in {@code unit}. The
     * new uncertainty is calculated via standard Gaussian error propagation.
     * 
     * @implSpec The default implementation assumes both the uncertainty of the
     *           current constant, and the specified {@code uncertainty} are each
     *           one standard deviation of a Gaussian distribution.
     * @param value       the numeric value to multiply with
     * @param uncertainty the standard deviation
     * @param unit        the unit to multiply with, not null
     * @return the constant resulting from the product
     */
    default Constant mul(double value, double uncertainty, Unit unit) {
        Objects.requireNonNull(unit);

        double resultingValue = value() * value;
        double resultingUncertainty = resultingValue * Math.sqrt(Math.pow(uncertainty() / value(), 2) + Math.pow(uncertainty / value, 2));
        Unit resultingUnit = Unit.of(unit().symbols().get(0) + Strings.NON_BREAKABLE_SPACE + unit.symbols().get(0));
        return of(resultingValue, resultingUncertainty, resultingUnit);
    }

    /**
     * Divides the current constant by {@code constant}, respecting units and
     * uncertainties.
     * 
     * @implSpec The default implementation assumes both the uncertainty of the
     *           current constant, and the specified {@code constant} are each one
     *           standard deviation of a Gaussian distribution.
     * @param constant the constant to divide with, not null
     * @return the constant resulting from the division
     */
    default Constant div(Constant constant) {
        Objects.requireNonNull(constant);
        return div(constant.value(), constant.uncertainty(), constant.unit());
    }

    /**
     * Divides the current constant by the {@code value} in {@code unit}. The new
     * uncertainty is calculated via standard Gaussian error propagation.
     * 
     * @implSpec The default implementation assumes both the uncertainty of the
     *           current constant, and the specified {@code uncertainty} are each
     *           one standard deviation of a Gaussian distribution.
     * @param value       the numeric value to divide by
     * @param uncertainty the standard deviation
     * @param unit        the unit to divide by, not null
     * @return the constant resulting from the division
     */
    default Constant div(double value, double uncertainty, Unit unit) {
        Objects.requireNonNull(unit);

        double resultingValue = value() / value;
        double resultingUncertainty = Math.abs(resultingValue) * Math.sqrt(Math.pow(uncertainty() / value(), 2) + Math.pow(uncertainty / value, 2));
        String[] unitParts = Regexes.ALL_SPACE.split(unit.symbols().get(0).trim());
        for (int i = 0; i < unitParts.length; i++) {
            String[] unitPower = Regexes.EXPONENT.split(unitParts[i]);
            if (unitPower.length == 1) {
                String unitPart = unitPower[0].trim();
                unitParts[i] = unitPart + (unitPart.isEmpty() ? "" : "^-1");
            } else if (unitPower[1].startsWith("-")) {
                unitPower[1] = unitPower[1].trim().replaceFirst("-", "");
                unitParts[i] = String.join("^", unitPower);
            } else {
                unitParts[i] = String.join("^-", unitPower);
            }
        }
        Unit invertedUnit = Unit.of(unit().symbols().get(0) + Strings.NON_BREAKABLE_SPACE + String.join(Strings.NON_BREAKABLE_SPACE, unitParts));
        return of(resultingValue, resultingUncertainty, invertedUnit);
    }

    /**
     * Powers the constant, i.e. the numeric value, the uncertainty, and the units.
     * 
     * @implSpec The default implementation assumes the uncertainty of the current
     *           constant is one standard deviation of a Gaussian distribution.
     * @param exponent the exponent
     * @return the power of the constant
     */
    default Constant pow(int exponent) {
        double resultingValue = Math.pow(value(), exponent);
        double resultingUncertainty = exponent * uncertainty() / value();
        String[] unitParts = Regexes.ALL_SPACE.split(unit().symbols().get(0).trim());
        for (int i = 0; i < unitParts.length; i++) {
            String[] unitPower = Regexes.EXPONENT.split(unitParts[i]);
            if (unitPower.length == 1) {
                unitParts[i] = unitPower[0] + "^2";
            } else {
                unitPower[1] = Integer.toString(exponent * Integer.parseInt(unitPower[1]));
                unitParts[i] = String.join("^", unitPower);
            }
        }
        Unit poweredUnits = Unit.of(String.join(Strings.NON_BREAKABLE_SPACE, unitParts).trim());
        return of(resultingValue, resultingUncertainty, poweredUnits);
    }

    /**
     * Gets the {@code n}th root of the numeric value, the uncertainty, and the
     * units. The unit exponents need to remain integers for this to work!
     * 
     * @implSpec The default implementation assumes the uncertainty of the current
     *           constant is one standard deviation of a Gaussian distribution.
     * @param n the root exponent
     * @return the nth root of the constant
     * @throws UnsupportedOperationException if taking the nth root of the constants
     *                                       units would yield non-integer exponents
     *                                       of the unit
     */
    default Constant root(int n) {
        double resultingValue = Math.pow(value(), 1.0 / n);
        double resultingUncertainty = (1.0 / n) * uncertainty() * Math.pow(value(), (1.0 / n) - 1);
        String[] unitParts = Regexes.ALL_SPACE.split(unit().symbols().get(0).trim());
        for (int i = 0; i < unitParts.length; i++) {
            String[] unitPower = Regexes.EXPONENT.split(unitParts[i]);
            if (unitPower.length == 1) {
                unitParts[i] = unitPower[0] + "^2";
            } else {
                double unitExponent = Double.parseDouble(unitPower[1]) / n;
                if (unitExponent == 1) {
                    unitParts[i] = unitPower[0];
                } else if (Double.isFinite(unitExponent) && Double.compare(unitExponent, StrictMath.rint(unitExponent)) == 0) {
                    unitPower[1] = Integer.toString((int) unitExponent);
                    unitParts[i] = String.join("^", unitPower);
                } else {
                    throw new UnsupportedOperationException(String.format(Locale.ENGLISH,
                            "Taking the %s root of %s would yield a non-integer exponent (%f) for %s. "
                                    + "Taking the root of a Constant is (currently) only allowed if the unit exponents stay integers.",
                                    Strings.ordinalNumeral(n), unit().symbols().get(0), unitExponent, unitPower[0]));
                }
            }
        }
        Unit rootedUnit = Unit.of(String.join(Strings.NON_BREAKABLE_SPACE, unitParts).trim());
        return of(resultingValue, resultingUncertainty, rootedUnit);
    }

    /**
     * @implSpec The default implementation assumes the units can be expressed by
     *           the {@code siunitx} package.
     */
    @Override
    default List<LatexPackage> neededPackages() {
        return List.of(new LatexPackage("siunitx"));
    }

    /**
     * @implSpec The default implementation assumes the units can be expressed by
     *           the {@code siunitx} package.
     */
    @Override
    default List<LatexPreambleEntry> preambleExtras() {
        var latexInfos = getLatexSiUnit(unit());
        List<LatexPreambleEntry> preambleExtras = new ArrayList<>();
        for (String siUnit : latexInfos.keySet()) {
            preambleExtras.add(new LatexPreambleEntry("\\DeclareSIUnit\\" + siUnit + "{" + siUnit + "}"));
        }
        return List.copyOf(preambleExtras);
    }

    /**
     * @implSpec The default implementation assumes the units can be expressed by
     *           the {@code siunitx} package.
     */
    @Override
    default List<String> latexCode() {
        var latexInfos = getLatexSiUnit(unit());
        List<String> units = new ArrayList<>(latexInfos.size());
        for (Entry<String, Integer> siUnit : latexInfos.entrySet()) {
            if (siUnit.getValue() == 1) {
                units.add("\\" + siUnit.getKey());
            } else {
                units.add("\\" + siUnit.getKey() + "\\tothe{" + siUnit.getValue() + "}");
            }
        }

        String cleanUnits = String.join(" ", units);

        int valueMantissa = 0;
        String valueText = Double.toString(value()).toLowerCase(Locale.ENGLISH);
        if (valueText.contains("e")) {
            valueMantissa = Integer.parseInt(Regexes.E.split(valueText)[1]);
        }
        
        if (Double.isNaN(value())) {
            // only unit
            return List.of("\\si{" + cleanUnits + "}");
        } else if (uncertainty() == 0 || Double.isNaN(uncertainty())) {
            if (valueText.contains("e")) {
                // remove trailing zeros before the "e"
                String[] split = Regexes.E.split(valueText);
                split[0] = split[0].contains(".") ? split[0].replaceAll("0*$", "").replaceAll("\\.$", "") : split[0];
                valueText = String.join("e", split);
            }

            // remove trailing zeros
            valueText = valueText.contains(".") ? valueText.replaceAll("0*$", "").replaceAll("\\.$", "") : valueText;

            return List.of("\\SI{" + valueText + "}{" + cleanUnits + "}");
        }

        int uncertaintyMantissa = 0;
        String uncertaintyText = Double.toString(uncertainty()).toLowerCase(Locale.ENGLISH);
        if (uncertaintyText.contains("e")) {
            uncertaintyMantissa = Integer.parseInt(Regexes.E.split(uncertaintyText)[1]);
        }

        int mantissa = Math.max(valueMantissa, uncertaintyMantissa);

        if (mantissa == 0 && valueMantissa == uncertaintyMantissa) {
            // remove trailing zeros
            valueText = valueText.contains(".") ? valueText.replaceAll("0*$", "").replaceAll("\\.$", "") : valueText;
            uncertaintyText = uncertaintyText.contains(".") ? uncertaintyText.replaceAll("0*$", "").replaceAll("\\.$", "") : uncertaintyText;

            return List.of("\\SI[separate-uncertainty=true]{" + valueText + "\\pm" + uncertaintyText + "}{" + cleanUnits + "}");
        }

        if (mantissa > valueMantissa) {
            BigDecimal valueFactor = new BigDecimal(Regexes.E.split(Double.toString(value()).toLowerCase(Locale.ENGLISH))[0]);
            String rescaledText;
            if (valueMantissa - uncertaintyMantissa > 0) {
                BigDecimal tenToThe = BigDecimal.TEN.pow(valueMantissa - uncertaintyMantissa);
                rescaledText = valueFactor.multiply(tenToThe).toPlainString();
            } else {
                BigDecimal tenToThe = BigDecimal.TEN.pow(Math.abs(valueMantissa - uncertaintyMantissa));
                rescaledText = valueFactor.divide(tenToThe).toPlainString();
            }

            // remove trailing zeros
            rescaledText = rescaledText.contains(".") ? rescaledText.replaceAll("0*$", "").replaceAll("\\.$", "") : rescaledText;
            uncertaintyText = uncertaintyText.contains(".") ? uncertaintyText.replaceAll("0*$","").replaceAll("\\.$","") : uncertaintyText;

            return List.of("\\SI[separate-uncertainty=true]{" + rescaledText + "\\pm" + uncertaintyText + "}{" + cleanUnits + "}");
        } else if (mantissa > uncertaintyMantissa) {
            String rescaledValueText = Regexes.E.split(Double.toString(value()).toLowerCase(Locale.ENGLISH))[0];

            BigDecimal uncFactor = new BigDecimal(Regexes.E.split(Double.toString(uncertainty()).toLowerCase(Locale.ENGLISH))[0]);
            String rescaledUncertaintyText;
            if (uncertaintyMantissa - valueMantissa > 0) {
                BigDecimal tenToThe = BigDecimal.TEN.pow(uncertaintyMantissa - valueMantissa);
                rescaledUncertaintyText = uncFactor.multiply(tenToThe).toPlainString();
            } else {
                BigDecimal tenToThe = BigDecimal.TEN.pow(Math.abs(uncertaintyMantissa - valueMantissa));
                rescaledUncertaintyText = uncFactor.divide(tenToThe).toPlainString();
            }

            // remove trailing zeros
            rescaledValueText = rescaledValueText.contains(".") ? rescaledValueText.replaceAll("0*$","").replaceAll("\\.$","") : rescaledValueText;

            rescaledUncertaintyText = rescaledUncertaintyText.contains(".") ? rescaledUncertaintyText.replaceAll("0*$","").replaceAll("\\.$","") : rescaledUncertaintyText;

            return List.of("\\SI[separate-uncertainty=true]{" + rescaledValueText + "\\pm" + rescaledUncertaintyText + (mantissa == 0 ? "" : "e" + mantissa) + "}{" + cleanUnits + "}");
        } else {
            String rescaledText = Regexes.E.split(Double.toString(value()).toLowerCase(Locale.ENGLISH))[0];

            // remove trailing zeros
            rescaledText = rescaledText.contains(".") ? rescaledText.replaceAll("0*$","").replaceAll("\\.$","") : rescaledText;
            uncertaintyText = uncertaintyText.contains(".") ? uncertaintyText.replaceAll("0*$","").replaceAll("\\.$","") : uncertaintyText;

            return List.of("\\SI[separate-uncertainty=true]{" + rescaledText + "\\pm" + uncertaintyText + "}{" + cleanUnits + "}");
        }
    }

    /**
     * Gets the infos required for texing the constant.
     * 
     * @param unit the unit to prepare
     * @return the info necessary for LaTeX
     */
    private static Map<String, Integer> getLatexSiUnit(Unit unit) {
        String[] splitUnits = Regexes.ALL_SPACE.split(unit.symbols().get(0));
        Map<String, Integer> siUnits = new LinkedHashMap<>();
        for (String unitsPart : splitUnits) {
            String[] dimensionPower = Regexes.EXPONENT.split(unitsPart);
            int extraExponent = dimensionPower.length == 1 ? 1 : Integer.parseInt(dimensionPower[1]);
            siUnits.compute(dimensionPower[0], (k, v) -> v == null ? extraExponent : v + extraExponent);
        }

        Map<String, Integer> ret = new LinkedHashMap<>(siUnits);
        for (Entry<String, Integer> siUnit : siUnits.entrySet()) {
            if (siUnit.getValue() == 0) {
                ret.remove(siUnit.getKey());
            }
        }

        return ret;
    }
}
