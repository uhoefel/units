package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the Swabian unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Swabian units")
@SuppressWarnings("javadoc")
class SwabianUnitTests {

	@DisplayName("Testing Swabian units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(SwabianUnit.class)
	void testSwabianUnitConversions(SwabianUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing Swabian units -> SI base units")
	@ParameterizedTest
	@EnumSource(SwabianUnit.class)
	void testSwabianUnitBaseUnits(SwabianUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}

	@DisplayName("Testing Swabian unit basicality")
	@ParameterizedTest
	@EnumSource(SwabianUnit.class)
	void testSwabianUnitBasics(SwabianUnit unit) {
		assertFalse(unit.isBasic());
	}

	@DisplayName("Testing specific Swabian <-> SI conversions")
	@Test
	void testSwabianConversion() {
		assertEquals(1, Units.convert(0.22, "mm", "muggaseggele", SwabianUnit.values()), 1e-15);
		assertEquals(0.22, Units.convert(1, "muggaseggele", "mm", SwabianUnit.values()));
		assertEquals(4545.454545454545, Units.convert(1, "m", "muggaseggele", SwabianUnit.values()));
		assertEquals(4000, Units.convert(1, "m^3", "viertele", SwabianUnit.values()));
		assertEquals(8 * 93914.35011269718, Units.convert(1, "m^3", "fitsele", SwabianUnit.values()));
		assertEquals(93914.35011269718, Units.convert(1, "m^3", "breggele", SwabianUnit.values()));
		assertEquals(9391.43501126972, Units.convert(1, "m^3", "brogga", SwabianUnit.values()));
		assertEquals(18782.87002253944, Units.convert(1, Unit.of("m^3"), SwabianUnit.WENGELE));
	}
}