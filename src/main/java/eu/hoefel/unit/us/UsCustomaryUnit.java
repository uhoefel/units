package eu.hoefel.unit.us;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.history.roman.RomanUnit;
import eu.hoefel.unit.imperial.ImperialUnit;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiCommonUnit;
import eu.hoefel.unit.si.SiPrefix;

/**
 * United States customary units were formally adopted in 1832. It originated
 * from the units in use in the British empire at the time (thus the similarity
 * to {@link ImperialUnit imperial units}, although one should note that the
 * imperial units were changed later on, such that there are notable differences
 * for some units).
 * 
 * <p>
 * Many US customary units were redefined with respect to the {@link SiBaseUnit
 * metric system} by the Mendenhall Order of 1893 and again in 1959 by the
 * international yard and pound agreement.
 * 
 * <p>
 * Nowadays, the customary units are largely used in commercial activities and
 * in a personal context. In scientific, military and many more specialized
 * sectors the metric system dominates, as is encouraged by the U.S. National
 * Institute of Standards and Technology (NIST).
 * 
 * @see <a href="https://dx.doi.org/10.6028/NBS.SP.447">Weights and measures
 *      standards of the United States: A brief history</a>
 * 
 * @author Udo Hoefel
 */
public enum UsCustomaryUnit implements Unit {

    /**
     * The inch (derived from the Roman {@link RomanUnit#UNCIA uncia}, "twelfth")
     * corresponds to the twelfth of a {@link #FOOT foot}.
     */
    INCH(0.0254 /* 6 P̸ */, "in", "″"),

    /**
     * A foot is defined to comprise 12 {@link #INCH inches}, and a {@link #YARD
     * yard} comprises 3 feet.
     */
    FOOT(0.3048 /* 12 in */, "ft", "′"),

    /**
     * A yard is defined to comprise 3 {@link #FOOT feet}. It is still frequently
     * used conversationally in the US.
     */
    YARD(0.9144 /* 3 ft */, "yd"),

    /**
     * The chain comprises 22 {@link #YARD yards}. It has been historically used for
     * surveying purposes. Nowadays, it is still used in some agricultural areas.
     */
    CHAIN(20.1168402337 /* 66 ft */, "ch"),

    /**
     * A furlong is an eight of a {@link #MILE mile}. The unit is seldomly used
     * nowadays, with the exception of distances in horse-racing in English-speaking
     * countries.
     */
    FURLONG(201.168 /* 660 ft */, "fur"),

    /**
     * The mile corresponds to roughly 1.6 kilometers. Internationally, the
     * {@link SiBaseUnit#METER meter} has replaced the mile in most countries,
     * except for some countries with close ties to the United Kingdom or the United
     * States.
     */
    MILE(1609.344 /* 5280 ft */, "mi"),

    /**
     * A league was originally the distance a person could walk in an hour. Today,
     * the league is in no country an official unit.
     */
    LEAGUE(4.82804165608 /* 3 mi */, "lea"),

    // maritime units

    /**
     * A fathom is used (predominantly in English speaking countries) to measure
     * water depth. Originally, it described the span of a man's outstretched arms.
     */
    FATHOM(1.8288 /* 2 yd */, "ftm"),

    /**
     * A cable is a nautical length unit, corresponding to roughly the length of a
     * ship's anchor cable in the Age of Sail.
     */
    CABLE(0.219456 /* 120 ftm */, "cbl"),

    /** The nautical mile is used in marine, air and space navigation. */
    NAUTICAL_MILE(1852.354944 /* 1.151 mi */, "nmi"),

    // Gunter's survey units

    /**
     * The link is based on Gunter's chain, a metal chain that was used in land
     * surveying. As this chain had 100 links, a link is a hundredth of a
     * {@link #CHAIN chain}.
     */
    LINK(0.20116840233 /* 33/50 ft */, "l.", "li.", "lnk."),

