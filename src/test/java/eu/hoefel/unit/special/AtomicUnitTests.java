package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the atomic unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Atomic units")
@SuppressWarnings("javadoc")
class AtomicUnitTests {

	@DisplayName("Testing atomic units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(AtomicUnit.class)
	void testAtomicUnitConversions(AtomicUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing atomic units -> SI base units")
	@ParameterizedTest
	@EnumSource(AtomicUnit.class)
	void testAtomicUnitBaseUnits(AtomicUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}
}