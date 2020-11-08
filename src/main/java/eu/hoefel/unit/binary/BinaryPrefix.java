package eu.hoefel.unit.binary;

import java.util.List;

import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.si.SiPrefix;

/**
 * <a href="https://doi.org/10.1088/0026-1394/37/1/12">Before 2005</a>, they
 * were only defined up to {@link #EXBI exbi}. Since then, they are defined up to
 * {@link #YOBI yobi}, matching their {@link SiPrefix SI} counterparts starting at
 * {@link SiPrefix#KILO kilo}.
 */
public enum BinaryPrefix implements UnitPrefix {

	/**
	 * Kibi is the binary prefix in the metric system that corresponds to
	 * 2<sup>10</sup>. Its name is based on the {@link SiPrefix#KILO SI prefix
	 * kilo}.
	 */
	KIBI(Math.pow(2, 10), "ki"),

	/**
	 * Mebi is the binary prefix in the metric system that corresponds to
	 * 2<sup>20</sup>. Its name is based on the {@link SiPrefix#MEGA SI prefix
	 * mega}.
	 */
	MEBI(Math.pow(2, 20), "Mi"),

	/**
	 * Gibi is the binary prefix in the metric system that corresponds to
	 * 2<sup>30</sup>. Its name is based on the {@link SiPrefix#GIGA SI prefix
	 * giga}.
	 */
	GIBI(Math.pow(2, 30), "Gi"),

	/**
	 * Tebi is the binary prefix in the metric system that corresponds to
	 * 2<sup>40</sup>. Its name is based on {@link SiPrefix#TERA SI prefix tera}.
	 */
	TEBI(Math.pow(2, 40), "Ti"),

	/**
	 * Pebi is the binary prefix in the metric system that corresponds to
	 * 2<sup>50</sup>. Its name is based on {@link SiPrefix#PETA SI prefix peta}.
	 */
	PEBI(Math.pow(2, 50), "Pi"),

	/**
	 * Exbi is the binary prefix in the metric system that corresponds to
	 * 2<sup>60</sup>. Its name is based on {@link SiPrefix#EXA SI prefix exa}.
	 */
	EXBI(Math.pow(2, 60), "Ei"),

	/**
	 * Zebi is the binary prefix in the metric system that corresponds to
	 * 2<sup>70</sup>. Its name is based on {@link SiPrefix#ZETTA SI prefix zetta}.
	 */
	ZEBI(Math.pow(2, 70), "Zi"),

	/**
	 * Yobi is the binary prefix in the metric system that corresponds to
	 * 2<sup>80</sup>. Its name is based on {@link SiPrefix#YOTTA SI prefix yotta}.
	 */
	YOBI(Math.pow(2, 80), "Yi");

	/** The prefix factor. */
	private final double factor;

	/** The symbols representing the prefix. */
	private final List<String> symbols;

	/**
	 * Constructor for binary prefixes.
	 * 
	 * @param factor  the factor corresponding to the prefix
	 * @param symbols the symbols representing the prefix
	 */
	private BinaryPrefix(double factor, String... symbols) {
		this.factor = factor;
		this.symbols = List.of(symbols);
	}

	@Override public double factor() { return factor; }
	@Override public List<String> symbols() { return symbols; }
}
