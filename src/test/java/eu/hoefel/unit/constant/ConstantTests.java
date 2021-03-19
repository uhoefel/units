package eu.hoefel.unit.constant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.hoefel.unit.Unit;

/**
 * Tests for generic constants.
 * 
 * @author Udo Hoefel
 */
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
}
