package eu.hoefel.unit;

import java.util.Set;

import eu.hoefel.unit.si.SiDerivedUnit;

/**
 * Determines how strict the match between a symbol and the available named
 * symbols should be (see
 * {@link Units#inContext(String, UnitContextMatch, UnitContext, Unit[]...)}).
 * 
 * @author Udo Hoefel
 */
public enum UnitContextMatch {

	/**
	 * An exact match of two units is only fulfilled if all units and exponents are
	 * identical in both units.
	 * <p>
	 * For example:<br>
	 * <ul>
	 * <li>{@code "kg^3 s^-1"} is an exact match to {@link "s^-1 kg^3"}
	 * <li>{@code "kg^3 s^-1 sr"} is <em>not</em> an exact match to {@code "s^-1
	 * kg^3"}, as the {@link SiDerivedUnit#STERADIAN steradian} part does not exactly match, even though the
	 * dimension of steradian is 1.
	 * </ul>
	 */
	EXACT(UnitContextMatch::exactMatch),

	/**
	 * Two units are considered equivalent if one can convert one into the other
	 * one, and no prefactors/shifts are necessary by the transformation.
	 *  <p>
	 * For example:<br>
	 * <ul>
	 * <li>{@code "kg^3 s^-1 sr"} is compatible to {@link "s^-1 kg^3"}
	 * <li>{@code "Mg^3 s^-1 sr"} is <em>not</em> compatible to {@link "s^-1 kg^3"}
	 * </ul>
	 */
	EQUIVALENT((origin, target, extraUnits) -> Units.equivalent(1, origin, target, extraUnits)),

	/**
	 * Two units are considered compatible if one can convert one into the other
	 * one, regardless of potential prefactors/shifts.
	 * <p>
	 * For example:<br>
	 * <ul>
	 * <li>{@code "Mg^3 s^-1 sr"} is compatible to {@link "s^-1 kg^3"}
	 * </ul>
	 */
	COMPATIBLE(Units::convertible);

	/** The predicate to use for checking units "matching". */
	private final UnitContextMatchPredicate predicate;

	/**
	 * This is a helper predicate interface to be able to use method references in
	 * {@link UnitContextMatch}.
	 * 
	 * @author Udo Hoefel
	 */
	@FunctionalInterface
	private interface UnitContextMatchPredicate {

		/**
		 * Checks whether the given origin and target units are considered "matching"
		 * (the exact behavior corresponding to {@link UnitContextMatch}).
		 * 
		 * @param origin     the original units, e.g. "kg m^2"
		 * @param target     the target units, e.g. "m^2 kg"
		 * @param extraUnits additional units to check against
		 * @return true if the units "match"
		 */
	    boolean matches(String origin, String target, Unit[]... extraUnits);
	}

	/**
	 * Constructor for the match options and the {@link UnitContext}.
	 * 
	 * @param predicate the method reference to use for check if two units are
	 *                  considered matching
	 */
	UnitContextMatch(UnitContextMatchPredicate predicate) {
		this.predicate = predicate;
	}

	/**
	 * Checks whether the two given units are considered matching (with the exact
	 * meaning of "matching" depending on the {@link UnitContextMatch} value).
	 * 
	 * @param origin     the original units, e.g. "kg m^2"
	 * @param target     the target units, e.g. "m^2 kg"
	 * @param extraUnits the additional units to check against
	 * @return true if the two units are matching
	 */
	public boolean matches(String origin, String target, Unit[]... extraUnits) {
		return predicate.matches(origin, target, extraUnits);
	}

	/**
	 * Checks whether the two given units are identical, except for potential
	 * differences in ordering of the symbol units.
	 * 
	 * @param origin     the original units, e.g. "kg m^2"
	 * @param target     the target units, e.g. "m^2 kg"
	 * @param extraUnits the additional units to check against
	 * @return true if the two units are exactly identical (save order)
	 */
	private static final boolean exactMatch(String origin, String target, Unit[]... extraUnits) {
		Set<UnitInfo> originUnits = Set.of(Units.collectInfo(origin, extraUnits));
		Set<UnitInfo> targetUnits = Set.of(Units.collectInfo(target, extraUnits));
		return originUnits.equals(targetUnits);
	}
}
