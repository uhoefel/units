package eu.hoefel.unit.si;

import java.util.List;

import eu.hoefel.unit.UnitPrefix;

/**
 * Standardized by the International Bureau of Weights and Measures (BIPM)
 * between 1960 and 1991, the metric SI prefixes are used in the
 * <a href="https://doi.org/10.1515/ci-2019-0108">International System of Units
 * (SI)</a>.
 */
public enum SiPrefix implements UnitPrefix {

	/**
	 * Yocto is the decimal unit prefix in the metric system that corresponds to one
	 * septillionth (10<sup>-24</sup>). It was added in
	 * <a href="https://doi.org/10.1088/0026-1394/29/1/002">1991</a> to the SI
	 * system. Its name is based on "eight" in latin (1000<sup><b>-8</b></sup>).
	 */
	YOCTO(1e-24, "y"),

	/**
	 * Zepto is the decimal unit prefix in the metric system that corresponds to one
	 * sextillionth (10<sup>-21</sup>). It was added in
	 * <a href="https://doi.org/10.1088/0026-1394/29/1/002">1991</a> to the SI
	 * system. Its name is based on "seven" in latin (1000<sup><b>-7</b></sup>).
	 */
	ZEPTO(1e-21, "z"),

	/**
	 * Atto is the decimal unit prefix in the metric system that corresponds to one
	 * quintillionth (10<sup>-18</sup>). Its name is based on "eighteen" in danish.
	 */
	ATTO(1e-18, "a"),

	/**
	 * Femto is the decimal unit prefix in the metric system that corresponds to one
	 * quadrillionth (10<sup>-15</sup>). Its name is based on "fifteen" in danish.
	 */
	FEMTO(1e-15, "f"),

	/**
	 * Pico is the decimal unit prefix in the metric system that corresponds to one
	 * trillionth (10<sup>-12</sup>). It was adopted in <a href=
	 * "https://www.bipm.org/utils/common/pdf/CGPM/CGPM11.pdf#page=87">1961</a>. Its
	 * name is based on "peak,bit" in spanish.
	 */
	PICO(1e-12, "p"),

	/**
	 * Nano is the decimal unit prefix in the metric system that corresponds to one
	 * billionth (10<sup>-9</sup>). Its name is based on "dwarf" in greek.
	 */
	NANO(1e-9, "n"),

	/**
	 * Micro is the decimal unit prefix in the metric system that corresponds to one
	 * millionth (10<sup>-6</sup>). Its name is based on "small" in greek.
	 */
	MICRO(1e-6, "μ", "u"),

	/**
	 * Milli is the decimal unit prefix in the metric system that corresponds to one
	 * thousandth (10<sup>-3</sup>). Its name is based on "one thousand" in latin.
	 */
	MILLI(1e-3, "m"),

	/**
	 * Centi is the decimal unit prefix in the metric system that corresponds to one
	 * hundredth (10<sup>-2</sup>). Its name is based on "hundred" in latin.
	 */
	CENTI(1e-2, "c"),

	/**
	 * Deci is the decimal unit prefix in the metric system that corresponds to one
	 * tenth (10<sup>-1</sup>). Its name is based on "tenth" in latin.
	 */
	DECI(1e-1, "d"),

	/**
	 * Deca is the decimal unit prefix in the metric system that corresponds to ten
	 * (10<sup>1</sup>). Its name is based on "ten" in ancient greek.
	 */
	DECA(1e1, "da"),

	/**
	 * Hecto is the decimal unit prefix in the metric system that corresponds to one
	 * hundred (10<sup>2</sup>). Its name is based on "hundred" in ancient greek
	 * (cf. also <a href="https://en.wikipedia.org/wiki/Hecatomb">hecatombs</a>).
	 */
	HECTO(1e2, "h"),

	/**
	 * Kilo is the decimal unit prefix in the metric system that corresponds to one
	 * thousand (10<sup>3</sup>). Its name is based on "thousand" in ancient greek.
	 */
	KILO(1e3, "k"),

	/**
	 * Mega is the decimal unit prefix in the metric system that corresponds to one
	 * million (10<sup>6</sup>). Its name is based on "great" in ancient greek.
	 */
	MEGA(1e6, "M"),

	/**
	 * Giga is the decimal unit prefix in the metric system that corresponds to one
	 * billion (10<sup>9</sup>). Its name is based on "giant" in ancient greek.
	 */
	GIGA(1e9, "G"),

	/**
	 * Tera is the decimal unit prefix in the metric system that corresponds to one
	 * trillion (10<sup>12</sup>). Its name is based on "monster" in ancient greek.
	 */
	TERA(1e12, "T"),

	/**
	 * Peta is the decimal unit prefix in the metric system that corresponds to one
	 * quadrillion (10<sup>15</sup>). It was added in
	 * <a href="https://doi.org/10.1088/0026-1394/11/4/006">1975</a> to the SI
	 * system. Its name is based on "five" in ancient greek
	 * (1000<sup><b>5</b></sup>).
	 */
	PETA(1e15, "P"),

	/**
	 * Exa is the decimal unit prefix in the metric system that corresponds to one
	 * quintillion (10<sup>18</sup>). It was added in
	 * <a href="https://doi.org/10.1088/0026-1394/11/4/006">1975</a> to the SI
	 * system. Its name is based on "six" in ancient greek
	 * (1000<sup><b>6</b></sup>).
	 */
	EXA(1e18, "E"),

	/**
	 * Zetta is the decimal unit prefix in the metric system that corresponds to one
	 * sextillion (10<sup>21</sup>). It was added in
	 * <a href="https://doi.org/10.1088/0026-1394/29/1/002">1991</a> to the SI
	 * system. Its name is based on "seven" in latin (1000<sup><b>7</b></sup>).
	 */
	ZETTA(1e21, "Z"),

	/**
	 * Yotta is the decimal unit prefix in the metric system that corresponds to one
	 * septillion (10<sup>24</sup>). It was added in
	 * <a href="https://doi.org/10.1088/0026-1394/29/1/002">1991</a> to the SI
	 * system. Its name means "eight" in ancient greek (1000<sup><b>8</b></sup>).
	 */
	YOTTA(1e24, "Y");

	/** The prefix factor. */
	private final double factor;

	/** The symbols representing the prefix. */
	private final List<String> symbols;

	/**
	 * Constructor for SI prefixes.
	 * 
	 * @param factor  the factor corresponding to the prefix
	 * @param symbols the symbols representing the prefix
	 */
	private SiPrefix(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override public double factor() { return factor; }
	@Override public List<String> symbols() { return symbols; }
}
