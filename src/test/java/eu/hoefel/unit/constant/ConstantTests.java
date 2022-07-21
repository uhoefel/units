package eu.hoefel.unit.constant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiCommonUnit;
import eu.hoefel.unit.si.SiDerivedUnit;
import eu.hoefel.unit.special.NaturalUnit;
import eu.hoefel.unit.special.SwabianUnit;

/**
 * Tests for generic constants.
 * 
 * @author Udo Hoefel
 */
@SuppressWarnings("javadoc")
@DisplayName("Constants")
class ConstantTests {

    @DisplayName("Testing generic scientific constants")
    @Test
    void testGenericScientificConstant() {
        assertEquals(1e13, Constant.of(1, 0, "km").inUnitsOf("Angstrom").value());
        assertEquals(1.0055256868158594e-31, Constant.of(3, 0, "m").pow(2).inUnitsOf("ly^2").value());
        assertEquals(9, Constant.of(3, 0, "m").pow(2).mul(1, 0, Unit.of("kg s^-2")).inUnitsOf("J").value());

        var gsc = Constant.of(4, 0, "kg^2").root(2);
        assertEquals(2, gsc.value());
        assertEquals("kg", gsc.unit().symbols().get(0));

        gsc = Constant.of(4, 0, "kg^4").root(2);
        assertEquals("kg^2", gsc.unit().symbols().get(0));

        var constant = assertDoesNotThrow(() -> Constant.of(1, 0, "mol^10"));
        var e = assertThrows(UnsupportedOperationException.class, () -> constant.root(3));
        assertEquals("Taking the 3rd root of mol^10 would yield a non-integer exponent (3.333333) for mol. "
                + "Taking the root of a Constant is (currently) only allowed if the unit "
                + "exponents stay integers.", e.getMessage());

        gsc = Constant.of(4, 0, "m kg^2");
        assertEquals("\\SI{4}{\\m \\kg\\tothe{2}}", gsc.latexCode().get(0));

        gsc = Constant.of(4, 0.1, "m kg^2");
        assertEquals("\\SI[separate-uncertainty=true]{4\\pm0.1}{\\m \\kg\\tothe{2}}", gsc.latexCode().get(0));

        gsc = Constant.of(4e13, 1e11, "m kg^2");
        assertEquals("\\SI[separate-uncertainty=true]{4\\pm0.01e13}{\\m \\kg\\tothe{2}}", gsc.latexCode().get(0));

        gsc = Constant.of(4e-1, 1e-13, "m kg^2");
        assertEquals("\\SI[separate-uncertainty=true]{0.4\\pm0.0000000000001}{\\m \\kg\\tothe{2}}", gsc.latexCode().get(0));
    }

    @DisplayName("Tests equivalency")
    @Test
    void testScientificConstantEquivalency() {
        Constant constant = Constant.of(3.31, 0, SiBaseUnit.KILOGRAM);
        Constant constant2 = Constant.of(3.31, 0, "km");

        assertTrue(constant.equivalent(constant));
        assertFalse(constant.equivalent(constant2));
        assertTrue(constant.equivalent(Constant.of(3310, 0, "g")));
        assertFalse(constant.equivalent(Constant.of(345, 0, "g")));
        assertFalse(constant.equivalent(Constant.of(31232, 0, "m")));
        assertFalse(constant.equivalent(Constant.of(3.31, 0.1, "kg")));
    }

    @DisplayName("Tests unit conversion")
    @Test
    void testScientificConstantUnitConversion() {
        Constant constant1 = Constant.of(900, 1, SiBaseUnit.SECOND);
        Constant constant2 = constant1.inUnitsOf(SiCommonUnit.HOUR);

        assertTrue(constant1.equivalent(constant2));
        assertEquals(0.25, constant2.value());
        assertEquals(1.0 / 3600, constant2.uncertainty());
        assertEquals("h", constant2.unit().symbols().get(0));
    }

    @DisplayName("Tests convertibility")
    @Test
    void testScientificConstantConvertibility() {
        Constant constant1 = Constant.of(0.232, 23, SiBaseUnit.SECOND);

        assertTrue(constant1.convertible(SiCommonUnit.YEAR));
        assertTrue(constant1.convertible(SiCommonUnit.DAY));
        assertTrue(constant1.convertible(SiCommonUnit.MINUTE));

        Constant constant2 = Constant.of(414, 474, "breggele", SwabianUnit.values());

        assertTrue(constant2.convertible("mm^3"));

        Constant constant3 = Constant.of(34.23, 664, "m^3");
        
        assertTrue(constant3.convertible(SwabianUnit.BREGGELE));
    }

    @DisplayName("Tests addition")
    @Test
    void testScientificConstantAddition() {
        Constant constant1 = Constant.of(0.43241, 13.13, SiBaseUnit.MOLE);
        Constant constant2 = constant1.add(2.45);

        assertEquals(2.88241, constant2.value());
        assertEquals(13.13, constant2.uncertainty());
        assertEquals("mol", constant2.unit().symbols().get(0));
    }

    @DisplayName("Tests subtraction")
    @Test
    void testScientificConstantSubtraction() {
        Constant constant1 = Constant.of(141.13451, 0.0012, SiBaseUnit.MOLE);
        Constant constant2 = constant1.sub(11151.4132);

        assertEquals(-11010.27869, constant2.value(), 1e-8);
        assertEquals(0.0012, constant2.uncertainty());
        assertEquals("mol", constant2.unit().symbols().get(0));
    }

    @DisplayName("Tests multiplication")
    @Test
    void testScientificConstantMultiplication() {
        Constant constant1 = Constant.of(141.13451, 0.0012, SiBaseUnit.KELVIN);
        var c = Constant.of(1, 0, NaturalUnit.OF_MASS);
        Constant constant2 = constant1.mul(c);

        assertTrue(constant2.convertible("K kg"));
        assertEquals(141.13451, constant2.value());
        assertEquals(0.0012, constant2.uncertainty());
        assertEquals("K me", constant2.unit().symbols().get(0));
    }

    @DisplayName("Tests division")
    @Test
    void testScientificConstantDivision() {
        Constant constant1 = Constant.of(141.13451, 0.0012, SiBaseUnit.KELVIN);
        Constant constant2 = constant1.div(3.21, 0.0, SiDerivedUnit.DEGREE_CELSIUS);

        assertFalse(constant2.convertible("K kg", SiBaseUnit.values(), NaturalUnit.values()));
        assertEquals(43.967137071651095, constant2.value());
        assertEquals(3.7383177570093456E-4, constant2.uncertainty());
        assertEquals("K °C^-1", constant2.unit().symbols().get(0));
    }

    @DisplayName("Tests Texability")
    @Test
    void testScientificConstantTeXability() {
        Constant constant1 = Constant.of(141.13451, 0.0012, SiBaseUnit.KELVIN);

        assertTrue(constant1.neededPackages().stream().allMatch(pckg -> pckg.incompatiblePackages().isEmpty()));
        assertTrue(constant1.preambleExtras().stream().anyMatch(lpe -> lpe.preambleLine().equals("\\DeclareSIUnit\\K{K}")));
        assertTrue(constant1.neededPackages().stream().anyMatch(pckg -> pckg.name().equals("siunitx")));
        assertEquals(List.of("\\SI[separate-uncertainty=true]{141.13451\\pm0.0012}{\\K}"), constant1.latexCode());
    }
}
