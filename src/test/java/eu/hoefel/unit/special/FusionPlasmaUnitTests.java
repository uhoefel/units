package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the fusion plasma unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Fusion plasma units")
class FusionPlasmaUnitTests {

	@DisplayName("Testing fusion plasma units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(FusionPlasmaUnit.class)
	void testFusionPlasmaUnitConversions(FusionPlasmaUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing fusion plasma units -> SI base units")
	@ParameterizedTest
	@EnumSource(FusionPlasmaUnit.class)
	void testFusionPlasmaUnitBaseUnits(FusionPlasmaUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}
}