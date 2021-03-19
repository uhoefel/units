package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the natural unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Natural units")
class NaturalUnitTests {

	@DisplayName("Testing natural units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(NaturalUnit.class)
	void testNaturalUnitConversions(NaturalUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing natural units -> SI base units")
	@ParameterizedTest
	@EnumSource(NaturalUnit.class)
	void testNaturalUnitBaseUnits(NaturalUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}

	@DisplayName("Testing specific natural unit <-> SI conversions")
	@Test
	void testNaturalUnitConversion() {
		assertEquals(3.8615926796089057E-13, Units.convert(1, "lambdabarC", "m", NaturalUnit.values()), 1e-23);
		assertEquals(3.8615926796089057E-13, Units.convert(1, NaturalUnit.OF_LENGTH, Unit.of("m")), 1e-23);
	}
}