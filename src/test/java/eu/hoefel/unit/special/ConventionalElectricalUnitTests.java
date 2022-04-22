package eu.hoefel.unit.special;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the conventional electrical unit implementation.
 * 
 * @author Udo Hoefel
 */
@SuppressWarnings("javadoc")
@DisplayName("Conventional electrical units")
class ConventionalElectricalUnitTests {

    @DisplayName("Testing conventional electrical units <-> SI conversions")
    @ParameterizedTest
    @EnumSource(ConventionalElectricalUnit.class)
    void testConventionalElectricalUnitConversions(ConventionalElectricalUnit unit) {
        assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
    }

    @DisplayName("Testing conventional electrical units -> SI base units")
    @ParameterizedTest
    @EnumSource(ConventionalElectricalUnit.class)
    void testConventionalElectricalUnitBaseUnits(ConventionalElectricalUnit unit) {
        assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
    }
}