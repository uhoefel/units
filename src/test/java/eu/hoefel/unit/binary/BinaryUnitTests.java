package eu.hoefel.unit.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the binary (base) unit implementation.
 * 
 * @author Udo Hoefel
 */
@SuppressWarnings("javadoc")
@DisplayName("Base binary units")
class BinaryUnitTests {

    @DisplayName("Testing binary base units <-> Binary conversions")
    @ParameterizedTest
    @EnumSource(BinaryUnit.class)
    void testBinaryBaseUnitConversions(BinaryUnit unit) {
        assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
    }

    @DisplayName("Testing binary base units -> binary base units")
    @ParameterizedTest
    @EnumSource(BinaryUnit.class)
    void testBinaryBaseUnitBaseUnits(BinaryUnit unit) {
        assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
    }
}