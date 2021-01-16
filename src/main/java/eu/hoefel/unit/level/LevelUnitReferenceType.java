package eu.hoefel.unit.level;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

import eu.hoefel.unit.Unit;
import eu.hoefel.unit.Units;
import eu.hoefel.unit.si.SiBaseUnit;
import eu.hoefel.unit.si.SiDerivedUnit;

/**
 * For comparing quantities via {@link LevelUnit} one needs to know whether the
 * reference unit behaves like a power or root power quantity. This enum
 * provides reference units for both types, and allows therefore (to some
 * extent) automatic detection of the appropriate type.
 * 
 * @author Udo Hoefel
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Power,_root-power,_and_field_quantities">Wikpedia
 *      article about power and root power quantities</a>
 * @see <a href=
 *      "https://books.google.de/books?id=OywDx9pxCMYC&pg=PA11&redir_esc=y#v=onepage&q&f=false">Moore,
 *      B.C.J. (1995) (ed.). Hearing. Volume 6 of the Handbook of Perception and
 *      Cognition (Academic Press, New York), page 11.</a>
 */
public enum LevelUnitReferenceType {
	// Note that energy density/Pascal appear to be both a power and field reference
	// unit type, so we do not add them here -> the user has to define the type for
	// them

	/**
	 * If the reference unit for a level unit is of type root power it is implied
	 * that its square is proportional to power. Further units to which reference
	 * units considered to be of type power may be proportional include e.g.
	 * {@link SiDerivedUnit#VOLT V}, {@link SiBaseUnit#AMPERE A} and
	 * {@link SiDerivedUnit#TESLA T}.
	 * 
	 * @see <a href=
	 *      "https://www.iso.org/obp/ui/#iso:std:iso:80000:-1:ed-1:v1:en">ISO
	 *      80000-1 § Annex C.2</a>
	 */
	ROOT_POWER(
			/* electric current */SiBaseUnit.AMPERE,
			/* magnetic induction */SiDerivedUnit.TESLA,
			/* electric potential */SiDerivedUnit.VOLT,
			/* speed of sound? Schallschnelle in german */Unit.of("m s^-1"), 
			/* electric displacement field */Unit.of("A s m^-2"),
			/* electrical field strength */Unit.of("N C^-1"), 
			/* charge density */Unit.of("C m^-1"),
			/* charge density */Unit.of("C m^-2"),
			/* charge density */Unit.of("C m^-3"),
			/* gravitational field strength */Unit.of("N kg^-1")),
			
	/**
	 * If the reference unit for a level unit is of type power it is implied that it
	 * is directly proportional to power (or energy). Further units to which
	 * reference units considered to be of type power may be proportional include
	 * e.g. {@link SiDerivedUnit#GRAY Gy} and power densities.
	 * 
	 * @see <a href="https://www.iso.org/obp/ui/#iso:std:iso:80000:-1:ed-1:v1:en">ISO 80000-1 § Annex C.3</a>
	 */
	POWER(
			/* power */SiDerivedUnit.WATT, 
			/* power density */Unit.of("W m^-1"),
			/* power density */Unit.of("W m^-2"),
			/* power density */Unit.of("W m^-3"),
			/* energy */SiDerivedUnit.JOULE,
			/* energy density */Unit.of("J m^-1"),
			/* energy density */Unit.of("J m^-2"),
			/* noise temperature */SiBaseUnit.KELVIN,
			/* luminous intensity */SiBaseUnit.CANDELA,
			/* optical luminance */Unit.of("cd m^-1"),
			/* optical luminance */Unit.of("cd m^-2"),
			/* optical luminance */Unit.of("cd m^-3"),
			/* dose of ionizing radiation */SiDerivedUnit.GRAY,
			/* dose of ionizing radiation */SiDerivedUnit.SIEVERT,
			/* dBZ (meteorology) */Unit.of("mm^6 m^-3"));

	/** The reference units for the type. */
	private final Set<Unit> referenceUnits;

	/**
	 * Constructor for reference unit types.
	 * 
	 * @param refUnits the reference units
	 */
	private LevelUnitReferenceType(Unit... refUnits) {
		referenceUnits = Set.of(refUnits);
	}

	/**
	 * Gets the reference units, i.e., if a unit is proportional to one of the units
	 * given here it is of the type for which the match was found.
	 * 
	 * @return the reference units
	 */
	public final Set<Unit> referenceUnits() {
		return referenceUnits;
	}

	/**
	 * Checks the convertibility (and subqsequently proportionality if nothing has
	 * been found) of <code>refUnit</code> to known
	 * {@link LevelUnitReferenceType#POWER power} and
	 * {@link LevelUnitReferenceType#ROOT_POWER root power} units and gets the type
	 * for which a match was found.
	 * 
	 * @param refUnit the reference unit to check
	 * @return the reference unit type if found, else an empty otpional
	 */
	public static final Optional<LevelUnitReferenceType> findMatchingType(Unit refUnit) {
		Objects.requireNonNull(refUnit);

		List<BiPredicate<Unit, Unit>> checks = List.of(Units::convertible, Units::proportional);
		for (BiPredicate<Unit, Unit> check : checks) {
			for (LevelUnitReferenceType type : LevelUnitReferenceType.values()) {
				for (Unit typeRefUnit : type.referenceUnits()) {
					if (check.test(refUnit, typeRefUnit)) {
						return Optional.of(type);
					}
				}
			}
		}
		return Optional.empty();
	}
}
