package eu.hoefel.unit.constant.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class intends to test the maths constants.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Math constants")
class MathsConstantTests {

	@DisplayName("Tests math constant toString")
	@Test
	void testToString() {
		assertEquals("0.643410546288338 ± 0.0", MathConstant.CAHEN_CONSTANT.toString());
	}
}
