package eu.hoefel.unit.si;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the SI derived unit implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Derived SI units")
class SiDerivedUnitTests {

	@DisplayName("Testing SI derived units <-> SI conversions")
	@ParameterizedTest
	@EnumSource(SiDerivedUnit.class)
	void testSiDerivedUnitUnitConversions(SiDerivedUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing SI derived units -> SI base units")
	@ParameterizedTest
	@EnumSource(SiDerivedUnit.class)
	void testSiDerivedUnitBaseUnits(SiDerivedUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}
}
