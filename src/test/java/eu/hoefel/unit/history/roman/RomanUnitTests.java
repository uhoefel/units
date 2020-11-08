package eu.hoefel.unit.history.roman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the Roman unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Roman units")
class RomanUnitTests {

	@DisplayName("Testing Roman units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(RomanUnit.class)
	void testRomanUnitConversions(RomanUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing Roman units -> SI base units")
	@ParameterizedTest
	@EnumSource(RomanUnit.class)
	void testRomanUnitBaseUnits(RomanUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}

//	@DisplayName("Testing specific Roman <-> SI conversions")
//	@Test
//	void testRomanConversion() {
//		assertEquals(1, Units.convert(0.296, "m", "pes", RomanUnit.values()), 1e-15);
//		assertEquals(1, Units.convert(28800, "pes^2", "jugerum", RomanUnit.values()), 1e-15);
//		assertEquals(2523.3407999999995, Units.convert(1, "jugerum", "m^2", RomanUnit.values()));
//		assertEquals(0.25, Units.convert(1, "quadrans", "jugerum", RomanUnit.values()));
//		assertEquals(25.934335999999988, Units.convert(1, "amphora-quadrantal", "dm^3", RomanUnit.values()));
//		assertEquals(0.04701967592592592, Units.convert(1, "granum", "g", RomanUnit.values()));
//	}
}