    /**
     * The rod was used by surveyors. It corresponds to roughly 5
     * {@link SiBaseUnit#METER meters}.
     */
    ROD(5.02921005842 /* 16.5 ft */, "rd", "rod"),

    // area

    /**
     * The acre used to correspond to (roughly) the area that could be ploughed in
     * one day by a yoke of oxen. It comprises 10 square {@link #CHAIN chain}.
     */
    ACRE(4046.8564224 /* 43560 ft^2 */, "ac", "acre"),

    /**
     * A section corresponds to one square mile (survey). It is used in U.S. land
     * surveying (Public Land Survey System).
     */
    SECTION(2589988.11034 /* 640 acre */, "section"),

    /**
     * A (survey) township is made up of 36 {@link #SECTION sections}. It is used in
     * U.S. land surveying (Public Land Survey System).
     */
    TOWNSHIP(93239571.9721 /* 36 section */, "twp"),

    // volume

    /**
     * The minim corresponds to 1/60 of a {@link #FLUID_DRAM fluid dram}. It
     * corresponds to roughly one drop, which it replaced as the size of a drop
     * depends, amongst other things, strongly on the viscosity. It is traditionally
     * used in a pharmaceutical context, but its use is declining due to
     * metrication.
     */
    MINIM(0.00000061611519921875 /* roughly one drop */, "min"),

    /**
     * A fluid dram is a measure of volume. It is rarely used today, with the
     * exception of pill containers.
     */
    FLUID_DRAM(0.0000036966911953125 /* 60 min */, "fl.dr."),

    /**
     * A teaspoon corresponds to 80 {@link #MINIM min}. Note that the teaspoon
     * for nutritional labeling and medicine in the US corresponds to the
     * <em>metric</em> teaspoon (which is defined to be 5 {@link SiPrefix#MILLI
     * m}{@link SiCommonUnit#LITER L}).
     */
    TEASPOON(0.00000492892159375 /* 1/6 fl.oz. */, "tsp"),

    /**
     * A tablespoon corresponds to 3 {@link #TEASPOON teaspoons}. Similar to the
     * teaspoon the tablespoon for nutritional labeling and medicine corresponds to
     * the <em>metric</em> tablespoon (which is defined to be 15
     * {@link SiPrefix#MILLI m}{@link SiCommonUnit#LITER L} and hence slightly
     * different).
     */
    TABLESPOON(0.0001478676478125 /* 0.5 fl.oz. */, "Tbsp"),

    /**
     * A fluid ounce corresponds to a sixteenth of a {@link #LIQUID_PINT pint} and
     * is used to measure liquids.
     */
    FLUID_OUNCE(0.0000295735295625, "fl.oz.", "oz.fl."),

    /** A shot (as defined here) corresponds to a 3 {@link #TABLESPOON tablespoons}. */
    SHOT(0.0000443602943437 /* 1.5 fl.oz. */, "jig"),

    /**
     * A gill, also called teacup, corresponds to a quarter of a {@link #LIQUID_PINT
     * pint}. It is rarely used, except for alcoholic spirit measures.
     */
    GILL(0.00011829411825 /* 4 fl.oz. */, "gi"),

    /**
     * A cup is a measure associated with cooking and corresponds to half a
     * {@link #LIQUID_PINT liquid pint}.
     */
    CUP(0.0002365882365 /* 8 fl.oz. */, "cp"),

    /**
     * A pint is slightly less than half a {@link SiCommonUnit#LITER liter}. It is
     * often used in English speaking countries to measure, e.g., beer volume. It
     * corresponds to an eighth of a {@link #LIQUID_GALLON gallon}.
     */
    LIQUID_PINT(0.000473176473 /* 16 fl.oz. */, "pt"),

    /**
     * The quart corresponds to a quarter of a {@link #LIQUID_GALLON gallon}, i.e. a
     * quart represents roughly one {@link SiCommonUnit#LITER liter}.
     */
    LIQUID_QUART(0.000946352946 /* 32 fl.oz. */, "qt"),

