package eu.hoefel.unit.si;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.constant.physics.PhysicsConstant;

/**
 * Tests for everything related to the SI common unit implementation.
 * 
 * @author Udo Hoefel
 */
@SuppressWarnings("javadoc")
@DisplayName("Common SI units")
class SiCommonUnitTests {

    @DisplayName("Testing SI common units <-> SI conversions")
    @ParameterizedTest
    @EnumSource(SiCommonUnit.class)
    void testSiCommonUnitConversions(SiCommonUnit unit) {
        assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
    }

    @DisplayName("Testing SI common units -> SI base units")
    @ParameterizedTest
    @EnumSource(SiCommonUnit.class)
    void testSiCommonUnitBaseUnits(SiCommonUnit unit) {
        assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
    }

    @DisplayName("Testing safeness of baseUnits")
    @ParameterizedTest
    @EnumSource(SiCommonUnit.class)
    void testSiCommonUnitBaseUnitSafety(SiCommonUnit unit) {
        var baseUnits = assertDoesNotThrow(unit::baseUnits);
        assertThrows(UnsupportedOperationException.class, () -> baseUnits.put(SiBaseUnit.AMPERE, 3));
    }

    @DisplayName("Testing value of electronvolt <-> elementary charge")
    @Test
    void testElectronvoltValue() {
        assertEquals(PhysicsConstant.ELEMENTARY_CHARGE.value(), SiCommonUnit.ELECTRONVOLT.factor() / SiPrefix.KILO.factor());
    }

    @DisplayName("Testing value of dalton <-> unified atomic mass unit")
    @Test
    void testDaltonValue() {
        assertEquals(PhysicsConstant.UNIFIED_ATOMIC_MASS_UNIT.value(), SiCommonUnit.DALTON.factor() / SiPrefix.KILO.factor());
    }
}
