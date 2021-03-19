package eu.hoefel.unit.us;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the US customary unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("US customary units")
class USCustomaryUnitTests {

	@DisplayName("Testing US customary units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(USCustomaryUnit.class)
	void testBinaryBaseUnitConversions(USCustomaryUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing US customary units -> SI base units")
	@ParameterizedTest
	@EnumSource(USCustomaryUnit.class)
	void testBinaryBaseUnitBaseUnits(USCustomaryUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}
}