    /**
     * The pottle is a measure of volume and corresponds to two {@link #LIQUID_QUART
     * liquid quart}.
     */
    LIQUID_POTTLE(0.001892705892 /* 64 fl.oz. */, "pot"),

    /**
     * The gallon corresponds to 4 {@link #LIQUID_QUART quarts} or 8
     * {@link #LIQUID_PINT pints}. It is still used in some countries, particularly
     * for gasoline.
     */
    LIQUID_GALLON(0.003785411784 /* 128 fl.oz. */, "gal"),

    /**
     * The liquid barrel is a measure of volume and corresponds to roughly 119
     * {@link SiCommonUnit#LITER L}.
     */
    LIQUID_BARREL(0.119240471196 /* 31.5 gal */, "bbl.(liquid)", "bl.(liquid)"),

    /** A barrel of oil. */
    OIL_BARREL(0.158987294928 /* 42 gal */, "bbl.(oil)", "bl.(oil)"),

    /**
     * The hogshead is a measure of volume, corresponding to a large cask of typically
     * alcoholic beverages.
     */
    HOGSHEAD(0.238480942392 /* 63 gal */, "hhd"),

    /**
     * The dry pint is a measure of volume, corresponding to an eighth of a
     * {@link #DRY_QUART dry quart}.
     */
    DRY_PINT(0.0005506104713575 /* 33.6003125 in^3 */, "pt(dry)"),

    /**
     * The dry quart is a measure of volume, corresponding to a quarter of a
     * {@link #DRY_GALLON dry gallon}. It represent roughly 1.1
     * {@link SiCommonUnit#LITER L}.
     */
    DRY_QUART(0.00110122094271 /* 2 dry pint */, "qt(dry)"),

    /**
     * The dry gallon is a measure of volume, corresponding to an eighth of a
     * {@link #BUSHEL bushel}. It is not used in commerce.
     */
    DRY_GALLON(0.00440488377 /* 4 dry quart */, "gal(dry)"),

    /**
     * The peck is a measure of volume, corresponding to a quarter of a {@link #BUSHEL
     * bushel}. It is rarely used nowadays. Apples in the US are sometimes still
     * sold by the peck.
     */
    PECK(0.00880976754 /* 2 dry gallon */, "pk"),

    /**
     * The (Winchester) bushel is a measure of volume, corresponding roughly to a
     * cylinder of 18.5 {@link #INCH in} diameter and 8 {@link #INCH in} in height.
     */
    BUSHEL(0.03523907016 /* 4 pk */, "bu"),

    /**
     * The dry barrel is a measure of volume and corresponds to roughly 116
     * {@link SiCommonUnit#LITER L}.
     */
    DRY_BARREL(0.11562712358 /* 7056 in^3 */, "bbl.(dry)", "bl.(dry)"),

    // weight

    /**
     * A grain corresponds to the idealized weight of a single grain of barley. It
     * is still used, e.g., to measure bullet and arrow weight.
     */
    GRAIN(0.00006479891 /* 1/7000 lb */, "gr"),

    /**
     * The pennyweight is a measure of weight and corresponds to a twentieth of a
     * {@link #TROY_OUNCE troy ounce}.
     */
    PENNYWEIGHT(0.00155517384 /* 24 gr */, "dwt"),

    /**
     * The troy ounce is a measure of weight particularly used in the precious metal
     * industry. It corresponds to 20 {@link #PENNYWEIGHT pennyweights}.
     */
    TROY_OUNCE(0.0311034768 /* 20 dwt */, "oz.t."),

    /**
     * The troy pound is a measure of weight particularly used in the precious metal
     * industry. It corresponds to 12 {@link #TROY_OUNCE troy ounces} and is roughly
     * 18% lighter than the {@link #POUND}.
     */
    TROY_POUND(0.3732417216 /* 12 oz.t. */, "lb.t."),

