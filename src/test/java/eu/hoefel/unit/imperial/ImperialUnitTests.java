package eu.hoefel.unit.imperial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the imperial unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Imperial units")
@SuppressWarnings("javadoc")
class ImperialUnitTests {

	@DisplayName("Testing imperial units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(ImperialUnit.class)
	void testBinaryBaseUnitConversions(ImperialUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing imperial units -> SI base units")
	@ParameterizedTest
	@EnumSource(ImperialUnit.class)
	void testBinaryBaseUnitBaseUnits(ImperialUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}
}