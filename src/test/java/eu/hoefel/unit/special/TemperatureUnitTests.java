package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the temperature unit implementation.
 * 
 * @author Udo Hoefel
 */
@SuppressWarnings("javadoc")
@DisplayName("Temperature units")
class TemperatureUnitTests {

    @DisplayName("Testing temperature units <-> SI conversions")
    @ParameterizedTest
    @EnumSource(TemperatureUnit.class)
    void testTemperatureUnitConversions(TemperatureUnit unit) {
        // there are some conversion losses, unfortunately
        assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)), 1e-13);
    }

    @DisplayName("Testing temperature units -> SI base units")
    @ParameterizedTest
    @EnumSource(TemperatureUnit.class)
    void testTemperatureUnitBaseUnits(TemperatureUnit unit) {
        assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
    }
}