    /**
     * A dram (in the avoirdupois version) corresponds to a sixteenth of an
     * (avoirdupois) {@link #OUNCE ounce}. In ancient Greece a drachm used to be a
     * coin, as well as a weight measure, from which the name dram stems.
     */
    DRAM(0.0017718451953125 /* 27 11/32 gr */, "dr"),

    /**
     * An (avoirdupois) ounce corresponds to a sixteenth of an (avoirdupois)
     * {@link #POUND pound}. It is still a standard unit andhence commonly used in
     * the United States. Its name stems from the Roman
     * {@link RomanUnit#UNCIA_WEIGHT uncia}.
     */
    OUNCE(0.028349523125 /* 16 dr */, "oz"),

    /**
     * A pound consists of 16 {@link #OUNCE ounces}. It is still in common use in
     * the United States. The unit descended from the Roman {@link RomanUnit#LIBRA
     * libra}.
     */
    POUND(0.45359237 /* 7000 gr */, "lb"),

    /**
     * A (short) hundredweight, also known as centa, corresponds to 100
     * {@link #POUND pound}.
     */
    HUNDREDWEIGHT(45.359237 /* 100 lb */, "cwt"),

    /**
     * A (short) ton corresponds to 20 {@link #HUNDREDWEIGHT hundredweight}. The
     * unit is widely used in the United States (typically without the "short"
     * prefix). It should not be confused with a {@link SiCommonUnit#TONNE tonne}.
     */
    TON(907.18474 /* 20 cwt */, "t");


    /** The symbols representing the imperial unit. */
    private final List<String> symbols;

    /** The factor to convert from the imperial unit to the SI base units. */
    private final double factor;

    /** The SI base units to which the imperial unit corresponds. */
    private Map<Unit, Integer> baseUnits;

    /**
     * Constructor for imperial units.
     * 
     * @param factor  the conversion factor to base SI units
     * @param symbols the symbols representing the imperial unit
     */
    UsCustomaryUnit(double factor, String... symbols) {
        this.factor = factor;
        this.symbols = List.of(symbols);
    }

    @Override
    public Map<Unit, Integer> baseUnits() {
        if (baseUnits == null) {
            baseUnits = switch (this) {
                case INCH, FOOT, YARD, CHAIN, FURLONG, MILE, LEAGUE -> Map.of(SiBaseUnit.METER, 1);
                case FATHOM, CABLE, NAUTICAL_MILE -> Map.of(SiBaseUnit.METER, 1);
                case LINK, ROD -> Map.of(SiBaseUnit.METER, 1);
                case ACRE, SECTION, TOWNSHIP -> Map.of(SiBaseUnit.METER, 2);
                case MINIM, FLUID_DRAM, TEASPOON, TABLESPOON, FLUID_OUNCE,
                        SHOT, GILL, CUP, LIQUID_PINT, LIQUID_QUART,
                        LIQUID_POTTLE, LIQUID_GALLON, LIQUID_BARREL,
                        OIL_BARREL, HOGSHEAD -> Map.of(SiBaseUnit.METER, 3);
                case DRY_PINT, DRY_QUART, DRY_GALLON, PECK, BUSHEL,
                        DRY_BARREL -> Map.of(SiBaseUnit.METER, 3);
                case GRAIN, PENNYWEIGHT, TROY_OUNCE, TROY_POUND, DRAM, OUNCE,
                        POUND, HUNDREDWEIGHT, TON -> Map.of(SiBaseUnit.KILOGRAM, 1);
            };
        }
        return baseUnits;
    }

    @Override public double factor(String symbol) { return factor; }
    @Override public double convertToBaseUnits(double value) { return factor(symbols().get(0)) * value; }
    @Override public double convertFromBaseUnits(double value) { return value / factor(symbols().get(0)); }
    @Override public List<String> symbols() { return symbols; }
    @Override public boolean prefixAllowed(String symbol) { return false; }
    @Override public boolean isConversionLinear() { return true; }
    @Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
    @Override public boolean isBasic() { return false; }
}
