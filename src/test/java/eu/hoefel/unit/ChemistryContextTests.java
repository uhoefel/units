package eu.hoefel.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import eu.hoefel.unit.context.ChemistryContext;

/**
 * Tests for everything related to the chemistry context implementation (with
 * respect to units).
 * 
 * @author Udo Hoefel
 */
@DisplayName("Chemistry context")
class ChemistryContextTests {

	@DisplayName("Interpreting specific units in chemistry context")
	@Test
	void testChemistryContext() {
		assertEquals(Set.of("molality"), Units.inContext("mol kg^-1", UnitContextMatch.COMPATIBLE, ChemistryContext.GENERAL));
		assertEquals(Set.of("third virial coefficient"), ChemistryContext.THERMODYNAMICS.forUnits("m^6 mol^-2", UnitContextMatch.COMPATIBLE));
		assertEquals(Set.of("rate of concentration change", "rate of reaction"), Units.inContext("mol m^-3 s^-1", UnitContextMatch.COMPATIBLE, ChemistryContext.KINETICS));
		assertEquals(Set.of("charge number of an ion", "pH", "electrochemical transfer coefficient", "transport number"), Units.inContext("", UnitContextMatch.COMPATIBLE, ChemistryContext.ELECTROCHEMISTRY));
		assertEquals(Set.of("specific surface area"), Units.inContext("m^2 kg^-1", UnitContextMatch.COMPATIBLE, ChemistryContext.COLLOID_AND_SURFACE_CHEMISTRY));
		assertEquals(Set.of("coefficient of heat transfer"), Units.inContext("W m^-2 K^-1", UnitContextMatch.COMPATIBLE, ChemistryContext.TRANSPORT));
	}
}