package eu.hoefel.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

import eu.hoefel.unit.binary.BinaryUnit;
import eu.hoefel.unit.context.PhysicsContext;
import eu.hoefel.unit.level.LevelUnit;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiCommonUnit;
import eu.hoefel.unit.si.SiDerivedUnit;
import eu.hoefel.utils.Regexes;
import eu.hoefel.utils.Strings;

/**
 * Helper methods and fields for unit handling. Mainly of interest if you are
 * going to overwrite some of the default methods from {@link Unit} or
 * implementing new units.
 * 
 * @author Udo Hoefel
 */
public final class Units {

    /** Hiding any public constructor. */
    private Units() {
        throw new IllegalStateException("This is a pure utility class!");
    }

    /** Empty units, mainly for use within (some) implementations of {@link Unit}. */
    public static final Set<Unit> EMPTY_UNITS = Set.of();

    /**
     * Empty unit prefixes, mainly for use within (some) implementations of
     * {@link Unit}.
     */
    public static final Set<UnitPrefix> EMPTY_PREFIXES = Set.of();

    /** The "Unit" of "no unit", i.e., it is a dimensionless unit. */
    public static final Unit EMPTY_UNIT = new Unit() {
        @Override public List<String> symbols() { return List.of(""); }
        @Override public Set<UnitPrefix> prefixes() { return EMPTY_PREFIXES; }
        @Override public boolean prefixAllowed(String symbol) { return false; }
        @Override public boolean isBasic() { return true; }
        @Override public Map<Unit, Integer> baseUnits() { return Map.of(); }
        @Override public double factor(String symbol) { return 1; }
        @Override public double convertToBaseUnits(double value) { return value; }
        @Override public double convertFromBaseUnits(double value) { return value; }
        @Override public boolean isConversionLinear() { return true; }
        @Override public Set<Unit> compatibleUnits() { return EMPTY_UNITS; }
        
        @Override
        public final String toString() {
            return "EmptyUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
                    + isConversionLinear() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits() + "]";
        }

        @Override
        public final int hashCode() {
            return Objects.hash(baseUnits(), isConversionLinear(), compatibleUnits(), isBasic(), prefixes(), symbols());
        }

        @Override
        public final boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof Unit other) {
                return Objects.equals(baseUnits(), other.baseUnits()) && isConversionLinear() == other.isConversionLinear()
                        && Objects.equals(compatibleUnits(), other.compatibleUnits()) && isBasic() == other.isBasic()
                        && Objects.equals(prefixes(), other.prefixes()) && Objects.equals(symbols(), other.symbols());
            }
            return false;
        }
    };

    /** Represents a "prefix" with no symbol and a factor of 1. */
    public static final UnitPrefix IDENTITY_PREFIX = new UnitPrefix() {
        @Override public List<String> symbols() { return List.of(""); }
        @Override public double factor() { return 1; }
        @Override public String toString() { return "IDENTITY_PREFIX[symbols=" + symbols() + ", factor=" + factor() + "]"; }
        @Override public int hashCode() { return Objects.hash(symbols(), factor()); }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof UnitPrefix other) {
                return Objects.equals(symbols(), other.symbols()) && factor() == other.factor();
            }
            return false;
        }
    };

    /**
     * This record holds the information of the units and the accompanying extra
     * units to be used for parsing. This is useful for maps, in which the units
     * should serve as the key, but the units available for parsing might change the
     * meaning of the unit, i.e., it is used for {@link Units#specialUnits}.
     * 
     * @param units      the units, e.g. "kg^2 m s^-1"
     * @param extraUnits the additional units to use for parsing the units
     * 
     * @author Udo Hoefel
     */
    private static final record StringBasedUnit(String units, Set<Unit> extraUnits) {}

    /**
     * Holds the indices for a range within a string.
     * 
     * @param from the first index
     * @param to   the end index, inclusive
     * 
     * @author Udo Hoefel
     */
    public static final record StringRange(int from, int to) implements Comparable<StringRange> {

        /** Compares two StringRanges by their starting index, and then by their length. */
        private static final Comparator<StringRange> COMPARATOR = Comparator.comparingInt(StringRange::from)
                                                                            .thenComparingInt(StringRange::length);

        /**
         * Constructs a new string range.
         * 
         * @param from the first index
         * @param to   the end index, inclusive
         * @throws IllegalArgumentException if {@code from} is larger than {@code to}
         */
        public StringRange {
            if (from > to) {
                throw new IllegalArgumentException("'from' must be smaller than or equal to 'to'!");
            }
        }

        @Override
        public final int compareTo(StringRange sr) {
            return COMPARATOR.compare(this, sr);
        }

        /**
         * Gets the length of the current string range.
         * 
         * @return the length of the range
         */
        public final int length() {
            return to - from + 1; // +1 due to from being inclusive
        }

        /**
         * Checks whether the current string range comprises the other string range.
         * 
         * @param sr the other string range
         * @return true if the current range comprises the other range
         */
        public boolean comprises(StringRange sr) {
            return (from <= sr.from() && to > sr.to()) || (from < sr.from() && to >= sr.to());
        }

        /**
         * Checks whether the current string intersects with the given string range.
         * 
         * @param sr the other string range
         * @return true if the string ranges intersect
         */
        public final boolean intersects(StringRange sr) {
            return to >= sr.from && from <= sr.to;
        }
    }

    /**
     * Map for holding simplifications of specified string based units, ensuring that
     * one does not need to redo the simplification too often, as
     * {@link #searchSimplificationSpace(String, Set, Unit[]...)} is an expensive
     * operation.
     */
    private static final ConcurrentMap<StringBasedUnit, String> simplifiedUnits = new ConcurrentHashMap<>();

    /**
     * The default units used within {@link Unit}. These include all of
     * {@link SiBaseUnit}, {@link SiDerivedUnit}, {@link SiCommonUnit} and
     * {@link BinaryUnit}. Might also be useful within (some) implementations of
     * {@link Unit}.
     */
    public static final Set<Unit> DEFAULT_UNITS;

    /**
     * The default unit prefixes used within {@link Unit}. Might also be useful
     * within (some) implementations of {@link Unit}.
     */
    public static final Set<UnitPrefix> DEFAULT_PREFIXES;

    /**
     * The default parser. Using this specific parser allows certain optimizations
     * that can speed up parsing operations notably. See {@link #collectInfo(String,
     * Unit[][])} for more information.
     */
    public static final BiFunction<String, Unit[][], NavigableMap<StringRange, UnitInfo>> DEFAULT_PARSER = Units::collectInfo;

    static {
        Set<Unit> defUnits = new LinkedHashSet<>();
        Collections.addAll(defUnits, SiBaseUnit.values());
        Collections.addAll(defUnits, SiDerivedUnit.values());
        Collections.addAll(defUnits, SiCommonUnit.values());
        Collections.addAll(defUnits, BinaryUnit.values());
        Collections.addAll(defUnits, LevelUnit.values());
        defUnits.add(EMPTY_UNIT);
        DEFAULT_UNITS = Collections.unmodifiableSet(defUnits);

        Set<UnitPrefix> defPrefixes = new LinkedHashSet<>();
        for (Unit refUnit : DEFAULT_UNITS) {
            defPrefixes.addAll(refUnit.prefixes());
        }
        DEFAULT_PREFIXES = Collections.unmodifiableSet(defPrefixes);
    }

    /** Key for the exponent in maps. */
    private static final String EXPONENT = "exponent";

    /** Key for the factor in maps. */
    private static final String FACTOR = "factor";

    /**
     * Compares two symbols, first by checking if uppercase letters get used (if so,
     * the symbol is considered smaller), second by checking the symbol length and
     * finally by lexicographical comparison. Note that there is a small, but
     * important difference to {@link #comparatorForEquivalentSymbols(String)}, as the
     * latter checks first if one of the two strings was given in the original
     * units, which this one does not.
     */
    public static final Comparator<String> COMPARATOR_FOR_UNIT_ORDERING = (symbol1, symbol2) -> {
        boolean refUnit1HasUppercase = symbol1.chars().anyMatch(c -> Character.isLetter(c) && Character.isUpperCase(c));
        boolean refUnit2HasUppercase = symbol2.chars().anyMatch(c -> Character.isLetter(c) && Character.isUpperCase(c));

        // pick the one with uppercase if possible
        if (refUnit1HasUppercase && !refUnit2HasUppercase) {
            return -1;
        } else if (!refUnit1HasUppercase && refUnit2HasUppercase) {
            return 1;
        }

        // pick the shorter one, if they differ in length
        if (symbol1.length() < symbol2.length()) {
            return -1;
        } else if (symbol1.length() > symbol2.length()) {
            return 1;
        }

        return symbol1.compareTo(symbol2);
    };

    /**
     * Compares two symbols, first by checking if one of the two symbols was
     * contained in the original units, then by checking if uppercase letters get
     * used (if so, the symbol is considered smaller), second by checking the symbol
     * length and finally by lexicographical comparison.
     * 
     * @param origin the original units, not null
     * @return the Comparator
     */
    public static final Comparator<String> comparatorForEquivalentSymbols(String origin) {
        Objects.requireNonNull(origin);

        return (symbol1, symbol2) -> {
            boolean originalUnitsHaveSymbol1 = origin.contains(symbol1);
            boolean originalUnitsHaveSymbol2 = origin.contains(symbol2);

            if (originalUnitsHaveSymbol1 && !originalUnitsHaveSymbol2) {
                return -1;
            } else if (!originalUnitsHaveSymbol1 && originalUnitsHaveSymbol2) {
                return 1;
            }
            return COMPARATOR_FOR_UNIT_ORDERING.compare(symbol1, symbol2);
        };
    }

    /**
     * Represents an unknown unit.
     * <p>
     * There is one catch with the unit as returned here: The unit cannot reference
     * itself in the {@link Unit#baseUnits()} as this would produce a stackoverflow
     * error. Hence, an "inner" unit is created that is identical to the returned
     * unknown ("outer") unit, except that it has no base units (but serves as the
     * base unit to the returned unit).
     * 
     * @param unit the symbol of the unknown unit, not null
     * @return the unknown unit
     */
    public static final Unit unknownUnit(String unit) {
        Objects.requireNonNull(unit);

        // This is a bit ugly:
        // As we cannot sensibly reference the unit itself in baseUnits() as this
        // produces a stackoverflow error, we have an "inner" unit that basically
        // behaves as the "outer" unknown unit, except that it has no base units, i.e.,
        // it is not reasonable to ask the base units for the base units in this
        // particular instance.
        Unit innerUnit = new Unit() {
            @Override public List<String> symbols() { return List.of(unit); }
            @Override public Set<UnitPrefix> prefixes() { return EMPTY_PREFIXES; }
            @Override public boolean prefixAllowed(String symbol) { return false; }
            @Override public boolean isBasic() { return true; }
            @Override public Map<Unit, Integer> baseUnits() { return Map.of(); } // Note here the empty base units
            @Override public double factor(String symbol) { return 1; }
            @Override public double convertToBaseUnits(double value) { return value; }
            @Override public double convertFromBaseUnits(double value) { return value; }
            @Override public boolean isConversionLinear() { return true; }
            @Override public Set<Unit> compatibleUnits() { return EMPTY_UNITS; }

            @Override
            public String toString() {
                return "UnknownUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
                        + isConversionLinear() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits() + "]";
            }

            @Override
            public int hashCode() {
                return Objects.hash(baseUnits(), isConversionLinear(), compatibleUnits(), isBasic(), prefixes(), symbols());
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj instanceof Unit other) {
                    return Objects.equals(baseUnits(), other.baseUnits()) && isConversionLinear() == other.isConversionLinear()
                            && Objects.equals(compatibleUnits(), other.compatibleUnits()) && isBasic() == other.isBasic()
                            && Objects.equals(prefixes(), other.prefixes()) && Objects.equals(symbols(), other.symbols());
                }
                return false;
            }
        };

        return new Unit() {
            @Override public List<String> symbols() { return List.of(unit); }
            @Override public Set<UnitPrefix> prefixes() { return EMPTY_PREFIXES; }
            @Override public boolean prefixAllowed(String symbol) { return false; }
            @Override public boolean isBasic() { return true; }
            @Override public Map<Unit, Integer> baseUnits() { return Map.of(innerUnit, 1); } // Note here the reference to the "inner" unit
            @Override public double factor(String symbol) { return 1; }
            @Override public double convertToBaseUnits(double value) { return value; }
            @Override public double convertFromBaseUnits(double value) { return value; }
            @Override public boolean isConversionLinear() { return true; }
            @Override public Set<Unit> compatibleUnits() { return EMPTY_UNITS; }

            @Override
            public String toString() {
                return "UnknownUnit[symbols=" + symbols() + ", prefixes=" + prefixes() + ", isBasic=" + isBasic() + ", canUseFactor="
                        + isConversionLinear() + ", baseUnits=" + baseUnits() + ", compatibleUnits=" + compatibleUnits() + "]";
            }

            @Override
            public int hashCode() {
                return Objects.hash(baseUnits(), isConversionLinear(), compatibleUnits(), isBasic(), prefixes(), symbols());
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj instanceof Unit other) {
                    return Objects.equals(baseUnits(), other.baseUnits()) && isConversionLinear() == other.isConversionLinear()
                            && Objects.equals(compatibleUnits(), other.compatibleUnits()) && isBasic() == other.isBasic()
                            && Objects.equals(prefixes(), other.prefixes()) && Objects.equals(symbols(), other.symbols());
                }
                return false;
            }
        };
    }

    /**
     * Prints the units in a sensible manner, i.e. it handles unknown units in a
     * useful way.
     * 
     * @param units the units to print, not null
     * @return the listed units with unknown units being indicated. If only a single
     *         unit is given, the square brackets are not printed around the unit's
     *         value
     */
    public static final String toString(Unit... units) {
        Objects.requireNonNull(units);

        StringBuilder sb = new StringBuilder();
        for (Unit unit : units) {
            if (sb.length() == 0 && units.length > 1) {
                sb.append("[");
            } else if (units.length > 1) {
                sb.append(", ");
            }
            String symbol = unit.symbols().get(0);
            if (unit.equals(unknownUnit(symbol))) {
                sb.append(symbol + " (unknown unit)");
            } else {
                sb.append(unit.symbols().get(0));
            }
        }
        if (units.length > 1) sb.append("]");
        return sb.toString();
    }

    /**
     * Gets the symbol for the given map.
     * 
     * @param info the map with the Units and their exponents, not null
     * @return the corresponding symbol
     */
    public static final String toSymbol(Map<Unit, Integer> info) {
        Objects.requireNonNull(info);

        StringBuilder sb = new StringBuilder();
        for (var unitInfo : info.entrySet()) {
            if (!sb.isEmpty()) sb.append(Strings.NON_BREAKABLE_SPACE);
            if (unitInfo.getValue() != 0) {
                sb.append(unitInfo.getKey().symbols().get(0));
                if (unitInfo.getValue() != 1) {
                    sb.append("^");
                    sb.append(unitInfo.getValue().toString());
                }
            }
        }
        return sb.toString();
    }

    /**
     * Calculates the symbol score for a concatenation of both given symbols,
     * wherein smaller returned values indicate preferable symbols (i.e. e.g., they
     * should have smaller (absolute) and positive exponents)
     * 
     * @param symbol1   the first symbol, e.g. "m"
     * @param symbol2   the second symbol, e.g. "s"
     * @param exponent1 the exponent corresponding to the first given symbol
     * @param exponent2 the exponent corresponding to the second given symbol
     * @return the corresponding symbol score, larger values corresponding to
     *         symbols considered more complex
     */
    private static final long calculateSymbolScore(String symbol1, String symbol2, int exponent1, int exponent2) {
        long score = Long.MIN_VALUE;
        if (symbol1.isEmpty() && symbol2.isEmpty()) return score; // no unit -> it doesn't get simpler
        if (!symbol1.isEmpty() && !symbol2.isEmpty()) score = 0L; // we prefer one unit over two

        if (!symbol1.isEmpty()) {
            double exponent = 2.0 * Math.abs(exponent1) + (Math.signum(exponent1) == -1 ? 1 : 0);
            score += (long) Math.pow(2, exponent);
        }

        if (!symbol2.isEmpty()) {
            double exponent = 2.0 * Math.abs(exponent2) + (Math.signum(exponent2) == -1 ? 1 : 0);
            score += (long) Math.pow(2, exponent);
        }

        return score;
    }

    /**
     * Collects the info necessary for further conversion operations for the
     * specified units. All separate units have to be separated by a space, prefixes
     * have to be prepended (without a separating space) to their unit, exponents
     * have to be specified by appending (without a separating space) "^" plus the
     * value of the exponent to the unit. Example: "km^2 s^-1". If additional units,
     * not (yet) in the standard implementations, are needed for the conversions,
     * they can be supplied via {@code extraUnits}.
     * 
     * @param units      the units, e.g. "kg^2 s^-1", not null
     * @param extraUnits additional units to check against, not null
     * @return info of the decoded units and their position in the given string
     */
    public static final NavigableMap<StringRange, UnitInfo> collectInfo(String units, Unit[]... extraUnits) {
        Objects.requireNonNull(units);
        Objects.requireNonNull(extraUnits);

        return provideUnitInfo(units, Units::checkUnits, extraUnits);
    }

    /**
     * Collects the info necessary for further conversion operations for the
     * specified unit. Compatible output to {@link #collectInfo(String, Unit[][])}.
     * 
     * @param unit the unit
     * @return info for further operations. The format represents the string range
     *         for which a match was found (which here corresponds to the whole
     *         symbol of the given unit) and the corresponding decoded
     *         {@link UnitInfo}
     */
    private static final NavigableMap<StringRange, UnitInfo> collectInfo(Unit unit) {
        // we just need that prefix in all further operations anyways to just give us a
        // factor of 1, which this does
        UnitPrefix prefix = IDENTITY_PREFIX;

        NavigableMap<StringRange, UnitInfo> map = new TreeMap<>();
        map.put(new StringRange(0, Math.max(0, unit.symbols().get(0).length() - 1)),
                new UnitInfo(prefix, unit, unit.symbols().get(0), 1));
        return Collections.unmodifiableNavigableMap(map);
    }

    /**
     * Collects the info necessary for further conversion operations for the
     * specified units. In contrast to {@link #collectInfo(String, Unit[]...)} this
     * adds an unknown unit if no matching unit is found. All separate units have to
     * be separated by a space, prefixes have to be prepended (without a separating
     * space) to their unit, exponents have to be specified by appending (without a
     * separating space) "^" plus the value of the exponent to the unit. Example:
     * "km^2 s^-1". If additional units, not (yet) in the standard implementations,
     * are needed for the conversions, they can be supplied via {@code extraUnits}.
     * 
     * @param units      the units, e.g. "kg^2 s^-1"
     * @param extraUnits additional units to check against
     * @return info of the decoded units and their position in the given string
     */
    private static final NavigableMap<StringRange, UnitInfo> collectInfoWithUnknownUnits(String units, Unit[]... extraUnits) {
        return provideUnitInfo(units, Units::checkUnitsWithUnknownUnit, extraUnits);
    }

    /**
     * Parses the given {@code units} with the specified parser. Note that calling
     * this method very often may inflict a notable performance penalty, as the
     * repeated splitting of (potentially the same input {@code units}) is
     * computationally quite expensive, even though the splitting patterns are
     * static. In these cases it may be helpful to consider using
     * {@link #provideUnitInfoWithRegexesAlreadyApplied(String, String[],
     * String[][], BiFunction, Unit[]...)} if the input {@code units} are identical
     * for multiple calls.
     * 
     * @param units      the full original units string, e.g. "kg^2 m^-1"
     * @param parser     the function to use for parsing
     * @param extraUnits the units that the parser may need (only necessary if the
     *                   {@link #DEFAULT_UNITS} are not sufficient)
     * @return the matched parts of the {@code units} decoded into their {link
     *         StringRange} and their corresponding {@link UnitInfo}
     */
    private static final NavigableMap<StringRange, UnitInfo> provideUnitInfo(String units,
            BiFunction<String[], Unit[][], UnitInfo> parser, Unit[]... extraUnits) {
        String[] unitsRaw = Regexes.ALL_SPACE.split(units.trim());
        String[][] unitPower = new String[unitsRaw.length][];
        for (int i = 0; i < unitsRaw.length; i++) {
            unitPower[i] = Regexes.EXPONENT.split(unitsRaw[i].trim());
        }
        return provideUnitInfoWithRegexesAlreadyApplied(units, unitsRaw, unitPower, parser, extraUnits);
    }

    /**
     * Parses the given strings with the specified parser. This method is necessary
     * as splitting the original {@code units} string (and each of the split parts
     * again being split at the exponent sign) for each unit to be parsed anew can
     * lead to a severe performance penalty.
     * 
     * @param units      the full original units string, e.g. "kg^2 m^-1"
     * @param unitsRaw   the original units string split at spaces, i.e. on the
     *                   example used for {@code units} this should be ["kg^2",
     *                   "m^-1"]
     * @param unitPower  each of the {@code unitsRaw} split at the exponent sign "^"
     * @param parser     the function to use for parsing
     * @param extraUnits the units that the parser may need (only necessary if the
     *                   {@link #DEFAULT_UNITS} are not sufficient)
     * @return the matched parts of the {@code units} decoded into their {link
     *         StringRange} and their corresponding {@link UnitInfo}
     */
    static final NavigableMap<StringRange, UnitInfo> provideUnitInfoWithRegexesAlreadyApplied(String units,
            String[] unitsRaw, String[][] unitPower, BiFunction<String[], Unit[][], UnitInfo> parser,
            Unit[]... extraUnits) {
        NavigableMap<StringRange, UnitInfo> unitInfo = new TreeMap<>();
        int position = 0;
        for (int i = 0; i < unitsRaw.length; i++) {
            int startIndex = units.indexOf(unitsRaw[i], position);
            position = startIndex + unitsRaw[i].length();
            var info = parser.apply(unitPower[i], extraUnits);
            if (info != null) unitInfo.put(new StringRange(startIndex, position), info);
        }
        return unitInfo;
    }

    /**
     * Collects the information required for conversions that require
     * non-multiplicative operations.
     * 
     * @param originInfos the info about the original units
     * @param targetInfos the info about the target units
     * @return the info containing the units, the symbol(s), and, on the
     *         innermost level, the exponent and factor information, accessible via
     *         {@value #EXPONENT} and {@value #FACTOR}
     */
    private static final Map<Unit, Map<String, Map<String, Double>>> collectNonMultiplicativeOperationInfo(Collection<UnitInfo> originInfos,
            Collection<UnitInfo> targetInfos) {
        Map<Unit, Map<String, Map<String, Double>>> effectiveTransformations = new LinkedHashMap<>();
        for (UnitInfo info : originInfos) {
            var unitInfos = effectiveTransformations.computeIfAbsent(info.unit(), s -> new LinkedHashMap<>());
            var symbolInfos = unitInfos.computeIfAbsent(info.symbol(), s -> new LinkedHashMap<>());
            symbolInfos.compute(EXPONENT, (k, v) -> (v == null ? 0 : v) + info.exponent());
            symbolInfos.compute(FACTOR,   (k, v) -> (v == null ? 1 : v) * info.prefix().factor());
        }
        for (UnitInfo info : targetInfos) {
            var unitInfos = effectiveTransformations.computeIfAbsent(info.unit(), s -> new LinkedHashMap<>());
            var symbolInfos = unitInfos.computeIfAbsent(info.symbol(), s -> new LinkedHashMap<>());
            symbolInfos.compute(EXPONENT, (k, v) -> (v == null ? 0 : v) - info.exponent());
            symbolInfos.compute(FACTOR,   (k, v) -> (v == null ? 1 : v) / info.prefix().factor());
        }
        return effectiveTransformations;
    }

    /**
     * Checks the {@code unit} versus the {@link Units#DEFAULT_UNITS} or the
     * given {@code extraUnits} and returns the first successful match.
     * 
     * @param unitParts  the first index contains the unit to check, e.g. "kg" or
     *                   "ms". Multiple units, as well as exponents, are not allowed
     *                   here. If unitParts is of length 2, the second part contains
     *                   information about the exponent
     * @param extraUnits additional units to check against
     * @return the unit info or null if no unit has been found
     */
    static final UnitInfo checkUnits(String[] unitParts, Unit[]... extraUnits) {
        Set<Unit> units;
        Set<UnitPrefix> prefixes;
        if (extraUnits.length == 0) {
            units = DEFAULT_UNITS;
            prefixes = DEFAULT_PREFIXES;
        } else if (extraUnits.length == 1 && extraUnits[0].length == 1) {
            units = Set.of(extraUnits[0][0]);
            prefixes = Set.copyOf(extraUnits[0][0].prefixes());
        } else {
            units = new LinkedHashSet<>();
            prefixes = new LinkedHashSet<>();
            Unit[] flatExtraUnits = flattenUnits(extraUnits);
            for (Unit singleUnit : flatExtraUnits) {
                units.add(singleUnit);
                units.addAll(singleUnit.compatibleUnits());
            }

            units.add(EMPTY_UNIT);

            for (Unit refUnit : units) {
                prefixes.addAll(refUnit.prefixes());
            }
        }

        for (Unit refUnit : units) {
            UnitInfo info = identifyUnitInfo(unitParts, refUnit, prefixes);
            if (info != null) return info;
        }

        return null;
    }

    /**
     * Checks the {@code unit} versus the {@link Units#DEFAULT_UNITS} or the
     * given {@code extraUnits} and returns the first successful match.
     * 
     * @param unitParts  the first index contains the unit to check, e.g. "kg" or
     *                   "ms". Multiple units, as well as exponents, are not allowed
     *                   here. If unitParts is of length 2, the second part contains
     *                   information about the exponent
     * @param extraUnits additional units to check against
     * @return the unit info
     */
    private static final UnitInfo checkUnitsWithUnknownUnit(String[] unitParts, Unit[]... extraUnits) {
        UnitInfo info = checkUnits(unitParts, extraUnits);
        if (info != null) return info;

        // if we get here, the unit is unknown
        int exponent = unitParts.length == 1 ? 1 : Integer.parseInt(unitParts[1]);
        return new UnitInfo(IDENTITY_PREFIX, unknownUnit(unitParts[0]), unitParts[0], exponent);
    }

    /**
     * Checks whether the given unit is used in {@code unit} and, if successful,
     * identifies the corresponding prefix.
     * 
     * @param unitParts  the first index contains the unit to check, e.g. "kg" or
     *                   "ms". Multiple units, as well as exponents, are not allowed
     *                   here. If unitParts is of length 2, the second part contains
     *                   information about the exponent
     * @param refUnit  the unit to check against
     * @param prefixes the prefixes to check
     * @return the unit info
     */
    private static final UnitInfo identifyUnitInfo(String[] unitParts, Unit refUnit, Set<UnitPrefix> prefixes) {
        List<String> symbols = refUnit.symbols();
        for (String symbol : symbols) {
            if (unitParts[0].endsWith(symbol)) {
                String prefixString = unitParts[0].substring(0, unitParts[0].lastIndexOf(symbol));

                if (prefixString.isEmpty()) {
                    int exponent = unitParts.length == 1 ? 1 : Integer.parseInt(unitParts[1]);
                    return new UnitInfo(IDENTITY_PREFIX, refUnit, symbol, exponent);
                }

                for (UnitPrefix prefix : prefixes) {
                    for (String prefixSymbol : prefix.symbols()) {
                        if (refUnit.prefixAllowed(symbol) && prefixString.equals(prefixSymbol)) {
                            int exponent = unitParts.length == 1 ? 1 : Integer.parseInt(unitParts[1]);
                            return new UnitInfo(prefix, refUnit, symbol, exponent);
                        }
                    }
                }
            }
        }

        // at the one place where we use this method an Optional<UnitInfo> does not
        // really help with the code, so we leave null here
        return null;
    }

    /**
     * Flattens the given units.
     * 
     * @param units the units, not null
     * @return the flattened units
     */
    public static final Unit[] flattenUnits(Unit[]... units) {
        Objects.requireNonNull(units);

        int size = 0;
        for (Unit[] unit : units) {
            size += unit.length;
        }

        Unit[] flat = new Unit[size];
        int index = 0;
        for (Unit[] unit : units) {
            int numNew = unit.length;
            System.arraycopy(unit, 0, flat, index, numNew);
            index += numNew;
        }

        return flat;
    }

    /**
     * Gets the conversion factor, if possible, from the origin units to the target
     * units. All separate units have to be separated by a space, prefixes have to
     * be prepended (without a separating space) to their unit, exponents have to be
     * specified by appending (without a separating space) "^" plus the value of the
     * exponent to the unit. Example: "km^2 s^-1". If additional units, not (yet) in
     * the standard implementations, are needed for the conversions, they can be
     * supplied via {@code extraUnits}.
     * 
     * @param origin     the original units, not null
     * @param target     the target units, not null
     * @param extraUnits the additional units to use for the conversion, not null
     * @return the conversion factor
     */
    public static final double factor(String origin, String target, Unit[]... extraUnits) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(target);
        Objects.requireNonNull(extraUnits);

        if (origin.equals(target)) {
            return 1;
        }

        var originInfos = collectInfoWithUnknownUnits(origin, extraUnits).values();
        var targetInfos = collectInfoWithUnknownUnits(target, extraUnits).values();

        return internalFactor(origin, target, originInfos, targetInfos);
    }

    /**
     * Gets the conversion factor, if possible, from the origin unit to the target
     * unit.
     * 
     * @param origin the original unit, not null
     * @param target the target unit, not null
     * @return the conversion factor
     * @throws IllegalArgumentException if the conversion is not possible for the
     *                                  given units
     */
    public static final double factor(Unit origin, Unit target) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(target);

        if (origin == target) {
            return 1;
        }

        if (origin.baseUnits().equals(target.baseUnits())) {
            if (origin.isConversionLinear() && target.isConversionLinear()) {
                return origin.factor(origin.symbols().get(0)) / target.factor(target.symbols().get(0));
            }

            throw new IllegalArgumentException(
                    ("Conversion from \"%s\" to \"%s\" contains non-multiplicative operations, "
                            + "hence a conversion factor cannot be used here.").formatted(toString(origin),
                                    toString(target)));
        }

        throw new IllegalArgumentException("Cannot convert from %s (units: %s) to %s (units: %s)".formatted(
                toString(origin), toSymbol(origin.baseUnits()), toString(target), toSymbol(target.baseUnits())));
    }

    /**
     * Converts a value, if possible, from the origin units to the target units. All
     * separate units have to be separated by a space, prefixes have to be prepended
     * (without a separating space) to their unit, exponents have to be specified by
     * appending (without a separating space) "^" plus the value of the exponent to
     * the unit. Example: "km^2 s^-1". If additional units, not (yet) in the
     * standard implementations, are needed for the conversions, they can be
     * supplied via {@code extraUnits}.
     * 
     * @param value      the value in the original units
     * @param origin     the original units, not null
     * @param target     the target units, not null
     * @param extraUnits the additional units to use for the conversion, not null
     * @return the converted value
     */
    public static final double convert(double value, String origin, String target, Unit[]... extraUnits) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(target);
        Objects.requireNonNull(extraUnits);

        if (origin.equals(target)) {
            return value;
        }

        var originInfos = collectInfoWithUnknownUnits(origin, extraUnits).values();
        var targetInfos = collectInfoWithUnknownUnits(target, extraUnits).values();
        return internalConversionOperation(originInfos, targetInfos).applyAsDouble(value);
    }

    /**
     * Converts a value, if possible, from the origin unit to the target unit.
     * 
     * @param value  the value in the original unit
     * @param origin the original unit, not null
     * @param target the target unit, not null
     * @return the converted value
     * @throws IllegalArgumentException if the conversion cannot be performed for
     *                                  the given units
     */
    public static final double convert(double value, Unit origin, Unit target) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(target);

        if (origin == target) {
            return value;
        }
        
        if (!origin.baseUnits().equals(target.baseUnits())) {
            throw new IllegalArgumentException("Cannot convert from %s (units: %s) to %s (units: %s)".formatted(
                    toString(origin), toSymbol(origin.baseUnits()), toString(target), toSymbol(target.baseUnits())));
        }

        if (origin.isConversionLinear()) {
            // TODO
            // does this distinction really really save time here?
            // same for the operation on the target
            value *= origin.factor(origin.symbols().get(0));
        } else {
            value = origin.convertToBaseUnits(value);
        }

        if (target.isConversionLinear()) {
            value /= target.factor(target.symbols().get(0));
        } else {
            value = target.convertFromBaseUnits(value);
        }

        return value;
    }

    /**
     * Checks whether the original units can be converted to the target units. All
     * separate units have to be separated by a space, prefixes have to be prepended
     * (without a separating space) to their unit, exponents have to be specified by
     * appending (without a separating space) "^" plus the value of the exponent to
     * the unit. Example: "km^2 s^-1". If additional units, not (yet) in the
     * standard implementations, are needed for the conversions, they can be
     * supplied via {@code extraUnits}.
     * 
     * @param origin     the original units. May be null.
     * @param target     the target units. May be null.
     * @param extraUnits the additional units to use for the conversion, not null
     * @return true if the original units can be converted to the target units
     */
    public static final boolean convertible(String origin, String target, Unit[]... extraUnits) {
        if (origin == null || target == null) {
            return false;
        } else if (origin.equals(target)) {
            return true;
        }
        var originInfos = collectInfoWithUnknownUnits(origin, extraUnits).values();
        var targetInfos = collectInfoWithUnknownUnits(target, extraUnits).values();
        return internalConversionCheck(originInfos, targetInfos);
    }

    /**
     * Checks whether the original unit can be converted to the target units. All
     * separate units (in the String format) have to be separated by a space,
     * prefixes have to be prepended (without a separating space) to their unit,
     * exponents have to be specified by appending (without a separating space) "^"
     * plus the value of the exponent to the unit. Example: "km^2 s^-1".
     * 
     * @param origin     the original units. May be null.
     * @param target     the target unit. May be null.
     * @return true if the original units can be converted to the target units
     */
    public static final boolean convertible(Unit origin, Unit target) {
        if (origin == null || target == null) {
            return false;
        }
        var originInfos = collectInfo(origin).values();
        var targetInfos = collectInfo(target).values();
        return internalConversionCheck(originInfos, targetInfos);
    }

    /**
     * Checks whether {@code units} are valid units.
     * 
     * @param units      the units to check, not null
     * @param extraUnits the additional units to use for the conversion, not null
     * @return true if {@code units} is valid
     */
    public static final boolean isValid(String units, Unit[]... extraUnits) {
        Objects.requireNonNull(units);
        Objects.requireNonNull(extraUnits);

        String[] unitsRaw = Regexes.ALL_SPACE.split(units.trim());
        for (String unitsRawPart : unitsRaw) {
            String[] unitPower = Regexes.EXPONENT.split(unitsRawPart.trim());
            UnitInfo unitInfo = checkUnits(unitPower, extraUnits);

            if (unitInfo == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the conversion factor.
     * 
     * @param origin      the original units
     * @param target      the target units
     * @param originInfos the infos about the origin units, in the format: infos[0]
     *                    contains info about the SIprefix, the SI unit and the
     *                    corresponding symbol, info[1] contains the corresponding
     *                    exponent
     * @param targetInfos the infos about the target units in the same format as the
     *                    {@code originInfos}
     * @return the conversion factor
     * @throws IllegalArgumentException if the conversion would require
     *                                  non-multiplicative operations
     */
    private static final double internalFactor(Object origin, Object target, Collection<UnitInfo> originInfos, Collection<UnitInfo> targetInfos) {
        BaseConversionInfo cleanOriginInfos = cleanup(originInfos);
        BaseConversionInfo cleanTargetInfos = cleanup(targetInfos);

        double factor = calculateFactor(cleanOriginInfos, cleanTargetInfos);

        // check if we can use a factor
        if (cleanOriginInfos.canUseFactor() && cleanTargetInfos.canUseFactor()) {
            return factor;
        }

        throw new IllegalArgumentException(("Conversion from \"%s\" to \"%s\" contains non-multiplicative "
                + "operations, hence a conversion factor cannot be used here.").formatted(origin, target));
    }

    /**
     * Calculates the conversion operation.
     * 
     * @param originInfos the infos about the origin units
     * @param targetInfos the infos about the target units
     * @return the conversion operation
     */
    static final DoubleUnaryOperator internalConversionOperation(Collection<UnitInfo> originInfos, Collection<UnitInfo> targetInfos) {
        BaseConversionInfo cleanOriginInfos = cleanup(originInfos);
        BaseConversionInfo cleanTargetInfos = cleanup(targetInfos);

        double factor = calculateFactor(cleanOriginInfos, cleanTargetInfos);

        // check if we can use a factor
        if (cleanOriginInfos.canUseFactor() && cleanTargetInfos.canUseFactor()) {
            return v -> factor * v;
        }

        // so we have celsius or similar shift conversions in here
        var nonMultiplicativeOperationInfo = collectNonMultiplicativeOperationInfo(originInfos, targetInfos);

        return v -> {
            for (var effectiveTransformation : nonMultiplicativeOperationInfo.entrySet()) {
                for (var symbolInfo : effectiveTransformation.getValue().entrySet()) {
                    v *= symbolInfo.getValue().get(FACTOR);
                    double exponent = symbolInfo.getValue().get(EXPONENT);
                    if (exponent > 0) {
                        Unit u = effectiveTransformation.getKey();
                        if (u.isBasic() && u.isConversionLinear()) {
                            v *= u.factor(symbolInfo.getKey());
                        } else if (!u.isBasic()) {
                            v = Math.pow(u.convertToBaseUnits(v), exponent);
                        }
                    } else if (exponent < 0) {
                        Unit u = effectiveTransformation.getKey();
                        if (u.isBasic() && u.isConversionLinear()) {
                            v /= u.factor(symbolInfo.getKey());
                        } else if (!u.isBasic()) {
                            v = Math.pow(u.convertFromBaseUnits(v), Math.abs(exponent));
                        }
                    }
                }
            }
            return v;
        };
    }

    /**
     * Checks whether the units are compatible.
     * 
     * @param originInfos the info with respect to the original units
     * @param targetInfos the info with respect to the target units
     * @return true if convertible
     */
    private static final boolean internalConversionCheck(Collection<UnitInfo> originInfos, Collection<UnitInfo> targetInfos) {
        var cleanOriginInfos = cleanup(originInfos).baseUnitInfos();
        var cleanTargetInfos = cleanup(targetInfos).baseUnitInfos();
        return cleanOriginInfos.equals(cleanTargetInfos);
    }

    /**
     * Gets the conversion factor from original units to the target units. Only
     * usable if all conversions contain multiplicative operations only.
     * 
     * @param cleanOriginInfos the info required for conversion into the base units
     *                         for the original units
     * @param cleanTargetInfos the info required for conversion into the base units
     *                         for the target units
     * @return the conversion factor
     */
    private static final double calculateFactor(BaseConversionInfo cleanOriginInfos, BaseConversionInfo cleanTargetInfos) {
        sanityCheck(cleanOriginInfos.baseUnitInfos(), cleanTargetInfos.baseUnitInfos());
        return cleanOriginInfos.factor() / cleanTargetInfos.factor();
    }

    /**
     * Checks whether the original units (or rather their representation in base
     * units) is compatible to the target units (again, via their representation in
     * base units).
     * 
     * @param origin the original units represented in base units
     * @param target the target units represented in base units
     * @throws IllegalArgumentException if the conversion is not possible
     */
    private static final void sanityCheck(Map<Unit, Integer> origin, Map<Unit, Integer> target) {
        for (var originBaseUnit : origin.entrySet()) {
            for (var targetBaseUnit : target.entrySet()) {
                if (!target.containsKey(originBaseUnit.getKey())) {
                    throw new IllegalArgumentException(
                            ("Target units do not include %s, although the original units contain it. "
                                    + "Explicit comparison: %s (original) vs. %s (target).").formatted(
                                            toString(originBaseUnit.getKey()),
                                            toString(origin.keySet().toArray(Unit[]::new)),
                                            toString(target.keySet().toArray(Unit[]::new))));            
                } else if (!origin.containsKey(targetBaseUnit.getKey())) {
                    throw new IllegalArgumentException(
                            ("Original units do not include %s, although the target units contain it. "
                                    + "Explicit comparison: %s (original) vs. %s (target).").formatted(
                                            toString(targetBaseUnit.getKey()),
                                            toString(origin.keySet().toArray(Unit[]::new)),
                                            toString(target.keySet().toArray(Unit[]::new))));
                } else if (originBaseUnit.getValue().intValue() != target.get(originBaseUnit.getKey()).intValue()) {
                    throw new IllegalArgumentException(("Target units do not include the correct exponent for %s. "
                            + "In the original units, the exponent is %s, while it is %s in the target units. "
                            + "Explicit comparison: %s (original) vs. %s (target)").formatted(
                                    toString(originBaseUnit.getKey()), originBaseUnit.getValue(),
                                    target.get(originBaseUnit.getKey()), compositeUnitToString(origin),
                                    compositeUnitToString(target)));
                }
            }
        }
    }

    /**
     * Helper method to print units with their exponents in a sensible manner, i.e.
     * it handles unknown units in a useful manner.
     * 
     * @param info the map with the units and their exponents
     * @return the string with sensible unit and exponent info
     */
    private static final String compositeUnitToString(Map<Unit, Integer> info) {
        StringBuilder sb = new StringBuilder("[");
        for (var unitInfo : info.entrySet()) {
            sb.append("{%s,%s}".formatted(toString(unitInfo.getKey()), unitInfo.getValue().toString()));
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Cleans the infos and merges them.
     * 
     * @param infos the unit infos
     * @return the information required for conversions to the base units
     */
    static final BaseConversionInfo cleanup(Collection<UnitInfo> infos) {
        double factor = 1;
        Map<Unit, Integer> baseUnitInfos = new LinkedHashMap<>();
        boolean canUseFactor = true;
        for (UnitInfo info : infos) {
            Map<Unit, Integer> map = info.unit().baseUnits();
            boolean factorCalculated = false;
            for (Entry<Unit, Integer> correspondingBase : map.entrySet()) {
                // the exponent contributed by previous units
                int exponent = baseUnitInfos.computeIfAbsent(correspondingBase.getKey(), s -> 0);
                baseUnitInfos.put(correspondingBase.getKey(), exponent + correspondingBase.getValue() * info.exponent());
                if (!factorCalculated && info.unit().prefixAllowed(info.symbol())) {
                    factor *= Math.pow(info.prefix().factor(), info.exponent());
                    factor *= Math.pow(info.unit().factor(info.symbol()), info.exponent());
                    factorCalculated = true;
                } else if (!factorCalculated) {
                    factor *= Math.pow(info.unit().factor(info.symbol()), info.exponent());
                    factorCalculated = true;
                }

            }
            if (canUseFactor) canUseFactor = info.unit().isConversionLinear();
        }

        // remove ^0
        Map<Unit, Integer> trimmedBaseUnitInfos = new LinkedHashMap<>(baseUnitInfos);
        for (Entry<Unit, Integer> baseUnitInfo : baseUnitInfos.entrySet()) {
            if (baseUnitInfo.getValue() == 0) {
                trimmedBaseUnitInfos.remove(baseUnitInfo.getKey());
            }
        }

        if (!canUseFactor) factor = Double.NaN;

        return new BaseConversionInfo(factor, Collections.unmodifiableMap(trimmedBaseUnitInfos), canUseFactor);
    }

    /**
     * Checks whether the origin and target units are equivalent (that is, the
     * origin units are convertible to the target units, and that the specified
     * amount of the origin units corresponds to the same amount of the target
     * units).
     * <p>
     * A typical usecase would be to check if a user defined unit is for the given
     * {@code value} equivalent to some standard unit. This method is usually
     * <em>not</em> the correct approach to check two constants for equivalency, as
     * one would want to not only check the units at one of their values.
     * 
     * @param value      the value which will be used to test for equivalence
     * @param origin     the original units, not null
     * @param target     the target units, not null
     * @param extraUnits the additional units to use for the conversion, not null
     * @return true if the units are equivalent
     */
    static final boolean equivalent(double value, String origin, String target, Unit[]... extraUnits) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(target);
        Objects.requireNonNull(extraUnits);

        Unit o = Unit.of(origin, extraUnits);
        Unit t = Unit.of(target, extraUnits);
        return equivalent(value, o, t);
    }

    /**
     * Checks whether the origin and target unit are equivalent (that is, the origin
     * unit is convertible to the target unit, and that 1 amount of the origin unit
     * corresponds to 1 amount of the target unit).
     * <p>
     * A typical usecase would be to check if a user defined unit is for the given
     * {@code value} equivalent to some standard unit. This method is usually
     * <em>not</em> the correct approach to check two constants for equivalency, as
     * one would want to not only check the units at one of their values.
     * 
     * @param value  the value which will be used to test for equivalence
     * @param origin the original unit, not null
     * @param target the target unit, not null
     * @return true if the units are equivalent
     */
    static final boolean equivalent(double value, Unit origin, Unit target) {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(target);

        return convertible(origin, target) && value == convert(value, origin, target);
    }

    /**
     * Simplifies the given units. Note that this is a costly operation (especially
     * if specific extra units are given), even though some optimizations have been
     * done. Note further that multiple unit combinations may be legit
     * simplifications.
     * 
     * <p>
     * For example:<br>
     * {@code "kg^3 m^4 s^-6 A^-1"} is equivalent to both {@code "N^2 Wb"} and
     * {@code "J^2 T"}.
     * 
     * <p>
     * In this case, the simplification that is picked follows these rules:
     * <ol>
     * <li>Pick only one unit, if possible.
     * <li>Pick the units with lower (absolute) exponents and prefer positive over
     * negative exponents if their magnitude is the same.
     * <li>Pick the units contained in the original units, if possible.
     * <li>Pick the units with uppercases, if possible.
     * <li>Pick the shorter units, if possible.
     * <li>Pick the lexicographically smaller units.
     * </ol>
     * Tries to combine at most 2 units, with exponents ranging from -3 to 3.
     * 
     * @param units      the units to simplify, not null
     * @param extraUnits the units that may be used for simplification. If none are
     *                   given the units given by {@link Units#DEFAULT_UNITS} are
     *                   used. May not be null.
     * @return the simplified symbol string, or the input string, if no
     *         simplification was found
     */
    public static final String simplify(String units, Unit[]... extraUnits) {
        Objects.requireNonNull(units);
        Objects.requireNonNull(extraUnits);

        final Set<Unit> refUnits;
        if (extraUnits.length == 0) {
            refUnits = DEFAULT_UNITS;
        } else {
            Unit[] flatExtraUnits = Stream.of(extraUnits).flatMap(Stream::of).toArray(Unit[]::new);

            refUnits = new HashSet<>();
            for (Unit singleUnit : flatExtraUnits) {
                refUnits.add(singleUnit);
                refUnits.addAll(singleUnit.compatibleUnits());
            }

            // for code simplification purposes we treat the single reference unit checks as
            // checks against a reference unit plus an empty unit
            refUnits.add(EMPTY_UNIT);
        }

        // make sure we don't get a gigantic cache over time
        if (simplifiedUnits.size() > 100) simplifiedUnits.clear();

        return simplifiedUnits.computeIfAbsent(new StringBasedUnit(units, refUnits), k -> {
            var compatibleSymbols = searchSimplificationSpace(units, refUnits, extraUnits);

            if (!compatibleSymbols.isEmpty()) {
                // so we might have bunch of equivalent symbols, like e.g. "N^2 Wb" and "J^2 T".
                // The special comparator used in the set we get takes care of this and
                // guarantees reproducible output
                NavigableSet<String> equivalentSymbols = compatibleSymbols.firstEntry().getValue();
                return equivalentSymbols.first();
            }

            // nothing found -> return input
            return units;
        });
    }

    /**
     * Searches though the given reference units and tries to find simplifications.
     * 
     * @param units      the units to simplify
     * @param refUnits   the reference units to use for potential simplifications
     * @param extraUnits the additional units needed for checking the equivalence
     * @return the found, simplified symbols
     */
    private static final NavigableMap<Long, NavigableSet<String>> searchSimplificationSpace(String units, Set<Unit> refUnits, Unit[]... extraUnits) {
        Comparator<String> comparator = comparatorForEquivalentSymbols(units);
        NavigableMap<Long, NavigableSet<String>> compatibleSymbols = new TreeMap<>();
        Set<Unit> alreadyProcessedUnits = new HashSet<>();
        Unit[][] allUnitReferences = new Unit[0][];
        if (extraUnits.length > 0) {
            allUnitReferences = new Unit[extraUnits.length + 1][];
            System.arraycopy(extraUnits, 0, allUnitReferences, 0, extraUnits.length);
            allUnitReferences[allUnitReferences.length - 1] = refUnits.stream().toArray(Unit[]::new);
        }

        Unit originalUnit = Unit.of(units, extraUnits);
        var baseUnits = originalUnit.baseUnits(); // for quickly skipping some combinations below

        // try a combination of two reference units, each with powers of -3,...,3
        int[] exponents = { 1, 2, -1, -2, 3, -3 }; // try to sort according to typical occurrences
        for (int exponent1 : exponents) {
            for (int exponent2 : exponents) {
                alreadyProcessedUnits.clear();
                for (Unit refUnit1 : refUnits) {
                    for (Unit refUnit2 : refUnits) {
                        // already processed (albeit in the other order, but that is irrelevant)
                        boolean alreadyProcessed = alreadyProcessedUnits.contains(refUnit2);

                        if (alreadyProcessed || checkForBaseUnitMismatch(baseUnits, refUnit1, exponent1, refUnit2, exponent2)) {
                            continue;
                        }

                        // if we have mixed all lowercase and partially uppercase symbols we put the
                        // partial uppercase unit first
                        String refUnit1Symbol = refUnit1.symbols().get(0);
                        String refUnit2Symbol = refUnit2.symbols().get(0);

                        String symbol = "";
                        if (COMPARATOR_FOR_UNIT_ORDERING.compare(refUnit1Symbol, refUnit2Symbol) > 0) {
                            if (!refUnit2Symbol.isEmpty()) symbol += refUnit2Symbol + (exponent2 == 1 ? "" : "^" + exponent2);
                            if (!refUnit1Symbol.isEmpty()) {
                                symbol += Strings.NON_BREAKABLE_SPACE;
                                symbol += refUnit1Symbol + (exponent1 == 1 ? "" : "^" + exponent1);
                            }
                        } else {
                            if (!refUnit1Symbol.isEmpty()) symbol += refUnit1Symbol + (exponent1 == 1 ? "" : "^" + exponent1);
                            if (!refUnit2Symbol.isEmpty()) {
                                symbol += Strings.NON_BREAKABLE_SPACE;
                                symbol += refUnit2Symbol + (exponent2 == 1 ? "" : "^" + exponent2);
                            }
                        }

                        Unit potentialTargetUnit = Unit.of(symbol, allUnitReferences);
                        if (equivalent(1, originalUnit, potentialTargetUnit)) {
                            Long score = calculateSymbolScore(refUnit1Symbol, refUnit2Symbol, exponent1, exponent2);
                            compatibleSymbols.computeIfAbsent(score, s -> new TreeSet<>(comparator)).add(symbol);
                            
                            // if the symbol is just the empty string and it is equivalent we can stop
                            // immediately, as it cannot get simpler.
                            if (potentialTargetUnit.symbols().get(0).isBlank()) {
                                return compatibleSymbols;
                            }
                        }
                    }
                    alreadyProcessedUnits.add(refUnit1);
                }
            }
        }
        return compatibleSymbols;
    }

    /**
     * Checks whether the base units and their exponents match the added base units
     * of the reference units (taking into account the givne exponents). This is
     * useful to stop deeply nested loops required for
     * {@link #searchSimplificationSpace(String, Set, Unit[]...)} early.
     * 
     * @param baseUnits the base units to check against
     * @param refUnit1  the first reference unit
     * @param exponent1 the power to raise the base units of the first reference
     *                  unit to
     * @param refUnit2  the second reference unit
     * @param exponent2 the power to raise the base units of the second reference
     *                  unit to
     * @return true if there is a mismatch of the base units vs. the combined base
     *         units of the given reference units (raised to their exponents)
     */
    private static boolean checkForBaseUnitMismatch(Map<Unit, Integer> baseUnits, Unit refUnit1, int exponent1, Unit refUnit2, int exponent2) {
        // If the base units don't match, we can skip further calculations, i.e. return
        // true.
        // First we check only the keys from the base units.
        Set<Unit> checkedUnits = new HashSet<>();
        for (Entry<Unit, Integer> entry : baseUnits.entrySet()) {
            if (entry.getValue() != exponent1 * refUnit1.baseUnits().getOrDefault(entry.getKey(), 0)
                    + exponent2 * refUnit2.baseUnits().getOrDefault(entry.getKey(), 0)) {
                // base units don't match
                return true;
            }
            checkedUnits.add(entry.getKey());
        }

        // Then we check the keys from ref unit 1, but without already checked units.
        for (Entry<Unit, Integer> entry : refUnit1.baseUnits().entrySet()) {
            if (checkedUnits.contains(entry.getKey())) continue;

            if (exponent1 * entry.getValue()
                    + exponent2 * refUnit2.baseUnits().getOrDefault(entry.getKey(), 0) != baseUnits
                            .getOrDefault(entry.getKey(), 0)) {
                // base units don't match
                return true;
            }
            checkedUnits.add(entry.getKey());
        }

        // Finally we check the keys from ref unit 2, but without already checked units.
        for (Entry<Unit, Integer> entry : refUnit2.baseUnits().entrySet()) {
            if (checkedUnits.contains(entry.getKey())) continue;

            if (exponent2 * entry.getValue()
                    + exponent1 * refUnit1.baseUnits().getOrDefault(entry.getKey(), 0) != baseUnits
                            .getOrDefault(entry.getKey(), 0)) {
                // base units don't match
                return true;
            }
            checkedUnits.add(entry.getKey());
        }
        return false;
    }

    /**
     * Gets the names of the units, within the specified context, whose symbols
     * match (for the specific meaning of "match" here see {@link UnitContextMatch})
     * the given units. Note that in contrast to
     * {@link #inContext(Unit, UnitContextMatch, UnitContext...)} only one context
     * can be given. If you want to check for multiple contexts use
     * {@link Unit#of(String, Unit[]...)} on your units and use
     * {@link #inContext(Unit, UnitContextMatch, UnitContext...)}.
     * 
     * <p>
     * Example usage:<br>
     * <code>Units.inContext("kg", UnitContextMatch.COMPATIBLE, PhysicsContext.GENERAL);</code>
     * 
     * 
     * @param units      the units whose interpretations in the given context is
     *                   wanted, not null
     * @param match      the value that determines how strict the match between the
     *                   given units and the available named symbols should be, not
     *                   null.
     * @param context    the context in which to view the given units, not null
     * @param extraUnits the additional units needed for checking the equivalence,
     *                   not null
     * @return the (lexicographically) ordered names of units that match the given
     *         unit within the specified context
     */
    public static final NavigableSet<String> inContext(String units, UnitContextMatch match, UnitContext context, Unit[]... extraUnits) {
        Objects.requireNonNull(units);
        Objects.requireNonNull(match);
        Objects.requireNonNull(context);
        Objects.requireNonNull(extraUnits);

        NavigableSet<String> ret = new TreeSet<>();
        for (var type : context.namedSymbols().entrySet()) {
            for (String unit : type.getValue()) {
                if (match.matches(units, unit, extraUnits)) {
                    ret.add(type.getKey());
                }
            }
        }

        return ret;
    }

    /**
     * Gets the names of the units, within the specified context, whose symbols
     * match (for the specific meaning of "match" here see {@link UnitContextMatch})
     * the given unit.
     * <p>
     * Example usage:<br>
     * <code>Units.inContext("kg", UnitContextMatch.COMPATIBLE, PhysicsContext.values());</code>
     * 
     * @param unit     the unit whose interpretations in the given context is
     *                 wanted, not null
     * @param match    the value that determines how strict the match between the
     *                 given units and the available named symbols should be, not
     *                 null.
     * @param contexts the contexts in which to view the given units, not null
     * @return the (lexicographically) ordered names of units that match the given
     *         unit within the specified context
     */
    public static final NavigableSet<String> inContext(Unit unit, UnitContextMatch match, UnitContext... contexts) {
        Objects.requireNonNull(unit);
        Objects.requireNonNull(match);
        Objects.requireNonNull(contexts);

        NavigableSet<String> ret = new TreeSet<>();
        // One could have a more performant implementation, but that seems like code
        // duplication not worth the effort, at least at the moment
        UnitContext[] contextsToUse = contexts.length == 0 ? PhysicsContext.values() : contexts;
        for (String symbol : unit.symbols()) {
            for (UnitContext context : contextsToUse) {
                ret.addAll(inContext(symbol, match, context));
            }
        }

        return ret;
    }

    /**
     * Checks whether the first unit is proportional to the second unit. The check
     * is done via checking whether all base units of the first unit are present,
     * with the same exponents, in the base units of the second unit.
     * 
     * @param first      the first unit, not null
     * @param second     the second unit, not null
     * @param extraUnits the additional units needed for checking the
     *                   proportionality, not null
     * @return true if the first unit is proportional to the second unit
     */
    public static final boolean proportional(String first, String second, Unit[]... extraUnits) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);
        Objects.requireNonNull(extraUnits);

        return proportional(Unit.of(first, extraUnits), Unit.of(second, extraUnits));
    }

    /**
     * Checks whether the first unit is proportional to the second unit. The check
     * is done via checking whether all base units of the first unit are present,
     * with the same exponents, in the base units of the second unit.
     * 
     * @param first  the first unit, not null
     * @param second the second unit, not null
     * @return true if the first unit is proportional to the second unit
     */
    public static final boolean proportional(Unit first, Unit second) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(second);

        if (!first.isConversionLinear() || !second.isConversionLinear()) return false;
        for (var o : first.baseUnits().entrySet()) {
            if (!o.getValue().equals(second.baseUnits().getOrDefault(o.getKey(), 0))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Processes the given map containing the StringRange-UnitInfo pair. Note that
     * the process is as follows:
     * 
     * <p>
     * <ol>
     * <li>Remove all substrings whose ranges are covered by a larger substring
     * <li>Check that there are no overlapping ranges
     * <li>Return the remaining unit infos in a list
     * </ol>
     * 
     * @param units        the units that were the source for {@code allUnitInfos}
     * @param allUnitInfos the collected unit infos, i.e. the result of the parsing
     *                     for all units that were used for it. This implies that
     *                     the map may contain multiple string ranges that comprise
     *                     other string ranges. It should generally, if everything
     *                     worked beforehand, not contain intersecting string ranges
     *                     - if it does, an exception will be thrown.
     * @return the list with the decoded unit infos
     * @throws IllegalStateException if there is an overlap of the string ranges (as
     *                               this effectively would prevent choosing the
     *                               adequate range to keep)
     */
    static List<UnitInfo> processStringRangeMatches(String units, NavigableMap<StringRange, UnitInfo> allUnitInfos) {
        // just to make sure we don't change this one by accident
        var finalAllUnitInfos = Collections.unmodifiableNavigableMap(allUnitInfos);

        // get a modifiable map
        var relevantUnitInfos = new TreeMap<>(allUnitInfos);

        // remove all substrings whose ranges are covered by a larger substring
        finalAllUnitInfos.forEach((range, info) -> {
            if (finalAllUnitInfos.keySet().stream().anyMatch(r -> r.comprises(range))) {
                relevantUnitInfos.remove(range);
            }
        });

        // make sure we have no overlapping ranges
        for (var sr1 : relevantUnitInfos.entrySet()) {
            for (var sr2 : relevantUnitInfos.entrySet()) {
                if (sr1.getKey().equals(sr2.getKey())) continue;
                if (sr1.getKey().intersects(sr2.getKey())) {
                    throw new IllegalStateException(
                            "The resolved units correspond to overlapping ranges in the given String. "
                                    + "Unable to determine which unit to use. The units in question were "
                                    + "%s (index %d to %d) and %s (index %d to %d) in %s".formatted(
                                            sr1.getValue(), sr1.getKey().from(), sr1.getKey().to(),
                                            sr2.getValue(), sr1.getKey().from(), sr2.getKey().to(), units));
                }
            }
        }

        return new ArrayList<>(relevantUnitInfos.values());
    }
}
