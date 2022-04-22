package eu.hoefel.unit.si;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the SI base unit implementation.
 * 
 * @author Udo Hoefel
 */
@SuppressWarnings("javadoc")
@DisplayName("Base SI units")
class SiBaseUnitTests {

    @DisplayName("Testing SI base units <-> SI conversions")
    @ParameterizedTest
    @EnumSource(SiBaseUnit.class)
    void testSiBaseUnitConversions(SiBaseUnit unit) {
        assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
    }

    @DisplayName("Testing SI base units -> SI base units")
    @ParameterizedTest
    @EnumSource(SiBaseUnit.class)
    void testSiBaseUnitBaseUnits(SiBaseUnit unit) {
        assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
    }
}
