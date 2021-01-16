package eu.hoefel.unit.imperial;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.history.roman.RomanUnit;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiCommonUnit;
import eu.hoefel.unit.us.USCustomaryUnit;

/**
 * The (British) imperial units were first defined in in the
 * <a href ="https://www.legislation.gov.uk/ukpga/Geo4/5/74/">British Weights
 * and Measures Act 1824</a>. They were historically used especially throughout
 * the British Empire. As of now, most countries of the former British Empire
 * have switched to the (cf. {@link SiBaseUnit metric system}), although the
 * imperial units remain in common use. Currently, the imperial units have been
 * defined in terms of the metric system (cf.
 * <a href="https://www.legislation.gov.uk/ukpga/1985/72">Weights and Measures
 * Act 1985</a>), which is why the base units of the SI are returned if
 * {@link #baseUnits()} gets called. The imperial units are closely related to
 * the {@link USCustomaryUnit United States customary units}.
 * 
 * @author Udo Höfel
 */
public enum ImperialUnit implements Unit {

	/**
	 * A thou is a thousandth of an {@link #INCH inch}. It is traditionally used in
	 * engineering and manufacturing.
	 */
	THOU(0.0000254, "th"),

	/**
	 * The inch (derived from the Roman {@link RomanUnit#UNCIA uncia}, "twelfth")
	 * corresponds to the twelfth of a {@link #FOOT foot}.
	 */
	INCH(0.0254, "in", "″"),

	/**
	 * A foot is defined to comprise 12 {@link #INCH inches}, and a {@link #YARD
	 * yard} comprises 3 feet. It remains in use in measuring altitude in
	 * international aviation.
	 */
	FOOT(0.3048, "ft", "′"),

	/**
	 * A yard is defined to comprise 3 {@link #FOOT feet}. It is still in use, e.g.,
	 * on road signs in the United Kingdom.
	 */
	YARD(0.9144, "yd"),

	/**
	 * The chain comprises 22 {@link #YARD yards}. It has been historically used for
	 * surveying purposes. Nowadays, it is still used in some agricultural areas.
	 */
	CHAIN(20.1168, "ch"),

	/**
	 * A furlong is an eighth of a {@link #MILE mile}. Five furlong correspond to
	 * roughly one kilometer. The unit is seldomly used nowadays, exceptions are
	 * road signs in Myanmar and distances along canals in England.
	 */
	FURLONG(201.168, "fur"),

	/**
	 * The mile corresponds to roughly 1.6 kilometers. Internationally, the
	 * {@link SiBaseUnit#METER meter} has replaced the mile in most countries,
	 * except for some countries with close ties to the United Kingdom or the United
	 * States.
	 */
	MILE(1609.344, "mi"),

	/**
	 * A league was originally the distance a person could walk in an hour. Today,
	 * the league is in no country an official unit.
	 */
	LEAGUE(4828.032, "lea"),

	// maritime units

	/**
	 * A fathom corresponds to roughly 6 {@link #FOOT feet}. It is used
	 * (predominantly in English speaking countries) to measure water depth.
	 * Originally, it described the span of a man's outstretched arms.
	 */
	FATHOM(1.852, "ftm"),

	/**
	 * A cable is a nautical length unit, corresponding to roughly 608 {@link #FOOT
	 * feet}, about the length of a ship's anchor cable in the Age of Sail. It
	 * corresponds to a tenth of a {@link #NAUTICAL_MILE nautical mile}.
	 */
	CABLE(185.2, "cbl"),

	/**
	 * A nautical mile, also known as an Admiralty measured mile, corresponds to 10
	 * {@link #CABLE cables}. It is used in marine, air and space navigation.
	 */
	NAUTICAL_MILE(1852, "nmi"),

	// Gunter's survey units

	/**
	 * The link is based on Gunter's chain, a metal chain that was used in land
	 * surveying. As this chain had 100 links, a link is a hundredth of a
	 * {@link #CHAIN chain}.
	 */
	LINK(0.201168, "l.", "li.", "lnk."),

	/**
	 * The rod was used by surveyors. It corresponds to roughly 5
	 * {@link SiBaseUnit#METER meters}.
	 */
	ROD(5.0292, "rd", "rod", "perch"),

	// area

	/**
	 * A square perch is one square {@link #ROD rod}. It is very rarely used
	 * nowadays.
	 */
	PERCH(25.29285264, "per", "perch"),

	/**
	 * A rood corresponds to the area of a rectangle with one side having the length
	 * of one {@link #FURLONG furlong} and the other side having the length of one
	 * {@link #ROD rod}. It was used in surveying, as it is easy to convert to
	 * {@link #ACRE acres} (a rood is a quarter of one {@link #ACRE acre}).
	 */
	ROOD(1011.7141056, "ro", "rood"),

	/**
	 * The acre used to correspond to (roughly) the area that could be ploughed in
	 * one day by a yoke of oxen. It comprises 4 {@link #ROOD roods}.
	 */
	ACRE(4046.8564224, "ac", "acre"),

	// volume

	/**
	 * A fluid ounce corresponds to a twentieth of a {@link #PINT pint} and is used
	 * to measure liquids.
	 */
	FLUID_OUNCE(0.0284130625, "fl.oz.", "oz.fl."),

