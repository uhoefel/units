package eu.hoefel.unit.special;

import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.UnitPrefix;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;

/**
 * Covers rarer temperature units. For Kelvin and degree Celsius see
 * {@link SiBaseUnit#KELVIN} and {@link SiDerivedUnit#DEGREE_CELSIUS}.
 * 
 * @author Udo Hoefel
 */
public enum TemperatureUnit implements Unit {

	/**
	 * The Delisle scale was invented by Joseph-Nicolas Delisle and uses two fix
	 * points; the temperature of boiling water as the fixed zero point and the
	 * freezing point of water at 150°De. It was used for several decades in Russia.
	 */
	DELISLE("°De"),

	/**
	 * The Fahrenheit scale was invented by Daniel Gabriel Fahrenheit and uses two
	 * fixed points; the freezing point of water is defined to be 32°F and the
	 * boiling point of water is defined to be 212°F. It is rarely used nowadays,
	 * except for the United States and several smaller territories.
	 */
	FAHRENHEIT("°F"),

	/**
	 * The Newton scale was invented by Isaac Newton and uses (like
	 * {@link SiBaseUnit#KELVIN kelvin}) two fixed points; the freezing point of
	 * water is defined to be 0°N and and the boiling point of water is defined to
	 * be 33°N.
	 */
	NEWTON("°N"),

	/**
	 * The Rankine scale was invented by William John Macquorn Rankine. It uses
	 * absolute zero as its zero fixed point (like {@link SiBaseUnit#KELVIN
	 * kelvin}), but uses the same temperature difference as {@link #FAHRENHEIT
	 * Fahrenheit}.
	 */
	RANKINE("°Ra", "°R"),

	/**
	 * The Réaumur scale was invented by René Antoine Ferchault de Réaumur and uses
	 * (like {@link SiBaseUnit#KELVIN kelvin}) two fixed points; the freezing point
	 * of water is defined to be 0°Ré and and the boiling point of water is defined
	 * to be 80°Ré.
	 */
	REAUMUR("°Ré", "°Re", "°r"),

	/**
	 * The Rømer scale was invented by Ole Christensen Rømer and was a predecessor
	 * of the {@link SiBaseUnit#KELVIN kelvin} and {@link #FAHRENHEIT Fahrenheit}
	 * scales. It uses two fixed points; the melting point of brine is defined to be
	 * 0°Rø and and the boiling point of water is defined to be 60°Rø.
	 */
	ROMER("°Rø");

	/** The SI base units to which the temperature unit corresponds. */
	private static final Map<Unit, Integer> BASE_UNITS = Map.of(SiBaseUnit.KELVIN, 1);

	/** The symbols representing the temperature unit. */
	private final List<String> symbols;

	/**
	 * Constructor for temperature unit.
	 * 
	 * @param symbols the symbols representing the temperature SI unit
	 */
	private TemperatureUnit(String... symbols) {
		this.symbols = List.of(symbols);
	}

	@Override
	public double convertToBaseUnits(double value) {
		return switch (this) {
				case DELISLE -> 373.15 - 2.0 * value / 3.0;
				case FAHRENHEIT -> 5.0 * (value + 459.67) / 9.0;
				case NEWTON -> 100 * value / 33.0 + 273.15;
				case RANKINE -> 5.0 * value / 9.0;
				case REAUMUR -> 1.25 * value + 273.15;
				case ROMER -> 40 * (value - 7.5) / 21.0 + 273.15;
			};
	}

	@Override
	public double convertFromBaseUnits(double value) {
		return switch (this) {
				case DELISLE -> 1.5 * (373.15 - value);
				case FAHRENHEIT -> 9.0 * value / 5.0 - 459.67;
				case NEWTON -> 0.33 * (value - 273.15);
				case RANKINE -> 1.8 * value;
				case REAUMUR -> 0.8 * (value - 273.15);
				case ROMER -> 21.0 * (value - 273.15) / 40.0 + 7.5;
			};
	}

	@Override public Map<Unit, Integer> baseUnits() { return BASE_UNITS; }
	@Override public List<String> symbols() { return symbols; }
	@Override public boolean prefixAllowed(String symbol) { return true; }
	@Override public boolean canUseFactor() { return false; }
	@Override public double factor(String symbol) { return Double.NaN; }
	@Override public Set<UnitPrefix> prefixes() { return Units.DEFAULT_PREFIXES; }
	@Override public boolean isBasic() { return false; }
	@Override public Set<Unit> compatibleUnits() { return Units.DEFAULT_UNITS; }
}
