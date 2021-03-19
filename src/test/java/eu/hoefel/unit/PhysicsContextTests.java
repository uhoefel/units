package eu.hoefel.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.hoefel.unit.context.PhysicsContext;

/**
 * Tests for everything related to the physics context implementation (with
 * respect to units).
 * 
 * @author Udo Hoefel
 */
@DisplayName("Physics context")
class PhysicsContextTests {

	@DisplayName("Interpreting specific units in physics context")
	@Test
	void testPhysicsContext() {
		assertEquals(Set.of("energy"), Units.inContext("J", UnitContextMatch.COMPATIBLE, PhysicsContext.GENERAL));
		assertEquals(Set.of("luminous exposure"), Units.inContext("lx s", UnitContextMatch.COMPATIBLE, PhysicsContext.PHOTOMETRY));
		assertEquals(Set.of("luminous flux", "luminous intensity"), PhysicsContext.PHOTOMETRY.forUnits("cd", UnitContextMatch.COMPATIBLE));
		assertEquals(Set.of("radiant flux", "radiant intensity"), Units.inContext("W", UnitContextMatch.COMPATIBLE, PhysicsContext.RADIOMETRY));
		assertEquals(Set.of("irradiance", "radiance", "radiant exitance", "radiosity"), Units.inContext("W sr^-1 m^-2", UnitContextMatch.COMPATIBLE, PhysicsContext.RADIOMETRY));
		assertEquals(Set.of("luminous exposure"), Units.inContext("lx s", UnitContextMatch.COMPATIBLE, PhysicsContext.PHOTOMETRY));
	}
}