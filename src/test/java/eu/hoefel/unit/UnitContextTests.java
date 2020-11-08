package eu.hoefel.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.hoefel.unit.context.PhysicsContext;

/**
 * Tests for everything related to the unit context (with respect to units).
 * 
 * @author Udo Hoefel
 */
@DisplayName("Generic unit context")
@SuppressWarnings("javadoc")
class UnitContextTests {

	@DisplayName("Interpreting specific units with different matching types")
	@Test
	void testUnitContext() {
		assertEquals(Set.of("angular velocity"), Units.inContext("rad s^-1", UnitContextMatch.EXACT, PhysicsContext.GENERAL));
		assertEquals(Set.of("angular velocity", "frequency"), Units.inContext("rad s^-1", UnitContextMatch.EQUIVALENT, PhysicsContext.GENERAL));
		assertEquals(Set.of(), Units.inContext("rad ms^-1", UnitContextMatch.EQUIVALENT, PhysicsContext.GENERAL));
		assertEquals(Set.of("angular velocity", "frequency"), Units.inContext("rad ms^-1", UnitContextMatch.COMPATIBLE, PhysicsContext.GENERAL));
	}
}