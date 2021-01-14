package eu.hoefel.unit.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;

/**
 * Tests for everything related to the level units implementation.
 * 
 * @author Udo Hoefel
 */
@DisplayName("Level units")
class LevelUnitTests {

	@DisplayName("Testing level base units <-> level conversions")
	@ParameterizedTest
	@EnumSource(LevelUnit.class)
	void testLevelBaseUnitConversions(LevelUnit unit) {
	    assertEquals(1, unit.convertFromBaseUnits(unit.convertToBaseUnits(1)));
	}

	@DisplayName("Testing level base units -> level base units")
	@ParameterizedTest
	@EnumSource(LevelUnit.class)
	void testLevelBaseUnitBaseUnits(LevelUnit unit) {
		assertTrue(Units.convertible(unit, Unit.of(Units.toSymbol(unit.baseUnits()))));
	}

	@DisplayName("Testing Bel in reference to some unit")
	@Test
	void testBelInReferenceTo() {
		Unit u = LevelUnit.BEL.inReferenceTo(1, Unit.of("mW"));
		assertEquals(List.of("log(re 1 mW)"), u.symbols());
		// note that this is with respect to Bel, not deciBel
		assertEquals(1.9952623149688795, u.convertToBaseUnits(0.3));
		assertEquals(100, u.convertToBaseUnits(2));
		assertEquals(0.3010299956639812, u.convertFromBaseUnits(2));
		
		u = LevelUnit.BEL.inReferenceTo(1.0000002, Unit.of("mJ"));
		assertEquals(List.of("log(re 1.0000002 mJ)"), u.symbols());
		// note that this is with respect to Bel, not deciBel
		assertEquals(1.9952627140213424, u.convertToBaseUnits(0.3));
		assertEquals(1000, u.convertToBaseUnits(3), 2e-4);
		assertEquals(0.3010299088050935, u.convertFromBaseUnits(2));

		u = LevelUnit.BEL.inReferenceTo(1.0300000, Unit.of("mV"));
		assertEquals(List.of("log(re 1.03 mV)"), u.symbols());
		// note that this is with respect to Bel, not deciBel
		assertEquals(1.454913670961437, u.convertToBaseUnits(0.3));
		assertEquals(32.57145989973431, u.convertToBaseUnits(3));
		assertEquals(0.576385541917618, u.convertFromBaseUnits(2));

		u = LevelUnit.BEL.inReferenceTo(1, Unit.of("W m^-2"));
		assertEquals(List.of("log(re 1 W m^-2)"), u.symbols());
		// note that this is with respect to Bel, not deciBel
		assertEquals(1.9952623149688795, u.convertToBaseUnits(0.3));
		assertEquals(0.3010299956639812, u.convertFromBaseUnits(2));
	}

	@DisplayName("Testing Neper in reference to some unit")
	@Test
	void testNeperInReferenceTo() {
		Unit u = LevelUnit.NEPER.inReferenceTo(1, Unit.of("mW"));
		assertEquals(List.of("ln(re 1 mW)"), u.symbols());
		assertEquals(1.8221188003905089, u.convertToBaseUnits(0.3));
		assertEquals(54.598150033144236, u.convertToBaseUnits(2));
		assertEquals(0.34657359027997264, u.convertFromBaseUnits(2));
		
		u = LevelUnit.NEPER.inReferenceTo(1.0000002, Unit.of("mJ"));
		assertEquals(List.of("ln(re 1.0000002 mJ)"), u.symbols());
		assertEquals(1.8221191648142687, u.convertToBaseUnits(0.3));
		assertEquals(403.42887417849374, u.convertToBaseUnits(3), 2e-4);
		assertEquals(0.3465734902799827, u.convertFromBaseUnits(2));

		u = LevelUnit.NEPER.inReferenceTo(1.0300000, Unit.of("mV"));
		assertEquals(List.of("ln(re 1.03 mV)"), u.symbols());
		assertEquals(1.3903545718032833, u.convertToBaseUnits(0.3));
		assertEquals(20.688103030883298, u.convertToBaseUnits(3));
		assertEquals(0.6635883783184009, u.convertFromBaseUnits(2));

		u = LevelUnit.NEPER.inReferenceTo(1, Unit.of("W m^-2"));
		assertEquals(List.of("ln(re 1 W m^-2)"), u.symbols());
		assertEquals(1.8221188003905089, u.convertToBaseUnits(0.3));
		assertEquals(0.34657359027997264, u.convertFromBaseUnits(2));
	}
}