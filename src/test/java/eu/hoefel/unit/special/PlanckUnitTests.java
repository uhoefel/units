package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the Planck unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Planck units")
class PlanckUnitTests {

	@DisplayName("Testing Planck units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(PlanckUnit.class)
	void testPlanckUnitConversions(PlanckUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing Planck units -> SI base units")
	@ParameterizedTest
	@EnumSource(PlanckUnit.class)
	void testPlanckUnitBaseUnits(PlanckUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}
}