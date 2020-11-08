/**
 * This module provides support for units and constants.
 * <p>
 * The {@link eu.hoefel.unit} is the package containing the base classes needed
 * for the handling of units.
 * <p>
 * The {@link eu.hoefel.unit.binary} is the package containing the
 * implementations needed for the handling of binary units and prefixes.
 * <p>
 * The {@link eu.hoefel.unit.constant} is the package containing the classes
 * needed for the definition of constants.
 * <p>
 * The {@link eu.hoefel.unit.constant.math} is the package containing the
 * implementation needed for the handling of (some) math constants.
 * <p>
 * The {@link eu.hoefel.unit.constant.physics} is the package containing the
 * implementation needed for the handling of (some) physics constants.
 * <p>
 * The {@link eu.hoefel.unit.context} is the package containing the
 * implementations needed for the handling of (some) chemistry and physics
 * contexts.
 * <p>
 * The {@link eu.hoefel.unit.history.roman} is the package containing the
 * implementations needed for the handling of (some) ancient roman units
 * (undocumented!).
 * <p>
 * The {@link eu.hoefel.unit.imperial} is the package containing the
 * implementation needed for the handling of British imperial units.
 * <p>
 * The {@link eu.hoefel.unit.level} is the package containing the implementation
 * needed for the handling of level units.
 * <p>
 * The {@link eu.hoefel.unit.si} is the package containing the implementations
 * needed for the handling of SI units.
 * <p>
 * The {@link eu.hoefel.unit.special} is the package containing (some)
 * implementations needed for the handling of more rarely used units, like
 * {@link eu.hoefel.unit.special.Planck Planck} units.
 * <p>
 * The {@link eu.hoefel.unit.us} is the package containing the implementation
 * needed for the handling of US customary units.
 * 
 * @author Udo Hoefel
 */
module eu.hoefel.unit {
	exports eu.hoefel.unit;
	exports eu.hoefel.unit.binary;
	exports eu.hoefel.unit.constant;
	exports eu.hoefel.unit.constant.math;
	exports eu.hoefel.unit.constant.physics;
	exports eu.hoefel.unit.context;
	exports eu.hoefel.unit.history.roman;
	exports eu.hoefel.unit.imperial;
	exports eu.hoefel.unit.si;
	exports eu.hoefel.unit.special;
	exports eu.hoefel.unit.us;

	// ugh...
	opens eu.hoefel.unit               to org.junit.platform.commons;
	opens eu.hoefel.unit.binary        to org.junit.platform.commons;
	opens eu.hoefel.unit.context       to org.junit.platform.commons;
	opens eu.hoefel.unit.history.roman to org.junit.platform.commons;
	opens eu.hoefel.unit.imperial      to org.junit.platform.commons;
	opens eu.hoefel.unit.si            to org.junit.platform.commons;
	opens eu.hoefel.unit.special       to org.junit.platform.commons;
	opens eu.hoefel.unit.us            to org.junit.platform.commons;

	requires java.logging;
	requires org.junit.jupiter.api;
	requires eu.hoefel.utils;
	requires transitive eu.hoefel.jatex;
}