	/**
	 * A gill, also called teacup, corresponds to a quarter of a {@link #PINT pint}.
	 * It is rarely used, except for alcoholic spirit measures.
	 */
	GILL(0.0001420653125, "gi"),

	/**
	 * A pint is slightly more than half a {@link SiCommonUnit#LITER liter}. It is
	 * often used in English speaking countries to measure, e.g., beer volume. It
	 * corresponds to an eighth of a {@link #GALLON gallon}.
	 */
	PINT(0.00056826125, "pt"),

	/**
	 * A quart corresponds to a quarter of a {@link #GALLON gallon}, i.e. a quart
	 * represents roughly one {@link SiCommonUnit#LITER liter}.
	 */
	QUART(0.0011365225, "qt"),

	/**
	 * A gallon corresponds to 4 {@link #QUART quarts} or 8 {@link #PINT pints}. It
	 * is still used in some countries, particularly for gasoline.
	 */
	GALLON(0.00454609, "gal", "impgal"),

	/** A barrel of oil. */
	BARREL(0.000158987294928, "bbl", "barrel"),

	// weight

	/**
	 * A grain corresponds to the idealized weight of a single grain of barley. It
	 * is still used, e.g., to measure bullet and arrow weight.
	 */
	GRAIN(6.479891e-5, "gr"),

	/**
	 * A dram (in the avoirdupois version) corresponds to a sixteenth of an
	 * (avoirdupois) {@link #OUNCE ounce}. In ancient Greece a drachm used to be a
	 * coin, as well as a weight measure, from which the name dram stems.
	 */
	DRAM(0.0017718451953125, "dr"),

	/**
	 * An (avoirdupois) ounce corresponds to a sixteenth of an (avoirdupois)
	 * {@link #POUND pound}. Nowadays, it is used only informally in the United
	 * Kingdom. Its name stems from the Roman {@link RomanUnit#UNCIA_WEIGHT uncia}.
	 */
	OUNCE(0.028349523125, "oz"),

	/**
	 * A pound consists of 16 {@link #OUNCE ounces}. It is still in common use in
	 * the United Kingdom. The unit descended from the Roman {@link RomanUnit#LIBRA
	 * libra}.
	 */
	POUND(0.45359237, "lb"),

	/**
	 * A stone corresponds to 14 {@link #POUND pounds}. It is still in customary use
	 * for body weights in Britain and Ireland, but no longer used commerically.
	 */
	STONE(6.35029318, "st"),

	/**
	 * A quarter corresponds to 28 {@link #POUND pounds} or 2 {@link #STONE stone}.
	 * Its name refers to it being a quarter of a {@link #HUNDREDWEIGHT
	 * hundredweight}.
	 */
	QUARTER(12.70058636, "qr", "qtr"),

	/**
	 * A (long) hundredweight, also known as centum weight or quintal, corresponds
	 * to 8 {@link #STONE stone} or a twentieth of a {@link #TON ton}.
	 */
	HUNDREDWEIGHT(50.80234544, "cwt"),

	/**
	 * A (long) ton corresponds to 20 {@link #HUNDREDWEIGHT hundredweight}. The unit
	 * is still used in the United states to measure, e.g., the displacement of
	 * ships. It should not be confused with a {@link SiCommonUnit#TONNE tonne}.
	 */
	TON(1016.0469088, "tn.l."),

	/**
	 * A slug is defined as the mass that is accelerated by 1 {@link #FOOT
	 * ft}/{@link SiBaseUnit#SECOND s}<sup>2</sup> when a force of one {@link #POUND
	 * pound} is exerted on it.
	 */
	SLUG(14.59390294, "slug");

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
	private ImperialUnit(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override
	public Map<Unit, Integer> baseUnits() {
		if (baseUnits == null) {
			baseUnits = switch (this) {
				case THOU, INCH, FOOT, YARD, CHAIN, FURLONG, MILE, LEAGUE -> Map.of(SiBaseUnit.METER, 1);
				case FATHOM, CABLE, NAUTICAL_MILE -> Map.of(SiBaseUnit.METER, 1);
				case LINK, ROD -> Map.of(SiBaseUnit.METER, 1);
				case PERCH, ROOD, ACRE -> Map.of(SiBaseUnit.METER, 2);
				case FLUID_OUNCE, GILL, PINT, QUART, GALLON, BARREL -> Map.of(SiBaseUnit.METER, 3);
				case GRAIN, DRAM, OUNCE, POUND, STONE, QUARTER, HUNDREDWEIGHT, TON -> Map.of(SiBaseUnit.KILOGRAM, 1);
				case SLUG -> Map.of(SiBaseUnit.KILOGRAM, 1);
			};
		}
		return baseUnits;
	}

	@Override public double factor(String symbol) { return factor; }
	@Override public double convertToBaseUnits(double value) { return factor * value; }
	@Override public double convertFromBaseUnits(double value) { return value / factor; }
	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return false; }
	@Override public boolean isConversionLinear() { return true; }
	@Override public Set<UnitPrefix> prefixes() { return Units.EMPTY_PREFIXES; }
	@Override public boolean isBasic() { return false; }
}
